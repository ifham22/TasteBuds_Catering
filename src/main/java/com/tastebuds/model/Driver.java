package com.tastebuds.model;

import jakarta.xml.bind.annotation.*;


@XmlRootElement(name = "driver")
@XmlAccessorType(XmlAccessType.FIELD)
public class Driver {
    @XmlElement(required = true)
    private String id;

    @XmlElement(required = true)
    private String name;

    @XmlElement(required = true)
    private DrivingLicense license;

    @XmlElement
    private boolean available;

    public Driver() {}

    public Driver(String id, String name, String licenseNo) {
        this.id = id;
        this.name = name;
        this.license = new DrivingLicense(licenseNo);
        this.available = true;
    }

    public Driver(String id, String name, DrivingLicense license) {
        this.id = id;
        this.name = name;
        this.license = license;
        this.available = true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DrivingLicense getLicense() {
        return license;
    }

    public void setLicense(DrivingLicense license) {
        this.license = license;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean verifyLicense(String licenseNo) {
        return this.license.getLicenseNo().equals(licenseNo) && this.license.isValid();
    }

    @Override
    public String toString() {
        return "Driver{id='" + id + "', name='" + name +
                "', license=" + license + ", available=" + available + "}";
    }
}
