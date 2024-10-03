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

// this file should not be edited - if changes are necessary, the generator should be updated, then this file should be
// re-created

public class ContextAttributes extends PolarisResponse implements PolarisAttributes {
    @SerializedName("current")
    private Boolean current;

    @SerializedName("organizationname")
    private String organizationname;

    public Boolean getCurrent() {
        return current;
    }

    public void setCurrent(Boolean current) {
        this.current = current;
    }

    public String getOrganizationname() {
        return organizationname;
    }

    public void setOrganizationname(String organizationname) {
        this.organizationname = organizationname;
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
