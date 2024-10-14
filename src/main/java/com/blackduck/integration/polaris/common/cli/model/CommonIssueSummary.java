/*
 * blackduck-coverity-on-polaris
 *
 * Copyright ©2024 Black Duck Software, Inc. All rights reserved.
 * Black Duck® is a trademark of Black Duck Software, Inc. in the United States and other countries.
 */
package com.blackduck.integration.polaris.common.cli.model;

import com.synopsys.integration.rest.HttpUrl;
import java.util.Map;

public class CommonIssueSummary {
    private Map<String, Integer> issuesBySeverity;
    private HttpUrl summaryUrl;
    private Integer totalIssueCount;

    public Map<String, Integer> getIssuesBySeverity() {
        return issuesBySeverity;
    }

    public void setIssuesBySeverity(Map<String, Integer> issuesBySeverity) {
        this.issuesBySeverity = issuesBySeverity;
    }

    public HttpUrl getSummaryUrl() {
        return summaryUrl;
    }

    public void setSummaryUrl(HttpUrl summaryUrl) {
        this.summaryUrl = summaryUrl;
    }

    public Integer getTotalIssueCount() {
        return totalIssueCount;
    }

    public void setTotalIssueCount(Integer totalIssueCount) {
        this.totalIssueCount = totalIssueCount;
    }
}
