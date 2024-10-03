/*
 * blackduck-coverity-on-polaris
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.polaris.common.cli.model.json.v1;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Map;

public class IssueSummaryV1 {
    @SuppressFBWarnings(value = "UWF_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD")
    public Map<String, Integer> issuesBySeverity;

    @SuppressFBWarnings(value = "UWF_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD")
    public String summaryUrl;

    public int total;
}
