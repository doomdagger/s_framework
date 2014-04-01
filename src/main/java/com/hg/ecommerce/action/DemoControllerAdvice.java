package com.hg.ecommerce.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * 强调下Controller控制器的分包原则，请务必！务必！为Controller依据功能模块分包，这是好的开发习惯，
 * 也是为了能够依据包进行Controller分组，每个ControllerAdvice可以控制一个Controller分组的AOP配置。
 * 
 * 举例来说，包：com.hg.ecommerce.action.repository 负责存放所有跟仓库管理功能相关的Controller，
 * 那么对应的,在com.hg.ecommerce.action.advice中，就应该有RepositoryControllerAdvice类，来为这个包下
 * 的全部Controller提供统一的配置信息：包括DataBinder, ModelAttribute等等。
 * 
 * @author Li He
 * 
 */
@ControllerAdvice(basePackages="com.hg.ecommerce.action.demo")
public class DemoControllerAdvice {

	/**
	 * Date类型对象的Editor覆盖，CustomDateEditor需要继承PropertyEditor，实现两个核心方法，
	 * 从Date到String，以及从String到Date的转换，你可以自定义自己的Model类型的Editor，使其具备String到Model，
	 * Model到String的无缝切换
	 * 
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		
	}
	
	/**
	 * 空指针异常处理
	 * @param exception
	 * @return
	 * @throws IOException 
	 */
	@ExceptionHandler(NullPointerException.class)
	public void handleNullPointer(HttpServletRequest request, HttpServletResponse response, NullPointerException exception) throws IOException{
		response.setCharacterEncoding("utf8");
		response.setContentType("text/html");
		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		PrintWriter writer = response.getWriter();
		writer.print("<pr>");
		exception.printStackTrace(writer);
		writer.print("</pr>");
		writer.flush();
		writer.close();
	}
}
