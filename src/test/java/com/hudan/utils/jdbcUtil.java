package com.hudan.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.hudan.interf.pojo.DBChecker;

public class jdbcUtil {
	public static Properties properties;
	//静态代码快
	static {
		properties=new Properties();
		InputStream is;
		try { 
			is=new FileInputStream(new File("src/test/resources/jdbc.properties"));
			properties.load(is);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//,Object...  dbcheck可变参数
	public  static Map<String,Object>query(String sql,Object...  objects)
	{
		Map<String,Object> map=new HashMap<String,Object>();
		
		try {
			//1.注册驱动
			Class.forName(properties.getProperty("jdbc.driver"));
			//2提供链接信息，获取连接
			String url=properties.getProperty("jdbc.url");
			String user=properties.getProperty("jdbc.username");
			String password=properties.getProperty("jdbc.password");
			Connection con=DriverManager.getConnection(url, user,password);
			//3.获取Statement对象
			PreparedStatement prepardStatemnet=con.prepareStatement(sql);
			//通过循环给条件字段赋值,键值队
			for(int i=0;i<objects.length;i++)
			{
				prepardStatemnet.setObject(i+1, objects[i]);
			}
			//4.执行查询
			ResultSet resultSet=prepardStatemnet.executeQuery();
			//获取元数据（元数据里包含了查询的信息：查询字段信息，条件字段信息）
			ResultSetMetaData metaData=resultSet.getMetaData();
			//拿到结果集列的个数
			int columCount=metaData.getColumnCount();
			while(resultSet.next())
			{
				//循环取出每列的列名
				for(int i=1;i<=columCount;i++)
				{
					//根据列的索引拿列名
					String columLable=metaData.getColumnLabel(i);
					//根据每列的列名取出列的值
					Object value=resultSet.getObject(columLable);
					//把列名跟列的值以键值对的形式保存到Map
					map.put(columLable, value);
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	
	
	
 	public static void main(String [] args)
		{ 
			String sqlSgtr="select count(*) as prices from program_book where  book_price<=23";
			Map<String,Object>values=query(sqlSgtr);
			for(String key:values.keySet())
			{
				System.out.println("key="+key+",values="+values+"");

			}
		}
}
