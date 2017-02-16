package com.le;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Sets;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Text;

public class WifiNameAnalyzer extends UDF {
	// public static List<Category> list = null;
	// static {
	// list = Util.getWIFIName();
	// }
	public static final String UNION = "#";
	public static Map<String, List<Category>> map = Util.getWIFIName();

	public String evaluate(Text wifiName) {
		String wifi = wifiName.toString().toLowerCase();
		// for (Category category : list) {
		// for (String supportWord : category.getSupportWord()) {
		// if (wifi.contains(supportWord) ||
		// wifi.contains(Util.getPingYin(supportWord))) {
		// return category.getParentWord() + UNION + supportWord;
		// }
		// }
		// }
		// return "-";
		for (String category : map.keySet()) {
			for (Category c : map.get(category)) {
				if (c.getAliase() != null) {
					if (wifi.contains(c.getName()) || wifi.contains(c.getAliase())
							|| wifi.contains(Util.getPingYin(c.getName()))) {
						return category + UNION + c.getName();
					}
				} else {
					if (wifi.contains(c.getName()) || wifi.contains(Util.getPingYin(c.getName()))) {
						return category + UNION + c.getName();
					}
				}

			}
		}
		return "-";
	}

	public static void main(String[] args) {
		WifiNameAnalyzer analyzer = new WifiNameAnalyzer();
		System.out.println(analyzer.evaluate(new Text("饭店")));
	}
}
