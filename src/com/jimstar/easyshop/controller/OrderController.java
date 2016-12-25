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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    public String add(@RequestBody String mapString) throws Exception{
        Map<String, Object> map = JSONUtil.parseMap(mapString);
        String userName=(String) map.get("userName");
        String itemUId = (String) map.get("itemUid");
        String address=(String)map.get("address");
        String phone=(String)map.get("phone");
        String shipName=(String)map.get("shipName");
        Integer count=(Integer)map.get("count");
        Item item=itemService.getByUid(itemUId);
        ShipAddress shipAddress=shipAddressService.getShipAdress(shipName,address,phone);
        UserCustomer userCustomer=userCustomerService.getUserCustomerByName(userName);
        Order order=orderService.createOrderInCart(userCustomer,item.getUserMerchant(),shipAddress);
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
    @RequestMapping(value = "changeStatus",method = RequestMethod.POST)
    @ResponseBody
    public String changeStatus(@RequestBody String mapString) throws Exception{
        Map<String, Object> map = JSONUtil.parseMap(mapString);
        String orderId = (String) map.get("orderId");
        Integer status=(Integer)map.get("orderStatus");
        Order order = orderService.getOrderById(orderId);
        Set<OrderItem> orderItems = order.getOrderItems();

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
        Map map=JSONUtil.parseMap(mapString);
        String orderId=(String)map.get("orderId");
        Integer count=(Integer) map.get("count");
        String itemUid=(String)map.get("itemUid");
        Order order=orderService.getOrderById(orderId);
        for(OrderItem orderItem:order.getOrderItems()){
            if(orderItem.getItem().getUid()==itemUid){
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
            map.put("order", order);
        }
        return JSONUtil.toJSON(map);
    }

    @RequestMapping("getByUser")
    @ResponseBody
    public String getByUser(String userName) throws Exception{
        Map<String,Object> map=new HashMap<>();
        List<Order> orders=orderService.getOrdersByCustomer(userName);
        for(Order order:orders){
            
        }
        if (orders == null) {
            map.put("status", 1);
            map.put("error", "No such order");
        } else {
            map.put("status", 0);
            map.put("orders",orders);
        }
        return JSONUtil.toJSON(map);
    }
}
