package com.jimstar.easyshop.service;

import com.jimstar.easyshop.dao.UserMerchantDao;
import com.jimstar.easyshop.entity.UserMerchant;
import com.jimstar.easyshop.util.MD5Generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * Created by 63289 on 2016/12/21.
 */
@Service
public class UserMerchantService {
    @Autowired
    private UserMerchantDao userMerchantDao;

    public boolean addUserMerchantByNameAndPwd(String name, String password, String shopName, String shopDesc) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try {
            String pwdDigest = MD5Generator.EncoderByMd5(password);
            UserMerchant userMerchant = new UserMerchant();
            userMerchant.setRegTime(timestamp);
            userMerchant.setName(name);
            userMerchant.setPwdDigest(pwdDigest);
            userMerchant.setShopName(shopName);
            userMerchant.setShopDesc(shopDesc);
            return userMerchantDao.add(userMerchant);
        } catch (Exception e) {
            System.out.println("MD5 error");
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkPasswordByNameAndPwd(String name, String password) {
        UserMerchant userMerchant = userMerchantDao.selectByName(name);
        try {
            return MD5Generator.checkPassword(password, userMerchant.getPwdDigest());
        } catch (Exception e) {
            System.out.println("MD5 error");
            e.printStackTrace();
            return false;
        }
    }

    public boolean changePasswordByNameAndPwd(String name, String password) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try {
            String pwdDigest = MD5Generator.EncoderByMd5(password);
            UserMerchant userMerchant = userMerchantDao.selectByName(name);
            userMerchant.setPwdDigest(pwdDigest);
            return userMerchantDao.update(userMerchant);
        } catch (Exception e) {
            System.out.println("MD5 error");
            e.printStackTrace();
            return false;
        }
    }

    public boolean changeShopByNameAndPwd(String name,String shopName, String shopDesc) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        UserMerchant userMerchant = userMerchantDao.selectByName(name);
        userMerchant.setShopName(shopName);
        userMerchant.setShopDesc(shopDesc);
        return userMerchantDao.update(userMerchant);
    }
}


