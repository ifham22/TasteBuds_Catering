package com.tastebuds.model;
import jakarta.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "drivers")
@XmlAccessorType(XmlAccessType.FIELD)
public class DriverList {
    @XmlElement(name = "driver")
    private List<Driver> drivers;

    public DriverList() {
        this.drivers = new ArrayList<>();
    }

    public DriverList(List<Driver> drivers) {
        this.drivers = drivers;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

    public void addDriver(Driver driver) {
        this.drivers.add(driver);
    }
}
