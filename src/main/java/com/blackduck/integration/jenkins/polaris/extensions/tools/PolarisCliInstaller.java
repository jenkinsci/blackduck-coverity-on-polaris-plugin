/*
 * blackduck-coverity-on-polaris
 *
 * Copyright ©2024 Black Duck Software, Inc. All rights reserved.
 * Black Duck® is a trademark of Black Duck Software, Inc. in the United States and other countries.
 */
package com.blackduck.integration.jenkins.polaris.extensions.tools;

import com.blackduck.integration.exception.IntegrationException;
import com.blackduck.integration.jenkins.extensions.JenkinsIntLogger;
import com.blackduck.integration.jenkins.polaris.extensions.global.PolarisGlobalConfig;
import com.blackduck.integration.jenkins.service.JenkinsConfigService;
import com.blackduck.integration.jenkins.wrapper.JenkinsWrapper;
import com.blackduck.integration.polaris.common.rest.AccessTokenPolarisHttpClient;
import hudson.EnvVars;
import hudson.Extension;
import hudson.FilePath;
import hudson.model.Node;
import hudson.model.TaskListener;
import hudson.remoting.VirtualChannel;
import hudson.tools.ToolInstallation;
import hudson.tools.ToolInstaller;
import hudson.tools.ToolInstallerDescriptor;
import java.io.IOException;
import java.util.Optional;
import org.kohsuke.stapler.DataBoundConstructor;

public class PolarisCliInstaller extends ToolInstaller {
    @DataBoundConstructor
    public PolarisCliInstaller(String label) {
        super(label);
    }

    @Override
    public FilePath performInstallation(ToolInstallation tool, Node node, TaskListener log)
            throws IOException, InterruptedException {
        JenkinsIntLogger jenkinsIntLogger = JenkinsIntLogger.logToListener(log);

        VirtualChannel virtualChannel = node.getChannel();

        if (virtualChannel == null) {
            throw new AbortPolarisCliInstallException(
                    tool, "Node " + node.getDisplayName() + " is not connected or offline.");
        }

        JenkinsConfigService jenkinsConfigService =
                new JenkinsConfigService(EnvVars.getRemote(virtualChannel), node, log);
        Optional<PolarisGlobalConfig> possiblePolarisGlobalConfig =
                jenkinsConfigService.getGlobalConfiguration(PolarisGlobalConfig.class);

        if (!possiblePolarisGlobalConfig.isPresent()) {
            throw new AbortPolarisCliInstallException(
                    tool,
                    "No Coverity on Polaris Platform global configuration was found. Please check your system config.");
        }

        PolarisGlobalConfig polarisGlobalConfig = possiblePolarisGlobalConfig.get();

        JenkinsWrapper jenkinsWrapper = JenkinsWrapper.initializeFromJenkinsJVM();

        if (!jenkinsWrapper.getJenkins().isPresent()) {
            throw new AbortPolarisCliInstallException(
                    tool,
                    "The Jenkins instance was not started, was already shut down, or is not reachable from this JVM.");
        }

        FilePath installLocation = preferredLocation(tool, node);
        installLocation.mkdirs();

        AccessTokenPolarisHttpClient polarisHttpClient = polarisGlobalConfig
                .getPolarisServerConfig(jenkinsWrapper.getCredentialsHelper(), jenkinsWrapper.getProxyHelper())
                .createPolarisHttpClient(jenkinsIntLogger);
        FindOrInstallPolarisCli findOrInstallPolarisCli = FindOrInstallPolarisCli.getConnectionDetailsFromHttpClient(
                jenkinsIntLogger, polarisHttpClient, installLocation.getRemote());

        try {
            String polarisCliRemotePath = virtualChannel.call(findOrInstallPolarisCli);
            return new FilePath(virtualChannel, polarisCliRemotePath);
        } catch (IntegrationException ex) {
            throw new IOException("Coverity on Polaris CLI was not correctly installed.", ex);
        }
    }

    @Extension
    public static final class DescriptorImpl extends ToolInstallerDescriptor<PolarisCliInstaller> {
        @Override
        public String getDisplayName() {
            return "Install from Coverity on Polaris";
        }

        @Override
        public boolean isApplicable(Class<? extends ToolInstallation> toolType) {
            return toolType == PolarisCli.class;
        }
    }
}
