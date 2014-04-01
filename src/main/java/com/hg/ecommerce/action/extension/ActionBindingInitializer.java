package com.hg.ecommerce.action.extension;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

/**
 * 全局的PropertyEditor覆盖，请在这里注册
 * 如果PropertyEditor是基于Controller分组的，请与action包中的ControllerAdvice中注册
 * 
 * 除了Editor的注册之外，TypeConversion， 以及Formatter都可以在这里进行~
 * @author Li He
 *
 */
public class ActionBindingInitializer implements WebBindingInitializer{

	@Override
	public void initBinder(WebDataBinder binder, WebRequest request) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}

}
