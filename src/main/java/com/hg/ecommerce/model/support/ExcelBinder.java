package com.hg.ecommerce.model.support;

import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.DateFormat;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import com.hg.ecommerce.config.ProjectConfig;
import com.hg.ecommerce.util.Util;

public class ExcelBinder<T> implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3868661871474235719L;
	
	
	private Class<? extends T> cls;//绑定的实体类类对象
	private Map<String, Field> binder = new HashMap<String, Field>();//通过调用bind来存储的映射信息
	private WritableCellFormat dateCellFormat;//日期格式的单元格样式
	private WritableCellFormat headerCellFormat;//header单元格的样式
	private WritableCellFormat bodyCellFormat;//body单元格的样式
	private WritableCellFormat titleCellFormat;//标题单元格的样式
	private String sheetName;//sheet的名字
	private String title; //文件第一行头部
	private int startRow = 0;//开始写入数据第一行
	private int startColumn = 0;//开始写入数据第一列
	
	public ExcelBinder(Class<? extends T> cls){
		this.cls = cls;
	}
	
	
	
	public WritableCellFormat getDateCellFormat() throws WriteException {
		if(dateCellFormat==null){
			DateFormat customDateFormat = new DateFormat(ProjectConfig.getProperty("format.date"));
			dateCellFormat = new WritableCellFormat (customDateFormat);
			dateCellFormat.setFont(new WritableFont(WritableFont.ARIAL, 14, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK));
			dateCellFormat.setAlignment(jxl.format.Alignment.CENTRE);
		}
		return dateCellFormat;
	}
	public void setDateCellFormat(WritableCellFormat dateCellFormat) {
		this.dateCellFormat = dateCellFormat;
	}
	public WritableCellFormat getHeaderCellFormat() throws WriteException {
		if(headerCellFormat==null){
			WritableFont wf_head = new WritableFont(WritableFont.ARIAL, 19, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK); // 定义格式 字体 下划线 斜体 粗体 颜色 
			headerCellFormat = new WritableCellFormat(wf_head); // 单元格定义  
			//headerCellFormat.setBackground(jxl.format.Colour.WHITE); // 设置单元格的背景颜色  
			headerCellFormat.setAlignment(jxl.format.Alignment.CENTRE); // 设置对齐方式 
		}
		return headerCellFormat;
	}
	public void setHeaderCellFormat(WritableCellFormat headerCellFormat) {
		this.headerCellFormat = headerCellFormat;
	}
	
	public WritableCellFormat getBodyCellFormat() throws WriteException {
		if(bodyCellFormat==null){
			WritableFont wf_body = new WritableFont(WritableFont.ARIAL, 14, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK); // 定义格式 字体 下划线 斜体 粗体 颜色 
			bodyCellFormat = new WritableCellFormat(wf_body); // 单元格定义  
			//bodyCellFormat.setBackground(jxl.format.Colour.WHITE); // 设置单元格的背景颜色  
			bodyCellFormat.setAlignment(jxl.format.Alignment.CENTRE); // 设置对齐方式 
		}
		return bodyCellFormat;
	}
	public void setBodyCellFormat(WritableCellFormat bodyCellFormat) {
		this.bodyCellFormat = bodyCellFormat;
	}
	public WritableCellFormat getTitleCellFormat() {
		if(titleCellFormat==null){
			WritableFont wf_body = new WritableFont(WritableFont.TIMES, 21, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.RED); // 定义格式 字体 下划线 斜体 粗体 颜色 
			titleCellFormat = new WritableCellFormat(wf_body); // 单元格定义  
			//titleCellFormat.setBackground(jxl.format.Colour.WHITE); // 设置单元格的背景颜色  
			//titleCellFormat.setAlignment(jxl.format.Alignment.CENTRE); // 设置对齐方式 
		}
		return titleCellFormat;
	}
	public void setTitleCellFormat(WritableCellFormat titleCellFormat) {
		this.titleCellFormat = titleCellFormat;
	}



	public String getSheetName() {
		if(sheetName==null||"".equals(sheetName))
			sheetName = cls.getSimpleName()+" Sheet";
		return sheetName;
	}
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	public String getTitle() {
		if("".equals(title))
			title = null;
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getStartRow() {
		return startRow;
	}
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}
	public int getStartColumn() {
		return startColumn;
	}
	public void setStartColumn(int startColumn) {
		this.startColumn = startColumn;
	}


	/**
	 * 给出单个字段名称和想要打印出来的中文名称，让ExcelBinder来存储映射信息
	 * @param fieldName
	 * @param printName
	 * @return
	 */
	public ExcelBinder<T> bind(String fieldName, String printName) {
		try {
			binder.put(printName,cls.getDeclaredField(fieldName));
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Bind "+fieldName+" to "+printName+" failed.\n"+e.getMessage());
		} 
		return this;
	}
	
	/**
	 * 生成excel文件
	 * @param data
	 * @param out
	 * @return
	 * @throws Exception
	 */
	public void genExcel(List<T> data, OutputStream out) throws Exception{
		
		WritableWorkbook workbook = Workbook.createWorkbook(out);
		
		WritableSheet sheet = workbook.createSheet(getSheetName(), 0);
		
		for(int column = 0; column < 100; column++){
			sheet.setColumnView(column, 28);
		}
		
		int localStartRow = getStartRow();
		
		if(getTitle()!=null){
			sheet.addCell(new Label(startColumn, localStartRow, getTitle(),getTitleCellFormat()));
			sheet.mergeCells(startColumn, localStartRow, 7, localStartRow);
			localStartRow++;
		}
		
		int tempColumnIndex = startColumn;
		for(Map.Entry<String, Field> entry : binder.entrySet()){
			int tempRowIndex = localStartRow;
			sheet.addCell(new Label(tempColumnIndex, tempRowIndex++, entry.getKey(), getHeaderCellFormat()));
			
			for(T t : data){
				Object obj = Util.getFieldValue(entry.getValue(), t);
				
				if(obj instanceof Date){
					sheet.addCell(new Label(tempColumnIndex, tempRowIndex++, Util.dateToString((Date)obj), getDateCellFormat()));
				}else{
					sheet.addCell(new Label(tempColumnIndex, tempRowIndex++, obj.toString(), getBodyCellFormat()));
				}
				
				
			}
			tempColumnIndex++;
		}
		
		workbook.write();
		workbook.close();
	}
}
