package com.tastebuds.persistence;

import com.tastebuds.model.*;
import jakarta.xml.bind.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JAXBHandler {
    private static final String DATA_DIR = "data/";
    private static final String CUSTOMERS_FILE = DATA_DIR + "customers.xml";
    private static final String ORDERS_FILE = DATA_DIR + "orders.xml";
    private static final String DRIVERS_FILE = DATA_DIR + "drivers.xml";
    private static final String CHEFS_FILE = DATA_DIR + "chefs.xml";
    private static final String VEHICLES_FILE = DATA_DIR + "vehicles.xml";
    private static final String FEEDBACK_FILE = DATA_DIR + "feedback.xml";

    public JAXBHandler() {

        File dataDir = new File(DATA_DIR);
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
    }

    public void saveCustomers(List<Customer> customers) {
        try {
            JAXBContext context = JAXBContext.newInstance(CustomerList.class);
            Marshaller marshaller = context.createMarshaller();


            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

            CustomerList customerList = new CustomerList(customers);
            marshaller.marshal(customerList, new File(CUSTOMERS_FILE));

            System.out.println("Saved customers to " + CUSTOMERS_FILE);
        } catch (JAXBException e) {
            System.err.println("Error saving customers: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Customer> loadCustomers() {
        try {
            File file = new File(CUSTOMERS_FILE);
            if (!file.exists()) {
                System.out.println("No customer data found.");
                return new ArrayList<>();
            }

            JAXBContext context = JAXBContext.newInstance(CustomerList.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            CustomerList customerList = (CustomerList) unmarshaller.unmarshal(file);

            System.out.println("Loaded " + customerList.getCustomers().size() + " customers");
            return customerList.getCustomers();
        } catch (JAXBException e) {
            System.err.println("Error loading customers: " + e.getMessage());
            return new ArrayList<>();
        }
    }


    public void saveOrders(List<Order> orders) {
        try {
            JAXBContext context = JAXBContext.newInstance(OrderList.class);
            Marshaller marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

            OrderList orderList = new OrderList(orders);
            marshaller.marshal(orderList, new File(ORDERS_FILE));

            System.out.println("Saved orders to " + ORDERS_FILE);
        } catch (JAXBException e) {
            System.err.println("Error saving orders: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Order> loadOrders() {
        try {
            File file = new File(ORDERS_FILE);
            if (!file.exists()) {
                System.out.println("No order data found.");
                return new ArrayList<>();
            }

            JAXBContext context = JAXBContext.newInstance(OrderList.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            OrderList orderList = (OrderList) unmarshaller.unmarshal(file);

            System.out.println("Loaded " + orderList.getOrders().size() + " orders");
            return orderList.getOrders();
        } catch (JAXBException e) {
            System.err.println("Error loading orders: " + e.getMessage());
            return new ArrayList<>();
        }
    }


    public void saveDrivers(List<Driver> drivers) {
        try {
            JAXBContext context = JAXBContext.newInstance(DriverList.class);
            Marshaller marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

            DriverList driverList = new DriverList(drivers);
            marshaller.marshal(driverList, new File(DRIVERS_FILE));

            System.out.println("Saved drivers to " + DRIVERS_FILE);
        } catch (JAXBException e) {
            System.err.println("Error saving drivers: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Driver> loadDrivers() {
        try {
            File file = new File(DRIVERS_FILE);
            if (!file.exists()) {
                System.out.println("No driver data found.");
                return new ArrayList<>();
            }

            JAXBContext context = JAXBContext.newInstance(DriverList.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            DriverList driverList = (DriverList) unmarshaller.unmarshal(file);

            System.out.println("Loaded " + driverList.getDrivers().size() + " drivers");
            return driverList.getDrivers();
        } catch (JAXBException e) {
            System.err.println("Error loading drivers: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void saveFeedbacks(List<Feedback> feedbacks) {
        try {
            JAXBContext context = JAXBContext.newInstance(FeedbackList.class);
            Marshaller marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

            FeedbackList feedbackList = new FeedbackList(feedbacks);
            marshaller.marshal(feedbackList, new File(FEEDBACK_FILE));

            System.out.println("Saved feedbacks to " + FEEDBACK_FILE);
        } catch (JAXBException e) {
            System.err.println("Error saving feedbacks: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public List<Feedback> loadFeedbacks() {
        try {
            File file = new File(FEEDBACK_FILE);
            if (!file.exists()) {
                System.out.println("No feedback data found.");
                return new ArrayList<>();
            }

            JAXBContext context = JAXBContext.newInstance(FeedbackList.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            FeedbackList feedbackList = (FeedbackList) unmarshaller.unmarshal(file);

            System.out.println("Loaded " + feedbackList.getFeedbacks().size() + " feedbacks");
            return feedbackList.getFeedbacks();
        } catch (JAXBException e) {
            System.err.println("Error loading feedbacks: " + e.getMessage());
            return new ArrayList<>();
        }
    }


    public void saveChefs(List<Chef> chefs) {
        try {
            JAXBContext context = JAXBContext.newInstance(ChefList.class);
            Marshaller marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

            ChefList chefList = new ChefList(chefs);
            marshaller.marshal(chefList, new File(CHEFS_FILE));

            System.out.println("Saved chefs to " + CHEFS_FILE);
        } catch (JAXBException e) {
            System.err.println("Error saving chefs: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Chef> loadChefs() {
        try {
            File file = new File(CHEFS_FILE);
            if (!file.exists()) {
                System.out.println("No chef data found.");
                return new ArrayList<>();
            }

            JAXBContext context = JAXBContext.newInstance(ChefList.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            ChefList chefList = (ChefList) unmarshaller.unmarshal(file);

            System.out.println("Loaded " + chefList.getChefs().size() + " chefs");
            return chefList.getChefs();
        } catch (JAXBException e) {
            System.err.println("Error loading chefs: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void saveVehicles(List<Vehicle> vehicles) {
        try {
            JAXBContext context = JAXBContext.newInstance(VehicleList.class);
            Marshaller marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

            VehicleList vehicleList = new VehicleList(vehicles);
            marshaller.marshal(vehicleList, new File(VEHICLES_FILE));

            System.out.println("Saved vehicles to " + VEHICLES_FILE);
        } catch (JAXBException e) {
            System.err.println("Error saving vehicles: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Vehicle> loadVehicles() {
        try {
            File file = new File(VEHICLES_FILE);
            if (!file.exists()) {
                System.out.println("No vehicle data found.");
                return new ArrayList<>();
            }

            JAXBContext context = JAXBContext.newInstance(VehicleList.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            VehicleList vehicleList = (VehicleList) unmarshaller.unmarshal(file);

            System.out.println("Loaded " + vehicleList.getVehicles().size() + " vehicles");
            return vehicleList.getVehicles();
        } catch (JAXBException e) {
            System.err.println("Error loading vehicles: " + e.getMessage());
            return new ArrayList<>();
        }
    }


    public void clearAllData() {
        deleteFile(CUSTOMERS_FILE);
        deleteFile(ORDERS_FILE);
        deleteFile(DRIVERS_FILE);
        deleteFile(CHEFS_FILE);
        deleteFile(VEHICLES_FILE);
        deleteFile(FEEDBACK_FILE);
        System.out.println("All data files cleared");
    }

    private void deleteFile(String filename) {
        File file = new File(filename);
        if (file.exists()) {
            file.delete();
        }
    }
}