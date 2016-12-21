package com.jimstar.easyshop.util;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DigestUtil {

    private static MessageDigest Md5Instance;

    static {
        try {
            Md5Instance = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static String Md5Encoder(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //确定计算方法
        BASE64Encoder base64en = new BASE64Encoder();
        //加密后的字符串
        return base64en.encode(Md5Instance.digest(str.getBytes("utf-8")));
    }

    public static boolean checkPassword(String inputPasswd, String digest) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return Md5Encoder(inputPasswd).equals(digest);
    }
}
