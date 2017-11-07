package org.loushang.framework.mybatis.util;

import java.sql.SQLException;

import org.apache.ibatis.mapping.MappedStatement;

public class DBTypeUtil {
	public static String getDBTypeByDBId(MappedStatement ms) {
		try {
			String dataSourceUrl = ms.getConfiguration().getEnvironment().getDataSource().getConnection().getMetaData()
					.getURL();
			if (null != dataSourceUrl && !"".equals(dataSourceUrl)) {
				if (dataSourceUrl.toLowerCase().contains("db2")) {
					return "db2";
				} else if (dataSourceUrl.toLowerCase().contains("oracle")) {
					return "oracle";
				} else if (dataSourceUrl.toLowerCase().contains("mysql")) {
					return "mysql";
				} else if (dataSourceUrl.toLowerCase().contains("sqlserver")) {
					return "sqlserver";
				}
			} else {
				return "db2";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "db2";
	};
}
