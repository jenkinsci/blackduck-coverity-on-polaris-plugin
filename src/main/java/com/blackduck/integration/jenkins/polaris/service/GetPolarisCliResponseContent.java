/*
 * blackduck-coverity-on-polaris
 *
 * Copyright ©2024 Black Duck Software, Inc. All rights reserved.
 * Black Duck® is a trademark of Black Duck Software, Inc. in the United States and other countries.
 */
package com.blackduck.integration.jenkins.polaris.service;

import com.blackduck.integration.polaris.common.cli.PolarisCliResponseUtility;
import com.blackduck.integration.polaris.common.exception.PolarisIntegrationException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import jenkins.security.MasterToSlaveCallable;

public class GetPolarisCliResponseContent extends MasterToSlaveCallable<String, PolarisIntegrationException> {
    private static final long serialVersionUID = -5698280934593066898L;
    private final String workspaceRemotePath;
    private final String polarisCliVersion;

    public GetPolarisCliResponseContent(String workspaceRemotePath, String polarisCliVersion) {
        this.workspaceRemotePath = workspaceRemotePath;
        this.polarisCliVersion = polarisCliVersion;
    }

    @Override
    public String call() throws PolarisIntegrationException {
        try {
            byte[] bytes = Files.readAllBytes(
                    PolarisCliResponseUtility.getDefaultPathToJson(workspaceRemotePath, polarisCliVersion));
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new PolarisIntegrationException(
                    "There was an error getting the Coverity on Polaris CLI response.", e);
        }
    }
}
