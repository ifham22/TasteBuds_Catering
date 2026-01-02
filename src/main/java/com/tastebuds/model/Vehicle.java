package com.tastebuds.model;

import jakarta.xml.bind.annotation.*;


@XmlRootElement(name = "vehicle")
@XmlAccessorType(XmlAccessType.FIELD)
public class Vehicle {
    @XmlElement
    private String vehicleId;

    @XmlElement
    private String type;

    @XmlElement
    private boolean available;

    public Vehicle() {}

    public Vehicle(String vehicleId, String type) {
        this.vehicleId = vehicleId;
        this.type = type;
        this.available = true;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Vehicle{vehicleId='" + vehicleId + "', type='" + type +
                "', available=" + available + "}";
    }
}