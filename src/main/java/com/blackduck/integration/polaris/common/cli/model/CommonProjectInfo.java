/*
 * blackduck-coverity-on-polaris
 *
 * Copyright ©2024 Black Duck Software, Inc. All rights reserved. 
 * Black Duck® is a trademark of Black Duck Software, Inc. in the United States and other countries.
 */
package com.blackduck.integration.polaris.common.cli.model;

public class CommonProjectInfo {
    private String projectId;
    private String branchId;
    private String revisionId;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(final String projectId) {
        this.projectId = projectId;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(final String branchId) {
        this.branchId = branchId;
    }

    public String getRevisionId() {
        return revisionId;
    }

    public void setRevisionId(final String revisionId) {
        this.revisionId = revisionId;
    }
}
