package com.jimstar.easyshop.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import java.io.Serializable;

@Entity
@PrimaryKeyJoinColumn(name = "UserCustomer")
@DiscriminatorValue("customer")
public class UserCustomer extends User implements Serializable {
    private Order cert;

    public UserCustomer() {
    }

    @OneToOne
    public Order getCert() {
        return cert;
    }

    public void setCert(Order cert) {
        this.cert = cert;
    }
}
