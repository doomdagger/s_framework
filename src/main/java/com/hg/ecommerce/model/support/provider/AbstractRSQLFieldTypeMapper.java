package com.hg.ecommerce.model.support.provider;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hg.ecommerce.model.support.FieldEntry;
import com.hg.ecommerce.model.support.FieldTypeMapper;
import com.hg.ecommerce.model.support.TableDef;

public abstract class AbstractRSQLFieldTypeMapper implements FieldTypeMapper {

	/**
	 * <p>
	 * 获取Connection
	 * </p>
	 * 
	 * @param url
	 *            基于JDBC的规范数据库url
	 * @param username
	 *            用户名
	 * @param password
	 *            用户密码
	 * @return 返回Connection，可能为null
	 */
	protected Connection fetchConnection(String url, String username,
			String password) {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(url, username, password);
		} catch (SQLException exception) {
			exception.printStackTrace();
		}

		return connection;
	}

	/**
	 * 关闭连接，处理异常代码
	 * 
	 * @param connection
	 */
	public void closeConnection(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException exception) {
				exception.printStackTrace();
			}
		}
	}

	@Override
	public Map<TableDef, List<FieldEntry>> fetchDatabaseMeta(String url,
			String username, String password) {
		Connection connection = fetchConnection(url, username, password);
		if (connection == null)
			return null;

		Map<TableDef, List<FieldEntry>> metaMap = new HashMap<TableDef, List<FieldEntry>>();

		ResultSet rs = null;
		ResultSet temp_rs = null;
		try {
			DatabaseMetaData metaData = connection.getMetaData();

			// test... I want to know all the table types
			// List<String> tableTypes = new ArrayList<String>();
			// rs = metaData.getTableTypes();
			// while (rs.next()) {
			// tableTypes.add(rs.getString(1));
			// }
			// System.err.println(tableTypes.toString());
			// test end...

			// 获取table列表
			rs = metaData.getTables(connection.getCatalog(),
					connection.getSchema(), null, null);
			while (rs.next()) {
				String tableType = rs.getString("TABLE_TYPE"); // 表类型

				if (!"TABLE".equalsIgnoreCase(tableType))
					continue;

				TableDef tableDef = new TableDef();

				tableDef.setTableCatalog(rs.getString("TABLE_CAT"));
				tableDef.setTableDescription(rs.getString("REMARKS")); // 表描述
				tableDef.setTableName(rs.getString("TABLE_NAME"));
				tableDef.setTableSchema(rs.getString("TABLE_SCHEM"));
				tableDef.setTableType(tableType);

				List<FieldEntry> tempList = new ArrayList<FieldEntry>();

				//添加主键
				temp_rs = metaData.getPrimaryKeys(connection.getCatalog(), connection.getSchema(), tableDef.getTableName());
				while (temp_rs.next()) {
					tableDef.addPKColumnName(temp_rs.getString("COLUMN_NAME"));
				}
				temp_rs.close();
				temp_rs = metaData.getColumns(connection.getCatalog(),
						connection.getSchema(), tableDef.getTableName(), null);
				
				while (temp_rs.next()) {
					FieldEntry entry = new FieldEntry();

					entry.setFieldType(temp_rs.getInt("DATA_TYPE"));
					entry.setMappedFieldName(temp_rs.getString("COLUMN_NAME"));
					entry.setFieldLength(temp_rs.getInt("COLUMN_SIZE"));
					entry.setCharLength(temp_rs.getInt("CHAR_OCTET_LENGTH"));
					entry.setDecimalDigits(temp_rs.getInt("DECIMAL_DIGITS"));
					entry.setFieldDescription(temp_rs.getString("REMARKS"));
					entry.setRefTableName(temp_rs.getString("SCOPE_TABLE"));
					entry.setMappedTypeName(temp_rs.getString("TYPE_NAME"));
					
					tempList.add(entry);
				}
				temp_rs.close();
				metaMap.put(tableDef, tempList);
			}
		} catch (SQLException exception) {
			exception.printStackTrace();
		} finally {
			closeConnection(connection);
		}

		return metaMap;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public  Class mapFieldType(FieldEntry entry) {
		//System.err.println(entry.getMappedTypeName());
		
		Class cls = null;
		
		switch (entry.getFieldType()) {
		case Types.CHAR:
			if(entry.getCharLength()==1) cls = char.class;
			else cls = String.class;
			break;
		case Types.VARCHAR:
			cls = String.class;
			break;
		case Types.DOUBLE:
			cls = double.class;
			break;
		case Types.INTEGER:
			cls = int.class;
			break;
		case Types.BIGINT:
			cls = long.class;
			break;
		case Types.BIT:
			cls = boolean.class;
			break;
		case Types.DECIMAL:
			cls = double.class;
			break;
		case Types.NUMERIC:
			if(entry.getDecimalDigits()==0) cls = int.class;
			else cls = double.class;
			break;
		case Types.DATE:
		case Types.TIME:
		case Types.TIMESTAMP:
			cls = Date.class;
			break;
		
		
		}
		
		return cls;
	}

}
