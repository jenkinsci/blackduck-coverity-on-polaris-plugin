/*
 * blackduck-coverity-on-polaris
 *
 * Copyright ©2024 Black Duck Software, Inc. All rights reserved. 
 * Black Duck® is a trademark of Black Duck Software, Inc. in the United States and other countries.
 */
package com.blackduck.integration.polaris.common.service;

import com.blackduck.integration.polaris.common.api.PolarisResource;
import com.blackduck.integration.polaris.common.api.model.CountV0Attributes;
import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.rest.HttpUrl;
import java.util.List;
import java.util.Objects;

public class CountService {
    private final PolarisService polarisService;

    public CountService(PolarisService polarisService) {
        this.polarisService = polarisService;
    }

    public List<PolarisResource<CountV0Attributes>> getCountV0ResourcesFromIssueApiUrl(HttpUrl issueApiUrl)
            throws IntegrationException {
        return polarisService.getAll(issueApiUrl, CountV0Attributes.class);
    }

    public Integer getTotalIssueCountFromIssueApiUrl(HttpUrl issueApiUrl) throws IntegrationException {
        return getCountV0ResourcesFromIssueApiUrl(issueApiUrl).stream()
                .map(PolarisResource::getAttributes)
                .map(CountV0Attributes::getValue)
                .filter(Objects::nonNull)
                .reduce(0, Integer::sum);
    }
}
