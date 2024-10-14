/*
 * blackduck-coverity-on-polaris
 *
 * Copyright ©2024 Black Duck Software, Inc. All rights reserved.
 * Black Duck® is a trademark of Black Duck Software, Inc. in the United States and other countries.
 */
package com.blackduck.integration.polaris.common.cli.model.json.v2;

import com.blackduck.integration.polaris.common.cli.model.json.CliScanResponse;
import com.blackduck.integration.polaris.common.cli.model.json.v1.IssueSummaryV1;
import com.blackduck.integration.polaris.common.cli.model.json.v1.ProjectInfoV1;
import com.blackduck.integration.polaris.common.cli.model.json.v1.ScanInfoV1;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.List;

public class CliScanV2 implements CliScanResponse {
    @SuppressFBWarnings(value = "UUF_UNUSED_PUBLIC_OR_PROTECTED_FIELD")
    public String version;

    @SuppressFBWarnings(value = "UWF_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD")
    public ScanInfoV1 scanInfo;

    @SuppressFBWarnings(value = "UWF_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD")
    public ProjectInfoV1 projectInfo;

    @SuppressFBWarnings(value = "UWF_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD")
    public IssueSummaryV1 issueSummary;

    @SuppressFBWarnings(value = "UWF_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD")
    public List<ToolInfoV2> tools;
}
