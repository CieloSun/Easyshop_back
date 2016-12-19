package com.jimstar.easyshop.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by jim on 2016/12/19.
 */
@Entity
@PrimaryKeyJoinColumn(name = "UserMerchant")
public class UserMerchant extends User implements Serializable {
    private String shopName;
    private String shopDesc;

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

    public UserMerchant() {
    }

}
