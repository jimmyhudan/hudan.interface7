package com.hudan.utils;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/*
 * httpclient发送请求，参数List、HeaderList都不能为空
 * common
 * author :胡丹
 * 
 */
public class HttpPostUtils { 
	private  static  Map<String,String> map=new HashMap<String,String>(); 
	private CloseableHttpClient httpClient;
	private CookieStore cookieStore;
	public HttpPostUtils() {
		cookieStore = new BasicCookieStore();
		httpClient = HttpClients.custom() // 自定义httpclient对象
				.setDefaultCookieStore(cookieStore) // 设置默认cookie存储区
				.build(); 
	}

	public String doGet(String resturl, List<BasicNameValuePair> params, List<Header> headers) {
		//选择接口的提交方式（以Post为例）
		HttpGet hpget = new HttpGet(resturl);
		//将参数拼接在URL上
		String responseStr = null;
		String paramsStr=null;
		resturl+=("?"+paramsStr);
		CloseableHttpResponse response = null;
		//		if(params!=null)
		//		{ 
		//			//把参数封装到请求体中（因为Post提交的时候参数并不是拼接在URL上而是封装在请求体中的）
		//			post.setEntity(new UrlEncodedFormEntity(params,Consts.UTF_8)); // 将准备好的参数集合放入post请求中,增加编码集
		//
		//		} 
		try {
			//准备客户端
			hpget.setURI(new URI(resturl));
			//准备客户端
			CloseableHttpClient httpclient=HttpClients.createDefault();
			CloseableHttpResponse httpResponse=httpclient.execute(hpget);
			HttpEntity entity = response.getEntity();  // 获取响应实例
			responseStr = EntityUtils.toString(entity); // 将实例转换为字符串

		}catch(Exception e)
		{
			e.printStackTrace();
		}

		return responseStr;
	}

	public String doPost(String url, List<BasicNameValuePair> params, List<Header> headers) {
		//选择接口的提交方式（以Post为例）
		HttpPost post = new HttpPost(url);
		String responseStr = null;
		//用COOKIE
//		post.addHeader("Cookie",map.get("JSESSIONID"));
		if(params!=null)
		{ 
			//把参数封装到请求体中（因为Post提交的时候参数并不是拼接在URL上而是封装在请求体中的）
			post.setEntity(new UrlEncodedFormEntity(params,Consts.UTF_8)); // 将准备好的参数集合放入post请求中,增加编码集

		}
		//准备Header的值
		if(headers!=null) {
			Header[] h = new Header[headers.size()];
			post.setHeaders(headers.toArray(h));
		}
		CloseableHttpResponse response = null;
		try {
			//准备客户端
			response = httpClient.execute(post); // 使用httpclient发送post请求
			HttpEntity entity = response.getEntity();  // 获取响应实例
			responseStr = EntityUtils.toString(entity); // 将实例转换为字符串
//			addJsessionIdInMap(response);

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				response.close(); // 关闭响应
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return responseStr;
	}
	/*常见鉴权框架的鉴权方式。
	1.jsessionID相当于是一张票，当你登录完，服务器就会返回这样的一张票给你（在响应头里面有一个Set-cookie,放的就是服务器返回票的信息）
	后面在请求的时候会默认带着这张票过去，服务器会判断请求的头里面是否有Cookie头,并且Cookie头里的JessionID是否跟服务器的会话ID一致
	如果一致，就说明已经登录过了。否则认为还未登录*/
	public static void addJsessionIdInMap(CloseableHttpResponse httpResponse)
	{
		if(httpResponse.getFirstHeader("Set-Cookie")!=null)
		{
			String setCookieValue=httpResponse.getFirstHeader("Set-Cookie").getValue();
			if(setCookieValue!=null && setCookieValue.trim().length()>0)
			{
				int index=setCookieValue.indexOf("JSESSIONID");
				if(index!=-1)
				{
					setCookieValue=setCookieValue.substring(index);
					int index2=setCookieValue.indexOf(";");
					if(index2!=-1)
					{
						String jessionID=setCookieValue.substring(0,index2);
						map.put("JSESSIONID", jessionID);
					}
				}
			}
		}
	}



}
