package com.tastebuds.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.assertj.core.api.Assertions.*;


class DeliveryManagerTest {

    private DeliveryManager manager;
    private Order order;
    private Driver driver;

    @BeforeEach
    void setUp() {
        manager = new DeliveryManager("Hassan Ahmed");
        order = new Order("001", "C001", "Pizza", 1000.0, 900.0, 1);
        order.markAsReady();
        driver = new Driver("D001", "Rahim", "DL-123456");
    }

    @Test
    @DisplayName("DeliveryManager should assign delivery to available driver")
    void testAssignDeliverySuccess() {
        manager.assignDelivery(order, driver, "Bike-12");

        assertThat(order.getStatus()).isEqualTo("OUT_FOR_DELIVERY");
        assertThat(order.getDriverId()).isEqualTo("D001");
        assertThat(order.getVehicle()).isEqualTo("Bike-12");
        assertThat(driver.isAvailable()).isFalse();
    }

    @Test
    @DisplayName("DeliveryManager should handle unavailable driver")
    void testAssignDeliveryUnavailableDriver() {
        driver.setAvailable(false);
        String originalStatus = order.getStatus();

        manager.assignDelivery(order, driver, "Bike-12");

        // Order should remain in original status
        assertThat(order.getStatus()).isEqualTo(originalStatus);
    }

    @Test
    @DisplayName("DeliveryManager should complete delivery correctly")
    void testCompleteDelivery() {
        manager.assignDelivery(order, driver, "Bike-12");
        assertThat(driver.isAvailable()).isFalse();

        manager.completeDelivery(order, driver);

        assertThat(order.getStatus()).isEqualTo("DELIVERED");
        assertThat(driver.isAvailable()).isTrue();
    }

    @Test
    @DisplayName("DeliveryManager should handle priority orders")
    void testPriorityOrderAssignment() {
        order.setCategory(OrderCategory.PRIORITY);

        manager.assignDelivery(order, driver, "Car-5");

        assertThat(order.getStatus()).isEqualTo("OUT_FOR_DELIVERY");
        assertThat(order.getCategory()).isEqualTo(OrderCategory.PRIORITY);
    }

    @Test
    @DisplayName("DeliveryManager should handle normal orders")
    void testNormalOrderAssignment() {
        order.setCategory(OrderCategory.NORMAL);

        manager.assignDelivery(order, driver, "Bike-3");

        assertThat(order.getStatus()).isEqualTo("OUT_FOR_DELIVERY");
        assertThat(order.getCategory()).isEqualTo(OrderCategory.NORMAL);
    }

    @Test
    @DisplayName("DeliveryManager toString should work")
    void testDeliveryManagerToString() {
        String result = manager.toString();
        assertThat(result).contains("Hassan Ahmed");
    }
}