package com.jimstar.easyshop.controller;

import com.jimstar.easyshop.entity.UserCustomer;
import com.jimstar.easyshop.entity.UserMerchant;
import com.jimstar.easyshop.service.UserCustomerService;
import com.jimstar.easyshop.service.UserMerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.portlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/User")
@SessionAttributes("userName")
public class UserController {
    private final UserCustomerService userCustomerService;
    private final UserMerchantService userMerchantService;

    @Autowired
    public UserController(UserCustomerService userCustomerService, UserMerchantService userMerchantService) {
        this.userCustomerService = userCustomerService;
        this.userMerchantService = userMerchantService;
    }

    @RequestMapping(value="/login",method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView login(Map<String,Object> map, ModelMap modelMap) throws Exception{
        ModelAndView modelAndView=new ModelAndView();
        String username=(String) map.get("username");
        String password=(String)map.get("password");
        if(map.get("type").equals("customer")){
            if(userCustomerService.checkPasswordByName(username,password)){
                UserCustomer userCustomer=userCustomerService.getUserCustomerByName(username);
                Integer userName=userCustomer.getId();
                modelMap.addAttribute("userName",userName);
                modelMap.addAttribute("type","customer");
                modelMap.addAttribute("status",0);
                modelMap.addAttribute("info","Success to login!");
            }
            else{
                modelMap.addAttribute("status",1);
                modelMap.addAttribute("info","Username or password is wrong.");
            }
        }
        else if(map.get("type").equals("merchant")){
            if(userMerchantService.checkPasswordByName(username,password)){
                UserMerchant userMerchant=userMerchantService.getUserMerchantByName(username);
                Integer userName=userMerchant.getId();
                modelMap.addAttribute("userName",userName);
                modelMap.addAttribute("type","merchant");
                modelMap.addAttribute("status",0);
                modelMap.addAttribute("info","Success to login!");
            }
            else{
                modelMap.addAttribute("status",1);
                modelMap.addAttribute("info","Username or password is wrong.");
            }
        }
        else{
            modelMap.addAttribute("info","Type is wrong.");
            modelMap.addAttribute("status",1);
        }
        modelAndView.addAllObjects(modelMap);
        return modelAndView;
    }
    @RequestMapping(value = "register",method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView register(@RequestBody Map<String, Object> map, ModelMap modelMap) throws Exception {
        ModelAndView modelAndView=new ModelAndView();
        String username=(String) map.get("username");
        String password=(String) map.get("password");
        String type=(String) map.get("type");
        switch (type) {
            case "customer":
                if (userCustomerService.addUserCustomerByNameAndPwd(username, password)) {
                    UserCustomer userCustomer = userCustomerService.getUserCustomerByName(username);
                    String userName = userCustomer.getName();
                    modelMap.addAttribute("userName", userName);
                    modelMap.addAttribute("type", "customer");
                    modelMap.addAttribute("status", 0);
                    modelMap.addAttribute("info", "Success to register!");
                } else {
                    modelMap.addAttribute("status", 1);
                    modelMap.addAttribute("info", "Cannot register, maybe username has been used.");
                }
                break;
            case "merchant":
                String shopName = (String) map.get("shopName");
                String shopDescription = (String) map.get("shopDescription");
                if (userMerchantService.addUserMerchantByInfo(username, password, shopName, shopDescription)) {
                    UserMerchant userMerchant = userMerchantService.getUserMerchantByName(username);
                    String userName = userMerchant.getName();
                    modelMap.addAttribute("userName", userName);
                    modelMap.addAttribute("type", "merchant");
                    modelMap.addAttribute("status", 0);
                    modelMap.addAttribute("info", "Success to register!");
                } else {
                    modelMap.addAttribute("info", "Cannot register, maybe username has been used.");
                    modelMap.addAttribute("status", 1);
                }
                break;
            default:
                modelMap.addAttribute("status", 1);
                modelMap.addAttribute("info", "Type is wrong.");
                break;
        }
        modelAndView.addAllObjects(modelMap);
        return modelAndView;
    }
    @RequestMapping(value = "/changePwd",method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView changePwd(Map<String,Object> map,ModelMap modelMap) throws Exception{
        ModelAndView modelAndView=new ModelAndView();
        String username=(String)map.get("username");
        String originalPassword=(String)map.get("originalPassword");
        String password=(String)map.get("password");
        String type=(String)map.get("type");
        if(type.equals("customer")){
            if(userCustomerService.checkPasswordByName(username,originalPassword)){
                modelMap.addAttribute("verify",0);
                if(userCustomerService.changePasswordByName(username,password))
                {
                    modelMap.addAttribute("userName",username);
                    modelMap.addAttribute("type","customer");
                    modelMap.addAttribute("status",0);
                    modelMap.addAttribute("info","Success to change password");
                }
                else{
                    modelMap.addAttribute("status",1);
                    modelMap.addAttribute("info","Fail to update the data");
                }
            }
            else{
                modelMap.addAttribute("verify",1);
                modelMap.addAttribute("status",1);
                modelMap.addAttribute("info","The original password is wrong.");
            }
        }
        if(type.equals("merchant")){
            if(userMerchantService.checkPasswordByName(username,originalPassword)){
                modelMap.addAttribute("verify",0);
                if(userMerchantService.changePasswordByName(username,password))
                {
                    modelMap.addAttribute("userName",username);
                    modelMap.addAttribute("typp","merchant");
                    modelMap.addAttribute("status",0);
                    modelMap.addAttribute("info","Success to change password");
                }
                else{
                    modelMap.addAttribute("status",1);
                    modelMap.addAttribute("info","Fail to update the data");
                }
            }
            else{
                modelMap.addAttribute("status",1);
                modelMap.addAttribute("info","The original password is wrong.");
            }
        }
        modelAndView.addAllObjects(modelMap);
        return modelAndView;
    }
    @RequestMapping("/changeShop")
    @ResponseBody
    public ModelAndView changeShop(Map<String,Object> map,ModelMap modelMap) throws Exception{
        ModelAndView modelAndView=new ModelAndView();
        String userName=(String)map.get("userName");
        String shopName=(String)map.get("shopName");
        String shopDescription=(String)map.get("shopDescription");
        String type=(String)map.get("type");
        if(type.equals("merchant")){
            if(userMerchantService.changeShopByName(userName,shopName,shopDescription)){
                modelMap.addAttribute("status",0);
                modelMap.addAttribute("info","The information of shop has changed");
            }
            else{
                modelMap.addAttribute("status",1);
                modelMap.addAttribute("info","Updated failed.");
            }
        }
        else{
            modelMap.addAttribute("status",1);
            modelMap.addAttribute("info","You are not merchant account!");
        }
        modelAndView.addAllObjects(modelMap);
        return modelAndView;
    }
    @RequestMapping("/showInfo")
    @ResponseBody
    public ModelAndView showInfo(Map<String,Object> map, ModelMap modelMap) throws Exception{
        String type=(String)map.get("type");
        String userName=(String)map.get("userName");
        if(type.equals("merchant")){
            UserMerchant userMerchant=userMerchantService.getUserMerchantByName(userName);
            modelMap.addAttribute("userName",userName);
            modelMap.addAttribute("regTime",userMerchant.getRegTime());
            modelMap.addAttribute("shopName",userMerchant.getShopName());
            modelMap.addAttribute("shopDescription",userMerchant.getShopDesc());
            modelMap.addAttribute("status",0);
            modelMap.addAttribute("info","Success to get the information of the merchant.");
        }
        else{
            modelMap.addAttribute("status",1);
            modelMap.addAttribute("info","This is not a merchant account.");
        }
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addAllObjects(modelMap);
        return modelAndView;
    }
}
