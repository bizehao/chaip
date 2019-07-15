package com.world.chaip;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.world.chaip.entity.Rainfall;
import com.world.chaip.entity.newRsvr.RevrXunQi;
import com.world.chaip.entity.report.Pptn;
import com.world.chaip.entity.report.River;
import com.world.chaip.entity.report.Rsvr;
import com.world.chaip.entity.report.gson.PptnGson;
import com.world.chaip.mapper.ReportMapper;
import com.world.chaip.mapper.RsvrfallMapper;
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
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
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
        //String ipAddress = InetAddress.getLocalHost().getHostAddress();
        //System.out.println(ipAddress);
        String kk = getLocalIP();
		InetAddress.getLocalHost().getAddress();
		System.out.println(kk);
    }

	public static String getLocalIP(){
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		byte[] ipAddr = addr.getAddress();
		String ipAddrStr = "";
		for (int i = 0; i < ipAddr.length; i++) {
			if (i > 0) {
				ipAddrStr += ".";
			}
			ipAddrStr += ipAddr[i] & 0xFF;
		}
		//System.out.println(ipAddrStr);
		return ipAddrStr;
	}

	@Autowired
	RsvrfallMapper rsvrfallMapper;

	@Test
	public void testSql(){
		List<RevrXunQi> aa = rsvrfallMapper.getRsvrFS();
		System.out.println(aa.size());
	}
}
