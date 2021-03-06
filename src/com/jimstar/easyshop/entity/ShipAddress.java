package com.jimstar.easyshop.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ShipAddress implements Serializable {
    private String name;
    private String address;
    private String phone;

    public ShipAddress() {
    }

    public ShipAddress(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    @Column(name = "name_")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
