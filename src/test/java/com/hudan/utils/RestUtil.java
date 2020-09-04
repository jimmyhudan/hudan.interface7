package com.hudan.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;

import com.hudan.interf.pojo.Rest;

public class RestUtil {

	/**
	 * 集合里面保存的是所有的接口信息
	 */
	public static List<Rest> rests=new ArrayList<Rest>(); 
//	private  static  Map<String,String> map=new HashMap<String,String>();
	//保存一些变量
	public static Map<String,String> variableValues=new HashMap<String,String>();
	static {
		String filePath="src/test/resources/rest_info.xlsx";
		ExcelUtil.loadBeans(filePath, 1,Rest.class); 
	}
	/**
	 * @param apiId
	 */
	public static String getRestUrlByApid(String apiId) {
		//根据ID,来获取Url
		for(Rest rest:rests)
		{
			if(rest.getApiId().equals(apiId)) {
				return rest.getUrl();
			}
		}
		return "";

	}

	public static String getRestTypeByApid(String apiId) {
		//根据ID,来获取Type
		for(Rest rest:rests)
		{
			if(rest.getApiId().equals(apiId)) {
				return rest.getType();
			}
		}
		return "";
	}

//
//	public static void main(String [] args)
//	{
//
//
//		int [] rows= {2,3,4}; //行 
//		int [] cells= {1,2,3,4};//列 /hudan.interface7/src/test/resources 
//		Object [][] datas=  ExcelUtil.read("src/test/resources/rest_info.xlsx", 1, rows, cells);
//		for(Object [] objects:datas)
//		{
//			//打印行的数据
//			for(Object object:objects) {
//				System.out.print("【"+object+"】");
//			}
//			System.out.println();
//		}
//	}

	public static void main(String [] args)
	{
		for(Rest rest:rests)
		{
			System.out.println(rest);
		}
	}

	


}
