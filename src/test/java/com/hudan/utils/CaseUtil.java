package com.hudan.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hudan.interf.pojo.Case;

public class CaseUtil {

	/**
	 * 集合里面保存的是所有的接口信息
	 */
	public static List<Case> cases=new ArrayList<Case>();
	//未用
//	private  static  Map<String,String> map=new HashMap<String,String>();
	
	public  static  Map<String,Integer> cellNameAndCellNumMap=new HashMap<String,Integer>();

	
	static {
		String filePath="src/test/resources/rest_info.xlsx";
		ExcelUtil.loadBeans(filePath, 2,Case.class); 
	}
 
 
	public static void main(String [] args)
	{
		for(Case cases:cases)
		{
			System.out.println(cases);
		}
	}



}
