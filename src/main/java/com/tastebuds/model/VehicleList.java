package com.tastebuds.model;

import jakarta.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "vehicles")
@XmlAccessorType(XmlAccessType.FIELD)
public class VehicleList {

    @XmlElement(name = "vehicle")
    private List<Vehicle> vehicles;

    public VehicleList() {
        this.vehicles = new ArrayList<>();
    }

    public VehicleList(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public void addVehicle(Vehicle vehicle) {
        this.vehicles.add(vehicle);
    }
}
