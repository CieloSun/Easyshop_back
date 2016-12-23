package com.jimstar.easyshop.controller;

import com.jimstar.easyshop.service.UserCustomerService;
import com.jimstar.easyshop.service.UserMerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by 63289 on 2016/12/23.
 */
@Controller
@RequestMapping("/User")
public class UserController {
    @Autowired
    private UserCustomerService userCustomerService;
    @Autowired
    private UserMerchantService userMerchantService;
    @RequestMapping("/firstPage")
    public String firstPage(){
        return "firstPage";
    }
    @RequestMapping(value="/login",method = RequestMethod.POST)
    @ResponseBody
    public String login(@RequestBody Map<String,Object> map) throws Exception{

        System.out.println(map.toString());
        //TODO
        return null;
    }
}
