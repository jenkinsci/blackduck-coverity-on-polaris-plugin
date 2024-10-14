/*
 * blackduck-coverity-on-polaris
 *
 * Copyright ©2024 Black Duck Software, Inc. All rights reserved.
 * Black Duck® is a trademark of Black Duck Software, Inc. in the United States and other countries.
 */
package com.blackduck.integration.polaris.common.api;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class PolarisPagedResourceResponse<R extends PolarisResource> extends PolarisResponse {
    @SerializedName("data")
    private List<R> data = null;

    @SerializedName("meta")
    private PolarisPaginationMeta meta = null;

    public List<R> getData() {
        return data;
    }

    public void setData(List<R> data) {
        this.data = data;
    }

    public PolarisPaginationMeta getMeta() {
        return meta;
    }

    public void setMeta(PolarisPaginationMeta meta) {
        this.meta = meta;
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
