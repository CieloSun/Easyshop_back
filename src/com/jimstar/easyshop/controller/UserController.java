package com.jimstar.easyshop.controller;

import com.jimstar.easyshop.entity.UserCustomer;
import com.jimstar.easyshop.entity.UserMerchant;
import com.jimstar.easyshop.service.UserCustomerService;
import com.jimstar.easyshop.service.UserMerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
    public String login(@RequestBody Map<String,Object> map, ModelMap modelMap) throws Exception{
        String username=(String) map.get("username");
        String password=(String)map.get("password");
        if(map.get("type").equals("customer")){
            if(userCustomerService.checkPasswordByNameAndPwd(username,password)){
                UserCustomer userCustomer=userCustomerService.getUserCustomerByName(username);
                Integer id=userCustomer.getId();
                modelMap.addAttribute("id",id);
                modelMap.addAttribute("status",0);
                modelMap.addAttribute("info","Success to login!");
                return "success";
            }
            modelMap.addAttribute("status",1);
            modelMap.addAttribute("info","Username or password is wrong.");
            return "error";
        }
        else if(map.get("type").equals("merchant")){
            if(userMerchantService.checkPasswordByName(username,password)){
                UserMerchant userMerchant=userMerchantService.getUserMerchantByName(username);
                Integer id=userMerchant.getId();
                modelMap.addAttribute("id",id);
                modelMap.addAttribute("status",0);
                modelMap.addAttribute("info","Success to login!");
                return "success";
            }
            modelMap.addAttribute("status",1);
            modelMap.addAttribute("info","Username or password is wrong.");
            return "error";
        }
        modelMap.addAttribute("info","Type is wrong.");
        modelMap.addAttribute("status",1);
        return "error";
    }
    @RequestMapping(value = "register",method = RequestMethod.POST)
    @ResponseBody
    public String register(@RequestBody Map<String,Object> map,ModelMap modelMap) throws Exception{
        String username=(String) map.get("username");
        String password=(String) map.get("password");
        String type=(String) map.get("type");
        if(type.equals("customer")){
            if(userCustomerService.addUserCustomerByNameAndPwd(username,password)){
                UserCustomer userCustomer=userCustomerService.getUserCustomerByName(username);
                Integer id=userCustomer.getId();
                modelMap.addAttribute("id",id);
                modelMap.addAttribute("status",0);
                modelMap.addAttribute("info","Success to register!");
                return "success";
            }
            modelMap.addAttribute("status",1);
            modelMap.addAttribute("info","Cannot register, maybe username has been used.");
            return "error";
        }
        else if(type.equals("merchant")){
            String shopName=(String) map.get("shopName");
            String shopDescription=(String) map.get("shopDescription");
            if(userMerchantService.addUserMerchantByInfo(username,password,shopName,shopDescription)){
                UserMerchant userMerchant=userMerchantService.getUserMerchantByName(username);
                Integer id=userMerchant.getId();
                modelMap.addAttribute("id",id);
                modelMap.addAttribute("status",1);
                modelMap.addAttribute("info","Success to register!");
                return "success";
            }
            modelMap.addAttribute("info","Cannot register, maybe username has been used.");
            modelMap.addAttribute("status",1);
            return "error";
        }
        modelMap.addAttribute("status",1);
        modelMap.addAttribute("info","Type is wrong.");
        return "error";
    }
}
