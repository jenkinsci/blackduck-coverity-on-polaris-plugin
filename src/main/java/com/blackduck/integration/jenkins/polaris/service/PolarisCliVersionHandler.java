package com.blackduck.integration.jenkins.polaris.service;

import com.fasterxml.jackson.core.Version;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PolarisCliVersionHandler {
    private static final String POLARIS_VERSION_PATTERN =
            "Polaris Software Integrity Platform CLI Scan Client version - (\\d+\\.\\d+\\.\\d+)";

    public String extractPolarisCliVersion(InputStream logStream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(logStream, StandardCharsets.UTF_8))) {
            String line;
            Pattern pattern = Pattern.compile(POLARIS_VERSION_PATTERN);
            while ((line = reader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    return matcher.group(1);
                }
            }
        }
        return null;
    }

    public int comparePolarisVersions(String version1, String version2) {
        String[] ver1Parts = version1.split("\\.");
        String[] ver2Parts = version2.split("\\.");

        if (ver1Parts.length != 3 && ver2Parts.length != 3) {
            return 1;
        }

        Version ver1 = new Version(
                Integer.parseInt(ver1Parts[0]),
                Integer.parseInt(ver1Parts[1]),
                Integer.parseInt(ver1Parts[2]),
                null,
                null,
                null);
        Version ver2 = new Version(
                Integer.parseInt(ver2Parts[0]),
                Integer.parseInt(ver2Parts[1]),
                Integer.parseInt(ver2Parts[2]),
                null,
                null,
                null);

        return ver1.compareTo(ver2);
    }
}
