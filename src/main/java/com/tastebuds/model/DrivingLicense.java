package com.tastebuds.model;

import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "drivingLicense")
@XmlAccessorType(XmlAccessType.FIELD)
public class DrivingLicense {
    @XmlElement(required = true)
    private String licenseNo;

    @XmlElement
    private boolean valid;

    public DrivingLicense() {}

    public DrivingLicense(String licenseNo) {
        this.licenseNo = licenseNo;
        this.valid = true;
    }

    public DrivingLicense(String licenseNo, boolean valid) {
        this.licenseNo = licenseNo;
        this.valid = valid;
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return "DrivingLicense{licenseNo='" + licenseNo + "', valid=" + valid + "}";
    }
}