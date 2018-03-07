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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChaipApplicationTests {

	@Autowired
	RainfallService service;

	@Test
	public void contextLoads1() throws ParseException {
		String dateStart = "2018-03-07 08";
		Date dateS = DateUtils.parse(dateStart, "yyyy-MM-dd hh");
		Calendar now = Calendar.getInstance();
		now.setTime(dateS);
		Date mn = now.getTime();
		SimpleDateFormat kl = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String s = kl.format(dateS);
		System.out.println(dateS);
		System.out.println(s);
	}
}
