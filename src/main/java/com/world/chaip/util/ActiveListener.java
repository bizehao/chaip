package com.world.chaip.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @Author: 毕泽浩
 * @Date: 19-3-26 上午9:28
 * @Descript
 */

//@Component
public class ActiveListener implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(ActiveListener.class);

    @Override
    public void run(ApplicationArguments applicationArguments) {
        ResourceBundle rb = ResourceBundle.getBundle("ipConfig".trim());
        String tomcat = rb.getString("tomcat");
        String tomcatIp = rb.getString("tomcatIp");
        System.out.println(tomcatIp);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    URL url = new URL(tomcatIp);
                    logger.error("开始");
                    int v;
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    con.setConnectTimeout(2000);
                    con.setReadTimeout(2000);
                    byte[] bb = new byte[1];
                    int index = 0;
                    InputStream in = con.getInputStream();
                    while ((v = in.read()) != -1) {
                        bb[index++] = (byte) v;
                    }
                    String nn = new String(bb);
                    int mm = Integer.parseInt(nn);
                    System.out.println(mm);
                    con.disconnect();
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.error("执行");
                    try {
                        callCommand(tomcat);
                    } catch (IOException m) {
                        System.out.println("执行命令时出错：" + m.getMessage());
                    }
                }
            }
        }, 60000, 300000);
    }

    /**
     * 执行命令
     *
     * @throws IOException
     */
    private void callCommand(String command) throws IOException {

        Runtime runtime = Runtime.getRuntime();//返回与当前的Java应用相关的运行时对象
        //指示Java虚拟机创建一个子进程执行指定的可执行程序，并返回与该子进程对应的Process对象实例
        runtime.exec(command);


        /*try {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec("ifconfig");
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
            String line = null;
            StringBuilder build = new StringBuilder();
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                build.append(line);
            }
            System.out.println( build.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }*/


    }
}
