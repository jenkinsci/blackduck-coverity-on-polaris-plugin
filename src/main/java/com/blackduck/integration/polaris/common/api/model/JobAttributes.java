/*
 * blackduck-coverity-on-polaris
 *
 * Copyright ©2024 Black Duck Software, Inc. All rights reserved.
 * Black Duck® is a trademark of Black Duck Software, Inc. in the United States and other countries.
 */
package com.blackduck.integration.polaris.common.api.model;

import com.blackduck.integration.polaris.common.api.PolarisAttributes;
import com.blackduck.integration.polaris.common.api.PolarisResponse;
import com.google.gson.annotations.SerializedName;

public class JobAttributes extends PolarisResponse implements PolarisAttributes {
    private static final long serialVersionUID = 2618385287180907810L;

    @SerializedName("failureInfo")
    private FailureInfo failureInfo = null;

    @SerializedName("status")
    private JobStatus status = null;

    public FailureInfo getFailureInfo() {
        return failureInfo;
    }

    public void setFailureInfo(FailureInfo failureInfo) {
        this.failureInfo = failureInfo;
    }

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
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
