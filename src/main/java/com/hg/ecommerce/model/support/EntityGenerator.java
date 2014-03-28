package com.hg.ecommerce.model.support;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
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
	private static final String CWD_RELATIVE_PATH = "src"+File.separatorChar+"main"+File.separatorChar+"java"+File.separatorChar;
	private static final String TEMPLATE_FILE_PATH = "com/hg/ecommerce/model/support/model_template.mustache";
	
	public void generateModel() throws IOException{
		
		String packagePath = Util.join(File.separatorChar, ProjectConfig.getProperty("package.pojo").split("\\."));
		
		Map<TableDef, List<FieldEntry>> metaMap = mapper.fetchDatabaseMeta(ProjectConfig.getProperty("db.url")
				, ProjectConfig.getProperty("db.username")
				, ProjectConfig.getProperty("db.password"));
		
		Resource template = new ClassPathResource(TEMPLATE_FILE_PATH);
		BufferedReader templateReader = new BufferedReader(new InputStreamReader(template.getInputStream()));
		
		StringBuilder builder = new StringBuilder();
		String line;
		while((line=templateReader.readLine())!=null){
			builder.append(line+"\n");
		}
		templateReader.close();
		
		for(Map.Entry<TableDef, List<FieldEntry>> entry : metaMap.entrySet()){
//			System.err.println(entry.getKey().toJSON());
//			System.err.println(Util.getJsonObject(entry.getValue()));
//			System.err.println();
			
			TableDef tableDef = entry.getKey();
			List<FieldEntry> entries = entry.getValue();
			
			Resource resource = new FileSystemResource(CWD_RELATIVE_PATH+packagePath+File.separatorChar+tableDef.getTableName()+SOURCE_FILE_AFFIX);
			//Writer is ready, now start to write something to the opened file
			PrintWriter writer = new PrintWriter(resource.getFile());
			
			ModelConfig modelConfig = new ModelConfig();
			modelConfig.setFieldEntries(entries);
			modelConfig.setModelDescription(tableDef.getTableDescription());
			modelConfig.setModelName(tableDef.getTableName());
			modelConfig.setPackageName(ProjectConfig.getProperty("package.pojo"));
			modelConfig.setSchema((tableDef.getTableCatalog()==null||tableDef.getTableCatalog().equals(""))?tableDef.getTableSchema():tableDef.getTableCatalog());
			modelConfig.setSuperClsPath(EntityObject.class.getName());
			modelConfig.setTable(tableDef.getTableName());
			
			
			Util.render(builder.toString(), writer, modelConfig);
			
			writer.close();
		}
	}
}
