package com.blackduck.integration.polaris.common.rest;

import com.blackduck.integration.exception.IntegrationException;
import com.blackduck.integration.log.LogLevel;
import com.blackduck.integration.log.PrintStreamIntLogger;
import com.blackduck.integration.rest.HttpUrl;
import com.blackduck.integration.rest.RestConstants;
import com.blackduck.integration.rest.proxy.ProxyInfo;
import com.blackduck.integration.rest.response.Response;
import com.blackduck.integration.rest.support.AuthenticationSupport;
import com.google.gson.Gson;
import org.apache.http.client.methods.HttpUriRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class AccessTokenErrorTestIT {
    @Test
    public void unauthorizedTest() throws IntegrationException {
        AccessTokenPolarisHttpClient httpClient = new AccessTokenPolarisHttpClient(
                new PrintStreamIntLogger(System.out, LogLevel.INFO),
                300,
                ProxyInfo.NO_PROXY_INFO,
                new HttpUrl("http://www.blackducksoftware.com"),
                "garbage token",
                new Gson(),
                new AuthenticationSupport());

        String authHeader = "Authorization";
        HttpUriRequest request = Mockito.mock(HttpUriRequest.class);
        Mockito.when(request.containsHeader(authHeader)).thenReturn(true);
        Mockito.doNothing().when(request).removeHeaders(authHeader);

        Response response = Mockito.mock(Response.class);
        Mockito.when(response.getStatusCode()).thenReturn(RestConstants.UNAUTHORIZED_401);

        httpClient.handleErrorResponse(request, response);
        Mockito.verify(request, Mockito.times(1)).removeHeaders(authHeader);
        Mockito.verify(response, Mockito.times(1)).getStatusCode();
    }
}
