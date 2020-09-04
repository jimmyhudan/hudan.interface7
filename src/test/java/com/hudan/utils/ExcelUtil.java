package com.hudan.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;

import com.hudan.interf.pojo.Case;
import com.hudan.interf.pojo.Rest;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellAddress;

/***
 * 
 * 读取Excel相关行列的数据，行列不连续
 * 
 * @author hudan
 *
 */
public class ExcelUtil {

	//	public static Object[][] read(String filePath,int sheetNum,int startRow,int endRow)
	//	{
	//		
	//	}


	/**
	 * 
	 * @param filePath 文件路径
	 * @param rows 行
	 * @param cells 列
	 * @param  sheetNum 传的是表单号，不是表单的索引
	 * @return
	 */

	public static Object[][] read(String filePath,int sheetNum,int [] rows,int [] cells)
	{
		//定义一个二维数组，保存取出的数据
		Object [] [] dataobjs=new Object[rows.length][cells.length];
		try {
			//获取文件的路径
			Workbook workbook=WorkbookFactory.create(new File(filePath));
			//拿出我们要操作的表单
			Sheet sheet= workbook.getSheetAt(sheetNum-1);
			//取出我们想要的行和列对应的数据，处理行
			for(int i=0;i<rows.length;i++)
			{
				//取出区间的每一行
				Row row=sheet.getRow(rows[i]-1);
				//取出对应每一行的相应列的值
				for(int j=0;j<cells.length;j++)
				{
					//设置空列的处理策略
					Cell cell=row.getCell(cells[j]-1,MissingCellPolicy.CREATE_NULL_AS_BLANK);
					//设置列类型为字符串
					cell.setCellType(CellType.STRING);
					//取出列的值
					String strValue=cell.getStringCellValue();
					dataobjs[i][j]=strValue;

				}
			}

		}catch(Exception e)
		{
			e.printStackTrace(); 
		}
		return dataobjs;
	}


	/***
	 * 将接口响应的数据写入到EXCEL里面去 
	 * @param sheetNum 表单的序号
	 * @param caseID 用例编号
	 * @param cellnum  需要写数据的列号
	 * @param result 需要写进去的数据
	 * @return */

	public static String writeResultToExcel(String filePath,int sheetNum,String caseID,int cellnum,String result) {
		// TODO Auto-generated method stub
		OutputStream ostream=null;
		InputStream	istream=null;
		Workbook workbook;
		try {
			//new File("target/test-classes/rest_info.xlsx")
			istream=new FileInputStream(new File(filePath));
			workbook = WorkbookFactory.create(istream); 
			Sheet sheet =workbook.getSheetAt(sheetNum-1); 
			//获取EXCEL的最后一行的行索引
			int lastRowNum=sheet.getLastRowNum();
			for(int i=0;i<=lastRowNum;i++)
			{
				Row row=sheet.getRow(i);
				//取出每行的第一列
				Cell firstCell=row.getCell(0,MissingCellPolicy.CREATE_NULL_AS_BLANK);
				firstCell.setCellType(CellType.STRING);
				String cellValue=firstCell.getStringCellValue();
				//如果此行的第一列数据跟我们的用例编号是一样的，那么此行就是我们想要的这一行
				if(caseID.equals(cellValue))
				{
					//取出要写入的这一列
					Cell toBeWriteCell=row.getCell(cellnum-1,MissingCellPolicy.CREATE_NULL_AS_BLANK);
					toBeWriteCell.setCellType(CellType.STRING);
					//赋值
					toBeWriteCell.setCellValue(result);
					break;
				}

			} 
			ostream=new FileOutputStream(new File(filePath));
			//往EXCEL写
			workbook.write(ostream);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(ostream!=null)
			{
				try {
					ostream.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}

			if(istream!=null)
			{
				try {
					istream.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}

		return null;
	}


	/**
	 * 批量写入测试结果到Excel用例
	 * @param filePath 用例文件路径
	 * @param SheetNum 用例表单序号，索引为sheetNum-1
	 */
	public static void batchWriteTestResultToExcel(String filePath, String SheetNum) {
		InputStream iStream=null;
		FileOutputStream oStream=null;
		try {
			iStream = new FileInputStream(new File(filePath));
			Workbook workbook=WorkbookFactory.create(iStream);
			Sheet sheet=workbook.getSheetAt(Integer.valueOf(SheetNum)-1);
			//取出数据,取出MAP中用例编号的集合 
			Set<String> caseIdSet=CaseResultUtil.caseResultsMapping.keySet();
			//循环处理每一条用例下需要写如的数据
			for(String caseId:caseIdSet)
			{
				//一行行匹配
				int lastRowIndex=sheet.getLastRowNum();
				for(int i=0;i<=lastRowIndex;i++)
				{
					Row row=sheet.getRow(i);
					//取出第一列,
					Cell cell=row.getCell(0,MissingCellPolicy.CREATE_NULL_AS_BLANK);
					cell.setCellType(CellType.STRING);
					String firstCellValue=cell.getStringCellValue();
					//依次判断，
					if(caseId.equals(firstCellValue))
					{
						//取出要写入的列（列名+需要写入的列的值）
						Map<String,String> cellNumAndValueMap=CaseResultUtil.caseResultsMapping.get(caseId);
						Set<String> cellNames=cellNumAndValueMap.keySet();

						for(String cellName:cellNames)
						{
							//根据列名拿到此列的序号
							int cellNum=CaseUtil.cellNameAndCellNumMap.get(cellName);
							Cell toBeWritecell=row.getCell(cellNum,MissingCellPolicy.CREATE_NULL_AS_BLANK);
							toBeWritecell.setCellType(CellType.STRING);
							String values=cellNumAndValueMap.get(cellName);
							//如果超过，就截取 
							if(values.length()>32767)
							{
								values=values.substring(0,300); 
							}
							toBeWritecell.setCellValue(values); 
						}
						break;
					}
				}

			}
			oStream=new FileOutputStream(new File(filePath));
			workbook.write(oStream);

		} catch (Exception e) {
			System.out.println("***********************异常***********************"+e.getMessage());
		}finally {
			if(oStream!=null)
			{
				try {
					oStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(iStream!=null)
			{
				try {
					iStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	//	public static void main(String [] args)
	//	{
	//		int [] rows= {2,3,4,5,6,7}; //行 
	//		int [] cells= {1,2,3,4};   //列 /hudan.interface7/src/test/resources 
	//		Object [] [] dataobjs=ExcelUtil.read("src/test/resources/register.xlsx",1, rows, cells);
	//		for(Object [] objects:dataobjs)
	//		{
	//			//打印行的数据
	//			for(Object object:objects) {
	//				System.out.print("{"+object+"}");
	//			}
	//			System.out.println();
	//		}
	//
	//
	//	}


	//把接口文档全部读处来放到内存
	public static <T> void loadBeans(String filePath, int sheetNum,Class<T> Clazz) {
		InputStream ipStrm=null; 
		// TODO Auto-generated method stub
		try {
			ipStrm=new FileInputStream(new File(filePath));
			Workbook workbook=WorkbookFactory.create(ipStrm);
			Sheet sheet=workbook.getSheetAt(sheetNum-1);
			int lastRowIndex=sheet.getLastRowNum();
			String [] fields=null;
			for(int i=0;i<=lastRowIndex;i++)
			{
				Row row=sheet.getRow(i);
				int lastcell=row.getLastCellNum();
				if(i==0)//处理的第一行：标题行
				{
					fields=new String[lastcell]; 
					for(int j=0;j<lastcell;j++)
					{
						Cell fieldCell=row.getCell(j, MissingCellPolicy.CREATE_NULL_AS_BLANK);
						fieldCell.setCellType(CellType.STRING);
						String fieldValue=fieldCell.getStringCellValue();
						fieldValue=fieldValue.substring(0,fieldValue.indexOf("("));
						fields[j]=fieldValue;
						//列所在的位置
						int cellNum =fieldCell.getAddress().getColumn();
						CaseUtil.cellNameAndCellNumMap.put(fieldValue, cellNum);
					}
				}else//处理的数据行
				{	
					//Class clazz=Rest.class; 
					Object object=Clazz.newInstance(); 

					for(int j=0;j<lastcell;j++)
					{
						Cell dataCell=row.getCell(j, MissingCellPolicy.CREATE_NULL_AS_BLANK);
						dataCell.setCellType(CellType.STRING);
						String fieldValue=dataCell.getStringCellValue(); 
						String methodName="set"+fields[j];
						Method method=Clazz.getMethod(methodName, String.class);
						method.invoke(object, fieldValue);

					} 
					if(object instanceof Rest)
					{
						Rest rest=(Rest) object;
						RestUtil.rests.add(rest); 
					}else if(object instanceof Case)
					{
						Case cases11=(Case) object;
						CaseUtil.cases.add(cases11);
					}

				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	public static void main(String[] args)
	{
		FileInputStream ipStrm;
		try {
			ipStrm = new FileInputStream(new File("src/test/resources/register.xlsx")); 
			Workbook workbook; 
			workbook = WorkbookFactory.create(ipStrm);
			Sheet sheet=workbook.getSheetAt(0);
			Cell cell=sheet.getRow(0).getCell(0,MissingCellPolicy.CREATE_NULL_AS_BLANK);
			CellAddress cellAddress=cell.getAddress();	
			int rowNum=cellAddress.getRow();
			int cellNum=cellAddress.getColumn();
			System.out.println("rowNum:"+rowNum+"cellNum:"+cellNum);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	/**
	 * 
	 * @param filePath 要写入的文件路径
	 * @param sheetName   要写入的数据表单名称
	 */
	public static void writeVarableValue(String filePath, String sheetName) {
		// TODO Auto-generated method stub
		InputStream istream=null;
		OutputStream outputStream=null;
		try {
			istream=new FileInputStream(new File(filePath));
			Workbook workbook = WorkbookFactory.create(istream);
//			Sheet sheet=workbook.getSheetAt(Integer.valueOf(sheetName).intValue()-1); //表单的索引来取值
			
			Sheet sheet=workbook.getSheet(sheetName);//表单的名称来取值
			Set<String> variables=RestUtil.variableValues.keySet();
			Object [] vatiableNames=variables.toArray();
			for(int i=1;i<=vatiableNames.length;i++)
			{
				String vatiableName=(String) vatiableNames[i-1];
				String variableValue=RestUtil.variableValues.get(vatiableName);
				//取出一行，然后往第一行第二行写入数据 
				Row  row=sheet.getRow(i);
				if(row==null)
				{
					row=sheet.createRow(i);	
				}
				
				Cell variableNameCell=row.getCell(0,MissingCellPolicy.CREATE_NULL_AS_BLANK);
				variableNameCell.setCellType(CellType.STRING);
				variableNameCell.setCellValue(vatiableName);
				
				Cell variableValueCell=row.getCell(1,MissingCellPolicy.CREATE_NULL_AS_BLANK);
				variableValueCell.setCellType(CellType.STRING);
				variableValueCell.setCellValue(variableValue);
			}
			
			outputStream=new FileOutputStream(new File(filePath));
			workbook.write(outputStream);
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally {
			if(outputStream!=null)
			{
				try {
					outputStream.close();
				}catch(IOException e)
				{
					e.printStackTrace();
				}
			}
			
			if(istream!=null)
			{
				try {
					istream.close();
				}catch(IOException e)
				{
					e.printStackTrace();
				}
			}
			
			System.out.println("finish");
		}

	}
}
