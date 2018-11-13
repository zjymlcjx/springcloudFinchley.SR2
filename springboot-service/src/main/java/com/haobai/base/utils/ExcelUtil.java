package com.haobai.base.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {

	private static int[] maxWidth;
	private static String[] head;
	// Excel默认字号为10磅
	private static final int DEFAULT_FONT_SIZE = 10;

	/*
	 * 通过给出的数据，生成Excle clazz 数据类型(.class) list 数据 file 写入的文件
	 */
	public static <T, K> void createExcle(Class<T> clazz, List<K> list, File file) {
		Workbook wb = null;

		if (file.getName().endsWith("xls")) {
			wb = new HSSFWorkbook();
		} else if (file.getName().endsWith("xlsx")) {
			wb = new XSSFWorkbook();
		} else {
			return;
		}

		Sheet sheet = wb.createSheet("sheet1");
		// 根据属性名称生成表头
		createHead(sheet, clazz, wb);
		// 插入数据
		insertData(list, sheet, wb);
		// 设置列宽
		for (int i = 0; i < maxWidth.length; i++) {
			// System.out.println(maxWidth[i]);
			sheet.setColumnWidth(i, maxWidth[i]);
		}

		try {
			OutputStream os = new FileOutputStream(file);
			wb.write(os);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * 生成Excle表的表头 sheet 工作表 c 数据类型 wb 工作簿
	 */
	public static <T> void createHead(Sheet sheet, Class<T> c, Workbook wb) {
		Map<String, String> m = null;
		try {

			Field nameField = c.getField("nameMap");
			m = (Map<String, String>) nameField.get(c);
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			System.out.println("NoSuchFieldException");
			e.printStackTrace();
		} catch (SecurityException e) {
			System.out.println("SecurityException");
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			System.out.println("IllegalArgumentException");
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			System.out.println("IllegalAccessException");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String[] classNames = c.getName().split("\\.");
		String className = classNames[classNames.length - 1];

		Field[] fs = c.getDeclaredFields();
		Row row = sheet.createRow(0);
		// 表头样式
		CellStyle style = wb.createCellStyle();
		Font font = wb.createFont();
		font.setBold(true);
		// font.setFontName("华文行楷");
		font.setFontHeight((short) 300);
		// font.setFontHeight((short) 20);
		style.setFont(font);
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		// row.setRowStyle(style);

		// row.setHeightInPoints(30);

		maxWidth = new int[fs.length - 1];
		head = new String[fs.length - 1];

		for (int i = 0; i < fs.length - 1; i++) {
			Field f = fs[i];
			// if(f.getName().equals("nameMap")){
			// continue;
			// }
			String key = f.getName();
			Cell cell = row.createCell(i, CellType.STRING);
			cell.setCellStyle(style);
			String title = m.get(key) == null ? "无标题" : m.get(key);
			cell.setCellValue(title);

			head[i] = f.getName();
			setColumnWidth(cell, wb, title, i);
		}
	}

	/*
	 * 生成Excle表的表头 sheet 工作表 l 数据类型 wb 工作簿
	 */
	public static <T> void insertData(List<T> l, Sheet sheet, Workbook wb) {
		for (int i = 0; i < l.size(); i++) {
			Row row = sheet.createRow(i + 1);
			T data = l.get(i);
			Class clazz = data.getClass();
			Field[] fields = clazz.getDeclaredFields();

			for (int j = 0; j < head.length; j++) {
				for (Field f : fields) {
					f.setAccessible(true);
					// 匹配第j列
					if (f.getName().equals(head[j])) {
						String value = null;
						try {
							value = String.valueOf((f.get(data)));
						} catch (IllegalArgumentException | IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (value == "null")
							value = "";
						if (f.getName().equals("gender")) {
							value = value.equals("1") ? "女" : "男";
						}
						if (f.getName().equals("language")) {
							value = value.equals("1") ? "日文" : "中文";
						}
						Cell scell = row.createCell(j, CellType.STRING);
						scell.setCellValue(value);
						Font fo = wb.getFontAt(scell.getCellStyle().getFontIndex());
						// System.out.println(fo.getFontHeight());
						setColumnWidth(scell, wb, value, j);
						break;
					}
				}
			}
			// for (int j = 0; j < fields.length - 1; j++) {
			// Field f = fields[j];
			// f.setAccessible(true);
			//
			// // if(f.getName().equals("nameMap")){
			// // continue;
			// // }
			//
			// String value = null;
			// try {
			// value = (String) (f.get(data));
			// } catch (IllegalArgumentException | IllegalAccessException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// Cell scell = row.createCell(j, CellType.STRING);
			// scell.setCellValue(value);
			// Font fo = wb.getFontAt(scell.getCellStyle().getFontIndex());
			// //System.out.println(fo.getFontHeight());
			// setColumnWidth(scell, wb, value, j);
			//
			// }

		}
	}

	// 设置列宽
	public static void setColumnWidth(Cell cell, Workbook wb, String content, int columnNum) {
		// 中文个数
		int chineseCount = 0;
		int otherCount = 0;
		int width = 0;
		char[] c = content.toCharArray();
		for (int j = 0; j < c.length; j++) {
			String len = Integer.toBinaryString(c[j]);
			// System.out.println(len);
			if (len.length() > 8)
				chineseCount++;
		}

		otherCount = content.length() - chineseCount;
		//
		// if(columnNum==3){
		// System.out.println(content);
		// System.out.println(otherCount);
		// System.out.println(chineseCount);
		// }
		width = chineseCount * 512 + otherCount * 256;

		// 获取当前单元格字体
		Font fo = wb.getFontAt(cell.getCellStyle().getFontIndex());
		// System.out.println("dangqian :"+fo.getFontHeight());
		// System.out.println("moren:"+wb.getFontAt((short) 0).getFontHeight());
		// System.out.println("zonggeshu :"+title.length()+"zhognen:"+count);

		// System.out.println(maxWidth[i]);
		double times = (double) fo.getFontHeight() / (20 * DEFAULT_FONT_SIZE);
		// System.out.println(times);
		width = (int) (width * times);
		if (width > maxWidth[columnNum]) {
			maxWidth[columnNum] = width;
		}
	}

}
