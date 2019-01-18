package com.world.chaip.business;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;

import static java.net.InetAddress.getLocalHost;

public class StaticConfig {

    public static String ipAddress;

    public static String autograph;

    static {
        try {
            ipAddress = getIp();
            autograph = getAutograph();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取ip
     * @return
     * @throws IOException
     */
    public static String getIp() throws IOException {

        String ipAddress = Inet4Address.getLocalHost().getHostAddress();
        String duankou = null;
        ResourceBundle rb = ResourceBundle.getBundle("ipConfig".trim());
        duankou = rb.getString("ip");

        // 通过资源包拿到所有的key
        //Enumeration<String> allKey = rb.getKeys();
        // 遍历key 得到 value
        /*while (allKey.hasMoreElements()) {
            String key = allKey.nextElement();
            String value = rb.getString(key);
            duankou = value;
        }*/
        //return ipAddress+duankou;
        return duankou;
    }

    /**
     * 获取签名
     */
    public static String getAutograph() throws IOException {

        String autograph = null;
        ResourceBundle rb = ResourceBundle.getBundle("ipConfig".trim());
        autograph = new String(rb.getString("autograph").getBytes("ISO-8859-1"), "UTF8");
        return autograph;
    }
}
