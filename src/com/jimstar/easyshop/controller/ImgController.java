package com.jimstar.easyshop.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jimstar.easyshop.entity.Img;
import com.jimstar.easyshop.service.ImgService;
import com.jimstar.easyshop.util.DigestUtil;
import com.jimstar.easyshop.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import java.io.IOException;
import java.lang.String;
import java.util.Map;

import static com.sun.corba.se.spi.activation.IIOP_CLEAR_TEXT.value;

@Controller
@RequestMapping("/Img")
public class ImgController {
    private final ImgService imgService;

    @Autowired
    public ImgController(ImgService imgService) {
        this.imgService = imgService;
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public String add(@RequestBody String mapString) throws Exception {
        Map map=JSONUtil.parseMap(mapString);
        String value=(String) map.get("value");
        Img img=imgService.addAnImageByValue(value);
        String id=img.getId();
        if (id != null) {
            map.put("status",0);
            map.put("info","Success to save the image");
            map.put("id", id);
        }
        else{
            map.put("status",1);
            map.put("info","Fail to save the image");
        }
        return JSONUtil.toJSON(map);
    }

    @RequestMapping(value = "get", method = RequestMethod.POST)
    @ResponseBody
    public String get(@RequestBody String mapString) throws Exception {
        Map map=JSONUtil.parseMap(mapString);
        String id=(String) map.get("id");
        System.err.println("ImgID=" + id);
        String value = imgService.getImgById(id).getValue();
        map.put("value", value);
        return JSONUtil.toJSON(map);
    }
}
