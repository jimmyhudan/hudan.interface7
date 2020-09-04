package com.hudan.interf;

import org.testng.annotations.DataProvider;

import com.hudan.interf.pojo.Constants;
import com.hudan.utils.DataProviderUtil;
import com.hudan.utils.RestUtil;

/**
 * 
 * 登录测试的接口
 * @author 胡丹
 *
 */
public class LoginProcessor extends UniformProcessor{
	//被子类实现
	@Override
	@DataProvider
	public Object[] [] datas(){ 
		String [] columLables= {"CaseId","ApiId","RequestData","PreValidateSql","AfterValidateSql"};
		Object [][] objs= 	DataProviderUtil.getAllTestDataByApiId("1", columLables);
		//如果是可变的
		//		String sql="select concat(max(mobilePhone)+1,"") as mobilephone from member";
		//		Map<String,Object> result=jdbcUtil.query(sql); 
		RestUtil.variableValues.put(Constants.VARIABLE_NORMAL_Users, "hudan");
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
			String requestData=(String) objects[i]; 
			if(requestData.contains(Constants.VARIABLE_NORMAL_Users))
			{ 
				String user=RestUtil.variableValues.get(Constants.VARIABLE_NORMAL_Users); 
				requestData=requestData.replace(Constants.VARIABLE_NORMAL_Users,user);
				//requestData.replaceFirst("${{user}", result.get("mobilephone").toString());
				//替换 
			}
			objects[i]=requestData;
			System.out.println(".....................替换后的:"+requestData);


		}
	}

}
