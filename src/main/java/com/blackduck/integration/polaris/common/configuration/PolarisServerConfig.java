/*
 * blackduck-coverity-on-polaris
 *
 * Copyright ©2024 Black Duck Software, Inc. All rights reserved.
 * Black Duck® is a trademark of Black Duck Software, Inc. in the United States and other countries.
 */
package com.blackduck.integration.polaris.common.configuration;
/**
 * polaris-common
 *
 * Copyright (c) 2020 Black Duck Software, Inc.
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
import com.blackduck.integration.builder.Buildable;
import com.blackduck.integration.log.IntLogger;
import com.blackduck.integration.polaris.common.rest.AccessTokenPolarisHttpClient;
import com.blackduck.integration.polaris.common.service.PolarisServicesFactory;
import com.blackduck.integration.rest.HttpUrl;
import com.blackduck.integration.rest.proxy.ProxyInfo;
import com.blackduck.integration.rest.support.AuthenticationSupport;
import com.blackduck.integration.util.Stringable;
import com.google.gson.Gson;
import java.util.function.BiConsumer;

public class PolarisServerConfig extends Stringable implements Buildable {
    private final HttpUrl polarisUrl;
    private final int timeoutSeconds;

    @SuppressWarnings("lgtm[jenkins/plaintext-storage]")
    private final String accessToken;

    private final ProxyInfo proxyInfo;
    private final Gson gson;
    private final AuthenticationSupport authenticationSupport;

    public PolarisServerConfig(
            HttpUrl polarisUrl,
            int timeoutSeconds,
            String accessToken,
            ProxyInfo proxyInfo,
            Gson gson,
            AuthenticationSupport authenticationSupport) {
        this.polarisUrl = polarisUrl;
        this.timeoutSeconds = timeoutSeconds;
        this.accessToken = accessToken;
        this.proxyInfo = proxyInfo;
        this.gson = gson;
        this.authenticationSupport = authenticationSupport;
    }

    public static PolarisServerConfigBuilder newBuilder() {
        return new PolarisServerConfigBuilder();
    }

    public AccessTokenPolarisHttpClient createPolarisHttpClient(IntLogger logger) {
        return new AccessTokenPolarisHttpClient(
                logger, timeoutSeconds, proxyInfo, polarisUrl, accessToken, gson, authenticationSupport);
    }

    public PolarisServicesFactory createPolarisServicesFactory(IntLogger logger) {
        return new PolarisServicesFactory(logger, createPolarisHttpClient(logger), gson);
    }

    public void populateEnvironmentVariables(BiConsumer<String, String> pairsConsumer) {
        pairsConsumer.accept(PolarisServerConfigBuilder.URL_KEY.getKey(), polarisUrl.toString());
        pairsConsumer.accept(PolarisServerConfigBuilder.ACCESS_TOKEN_KEY.getKey(), accessToken);
    }

    public HttpUrl getPolarisUrl() {
        return polarisUrl;
    }

    public int getTimeoutSeconds() {
        return timeoutSeconds;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public ProxyInfo getProxyInfo() {
        return proxyInfo;
    }

    public Gson getGson() {
        return gson;
    }

    public AuthenticationSupport getAuthenticationSupport() {
        return authenticationSupport;
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
