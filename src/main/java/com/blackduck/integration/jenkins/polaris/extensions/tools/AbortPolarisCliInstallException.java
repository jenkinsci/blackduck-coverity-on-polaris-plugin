/*
 * blackduck-coverity-on-polaris
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jenkins.polaris.extensions.tools;

import hudson.AbortException;
import hudson.tools.ToolInstallation;

public class AbortPolarisCliInstallException extends AbortException {
    public AbortPolarisCliInstallException(ToolInstallation toolInstallation, String reason) {
        super("Cannot install Coverity on Polaris CLI Installation " + toolInstallation.getName() + " because: " + reason);
    }
}
