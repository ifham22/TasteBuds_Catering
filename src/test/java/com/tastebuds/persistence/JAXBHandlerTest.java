package com.tastebuds.persistence;

import com.tastebuds.model.*;
import com.tastebuds.model.Order;

import org.junit.jupiter.api.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.*;

/**
 * Integration tests for JAXBHandler - XML Marshalling and Unmarshalling
 */
class JAXBHandlerTest {

    private JAXBHandler handler;
    private static final String TEST_DATA_DIR = "data/";

    @BeforeEach
    void setUp() {
        handler = new JAXBHandler();
        // Clean up test data before each test
        handler.clearAllData();
    }

    @AfterEach
    void tearDown() {
        // Clean up after tests
        handler.clearAllData();
    }

    // ==================== CUSTOMER TESTS ====================

    @Test
    @DisplayName("JAXBHandler should save and load registered customers")
    void testSaveAndLoadRegisteredCustomers() {
        // Create test customers
        List<Customer> customers = new ArrayList<>();
        customers.add(new RegisteredCustomer("C001", "Samir", 5));
        customers.add(new RegisteredCustomer("C002", "Fatima", 10));

        // Save
        handler.saveCustomers(customers);

        // Verify file exists
        File file = new File(TEST_DATA_DIR + "customers.xml");
        assertThat(file).exists();

        // Load
        List<Customer> loaded = handler.loadCustomers();

        // Verify
        assertThat(loaded).hasSize(2);
        assertThat(loaded.get(0).getId()).isEqualTo("C001");
        assertThat(loaded.get(0).getName()).isEqualTo("Samir");

        RegisteredCustomer rc1 = (RegisteredCustomer) loaded.get(0);
        assertThat(rc1.getOrdersThisMonth()).isEqualTo(5);
        assertThat(rc1.getDiscount()).isEqualTo(10.0);
    }

    @Test
    @DisplayName("JAXBHandler should save and load guest customers")
    void testSaveAndLoadGuestCustomers() {
        List<Customer> customers = new ArrayList<>();
        customers.add(new GuestCustomer("G001", "Anonymous"));

        handler.saveCustomers(customers);
        List<Customer> loaded = handler.loadCustomers();

        assertThat(loaded).hasSize(1);
        assertThat(loaded.get(0)).isInstanceOf(GuestCustomer.class);
        assertThat(loaded.get(0).getId()).isEqualTo("G001");
    }

    @Test
    @DisplayName("JAXBHandler should handle mixed customer types")
    void testSaveAndLoadMixedCustomers() {
        List<Customer> customers = new ArrayList<>();
        customers.add(new RegisteredCustomer("C001", "Rahim", 3));
        customers.add(new GuestCustomer("G001", "Guest1"));
        customers.add(new RegisteredCustomer("C002", "Sara", 7));

        handler.saveCustomers(customers);
        List<Customer> loaded = handler.loadCustomers();

        assertThat(loaded).hasSize(3);
        assertThat(loaded.get(0)).isInstanceOf(RegisteredCustomer.class);
        assertThat(loaded.get(1)).isInstanceOf(GuestCustomer.class);
        assertThat(loaded.get(2)).isInstanceOf(RegisteredCustomer.class);
    }

    @Test
    @DisplayName("JAXBHandler should handle empty customer list")
    void testSaveAndLoadEmptyCustomers() {
        List<Customer> customers = new ArrayList<>();
        handler.saveCustomers(customers);

        List<Customer> loaded = handler.loadCustomers();
        assertThat(loaded).isEmpty();
    }

    // ==================== ORDER TESTS ====================

    @Test
    @DisplayName("JAXBHandler should save and load orders")
    void testSaveAndLoadOrders() {
        List<Order> orders = new ArrayList<>();
        Order order1 = new Order("001", "C001", "Pizza, Burger", 1000.0, 900.0, 1);
        order1.markAsPreparing(OrderCategory.PRIORITY, "Chef A", 30);
        orders.add(order1);

        handler.saveOrders(orders);

        File file = new File(TEST_DATA_DIR + "orders.xml");
        assertThat(file).exists();

        List<Order> loaded = handler.loadOrders();

        assertThat(loaded).hasSize(1);
        assertThat(loaded.get(0).getOrderNo()).isEqualTo("001");
        assertThat(loaded.get(0).getCustomerId()).isEqualTo("C001");
        assertThat(loaded.get(0).getStatus()).isEqualTo("PREPARING");
        assertThat(loaded.get(0).getCategory()).isEqualTo(OrderCategory.PRIORITY);
        assertThat(loaded.get(0).getAssignedChefs()).isEqualTo("Chef A");
    }

    @Test
    @DisplayName("JAXBHandler should preserve order lifecycle")
    void testOrderLifecyclePersistence() {
        List<Order> orders = new ArrayList<>();
        Order order = new Order("002", "C002", "Steak", 2000.0, 1800.0, 1);
        order.markAsPreparing(OrderCategory.NORMAL, "Chef B", 45);
        order.markAsReady();
        order.assignDelivery("D001", "Car-5");
        orders.add(order);

        handler.saveOrders(orders);
        List<Order> loaded = handler.loadOrders();

        assertThat(loaded.get(0).getStatus()).isEqualTo("OUT_FOR_DELIVERY");
        assertThat(loaded.get(0).getDriverId()).isEqualTo("D001");
        assertThat(loaded.get(0).getVehicle()).isEqualTo("Car-5");
    }

    // ==================== DRIVER TESTS ====================

    @Test
    @DisplayName("JAXBHandler should save and load drivers")
    void testSaveAndLoadDrivers() {
        List<Driver> drivers = new ArrayList<>();
        drivers.add(new Driver("D001", "Rahim", "DL-123456"));
        drivers.add(new Driver("D002", "Karim", "DL-789012"));

        handler.saveDrivers(drivers);

        File file = new File(TEST_DATA_DIR + "drivers.xml");
        assertThat(file).exists();

        List<Driver> loaded = handler.loadDrivers();

        assertThat(loaded).hasSize(2);
        assertThat(loaded.get(0).getId()).isEqualTo("D001");
        assertThat(loaded.get(0).getName()).isEqualTo("Rahim");
        assertThat(loaded.get(0).getLicense().getLicenseNo()).isEqualTo("DL-123456");
    }

    @Test
    @DisplayName("JAXBHandler should preserve driver license information")
    void testDriverLicensePersistence() {
        List<Driver> drivers = new ArrayList<>();
        Driver driver = new Driver("D003", "Hassan", "DL-555555");
        driver.setAvailable(false);
        drivers.add(driver);

        handler.saveDrivers(drivers);
        List<Driver> loaded = handler.loadDrivers();

        assertThat(loaded.get(0).getLicense()).isNotNull();
        assertThat(loaded.get(0).getLicense().getLicenseNo()).isEqualTo("DL-555555");
        assertThat(loaded.get(0).getLicense().isValid()).isTrue();
    }

    // ==================== FEEDBACK TESTS ====================

    @Test
    @DisplayName("JAXBHandler should save and load feedbacks")
    void testSaveAndLoadFeedbacks() {
        List<Feedback> feedbacks = new ArrayList<>();
        feedbacks.add(new Feedback("001", 5, "Excellent!"));
        feedbacks.add(new Feedback("002", 3, "Average"));

        handler.saveFeedbacks(feedbacks);

        File file = new File(TEST_DATA_DIR + "feedback.xml");
        assertThat(file).exists();

        List<Feedback> loaded = handler.loadFeedbacks();

        assertThat(loaded).hasSize(2);
        assertThat(loaded.get(0).getOrderNo()).isEqualTo("001");
        assertThat(loaded.get(0).getRating()).isEqualTo(5);
        assertThat(loaded.get(0).getComment()).isEqualTo("Excellent!");
    }

    @Test
    @DisplayName("JAXBHandler should handle feedback with special characters")
    void testFeedbackWithSpecialCharacters() {
        List<Feedback> feedbacks = new ArrayList<>();
        feedbacks.add(new Feedback("003", 4, "Good food & fast delivery!"));

        handler.saveFeedbacks(feedbacks);
        List<Feedback> loaded = handler.loadFeedbacks();

        assertThat(loaded.get(0).getComment()).isEqualTo("Good food & fast delivery!");
    }

    // ==================== INTEGRATION TESTS ====================

    @Test
    @DisplayName("JAXBHandler should handle complete system data")
    void testCompleteSystemDataPersistence() {
        // Create complete system data
        List<Customer> customers = new ArrayList<>();
        customers.add(new RegisteredCustomer("C001", "Samir", 5));

        List<Order> orders = new ArrayList<>();
        orders.add(new Order("001", "C001", "Pizza", 1000.0, 900.0, 1));

        List<Driver> drivers = new ArrayList<>();
        drivers.add(new Driver("D001", "Rahim", "DL-123456"));

        List<Feedback> feedbacks = new ArrayList<>();
        feedbacks.add(new Feedback("001", 5, "Great!"));

        // Save all
        handler.saveCustomers(customers);
        handler.saveOrders(orders);
        handler.saveDrivers(drivers);
        handler.saveFeedbacks(feedbacks);

        // Load all
        List<Customer> loadedCustomers = handler.loadCustomers();
        List<Order> loadedOrders = handler.loadOrders();
        List<Driver> loadedDrivers = handler.loadDrivers();
        List<Feedback> loadedFeedbacks = handler.loadFeedbacks();

        // Verify all
        assertThat(loadedCustomers).hasSize(1);
        assertThat(loadedOrders).hasSize(1);
        assertThat(loadedDrivers).hasSize(1);
        assertThat(loadedFeedbacks).hasSize(1);
    }

    @Test
    @DisplayName("JAXBHandler should handle non-existent files gracefully")
    void testLoadNonExistentFiles() {
        handler.clearAllData();

        List<Customer> customers = handler.loadCustomers();
        List<Order> orders = handler.loadOrders();
        List<Driver> drivers = handler.loadDrivers();
        List<Feedback> feedbacks = handler.loadFeedbacks();

        assertThat(customers).isEmpty();
        assertThat(orders).isEmpty();
        assertThat(drivers).isEmpty();
        assertThat(feedbacks).isEmpty();
    }
}