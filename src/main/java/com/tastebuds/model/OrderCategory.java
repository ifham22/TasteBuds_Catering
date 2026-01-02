package com.tastebuds.model;

import jakarta.xml.bind.annotation.*;

@XmlEnum
@XmlType(name = "orderCategory")
public enum OrderCategory {
    @XmlEnumValue("NORMAL")
    NORMAL,

    @XmlEnumValue("PRIORITY")
    PRIORITY
}

