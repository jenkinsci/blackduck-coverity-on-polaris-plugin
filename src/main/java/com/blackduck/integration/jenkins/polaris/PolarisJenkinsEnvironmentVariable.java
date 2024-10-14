/*
 * blackduck-coverity-on-polaris
 *
 * Copyright ©2024 Black Duck Software, Inc. All rights reserved. 
 * Black Duck® is a trademark of Black Duck Software, Inc. in the United States and other countries.
 */
package com.blackduck.integration.jenkins.polaris;

public enum PolarisJenkinsEnvironmentVariable {
    CHANGE_SET_FILE_PATH("CHANGE_SET_FILE_PATH");

    private final String environmentVariable;

    PolarisJenkinsEnvironmentVariable(String environmentVariable) {
        this.environmentVariable = environmentVariable;
    }

    public String stringValue() {
        return environmentVariable;
    }
}
