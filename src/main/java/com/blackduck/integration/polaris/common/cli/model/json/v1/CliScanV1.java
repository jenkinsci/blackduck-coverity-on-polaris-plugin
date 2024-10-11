/*
 * blackduck-coverity-on-polaris
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.polaris.common.cli.model.json.v1;

import com.blackduck.integration.polaris.common.cli.model.json.CliScanResponse;
import com.google.gson.annotations.SerializedName;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

public class CliScanV1 implements CliScanResponse {
    @SuppressFBWarnings(value = "UUF_UNUSED_PUBLIC_OR_PROTECTED_FIELD")
    public String version;

    @SuppressFBWarnings(value = "UWF_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD")
    public ScanInfoV1 scanInfo;

    @SuppressFBWarnings(value = "UWF_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD")
    public ProjectInfoV1 projectInfo;

    @SuppressFBWarnings(value = "UWF_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD")
    public IssueSummaryV1 issueSummary;

    @SerializedName("coverity")
    public ToolInfoV1 coverityToolInfo;

    @SerializedName("sca")
    public ToolInfoV1 blackDuckScaToolInfo;
}