package com.hudan.interf;


import org.testng.annotations.DataProvider;

import com.hudan.interf.pojo.Constants;
import com.hudan.utils.DataProviderUtil;
import com.hudan.utils.RestUtil;

public class SearchProcessor extends UniformProcessor {
	//被子类实现
	@Override
	@DataProvider
	public Object[] [] datas(){ 
		String [] columLables= {"CaseId","ApiId","RequestData","PreValidateSql","AfterValidateSql"};
		Object [][] objs= 	DataProviderUtil.getAllTestDataByApiId("3", columLables);
		//如果是可变的
		//		String sql="select max(mobilePhone)+1 as mobilephone from member";
		//		Map<String,Object> result=jdbcUtil.query(sql); 
		for(Object [] objects:objs)
		{
			replaceVariables(objects);
		}


		return objs;
	}

	public void replaceVariables(Object [] objects)
	{
		for(int i=0;i<objects.length;i++)
		{
			String requestData=(String) objects[i]; //只要有就替换
			//				String requestData=(String) objects[2]; //取出的是RequestData
			if(requestData.contains(Constants.VARIABLE_NORMAL_Users))
			{ 
				String user=RestUtil.variableValues.get(Constants.VARIABLE_NORMAL_Users); 
				//requestData.replaceFirst("${{user}", result.get("mobilephone").toString());//替换 
				requestData=requestData.replace(Constants.VARIABLE_NORMAL_Users,user);

				
			}
			objects[i]=requestData;
			System.out.println(".....................替换后的:"+requestData); 
			
		}
	}
	//	public  static void main(String [] args)
	//	{
	//		String [] columLables= {"CaseId","ApiId","RequestData","PreValidateSql","AfterValidateSql"};
	//		Object [][] objs= 	DataProviderUtil.getAllTestDataByApiId("3", columLables);
	//		for(Object [] objects:objs)
	//		{
	//			String dd=(String) objects[2];
	//			System.out.println(dd);
	//		} 
	//	}
}
