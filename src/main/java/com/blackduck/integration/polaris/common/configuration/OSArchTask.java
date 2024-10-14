/*
 * blackduck-coverity-on-polaris
 *
 * Copyright ©2024 Black Duck Software, Inc. All rights reserved. 
 * Black Duck® is a trademark of Black Duck Software, Inc. in the United States and other countries.
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
