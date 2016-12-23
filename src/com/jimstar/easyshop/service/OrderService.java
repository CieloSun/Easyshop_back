package com.jimstar.easyshop.service;

import com.jimstar.easyshop.dao.OrderDao;
import com.jimstar.easyshop.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class OrderService {
    @Autowired
    private OrderDao orderDao;

    public boolean createOrderInCart(UserCustomer userCustomer, UserMerchant userMerchant, ShipAddress shipAddress, Item item){
        Timestamp timestamp=new Timestamp(System.currentTimeMillis());
        Integer status=1;
        Order order=new Order();
        //TODO
        return false;
    }
    public boolean createOrderDerictly(){
        //TODO
        return false;
    }
    public boolean createOrderFromCart(){
        //TODO
        return false;
    }
}
