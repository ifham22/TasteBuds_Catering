package com.tastebuds.model;

import jakarta.xml.bind.annotation.*;


@XmlRootElement(name = "deliveryManager")
@XmlAccessorType(XmlAccessType.FIELD)
public class DeliveryManager {
    @XmlElement
    private String name;

    public DeliveryManager() {}

    public DeliveryManager(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void assignDelivery(Order order, Driver driver, String vehicle) {
        if (!driver.isAvailable()) {
            System.out.println("Driver not available.");
            return;
        }

        order.assignDelivery(driver.getId(), vehicle);
        driver.setAvailable(false);

        if (order.getCategory() == OrderCategory.PRIORITY) {
            System.out.println("Priority delivery assigned.");
        } else {
            System.out.println("Delivery assigned.");
        }
    }

    public void completeDelivery(Order order, Driver driver) {
        order.markAsDelivered();
        driver.setAvailable(true);
        System.out.println("Delivery completed for Order " + order.getOrderNo() + ".");
    }

    @Override
    public String toString() {
        return "DeliveryManager{name='" + name + "'}";
    }
}