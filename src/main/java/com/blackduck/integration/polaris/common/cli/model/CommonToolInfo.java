/*
 * blackduck-coverity-on-polaris
 *
 * Copyright ©2024 Black Duck Software, Inc. All rights reserved.
 * Black Duck® is a trademark of Black Duck Software, Inc. in the United States and other countries.
 */
package com.blackduck.integration.polaris.common.cli.model;

import com.synopsys.integration.rest.HttpUrl;
import java.util.Optional;

public class CommonToolInfo {
    private String toolName;
    private String toolVersion;
    private String jobId;
    private HttpUrl jobStatusUrl;
    private String jobStatus;
    private HttpUrl issueApiUrl;

    public String getToolName() {
        return toolName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    public String getToolVersion() {
        return toolVersion;
    }

    public void setToolVersion(String toolVersion) {
        this.toolVersion = toolVersion;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public HttpUrl getJobStatusUrl() {
        return jobStatusUrl;
    }

    public void setJobStatusUrl(HttpUrl jobStatusUrl) {
        this.jobStatusUrl = jobStatusUrl;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public Optional<HttpUrl> getIssueApiUrl() {
        return Optional.ofNullable(issueApiUrl);
    }

    public void setIssueApiUrl(HttpUrl issueApiUrl) {
        this.issueApiUrl = issueApiUrl;
    }
}
