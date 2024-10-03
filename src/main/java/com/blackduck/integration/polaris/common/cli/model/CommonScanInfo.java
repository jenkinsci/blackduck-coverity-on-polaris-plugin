/*
 * blackduck-coverity-on-polaris
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.polaris.common.cli.model;

import com.synopsys.integration.rest.HttpUrl;

public class CommonScanInfo {
    private String cliVersion;
    private String scanTime;
    private HttpUrl issueApiUrl;

    public String getCliVersion() {
        return cliVersion;
    }

    public void setCliVersion(String cliVersion) {
        this.cliVersion = cliVersion;
    }

    public String getScanTime() {
        return scanTime;
    }

    public void setScanTime(String scanTime) {
        this.scanTime = scanTime;
    }

    public HttpUrl getIssueApiUrl() {
        return issueApiUrl;
    }

    public void setIssueApiUrl(HttpUrl issueApiUrl) {
        this.issueApiUrl = issueApiUrl;
    }
}
