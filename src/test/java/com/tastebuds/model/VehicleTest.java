package com.tastebuds.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.assertj.core.api.Assertions.*;


class VehicleTest {

    @Test
    @DisplayName("Vehicle should be created with correct initial values")
    void testVehicleCreation() {
        Vehicle vehicle = new Vehicle("BIKE-001", "Motorcycle");

        assertThat(vehicle.getVehicleId()).isEqualTo("BIKE-001");
        assertThat(vehicle.getType()).isEqualTo("Motorcycle");
        assertThat(vehicle.isAvailable()).isTrue();
    }

    @Test
    @DisplayName("Vehicle availability should be changeable")
    void testVehicleAvailability() {
        Vehicle vehicle = new Vehicle("CAR-001", "Sedan");

        assertThat(vehicle.isAvailable()).isTrue();

        vehicle.setAvailable(false);
        assertThat(vehicle.isAvailable()).isFalse();
    }

    @Test
    @DisplayName("Vehicle types should be distinguishable")
    void testDifferentVehicleTypes() {
        Vehicle bike = new Vehicle("BIKE-001", "Motorcycle");
        Vehicle car = new Vehicle("CAR-001", "Sedan");
        Vehicle van = new Vehicle("VAN-001", "Van");

        assertThat(bike.getType()).isEqualTo("Motorcycle");
        assertThat(car.getType()).isEqualTo("Sedan");
        assertThat(van.getType()).isEqualTo("Van");
    }

    @Test
    @DisplayName("Vehicle toString should work correctly")
    void testVehicleToString() {
        Vehicle vehicle = new Vehicle("BIKE-002", "Motorcycle");
        String result = vehicle.toString();

        assertThat(result).contains("BIKE-002");
        assertThat(result).contains("Motorcycle");
        assertThat(result).contains("true");
    }
}