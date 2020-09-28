/**
 * synopsys-polaris
 *
 * Copyright (c) 2020 Synopsys, Inc.
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
package com.synopsys.integration.jenkins.polaris.extensions.pipeline;

import java.io.Serializable;

import javax.annotation.Nullable;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.StaplerRequest;

import com.synopsys.integration.jenkins.annotations.HelpMarkdown;

import hudson.Extension;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Descriptor;
import net.sf.json.JSONObject;

public class PipelineCreateChangeSetFile extends AbstractDescribableImpl<PipelineCreateChangeSetFile> implements Serializable {
    private static final long serialVersionUID = 3487021835917084100L;
    @Nullable
    @HelpMarkdown("Specify a comma separated list of filename patterns that you would like to explicitly excluded from the Jenkins change set.  \r\n"
                      + "The pattern is applied to determine which files will be populated in the change set file, stored at $CHANGE_SET_FILE_PATH.  \r\n"
                      + "If blank, will exclude none.  \r\n"
                      + "Examples:\r\n"
                      + "\r\n"
                      + "| File Name | Pattern    | Will be excluded |\r\n"
                      + "| --------- | ---------- | ---------------- |\r\n"
                      + "| test.java | *.java     | Yes              |\r\n"
                      + "| test.java | *.jpg      | No               |\r\n"
                      + "| test.java | test.*     | Yes              |\r\n"
                      + "| test.java | test.????  | Yes              |\r\n"
                      + "| test.java | test.????? | No               |")
    private String excluding;

    @Nullable
    @HelpMarkdown("Specify a comma separated list of filename patterns that you would like to explicitly included from the Jenkins change set.  \r\n"
                      + "The pattern is applied to determine which files will be populated in the change set file, stored at $CHANGE_SET_FILE_PATH.  \r\n"
                      + "If blank, will include all. \r\n"
                      + "Examples:\r\n"
                      + "\r\n"
                      + "| File Name | Pattern    | Will be included |\r\n"
                      + "| --------- | ---------- | ---------------- |\r\n"
                      + "| test.java | *.java     | Yes              |\r\n"
                      + "| test.java | *.jpg      | No               |\r\n"
                      + "| test.java | test.*     | Yes              |\r\n"
                      + "| test.java | test.????  | Yes              |\r\n"
                      + "| test.java | test.????? | No               |")
    private String including;

    @Nullable
    @HelpMarkdown("If true (checked), returns -1 instead of throwing a IntegrationAbortException when static analysis is skipped because the change set contained no files to analyze.")
    private Boolean skipQuietly;

    @DataBoundConstructor
    public PipelineCreateChangeSetFile() {
        // do nothing
    }

    @Nullable
    public String getIncluding() {
        return including;
    }

    @DataBoundSetter
    public void setIncluding(String including) {
        this.including = including;
    }

    @Nullable
    public String getExcluding() {
        return excluding;
    }

    @DataBoundSetter
    public void setExcluding(String excluding) {
        this.excluding = excluding;
    }

    @Override
    public DescriptorImpl getDescriptor() {
        return (DescriptorImpl) super.getDescriptor();
    }

    @Nullable
    public Boolean getSkipQuietly() {
        if (Boolean.TRUE.equals(skipQuietly)) {
            return skipQuietly;
        }
        return null;
    }

    @DataBoundSetter
    public void setSkipQuietly(Boolean skipQuietly) {
        this.skipQuietly = skipQuietly;
    }

    @Extension
    public static class DescriptorImpl extends Descriptor<PipelineCreateChangeSetFile> {
        public DescriptorImpl() {
            super(PipelineCreateChangeSetFile.class);
            load();
        }

        @Override
        public boolean configure(StaplerRequest req, JSONObject formData) throws FormException {
            req.bindJSON(this, formData);
            save();
            return super.configure(req, formData);
        }

    }

}