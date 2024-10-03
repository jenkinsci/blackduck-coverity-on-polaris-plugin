/*
 * blackduck-coverity-on-polaris
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck End User Software License and Maintenance Agreement. All rights reserved worldwide.
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

    public GetPolarisCliResponseContent(String workspaceRemotePath) {
        this.workspaceRemotePath = workspaceRemotePath;
    }

    @Override
    public String call() throws PolarisIntegrationException {
        try {
            byte[] bytes = Files.readAllBytes(PolarisCliResponseUtility.getDefaultPathToJson(workspaceRemotePath));
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new PolarisIntegrationException("There was an error getting the Coverity on Polaris CLI response.", e);
        }
    }
}
