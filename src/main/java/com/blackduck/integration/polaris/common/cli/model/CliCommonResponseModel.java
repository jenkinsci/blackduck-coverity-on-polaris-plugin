/*
 * blackduck-coverity-on-polaris
 *
 * Copyright ©2024 Black Duck Software, Inc. All rights reserved. 
 * Black Duck® is a trademark of Black Duck Software, Inc. in the United States and other countries.
 */
package com.blackduck.integration.polaris.common.cli.model;

import java.util.List;
import java.util.Optional;

public class CliCommonResponseModel {
    private CommonScanInfo scanInfo;
    private CommonProjectInfo projectInfo;
    private CommonIssueSummary issueSummary;
    private List<CommonToolInfo> tools;

    public CommonScanInfo getScanInfo() {
        return scanInfo;
    }

    public void setScanInfo(CommonScanInfo scanInfo) {
        this.scanInfo = scanInfo;
    }

    public CommonProjectInfo getProjectInfo() {
        return projectInfo;
    }

    public void setProjectInfo(CommonProjectInfo projectInfo) {
        this.projectInfo = projectInfo;
    }

    public Optional<CommonIssueSummary> getIssueSummary() {
        return Optional.ofNullable(issueSummary);
    }

    public void setIssueSummary(CommonIssueSummary issueSummary) {
        this.issueSummary = issueSummary;
    }

    public List<CommonToolInfo> getTools() {
        return tools;
    }

    public void setTools(List<CommonToolInfo> tools) {
        this.tools = tools;
    }
}
