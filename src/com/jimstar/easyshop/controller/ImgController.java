package com.jimstar.easyshop.controller;

import com.jimstar.easyshop.service.ImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Blob;

/**
 * Created by 63289 on 2016/12/23.
 */
@Controller
@RequestMapping("/Img")
public class ImgController {
    private final ImgService imgService;

    @Autowired
    public ImgController(ImgService imgService) {
        this.imgService = imgService;
    }

    @RequestMapping("/add")
    public String add(){
        return "addImage";
    }
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ResponseBody
    public String edit(@RequestBody Blob value,ModelMap modelMap){
        boolean success=imgService.addAnImageByValue(value);
        if(success) return "editImage";
        modelMap.addAttribute("info","Fail to save the image");
        return "error";
    }

    @RequestMapping(value = "/get")
    @ResponseBody
    public String get(@RequestBody String id, ModelMap modelMap){
        Blob value=imgService.getImgById(id).getValue();
        modelMap.addAttribute("value",value);
        return "getImage";
    }
}
