package com.le;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.wltea.analyzer.cfg.DefaultConfig;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;
import org.wltea.analyzer.dic.Dictionary;

public class WifiNameAnalyzer1 extends UDF {
	static{
		Dictionary.initial(DefaultConfig.getInstance());
	}
	public List<String> evaluate(final String wifiName) {
		List<String> list=new ArrayList<String>();
		StringReader reader = new StringReader(wifiName.toLowerCase());
		IKSegmenter ik = new IKSegmenter(reader, true);
		Lexeme lexeme = null;
		try {

			while ((lexeme = ik.next()) != null) {
				list.add(lexeme.getLexemeText());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			reader.close();
		}
        
	
		return list;
	}
	
	public static void main(String[] args) {
		WifiNameAnalyzer1 w=new WifiNameAnalyzer1();
		List<String> evaluate = w.evaluate("鱼C工作室|鱼C论坛 终身VIP会员，一次支持，终身学习！-淘宝网");
		System.out.println(evaluate);
	}
}
