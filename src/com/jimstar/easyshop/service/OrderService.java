package com.jimstar.easyshop.service;

import com.jimstar.easyshop.dao.*;
import com.jimstar.easyshop.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class OrderService {
    private final OrderDao orderDao;
    private final UserCustomerDao userCustomerDao;
    private final UserMerchantDao userMerchantDao;
    private final ItemDao itemDao;
    private final OrderItemDao orderItemDao;

    @Autowired
    public OrderService(OrderDao orderDao, UserCustomerDao userCustomerDao, ItemDao itemDao, OrderItemDao orderItemDao, UserMerchantDao userMerchantDao) {
        this.orderDao = orderDao;
        this.userCustomerDao=userCustomerDao;
        this.itemDao = itemDao;
        this.orderItemDao = orderItemDao;
        this.userMerchantDao=userMerchantDao;
    }

    public Order createOrderInCart(UserCustomer userCustomer, UserMerchant userMerchant, ShipAddress shipAddress){
        Timestamp timestamp=new Timestamp(System.currentTimeMillis());

        Order order = new Order();
        Integer status = Order.OrderStatus.UNDETERMINED;
        order.setCreateTime(timestamp);
        order.setAlterTime(timestamp);
        order.setCustomer(userCustomer);
        order.setMerchant(userMerchant);
        order.setShipAddress(shipAddress);
        order.setStatus(status);
        order.setOrderItems(new HashSet<>());
        order = orderDao.add(order);
        userCustomer.setCert(order);
        userCustomerDao.update(userCustomer);
        return order;
    }

    public Order createOrderByOldOrder(Order order){
        Timestamp timestamp=new Timestamp(System.currentTimeMillis());

        Order newOrder=new Order();
        newOrder.setCreateTime(timestamp);
        newOrder.setAlterTime(timestamp);
        newOrder.setCustomer(order.getCustomer());
        newOrder.setMerchant(null);
        newOrder.setShipAddress(order.getShipAddress());
        newOrder.setStatus(Order.OrderStatus.UNDETERMINED);
        newOrder.setOrderItems(new HashSet<>());
        newOrder = orderDao.add(newOrder);
        return newOrder;
    }

    public boolean updateOrder(Order order) {
        return orderDao.update(order);
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
    public List<Order> getOrdersByCustomer(String userCustomerName){
        UserCustomer userCustomer=userCustomerDao.selectByName(userCustomerName);
        return orderDao.selectByCustomer(userCustomer);
    }

    public List<Order> getOrdersByMerchant(String userMerchantName){
        UserMerchant userMerchant=userMerchantDao.selectByName(userMerchantName);
        return orderDao.selectByMerchant(userMerchant);
    }

    public Order createCert(UserCustomer userCustomer) {
        Order order = userCustomer.getCert();
        if (order == null) {
            Timestamp current = new Timestamp(System.currentTimeMillis());
            order = new Order();
            order.setCreateTime(current);
            order.setAlterTime(current);
            order.setStatus(Order.OrderStatus.UNDETERMINED);
            order.setCustomer(userCustomer);
            order = orderDao.add(order);
        }
        return order;
    }

    public boolean deleteCert(UserCustomer userCustomer) {
        Order order = userCustomer.getCert();
        if (order == null) return false;
        if (order.getStatus() != Order.OrderStatus.UNDETERMINED) return false;
        return orderDao.delete(order);
    }

    public Order addOrderItemtoCert(UserCustomer userCustomer, OrderItem orderItem) throws Exception {
        Order order = userCustomer.getCert();
        if (order.getMerchant() == null) {
            order.setMerchant(orderItem.getItem().getUserMerchant());
        } else if (!order.getMerchant().getId().equals(orderItem.getItem().getUserMerchant().getId())) {
            throw new Exception("Not the same merchant");
        }
        Set<OrderItem> orderItems = order.getOrderItems();
        orderItems.add(orderItem);
        order.setOrderItems(orderItems);
        if (!orderDao.update(order))
            throw new Exception("Failed to update order");
        return order;
    }

    /**
     * 更新过时商品并缩减数量，避免超售
     *
     * @param userCustomer
     * @return
     * @throws Exception
     */
    public Order traceCertItems(UserCustomer userCustomer) throws Exception {
        Order order = userCustomer.getCert();
        if (order.getStatus() != Order.OrderStatus.UNDETERMINED)
            throw new Exception("Not indetermined cert");
        Set<OrderItem> orderItems = order.getOrderItems();
        //合并重复OrderItem
        Map<String, OrderItem> distinctOrderItem = new HashMap<>();
        for (OrderItem it : orderItems) {
            String iid = it.getItem().getIid();
            OrderItem objOrderItem = distinctOrderItem.get(iid);
            if (objOrderItem == null) {
                //更新至最新Uid
                Item newItem = itemDao.selectLatestByIid(iid);
                //如果是新版本
                if (newItem.getVer() > it.getItem().getVer()) {
                    it.setItem(newItem);
                }
                distinctOrderItem.put(iid, it);
            } else {
                //重复元素
                objOrderItem.setCount(objOrderItem.getCount() + it.getCount());
                orderItemDao.delete(it);
            }
        }
        //根据库存数量缩减订单
        orderItems = new HashSet<>();
        for (String iid : distinctOrderItem.keySet()) {
            OrderItem orderItem = distinctOrderItem.get(iid);
            orderItem.setCount(Integer.min(orderItem.getCount(), orderItem.getItem().getCount()));
            if (orderItem.getCount() > 0)
                orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);
        order.setAlterTime(new Timestamp(System.currentTimeMillis()));
        orderDao.update(order);
        return order;
    }

    /**
     * 调整库存，产生正式订单
     *
     * @param userCustomer
     * @param shipAddress
     * @return
     * @throws Exception
     */
    public Order checkAndGenerateOrder(UserCustomer userCustomer, ShipAddress shipAddress) throws Exception {
        Order order = userCustomer.getCert();
        order.setShipAddress(shipAddress);
        order = traceCertItems(userCustomer);
        Set<OrderItem> items = order.getOrderItems();
        for (OrderItem it : items) {
            if (it.getCount() > it.getItem().getCount())
                throw new Exception("Not enough inventory");
            else {
                //减少库存
                it.getItem().setCount(it.getItem().getCount() - it.getCount());
                itemDao.update(it.getItem());
            }
        }
        order.setStatus(Order.OrderStatus.PAYED);
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        order.setCreateTime(currentTime);
        order.setAlterTime(currentTime);
        orderDao.update(order);
        userCustomer.setCert(null);
        return order;
    }
}
