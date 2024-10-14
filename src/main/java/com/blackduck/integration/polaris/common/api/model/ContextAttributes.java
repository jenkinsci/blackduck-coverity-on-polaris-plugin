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
