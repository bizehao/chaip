package com.world.chaip.business;

import java.io.*;
import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;

public class StaticConfig {

    public static String ipAddress;

    static {
        try {
            ipAddress = getIp();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getIp() throws IOException {

        ResourceBundle rb = ResourceBundle.getBundle("ipConfig".trim());
        // 通过资源包拿到所有的key
        Enumeration<String> allKey = rb.getKeys();
        // 遍历key 得到 value
        while (allKey.hasMoreElements()) {
            String key = allKey.nextElement();
            String value = (String) rb.getString(key);
            ipAddress = value;
        }
        return ipAddress;
    }
}
