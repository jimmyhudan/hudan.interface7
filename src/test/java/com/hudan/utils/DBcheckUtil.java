package com.hudan.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.hudan.interf.pojo.CheckResult;
import com.hudan.interf.pojo.DBChecker;

public class DBcheckUtil {
	private static  Logger loggers=Logger.getLogger(DBChecker.class);
	
	/** 
	 * 数据验证
	 * @param validateSql
	 * @return
	 */
	public static String doValidate(String validateSql)
	{
		loggers.info("验证脚本，json格式的数组字符串为："+validateSql); 
		//解析数组的字符串，将要执行SQL和编号封装到对象中保存
		List<DBChecker> dbcheckers=JSONObject.parseArray(validateSql, DBChecker.class);
		loggers.info("有几条SQL语句："+dbcheckers.size()); 

		List<CheckResult> dbcheckResults=new ArrayList<CheckResult>(); 
		//遍历集合取出每一个对象里保存sql脚本
		loggers.info("***************************开始数据库脚本查询*******************************"); 
		for(DBChecker dbchecker:dbcheckers)
		{
			//拿脚本
			String sql=dbchecker.getSql();
			loggers.info("执行脚本的编号为: {"+sql+"}");  
			//拿编号
			String no=dbchecker.getNo();
			loggers.info("执行脚本为: {"+no+"}"); 

			Map<String,Object> clumandVlaueMap=jdbcUtil.query(sql);
			CheckResult ckresult=new CheckResult();
			ckresult.setNo(no);
			ckresult.setClumandVlaueMap(clumandVlaueMap);
			dbcheckResults.add(ckresult); 
		}
		loggers.info("***************************结束数据库脚本查询*******************************"); 

		String resultStr=JSONObject.toJSONString(dbcheckResults);
		return resultStr;
	}

	public static void main(String [] args)
	{
		List<CheckResult> Listckr=new ArrayList<CheckResult>(); 
		CheckResult ckr=new CheckResult();
		ckr.setNo("1");
		Map<String,Object> clumandVlaueMap=new HashMap<String,Object>(); 
		clumandVlaueMap.put("fieldA", "value1");
		clumandVlaueMap.put("fieldb", "valueb");
		clumandVlaueMap.put("fieldc", "valuec");
		ckr.setClumandVlaueMap(clumandVlaueMap);


		CheckResult ckr1=new CheckResult();
		ckr1.setNo("2");
		Map<String,Object> clumandVlaueMap1=new HashMap<String,Object>(); 
		clumandVlaueMap1.put("111fieldA", "value1");
		clumandVlaueMap1.put("1fieldb", "valueb");
		clumandVlaueMap1.put("fieldc", "valuec");
		ckr1.setClumandVlaueMap(clumandVlaueMap1);


		CheckResult ckr21=new CheckResult();
		ckr21.setNo("3");
		Map<String,Object> clumandVlaueMap2=new HashMap<String,Object>(); 
		clumandVlaueMap2.put("fieldA", "value1");
		clumandVlaueMap2.put("fieldb", "valueb");
		clumandVlaueMap2.put("fieldc", "valuec");
		ckr21.setClumandVlaueMap(clumandVlaueMap2);

		Listckr.add(ckr); 
		Listckr.add(ckr1); 
		Listckr.add(ckr21); 
		String resultStr=JSONObject.toJSONString(Listckr);
		System.out.println(resultStr);
	}

}
