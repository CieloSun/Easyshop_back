package com.jimstar.easyshop.util;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by 63289 on 2016/12/21.
 */
public class MD5Generator {
    public static String EncoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //确定计算方法
        MessageDigest md5=MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        //加密后的字符串
        String newstr=base64en.encode(md5.digest(str.getBytes("utf-8")));
        return newstr;
    }

    public static boolean checkPassword(String inputPasswd,String storePasswd) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        if(EncoderByMd5(inputPasswd).equals(storePasswd))
            return true;
        else
            return false;
    }
}
