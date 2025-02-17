/*
 * blackduck-coverity-on-polaris
 *
 * Copyright ©2024 Black Duck Software, Inc. All rights reserved.
 * Black Duck® is a trademark of Black Duck Software, Inc. in the United States and other countries.
 */
package com.blackduck.integration.polaris.common.service;

import com.blackduck.integration.exception.IntegrationException;
import com.blackduck.integration.log.IntLogger;
import com.blackduck.integration.polaris.common.api.PolarisResponse;
import com.blackduck.integration.rest.response.Response;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.lang.reflect.Type;
import java.util.Map;

public class PolarisJsonTransformer {
    private static final String FIELD_NAME_POLARIS_COMPONENT_JSON = "json";

    private final Gson gson;
    private final IntLogger logger;

    public PolarisJsonTransformer(Gson gson, IntLogger logger) {
        this.gson = gson;
        this.logger = logger;
    }

    public <C extends PolarisResponse> C getResponse(Response response, Type responseType) throws IntegrationException {
        String json = response.getContentString();
        return getResponseAs(json, responseType);
    }

    public <C extends PolarisResponse> C getResponseAs(String json, Type responseType) throws IntegrationException {
        try {
            JsonObject jsonElement = gson.fromJson(json, JsonObject.class);
            return getResponseAs(jsonElement, responseType);
        } catch (JsonSyntaxException e) {
            logger.error(
                    String.format("Could not parse the provided json with Gson:%s%s", System.lineSeparator(), json));
            throw new IntegrationException(e.getMessage(), e);
        }
    }

    public <C extends PolarisResponse> C getResponseAs(JsonObject jsonObject, Type responseType)
            throws IntegrationException {
        String json = gson.toJson(jsonObject);
        try {
            addJsonAsField(jsonObject);
            return gson.fromJson(jsonObject, responseType);
        } catch (JsonSyntaxException e) {
            logger.error(String.format(
                    "Could not parse the provided jsonElement with Gson:%s%s", System.lineSeparator(), json));
            throw new IntegrationException(e.getMessage(), e);
        }
    }

    private void addJsonAsField(JsonElement jsonElement) {
        if (jsonElement.isJsonObject()) {
            JsonObject innerObject = jsonElement.getAsJsonObject();
            jsonElement.getAsJsonObject().addProperty(FIELD_NAME_POLARIS_COMPONENT_JSON, gson.toJson(innerObject));
            for (Map.Entry<String, JsonElement> innerObjectFields : innerObject.entrySet()) {
                addJsonAsField(innerObjectFields.getValue());
            }
        } else if (jsonElement.isJsonArray()) {
            for (JsonElement arrayElement : jsonElement.getAsJsonArray()) {
                addJsonAsField(arrayElement);
            }
        }
    }
}
