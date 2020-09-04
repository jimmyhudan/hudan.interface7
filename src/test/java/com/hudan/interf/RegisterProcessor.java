package com.hudan.interf;
 
import org.testng.annotations.DataProvider;

import com.hudan.interf.pojo.Constants; 
import com.hudan.utils.DataProviderUtil;
import com.hudan.utils.RestUtil;

/**
 * 
 * 注册测试的接口
 * @author 胡丹
 *
 */
public class RegisterProcessor extends UniformProcessor {

	
	//被子类实现
	@Override
	@DataProvider
	public Object[] [] datas(){ 
		String [] columLables= {"CaseId","ApiId","RequestData","PreValidateSql","AfterValidateSql"};
		Object [] [] datas= 	DataProviderUtil.getAllTestDataByApiId("2", columLables);
		for(Object [] objects:datas)
		{
			replaceVariables(objects);
		}

		return datas;
	}


	public void replaceVariables(Object [] objects)
	{
		RestUtil.variableValues.put(Constants.VARIABLE_NORMAL_AdminUsers, "admin");

		for(int i=0;i<objects.length;i++)
		{
			String requestData=(String) objects[i]; 
			if(requestData.contains(Constants.VARIABLE_NORMAL_AdminUsers))
			{ 
				String searchUser=RestUtil.variableValues.get(Constants.VARIABLE_NORMAL_AdminUsers);   
				requestData=requestData.replace(Constants.VARIABLE_NORMAL_AdminUsers,searchUser);
				//requestData.replaceFirst("${{user}", result.get("mobilephone").toString());
				//替换 
			}
			objects[i]=requestData;
			System.out.println(".....................替换后的:"+requestData);
		}
	}


	public  static void main(String [] args)
	{
		String [] columLables= {"CaseId","ApiId","RequestData","PreValidateSql","AfterValidateSql"};
		Object [][] objs= 	DataProviderUtil.getAllTestDataByApiId("2", columLables);
		for(Object [] objects:objs)
		{
			String dd=(String) objects[2];
			System.out.println(dd);
		} 
	} 

}
