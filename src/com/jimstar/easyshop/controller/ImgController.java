package com.jimstar.easyshop.controller;

import com.jimstar.easyshop.service.ImgService;
import com.jimstar.easyshop.util.DigestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import java.io.InputStream;

@Controller
@RequestMapping("/Img")
public class ImgController {
    private final ImgService imgService;

    @Autowired
    public ImgController(ImgService imgService) {
        this.imgService = imgService;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView add(@RequestBody InputStream value, ModelMap modelMap) {
        ModelAndView modelAndView=new ModelAndView();
        String id = null;
        if (id != null) {
            modelMap.addAttribute("status",0);
            modelMap.addAttribute("info","Success to save the image");
            modelMap.addAttribute("id", id);
        }
        else{
            modelMap.addAttribute("status",1);
            modelMap.addAttribute("info","Fail to save the image");
        }
        modelAndView.addAllObjects(modelMap);
        return modelAndView;
    }

    @RequestMapping(value = "/get")
    @ResponseBody
    public ModelAndView get(String id, ModelMap modelMap){
        ModelAndView modelAndView=new ModelAndView();
        System.err.println("ImgID=" + id);
        byte[] value = imgService.getImgById(id).getValue();
        modelMap.addAttribute("valueCRC32", DigestUtil.Crc32Encode(value));
        modelAndView.addAllObjects(modelMap);
        return modelAndView;
    }

}
