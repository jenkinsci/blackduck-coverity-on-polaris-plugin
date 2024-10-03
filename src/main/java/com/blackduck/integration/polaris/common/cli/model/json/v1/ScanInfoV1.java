/*
 * blackduck-coverity-on-polaris
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck End User Software License and Maintenance Agreement. All rights reserved worldwide.
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
