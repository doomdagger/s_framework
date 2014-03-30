package com.hg.ecommerce.model.support;

/**
 * 对应的是一个Field，功用实体
 * <ol>
 * <li>TABLE_CAT String => table catalog (may be null)</li>
 * <li>TABLE_SCHEM String => table schema (may be null)</li>
 * <li>TABLE_NAME String => table name</li>
 * <li>COLUMN_NAME String => column name</li>
 * <li>DATA_TYPE int => SQL type from java.sql.Types</li>
 * <li>TYPE_NAME String => Data source dependent type name, for a UDT the type
 * name is fully qualified</li>
 * <li>COLUMN_SIZE int => column size.</li>
 * <li>BUFFER_LENGTH is not used.</li>
 * <li>DECIMAL_DIGITS int => the number of fractional digits. Null is returned
 * for data types where DECIMAL_DIGITS is not applicable.</li>
 * <li>NUM_PREC_RADIX int => Radix (typically either 10 or 2)</li>
 * <li>NULLABLE int => is NULL allowed.
 * <ul>
 * <li>columnNoNulls - might not allow NULL values</li>
 * <li>columnNullable - definitely allows NULL values</li>
 * <li>columnNullableUnknown - nullability unknown</li>
 * </ul>
 * </li>
 * <li>REMARKS String => comment describing column (may be null)</li>
 * <li>COLUMN_DEF String => default value for the column, which should be
 * interpreted as a string when the value is enclosed in single quotes (may be
 * null)</li>
 * <li>SQL_DATA_TYPE int => unused</li>
 * <li>SQL_DATETIME_SUB int => unused</li>
 * <li>CHAR_OCTET_LENGTH int => for char types the maximum number of bytes in
 * the column</li>
 * <li>ORDINAL_POSITION int => index of column in table (starting at 1)</li>
 * <li>IS_NULLABLE String => ISO rules are used to determine the nullability for
 * a column.
 * <ul>
 * <li>YES --- if the column can include NULLs</li>
 * <li>NO --- if the column cannot include NULLs</li>
 * <li>empty string --- if the nullability for the column is unknown</li>
 * </ul>
 * </li>
 * <li>SCOPE_CATALOG String => catalog of table that is the scope of a reference
 * attribute (null if DATA_TYPE isn't REF)</li>
 * <li>SCOPE_SCHEMA String => schema of table that is the scope of a reference
 * attribute (null if the DATA_TYPE isn't REF)</li>
 * <li>SCOPE_TABLE String => table name that this the scope of a reference
 * attribute (null if the DATA_TYPE isn't REF)</li>
 * <li>SOURCE_DATA_TYPE short => source type of a distinct type or
 * user-generated Ref type, SQL type from java.sql.Types (null if DATA_TYPE
 * isn't DISTINCT or user-generated REF)</li>
 * <li>IS_AUTOINCREMENT String => Indicates whether this column is auto
 * incremented
 * <ul>
 * <li>YES --- if the column is auto incremented</li>
 * <li>NO --- if the column is not auto incremented</li>
 * <li>empty string --- if it cannot be determined whether the column is auto
 * incremented</li>
 * </ul>
 * </li>
 * <li>IS_GENERATEDCOLUMN String => Indicates whether this is a generated column
 * <ul>
 * <li>YES --- if this a generated column</li>
 * <li>NO --- if this not a generated column</li>
 * <li>empty string --- if it cannot be determined whether this is a generated
 * column</li>
 * </ul>
 * </li>
 * </ol>
 * 
 * @author Li He
 * 
 */
public class FieldEntry extends EntityObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2657228099216613449L;

	// 字段名称
	private String fieldName;
	//原有字段名称
	private String mappedFieldName;
	//用户setter与getter的字段名
	private String qualifiedFieldName;
	// 字段类型名称
	private String typeName;
	// 数据库字段名
	private String mappedTypeName;
	// 字段类型
	private int fieldType;
	// 字段长度
	private int fieldLength;
	// 小数位
	private int decimalDigits; //DECIMAL_DIGITS 
	// 列说明
	private String fieldDescription; //REMARKS 
	// 字符占位
	private int charLength; //CHAR_OCTET_LENGTH 
	// 外键指向表
	private String refTableName; //SCOPE_TABLE 
	// 是否为主键
	private boolean primaryKey;
	
	
	public boolean isPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public int getFieldType() {
		return fieldType;
	}

	public void setFieldType(int fieldType) {
		this.fieldType = fieldType;
	}

	public int getDecimalDigits() {
		return decimalDigits;
	}

	public void setDecimalDigits(int decimalDigits) {
		this.decimalDigits = decimalDigits;
	}

	public String getFieldDescription() {
		return fieldDescription;
	}

	public void setFieldDescription(String fieldDescription) {
		this.fieldDescription = fieldDescription;
	}

	public int getCharLength() {
		return charLength;
	}

	public void setCharLength(int charLength) {
		this.charLength = charLength;
	}

	public String getRefTableName() {
		return refTableName;
	}

	public void setRefTableName(String refTableName) {
		this.refTableName = refTableName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public int getFieldLength() {
		return fieldLength;
	}

	public void setFieldLength(int fieldLength) {
		this.fieldLength = fieldLength;
	}

	public String getMappedTypeName() {
		return mappedTypeName;
	}

	public void setMappedTypeName(String mappedTypeName) {
		this.mappedTypeName = mappedTypeName;
	}

	public String getMappedFieldName() {
		return mappedFieldName;
	}

	public void setMappedFieldName(String mappedFieldName) {
		this.mappedFieldName = mappedFieldName;
	}

	public String getQualifiedFieldName() {
		return qualifiedFieldName;
	}

	public void setQualifiedFieldName(String qualifiedFieldName) {
		this.qualifiedFieldName = qualifiedFieldName;
	}

}
