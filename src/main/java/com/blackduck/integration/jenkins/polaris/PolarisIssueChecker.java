/*
 * blackduck-coverity-on-polaris
 *
 * Copyright ©2024 Black Duck Software, Inc. All rights reserved.
 * Black Duck® is a trademark of Black Duck Software, Inc. in the United States and other countries.
 */
package com.blackduck.integration.jenkins.polaris;

import com.blackduck.integration.exception.IntegrationException;
import com.blackduck.integration.jenkins.polaris.service.GetPolarisCliResponseContent;
import com.blackduck.integration.jenkins.polaris.service.PolarisCliIssueCountService;
import com.blackduck.integration.jenkins.polaris.service.PolarisCliVersionHandler;
import com.blackduck.integration.jenkins.service.JenkinsRemotingService;
import com.blackduck.integration.jenkins.service.JenkinsRunService;
import com.blackduck.integration.jenkins.wrapper.JenkinsVersionHelper;
import com.blackduck.integration.log.IntLogger;
import com.blackduck.integration.polaris.common.service.JobService;
import java.io.IOException;
import java.util.Optional;

public class PolarisIssueChecker {
    private final IntLogger logger;
    private final PolarisCliIssueCountService polarisCliIssueCountService;
    private final JenkinsRemotingService jenkinsRemotingService;
    private final JenkinsRunService jenkinsRunService;
    private final JenkinsVersionHelper versionHelper;

    public PolarisIssueChecker(
            IntLogger logger,
            PolarisCliIssueCountService polarisCliIssueCountService,
            JenkinsRemotingService jenkinsRemotingService,
            JenkinsRunService jenkinsRunService,
            JenkinsVersionHelper versionHelper) {
        this.logger = logger;
        this.polarisCliIssueCountService = polarisCliIssueCountService;
        this.jenkinsRemotingService = jenkinsRemotingService;
        this.jenkinsRunService = jenkinsRunService;
        this.versionHelper = versionHelper;
    }

    public int getPolarisIssueCount(Integer jobTimeoutInMinutes)
            throws IOException, InterruptedException, IntegrationException {
        String logMessage = versionHelper
                .getPluginVersion("blackduck-coverity-on-polaris")
                .map(version -> String.format("Running Coverity on Polaris Platform for Jenkins version %s", version))
                .orElse("Running Coverity on Polaris Platform for Jenkins");
        logger.info(logMessage);

        Long jobTimeoutInSeconds = Optional.ofNullable(jobTimeoutInMinutes)
                .map(value -> value * 60L)
                .orElse(JobService.DEFAULT_TIMEOUT);

        PolarisCliVersionHandler polarisCliVersionHandler = new PolarisCliVersionHandler();

        String polarisCliVersion = polarisCliVersionHandler.extractPolarisCliVersion(
                jenkinsRunService.getRun().getLogInputStream());

        if (polarisCliVersion != null) {
            logger.info("Coverity on Polaris CLI version: " + polarisCliVersion);
        } else {
            logger.warn("Coverity on Polaris CLI version cannot be extracted");
        }

        String cliCommonResponseModelJson = jenkinsRemotingService.call(
                new GetPolarisCliResponseContent(jenkinsRemotingService.getRemoteWorkspacePath(), polarisCliVersion));

        return polarisCliIssueCountService.getIssueCount(jobTimeoutInSeconds, cliCommonResponseModelJson);
    }
}
