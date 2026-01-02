package com.tastebuds.model;

import jakarta.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "chefs")
@XmlAccessorType(XmlAccessType.FIELD)
public class ChefList {

    @XmlElement(name = "chef")
    private List<Chef> chefs;

    public ChefList() {
        this.chefs = new ArrayList<>();
    }

    public ChefList(List<Chef> chefs) {
        this.chefs = chefs;
    }

    public List<Chef> getChefs() {
        return chefs;
    }

    public void setChefs(List<Chef> chefs) {
        this.chefs = chefs;
    }

    public void addChef(Chef chef) {
        this.chefs.add(chef);
    }
}
