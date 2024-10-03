/*
 * blackduck-coverity-on-polaris
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jenkins.polaris.service;

import com.synopsys.integration.jenkins.exception.JenkinsUserFriendlyException;
import com.synopsys.integration.util.OperatingSystemType;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import jenkins.security.MasterToSlaveCallable;

public class GetPathToPolarisCli extends MasterToSlaveCallable<String, JenkinsUserFriendlyException> {
    private static final long serialVersionUID = -8823365241230615671L;
    private final String polarisCliHome;

    public GetPathToPolarisCli(String polarisCliHome) {
        this.polarisCliHome = polarisCliHome;
    }

    @Override
    public String call() throws JenkinsUserFriendlyException {
        Path homePath = Paths.get(polarisCliHome);
        Path binPath = homePath.resolve("bin");

        OperatingSystemType operatingSystemType = OperatingSystemType.determineFromSystem();

        Optional<String> polarisCli = checkFile(operatingSystemType, binPath, "polaris");
        Optional<String> swipCli = checkFile(operatingSystemType, binPath, "swip_cli");

        if (polarisCli.isPresent()) {
            return polarisCli.get();
        } else if (swipCli.isPresent()) {
            return swipCli.get();
        }

        throw new JenkinsUserFriendlyException("The Coverity on Polaris CLI could not be found in " + binPath.toString()
                + " on this node. Please verify the cli exists there and is executable.");
    }

    private Optional<String> checkFile(OperatingSystemType operatingSystemType, Path binPath, String filePrefix) {
        String binaryName = filePrefix;
        if (OperatingSystemType.WINDOWS == operatingSystemType) {
            binaryName += ".exe";
        }
        Path binaryPath = binPath.resolve(binaryName);

        try {
            if (!Files.isDirectory(binaryPath) && Files.size(binaryPath) > 0L) {
                Path realFilePath = binaryPath.toRealPath();
                return Optional.of(realFilePath.toString());
            }
        } catch (IOException e) {
            // Do nothing,
        }
        return Optional.empty();
    }
}
