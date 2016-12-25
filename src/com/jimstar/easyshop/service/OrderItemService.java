package com.jimstar.easyshop.service;

import com.jimstar.easyshop.dao.OrderDao;
import com.jimstar.easyshop.dao.OrderItemDao;
import com.jimstar.easyshop.entity.Item;
import com.jimstar.easyshop.entity.Order;
import com.jimstar.easyshop.entity.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class OrderItemService {
    private final OrderItemDao orderItemDao;
    private final OrderDao orderDao;

    @Autowired
    public OrderItemService(OrderItemDao orderItemDao, OrderDao orderDao) {
        this.orderItemDao = orderItemDao;
        this.orderDao=orderDao;
    }

    public OrderItem createOrderItemByItemAndOrderAndCount(Item item, Order order, Integer count){
        OrderItem orderItem=new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrder(order);
        orderItem.setCount(count);
        Set<OrderItem> orderItems=order.getOrderItems();
        orderItems.add(orderItem);
        order.setOrderItems(orderItems);
        if(orderDao.update(order)){
            return orderItemDao.add(orderItem);
        }
        else {
            return null;
        }
    }

    public boolean changeCountByOrderItemId(String orderItemId, Integer count){
        OrderItem orderItem=orderItemDao.selectById(orderItemId);
        orderItem.setCount(count);
        return orderItemDao.update(orderItem);
    }

    public OrderItem getOrderItemById(String id){
        return orderItemDao.selectById(id);
    }

    public boolean deleteOrderItemById(String id){
        OrderItem orderItem=orderItemDao.selectById(id);
        Order order=orderItem.getOrder();
        Set<OrderItem> orderItems=order.getOrderItems();
        orderItems.remove(orderItem);
        order.setOrderItems(orderItems);
        if(orderDao.update(order)){
            return orderItemDao.deleteById(id);
        }
        else return false;
    }

}
