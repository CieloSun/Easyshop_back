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
import org.springframework.web.portlet.ModelAndView;

import java.util.Map;

/**
 * Created by 63289 on 2016/12/23.
 */
@Controller
@RequestMapping("/User")
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
            if(userCustomerService.checkPasswordByNameAndPwd(username,password)){
                UserCustomer userCustomer=userCustomerService.getUserCustomerByName(username);
                Integer id=userCustomer.getId();
                modelMap.addAttribute("id",id);
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
                Integer id=userMerchant.getId();
                modelMap.addAttribute("id",id);
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
    public ModelAndView register(Map<String,Object> map,ModelMap modelMap) throws Exception{
        ModelAndView modelAndView=new ModelAndView();
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
            }
            else{
                modelMap.addAttribute("status",1);
                modelMap.addAttribute("info","Cannot register, maybe username has been used.");
            }
        }
        else if(type.equals("merchant")){
            String shopName=(String) map.get("shopName");
            String shopDescription=(String) map.get("shopDescription");
            if(userMerchantService.addUserMerchantByInfo(username,password,shopName,shopDescription)){
                UserMerchant userMerchant=userMerchantService.getUserMerchantByName(username);
                Integer id=userMerchant.getId();
                modelMap.addAttribute("id",id);
                modelMap.addAttribute("status",0);
                modelMap.addAttribute("info","Success to register!");
            }
            else{
                modelMap.addAttribute("info","Cannot register, maybe username has been used.");
                modelMap.addAttribute("status",1);
            }
        }
        else{
            modelMap.addAttribute("status",1);
            modelMap.addAttribute("info","Type is wrong.");
        }
        modelAndView.addAllObjects(modelMap);
        return modelAndView;
    }
}
