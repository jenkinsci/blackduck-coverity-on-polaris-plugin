/*
 * blackduck-coverity-on-polaris
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.jenkins.polaris.service;

import com.blackduck.integration.polaris.common.cli.PolarisCliResponseUtility;
import com.blackduck.integration.polaris.common.cli.model.CliCommonResponseModel;
import com.blackduck.integration.polaris.common.cli.model.CommonIssueSummary;
import com.blackduck.integration.polaris.common.cli.model.CommonScanInfo;
import com.blackduck.integration.polaris.common.cli.model.CommonToolInfo;
import com.blackduck.integration.polaris.common.service.CountService;
import com.blackduck.integration.polaris.common.service.JobService;
import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.jenkins.exception.JenkinsUserFriendlyException;
import com.synopsys.integration.jenkins.extensions.JenkinsIntLogger;
import com.synopsys.integration.rest.HttpUrl;
import java.util.Optional;

public class PolarisCliIssueCountService {
    public static final String STEP_EXCEPTION_PREFIX =
            "Issue count for most recent Coverity on Polaris Platform analysis could not be determined: ";
    private final JenkinsIntLogger logger;
    private final CountService countService;
    private final JobService jobService;
    private final PolarisCliResponseUtility polarisCliResponseUtility;

    public PolarisCliIssueCountService(
            JenkinsIntLogger logger,
            CountService countService,
            JobService jobService,
            PolarisCliResponseUtility polarisCliResponseUtility) {
        this.logger = logger;
        this.countService = countService;
        this.jobService = jobService;
        this.polarisCliResponseUtility = polarisCliResponseUtility;
    }

    public Integer getIssueCount(long jobTimeoutInSeconds, String cliCommonResponseModelJson)
            throws IntegrationException, JenkinsUserFriendlyException, InterruptedException {
        CliCommonResponseModel polarisCliResponseModel =
                polarisCliResponseUtility.getPolarisCliResponseModelFromString(cliCommonResponseModelJson);

        Optional<CommonIssueSummary> issueSummary = polarisCliResponseModel.getIssueSummary();
        CommonScanInfo scanInfo = polarisCliResponseModel.getScanInfo();

        if (issueSummary.isPresent()) {
            logger.debug("Found total issue count in cli-scan.json, scan must have been run with -w");
            return issueSummary.get().getTotalIssueCount();
        }

        if (jobTimeoutInSeconds < 1) {
            throw new JenkinsUserFriendlyException(STEP_EXCEPTION_PREFIX
                    + "Job timeout must be a positive integer if the Coverity on Polaris CLI is being run without -w");
        }

        HttpUrl issueApiUrl = Optional.ofNullable(scanInfo)
                .map(CommonScanInfo::getIssueApiUrl)
                .orElseThrow(
                        () -> new JenkinsUserFriendlyException(
                                "Coverity on Polaris Platform for Jenkins cannot find the total issue count or issue api url in the cli-scan.json. Please ensure that you are using a supported version of the Coverity on Polaris CLI."));

        logger.debug("Found issue api url, polling for job status");

        for (CommonToolInfo tool : polarisCliResponseModel.getTools()) {
            HttpUrl jobStatusUrl = tool.getJobStatusUrl();
            if (jobStatusUrl == null) {
                throw new JenkinsUserFriendlyException(
                        STEP_EXCEPTION_PREFIX + "tool with name " + tool.getToolName() + " has no jobStatusUrl");
            }
            jobService.waitForJobStateIsCompletedOrDieByUrl(
                    jobStatusUrl, jobTimeoutInSeconds, JobService.DEFAULT_WAIT_INTERVAL);
        }

        return countService.getTotalIssueCountFromIssueApiUrl(issueApiUrl);
    }
}
