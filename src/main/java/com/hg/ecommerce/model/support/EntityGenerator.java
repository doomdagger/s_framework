package com.hg.ecommerce.model.support;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import ch.rasc.extclassgenerator.Model;
import ch.rasc.extclassgenerator.ModelField;

import com.hg.ecommerce.config.ProjectConfig;
import com.hg.ecommerce.config.ProjectContainer;
import com.hg.ecommerce.model.support.annotation.Column;
import com.hg.ecommerce.model.support.annotation.Id;
import com.hg.ecommerce.model.support.annotation.Table;
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
		
		List<String> mustache = new ArrayList<String>();
		String line;
		while((line=templateReader.readLine())!=null){
			mustache.add(line);
		}
		templateReader.close();
		
		for(Map.Entry<TableDef, List<FieldEntry>> entry : metaMap.entrySet()){
//			System.err.println(entry.getKey().toJSON());
//			System.err.println(Util.getJsonObject(entry.getValue()));
//			System.err.println();
			
			TableDef tableDef = entry.getKey();
			List<FieldEntry> entries = entry.getValue();
			
			Resource resource = new FileSystemResource(CWD_RELATIVE_PATH+packagePath+File.separatorChar+Util.qualifyModelName(tableDef.getTableName())+SOURCE_FILE_AFFIX);
			//Writer is ready, now start to write something to the opened file
			PrintWriter writer = new PrintWriter(resource.getFile());
			
			ModelConfig modelConfig = new ModelConfig();
			modelConfig.setFieldEntries(entries);
			modelConfig.setModelDescription(Util.conditionedGet(tableDef.getTableDescription(), "No Description Available"));
			modelConfig.setModelName(Util.qualifyModelName(tableDef.getTableName()));
			modelConfig.setPackageName(ProjectConfig.getProperty("package.pojo"));
			modelConfig.setSchema(Util.conditionedGet(tableDef.getTableCatalog(), tableDef.getTableSchema()));
			modelConfig.setSuperClsPath(EntityObject.class.getName());
			modelConfig.setTable(tableDef.getTableName());
			modelConfig.setExtPackageName(ProjectConfig.getProperty("extjs.model.package"));
			
			Set<String> importInfos = new HashSet<String>();
			importInfos.add(modelConfig.getSuperClsPath());
			importInfos.add(Table.class.getName());
			importInfos.add(Column.class.getName());
			importInfos.add(Model.class.getName());
			
			for(FieldEntry fieldEntry : entries){
				@SuppressWarnings("rawtypes")
				Class cls = mapper.mapFieldType(fieldEntry);
				
				if(fieldEntry.isDateType()){
					importInfos.add(ModelField.class.getName());
					fieldEntry.setExtDateFormat(ProjectConfig.getProperty("extjs.format.date"));
				}
				
				String clsName = cls.getName();
				
				if(clsName.split("\\.").length>1){
					if(!clsName.contains("java.lang."))
						importInfos.add(cls.getName());
				}
				
				if(tableDef.getPkColumnNames().contains(fieldEntry.getMappedFieldName())){
					importInfos.add(Id.class.getName());
					fieldEntry.setPrimaryKey(true);
				}
				
				fieldEntry.setFieldName(Util.qualifyFieldName(fieldEntry.getMappedFieldName()));
				fieldEntry.setQualifiedFieldName(Util.repairName(fieldEntry.getFieldName()));
				fieldEntry.setTypeName(cls.getSimpleName());
			}
			
			modelConfig.setImportInfos(importInfos);
			
			Util.render(mustache, writer, modelConfig.toJSON());
			
			
			writer.flush();
			writer.close();
		}
	}
}
