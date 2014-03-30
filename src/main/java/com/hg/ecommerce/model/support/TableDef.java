package com.hg.ecommerce.model.support;

import java.util.HashSet;
import java.util.Set;

/**
 * <ol>
 * 	<li>TABLE_CAT String => table catalog (may be null) </li>
 *	<li>TABLE_SCHEM String => table schema (may be null)</li> 
 *	<li>TABLE_NAME String => table name </li>
 *	<li>TABLE_TYPE String => table type. Typical types are "TABLE", "VIEW", "SYSTEM TABLE", "GLOBAL TEMPORARY", "LOCAL TEMPORARY", "ALIAS", "SYNONYM". </li>
 *	<li>REMARKS String => explanatory comment on the table </li>
 *	<li>TYPE_CAT String => the types catalog (may be null) </li>
 *	<li>TYPE_SCHEM String => the types schema (may be null) </li>
 *	<li>TYPE_NAME String => type name (may be null) </li>
 *	<li>SELF_REFERENCING_COL_NAME String => name of the designated "identifier" column of a typed table (may be null) </li>
 * 	<li>REF_GENERATION String => specifies how values in SELF_REFERENCING_COL_NAME are created. Values are "SYSTEM", "USER", "DERIVED". (may be null) </li>
 * </ol>
 * @author Li He
 *
 */
public class TableDef extends EntityObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8014515703325722197L;

	//表名
	private String tableName;
	//表描述
	private String tableDescription;
	//所属cat
	private String tableCatalog;
	//所属schema
	private String tableSchema;
	//表类型
	private String tableType;
	//主键字段名称
	private Set<String> pkColumnNames;
	
	public void addPKColumnName(String columnName){
		if(pkColumnNames==null){
			pkColumnNames = new HashSet<String>();
		}
		pkColumnNames.add(columnName);
	}
	
	public Set<String> getPkColumnNames() {
		return pkColumnNames;
	}
	public void setPkColumnNames(Set<String> pkColumnNames) {
		this.pkColumnNames = pkColumnNames;
	}
	public String getTableCatalog() {
		return tableCatalog;
	}
	public void setTableCatalog(String tableCatalog) {
		this.tableCatalog = tableCatalog;
	}
	public String getTableSchema() {
		return tableSchema;
	}
	public void setTableSchema(String tableSchema) {
		this.tableSchema = tableSchema;
	}
	public String getTableType() {
		return tableType;
	}
	public void setTableType(String tableType) {
		this.tableType = tableType;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTableDescription() {
		return tableDescription;
	}
	public void setTableDescription(String tableDescription) {
		this.tableDescription = tableDescription;
	}
	
	
}
