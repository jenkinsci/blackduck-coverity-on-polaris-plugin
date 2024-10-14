/*
 * blackduck-coverity-on-polaris
 *
 * Copyright ©2024 Black Duck Software, Inc. All rights reserved. 
 * Black Duck® is a trademark of Black Duck Software, Inc. in the United States and other countries.
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
