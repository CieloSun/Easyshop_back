package com.jimstar.easyshop.entity;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by jim on 2016/12/19.
 */
@Entity
@PrimaryKeyJoinColumn(name = "UserCustomer")
public class UserCustomer extends User implements Serializable {

    public UserCustomer() {
    }

}
