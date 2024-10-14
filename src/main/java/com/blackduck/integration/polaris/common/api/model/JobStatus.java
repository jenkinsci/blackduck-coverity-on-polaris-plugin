/*
 * blackduck-coverity-on-polaris
 *
 * Copyright ©2024 Black Duck Software, Inc. All rights reserved. 
 * Black Duck® is a trademark of Black Duck Software, Inc. in the United States and other countries.
 */
package com.blackduck.integration.polaris.common.api.model;

import com.blackduck.integration.polaris.common.api.PolarisResponse;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

// this file should not be edited - if changes are necessary, the generator should be updated, then this file should be
// re-created

public class JobStatus extends PolarisResponse {
    private static final long serialVersionUID = 3583443188791241547L;

    @SerializedName("state")
    private StateEnum state;

    @SerializedName("progress")
    private Integer progress;

    public StateEnum getState() {
        return state;
    }

    /**
     * Get progress
     * minimum: 0
     * maximum: 100
     * @return progress
     */
    public Integer getProgress() {
        return progress;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @JsonAdapter(StateEnum.Adapter.class)
    public enum StateEnum {
        UNSCHEDULED("UNSCHEDULED"),
        DISPATCHED("DISPATCHED"),
        QUEUED("QUEUED"),
        RUNNING("RUNNING"),
        COMPLETED("COMPLETED"),
        CANCELLED("CANCELLED"),
        FAILED("FAILED");

        private final String value;

        StateEnum(String value) {
            this.value = value;
        }

        public static StateEnum fromValue(String text) {
            for (StateEnum b : StateEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }

        public static class Adapter extends TypeAdapter<StateEnum> {
            @Override
            public void write(JsonWriter jsonWriter, StateEnum enumeration) throws IOException {
                jsonWriter.value(enumeration.getValue());
            }

            @Override
            public StateEnum read(JsonReader jsonReader) throws IOException {
                String value = jsonReader.nextString();
                return StateEnum.fromValue(String.valueOf(value));
            }
        }
    }
}
