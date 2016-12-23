package com.jimstar.easyshop.service;

import com.jimstar.easyshop.dao.OrderItemDao;
import com.jimstar.easyshop.entity.Item;
import com.jimstar.easyshop.entity.Order;
import com.jimstar.easyshop.entity.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 63289 on 2016/12/23.
 */
@Service
public class OrderItemService {
    @Autowired
    private OrderItemDao orderItemDao;
    public boolean createOrderItemByItemAndOrderAndCount(Item item, Order order, Integer count){
        OrderItem orderItem=new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrder(order);
        orderItem.setCount(count);
        return orderItemDao.add(orderItem);
    }

    public boolean deleteOrderItemById(String id){
        return orderItemDao.deleteById(id);
    }
}