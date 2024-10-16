/*
 * blackduck-coverity-on-polaris
 *
 * Copyright ©2024 Black Duck Software, Inc. All rights reserved.
 * Black Duck® is a trademark of Black Duck Software, Inc. in the United States and other countries.
 */
package com.blackduck.integration.polaris.common.rest;

import com.blackduck.integration.exception.IntegrationException;
import com.blackduck.integration.log.IntLogger;
import com.blackduck.integration.rest.HttpMethod;
import com.blackduck.integration.rest.HttpUrl;
import com.blackduck.integration.rest.client.AuthenticatingIntHttpClient;
import com.blackduck.integration.rest.proxy.ProxyInfo;
import com.blackduck.integration.rest.response.Response;
import com.blackduck.integration.rest.support.AuthenticationSupport;
import com.google.gson.Gson;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;

public class AccessTokenPolarisHttpClient extends AuthenticatingIntHttpClient {
    private static final String AUTHENTICATION_SPEC = "api/auth/authenticate";
    private static final String AUTHENTICATION_RESPONSE_KEY = "jwt";

    private static final String ACCESS_TOKEN_REQUEST_KEY = "accesstoken";
    private static final String ACCESS_TOKEN_REQUEST_CONTENT_TYPE = "application/x-www-form-urlencoded";

    private final Gson gson;
    private final AuthenticationSupport authenticationSupport;
    private final HttpUrl baseUrl;
    private final String accessToken;

    public AccessTokenPolarisHttpClient(
            IntLogger logger,
            int timeout,
            ProxyInfo proxyInfo,
            HttpUrl baseUrl,
            String accessToken,
            Gson gson,
            AuthenticationSupport authenticationSupport) {
        super(logger, gson, timeout, false, proxyInfo);
        this.baseUrl = baseUrl;
        this.accessToken = accessToken;
        this.gson = gson;
        this.authenticationSupport = authenticationSupport;

        if (StringUtils.isBlank(accessToken)) {
            throw new IllegalArgumentException("No access token was found.");
        }
    }

    @Override
    public void handleErrorResponse(HttpUriRequest request, Response response) {
        super.handleErrorResponse(request, response);

        authenticationSupport.handleTokenErrorResponse(this, request, response);
    }

    @Override
    public boolean isAlreadyAuthenticated(HttpUriRequest request) {
        return authenticationSupport.isTokenAlreadyAuthenticated(request);
    }

    @Override
    protected void completeAuthenticationRequest(HttpUriRequest request, Response response) {
        authenticationSupport.completeTokenAuthenticationRequest(
                request, response, logger, gson, this, AccessTokenPolarisHttpClient.AUTHENTICATION_RESPONSE_KEY);
    }

    @Override
    public final Response attemptAuthentication() throws IntegrationException {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", AccessTokenPolarisHttpClient.ACCESS_TOKEN_REQUEST_CONTENT_TYPE);

        String httpBody = String.format("%s=%s", AccessTokenPolarisHttpClient.ACCESS_TOKEN_REQUEST_KEY, accessToken);
        HttpEntity httpEntity = new StringEntity(httpBody, StandardCharsets.UTF_8);

        RequestBuilder requestBuilder = createRequestBuilder(HttpMethod.POST, headers);
        requestBuilder.setEntity(httpEntity);

        HttpUrl authenticationUrl = baseUrl.appendRelativeUrl(AccessTokenPolarisHttpClient.AUTHENTICATION_SPEC);
        return authenticationSupport.attemptAuthentication(this, authenticationUrl, requestBuilder);
    }

    public HttpUrl getPolarisServerUrl() {
        return baseUrl;
    }
}
