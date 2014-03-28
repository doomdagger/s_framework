package com.hg.ecommerce.model.support.provider;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
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
//			List<String> tableTypes = new ArrayList<String>();
//			rs = metaData.getTableTypes();
//			while (rs.next()) {
//				tableTypes.add(rs.getString(1));
//			}
//			System.err.println(tableTypes.toString());
			//test end...
			
			
			//获取table列表
			rs = metaData.getTables(connection.getCatalog(), connection.getSchema(), null, null);
			while (rs.next()) {
				String tableType = rs.getString("TABLE_TYPE"); // 表类型

				if (!"TABLE".equalsIgnoreCase(tableType))
					continue;

				TableDef tableDef = new TableDef();

				tableDef.setIdFieldName(rs.getString("SELF_REFERENCING_COL_NAME")); //主键
				tableDef.setTableCatalog(rs.getString("TABLE_CAT"));
				tableDef.setTableDescription(rs.getString("REMARKS")); // 表描述
				tableDef.setTableName(rs.getString("TABLE_NAME"));
				tableDef.setTableSchema(rs.getString("TABLE_SCHEM"));
				tableDef.setTableType(tableType);

				List<FieldEntry> tempList = new ArrayList<FieldEntry>();

				temp_rs = metaData.getColumns(connection.getCatalog(), connection.getSchema(), tableDef.getTableName(), null);

				while (temp_rs.next()) {
					FieldEntry entry = new FieldEntry();

					entry.setFieldType(temp_rs.getInt("DATA_TYPE"));
					entry.setFieldName(temp_rs.getString("COLUMN_NAME"));
					entry.setFieldLength(temp_rs.getInt("COLUMN_SIZE"));
					entry.setCharLength(temp_rs.getInt("CHAR_OCTET_LENGTH"));
					entry.setDecimalDigits(temp_rs.getInt("DECIMAL_DIGITS"));
					entry.setFieldDescription(temp_rs.getString("REMARKS"));
					entry.setRefTableName(temp_rs.getString("SCOPE_TABLE"));
					entry.setTypeName(temp_rs.getString("TYPE_NAME"));
					
					tempList.add(entry);
				}

				metaMap.put(tableDef, tempList);
			}
		} catch (SQLException exception) {
			exception.printStackTrace();
		} finally {
			closeConnection(connection);
		}

		return metaMap;
	}

}
