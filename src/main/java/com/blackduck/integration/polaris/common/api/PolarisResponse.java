/*
 * blackduck-coverity-on-polaris
 *
 * Copyright ©2024 Black Duck Software, Inc. All rights reserved.
 * Black Duck® is a trademark of Black Duck Software, Inc. in the United States and other countries.
 */
package com.blackduck.integration.polaris.common.api;

import com.synopsys.integration.util.Stringable;
import java.io.Serializable;

public class PolarisResponse extends Stringable implements Serializable {
    private static final long serialVersionUID = 1968298547235080384L;
    private String json;

    public PolarisResponse() {
        this.json = null;
    }

    public PolarisResponse(String json) {
        this.json = json;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
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
