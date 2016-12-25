package com.jimstar.easyshop.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jimstar.easyshop.entity.Img;
import com.jimstar.easyshop.entity.Item;
import com.jimstar.easyshop.entity.Order;
import com.jimstar.easyshop.entity.UserMerchant;
import com.jimstar.easyshop.service.ImgService;
import com.jimstar.easyshop.service.ItemService;
import com.jimstar.easyshop.service.UserMerchantService;
import com.jimstar.easyshop.util.JSONUtil;
import org.omg.CORBA.INTERNAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.*;


@Controller
@RequestMapping("Item")
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

    @RequestMapping(value="list",method = RequestMethod.POST)
    @ResponseBody
    public String list(@RequestBody String request) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        try {
            map = JSONUtil.parseMap(request);
            String pattern = (String) map.get("pattern");
            Integer offset = (Integer) map.get("offset");
            Integer count = (Integer) map.get("count");
            List<Item> result = itemService.selectByMatchName(pattern);
            List<String> idList = new ArrayList<>();
            if (offset > result.size()) {
                throw new Exception("Invalid offset");
            }
            Integer maxItemNo = Integer.min(offset + count, result.size());
            for (int i = offset; i < maxItemNo; i++) {
                idList.add(result.get(i).getUid());
            }
            map.put("itemUidList", idList);
            map.put("total", result.size());
            map.put("status", 0);
        } catch (Exception e) {
            map.put("error", e.getMessage());
            map.put("status", 1);
        }
        return JSONUtil.toJSON(map);
    }

    @RequestMapping(value = "get", method = RequestMethod.GET)
    @ResponseBody
    public String get(String uid) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        Item item = itemService.getByUid(uid);
        if (item == null) {
            map.put("status", 1);
            map.put("error", "No such item");
        } else {
            map.put("status", 0);
            map.put("item", item);
        }
        return JSONUtil.toJSON(map);
    }

    @RequestMapping(value = "getByIid", method = RequestMethod.GET)
    @ResponseBody
    public String getByIid(String iid) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        Item item = itemService.getByIid(iid);
        if (item == null) {
            map.put("status", 1);
            map.put("error", "No such item");
        } else {
            map.put("status", 0);
            map.put("item", item);
        }
        return JSONUtil.toJSON(map);
    }

    @RequestMapping(value = "get", method = RequestMethod.POST)
    @ResponseBody
    public String getJson(@RequestBody String request) throws IOException {
        String uid = (String) JSONUtil.parseMap(request).get("uid");
        return get(uid);
    }

    @RequestMapping(value="add",method = RequestMethod.POST)
    @ResponseBody
    public String add(@RequestBody String request) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        try {
            map = JSONUtil.parseMap(request);
            Integer userMerchantId = (Integer) map.get("userMerchantId");
            UserMerchant userMerchant = userMerchantService.getUserMerchantById(userMerchantId);
            if (userMerchant == null)
                throw new Exception("Unknown Merchant ID " + userMerchantId);

            List<String> imgRawList = (List<String>) map.get("rawImg");
            List<Img> imgList = new ArrayList<>();
            for (String imgRaw : imgRawList) {
                Img img = new Img();
                img.setValue(imgRaw);
                imgList.add(img);
            }

            Item newItem = itemService.createItemByInf((String) map.get("name"),
                    ((Double) map.get("price")).floatValue(), (Integer) map.get("count"), userMerchant,
                    (String) map.get("description"), imgList);
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

    //获取count与原count进行计算
    @RequestMapping(value="changeCount",method = RequestMethod.POST)
    @ResponseBody
    public String changeCount(@RequestBody String mapString) throws IOException {
        Map map = JSONUtil.parseMap(mapString);
        String itemUid = (String) map.get("itemUid");
        Integer count = (Integer) map.get("count");
        Item item = itemService.changeCountByChangeNumber(itemUid, count);
        if (item != null) {
            map.put("status", 0);
            map.put("info", "Success to changeCount");
        } else {
            map.put("status", 0);
            map.put("info", "Fail to changeCount");
        }
        return JSONUtil.toJSON(map);
    }

    @RequestMapping(value = "edit",method = RequestMethod.POST)
    @ResponseBody
    public String edit(@RequestBody String mapString) throws Exception {
        Map map = JSONUtil.parseMap(mapString);
        String iid = (String) map.get("itemIid");
        String name = (String) map.get("name");
        Float price = (Float) map.get("price");
        Integer count = (Integer) map.get("count");
        String userMerchantName = (String) map.get("userMerchantName");
        String description = (String) map.get("description");
        Map imgs = (Map<String, Img>) map.get("imgs");
        List<Img> imgList = new ArrayList<Img>();
        for (Object img : imgs.values()) {
            imgList.add((Img) img);
        }
        UserMerchant userMerchant = userMerchantService.getUserMerchantByName(userMerchantName);
        Item item = itemService.updatedByIid(iid, name, price, count, userMerchant, description, imgList);
        if(item!=null){
            map.put("status",0);
            map.put("itemUid",item.getUid());
            map.put("info","Success to edit the item.");
        }
        else{
            map.put("status",1);
            map.put("info","Error to generate the new item.");
        }
        return JSONUtil.toJSON(map);
    }
}
