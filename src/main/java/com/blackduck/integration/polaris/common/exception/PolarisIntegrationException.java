/*
 * blackduck-coverity-on-polaris
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck End User Software License and Maintenance Agreement. All rights reserved worldwide.
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