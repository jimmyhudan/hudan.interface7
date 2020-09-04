package com.hudan.interf;


import java.util.ArrayList; 
import java.util.List; 

import org.apache.http.Header; 
import org.apache.http.client.methods.HttpPost; 
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider; 
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hudan.entities.Authorization;
import com.hudan.entities.SearchBookResult;
import com.hudan.interf.pojo.DBChecker;
import com.hudan.utils.CaseResultUtil;
import com.hudan.utils.DBcheckUtil;
import com.hudan.utils.DataProviderUtil;
import com.hudan.utils.ExcelUtil;
import com.hudan.utils.HttpPostUtils;
import com.hudan.utils.ParamUtil;
import com.hudan.utils.RestUtil;

/**
 * 
 * 接口测试的统一处理流程
 * @author 胡丹
 *
 */
public abstract class UniformProcessor {
	private Authorization auth;
	private HttpPostUtils postutils; 
	
	private Logger logger=null;

	@BeforeClass 
	public void init()
	{
		logger	= Logger.getLogger(RegisterProcessor.class); 
		logger.info("**********************开始接口自动化框架*****************************"); 

	}
	
	
	//	1.通过POI解决了数据源的问题 
	//	2.通过TestNg的DataProvider数据提供者技术解决了接口批量测试的问题
	//	3.通过号的用例设计（关联+json）解决了用例数据臃肿的问题
	//	4.通过设计JSON字符串解决了传参的问题你
	//	5.通过fastjson解决了参数提取问题
	//	6.通过apiId接口编号拿到接口信息(地址+提交方式)

	@BeforeClass
	public void doLogin() 
	{ 
		//登录 
		Gson gson = new GsonBuilder().create(); 
		postutils=new HttpPostUtils();
		String url="http://localhost:8080/pk_api/login";
		HttpPost post = new HttpPost(url);
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>(); // 创建准备传送的post请求的body参数集合
		params.add(new BasicNameValuePair("user", "hudan"));
		params.add(new BasicNameValuePair("pwd", "123456"));
		//把参数封装到请求体中（因为Post提交的时候参数并不是拼接在URL上而是封装在请求体中的）	
		String responseStr=postutils.doPost(url, params, null);
		auth = gson.fromJson(responseStr, Authorization.class);
		System.out.println("result:"+responseStr);
	}


	@Test(dataProvider="datas")

	public void testCase(String CaseId,String ApiId,String requestData,String PreValidateSql,String afterValidateSql)
	{ 
		//JSON转成对象
		//		List<DBChecker> dbcheckers=JSONObject.parseArray(PreValidateSql, DBChecker.class);
		//		//接口调用后的数据验证
		//		for(DBChecker db:dbcheckers) {
		//			System.out.println(db);
		//		}
		//调用接口前做数据验证 
		if(PreValidateSql!=null && PreValidateSql.trim().length()>0)
		{
			String preResult=DBcheckUtil.doValidate(PreValidateSql);
			CaseResultUtil.addTestResultInMapping(CaseId, "PreValidateResult", preResult);
		}
		//接口的鉴权
		//接口访问地址  
		SearchBookResult sbr = null;
		Gson gson = new GsonBuilder().create();  
		String geturl=	RestUtil.getRestUrlByApid(ApiId);     
		//获取接口参数
		List<BasicNameValuePair> pararms=ParamUtil.PrepareParams(requestData);  
		Header header = new BasicHeader("Authorization", "Bearer "+auth.getToken()); // 准备添加用于token认证的头信息

		List<Header> headers1=	new ArrayList<>();
		headers1.add(header);  
		String restType=RestUtil.getRestTypeByApid(ApiId);
		//匹配（大小写）忽大小写
		//
		String result="";
		if("post".equalsIgnoreCase(restType))
		{
			result =postutils.doPost(geturl, pararms, headers1);
			//			sbr = gson.fromJson(result, SearchBookResult.class);
			//将接口响应的数据放到EXCEL里面去 
			//ExcelUtil.writeResultToExcel("target/test-classes/rest_info.xlsx",2,CaseId,5,result);


		}
		else
		{
			result=postutils.doGet(geturl, pararms, headers1);
			//			sbr = gson.fromJson(result, SearchBookResult.class);
			//性能不高
			//ExcelUtil.writeResultToExcel("target/test-classes/rest_info.xlsx",2,CaseId,5,result);
		} 

		//将接口的数据保存起来
		CaseResultUtil.addTestResultInMapping(CaseId, "ActualResponseData", result);

		if(afterValidateSql!=null && afterValidateSql.trim().length()>0)
		{
			//调用接口前做数据验证 
			String afterResult=DBcheckUtil.doValidate(afterValidateSql);
			//接口调用后的数据验证
			CaseResultUtil.addTestResultInMapping(CaseId, "AfterValidateResult", afterResult);
		}

	}

//		@DataProvider
//		public Object[] [] datas(){ 
//	//		int [] rows= {3,4,5,6,7,8}; //行 
//	//		int [] cells= {1,2,4,6,8};   //列 /hudan.interface7/src/test/resources  
//			int [] rows= {2,3,4,5,6,7,8}; //行 
//	 		int [] cells= {1,2,3,6,8};   //列 /hudan.interface7/src/test/resources  
//			Object [][] datas=  ExcelUtil.read("src/test/resources/rest_info.xlsx", 2, rows, cells);
//			return datas;
//		}

//	@DataProvider
//	public Object[] [] datas(){ 
//		String [] columLables= {"CaseId","ApiId","RequestData","PreValidateSql","AfterValidateSql"};
//		return 	DataProviderUtil.getAllTestDatas(columLables);
//	}
	
	//被子类实现
	@DataProvider
	public abstract Object[] [] datas();
	

	//	public static void main(String [] args)
	//	{ 
	//		String paramString="{\"user\": \"hudan\",\"price\": \"23\"}";
	//		Param p=JSONObject.parseObject(paramString,Param.class);
	//		System.out.println(p);
	//	}

	//整个套件最后一个执行
	@AfterSuite  
	public void batchWriteTestResultToExcel1() 
	{ 
		System.out.println("sssss test");
		String filePath="target/test-classes/rest_info.xlsx";
		String SheetNum="2";
		ExcelUtil.batchWriteTestResultToExcel(filePath,SheetNum);
		ExcelUtil.writeVarableValue(filePath,"变量值");
		logger.info("**********************结束接口自动化框架*****************************"); 


	}

	//	public static void main(String [] args) {
	//		String PreValidateSql="[{\"no\":\"1\",\"sql\":\"select count(*) as totalNum from member where mobilePhone='13761042305'\"},{\"no\":\"2\",\"sql\":\"select count(*) as totalNum from member where mobilePhone='13761042305'\"}]";
	//		List<DBChecker> dbcheckers=JSONObject.parseArray(PreValidateSql, DBChecker.class);
	//		for(DBChecker db:dbcheckers) {
	//			System.out.println(db);
	//		}
	//	}
	public static void main(String [] args)
	{ 
		int [] rows= {2,3,4,5,6,7,8}; //行 
		int [] cells= {1,2,3,6,8};   //列 /hudan.interface7/src/test/resources  
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
