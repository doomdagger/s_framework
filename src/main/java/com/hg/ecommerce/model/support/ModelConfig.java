package com.hg.ecommerce.model.support;

import java.util.List;
import java.util.Set;

/**
 * be applied to model template
 * @author lihe
 *
 */
public class ModelConfig extends EntityObject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1027102816942259394L;
	
	
	private String packageName;
	private String superClsPath;
	private String modelName;
	private String modelDescription;
	private String schema;
	private String table;
	private Set<String> importInfos;
	
	private List<FieldEntry> fieldEntries;

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getSuperClsPath() {
		return superClsPath;
	}

	public void setSuperClsPath(String superClsPath) {
		this.superClsPath = superClsPath;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getModelDescription() {
		return modelDescription;
	}

	public void setModelDescription(String modelDescription) {
		this.modelDescription = modelDescription;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public Set<String> getImportInfos() {
		return importInfos;
	}

	public void setImportInfos(Set<String> importInfos) {
		this.importInfos = importInfos;
	}

	public List<FieldEntry> getFieldEntries() {
		return fieldEntries;
	}

	public void setFieldEntries(List<FieldEntry> fieldEntries) {
		this.fieldEntries = fieldEntries;
	}

	

	
}
