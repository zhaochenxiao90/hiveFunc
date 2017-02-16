package suggest.data.handle;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.json.simple.JSONObject;
import org.walkmod.nsq.NSQProducer;
import org.walkmod.nsq.exceptions.NSQException;

import java.io.IOException;
import java.io.StringWriter;

//	select a.vid vid,a.sumplay sumplay, b.cn_name cn_name from
//	(select vid, count(1) sumplay from data_sum.sum_user_uuid_play_day where dt=20170212 group by vid )a
//	left join
//	(select vid,cn_name from dwd.dwd_cont_letv_video_day where dt=20170212)b on a.vid=b.vid


public class SuggestDataHandle extends UDF {

	public String evaluate(String index_name ,String index_id,String type_name,String vid,int sumplay,String cn_name) {
		JSONObject obj = new JSONObject();
		obj.put("index_name",index_name);
		obj.put("index_id",index_id);
		obj.put("type_name",type_name);
		obj.put("id",vid);
		obj.put("script","ctx._source.click_num += "+sumplay);
		obj.put("param",sumplay);
		JSONObject data = new JSONObject();
		data.put("id",vid);
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
		NSQProducer nSQProducer = new NSQProducer("http://10.185.29.228:4151","update");
		try {
			for (int i=0 ;i<100;i++){
				nSQProducer.put(jsonText);
			}

		} catch (NSQException e) {
			e.printStackTrace();
		}
		return "-";
	}

	public static void main(String[] args) {
		SuggestDataHandle analyzer = new SuggestDataHandle();
		System.out.println(analyzer.evaluate("search-caicai","1101eef8ff2d4f226d7968aeec1e1a91","search_type", String.valueOf(22),1000,"cn_name"));
	}
}



