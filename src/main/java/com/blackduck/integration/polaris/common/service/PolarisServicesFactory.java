/*
 * blackduck-coverity-on-polaris
 *
 * Copyright ©2024 Black Duck Software, Inc. All rights reserved.
 * Black Duck® is a trademark of Black Duck Software, Inc. in the United States and other countries.
 */
package com.blackduck.integration.polaris.common.service;

import com.blackduck.integration.polaris.common.request.PolarisRequestFactory;
import com.blackduck.integration.polaris.common.rest.AccessTokenPolarisHttpClient;
import com.google.gson.Gson;
import com.synopsys.integration.log.IntLogger;

public class PolarisServicesFactory {
    private final IntLogger logger;
    private final AccessTokenPolarisHttpClient httpClient;
    private final Gson gson;
    private final PolarisJsonTransformer polarisJsonTransformer;
    private int defaultPageSize;

    public PolarisServicesFactory(IntLogger logger, AccessTokenPolarisHttpClient httpClient, Gson gson) {
        this.logger = logger;
        this.httpClient = httpClient;
        this.gson = gson;
        this.polarisJsonTransformer = new PolarisJsonTransformer(gson, logger);
        this.defaultPageSize = PolarisRequestFactory.DEFAULT_LIMIT;
    }

    public PolarisService createPolarisService() {
        return new PolarisService(httpClient, polarisJsonTransformer, defaultPageSize);
    }

    public JobService createJobService() {
        return new JobService(logger, createPolarisService());
    }

    public CountService createCountService() {
        return new CountService(createPolarisService());
    }

    public ContextsService createContextsService() {
        return new ContextsService(createPolarisService(), httpClient.getPolarisServerUrl());
    }

    public IntLogger getLogger() {
        return logger;
    }

    public Gson getGson() {
        return gson;
    }

    public AccessTokenPolarisHttpClient getHttpClient() {
        return httpClient;
    }

    public void setDefaultPageSize(int defaultPageSize) {
        if (defaultPageSize >= 0) {
            this.defaultPageSize = defaultPageSize;
        }
    }
}
