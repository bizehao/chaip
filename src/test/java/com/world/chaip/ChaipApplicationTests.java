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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChaipApplicationTests {

	@Autowired
	RainfallService service;

	@Test
	public void contextLoads1() {
		String a="abcdefghi";
		CharSequence b = a.subSequence(2,5);
		System.out.println(b);
	}
}
