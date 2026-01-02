package com.tastebuds.model;

import jakarta.xml.bind.annotation.*;


@XmlRootElement(name = "headChef")
@XmlAccessorType(XmlAccessType.FIELD)
public class HeadChef {
    @XmlElement
    private String name;

    public HeadChef() {}

    public HeadChef(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void prepareOrder(Order order, OrderCategory category,
                             String assignedChefs, int estimatedTime) {
        order.markAsPreparing(category, assignedChefs, estimatedTime);
        System.out.println("Order assigned to " + assignedChefs);
    }

    public void markOrderReady(Order order) {
        order.markAsReady();
        System.out.println("Order " + order.getOrderNo() + " is ready.");
    }

    @Override
    public String toString() {
        return "HeadChef{name='" + name + "'}";
    }
}