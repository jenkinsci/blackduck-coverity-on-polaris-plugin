/*
 * blackduck-coverity-on-polaris
 *
 * Copyright ©2024 Black Duck Software, Inc. All rights reserved.
 * Black Duck® is a trademark of Black Duck Software, Inc. in the United States and other countries.
 */
package com.blackduck.integration.polaris.common.exception;

import com.synopsys.integration.exception.IntegrationException;

public class PolarisIntegrationException extends IntegrationException {
    public PolarisIntegrationException() {}

    public PolarisIntegrationException(
            final String message,
            final Throwable cause,
            final boolean enableSuppression,
            final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public PolarisIntegrationException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PolarisIntegrationException(final String message) {
        super(message);
    }

    public PolarisIntegrationException(final Throwable cause) {
        super(cause);
    }
}
