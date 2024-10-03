/*
 * blackduck-coverity-on-polaris
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck End User Software License and Maintenance Agreement. All rights reserved worldwide.
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
