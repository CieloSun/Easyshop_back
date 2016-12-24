package com.jimstar.easyshop.service;

import com.jimstar.easyshop.dao.UserMerchantDao;
import com.jimstar.easyshop.entity.UserMerchant;
import com.jimstar.easyshop.util.DigestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class UserMerchantService {
    private final UserMerchantDao userMerchantDao;

    @Autowired
    public UserMerchantService(UserMerchantDao userMerchantDao) {
        this.userMerchantDao = userMerchantDao;
    }

    public boolean addUserMerchantByInfo(String name, String password, String shopName, String shopDesc) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try {
            String pwdDigest = DigestUtil.Md5Encoder(password);
            UserMerchant userMerchant = new UserMerchant();
            userMerchant.setRegTime(timestamp);
            userMerchant.setName(name);
            userMerchant.setPwdDigest(pwdDigest);
            userMerchant.setShopName(shopName);
            userMerchant.setShopDesc(shopDesc);
            return userMerchantDao.add(userMerchant);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkPasswordByName(String name, String password) {
        UserMerchant userMerchant = userMerchantDao.selectByName(name);
        try {
            return DigestUtil.checkPassword(password, userMerchant.getPwdDigest());
        } catch (Exception e) {
            System.out.println("MD5 error");
            e.printStackTrace();
            return false;
        }
    }

    public boolean changePasswordByName(String name, String password) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try {
            String pwdDigest = DigestUtil.Md5Encoder(password);
            UserMerchant userMerchant = userMerchantDao.selectByName(name);
            userMerchant.setPwdDigest(pwdDigest);
            return userMerchantDao.update(userMerchant);
        } catch (Exception e) {
            System.out.println("MD5 error");
            e.printStackTrace();
            return false;
        }
    }

    public boolean changeShopByName(String name,String shopName, String shopDesc) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        UserMerchant userMerchant = userMerchantDao.selectByName(name);
        userMerchant.setShopName(shopName);
        userMerchant.setShopDesc(shopDesc);
        return userMerchantDao.update(userMerchant);
    }

    public UserMerchant getUserMerchantByName(String name) {
        return userMerchantDao.selectByName(name);
    }

}


