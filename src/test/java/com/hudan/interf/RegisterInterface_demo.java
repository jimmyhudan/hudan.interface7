package com.hudan.interf;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;

import com.hudan.utils.ExcelUtil;

public class RegisterInterface_demo {
	@Test(dataProvider="datas")
	public void testCase(String usernames,String pwd,String confirmpwd,String email,String enpired)
	{
		//接口访问地址
		//http://localhost:8088/Discuz/upload/admin.php
		String geturl="http://localhost:8088/Discuz/upload/admin.php";
		try {
			//选择接口提交的方式（以Post为例）x
			HttpPost httpPost=new HttpPost();
			httpPost.setURI(new URI(geturl));
			//准备参数
			List<BasicNameValuePair> pararms=new ArrayList<BasicNameValuePair>();
			BasicNameValuePair admin_password=new BasicNameValuePair("admin_password",usernames);
			BasicNameValuePair admin_answer=new BasicNameValuePair("admin_answer",pwd);
			pararms.add(admin_password);
			pararms.add(admin_answer);
			//把参数封装到请求体中（因为Post提交的时候参数并不是拼接在URL上而是封装在请求体中的）	
			httpPost.setEntity(new UrlEncodedFormEntity(pararms,"UTF-8"));
			//准备客户端
			CloseableHttpClient httpclient=HttpClients.createDefault();
			//发送接口请求，返回响应数据，响应数据封装在了HttpResponse响应对象中
			CloseableHttpResponse httpRsponse=httpclient.execute(httpPost);
			//获取响应回来的状态码.服务器返回的状态码
			int  httpCode=httpRsponse.getStatusLine().getStatusCode();
			String  ProtocolVersion=httpRsponse.getStatusLine().getProtocolVersion().getProtocol();
			System.out.println("httpCode:"+httpCode);
			System.out.println("ProtocolVersion:"+ProtocolVersion);
			//取出接口的响应数据,并且将接口的响应数据转化为字符串类型
			String strResult=EntityUtils.toString(httpRsponse.getEntity());
			System.out.println("result:"+strResult);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Object[] [] datas(){
	/*	Object [][] datas= { 
				{"s","","","","用户名不得小于 3 个字符"},
				{"danly1","d","","","密码太短，不得少于 6 个字符"},  
				{"danly1","123456","1234567","","两次输入的密码不一致"},
				{"danly1","123456","123456","c","Email 地址无效"}
//				{"s","","","","用户名不得小于 3 个字符"},
//				{"danly1","d","","","密码太短，不得少于 6 个字符"},  
//				{"danly1","123456","1234567","","两次输入的密码不一致"},
//				{"danly1","123456","123456","c","Email 地址无效"}
		};*/
		int [] rows= {4,5,6,7,8}; //行 
		int [] cells= {1,2,3,4};   //行 /hudan.interface7/src/test/resources 
		Object [][] datas=  ExcelUtil.read("src/test/resources/rest_info.xlsx", 2, rows, cells);
		
		return datas;
	}
	
	public static void main(String [] args)
	{ 
		int [] rows= {4,5,6,7,8}; //行 
		int [] cells= {1,2,3,4};   //列 /hudan.interface7/src/test/resources 
		Object [][] datas=  ExcelUtil.read("src/test/resources/rest_info.xlsx", 2, rows, cells);
		 for(Object [] objects:datas)
		{
			//打印行的数据
			for(Object object:objects) {
				System.out.print("【"+object+"】");
			}
			System.out.println();
		}
	}
}
