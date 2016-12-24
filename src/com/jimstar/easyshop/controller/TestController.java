package com.jimstar.easyshop.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/Test")
public class TestController {
    @RequestMapping(value = "test")
    public ModelAndView test(@RequestBody Map<String, Object> map) {
        System.out.println("EXEC");
        System.out.println(map.get("username").toString());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("username", map.get("username"));
        return modelAndView;
    }
}
