package com.tastebuds.service;

import com.tastebuds.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.assertj.core.api.Assertions.*;

class DriverDeliveryTest {

    @Test
    @DisplayName("Driver can complete delivery and queue updates correctly")
    void testDriverCompletesDelivery() {
        TasteBudsSystem system = new TasteBudsSystem();

        // Setup driver and vehicle
        Driver driver = new Driver("D100", "Test Driver", "LIC-100");
        Vehicle vehicle = new Vehicle("V100", "Bike");
        system.addDriver(driver);
        system.addVehicle(vehicle);

        // Setup a customer and place an order
        Customer customer = new GuestCustomer("G100", "Guest");
        system.addCustomer(customer);

        Order order = system.placeOrder(customer, "1x Chicken Biryani", 250.0);

        // Simulate order is ready and in queue position 1
        order.markAsReady();
        order.setQueuePosition(1);

        // Assign delivery
        system.assignDelivery(order.getOrderNo(), driver.getId(), vehicle.getVehicleId());

        // Sanity checks before completion
        assertThat(order.getStatus()).isEqualTo("OUT_FOR_DELIVERY");
        assertThat(driver.isAvailable()).isFalse();
        assertThat(vehicle.isAvailable()).isFalse();

        // Driver completes delivery
        boolean done = system.driverCompleteDelivery(order.getOrderNo(), "LIC-100");
        assertThat(done).isTrue();

        // Post-conditions
        assertThat(order.getStatus()).isEqualTo("DELIVERED");
        assertThat(driver.isAvailable()).isTrue();
        assertThat(vehicle.isAvailable()).isTrue();
        assertThat(order.getQueuePosition()).isEqualTo(0);

        // Current serving order should reflect delivered count (deliveredCount + 1)
        assertThat(system.getCurrentServingOrder()).isEqualTo(2);
    }
}
