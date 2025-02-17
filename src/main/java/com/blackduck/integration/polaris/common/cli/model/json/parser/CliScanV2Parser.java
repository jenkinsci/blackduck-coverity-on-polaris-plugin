/*
 * blackduck-coverity-on-polaris
 *
 * Copyright ©2024 Black Duck Software, Inc. All rights reserved.
 * Black Duck® is a trademark of Black Duck Software, Inc. in the United States and other countries.
 */
package com.blackduck.integration.polaris.common.cli.model.json.parser;

import com.blackduck.integration.exception.IntegrationException;
import com.blackduck.integration.polaris.common.cli.model.CliCommonResponseModel;
import com.blackduck.integration.polaris.common.cli.model.CommonToolInfo;
import com.blackduck.integration.polaris.common.cli.model.json.v2.CliScanV2;
import com.blackduck.integration.polaris.common.cli.model.json.v2.ToolInfoV2;
import com.blackduck.integration.rest.HttpUrl;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;

public class CliScanV2Parser extends CliScanParser<CliScanV2> {
    public CliScanV2Parser(Gson gson) {
        super(gson);
    }

    @Override
    public TypeToken<CliScanV2> getTypeToken() {
        return new TypeToken<CliScanV2>() {};
    }

    public CliCommonResponseModel fromCliScan(JsonObject versionlessModel) throws IntegrationException {
        CliScanV2 cliScanV2 = fromJson(versionlessModel);

        CliCommonResponseModel cliCommonResponseModel =
                createResponseModel(cliScanV2.issueSummary, cliScanV2.projectInfo, cliScanV2.scanInfo);

        List<CommonToolInfo> tools = Optional.ofNullable(cliScanV2.tools).orElse(Collections.emptyList()).stream()
                .map(this::fromToolInfoV2)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        cliCommonResponseModel.setTools(tools);

        return cliCommonResponseModel;
    }

    private Optional<CommonToolInfo> fromToolInfoV2(ToolInfoV2 toolInfoV2) {
        if (toolInfoV2 != null) {
            try {
                CommonToolInfo commonToolInfo = createCommonToolInfo(toolInfoV2);
                commonToolInfo.setToolName(toolInfoV2.toolName);
                if (StringUtils.isNotBlank(toolInfoV2.issueApiUrl)) {
                    commonToolInfo.setIssueApiUrl(new HttpUrl(toolInfoV2.issueApiUrl));
                }

                return Optional.of(commonToolInfo);
            } catch (IntegrationException ignored) {

            }
        }

        return Optional.empty();
    }
}
