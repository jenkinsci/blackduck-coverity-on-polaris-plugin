/*
 * blackduck-coverity-on-polaris
 *
 * Copyright ©2024 Black Duck Software, Inc. All rights reserved.
 * Black Duck® is a trademark of Black Duck Software, Inc. in the United States and other countries.
 */
package com.blackduck.integration.polaris.common.service;

import com.blackduck.integration.polaris.common.api.PolarisResource;
import com.blackduck.integration.polaris.common.api.model.ContextAttributes;
import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.rest.HttpUrl;
import java.util.List;
import java.util.Optional;

public class ContextsService {
    private final PolarisService polarisService;
    private final HttpUrl polarisServerUrl;

    public ContextsService(PolarisService polarisService, HttpUrl polarisServerUrl) {
        this.polarisService = polarisService;
        this.polarisServerUrl = polarisServerUrl;
    }

    public List<PolarisResource<ContextAttributes>> getAllContexts() throws IntegrationException {
        HttpUrl httpUrl = polarisServerUrl.appendRelativeUrl("/api/auth/contexts");
        return polarisService.getAll(httpUrl, ContextAttributes.class);
    }

    public Optional<PolarisResource<ContextAttributes>> getCurrentContext() throws IntegrationException {
        return getAllContexts().stream().filter(this::isCurrentContext).findFirst();
    }

    private Boolean isCurrentContext(PolarisResource<ContextAttributes> context) {
        return Optional.ofNullable(context)
                .map(PolarisResource::getAttributes)
                .map(ContextAttributes::getCurrent)
                .orElse(Boolean.FALSE);
    }
}
