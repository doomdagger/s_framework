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
 * <br><br>
 * <strong>在这里进行PropertyEditor配置是无效的，如果想要配置Editor，请选择使用ControllerAdvice，或者直接在
 * Controller中进行@initBinder配置。</strong>
 * @author Li He
 * @deprecated
 * @since 1.04.2014
 */
@Deprecated
public class ActionBindingInitializer implements WebBindingInitializer{

	@Override
	public void initBinder(WebDataBinder binder, WebRequest request) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}

}
