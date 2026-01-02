package com.tastebuds.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.assertj.core.api.Assertions.*;

/**
 * Unit tests for Order class
 */
class OrderTest {

    private Order order;

    @BeforeEach
    void setUp() {
        order = new Order("001", "C001", "Pizza, Burger", 1000.0, 900.0, 1);
    }

    @Test
    @DisplayName("Order should be created with correct initial values")
    void testOrderCreation() {
        assertThat(order.getOrderNo()).isEqualTo("001");
        assertThat(order.getCustomerId()).isEqualTo("C001");
        assertThat(order.getItems()).isEqualTo("Pizza, Burger");
        assertThat(order.getBillAmount()).isEqualTo(1000.0);
        assertThat(order.getFinalBill()).isEqualTo(900.0);
        assertThat(order.getQueuePosition()).isEqualTo(1);
        assertThat(order.getStatus()).isEqualTo("PLACED");
        assertThat(order.getCategory()).isEqualTo(OrderCategory.NORMAL);
    }

    @Test
    @DisplayName("Order should mark as preparing correctly")
    void testMarkAsPreparing() {
        order.markAsPreparing(OrderCategory.PRIORITY, "Chef A, Chef B", 30);

        assertThat(order.getStatus()).isEqualTo("PREPARING");
        assertThat(order.getCategory()).isEqualTo(OrderCategory.PRIORITY);
        assertThat(order.getAssignedChefs()).isEqualTo("Chef A, Chef B");
        assertThat(order.getEstimatedTime()).isEqualTo(30);
    }

    @Test
    @DisplayName("Order should mark as ready correctly")
    void testMarkAsReady() {
        order.markAsPreparing(OrderCategory.NORMAL, "Chef C", 20);
        order.markAsReady();

        assertThat(order.getStatus()).isEqualTo("READY");
    }

    @Test
    @DisplayName("Order should assign delivery correctly")
    void testAssignDelivery() {
        order.markAsReady();
        order.assignDelivery("D001", "Bike-12");

        assertThat(order.getStatus()).isEqualTo("OUT_FOR_DELIVERY");
        assertThat(order.getDriverId()).isEqualTo("D001");
        assertThat(order.getVehicle()).isEqualTo("Bike-12");
    }

    @Test
    @DisplayName("Order should mark as delivered correctly")
    void testMarkAsDelivered() {
        order.markAsReady();
        order.assignDelivery("D001", "Bike-12");
        order.markAsDelivered();

        assertThat(order.getStatus()).isEqualTo("DELIVERED");
    }

    @Test
    @DisplayName("Order category should be settable")
    void testSetCategory() {
        assertThat(order.getCategory()).isEqualTo(OrderCategory.NORMAL);

        order.setCategory(OrderCategory.PRIORITY);
        assertThat(order.getCategory()).isEqualTo(OrderCategory.PRIORITY);
    }

    @Test
    @DisplayName("Order should handle full lifecycle")
    void testFullOrderLifecycle() {
        // Initial state
        assertThat(order.getStatus()).isEqualTo("PLACED");

        // Kitchen preparation
        order.markAsPreparing(OrderCategory.PRIORITY, "Chef A", 25);
        assertThat(order.getStatus()).isEqualTo("PREPARING");
        assertThat(order.getCategory()).isEqualTo(OrderCategory.PRIORITY);

        // Ready for delivery
        order.markAsReady();
        assertThat(order.getStatus()).isEqualTo("READY");

        // Out for delivery
        order.assignDelivery("D001", "Car-5");
        assertThat(order.getStatus()).isEqualTo("OUT_FOR_DELIVERY");
        assertThat(order.getDriverId()).isEqualTo("D001");

        // Delivered
        order.markAsDelivered();
        assertThat(order.getStatus()).isEqualTo("DELIVERED");
    }

    @Test
    @DisplayName("Order toString should contain key information")
    void testOrderToString() {
        String result = order.toString();

        assertThat(result).contains("001");
        assertThat(result).contains("C001");
        assertThat(result).contains("PLACED");
        assertThat(result).contains("NORMAL");
    }

    @Test
    @DisplayName("Order should handle priority orders differently")
    void testPriorityOrderHandling() {
        Order priorityOrder = new Order("002", "C002", "Steak", 2000.0, 1800.0, 2);
        priorityOrder.markAsPreparing(OrderCategory.PRIORITY, "Chef A, Chef B, Chef C", 15);

        assertThat(priorityOrder.getCategory()).isEqualTo(OrderCategory.PRIORITY);
        assertThat(priorityOrder.getEstimatedTime()).isLessThan(30);
    }

    @Test
    @DisplayName("Order setters should work correctly")
    void testOrderSetters() {
        order.setOrderNo("999");
        order.setCustomerId("C999");
        order.setItems("New Items");
        order.setBillAmount(500.0);
        order.setFinalBill(450.0);
        order.setQueuePosition(10);

        assertThat(order.getOrderNo()).isEqualTo("999");
        assertThat(order.getCustomerId()).isEqualTo("C999");
        assertThat(order.getItems()).isEqualTo("New Items");
        assertThat(order.getBillAmount()).isEqualTo(500.0);
        assertThat(order.getFinalBill()).isEqualTo(450.0);
        assertThat(order.getQueuePosition()).isEqualTo(10);
    }
}