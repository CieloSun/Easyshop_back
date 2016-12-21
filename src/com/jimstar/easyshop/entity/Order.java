package com.jimstar.easyshop.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

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

    public Order() {
    }

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

    public static class OrderStatus {
        public final int UNDETERMINED = 1;
        public final int GENERATED = 2;
        public final int PAYED = 3;
        public final int DISPATCHED = 4;
        public final int COMPLETED = 5;
        public final int RETURING = 6;
        public final int CANCELLED = 7;
    }
}
