package com.jimstar.easyshop.controller;

import com.jimstar.easyshop.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;

import java.util.Map;

/**
 * Created by 63289 on 2016/12/24.
 */
@Controller
@RequestMapping("/Item")
public class ItemController {
    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @RequestMapping("/list")
    public ModelAndView list(Map<String,Object> map, ModelMap modelMap){
        ModelAndView modelAndView=new ModelAndView();
        //TODO
        return modelAndView;
    }
    @RequestMapping("/get")
    public ModelAndView get(Map<String,Object> map,ModelMap modelMap){
        ModelAndView modelAndView=new ModelAndView();
        //TODO
        return modelAndView;
    }
}
