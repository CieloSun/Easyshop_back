package com.jimstar.easyshop.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@PrimaryKeyJoinColumn(name = "UserMerchant")
@DiscriminatorValue("merchant")
public class UserMerchant extends User implements Serializable {
    private String shopName;
    private String shopDesc;

    public UserMerchant() {
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    @Lob
    @Basic(fetch = FetchType.LAZY)
    public String getShopDesc() {
        return shopDesc;
    }

    public void setShopDesc(String shopDesc) {
        this.shopDesc = shopDesc;
    }

    @Override
    public String toString() {
        return "UserMerchant{" +
                "shopName='" + shopName + '\'' +
                ", shopDesc='" + shopDesc + '\'' +
                "} " + super.toString();
    }
}
