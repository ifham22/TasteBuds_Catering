package com.tastebuds.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.assertj.core.api.Assertions.*;

/**
 * Unit tests for DrivingLicense class
 */
class DrivingLicenseTest {

    @Test
    @DisplayName("DrivingLicense should be created with valid status")
    void testDrivingLicenseCreation() {
        DrivingLicense license = new DrivingLicense("DL-123456");

        assertThat(license.getLicenseNo()).isEqualTo("DL-123456");
        assertThat(license.isValid()).isTrue();
    }

    @Test
    @DisplayName("DrivingLicense should accept custom validity status")
    void testDrivingLicenseCustomValidity() {
        DrivingLicense license = new DrivingLicense("DL-789012", false);

        assertThat(license.getLicenseNo()).isEqualTo("DL-789012");
        assertThat(license.isValid()).isFalse();
    }

    @Test
    @DisplayName("DrivingLicense validity should be changeable")
    void testDrivingLicenseSetValidity() {
        DrivingLicense license = new DrivingLicense("DL-345678");

        assertThat(license.isValid()).isTrue();

        license.setValid(false);
        assertThat(license.isValid()).isFalse();
    }

    @Test
    @DisplayName("DrivingLicense toString should work correctly")
    void testDrivingLicenseToString() {
        DrivingLicense license = new DrivingLicense("DL-111222");
        String result = license.toString();

        assertThat(result).contains("DL-111222");
        assertThat(result).contains("true");
    }
}