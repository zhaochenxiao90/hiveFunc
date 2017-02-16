package com.le;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class Util {

	public static String getPingYin(String inputString) {
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		format.setVCharType(HanyuPinyinVCharType.WITH_V);

		char[] input = inputString.trim().toCharArray();
		String output = "";

		try {
			for (int i = 0; i < input.length; i++) {
				if (java.lang.Character.toString(input[i]).matches("[\\u4E00-\\u9FA5]+")) {
					String[] temp = PinyinHelper.toHanyuPinyinStringArray(input[i], format);
					output += temp[0];
				} else
					output += java.lang.Character.toString(input[i]);
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}
		return output;
	}

	public static void main(String[] args) {
//		long currentTimeMillis = System.currentTimeMillis();
//		for(int i=0;i<10000;i++){
//			Util.getPingYin("汉庭");
//		}
//        System.out.println(System.currentTimeMillis()-currentTimeMillis);
		System.out.println(Util.getWIFIName());
	}

	public static Map<String, List<Category>> getWIFIName() {
		Connection conn = null;
		Statement stat = null;
		ResultSet query = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://10.148.10.66:3306/event_code", "scloud-dev",
					"scloud@letv");
			stat = conn.createStatement();
			query = stat.executeQuery(
					"select c.category_name,n.name,n.aliase from event_code.wifi_category c,event_code.wifi_name n where c.id=n.pid");
			Map<String, List<Category>> map = new HashMap<String, List<Category>>();

			while (query.next()) {
				String category_name = query.getString("category_name");
				if (map.get(category_name) == null) {
					map.put(category_name, new ArrayList<Category>());
					Category category = new Category();
					category.setName(query.getString("name"));
					category.setAliase(query.getString("aliase"));
					map.get(category_name).add(category);

				} else {
					Category category = new Category();
					category.setName(query.getString("name"));
					category.setAliase(query.getString("aliase"));
					map.get(category_name).add(category);
				}

			}
			return map;
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			if (query != null) {
				try {
					query.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (stat != null) {
				try {
					stat.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}
  
}
