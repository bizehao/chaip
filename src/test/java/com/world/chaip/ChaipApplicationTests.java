package com.world.chaip;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.world.chaip.entity.Rainfall;
import com.world.chaip.entity.report.Pptn;
import com.world.chaip.entity.report.River;
import com.world.chaip.entity.report.Rsvr;
import com.world.chaip.entity.report.gson.PptnGson;
import com.world.chaip.mapper.ReportMapper;
import com.world.chaip.service.RainfallService;
import com.world.chaip.service.ReportService;
import com.world.chaip.util.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChaipApplicationTests {

	@Autowired
	RainfallService service;

	@Test
	public void contextLoads1() throws IOException {
			/*Properties pro = new Properties();
			InputStream in = new BufferedInputStream( new FileInputStream("ipConfig.properties"));
			pro.load(in);
			String ipAddress = pro.getProperty("ip");
			System.out.println(ipAddress);
			in.close();*/
		// 获得资源包
		ResourceBundle rb = ResourceBundle.getBundle("ipConfig".trim());
		// 通过资源包拿到所有的key
		Enumeration<String> allKey = rb.getKeys();
		// 遍历key 得到 value
		while (allKey.hasMoreElements()) {
			String key = allKey.nextElement();
			String value = (String) rb.getString(key);
			System.out.println(value);
		}
	}
}
