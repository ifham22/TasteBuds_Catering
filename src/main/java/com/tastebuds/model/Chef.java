package com.tastebuds.model;

import jakarta.xml.bind.annotation.*;


@XmlRootElement(name = "chef")
@XmlAccessorType(XmlAccessType.FIELD)
public class Chef {
    @XmlElement
    private String name;

    @XmlElement
    private boolean available;

    public Chef() {}

    public Chef(String name) {
        this.name = name;
        this.available = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Chef{name='" + name + "', available=" + available + "}";
    }
}