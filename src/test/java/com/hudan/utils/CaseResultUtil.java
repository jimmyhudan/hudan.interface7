package com.hudan.utils;

import java.util.HashMap;
import java.util.Map;

public class CaseResultUtil {

	public static Map<String,Map<String,String>> caseResultsMapping=new HashMap<String ,Map<String,String>>();
	
	/**
	 * 
	 * //将执行的用例编号--列--数据添加到集合维护号映射关系
	 * @param caseId
	 * @param cellNum
	 * @param cellValue
	 */
	public static void addTestResultInMapping(String caseId,String cellName,String cellValue)
	{
		Map<String,String> map=caseResultsMapping.get(caseId);
		if(map==null) { //该用例底下还没有对应的映射数据
			
			HashMap<String,String> cellNumAndValueMap=new HashMap<String,String>();
			cellNumAndValueMap.put(cellName, cellValue);
			caseResultsMapping.put(caseId, cellNumAndValueMap);
		}else//该用例底下已经有对应的映射数据
		{
			map.put(cellName, cellValue);
			caseResultsMapping.put(caseId, map);
		}
	}
	
	public static void main(String [] args)
	{
		addTestResultInMapping("1","5","aa");
		addTestResultInMapping("1","5","aa");
		Map<String,String> map=caseResultsMapping.get("1");
		System.out.println(map.size());

		
		
	}
}
