package com.jimstar.easyshop.service;

import com.jimstar.easyshop.dao.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * Created by 63289 on 2016/12/21.
 */
@Service
public class OrderService {
    @Autowired
    private OrderDao orderDao;
    public boolean createOrder(){
        Timestamp timestamp=new Timestamp(System.currentTimeMillis());
        //TODO
        return false;
    }
}
