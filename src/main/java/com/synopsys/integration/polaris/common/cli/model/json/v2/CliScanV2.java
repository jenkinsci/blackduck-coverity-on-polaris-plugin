/*
 * synopsys-polaris
 *
 * Copyright (c) 2024 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.polaris.common.cli.model.json.v2;

import com.synopsys.integration.polaris.common.cli.model.json.CliScanResponse;
import com.synopsys.integration.polaris.common.cli.model.json.v1.IssueSummaryV1;
import com.synopsys.integration.polaris.common.cli.model.json.v1.ProjectInfoV1;
import com.synopsys.integration.polaris.common.cli.model.json.v1.ScanInfoV1;
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
