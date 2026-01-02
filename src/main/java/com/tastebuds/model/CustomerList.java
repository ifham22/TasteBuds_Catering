package com.tastebuds.model;

import jakarta.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerList {

    @XmlElements({
            @XmlElement(name = "registeredCustomer", type = RegisteredCustomer.class),
            @XmlElement(name = "guestCustomer", type = GuestCustomer.class)
    })
    private List<Customer> customers;

    public CustomerList() {
        this.customers = new ArrayList<>();
    }

    public CustomerList(List<Customer> customers) {
        this.customers = customers;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public void addCustomer(Customer customer) {
        this.customers.add(customer);
    }
}