/*
 * blackduck-coverity-on-polaris
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.polaris.common.api.model;

import com.blackduck.integration.polaris.common.api.PolarisAttributes;
import com.blackduck.integration.polaris.common.api.PolarisResponse;
import com.google.gson.annotations.SerializedName;

public class CountV0Attributes extends PolarisResponse implements PolarisAttributes {
    @SerializedName("value")
    private Integer value;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
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
