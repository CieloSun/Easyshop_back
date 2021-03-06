package com.jimstar.easyshop.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "Order_")
public class Order implements Serializable {
    private String id;
    private Timestamp createTime;
    private Timestamp alterTime;
    /*
    1 待定（购物车）
    2 已下单
    3 已付款（可能用不上）
    4 已发货
    5 已完成
    6 退货中(可能用不上)
    7 已取消
    */
    private Integer status;
    private UserCustomer customer;
    private UserMerchant merchant;
    private ShipAddress shipAddress;
    private Set<OrderItem> orderItems;

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

    @Column(name = "status_")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @ManyToOne
    public UserCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(UserCustomer customer) {
        this.customer = customer;
    }

    @ManyToOne
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", createTime=" + createTime +
                ", alterTime=" + alterTime +
                ", status=" + status +
                ", customer=" + customer +
                ", merchant=" + merchant +
                ", shipAddress=" + shipAddress +
                ", orderItems=" + orderItems +
                '}';
    }

    public static class OrderStatus {
        public static final int UNDETERMINED = 1;
        public static final int GENERATED = 2;
        public static final int PAYED = 3;
        public static final int DISPATCHED = 4;
        public static final int COMPLETED = 5;
        public static final int RETURNING = 6;
        public static final int CANCELLED = 7;
    }
}
