package com.tastebuds.model;

import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "customer")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({RegisteredCustomer.class, GuestCustomer.class})
public abstract class Customer {
    @XmlElement(required = true)
    protected String id;

    @XmlElement(required = true)
    protected String name;


    public Customer() {}

    public Customer(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract double calculateDiscount(double totalBill);
    public abstract String getCustomerType();
}