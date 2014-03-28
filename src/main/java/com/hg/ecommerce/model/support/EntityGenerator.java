package com.hg.ecommerce.model.support;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.hg.ecommerce.config.ProjectConfig;
import com.hg.ecommerce.config.ProjectContainer;
import com.hg.ecommerce.util.Util;

public class EntityGenerator {
	
	/**
	 * 向 ProjectContainer 索取 mapper对象
	 */
	private FieldTypeMapper mapper = ProjectContainer.getInstance(FieldTypeMapper.class);
	private static final String SOURCE_FILE_AFFIX = ".java";
	
	public void generateModel() throws IOException{
		
		String packagePath = Util.join(File.separatorChar, ProjectConfig.getProperty("package.pojo").split("\\."));
		
		Map<TableDef, List<FieldEntry>> metaMap = mapper.fetchDatabaseMeta(ProjectConfig.getProperty("db.url")
				, ProjectConfig.getProperty("db.username")
				, ProjectConfig.getProperty("db.password"));
		
		
		for(Map.Entry<TableDef, List<FieldEntry>> entry : metaMap.entrySet()){
//			System.err.println(entry.getKey().toJSON());
//			System.err.println(Util.getJsonObject(entry.getValue()));
//			System.err.println();
			
			TableDef tableDef = entry.getKey();
			List<FieldEntry> entries = entry.getValue();
			
			Resource resource = new ClassPathResource(packagePath+File.separatorChar+tableDef.getTableName()+SOURCE_FILE_AFFIX);
			
			PrintWriter writer = new PrintWriter(resource.getFile());
			
			writer.print("hello world!");
			
			writer.close();
		}
	}
}
