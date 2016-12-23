package com.jimstar.easyshop.service;

import com.jimstar.easyshop.dao.ItemDao;
import com.jimstar.easyshop.entity.Img;
import com.jimstar.easyshop.entity.Item;
import com.jimstar.easyshop.entity.UserMerchant;
import com.jimstar.easyshop.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ItemService {
    @Autowired
    private ItemDao itemDao;

    public ItemService() {
    }

    public boolean createItemByInf(String name, Float price, Integer count, UserMerchant userMerchant, String description, List<Img> imgs){
        Item item=new Item();
        String iid= UUIDGenerator.genShort();
        Integer ver=0;
        Timestamp timestamp=new Timestamp(System.currentTimeMillis());
        item.setIid(iid);
        item.setVer(ver);
        item.setName(name);
        item.setPrice(price);
        item.setCount(count);
        item.setUserMerchant(userMerchant);
        item.setCreateTime(timestamp);
        item.setDescription(description);
        item.setImgs(imgs);
        return itemDao.add(item);
    }
    public Item getByUid(String uid){
        return itemDao.selectByUid(uid);
    }

    public boolean changeCountByChangeNumber(String uid, Integer changeNumber){
        Item item=itemDao.selectByUid(uid);
        Integer newCount=item.getCount()+changeNumber;
        if(newCount>=0){
            item.setCount(newCount);
            return itemDao.update(item);
        }
        return false;
    }

    public boolean updatedByIid(String iid,String name, Float price, Integer count, UserMerchant userMerchant, String description, List<Img> imgs) {
        Item itemOld=itemDao.selectLatestByIid(iid);
        Item item=new Item();
        item.setIid(itemOld.getIid());
        item.setVer(itemOld.getVer()+1);
        item.setCreateTime(itemOld.getCreateTime());

        item.setName(name);
        item.setPrice(price);
        item.setCount(count);
        item.setUserMerchant(userMerchant);
        item.setDescription(description);
        item.setImgs(imgs);
        return itemDao.add(item);
    }

    public boolean deleteByUid(String uid){
        return itemDao.deleteByUid(uid);
    }

    public boolean deleteByIid(String iid){
        return itemDao.deleteByIid(iid);
    }

    public ItemDao getItemDao() {
        return itemDao;
    }

    public void setItemDao(ItemDao itemDao) {
        this.itemDao = itemDao;
    }
}
