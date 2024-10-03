/*
 * blackduck-coverity-on-polaris
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.polaris.common.cli.model.json.parser;

import com.blackduck.integration.polaris.common.cli.model.CliCommonResponseModel;
import com.blackduck.integration.polaris.common.cli.model.json.UnsupportedVersionCliScanResponse;
import com.blackduck.integration.polaris.common.exception.PolarisIntegrationException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

public class CliScanUnsupportedParser extends CliScanParser<UnsupportedVersionCliScanResponse> {
    private final String versionString;

    public CliScanUnsupportedParser(Gson gson, String versionString) {
        super(gson);
        this.versionString = versionString;
    }

    @Override
    public TypeToken<UnsupportedVersionCliScanResponse> getTypeToken() {
        return null;
    }

    @Override
    public CliCommonResponseModel fromCliScan(JsonObject versionlessModel) throws PolarisIntegrationException {
        throw new PolarisIntegrationException("Version " + versionString + " of the cli-scan.json is not supported.");
    }
}
