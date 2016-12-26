package com.jimstar.easyshop.controller;

import com.jimstar.easyshop.entity.*;
import com.jimstar.easyshop.service.*;
import com.jimstar.easyshop.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

@Controller
@RequestMapping("Order")
public class OrderController {
    private final OrderService orderService;
    private final OrderItemService orderItemService;
    private final ItemService itemService;
    private final ShipAddressService shipAddressService;
    private final UserCustomerService userCustomerService;

    @Autowired
    public OrderController(OrderService orderService, OrderItemService orderItemService, ItemService itemService,
                           ShipAddressService shipAddressService, UserCustomerService userCustomerService) {
        this.orderService = orderService;
        this.orderItemService = orderItemService;
        this.itemService = itemService;
        this.shipAddressService=shipAddressService;
        this.userCustomerService=userCustomerService;
    }
    @RequestMapping(value="add",method = RequestMethod.POST)
    @ResponseBody
    public String add(@RequestBody String mapString) throws IOException {
        Map<String, Object> map = JSONUtil.parseMap(mapString);
        String userName=(String) map.get("userName");
        String itemUId = (String) map.get("itemUid");

        Integer count=(Integer)map.get("count");
        Item item=itemService.getByUid(itemUId);

        UserCustomer userCustomer=userCustomerService.getUserCustomerByName(userName);
        Order order = orderService.createOrderInCart(userCustomer, item.getUserMerchant(), null);
        map.put("status",1);
        map.put("info","Fail to add the order");
        if(order!=null){
            OrderItem orderItem=orderItemService.createOrderItemByItemAndOrderAndCount(item,order,count);
            if(orderItem!=null){
                if(orderService.addOrderItemToOrderById(order.getId(),orderItem)){
                    map.replace("status",0);
                    map.replace("info","Success to add the order");
                    map.put("orderId",order.getId());
                }
            }
        }
        return JSONUtil.toJSON(map);
    }

    @RequestMapping(value = "addItem", method = RequestMethod.POST)
    @ResponseBody
    public String addItem(@RequestBody String mapString) throws Exception{
        Map<String, Object> map = JSONUtil.parseMap(mapString);
        String orderId=(String) map.get("orderId");
        String itemUid=(String) map.get("itemUid");
        Order order=orderService.getOrderById(orderId);
        Item item=itemService.getByUid(itemUid);
        map.put("status",1);
        map.put("info","Wrong to add item.");
        OrderItem orderItem=orderItemService.createOrderItemByItemAndOrderAndCount(item,order,1);
        if(orderItem!=null){
            map.replace("status",0);
            map.replace("info","Success to add item");
        }
        return JSONUtil.toJSON(map);
    }
    @RequestMapping(value = "changeStatus",method = RequestMethod.POST)
    @ResponseBody
    public String changeStatus(@RequestBody String mapString) throws Exception{
        Map<String, Object> map = JSONUtil.parseMap(mapString);
        String orderId = (String) map.get("orderId");
        Integer status=(Integer)map.get("orderStatus");
        Order order = orderService.getOrderById(orderId);
        Set<OrderItem> orderItems = order.getOrderItems();
        order.setAlterTime(new Timestamp(System.currentTimeMillis()));
        if (orderService.changeStatusById(order.getId(), status)) {
            if(status==3){
                for (OrderItem orderItem : orderItems) {
                    itemService.changeCountByChangeNumber(orderItem.getItem().getUid(), orderItem.getCount());
                }
            }
            map.put("status",0);
            map.put("info","Change order success!");
        }else{
            map.put("status",1);
            map.put("info","Change order fail");
        }
        return JSONUtil.toJSON(map);
    }
    @RequestMapping(value="changeCount", method = RequestMethod.POST)
    @ResponseBody
    public String changeCount(@RequestBody String mapString) throws Exception{
        Map<String, Object> map = JSONUtil.parseMap(mapString);
        String orderId=(String)map.get("orderId");
        Integer count=(Integer) map.get("count");
        String itemUid=(String)map.get("itemUid");
        Order order=orderService.getOrderById(orderId);
        for(OrderItem orderItem:order.getOrderItems()){
            if (orderItem.getItem().getUid().equals(itemUid)) {
                if(count==0){
                    if(orderItemService.deleteOrderItemById(orderItem.getId())){
                        map.put("status",0);
                        map.put("info","Success to delete the item");
                    }
                    else{
                        map.put("status",1);
                        map.put("info","Fail to delete the item");
                    }
                }
                else{
                    if(orderItemService.changeCountByOrderItemId(orderItem.getId(),count)){
                        map.put("status",0);
                        map.put("info","Success to update the item numbers");
                    }
                    else{
                        map.put("status",1);
                        map.put("info","Fail to update the item numbers");
                    }
                }
            }
        }
        return JSONUtil.toJSON(map);
    }

    @RequestMapping("get")
    @ResponseBody
    public String get(String orderId) throws Exception{
        Map<String, Object> map = new HashMap<>();
        Order order=orderService.getOrderById(orderId);
        if (order == null) {
            map.put("status", 1);
            map.put("error", "No such order");
        } else {
            map.put("status", 0);
            UserCustomer customer = order.getCustomer();
            UserMerchant merchant = order.getMerchant();
            ShipAddress address = order.getShipAddress();
            map.put("userCustomerName", customer.getName());
            map.put("userCustomerId", customer.getId());
            map.put("createTime", order.getCreateTime());
            map.put("createAlterTime", order.getAlterTime());
            map.put("orderStatus", order.getStatus());
            if (merchant != null) {
                map.put("userMerchantName", merchant.getName());
                map.put("userMerchantId", merchant.getId());
                map.put("shopName", merchant.getShopName());
            }
            if (address != null) {
                map.put("shipAddressName", address.getName());
                map.put("shipAddressAddress", address.getAddress());
                map.put("shipAddressPhone", address.getPhone());
            }
            Set<OrderItem> orderItems = order.getOrderItems();
            Map<String, Integer> itemPair = new HashMap<>();
            for (OrderItem it : orderItems) {
                itemPair.put(it.getItem().getIid(), it.getCount());
            }
            map.put("itemIid_count", itemPair);
        }
        return JSONUtil.toJSON(map);
    }

    @RequestMapping("getByUser")
    @ResponseBody
    public String getByUser(String userName) throws Exception{
        Map<String,Object> map=new HashMap<>();
        List<Order> orders=orderService.getOrdersByCustomer(userName);
        List<Map<String,Object>> orderList=new ArrayList<>();
        for(Order order:orders){
            Map<String,Object> orderMap=new HashMap<>();
            orderMap.put("id",order.getId());
            orderMap.put("createTime",order.getCreateTime());
            orderMap.put("alterTime",order.getAlterTime());
            orderMap.put("status",order.getStatus());
            orderMap.put("customerName",order.getCustomer().getName());
            orderMap.put("merchantName",order.getMerchant().getName());
            orderMap.put("shipAddressName",order.getShipAddress().getName());
            orderMap.put("shipAddressAddress",order.getShipAddress().getAddress());
            orderMap.put("shipAddressPhone",order.getShipAddress().getPhone());
            Map<String,Object> orderItemMap=new HashMap<>();
            for(OrderItem orderItem:order.getOrderItems()){
                orderItemMap.put("orderItemId",orderItem.getId());
                orderItemMap.put("itemUid",orderItem.getItem().getUid());
                orderItemMap.put("count",orderItem.getCount());
            }
            orderMap.put("orderItems",orderItemMap);
            orderList.add(orderMap);
        }
        if (orders == null) {
            map.put("status", 1);
            map.put("error", "No such order");
        } else {
            map.put("status", 0);
            map.put("orders",orderList);
        }
        return JSONUtil.toJSON(map);
    }
}
