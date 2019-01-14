package com.world.chaip.controller.Excel;

import com.world.chaip.business.ExportExecls;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2019/1/10 21:22
 */
@RestController
@RequestMapping("test")
public class Test {

	@GetMapping("excel")
	public void excel(HttpServletResponse response) {
		System.out.println("测试成功");
		List<Object[]> datas = new ArrayList<>();
		Object[] objects;
		for (int i = 0; i < 80; i++) {
			objects = new Object[4];
			objects[0] = i;
			objects[1] = "测试" + i;
			objects[2] = i;
			if (i % 2 == 0) {
				objects[3] = "男";
			} else {
				objects[3] = "女";
			}
			datas.add(objects);
		}

		ExportExecls exportExecls = new ExportExecls(response, "学生表", datas, "2018年", 40,4, ExportExecls.Direction.TRANSVERSE);
		exportExecls.export(new ExportExecls.ColumnAndHead() {
			@Override
			public void colHeadHandler(Sheet sheet) {
				CellStyle style = exportExecls.getContentStyle(sheet.getWorkbook());
				Row colTitleRow = sheet.createRow(3);
				Cell colTitle0 = colTitleRow.createCell(0);
				colTitle0.setCellValue("编号");
				colTitle0.setCellStyle(style);

				Cell colTitle1 = colTitleRow.createCell(1);
				colTitle1.setCellValue("姓名");
				colTitle1.setCellStyle(style);

				Cell colTitle2 = colTitleRow.createCell(2);
				colTitle2.setCellValue("年龄");
				colTitle2.setCellStyle(style);

				Cell colTitle3 = colTitleRow.createCell(3);
				colTitle3.setCellValue("性别");
				colTitle3.setCellStyle(style);
			}
		});
	}

	class Student {
		private int id;
		private String name;
		private int age;
		private String descript;

		public Student(int id, String name, int age, String descript) {
			this.id = id;
			this.name = name;
			this.age = age;
			this.descript = descript;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		public String getDescript() {
			return descript;
		}

		public void setDescript(String descript) {
			this.descript = descript;
		}
	}
}
