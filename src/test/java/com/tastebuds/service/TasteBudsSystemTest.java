package com.tastebuds.service;

import com.tastebuds.model.*;
import com.tastebuds.model.Order;

import org.junit.jupiter.api.*;
import static org.assertj.core.api.Assertions.*;

/**
 * Integration tests for TasteBudsSystem
 */
class TasteBudsSystemTest {

    private TasteBudsSystem system;

    @BeforeEach
    void setUp() {
        system = new TasteBudsSystem();
        // Don't load data for tests - start fresh
    }

    @AfterEach
    void tearDown() {
        // Clean up test data
        system = null;
    }

    // ==================== PLACE ORDER TESTS ====================

    @Test
    @DisplayName("System should place order for registered customer with discount")
    void testPlaceOrderRegisteredCustomer() {
        RegisteredCustomer customer = new RegisteredCustomer("C001", "Samir", 5);
        system.addCustomer(customer);

        Order order = system.placeOrder(customer, "Pizza, Burger", 1000.0);

        assertThat(order).isNotNull();
        assertThat(order.getOrderNo()).isEqualTo("001");
        assertThat(order.getCustomerId()).isEqualTo("C001");
        assertThat(order.getBillAmount()).isEqualTo(1000.0);
        assertThat(order.getFinalBill()).isEqualTo(900.0); // 10% discount
        assertThat(order.getStatus()).isEqualTo("PLACED");
        assertThat(order.getQueuePosition()).isEqualTo(1);

        // Customer orders should increment
        assertThat(customer.getOrdersThisMonth()).isEqualTo(6);
    }

    @Test
    @DisplayName("System should place order for guest customer without discount")
    void testPlaceOrderGuestCustomer() {
        GuestCustomer customer = new GuestCustomer("G001", "Anonymous");

        Order order = system.placeOrder(customer, "Steak", 2000.0);

        assertThat(order).isNotNull();
        assertThat(order.getFinalBill()).isEqualTo(2000.0); // No discount
    }

    @Test
    @DisplayName("System should increment order counter correctly")
    void testOrderCounterIncrement() {
        RegisteredCustomer customer = new RegisteredCustomer("C001", "Samir");
        system.addCustomer(customer);

        Order order1 = system.placeOrder(customer, "Item1", 500.0);
        Order order2 = system.placeOrder(customer, "Item2", 600.0);
        Order order3 = system.placeOrder(customer, "Item3", 700.0);

        assertThat(order1.getOrderNo()).isEqualTo("001");
        assertThat(order2.getOrderNo()).isEqualTo("002");
        assertThat(order3.getOrderNo()).isEqualTo("003");
    }

    @Test
    @DisplayName("System should calculate queue position correctly")
    void testQueuePositionCalculation() {
        RegisteredCustomer customer = new RegisteredCustomer("C001", "Samir");
        system.addCustomer(customer);

        Order order1 = system.placeOrder(customer, "Item1", 500.0);
        Order order2 = system.placeOrder(customer, "Item2", 600.0);

        assertThat(order1.getQueuePosition()).isEqualTo(1);
        assertThat(order2.getQueuePosition()).isEqualTo(2);
    }

    // ==================== KITCHEN PREPARATION TESTS ====================

    @Test
    @DisplayName("System should prepare order correctly")
    void testPrepareOrder() throws InterruptedException {
        RegisteredCustomer customer = new RegisteredCustomer("C001", "Samir");
        system.addCustomer(customer);
        Order order = system.placeOrder(customer, "Pizza", 800.0);

        system.prepareOrder("001", OrderCategory.PRIORITY, "Chef A, Chef B", 30);

        assertThat(order.getStatus()).isEqualTo("PREPARING");
        assertThat(order.getCategory()).isEqualTo(OrderCategory.PRIORITY);
        assertThat(order.getAssignedChefs()).isEqualTo("Chef A, Chef B");
        assertThat(order.getEstimatedTime()).isEqualTo(30);

        // Wait for async ready status
        Thread.sleep(2500);
        assertThat(order.getStatus()).isEqualTo("READY");
    }

    // ==================== DELIVERY ASSIGNMENT TESTS ====================

    @Test
    @DisplayName("System should assign delivery correctly")
    void testAssignDelivery() {
        RegisteredCustomer customer = new RegisteredCustomer("C001", "Samir");
        system.addCustomer(customer);
        Order order = system.placeOrder(customer, "Burger", 500.0);
        order.markAsReady();

        Driver driver = new Driver("D001", "Rahim", "DL-123456");
        system.addDriver(driver);

        system.assignDelivery("001", "D001", "Bike-12");

        assertThat(order.getStatus()).isEqualTo("OUT_FOR_DELIVERY");
        assertThat(order.getDriverId()).isEqualTo("D001");
        assertThat(order.getVehicle()).isEqualTo("Bike-12");
        assertThat(driver.isAvailable()).isFalse();
    }

    // ==================== DRIVER CHECKOUT TESTS ====================

    @Test
    @DisplayName("System should verify driver license correctly")
    void testDriverCheckoutSuccess() {
        RegisteredCustomer customer = new RegisteredCustomer("C001", "Samir");
        system.addCustomer(customer);
        Order order = system.placeOrder(customer, "Pizza", 800.0);
        order.markAsReady();

        Driver driver = new Driver("D001", "Rahim", "DL-123456");
        system.addDriver(driver);
        system.assignDelivery("001", "D001", "Bike-12");

        boolean result = system.driverCheckout("001", "DL-123456");

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("System should reject invalid license")
    void testDriverCheckoutInvalidLicense() {
        RegisteredCustomer customer = new RegisteredCustomer("C001", "Samir");
        system.addCustomer(customer);
        Order order = system.placeOrder(customer, "Pizza", 800.0);
        order.markAsReady();

        Driver driver = new Driver("D001", "Rahim", "DL-123456");
        system.addDriver(driver);
        system.assignDelivery("001", "D001", "Bike-12");

        boolean result = system.driverCheckout("001", "WRONG-LICENSE");

        assertThat(result).isFalse();
    }

    // ==================== FEEDBACK TESTS ====================

    @Test
    @DisplayName("System should submit feedback and free driver")
    void testSubmitFeedback() {
        RegisteredCustomer customer = new RegisteredCustomer("C001", "Samir");
        system.addCustomer(customer);
        Order order = system.placeOrder(customer, "Pizza", 800.0);
        order.markAsReady();

        Driver driver = new Driver("D001", "Rahim", "DL-123456");
        system.addDriver(driver);
        system.assignDelivery("001", "D001", "Bike-12");

        assertThat(driver.isAvailable()).isFalse();

        system.submitFeedback("001", 5, "Excellent!");

        assertThat(order.getStatus()).isEqualTo("DELIVERED");
        assertThat(driver.isAvailable()).isTrue();
        assertThat(system.getFeedbacks()).hasSize(1);
        assertThat(system.getFeedbacks().get(0).getRating()).isEqualTo(5);
    }

    // ==================== FINDER TESTS ====================

    @Test
    @DisplayName("System should find customer by ID")
    void testFindCustomer() {
        RegisteredCustomer customer = new RegisteredCustomer("C001", "Samir");
        system.addCustomer(customer);

        Customer found = system.findCustomer("C001");

        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo("C001");
        assertThat(found.getName()).isEqualTo("Samir");
    }

    @Test
    @DisplayName("System should return null for non-existent customer")
    void testFindCustomerNotFound() {
        Customer found = system.findCustomer("NONEXISTENT");
        assertThat(found).isNull();
    }

    @Test
    @DisplayName("System should find order by number")
    void testFindOrder() {
        RegisteredCustomer customer = new RegisteredCustomer("C001", "Samir");
        system.addCustomer(customer);
        system.placeOrder(customer, "Pizza", 800.0);

        Order found = system.findOrder("001");

        assertThat(found).isNotNull();
        assertThat(found.getOrderNo()).isEqualTo("001");
    }

    @Test
    @DisplayName("System should find driver by ID")
    void testFindDriver() {
        Driver driver = new Driver("D001", "Rahim", "DL-123456");
        system.addDriver(driver);

        Driver found = system.findDriver("D001");

        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo("D001");
    }

    // ==================== SERVING ORDER TESTS ====================

    @Test
    @DisplayName("System should update current serving order after delivery")
    void testCurrentServingOrderUpdate() {
        RegisteredCustomer customer = new RegisteredCustomer("C001", "Samir");
        system.addCustomer(customer);

        assertThat(system.getCurrentServingOrder()).isEqualTo(1);

        Order order = system.placeOrder(customer, "Pizza", 800.0);
        order.markAsReady();
        Driver driver = new Driver("D001", "Rahim", "DL-123456");
        system.addDriver(driver);
        system.assignDelivery("001", "D001", "Bike-12");
        system.submitFeedback("001", 5, "Great!");

        assertThat(system.getCurrentServingOrder()).isEqualTo(2);
    }

    // ==================== FULL LIFECYCLE TEST ====================

    @Test
    @DisplayName("System should handle complete order lifecycle")
    void testCompleteOrderLifecycle() throws InterruptedException {
        // Setup
        RegisteredCustomer customer = new RegisteredCustomer("C001", "Samir", 5);
        system.addCustomer(customer);
        Driver driver = new Driver("D001", "Rahim", "DL-123456");
        system.addDriver(driver);

        // Place order
        Order order = system.placeOrder(customer, "Pizza, Burger, Coke", 1200.0);
        assertThat(order.getStatus()).isEqualTo("PLACED");
        assertThat(order.getFinalBill()).isEqualTo(1080.0); // 10% discount
        assertThat(customer.getOrdersThisMonth()).isEqualTo(6);

        // Kitchen preparation
        system.prepareOrder("001", OrderCategory.PRIORITY, "Chef A, Chef B", 25);
        assertThat(order.getStatus()).isEqualTo("PREPARING");

        // Wait for ready
        Thread.sleep(2500);
        assertThat(order.getStatus()).isEqualTo("READY");

        // Assign delivery
        system.assignDelivery("001", "D001", "Bike-12");
        assertThat(order.getStatus()).isEqualTo("OUT_FOR_DELIVERY");
        assertThat(driver.isAvailable()).isFalse();

        // Driver checkout
        boolean verified = system.driverCheckout("001", "DL-123456");
        assertThat(verified).isTrue();

        // Submit feedback
        system.submitFeedback("001", 5, "Perfect service!");
        assertThat(order.getStatus()).isEqualTo("DELIVERED");
        assertThat(driver.isAvailable()).isTrue();
        assertThat(system.getFeedbacks()).hasSize(1);
    }

    // ==================== PERSISTENCE TESTS ====================

    @Test
    @DisplayName("System should save and load data correctly")
    void testSaveAndLoadData() {
        // Add data
        RegisteredCustomer customer = new RegisteredCustomer("C001", "Samir", 5);
        system.addCustomer(customer);

        Driver driver = new Driver("D001", "Rahim", "DL-123456");
        system.addDriver(driver);

        Order order = system.placeOrder(customer, "Pizza", 800.0);

        // Save
        system.saveData();

        // Create new system and load
        TasteBudsSystem newSystem = new TasteBudsSystem();
        newSystem.loadData();

        // Verify data loaded
        assertThat(newSystem.getCustomers()).isNotEmpty();
        assertThat(newSystem.getDrivers()).isNotEmpty();
        assertThat(newSystem.getOrders()).isNotEmpty();
    }
}