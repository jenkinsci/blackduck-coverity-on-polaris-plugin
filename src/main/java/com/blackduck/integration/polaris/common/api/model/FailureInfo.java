/*
 * blackduck-coverity-on-polaris
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.polaris.common.api.model;

import com.google.gson.annotations.SerializedName;
import com.synopsys.integration.util.Stringable;
import java.io.Serializable;

public class FailureInfo extends Stringable implements Serializable {
    private static final long serialVersionUID = 9118125719091019152L;

    @SerializedName("userFriendlyFailureReason")
    private String userFriendlyFailureReason;

    @SerializedName("exception")
    private String exception;

    public String getUserFriendlyFailureReason() {
        return userFriendlyFailureReason;
    }

    public String getException() {
        return exception;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
