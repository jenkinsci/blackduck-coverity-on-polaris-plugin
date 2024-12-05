package com.blackduck.integration.jenkins.polaris.service;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

public class PolarisCliVersionHandlerTest {

    private final PolarisCliVersionHandler versionHandler = new PolarisCliVersionHandler();

    @Test
    public void testExtractPolarisCliVersion_ValidLogForOlderCli() throws Exception {
        String log = "Some random log\n"
                + "[INFO] [b987uryi] Polaris Software Integrity Platform CLI Scan Client version - 2024.6.0\n"
                + "Some other log";

        InputStream logStream = new ByteArrayInputStream(log.getBytes(StandardCharsets.UTF_8));
        String extractedVersion = versionHandler.extractPolarisCliVersion(logStream);

        assertEquals("2024.6.0", extractedVersion, "Extracted version should match the expected value.");
    }

    @Test
    public void testExtractPolarisCliVersion_ValidLogNewCli() throws Exception {
        String log = "Some random log\n"
                + "[INFO] [b987uryi] Coverity on Polaris Platform CLI Scan Client version - 2024.9.1\n"
                + "Some other log";

        InputStream logStream = new ByteArrayInputStream(log.getBytes(StandardCharsets.UTF_8));
        String extractedVersion = versionHandler.extractPolarisCliVersion(logStream);

        assertEquals("2024.9.1", extractedVersion, "Extracted version should match the expected value.");
    }

    @Test
    public void testExtractPolarisCliVersion_InvalidLog() throws Exception {
        String log = "Some random log\n" + "No version info here\n" + "Some other log";

        InputStream logStream = new ByteArrayInputStream(log.getBytes(StandardCharsets.UTF_8));
        String extractedVersion = versionHandler.extractPolarisCliVersion(logStream);

        assertNull(extractedVersion, "Version should be null when no valid version string is found.");
    }

    @Test
    public void testComparePolarisVersions_SameVersion() {
        String version1 = "2024.6.0";
        String version2 = "2024.6.0";

        int result = versionHandler.comparePolarisVersions(version1, version2);

        assertEquals(0, result, "Comparison of identical versions should return 0.");
    }

    @Test
    public void testComparePolarisVersions_Version1GreaterThanVersion2() {
        String version1 = "2024.6.0";
        String version2 = "2023.5.1";

        int result = versionHandler.comparePolarisVersions(version1, version2);

        assertTrue(result > 0, "Version 2024.6.0 should be greater than 2023.5.1.");
    }

    @Test
    public void testComparePolarisVersions_Version1LessThanVersion2() {
        String version1 = "2023.5.1";
        String version2 = "2024.6.0";

        int result = versionHandler.comparePolarisVersions(version1, version2);

        assertTrue(result < 0, "Version 2023.5.1 should be less than 2024.6.0.");
    }

    @Test
    public void testComparePolarisVersions_PatchVersion() {
        String version1 = "2023.5.1";
        String version2 = "2023.5.0";

        int result = versionHandler.comparePolarisVersions(version1, version2);

        assertTrue(result > 0, "Version 2023.5.1 should be greater than 2023.5.0.");
    }

    @Test
    public void testComparePolarisVersions_DevBuildVersion() {
        String version1 = "1.24.33";
        String version2 = "2023.9.0";

        int result = versionHandler.comparePolarisVersions(version1, version2);

        assertTrue(result > 0, "Version 1.24.33 should be greater than 2023.5.0.");
    }
}
