/*
 * blackduck-coverity-on-polaris
 *
 * Copyright ©2024 Black Duck Software, Inc. All rights reserved.
 * Black Duck® is a trademark of Black Duck Software, Inc. in the United States and other countries.
 */
package com.blackduck.integration.polaris.common.cli.model;

import com.blackduck.integration.rest.HttpUrl;

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
