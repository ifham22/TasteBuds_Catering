package com.tastebuds.model;

import jakarta.xml.bind.annotation.*;


@XmlRootElement(name = "registeredCustomer")
@XmlAccessorType(XmlAccessType.FIELD)
public class RegisteredCustomer extends Customer {
    @XmlElement
    private int ordersThisMonth;

    @XmlElement
    private double discount;

    public RegisteredCustomer() {}

    public RegisteredCustomer(String id, String name) {
        super(id, name);
        this.ordersThisMonth = 0;
        this.discount = 0;
    }

    public RegisteredCustomer(String id, String name, int ordersThisMonth) {
        super(id, name);
        this.ordersThisMonth = ordersThisMonth;
        calculateDiscountRate();
    }

    public int getOrdersThisMonth() {
        return ordersThisMonth;
    }

    public void setOrdersThisMonth(int ordersThisMonth) {
        this.ordersThisMonth = ordersThisMonth;
        calculateDiscountRate();
    }

    public void incrementOrders() {
        this.ordersThisMonth++;
        calculateDiscountRate();
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    private void calculateDiscountRate() {
        if (ordersThisMonth >= 10) {
            discount = 15;
        } else if (ordersThisMonth >= 5) {
            discount = 10;
        } else if (ordersThisMonth >= 3) {
            discount = 5;
        } else {
            discount = 0;
        }
    }

    @Override
    public double calculateDiscount(double totalBill) {
        return totalBill * (discount / 100.0);
    }

    @Override
    public String getCustomerType() {
        return "REGISTERED";
    }

    @Override
    public String toString() {
        return "RegisteredCustomer{id='" + id + "', name='" + name +
                "', orders=" + ordersThisMonth + ", discount=" + discount + "%}";
    }
}