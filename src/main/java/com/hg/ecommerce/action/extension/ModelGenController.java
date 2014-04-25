package com.hg.ecommerce.action.extension;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.rasc.extclassgenerator.ModelGenerator;
import ch.rasc.extclassgenerator.OutputFormat;

import com.hg.ecommerce.config.ProjectConfig;
import com.hg.ecommerce.model.support.NullModel;

@RestController
@RequestMapping("/extension")
public class ModelGenController {

	@RequestMapping(value="/model/{modelName}",produces=MIME.JS)
    public void user(HttpServletRequest request
    		, HttpServletResponse response
    		, @PathVariable("modelName") String modelName
    		, @RequestParam(value="dto",defaultValue="false") boolean dto) throws IOException {
        
		try{
			String clsName;
			if(dto){
				clsName = ProjectConfig.getProperty("package.dto")+"."+modelName;
			}else{
				clsName = ProjectConfig.getProperty("package.pojo")+"."+modelName;
			}
			Class<?> cls = Class.forName(clsName);
			ModelGenerator.writeModel(request, response, cls, OutputFormat.EXTJS4,true);
		}catch(ClassNotFoundException exception){
			ModelGenerator.writeModel(request, response, NullModel.class, OutputFormat.EXTJS4,true);
		}
		
    }
	
}
