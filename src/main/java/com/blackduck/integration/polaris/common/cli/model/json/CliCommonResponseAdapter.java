/*
 * blackduck-coverity-on-polaris
 *
 * Copyright ©2024 Black Duck Software, Inc. All rights reserved. 
 * Black Duck® is a trademark of Black Duck Software, Inc. in the United States and other countries.
 */
package com.blackduck.integration.polaris.common.cli.model.json;

import com.blackduck.integration.polaris.common.cli.PolarisCliResponseVersion;
import com.blackduck.integration.polaris.common.cli.model.CliCommonResponseModel;
import com.blackduck.integration.polaris.common.cli.model.json.parser.CliScanParser;
import com.blackduck.integration.polaris.common.cli.model.json.parser.CliScanUnsupportedParser;
import com.blackduck.integration.polaris.common.cli.model.json.parser.CliScanV1Parser;
import com.blackduck.integration.polaris.common.cli.model.json.parser.CliScanV2Parser;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.synopsys.integration.exception.IntegrationException;

public class CliCommonResponseAdapter {
    private final Gson gson;

    public CliCommonResponseAdapter(Gson gson) {
        this.gson = gson;
    }

    public CliCommonResponseModel fromJson(
            String versionString, PolarisCliResponseVersion polarisCliResponseVersion, JsonObject versionlessModel)
            throws IntegrationException {
        CliScanParser<? extends CliScanResponse> cliScanParser = new CliScanUnsupportedParser(gson, versionString);

        int majorVersion = polarisCliResponseVersion.getMajor();
        if (majorVersion == 1) {
            cliScanParser = new CliScanV1Parser(gson);
        } else if (majorVersion == 2) {
            cliScanParser = new CliScanV2Parser(gson);
        }

        return cliScanParser.fromCliScan(versionlessModel);
    }
}
