package com.jimstar.easyshop.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by jim on 2016/12/19.
 */
@Entity
@Table(name = "Orders")
public class Order implements Serializable {
    private String id;
    private Timestamp createTime;
    private Timestamp alterTime;
    private Integer status;
    private UserCustomer customer;
    private UserMerchant merchant;
    private ShipAddress shipAddress;
    private List<Item> items;

    @Id
    @GenericGenerator(name = "UUIDgen" , strategy = "uuid")
    @GeneratedValue(generator = "UUIDgen")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getAlterTime() {
        return alterTime;
    }

    public void setAlterTime(Timestamp alterTime) {
        this.alterTime = alterTime;
    }

    @Column(name = "_status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @ManyToOne
    @PrimaryKeyJoinColumn
    public UserCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(UserCustomer customer) {
        this.customer = customer;
    }

    @ManyToOne
    @PrimaryKeyJoinColumn
    public UserMerchant getMerchant() {
        return merchant;
    }

    public void setMerchant(UserMerchant merchant) {
        this.merchant = merchant;
    }

    @Embedded
    public ShipAddress getShipAddress() {
        return shipAddress;
    }

    public void setShipAddress(ShipAddress shipAddress) {
        this.shipAddress = shipAddress;
    }

    @ManyToMany
    @JoinTable(name = "Order_Item")
    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Order() {
    }
}
