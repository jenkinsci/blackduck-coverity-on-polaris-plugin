/*
 * blackduck-coverity-on-polaris
 *
 * Copyright ©2024 Black Duck Software, Inc. All rights reserved.
 * Black Duck® is a trademark of Black Duck Software, Inc. in the United States and other countries.
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
