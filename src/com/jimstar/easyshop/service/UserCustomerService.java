package com.jimstar.easyshop.service;

import com.jimstar.easyshop.dao.UserCustomerDao;
import com.jimstar.easyshop.entity.UserCustomer;
import com.jimstar.easyshop.util.MD5Generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * Created by 63289 on 2016/12/21.
 */
@Service
public class UserCustomerService {
    @Autowired
    private UserCustomerDao userCustomerDao;
    public boolean addUserCustomerByNameAndPwd(String name, String password){
        Timestamp timestamp=new Timestamp(System.currentTimeMillis());
        try {
            String pwdDigest= MD5Generator.EncoderByMd5(password);
            UserCustomer userCustomer=new UserCustomer();
            userCustomer.setRegTime(timestamp);
            userCustomer.setName(name);
            userCustomer.setPwdDigest(pwdDigest);
            return userCustomerDao.add(userCustomer);
        }catch (Exception e){
            System.out.println("MD5 error");
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkPasswordByNameAndPwd(String name, String password){
        UserCustomer userCustomer=userCustomerDao.selectByName(name);
        try {
            return MD5Generator.checkPassword(password,userCustomer.getPwdDigest());
        }catch (Exception e){
            System.out.println("MD5 error");
            e.printStackTrace();
            return false;
        }
    }

    public boolean changePasswordByNameAndPwd(String name, String password){
        Timestamp timestamp=new Timestamp(System.currentTimeMillis());
        try {
            String pwdDigest= MD5Generator.EncoderByMd5(password);
            UserCustomer userCustomer=userCustomerDao.selectByName(name);
            userCustomer.setPwdDigest(pwdDigest);
            return userCustomerDao.update(userCustomer);
        }catch (Exception e){
            System.out.println("MD5 error");
            e.printStackTrace();
            return false;
        }
    }
}
