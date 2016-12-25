package com.jimstar.easyshop.controller;

import com.jimstar.easyshop.entity.*;
import com.jimstar.easyshop.service.*;
import com.jimstar.easyshop.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by 63289 on 2016/12/25.
 */
@Controller
@RequestMapping("/Order")
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

    @RequestMapping("/add")
    @ResponseBody
    public String add(@RequestBody String mapString) throws Exception{
        Map map= JSONUtil.parseMap(mapString);
        String userName=(String) map.get("userName");
        String itemUId=(String)map.get("itemUId");
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
}
