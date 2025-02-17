/*
 * blackduck-coverity-on-polaris
 *
 * Copyright ©2024 Black Duck Software, Inc. All rights reserved.
 * Black Duck® is a trademark of Black Duck Software, Inc. in the United States and other countries.
 */
package com.blackduck.integration.jenkins.polaris.service;

import com.blackduck.integration.function.ThrowingSupplier;
import com.blackduck.integration.jenkins.extensions.JenkinsIntLogger;
import com.blackduck.integration.jenkins.polaris.ChangeSetFileCreator;
import com.blackduck.integration.jenkins.polaris.PolarisCliRunner;
import com.blackduck.integration.jenkins.polaris.PolarisFreestyleCommands;
import com.blackduck.integration.jenkins.polaris.PolarisIssueChecker;
import com.blackduck.integration.jenkins.polaris.PolarisPipelineCommands;
import com.blackduck.integration.jenkins.polaris.extensions.global.PolarisGlobalConfig;
import com.blackduck.integration.jenkins.service.JenkinsBuildService;
import com.blackduck.integration.jenkins.service.JenkinsConfigService;
import com.blackduck.integration.jenkins.service.JenkinsFreestyleServicesFactory;
import com.blackduck.integration.jenkins.service.JenkinsRemotingService;
import com.blackduck.integration.jenkins.service.JenkinsRunService;
import com.blackduck.integration.jenkins.service.JenkinsServicesFactory;
import com.blackduck.integration.jenkins.wrapper.JenkinsWrapper;
import com.blackduck.integration.polaris.common.cli.PolarisCliResponseUtility;
import com.blackduck.integration.polaris.common.configuration.PolarisServerConfig;
import com.blackduck.integration.polaris.common.service.ContextsService;
import com.blackduck.integration.polaris.common.service.CountService;
import com.blackduck.integration.polaris.common.service.JobService;
import com.blackduck.integration.polaris.common.service.PolarisServicesFactory;
import hudson.AbortException;
import hudson.EnvVars;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.model.Node;
import hudson.model.Run;
import hudson.model.TaskListener;
import java.io.IOException;
import java.util.function.Supplier;

public class PolarisCommandsFactory {
    private final EnvVars envVars;
    private final TaskListener listener;
    private final ThrowingSupplier<JenkinsWrapper, AbortException> validatedJenkinsWrapper;
    // These fields are lazily initialized; within this class use the suppliers instead of referencing the fields
    // directly
    private JenkinsIntLogger _logger = null;
    private final Supplier<JenkinsIntLogger> initializedLogger = this::getOrCreateLogger;

    private PolarisCommandsFactory(JenkinsWrapper jenkinsWrapper, EnvVars envVars, TaskListener listener) {
        this.validatedJenkinsWrapper = () -> validateJenkinsWrapper(jenkinsWrapper);
        this.envVars = envVars;
        this.listener = listener;
    }

    public static PolarisFreestyleCommands fromPostBuild(
            AbstractBuild<?, ?> build, Launcher launcher, BuildListener listener)
            throws IOException, InterruptedException {
        PolarisCommandsFactory polarisCommandsFactory = new PolarisCommandsFactory(
                JenkinsWrapper.initializeFromJenkinsJVM(), build.getEnvironment(listener), listener);
        JenkinsFreestyleServicesFactory jenkinsServicesFactory = new JenkinsFreestyleServicesFactory(
                polarisCommandsFactory.getOrCreateLogger(),
                build,
                build.getEnvironment(listener),
                launcher,
                listener,
                build.getBuiltOn(),
                build.getWorkspace());

        JenkinsRemotingService jenkinsRemotingService = jenkinsServicesFactory.createJenkinsRemotingService();
        JenkinsConfigService jenkinsConfigService = jenkinsServicesFactory.createJenkinsConfigService();
        JenkinsBuildService jenkinsBuildService = jenkinsServicesFactory.createJenkinsBuildService();
        JenkinsRunService jenkinsRunService = jenkinsServicesFactory.createJenkinsRunService();

        ChangeSetFileCreator changeSetFileCreator =
                polarisCommandsFactory.createChangeSetFileCreator(jenkinsRemotingService, jenkinsRunService);
        PolarisCliRunner polarisCliRunner =
                polarisCommandsFactory.createPolarisCliRunner(jenkinsConfigService, jenkinsRemotingService);
        PolarisIssueChecker polarisIssueCounter = polarisCommandsFactory.createPolarisIssueCounter(
                jenkinsConfigService, jenkinsRemotingService, jenkinsRunService);

        return new PolarisFreestyleCommands(
                polarisCommandsFactory.getOrCreateLogger(),
                jenkinsBuildService,
                changeSetFileCreator,
                polarisCliRunner,
                polarisIssueCounter);
    }

    public static PolarisPipelineCommands fromPipeline(
            TaskListener listener, EnvVars envVars, Launcher launcher, Node node, Run<?, ?> run, FilePath workspace)
            throws AbortException {
        PolarisCommandsFactory polarisCommandsFactory =
                new PolarisCommandsFactory(JenkinsWrapper.initializeFromJenkinsJVM(), envVars, listener);
        JenkinsServicesFactory jenkinsServicesFactory = new JenkinsServicesFactory(
                polarisCommandsFactory.getOrCreateLogger(), envVars, launcher, listener, node, run, workspace);

        JenkinsRemotingService jenkinsRemotingService = jenkinsServicesFactory.createJenkinsRemotingService();
        JenkinsConfigService jenkinsConfigService = jenkinsServicesFactory.createJenkinsConfigService();
        JenkinsRunService jenkinsRunService = jenkinsServicesFactory.createJenkinsRunService();

        ChangeSetFileCreator changeSetFileCreator =
                polarisCommandsFactory.createChangeSetFileCreator(jenkinsRemotingService, jenkinsRunService);
        PolarisCliRunner polarisCliRunner =
                polarisCommandsFactory.createPolarisCliRunner(jenkinsConfigService, jenkinsRemotingService);
        PolarisIssueChecker polarisIssueCounter = polarisCommandsFactory.createPolarisIssueCounter(
                jenkinsConfigService, jenkinsRemotingService, jenkinsRunService);

        return new PolarisPipelineCommands(
                polarisCommandsFactory.getOrCreateLogger(),
                changeSetFileCreator,
                polarisCliRunner,
                polarisIssueCounter);
    }

    public PolarisIssueChecker createPolarisIssueCounter(
            JenkinsConfigService jenkinsConfigService,
            JenkinsRemotingService jenkinsRemotingService,
            JenkinsRunService jenkinsRunService)
            throws AbortException {
        return new PolarisIssueChecker(
                initializedLogger.get(),
                createPolarisCliIssueCountService(jenkinsConfigService),
                jenkinsRemotingService,
                jenkinsRunService,
                validatedJenkinsWrapper.get().getVersionHelper());
    }

    public PolarisCliRunner createPolarisCliRunner(
            JenkinsConfigService jenkinsConfigService, JenkinsRemotingService jenkinsRemotingService)
            throws AbortException {
        JenkinsWrapper jenkinsWrapper = validatedJenkinsWrapper.get();
        return new PolarisCliRunner(
                initializedLogger.get(),
                createPolarisCliArgumentService(),
                createPolarisEnvironmentService(),
                createPolarisPhoneHomeService(jenkinsConfigService),
                jenkinsRemotingService,
                jenkinsConfigService,
                jenkinsWrapper.getCredentialsHelper(),
                jenkinsWrapper.getProxyHelper(),
                jenkinsWrapper.getVersionHelper());
    }

    public ChangeSetFileCreator createChangeSetFileCreator(
            JenkinsRemotingService jenkinsRemotingService, JenkinsRunService jenkinsRunService) {
        return new ChangeSetFileCreator(
                initializedLogger.get(), jenkinsRemotingService, jenkinsRunService, createPolarisEnvironmentService());
    }

    private PolarisEnvironmentService createPolarisEnvironmentService() {
        return new PolarisEnvironmentService(envVars);
    }

    private PolarisCliArgumentService createPolarisCliArgumentService() {
        return new PolarisCliArgumentService(initializedLogger.get());
    }

    private PolarisCliIssueCountService createPolarisCliIssueCountService(JenkinsConfigService jenkinsConfigService)
            throws AbortException {
        PolarisServicesFactory polarisServicesFactory = createPolarisServicesFactory(jenkinsConfigService);
        JobService jobService = polarisServicesFactory.createJobService();
        CountService countService = polarisServicesFactory.createCountService();
        PolarisCliResponseUtility polarisCliResponseUtility =
                PolarisCliResponseUtility.defaultUtility(initializedLogger.get());

        return new PolarisCliIssueCountService(
                initializedLogger.get(), countService, jobService, polarisCliResponseUtility);
    }

    private PolarisPhoneHomeService createPolarisPhoneHomeService(JenkinsConfigService jenkinsConfigService)
            throws AbortException {
        PolarisServicesFactory polarisServicesFactory = createPolarisServicesFactory(jenkinsConfigService);
        ContextsService contextsService = polarisServicesFactory.createContextsService();
        JenkinsWrapper jenkinsWrapper = validatedJenkinsWrapper.get();

        return new PolarisPhoneHomeService(
                initializedLogger.get(),
                jenkinsWrapper.getVersionHelper(),
                contextsService,
                polarisServicesFactory.getHttpClient());
    }

    private JenkinsIntLogger getOrCreateLogger() {
        if (_logger == null) {
            _logger = JenkinsIntLogger.logToListener(listener);
        }
        return _logger;
    }

    private PolarisServicesFactory createPolarisServicesFactory(JenkinsConfigService jenkinsConfigService)
            throws AbortException {
        PolarisGlobalConfig polarisGlobalConfig = jenkinsConfigService
                .getGlobalConfiguration(PolarisGlobalConfig.class)
                .orElseThrow(
                        () -> new AbortException(
                                "Coverity on Polaris Platform for Jenkins cannot be executed: No Coverity on Polaris Platform global configuration detected in the Jenkins system configuration."));

        JenkinsIntLogger jenkinsIntLogger = getOrCreateLogger();
        JenkinsWrapper jenkinsWrapper = validatedJenkinsWrapper.get();
        PolarisServerConfig polarisServerConfig = polarisGlobalConfig.getPolarisServerConfig(
                jenkinsWrapper.getCredentialsHelper(), jenkinsWrapper.getProxyHelper());
        return polarisServerConfig.createPolarisServicesFactory(jenkinsIntLogger);
    }

    private JenkinsWrapper validateJenkinsWrapper(JenkinsWrapper jenkinsWrapper) throws AbortException {
        if (jenkinsWrapper.getJenkins().isPresent()) {
            return jenkinsWrapper;
        }

        throw new AbortException(
                "Coverity on Polaris Platform for Jenkins cannot be executed: The Jenkins instance was not started, was already shut down, or is not reachable from this JVM.");
    }
}
