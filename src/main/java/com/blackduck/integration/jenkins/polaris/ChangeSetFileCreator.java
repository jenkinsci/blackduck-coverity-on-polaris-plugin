/*
 * blackduck-coverity-on-polaris
 *
 * Copyright ©2024 Black Duck Software, Inc. All rights reserved.
 * Black Duck® is a trademark of Black Duck Software, Inc. in the United States and other countries.
 */
package com.blackduck.integration.jenkins.polaris;

import com.blackduck.integration.jenkins.polaris.service.PolarisEnvironmentService;
import com.synopsys.integration.jenkins.ChangeSetFilter;
import com.synopsys.integration.jenkins.extensions.JenkinsIntLogger;
import com.synopsys.integration.jenkins.service.JenkinsRemotingService;
import com.synopsys.integration.jenkins.service.JenkinsScmService;
import com.synopsys.integration.util.IntEnvironmentVariables;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import jenkins.security.MasterToSlaveCallable;
import org.apache.commons.lang3.StringUtils;

public class ChangeSetFileCreator {
    private final JenkinsIntLogger logger;
    private final JenkinsRemotingService jenkinsRemotingService;
    private final JenkinsScmService jenkinsScmService;
    private final PolarisEnvironmentService polarisEnvironmentService;

    public ChangeSetFileCreator(
            JenkinsIntLogger logger,
            JenkinsRemotingService jenkinsRemotingService,
            JenkinsScmService jenkinsScmService,
            PolarisEnvironmentService polarisEnvironmentService) {
        this.logger = logger;
        this.jenkinsRemotingService = jenkinsRemotingService;
        this.jenkinsScmService = jenkinsScmService;
        this.polarisEnvironmentService = polarisEnvironmentService;
    }

    public String createChangeSetFile(String exclusionPatterns, String inclusionPatterns)
            throws IOException, InterruptedException {
        ChangeSetFilter changeSetFilter = jenkinsScmService
                .newChangeSetFilter()
                .excludeMatching(exclusionPatterns)
                .includeMatching(inclusionPatterns);

        // ArrayLists are serializable, Lists are not. -- rotte SEP 2020
        ArrayList<String> changedFiles = new ArrayList<>();
        try {
            changedFiles.addAll(jenkinsScmService.getFilePathsFromChangeSet(changeSetFilter));
        } catch (Exception e) {
            logger.error("Could not get the Jenkins-provided SCM changeset: " + e.getMessage());
        }

        String remoteWorkspacePath = jenkinsRemotingService.getRemoteWorkspacePath();

        String changeSetFilePath;
        if (changedFiles.size() == 0) {
            logger.info(
                    "The changeset file could not be created because the Jenkins-provided SCM changeset contained no files to analyze.");
            changeSetFilePath = null;
        } else {
            IntEnvironmentVariables environment = polarisEnvironmentService.getInitialEnvironment();
            String valueOfChangeSetFilePath =
                    environment.getValue(PolarisJenkinsEnvironmentVariable.CHANGE_SET_FILE_PATH.stringValue());

            changeSetFilePath = jenkinsRemotingService.call(new CreateChangeSetFileAndGetRemotePath(
                    valueOfChangeSetFilePath, remoteWorkspacePath, changedFiles));
        }

        return changeSetFilePath;
    }

    private static class CreateChangeSetFileAndGetRemotePath extends MasterToSlaveCallable<String, IOException> {
        private static final long serialVersionUID = -8708849449533708805L;
        private final ArrayList<String> changedFiles;
        private final String changeSetFilePath;
        private final String valueOfChangeSetFilePath;

        public CreateChangeSetFileAndGetRemotePath(
                String valueOfChangeSetFilePath, String remoteWorkspacePath, ArrayList<String> changedFiles) {
            this.valueOfChangeSetFilePath = valueOfChangeSetFilePath;
            this.changeSetFilePath = remoteWorkspacePath;
            this.changedFiles = changedFiles;
        }

        @Override
        public String call() throws IOException {
            Path changeSetFile;
            if (StringUtils.isNotBlank(valueOfChangeSetFilePath)) {
                changeSetFile = Paths.get(valueOfChangeSetFilePath);
            } else {
                changeSetFile = Paths.get(changeSetFilePath)
                        .resolve(".blackduck")
                        .resolve("polaris")
                        .resolve("changeSetFiles.txt");
            }

            Path parentDir = changeSetFile.getParent();
            if (parentDir != null) {
                Files.createDirectories(parentDir);
            } else {
                throw new IOException("The change set file has no parent directory: " + changeSetFile);
            }
            Files.write(changeSetFile, changedFiles);

            return changeSetFile.toRealPath().toString();
        }
    }
}
