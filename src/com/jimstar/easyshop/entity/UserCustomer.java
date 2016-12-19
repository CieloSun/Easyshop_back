package com.jimstar.easyshop.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import java.io.Serializable;

@Entity
@PrimaryKeyJoinColumn(name = "UserCustomer")
@DiscriminatorValue("customer")
public class UserCustomer extends User implements Serializable {

    public UserCustomer() {
    }

}
