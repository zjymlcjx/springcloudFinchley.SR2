package com.haobai.base.utils;

import java.util.ArrayList;
import java.util.List;

public class CreateSql {

	
	public static String dbCreateInByString(List<String> list) {
		StringBuffer sb = new StringBuffer();
		sb.append("  (");
		for (int i = 0; i < list.size(); i++) {
			sb.append("'");
			sb.append(list.get(i));
			sb.append("'");
			if (list.size() > 1 && (i < (list.size() - 1))) {
				sb.append(",");
			}
		}
		sb.append(") ");
		return sb.toString();
	}
	
	public static String dbCreateInByLong(List<Long> list) {
		StringBuffer sb = new StringBuffer();
		sb.append("  (");
		for (int i = 0; i < list.size(); i++) {
			sb.append(list.get(i));
			if (list.size() > 1 && (i < (list.size() - 1))) {
				sb.append(",");
			}
		}
		sb.append(") ");
		return sb.toString();
	}
	public static void main(String[] args) {
		List<Long> list = new ArrayList<>();
		list.add(1l);
		list.add(2l);
		list.add(3l);
		System.out.println(dbCreateInByLong(list));
		
	}

}
