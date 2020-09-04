package com.hudan.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.hudan.interf.pojo.Case;

public class DataProviderUtil {
	
	
	public static Object [] [] getAllTestDatas(String [] columLables){
		List<Case> cases=CaseUtil.cases;
		int rows=cases.size();
		int cells=columLables.length;
		Object[][] datas=new Object[rows] [cells]; 
		Class<Case>clazz =Case.class;
		for(int i=0;i<cases.size();i++)
		{
			Case case1=cases.get(i);
			//根据列名（字段名）来取出对应字段上面的数据
			for(int j=0;j<columLables.length;j++)
			{
				String methodName ="get"+columLables[j];
				try {
					Method method=clazz.getMethod(methodName);
					Object object=method.invoke(case1);
					String fieldValue=object==null?"":object.toString();
					datas[i][j]=fieldValue;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return datas;
	}
	
	/**
	 * 根据API接口编号获取对应接口的测试数据
	 * @param apiId
	 * @param columLables
	 * @return
	 */
	public static Object [] [] getAllTestDataByApiId(String apiId,String [] columLables){
		List<Case> cases=new ArrayList<Case>();
		//根据传入的接口编号获取对应的接口数据
		for(Case case1:CaseUtil.cases)
		{
			if(case1.getApiId().equals(apiId))
			{
				cases.add(case1);
			}
				
		}
//		List<Case> cases=CaseUtil.cases;
		int rows=cases.size();
		int cells=columLables.length;
		Object[][] datas=new Object[rows] [cells]; 
		Class<Case>clazz =Case.class;
		for(int i=0;i<cases.size();i++)
		{
			Case case1=cases.get(i);
			//根据列名（字段名）来取出对应字段上面的数据
			for(int j=0;j<columLables.length;j++)
			{
				String methodName ="get"+columLables[j];
				try {
					Method method=clazz.getMethod(methodName);
					Object object=method.invoke(case1);
					String fieldValue=object==null?"":object.toString();
					datas[i][j]=fieldValue;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return datas;
	}
	
}
