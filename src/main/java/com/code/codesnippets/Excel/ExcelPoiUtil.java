package com.code.codesnippets.Excel;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 导入导出工具类
 * @author xushijie
 *
 */
public class ExcelPoiUtil {
	
	
	private static XSSFWorkbook wwb = null;
	
	private final static String excel2003L =".xls";    //2003- 版本的excel  
	
    private final static String excel2007U =".xlsx";   //2007+ 版本的excel  
      
    /** 
     * 描述：获取IO流中的数据，组装成List<List<Object>>对象 
     * @param in,fileName,isFilter
     * @return 
     * @throws IOException  
     */  
    public  List<List<Object>> getBankListByExcel(InputStream in,String fileName, boolean isFilter) throws Exception{  
        List<List<Object>> list = null;  
        //创建Excel工作薄  
        Workbook work = this.getWorkbook(in,fileName);  
        if(null == work){  
            throw new Exception("创建Excel工作薄为空！");  
        }  
        Sheet sheet = null;  
        Row row = null;  
        Cell cell = null;  
        list = new ArrayList<List<Object>>();  
        //遍历Excel中所有的sheet  
        for (int i = 0; i < work.getNumberOfSheets(); i++) {  
            sheet = work.getSheetAt(i);  
            if(sheet==null){continue;}  
              
            //遍历当前sheet中的所有行  
			for (int j = sheet.getFirstRowNum(); j <= sheet.getLastRowNum(); j++) {
				row = sheet.getRow(j);
				if (row == null || row.getFirstCellNum() == j) {
					continue;
				}
				if(isFilter && (row.getFirstCellNum() + 1) == j){ //判断是否过滤第二行
					continue;
				}
				// 遍历所有的列
				List<Object> li = new ArrayList<Object>();
				for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
					cell = row.getCell(y);
					if (cell != null) {
						li.add(this.getCellValue(cell));
					} else {
						li.add(null);
					}
				}
				list.add(li);
			}
        }  
        return list;  
    }  
      
    /** 
     * 描述：根据文件后缀，自适应上传文件的版本  
     * @param inStr,fileName 
     * @return 
     * @throws Exception 
     */  
    public  Workbook getWorkbook(InputStream inStr,String fileName) throws Exception{  
        Workbook wb = null;  
        String fileType = fileName.substring(fileName.lastIndexOf("."));  
        if(excel2003L.equals(fileType)){  
            wb = new HSSFWorkbook(inStr);  //2003-  
        }else if(excel2007U.equals(fileType)){  
            wb = new XSSFWorkbook(inStr);  //2007+  
        }else{  
            throw new Exception("解析的文件格式有误！");  
        }  
        return wb;  
    }  
  
    /** 
     * 描述：对表格中数值进行格式化 
     * @param cell 
     * @return 
     */  
    public  Object getCellValue(Cell cell){  
        Object value = null;  
        DecimalFormat df = new DecimalFormat("0");  //格式化number String字符  
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");  //日期格式化  
        DecimalFormat df2 = new DecimalFormat("0.00");  //格式化数字  
        switch (cell.getCellType()) {  
        case Cell.CELL_TYPE_STRING:  
            value = cell.getRichStringCellValue().getString();  
            break;  
        case Cell.CELL_TYPE_NUMERIC:  
			if ("General".equals(cell.getCellStyle().getDataFormatString())) {
				value = df.format(cell.getNumericCellValue());
			} else if ("m/d/yy".equals(cell.getCellStyle().getDataFormatString())
					|| "yyyy/m/d;@".equals(cell.getCellStyle().getDataFormatString())
					|| "d-mmm-yy".equals(cell.getCellStyle().getDataFormatString())
					|| "yyyy/m/d".equals(cell.getCellStyle().getDataFormatString())) {
				value = sdf.format(cell.getDateCellValue());
			} else {
				value = cell.getRichStringCellValue().getString();
			}  
            break;  
        case Cell.CELL_TYPE_BOOLEAN:  
            value = cell.getBooleanCellValue();  
            break;  
        case Cell.CELL_TYPE_BLANK:  
            value = "";  
            break;  
        default:  
            break;  
        }  
        return value;  
    }  
	
	
	/**
	 * 导出方法
	 * @param tittle
	 * @param data
	 * @param ouputStream
	 * @return
	 */
	public static <T> void export(Map<String, String> tittle,List<T> data, OutputStream ouputStream,String fileName){
		try {
			wwb = new XSSFWorkbook();
			SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook(wwb, 100);
			CellStyle cellStyle = wwb.createCellStyle();
			// 添加第一个工作表并设置第一个Sheet的名字
			cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);  
            cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); 
            XSSFSheet sheet = wwb.createSheet(fileName);
            ExportStyleUtil exportUtil = new ExportStyleUtil(wwb, sheet);  
            XSSFCellStyle headStyle = exportUtil.getHeadStyle();  
            XSSFCellStyle bodyStyle = exportUtil.getBodyStyle();
            int j=0,i = 0;
            // 构建表头  
            XSSFRow headRow = sheet.createRow(j);  
            XSSFCell cell = null;  
			Set<String> keys = tittle.keySet();
			for (String key : keys) {
				cell = headRow.createCell(i);  
	            cell.setCellStyle(headStyle);  
	            cell.setCellValue(tittle.get(key));
				i++;
			}
			j=1;
			if(null != data && data.size()>0){
				for(int k=0;k<data.size();k++){
					i = 0;
					T object = data.get(k);
					XSSFRow bodyRow = sheet.createRow(j + k); 
					for (String key : keys) {
						cell = bodyRow.createCell(i);  
		                cell.setCellStyle(bodyStyle);  
		                cell.setCellValue(getValue(key,object)); 
						i++;
					}
				}
			}
			sxssfWorkbook.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			try {
				if(ouputStream!=null){
					ouputStream.close();
				}
			} catch (IOException e) {
				new RuntimeException(e);
			}
		}
	}
	
	/**
	 * 获取对象  对应属性的值
	 * @param key
	 * @param object
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("rawtypes")
	public static <T> String getValue(String key,T object) throws IllegalArgumentException, IllegalAccessException{
		String value = "";
		Class objClass = object.getClass();
		Field[] fields = objClass.getDeclaredFields();
		if(null != fields && fields.length > 0){
			for(Field field : fields){
				field.setAccessible(true);
				String fieldName = field.getName();
				if(key.equals(fieldName)){
					String fieldType = field.getType().getSimpleName();
					if(null != field.get(object)){
						/*if("Long".equals(fieldType)){
							value = DateUtil.formatDateByFormat(new Date((Long) field.get(object)),"yyyy-MM-dd HH:mm:ss");
						} else if("Date".equals(fieldType)){
							value = DateUtil.formatDateByFormat((Date) field.get(object),"yyyy-MM-dd HH:mm:ss");
						}else{*/
							value = field.get(object).toString();
						//}
					}
					break;
				}
			}
		}
		return value;
	}
	
}
