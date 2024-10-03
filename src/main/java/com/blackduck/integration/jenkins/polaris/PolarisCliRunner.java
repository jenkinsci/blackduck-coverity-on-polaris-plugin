/*
 * blackduck-coverity-on-polaris
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jenkins.polaris;

import com.blackduck.integration.jenkins.polaris.extensions.global.PolarisGlobalConfig;
import com.blackduck.integration.jenkins.polaris.extensions.tools.PolarisCli;
import com.blackduck.integration.jenkins.polaris.service.GetPathToPolarisCli;
import com.blackduck.integration.jenkins.polaris.service.PolarisCliArgumentService;
import com.blackduck.integration.jenkins.polaris.service.PolarisEnvironmentService;
import com.blackduck.integration.jenkins.polaris.service.PolarisPhoneHomeService;
import com.blackduck.integration.polaris.common.configuration.PolarisServerConfigBuilder;
import com.blackduck.integration.polaris.common.exception.PolarisIntegrationException;
import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jenkins.exception.JenkinsUserFriendlyException;
import com.synopsys.integration.jenkins.service.JenkinsConfigService;
import com.synopsys.integration.jenkins.service.JenkinsRemotingService;
import com.synopsys.integration.jenkins.wrapper.JenkinsProxyHelper;
import com.synopsys.integration.jenkins.wrapper.JenkinsVersionHelper;
import com.synopsys.integration.jenkins.wrapper.SynopsysCredentialsHelper;
import com.synopsys.integration.log.IntLogger;
import com.synopsys.integration.phonehome.PhoneHomeResponse;
import com.synopsys.integration.util.IntEnvironmentVariables;
import com.synopsys.integration.util.OperatingSystemType;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;

public class PolarisCliRunner {
    private final PolarisCliArgumentService polarisCliArgumentService;
    private final PolarisEnvironmentService polarisEnvironmentService;
    private final PolarisPhoneHomeService polarisPhoneHomeService;
    private final JenkinsRemotingService jenkinsRemotingService;
    private final JenkinsConfigService jenkinsConfigService;
    private final IntLogger logger;
    private final SynopsysCredentialsHelper credentialsHelper;
    private final JenkinsProxyHelper proxyHelper;
    private final JenkinsVersionHelper versionHelper;

    public PolarisCliRunner(
            IntLogger logger,
            PolarisCliArgumentService polarisCliArgumentService,
            PolarisEnvironmentService polarisEnvironmentService,
            PolarisPhoneHomeService polarisPhoneHomeService,
            JenkinsRemotingService jenkinsRemotingService,
            JenkinsConfigService jenkinsConfigService,
            SynopsysCredentialsHelper credentialsHelper,
            JenkinsProxyHelper proxyHelper,
            JenkinsVersionHelper versionHelper) {
        this.logger = logger;
        this.polarisCliArgumentService = polarisCliArgumentService;
        this.polarisEnvironmentService = polarisEnvironmentService;
        this.polarisPhoneHomeService = polarisPhoneHomeService;
        this.jenkinsRemotingService = jenkinsRemotingService;
        this.jenkinsConfigService = jenkinsConfigService;
        this.credentialsHelper = credentialsHelper;
        this.proxyHelper = proxyHelper;
        this.versionHelper = versionHelper;
    }

    public int runPolarisCli(String polarisCliName, String changeSetFileRemotePath, String polarisArgumentString)
            throws IOException, InterruptedException, IntegrationException {
        Optional<PhoneHomeResponse> successfulPhoneHomeResponse = polarisPhoneHomeService.phoneHome();

        try {
            String logMessage = versionHelper
                    .getPluginVersion("blackduck-coverity-on-polaris")
                    .map(version ->
                            String.format("Running Coverity on Polaris Platform for Jenkins version %s", version))
                    .orElse("Running Coverity on Polaris Platform for Jenkins");
            logger.info(logMessage);

            Optional<PolarisCli> polarisCliWithName = jenkinsConfigService.getInstallationForNodeAndEnvironment(
                    PolarisCli.DescriptorImpl.class, polarisCliName);

            if (!polarisCliWithName.isPresent()) {
                throw new JenkinsUserFriendlyException(
                        "[ERROR] Coverity on Polaris Platform cannot be executed: No Coverity on Polaris CLI Installation with the name "
                                + polarisCliName + " could be found in the global tool configuration.");
            }

            PolarisCli polarisCli = polarisCliWithName.get();

            PolarisGlobalConfig polarisGlobalConfig = jenkinsConfigService
                    .getGlobalConfiguration(PolarisGlobalConfig.class)
                    .orElseThrow(
                            () -> new PolarisIntegrationException(
                                    "No Coverity on Polaris Platform for Jenkins system configuration could be found, please check your system configuration."));

            PolarisServerConfigBuilder polarisServerConfigBuilder =
                    polarisGlobalConfig.getPolarisServerConfigBuilder(credentialsHelper, proxyHelper);

            IntEnvironmentVariables intEnvironmentVariables = polarisEnvironmentService.createPolarisEnvironment(
                    changeSetFileRemotePath, polarisServerConfigBuilder);

            String polarisCliHome = polarisCli.getHome();

            if (StringUtils.isBlank(polarisCliHome)) {
                throw new JenkinsUserFriendlyException(
                        "[ERROR] Coverity on Polaris Platform cannot be executed: The Coverity on Polaris CLI installation home could not be determined for the configured Coverity on Polaris CLI. Please ensure that this installation is correctly configured in the global tool configuration.");
            }

            String pathToPolarisCli = jenkinsRemotingService.call(new GetPathToPolarisCli(polarisCliHome));

            OperatingSystemType operatingSystemType = jenkinsRemotingService.getRemoteOperatingSystemType();
            List<String> tokenizedPolarisArguments =
                    jenkinsRemotingService.tokenizeArgumentString(polarisArgumentString);
            List<String> tokenizedResolvedArguments = jenkinsRemotingService.resolveEnvironmentVariables(
                    intEnvironmentVariables, tokenizedPolarisArguments);
            List<String> polarisArguments = polarisCliArgumentService.finalizePolarisCliArguments(
                    operatingSystemType, pathToPolarisCli, tokenizedResolvedArguments);

            return jenkinsRemotingService.launch(intEnvironmentVariables, polarisArguments);
        } finally {
            successfulPhoneHomeResponse.ifPresent(PhoneHomeResponse::getImmediateResult);
        }
    }
}
