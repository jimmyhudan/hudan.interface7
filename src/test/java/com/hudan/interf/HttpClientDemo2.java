package com.hudan.interf;



import static org.testng.Assert.assertEquals;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hudan.entities.SearchBookResult;
import com.hudan.entities.SearchError;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair; 
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hudan.entities.Authorization;
import com.hudan.utils.ExcelUtil;
import com.hudan.utils.HttpPostUtils;

/***
 * testng加这个方法就可以yong'x
 * 	<class name="com.hudan.interf.HttpClientDemo" />
 * 
 * 第一种方式@DataProvider 来注入参数，每个类就是对应一个类型的接口测试，一个类可以写多个，针对失败一个类方法，针对登录一个方法。针对查询一个方法
 * 
 *  
 *  1.通过POI解决了数据源的问题 
* 	2.通过TestNg的DataProvider数据提供者技术解决了接口批量测试的问题
* 	3.通过号的用例设计（关联+json）解决了用例数据臃肿的问题
* 	4.通过设计JSON字符串解决了传参的问题你
* 	5.通过fastjson解决了参数提取问题
* 	6.通过apiId接口编号拿到接口信息(地址+提交方式)
* 	//登录保存Token
 * 
 * @author hudan
 *
 */

public class HttpClientDemo2 {
	private Authorization auth;
	private HttpPostUtils postutils; 
	
	//登录保存Token
	@BeforeClass
	public void doLogin()
	{ 
		//登录
		Gson gson = new GsonBuilder().create(); 
		postutils=new HttpPostUtils();
		String url="http://localhost:8080/pk_api/login";
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>(); // 创建准备传送的post请求的body参数集合
		params.add(new BasicNameValuePair("user", "hudan"));
		params.add(new BasicNameValuePair("pwd", "123456"));
		String responseStr=postutils.doPost(url, params, null);
		auth = gson.fromJson(responseStr, Authorization.class);
	}
	//针对成功
	@Test(dataProvider="datas")
	public void testsearchBook(String CaseId,String ApiId,String requestData){
 
		/*
		 * 2、进行查询操作，需要提供Authorization头，cookie，以及传送参数user
		 */
		SearchBookResult sbr = null;
		Gson gson = new GsonBuilder().create();  
		String url="http://localhost:8080/pk_api/sbook";
		//准备参数
		List<BasicNameValuePair> params2=new ArrayList<BasicNameValuePair>();
		Map<String,String> MapParams= (Map<String,String>)JSONObject.parse(requestData);
		Set<String> fields=MapParams.keySet();//拿出里面的参数
		for(String field:fields)
		{
			String fieldValue=MapParams.get(field);
			BasicNameValuePair pair=new BasicNameValuePair(field, fieldValue);
			params2.add(pair); 
		} 
		 
		Header header = new BasicHeader("Authorization", "Bearer "+auth.getToken()); // 准备添加用于token认证的头信息
		List<Header> headers1=	new ArrayList<>();
		headers1.add(header);
		String responseStr=postutils.doPost(url, params2, headers1);
		sbr = gson.fromJson(responseStr, SearchBookResult.class);
		System.out.println("result:"+responseStr);
		
//		assertEquals(sbr.getCount(),count);
	}
	
	@DataProvider
	public Object[] [] datas(){ 
		int [] rows= {4,5,6,7,8}; //行 
		int [] cells= {1,2,3};   //行 /hudan.interface7/src/test/resources 
		Object [][] datas=  ExcelUtil.read("src/test/resources/rest_info.xlsx", 2, rows, cells);

		return datas;
	}
	
	//针对失败
	//每个类都是一个接口.
	//如果要测试是错误的话，就直接在类里面加一个方法就好了,假如要添加错误的
	//一个类里面有多个,没有User的情况
	@Test
	public void testErrorsearchBook(){
 
		Gson gson = new GsonBuilder().create();
		SearchError error;
		String url="http://localhost:8080/pk_api/sbook"; 
		Header header = new BasicHeader("Authorization", "Bearer "+auth.getToken()); // 准备添加用于token认证的头信息
		List<Header> headers1=	new ArrayList<>();
		headers1.add(header);
		String responseStr=postutils.doPost(url, null, headers1);
		error = gson.fromJson(responseStr, SearchError.class);
		assertEquals(error.getErrorCode(),103);
		assertEquals(error.getMessage(),"认证失败，请确定token是否有效");  //如果有错误会出来
	}
	
}
