package com.le;

import java.util.Arrays;
import java.util.List;

import org.apache.hadoop.hive.ql.exec.UDF;

public class Weight extends UDF {
	public double evaluate(double score, List<Double> array) {
		int index = 0;
		for (int i = 0; i < array.size(); i++) {
			if (score >= array.get(i)) {
				index++;
			}else{
			  break;
			}
		}
		switch (index) {
		case 0:
			return 0.1;
		case 1:
			return 0.2;
		case 2:
			return 0.3;
		case 3:
			return 0.4;
		case 4:
			return 0.5;
		case 5:
			return 0.6;
		case 6:
			return 0.7;
		case 7:
			return 0.8;
		case 8:
			return 0.9;
		case 9:
			return 1.0;
		default:
			return 0.0;

		}
	}

	public static void main(String[] args) {
		Weight w = new Weight();
		Double[] array = new Double[] {141.0,649.0,1731.0,3664.0,6969.0,12735.0,23346.0,45138.0,101203.0};
		List<Double> asList = Arrays.asList(array);
		System.out.println(w.evaluate(649.0, asList));
	}
}
