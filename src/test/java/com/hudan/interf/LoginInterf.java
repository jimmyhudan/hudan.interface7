package com.hudan.interf; 
import java.io.IOException;
import java.net.URI; 
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;

public class LoginInterf {

	@Test(enabled=false)
	public void PosttestLogin()
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
			BasicNameValuePair admin_password=new BasicNameValuePair("admin_password","admin");
			BasicNameValuePair admin_answer=new BasicNameValuePair("admin_answer","admin");
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
	
	@Test
	public void GettestLogin()
	{
		//接口地址
		String geturl="http://localhost:8088/Discuz/upload/admin.php";
		//HttpGet
		HttpGet httpget=new HttpGet();
		//准备参数
		List<BasicNameValuePair> pararms=new ArrayList<BasicNameValuePair>();
		BasicNameValuePair admin_password=new BasicNameValuePair("admin_password","admin");
		BasicNameValuePair admin_answer=new BasicNameValuePair("admin_answer","admin");
		pararms.add(admin_password);
		pararms.add(admin_answer);
		//将参数拼接在Url上
		String urlstr=URLEncodedUtils.format(pararms, "UTF-8");
		geturl+=("?"+urlstr); 
		try {
			httpget.setURI(new URI(geturl));
			//准备客户端
			CloseableHttpClient httpclient=HttpClients.createDefault();
			//发送接口请求，返回响应数据，响应数据封装在了HttpResponse响应对象中
			CloseableHttpResponse httpRsponse;
			httpRsponse = httpclient.execute(httpget);
			
			int  httpCode=httpRsponse.getStatusLine().getStatusCode();
			String  ProtocolVersion=httpRsponse.getStatusLine().getProtocolVersion().getProtocol();
			System.out.println("get httpCode:"+httpCode);
			System.out.println("get ProtocolVersion:"+ProtocolVersion); 
			String strResult=EntityUtils.toString(httpRsponse.getEntity());
			System.out.println(strResult);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	
	}
	
}
