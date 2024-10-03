/*
 * blackduck-coverity-on-polaris
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.polaris.common.configuration;

import hudson.FilePath;
import hudson.remoting.VirtualChannel;
import java.io.File;
import jenkins.MasterToSlaveFileCallable;

public class OSArchTask extends MasterToSlaveFileCallable<String> implements FilePath.FileCallable<String> {
    @Override
    public String invoke(File workspace, VirtualChannel channel) {
        return System.getProperty("os.arch").toLowerCase();
    }
}
