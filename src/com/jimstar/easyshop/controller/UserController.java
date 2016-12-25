package com.jimstar.easyshop.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jimstar.easyshop.entity.UserCustomer;
import com.jimstar.easyshop.entity.UserMerchant;
import com.jimstar.easyshop.service.UserCustomerService;
import com.jimstar.easyshop.service.UserMerchantService;
import com.jimstar.easyshop.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/User")
@SessionAttributes({"username", "type", "userId"})
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
    public String login(@RequestBody String mapString) throws Exception{
        Map<String, Object> map = JSONUtil.parseMap(mapString);
        String username=(String) map.get("username");
        String password=(String)map.get("password");
        if(map.get("type").equals("customer")){
            if(userCustomerService.checkPasswordByName(username,password)){
                UserCustomer userCustomer=userCustomerService.getUserCustomerByName(username);
                String userName = userCustomer.getName();
                Integer userId = userCustomer.getId();
                map.put("userId", userId);
                map.put("status",0);
                map.put("info","Success to login!");
            }
            else{
                map.put("status",1);
                map.put("info","Username or password is wrong.");
            }
        }
        else if(map.get("type").equals("merchant")){
            if(userMerchantService.checkPasswordByName(username,password)){
                UserMerchant userMerchant=userMerchantService.getUserMerchantByName(username);
                Integer userName=userMerchant.getId();
                map.put("status",0);
                map.put("info","Success to login!");
            }
            else{
                map.put("status",1);
                map.put("info","Username or password is wrong.");
            }
        }
        else{
            map.put("info","Type is wrong.");
            map.put("status",1);
        }
        return JSONUtil.toJSON(map);
    }
    @RequestMapping(value = "register",method = RequestMethod.POST)
    @ResponseBody
    public String register(@RequestBody String mapString) throws Exception {
        Map<String, Object> map = JSONUtil.parseMap(mapString);
        String username=(String) map.get("username");
        String password=(String) map.get("password");
        String type=(String) map.get("type");
        switch (type) {
            case "customer":
                if (userCustomerService.addUserCustomerByNameAndPwd(username, password)) {
                    UserCustomer userCustomer = userCustomerService.getUserCustomerByName(username);
                    String userName = userCustomer.getName();
                    map.put("userName", userName);
                    map.put("status", 0);
                    map.put("info", "Success to register!");
                } else {
                    map.put("status", 1);
                    map.put("info", "Cannot register, maybe username has been used.");
                }
                break;
            case "merchant":
                String shopName = (String) map.get("shopName");
                String shopDescription = (String) map.get("shopDescription");
                if (userMerchantService.addUserMerchantByInfo(username, password, shopName, shopDescription)) {
                    UserMerchant userMerchant = userMerchantService.getUserMerchantByName(username);
                    String userName = userMerchant.getName();
                    map.put("userName", userName);
                    map.put("status", 0);
                    map.put("info", "Success to register!");
                } else {
                    map.put("info", "Cannot register, maybe username has been used.");
                    map.put("status", 1);
                }
                break;
            default:
                map.put("status", 1);
                map.put("info", "Type is wrong.");
                break;
        }
        return JSONUtil.toJSON(map);
    }
    @RequestMapping(value = "/changePwd",method = RequestMethod.POST)
    @ResponseBody
    public String changePwd(@RequestBody String mapString) throws Exception{
        Map<String, Object> map = JSONUtil.parseMap(mapString);
        String username=(String)map.get("username");
        String originalPassword=(String)map.get("originalPassword");
        String password=(String)map.get("password");
        String type=(String)map.get("type");
        if(type.equals("customer")){
            if(userCustomerService.checkPasswordByName(username,originalPassword)){
                map.put("verify",0);
                if(userCustomerService.changePasswordByName(username,password))
                {
                    map.put("userName",username);
                    map.put("status",0);
                    map.put("info","Success to change password");
                }
                else{
                    map.put("status",1);
                    map.put("info","Fail to update the data");
                }
            }
            else{
                map.put("verify",1);
                map.put("status",1);
                map.put("info","The original password is wrong.");
            }
        }
        if(type.equals("merchant")){
            if(userMerchantService.checkPasswordByName(username,originalPassword)){
                map.put("verify",0);
                if(userMerchantService.changePasswordByName(username,password))
                {
                    map.put("userName",username);
                    map.put("status",0);
                    map.put("info","Success to change password");
                }
                else{
                    map.put("status",1);
                    map.put("info","Fail to update the data");
                }
            }
            else{
                map.put("status",1);
                map.put("info","The original password is wrong.");
            }
        }
        return JSONUtil.toJSON(map);
    }
    @RequestMapping("/changeShop")
    @ResponseBody
    public String changeShop(@RequestBody String mapString) throws Exception{
        Map<String, Object> map = JSONUtil.parseMap(mapString);
        String userName=(String)map.get("userName");
        String shopName=(String)map.get("shopName");
        String shopDescription=(String)map.get("shopDescription");
        String type=(String)map.get("type");
        if(type.equals("merchant")){
            if(userMerchantService.changeShopByName(userName,shopName,shopDescription)){
                map.put("status",0);
                map.put("info","The information of shop has changed");
            }
            else{
                map.put("status",1);
                map.put("info","Updated failed.");
            }
        }
        else{
            map.put("status",1);
            map.put("info","You are not merchant account!");
        }
        return JSONUtil.toJSON(map);
    }

    @RequestMapping("/showInfo")
    @ResponseBody
    public String showInfo(@RequestBody String mapString) throws Exception{
        Map<String, Object> map = JSONUtil.parseMap(mapString);
        String type=(String)map.get("type");
        String userName=(String)map.get("userName");
        if(type.equals("merchant")){
            UserMerchant userMerchant=userMerchantService.getUserMerchantByName(userName);
            map.put("regTime",userMerchant.getRegTime());
            map.put("shopName",userMerchant.getShopName());
            map.put("shopDescription",userMerchant.getShopDesc());
            map.put("status",0);
            map.put("info","Success to get the information of the merchant.");
        }
        else{
            map.put("status",1);
            map.put("info","This is not a merchant account.");
        }
        return JSONUtil.toJSON(map);
    }

    @RequestMapping("getSession")
    @ResponseBody
    public String getSession(@ModelAttribute String userId, @ModelAttribute String username, ModelMap modelMap) throws JsonProcessingException {
        //Map<String,Object> map = new HashMap<>();
        return JSONUtil.toJSON(modelMap);
    }

    @RequestMapping("setSession")
    @ResponseBody
    public String setSession(String username, String type, String userId, ModelMap modelMap) throws JsonProcessingException {
        modelMap.addAttribute("username", username);
        modelMap.addAttribute("type", type);
        modelMap.addAttribute("userId", userId);
        return modelMap.toString();
    }
}
