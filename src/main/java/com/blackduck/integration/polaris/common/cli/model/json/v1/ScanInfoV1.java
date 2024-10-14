/*
 * blackduck-coverity-on-polaris
 *
 * Copyright ©2024 Black Duck Software, Inc. All rights reserved.
 * Black Duck® is a trademark of Black Duck Software, Inc. in the United States and other countries.
 */
package com.blackduck.integration.polaris.common.cli.model.json.v1;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

public class ScanInfoV1 {
    @SuppressFBWarnings(value = "UWF_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD")
    public String cliVersion;

    @SuppressFBWarnings(value = "UWF_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD")
    public String scanTime;

    @SuppressFBWarnings(value = "UWF_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD")
    public String issueApiUrl;
}
