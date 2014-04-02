package com.hg.ecommerce.test;

import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Date;

import com.hg.ecommerce.model.SysSetting;
import com.hg.ecommerce.model.support.ExcelBinder;

public class ExcelTest {
	public static void main(String[] args) throws Exception{
		ExcelBinder<SysSetting> binder = new ExcelBinder<SysSetting>(SysSetting.class);
		binder.setSheetName("系统设置表单");
		binder.setTitle("这是一个系统设置表单");
		binder.bind("propKey", "参数关键字").bind("propValue", "参数值").bind("remark", "描述").bind("createtime", "创建时间");
	
		FileOutputStream fileOutputStream = new FileOutputStream("demo.xls");
		
		SysSetting s1 = new SysSetting();
		s1.setPropKey("关键字1");
		s1.setPropValue("关键值1");
		s1.setRemark("描述1");
		s1.setCreatetime(new Date());
		SysSetting s2 = new SysSetting();
		s2.setPropKey("关键字2");
		s2.setPropValue("关键值2");
		s2.setRemark("描述2");
		s2.setCreatetime(null);
		SysSetting s3 = new SysSetting();
		s3.setPropKey("关键字3");
		s3.setPropValue("关键值3");
		s3.setRemark("描述3");
		s3.setCreatetime(new Date());
		
		binder.genExcel(Arrays.asList(s1,s2,s3), fileOutputStream);
	
		fileOutputStream.close();
	}
}
