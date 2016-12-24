package com.jimstar.easyshop.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jimstar.easyshop.entity.Item;
import com.jimstar.easyshop.entity.UserMerchant;
import com.jimstar.easyshop.service.ImgService;
import com.jimstar.easyshop.service.ItemService;
import com.jimstar.easyshop.service.UserMerchantService;
import com.jimstar.easyshop.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/Item")
public class ItemController {
    private final ItemService itemService;
    private final UserMerchantService userMerchantService;
    private final ImgService imgService;

    @Autowired
    public ItemController(ItemService itemService, UserMerchantService userMerchantService, ImgService imgService) {
        this.itemService = itemService;
        this.userMerchantService = userMerchantService;
        this.imgService = imgService;
    }

    @RequestMapping("/list")
    public ModelAndView list(String pattern, Integer offset, Integer count, ModelMap modelMap) {
        ModelAndView modelAndView=new ModelAndView();
        /*try {
            List<Item> result = itemService.selectByMatchName(pattern);
            List<String> idList = new ArrayList<String>();
            if (offset > result.size()) {
                throw new Exception("Invalid offset");
            }
            for(int i = offset ; i < offset + count ; i++) {
                idList.add(result.get(i).getIid());
            }
            modelAndView.addAttribute("item_list",idList);
            modelAndView.addAttribute("offset",offset);
            modelAndView.addAttribute("count",idList.size());
            modelAndView.addAttribute("total",result.size());
            modelAndView.addAttribute("status",0);
        } catch (Exception e) {
            modelAndView.addAttribute("error",e.getMessage());
            modelAndView.addAttribute("status",1);
        }*/
        return modelAndView;
    }

    @RequestMapping("/get")
    public ModelAndView get(Map<String,Object> map,ModelMap modelMap){
        ModelAndView modelAndView=new ModelAndView();
        //TODO
        return modelAndView;
    }

    @RequestMapping("add")
    @ResponseBody
    public String add(@RequestBody String request, ModelMap modelMap) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Map<String, Object> reqMap = JSONUtil.parseMap(request);
            Integer userMerchantId = (Integer) reqMap.get("userMerchantId");
            UserMerchant userMerchant = userMerchantService.getUserMerchantById(userMerchantId);

            //无法使用byte[]
            //TODO 暂时不添加Img
            /*List<String> imgRawList = (List<String>) reqMap.get("rawImg");
            List<Img> imgList = new ArrayList<Img>();
            for(int i = 0 ; i < imgRawList.size() ; i++) {
                Img img = imgService.addAnImageByValue(imgRawList.get(i).getBytes());
                if (img == null)
                    throw new Exception("Failed to add image " + i);
                else
                    imgList.add(img);
            }*/
            if (userMerchant == null)
                throw new Exception("Unknown Merchant ID " + userMerchantId);

            Item newItem = itemService.createItemByInf((String) reqMap.get("name"),
                    ((Double) reqMap.get("price")).floatValue(), (Integer) reqMap.get("count"), userMerchant,
                    (String) reqMap.get("description"), null);
            if (newItem != null) {
                map.put("status", 0);
                map.put("item", newItem);
            } else
                throw new Exception("Failed to add new Item");
        } catch (IOException e) {
            map.put("status", 2);
            map.put("error", "Invalid request format");
        } catch (Exception e) {
            map.put("status", 1);
            map.put("error", e.getMessage());
        }
        return JSONUtil.toJSON(map);
    }
}
