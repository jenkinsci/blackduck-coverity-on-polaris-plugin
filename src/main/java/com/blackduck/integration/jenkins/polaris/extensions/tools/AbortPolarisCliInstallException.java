/*
 * blackduck-coverity-on-polaris
 *
 * Copyright ©2024 Black Duck Software, Inc. All rights reserved. 
 * Black Duck® is a trademark of Black Duck Software, Inc. in the United States and other countries.
 */
package com.blackduck.integration.jenkins.polaris.extensions.tools;

import hudson.AbortException;
import hudson.tools.ToolInstallation;

public class AbortPolarisCliInstallException extends AbortException {
    public AbortPolarisCliInstallException(ToolInstallation toolInstallation, String reason) {
        super("Cannot install Coverity on Polaris CLI Installation " + toolInstallation.getName() + " because: "
                + reason);
    }
}
