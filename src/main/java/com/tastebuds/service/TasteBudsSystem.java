package com.tastebuds.service;

import com.tastebuds.model.*;
import com.tastebuds.persistence.JAXBHandler;

import java.util.*;

public class TasteBudsSystem {
    private List<Customer> customers;
    private List<Order> orders;
    private List<Driver> drivers;
    private List<Chef> chefs;
    private List<Vehicle> vehicles;
    private List<Feedback> feedbacks;
    private int orderCounter;
    private int currentServingOrder;
    private JAXBHandler jaxbHandler;

    public TasteBudsSystem() {
        this.customers = new ArrayList<>();
        this.orders = new ArrayList<>();
        this.drivers = new ArrayList<>();
        this.chefs = new ArrayList<>();
        this.vehicles = new ArrayList<>();
        this.feedbacks = new ArrayList<>();
        this.orderCounter = 0;
        this.currentServingOrder = 1;
        this.jaxbHandler = new JAXBHandler();
    }

    private final Map<String, Double> menu = Map.ofEntries(
            Map.entry("Chicken Biryani", 250.0),
            Map.entry("Beef Steak", 650.0),
            Map.entry("Mixed Grill Platter", 1200.0),
            Map.entry("Veg Burger", 180.0),
            Map.entry("Chicken Burger", 220.0),
            Map.entry("Fried Rice", 200.0),
            Map.entry("Mutton Korma", 900.0),
            Map.entry("Green Salad", 80.0),
            Map.entry("Soft Drink (500ml)", 50.0),
            Map.entry("Chocolate Cake (slice)", 150.0)
    );

    public Map<String, Double> getMenu() {
        return menu;
    }

    public void addVehicle(Vehicle v) {
        this.vehicles.add(v);
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public Vehicle findVehicle(String id) {
        return vehicles.stream().filter(v -> v.getVehicleId().equals(id)).findFirst().orElse(null);
    }

    public boolean autoAssignDelivery(String orderNo) {
        Order order = findOrder(orderNo);
        if (order == null || !order.getStatus().equals("READY")) return false;

        // Find first available driver
        Driver d = drivers.stream().filter(Driver::isAvailable).findFirst().orElse(null);
        Vehicle v = vehicles.stream().filter(Vehicle::isAvailable).findFirst().orElse(null);

        if (d == null || v == null) return false;

        order.assignDelivery(d.getId(), v.getVehicleId());
        d.setAvailable(false);
        v.setAvailable(false);
        return true;
    }

    /**
     * Load all data from XML files using JAXB Unmarshaller
     */
    public void loadData() {
        System.out.println("Loading data...");

        customers = jaxbHandler.loadCustomers();
        orders = jaxbHandler.loadOrders();
        drivers = jaxbHandler.loadDrivers();
    chefs = jaxbHandler.loadChefs();
    vehicles = jaxbHandler.loadVehicles();
        feedbacks = jaxbHandler.loadFeedbacks();

        if (!orders.isEmpty()) {
            orderCounter = orders.stream()
                    .mapToInt(o -> Integer.parseInt(o.getOrderNo()))
                    .max()
                    .orElse(0);
        }

        System.out.println("Data loaded: Customers: " + customers.size() + ", Orders: " + orders.size() + ", Drivers: " + drivers.size() + ", Chefs: " + chefs.size() + ", Vehicles: " + vehicles.size() + ", Feedbacks: " + feedbacks.size());
    }

    public void saveData() {
        System.out.println("Saving data...");

        jaxbHandler.saveCustomers(customers);
        jaxbHandler.saveOrders(orders);
        jaxbHandler.saveDrivers(drivers);
    jaxbHandler.saveChefs(chefs);
    jaxbHandler.saveVehicles(vehicles);
        jaxbHandler.saveFeedbacks(feedbacks);

        System.out.println("All data saved.");
    }


    public Order placeOrder(Customer customer, String items, double billAmount) {
        orderCounter++;
        String orderNo = String.format("%03d", orderCounter);

        int queuePos = (int) orders.stream()
                .filter(o -> o.getStatus().equals("PLACED") || o.getStatus().equals("PREPARING"))
                .count() + 1;

        double discount = customer.calculateDiscount(billAmount);
        double finalBill = billAmount - discount;

        Order order = new Order(orderNo, customer.getId(), items, billAmount, finalBill, queuePos);
        orders.add(order);

        if (customer instanceof RegisteredCustomer) {
            ((RegisteredCustomer) customer).incrementOrders();
        }

        return order;
    }

    public void prepareOrder(String orderNo, OrderCategory category, String chefs, int time) {
        Order order = findOrder(orderNo);
        if (order != null) {
            order.markAsPreparing(category, chefs, time);

            new Thread(() -> {
                try {
                    Thread.sleep(2000);
                    order.markAsReady();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }


    public void assignDelivery(String orderNo, String driverId, String vehicle) {
        Order order = findOrder(orderNo);
        Driver driver = findDriver(driverId);

        Vehicle v = findVehicle(vehicle);

        if (order != null && driver != null) {
            order.assignDelivery(driverId, vehicle);
            driver.setAvailable(false);
            if (v != null) v.setAvailable(false);
        }
    }

    public boolean driverCheckout(String orderNo, String licenseNo) {
        Order order = findOrder(orderNo);
        if (order == null) return false;

        Driver driver = findDriver(order.getDriverId());
        if (driver == null) return false;

        return driver.verifyLicense(licenseNo);
    }

    public void submitFeedback(String orderNo, int rating, String comment) {
        Order order = findOrder(orderNo);
        if (order != null) {
            order.markAsDelivered();
            Feedback feedback = new Feedback(orderNo, rating, comment);
            feedbacks.add(feedback);

            // Free up driver
            Driver driver = findDriver(order.getDriverId());
            if (driver != null) {
                driver.setAvailable(true);
            }

            updateServingOrder();
        }
    }

    public boolean driverCompleteDelivery(String orderNo, String licenseNo) {
        Order order = findOrder(orderNo);
        if (order == null) return false;

        Driver driver = findDriver(order.getDriverId());
        if (driver == null) return false;

        if (!driver.verifyLicense(licenseNo)) return false;

        order.markAsDelivered();

        driver.setAvailable(true);
        Vehicle v = findVehicle(order.getVehicle());
        if (v != null) v.setAvailable(true);

        int removedPos = order.getQueuePosition();
        if (removedPos > 0) {
            for (Order o : orders) {
                if (o.getOrderNo().equals(orderNo)) {
                    o.setQueuePosition(0);
                    continue;
                }
                if (o.getQueuePosition() > removedPos) {
                    o.setQueuePosition(o.getQueuePosition() - 1);
                }
            }
        }

        updateServingOrder();
        return true;
    }


    public Customer findCustomer(String id) {
        return customers.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Order findOrder(String orderNo) {
        return orders.stream()
                .filter(o -> o.getOrderNo().equals(orderNo))
                .findFirst()
                .orElse(null);
    }

    public Driver findDriver(String id) {
        return drivers.stream()
                .filter(d -> d.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void addDriver(Driver driver) {
        drivers.add(driver);
    }

    public void addChef(Chef chef) {
        chefs.add(chef);
    }

    public List<Chef> getChefs() {
        return chefs;
    }

    public Chef findChef(String name) {
        return chefs.stream()
                .filter(c -> c.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public int getCurrentServingOrder() {
        return currentServingOrder;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }


    private void updateServingOrder() {
        long deliveredCount = orders.stream()
                .filter(o -> o.getStatus().equals("DELIVERED"))
                .count();
        currentServingOrder = (int) deliveredCount + 1;
    }

    public void displayQueue() {
        List<Order> pendingOrders = orders.stream()
                .filter(o -> !o.getStatus().equals("DELIVERED"))
                .sorted(Comparator.comparingInt(Order::getQueuePosition))
                .toList();

        if (pendingOrders.isEmpty()) {
            System.out.println("No orders in queue");
            return;
        }

        System.out.println("Current orders:");
        for (Order order : pendingOrders) {
            System.out.println("Order " + order.getOrderNo() + " | Status: " + order.getStatus() + " | Category: " + order.getCategory());
        }
    }

    public void displayAllCustomers() {
        if (customers.isEmpty()) {
            System.out.println("No customers registered");
            return;
        }

        System.out.println("Customers:");
        for (Customer customer : customers) {
            String ordersStr = customer instanceof RegisteredCustomer ?
                    String.valueOf(((RegisteredCustomer) customer).getOrdersThisMonth()) : "N/A";
            System.out.println(customer.getId() + " | " + customer.getName() + " | " + customer.getCustomerType() + " | Orders: " + ordersStr);
        }
    }

    public void displayAllDrivers() {
        if (drivers.isEmpty()) {
            System.out.println("No drivers registered");
            return;
        }

        System.out.println("Drivers:");
        for (Driver driver : drivers) {
            System.out.println(driver.getId() + " | " + driver.getName() + " | License: " + driver.getLicense().getLicenseNo() + " | Available: " + (driver.isAvailable() ? "Yes" : "No"));
        }
    }

    public void displayAllChefs() {
        if (chefs.isEmpty()) {
            System.out.println("No chefs registered");
            return;
        }

        System.out.println("Chefs:");
        for (Chef chef : chefs) {
            System.out.println(chef.getName() + " | Available: " + (chef.isAvailable() ? "Yes" : "No"));
        }
    }
}