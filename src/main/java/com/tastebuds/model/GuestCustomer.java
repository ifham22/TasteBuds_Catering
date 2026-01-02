package com.tastebuds.model;

import jakarta.xml.bind.annotation.*;


@XmlRootElement(name = "guestCustomer")
@XmlAccessorType(XmlAccessType.FIELD)
public class GuestCustomer extends Customer {

    public GuestCustomer() {}

    public GuestCustomer(String id, String name) {
        super(id, name);
    }

    @Override
    public double calculateDiscount(double totalBill) {
        return 0;
    }

    @Override
    public String getCustomerType() {
        return "GUEST";
    }

    @Override
    public String toString() {
        return "GuestCustomer{id='" + id + "', name='" + name + "'}";
    }
}