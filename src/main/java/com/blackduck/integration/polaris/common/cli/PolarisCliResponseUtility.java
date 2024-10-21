/*
 * blackduck-coverity-on-polaris
 *
 * Copyright ©2024 Black Duck Software, Inc. All rights reserved.
 * Black Duck® is a trademark of Black Duck Software, Inc. in the United States and other countries.
 */
package com.blackduck.integration.polaris.common.cli;

import com.blackduck.integration.exception.IntegrationException;
import com.blackduck.integration.jenkins.polaris.service.PolarisCliVersionHandler;
import com.blackduck.integration.log.IntLogger;
import com.blackduck.integration.polaris.common.cli.model.CliCommonResponseModel;
import com.blackduck.integration.polaris.common.cli.model.json.CliCommonResponseAdapter;
import com.blackduck.integration.polaris.common.exception.PolarisIntegrationException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PolarisCliResponseUtility {
    private final IntLogger logger;
    private final Gson gson;
    private final CliCommonResponseAdapter cliCommonResponseAdapter;
    private static final String OLDER_POLARIS_CLI_VERSION = "2024.9.0";

    public PolarisCliResponseUtility(IntLogger logger, Gson gson, CliCommonResponseAdapter cliCommonResponseAdapter) {
        this.logger = logger;
        this.gson = gson;
        this.cliCommonResponseAdapter = cliCommonResponseAdapter;
    }

    public static PolarisCliResponseUtility defaultUtility(IntLogger logger) {
        Gson gson = new Gson();
        return new PolarisCliResponseUtility(logger, gson, new CliCommonResponseAdapter(gson));
    }

    public static Path getDefaultPathToJson(String projectRootDirectory, String polarisCliVersion) {
        String subDirectory;
        PolarisCliVersionHandler polarisCliVersionHandler = new PolarisCliVersionHandler();

        if (polarisCliVersion != null
                && polarisCliVersionHandler.comparePolarisVersions(polarisCliVersion, OLDER_POLARIS_CLI_VERSION) <= 0) {
            subDirectory = ".synopsys";
        } else {
            subDirectory = ".blackduck";
        }

        return Paths.get(projectRootDirectory)
                .resolve(subDirectory)
                .resolve("polaris")
                .resolve("cli-scan.json");
    }

    public Gson getGson() {
        return gson;
    }

    public CliCommonResponseModel getPolarisCliResponseModelFromDefaultLocation(String projectRootDirectory)
            throws PolarisIntegrationException {
        Path pathToJson = getDefaultPathToJson(projectRootDirectory, null);
        return getPolarisCliResponseModel(pathToJson);
    }

    public CliCommonResponseModel getPolarisCliResponseModel(String pathToJson) throws PolarisIntegrationException {
        Path actualPathToJson = Paths.get(pathToJson);
        return getPolarisCliResponseModel(actualPathToJson);
    }

    public CliCommonResponseModel getPolarisCliResponseModel(Path pathToJson) throws PolarisIntegrationException {
        try (BufferedReader reader = Files.newBufferedReader(pathToJson)) {
            logger.debug("Attempting to retrieve CliCommonResponseModel from " + pathToJson.toString());
            return getPolarisCliResponseModelFromJsonObject(gson.fromJson(reader, JsonObject.class));
        } catch (IOException | IntegrationException e) {
            throw new PolarisIntegrationException(
                    "There was a problem parsing the Coverity on Polaris CLI response json at " + pathToJson.toString(),
                    e);
        }
    }

    public CliCommonResponseModel getPolarisCliResponseModelFromString(String rawPolarisCliResponse)
            throws IntegrationException {
        return getPolarisCliResponseModelFromJsonObject(gson.fromJson(rawPolarisCliResponse, JsonObject.class));
    }

    public CliCommonResponseModel getPolarisCliResponseModelFromJsonObject(JsonObject versionlessModel)
            throws IntegrationException {
        String versionString = versionlessModel.get("version").getAsString();
        PolarisCliResponseVersion polarisCliResponseVersion = PolarisCliResponseVersion.parse(versionString)
                .orElseThrow(() -> new PolarisIntegrationException(
                        "Version " + versionString + " is not a valid version of cli-scan.json"));

        return cliCommonResponseAdapter.fromJson(versionString, polarisCliResponseVersion, versionlessModel);
    }
}
