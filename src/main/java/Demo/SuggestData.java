package Demo;

import com.google.common.base.Strings;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.walkmod.nsq.NSQProducer;
import org.walkmod.nsq.exceptions.NSQException;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

//select SuggestData('search-caicai','1101eef8ff2d4f226d7968aeec1e1a91','search_type',a.vid,a.sumplay, b.cn_name)
// from (select vid, count(1) sumplay from data_sum.sum_user_uuid_play_day where dt='20170212' group by vid )a
// left outer join (select vid,cn_name from dwd.dwd_cont_letv_video_day where dt='20170212')b on a.vid=b.vid


public class SuggestData extends UDF {
//	private static Logger logger = LoggerFactory.getLogger(SuggestData.class);
	public String evaluate(String index_name , String index_id, String type_name,String vid,String cn_name) {
		try {
			cn_name=java.net.URLEncoder.encode("cn_name","utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		JSONObject obj = new JSONObject();
		obj.put("index_name",index_name);
		obj.put("index_id",index_id);
		obj.put("type_name",type_name);
		obj.put("id",vid);
//		obj.put("script","ctx._source.click_num += "+sumplay);
//		obj.put("param",sumplay);
		JSONObject data = new JSONObject();
		data.put("id",vid);
//		logger.info(" cn_name: {}",cn_name);
		data.put("search_name",cn_name);
		data.put("pinyin_name",cn_name);
		data.put("first_name",cn_name);
		data.put("filter_name",cn_name);

		obj.put("data",data);


		StringWriter out = new StringWriter();
		try {
			obj.writeJSONString(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String jsonText = out.toString();
		System.out.println(jsonText);
		NSQProducer nSQProducer = new NSQProducer("http://10.185.29.228:4151","update2");
		try {
			for (int i=0 ;i<10;i++){
				nSQProducer.put(jsonText);
			}

		} catch (NSQException e) {
			e.printStackTrace();
		}
		return "-";
	}

	public static void main(String[] args) {
		SuggestData analyzer = new SuggestData();
		String s = "张三李四";
//		try {
//			s = new String(s.getBytes("gbk"),"utf-8");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
		System.out.println(s);
		System.out.println(analyzer.evaluate("search-caicai","1101eef8ff2d4f226d7968aeec1e1a91","search_type", String.valueOf(22),s));
	}
}



