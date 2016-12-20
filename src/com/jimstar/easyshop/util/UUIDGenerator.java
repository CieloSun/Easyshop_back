package com.jimstar.easyshop.util;

import java.util.UUID;

public class UUIDGenerator {
    public static String gen() {
        return UUID.randomUUID().toString();
    }

    public static String genShort() {
        String str = gen();
        // 去掉"-"符号
        return str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) + str.substring(24);
    }
}
