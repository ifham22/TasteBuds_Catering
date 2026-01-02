package com.tastebuds.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.assertj.core.api.Assertions.*;

/**
 * Unit tests for Customer classes
 */
class CustomerTest {

    @Test
    @DisplayName("RegisteredCustomer should calculate discount correctly for 3+ orders")
    void testRegisteredCustomerDiscount3Orders() {
        RegisteredCustomer customer = new RegisteredCustomer("C001", "Samir", 3);

        assertThat(customer.getId()).isEqualTo("C001");
        assertThat(customer.getName()).isEqualTo("Samir");
        assertThat(customer.getOrdersThisMonth()).isEqualTo(3);
        assertThat(customer.getDiscount()).isEqualTo(5.0);
        assertThat(customer.calculateDiscount(1000)).isEqualTo(50.0);
    }

    @Test
    @DisplayName("RegisteredCustomer should calculate discount correctly for 5+ orders")
    void testRegisteredCustomerDiscount5Orders() {
        RegisteredCustomer customer = new RegisteredCustomer("C002", "Rahim", 5);

        assertThat(customer.getDiscount()).isEqualTo(10.0);
        assertThat(customer.calculateDiscount(1000)).isEqualTo(100.0);
    }

    @Test
    @DisplayName("RegisteredCustomer should calculate discount correctly for 10+ orders")
    void testRegisteredCustomerDiscount10Orders() {
        RegisteredCustomer customer = new RegisteredCustomer("C003", "Fatima", 10);

        assertThat(customer.getDiscount()).isEqualTo(15.0);
        assertThat(customer.calculateDiscount(1000)).isEqualTo(150.0);
    }

    @Test
    @DisplayName("RegisteredCustomer should have no discount for less than 3 orders")
    void testRegisteredCustomerNoDiscount() {
        RegisteredCustomer customer = new RegisteredCustomer("C004", "Ahmed");

        assertThat(customer.getOrdersThisMonth()).isEqualTo(0);
        assertThat(customer.getDiscount()).isEqualTo(0.0);
        assertThat(customer.calculateDiscount(1000)).isEqualTo(0.0);
    }

    @Test
    @DisplayName("RegisteredCustomer should increment orders correctly")
    void testRegisteredCustomerIncrementOrders() {
        RegisteredCustomer customer = new RegisteredCustomer("C005", "Nadia");

        assertThat(customer.getOrdersThisMonth()).isEqualTo(0);
        assertThat(customer.getDiscount()).isEqualTo(0.0);

        customer.incrementOrders();
        customer.incrementOrders();
        customer.incrementOrders();

        assertThat(customer.getOrdersThisMonth()).isEqualTo(3);
        assertThat(customer.getDiscount()).isEqualTo(5.0);
    }

    @Test
    @DisplayName("RegisteredCustomer should update discount when orders increase")
    void testRegisteredCustomerDiscountUpdate() {
        RegisteredCustomer customer = new RegisteredCustomer("C006", "Karim");

        // Start with no discount
        assertThat(customer.getDiscount()).isEqualTo(0.0);

        // Reach 5 orders
        customer.setOrdersThisMonth(5);
        assertThat(customer.getDiscount()).isEqualTo(10.0);

        // Reach 10 orders
        customer.setOrdersThisMonth(10);
        assertThat(customer.getDiscount()).isEqualTo(15.0);
    }

    @Test
    @DisplayName("GuestCustomer should have no discount")
    void testGuestCustomerNoDiscount() {
        GuestCustomer customer = new GuestCustomer("G001", "Anonymous");

        assertThat(customer.getId()).isEqualTo("G001");
        assertThat(customer.getName()).isEqualTo("Anonymous");
        assertThat(customer.getCustomerType()).isEqualTo("GUEST");
        assertThat(customer.calculateDiscount(1000)).isEqualTo(0.0);
    }

    @Test
    @DisplayName("RegisteredCustomer should return correct customer type")
    void testRegisteredCustomerType() {
        RegisteredCustomer customer = new RegisteredCustomer("C007", "Sara");
        assertThat(customer.getCustomerType()).isEqualTo("REGISTERED");
    }

    @Test
    @DisplayName("GuestCustomer should return correct customer type")
    void testGuestCustomerType() {
        GuestCustomer customer = new GuestCustomer("G002", "John");
        assertThat(customer.getCustomerType()).isEqualTo("GUEST");
    }

    @Test
    @DisplayName("Customer discount calculation should be polymorphic")
    void testPolymorphicDiscountCalculation() {
        Customer registered = new RegisteredCustomer("C008", "Ali", 5);
        Customer guest = new GuestCustomer("G003", "Bob");

        // Same method call, different behavior
        double registeredDiscount = registered.calculateDiscount(1000);
        double guestDiscount = guest.calculateDiscount(1000);

        assertThat(registeredDiscount).isEqualTo(100.0);
        assertThat(guestDiscount).isEqualTo(0.0);
    }

    @Test
    @DisplayName("RegisteredCustomer toString should include all details")
    void testRegisteredCustomerToString() {
        RegisteredCustomer customer = new RegisteredCustomer("C009", "Hassan", 5);
        String result = customer.toString();

        assertThat(result).contains("C009");
        assertThat(result).contains("Hassan");
        assertThat(result).contains("5");
        assertThat(result).contains("10.0");
    }

    @Test
    @DisplayName("GuestCustomer toString should include basic details")
    void testGuestCustomerToString() {
        GuestCustomer customer = new GuestCustomer("G004", "Maria");
        String result = customer.toString();

        assertThat(result).contains("G004");
        assertThat(result).contains("Maria");
    }
}