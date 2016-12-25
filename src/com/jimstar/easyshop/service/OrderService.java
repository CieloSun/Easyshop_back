package com.jimstar.easyshop.service;

import com.jimstar.easyshop.dao.OrderDao;
import com.jimstar.easyshop.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Set;

@Service
public class OrderService {
    private final OrderDao orderDao;

    @Autowired
    public OrderService(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public Order createOrderInCart(UserCustomer userCustomer, UserMerchant userMerchant, ShipAddress shipAddress){
        Timestamp timestamp=new Timestamp(System.currentTimeMillis());
        Integer status=1;
        Order order=new Order();
        order.setCreateTime(timestamp);
        order.setAlterTime(timestamp);
        order.setCustomer(userCustomer);
        order.setMerchant(userMerchant);
        order.setShipAddress(shipAddress);
        order.setStatus(status);
        return orderDao.add(order);
    }
    public boolean addOrderItemToOrderById(String id, OrderItem orderItem){
        Order order=orderDao.selectById(id);
        Set<OrderItem> orderItems = order.getOrderItems();
        orderItems.add(orderItem);
        order.setOrderItems(orderItems);
        return orderDao.update(order);
    }
    public boolean changeStatusById(String id, Integer status){
        Order order=orderDao.selectById(id);
        order.setStatus(status);
        return orderDao.update(order);
    }
    public Order getOrderById(String id){
        return orderDao.selectById(id);
    }
}
