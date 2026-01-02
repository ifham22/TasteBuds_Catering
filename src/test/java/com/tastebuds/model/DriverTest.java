package com.tastebuds.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.assertj.core.api.Assertions.*;


class DriverTest {

    private Driver driver;

    @BeforeEach
    void setUp() {
        driver = new Driver("D001", "Rahim Khan", "DL-123456");
    }

    @Test
    @DisplayName("Driver should be created with correct initial values")
    void testDriverCreation() {
        assertThat(driver.getId()).isEqualTo("D001");
        assertThat(driver.getName()).isEqualTo("Rahim Khan");
        assertThat(driver.getLicense()).isNotNull();
        assertThat(driver.getLicense().getLicenseNo()).isEqualTo("DL-123456");
        assertThat(driver.isAvailable()).isTrue();
    }

    @Test
    @DisplayName("Driver should verify license correctly with valid license")
    void testDriverVerifyValidLicense() {
        boolean result = driver.verifyLicense("DL-123456");
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Driver should reject invalid license number")
    void testDriverVerifyInvalidLicenseNumber() {
        boolean result = driver.verifyLicense("DL-999999");
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("Driver should reject invalid license status")
    void testDriverVerifyInvalidLicenseStatus() {
        driver.getLicense().setValid(false);
        boolean result = driver.verifyLicense("DL-123456");
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("Driver availability should be changeable")
    void testDriverAvailability() {
        assertThat(driver.isAvailable()).isTrue();

        driver.setAvailable(false);
        assertThat(driver.isAvailable()).isFalse();

        driver.setAvailable(true);
        assertThat(driver.isAvailable()).isTrue();
    }

    @Test
    @DisplayName("Driver should be created with DrivingLicense object")
    void testDriverCreationWithLicenseObject() {
        DrivingLicense license = new DrivingLicense("DL-555555");
        Driver driver2 = new Driver("D002", "Karim Ali", license);

        assertThat(driver2.getId()).isEqualTo("D002");
        assertThat(driver2.getName()).isEqualTo("Karim Ali");
        assertThat(driver2.getLicense()).isEqualTo(license);
        assertThat(driver2.getLicense().getLicenseNo()).isEqualTo("DL-555555");
    }

    @Test
    @DisplayName("Driver toString should contain all details")
    void testDriverToString() {
        String result = driver.toString();

        assertThat(result).contains("D001");
        assertThat(result).contains("Rahim Khan");
        assertThat(result).contains("DL-123456");
        assertThat(result).contains("true");
    }

    @Test
    @DisplayName("Driver composition relationship with DrivingLicense")
    void testDriverLicenseComposition() {
        // Driver HAS-A DrivingLicense (composition)
        assertThat(driver.getLicense()).isNotNull();
        assertThat(driver.getLicense().getLicenseNo()).isEqualTo("DL-123456");

        // Can replace license
        DrivingLicense newLicense = new DrivingLicense("DL-777777");
        driver.setLicense(newLicense);
        assertThat(driver.getLicense().getLicenseNo()).isEqualTo("DL-777777");
    }
}
