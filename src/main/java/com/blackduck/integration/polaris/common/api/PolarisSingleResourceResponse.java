/*
 * blackduck-coverity-on-polaris
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.polaris.common.api;

import com.google.gson.annotations.SerializedName;

public class PolarisSingleResourceResponse<R extends PolarisResource> extends PolarisResponse {
    @SerializedName("data")
    private R data = null;

    /**
     * Get data
     * @return data
     */
    public R getData() {
        return data;
    }

    public void setData(R data) {
        this.data = data;
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
