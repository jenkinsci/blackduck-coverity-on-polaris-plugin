/*
 * blackduck-coverity-on-polaris
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck End User Software License and Maintenance Agreement. All rights reserved worldwide.
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