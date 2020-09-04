package com.hudan.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSONObject;
/**
 * 
 * 准备参数，保存到List集合
 * @author Administrator
 *
 */

public class ParamUtil {
	public static  List<BasicNameValuePair> PrepareParams(String requestData) {
		if(requestData==null || requestData.trim().length()==0)
		{
			return null;
		}
		//准备参数
		List<BasicNameValuePair> pararms=new ArrayList<BasicNameValuePair>();
		//将请求的参数解析为Map
		Map<String,String> MapParams= (Map<String,String>)JSONObject.parse(requestData);
		//获取map里面所有键（字段）
		Set<String> fields=MapParams.keySet();//拿出里面的参数
		//遍历所有的键
		for(String field:fields)
		{
			//根据键取值
			String fieldValue=MapParams.get(field);
			//将键和值封装为Pair对象
			BasicNameValuePair pair=new BasicNameValuePair(field, fieldValue);
			//统一添加到params集合
			pararms.add(pair); 
		}
		return pararms;
	}
}
