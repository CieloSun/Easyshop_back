package com.jimstar.easyshop.service;

import com.jimstar.easyshop.dao.ItemDao;
import com.jimstar.easyshop.dao.UserMerchantDao;
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
    private final ItemDao itemDao;
    private final UserMerchantDao userMerchantDao;

    @Autowired
    public ItemService(ItemDao itemDao, UserMerchantDao userMerchantDao) {
        this.itemDao = itemDao;
        this.userMerchantDao = userMerchantDao;
    }

    public ItemDao getItemDao() {
        return itemDao;
    }

    public Item createItemByInf(String name, Float price, Integer count, UserMerchant userMerchant, String description, List<Img> imgs) {
        Item item = new Item();
        String iid = UUIDGenerator.genShort();
        Integer ver = 0;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
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

    public Item getByUid(String uid) {
        return itemDao.selectByUid(uid);
    }

    public Item getByIid(String iid) {
        return itemDao.selectLatestByIid(iid);
    }

    public Item changeCountByChangeNumber(String uid, Integer changeNumber) {
        Item item = itemDao.selectByUid(uid);
        Integer newCount = item.getCount() + changeNumber;
        if (newCount < 0)
            return null;
        else {
            item.setCount(newCount);
            if (itemDao.update(item))
                return item;
            else
                return null;
        }
    }

    public Item updatedByIid(String iid, String name, Float price, String description, List<Img> imgs) {
        Item itemOld = itemDao.selectLatestByIid(iid);
        Item item = new Item();
        item.setIid(itemOld.getIid());
        item.setVer(itemOld.getVer() + 1);
        item.setCreateTime(itemOld.getCreateTime());
        item.setCount(itemOld.getCount());
        item.setUserMerchant(itemOld.getUserMerchant());

        if (name != null)
            item.setName(name);
        else item.setName(itemOld.getName());
        if (price != null)
            item.setPrice(price);
        else item.setPrice(itemOld.getPrice());
        if (description != null)
            item.setDescription(description);
        else item.setDescription(itemOld.getDescription());
        if (imgs.isEmpty() || imgs == null)
            item.setImgs(itemOld.getImgs());
        else item.setImgs(imgs);
        return itemDao.add(item);
    }

    public boolean deleteByUid(String uid) {
        return itemDao.deleteByUid(uid);
    }

    public boolean deleteByIid(String iid) {
        return itemDao.deleteByIid(iid);
    }

    public List<Item> selectByMatchName(String pattern) {
        return itemDao.selectByMatchName(pattern);
    }

    public List<Item> selectByMatchMerchant(String merchantName) {
        UserMerchant userMerchant = userMerchantDao.selectByName(merchantName);
        return itemDao.selectByMatchMerchant(userMerchant);
    }
}
