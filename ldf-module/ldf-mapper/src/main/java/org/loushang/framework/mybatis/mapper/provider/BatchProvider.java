package org.loushang.framework.mybatis.mapper.provider;

import org.apache.ibatis.mapping.MappedStatement;
import org.loushang.framework.mybatis.util.DBTypeUtil;

import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.entity.EntityTable;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;

/**
 * BatchProvider实现类，批量方法实现类
 *
 * @author 框架产品组
 */
public class BatchProvider extends MapperTemplate {

	public BatchProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
		super(mapperClass, mapperHelper);
	}

	/**
	 * 批量插入
	 *
	 * @param ms
	 */
	public String batchInsert(MappedStatement ms) {
		final Class<?> entityClass = getEntityClass(ms);
		EntityTable table = EntityHelper.getEntityTable(entityClass);
		// 开始拼sql
		StringBuilder sql = new StringBuilder();
		sql.append("insert into ");
		sql.append(table.getName());
		sql.append("(");
		boolean first = true;
		for (EntityColumn column : table.getEntityClassColumns()) {
			if (column.isId() && column.isUuid()) {
				continue;
			}
			if (!first) {
				sql.append(",");
			}
			sql.append(column.getColumn());
			first = false;
		}
		sql.append(") ");
		 String DBType=DBTypeUtil.getDBTypeByDBId( ms);
		// oracle特殊处理
		if (DBType != null && "oracle".equals(DBType)) {
			return dealInsertOracle(table, sql);
		}
		sql.append("values ");
		sql.append("<foreach collection=\"list\" item=\"item\" separator=\",\" >");
		sql.append("(");
		first = true;
		for (EntityColumn column : table.getEntityClassColumns()) {
			if (column.isId() && column.isUuid()) {
				continue;
			}
			if (!first) {
				sql.append(",");
			}
			sql.append("#{item.").append(column.getProperty()).append("}");
			first = false;
		}
		sql.append(")");
		sql.append("</foreach>");
		return sql.toString();
	}

	/**
	 * oracle数据库
	 * 
	 * @param table
	 * @param sql
	 * @return
	 */
	private String dealInsertOracle(EntityTable table, StringBuilder sql) {
		sql.append("<foreach collection=\"list\" item=\"item\" separator=\"union all\" >");
		sql.append("(select ");
		boolean first = true;
		for (EntityColumn column : table.getEntityClassColumns()) {
			if (column.isId() && column.isUuid()) {
				continue;
			}
			if (!first) {
				sql.append(",");
			} else {

			}
			sql.append("#{item.").append(column.getProperty()).append("}");
			first = false;
		}
		sql.append(" from dual)");
		sql.append("</foreach>");
		return sql.toString();
	}

	/**
	 * 批量更新
	 *
	 * @param ms
	 */
	/*public String batchUpdate(MappedStatement ms) {
		final Class<?> entityClass = getEntityClass(ms);
		EntityTable table = EntityHelper.getEntityTable(entityClass);
		// 开始拼sql
		StringBuilder sql = new StringBuilder();
		String DBType=DBTypeUtil.getDBTypeByDBId( ms);
		// oracle特殊处理
		if ( DBType != null	&& "oracle".equals( DBType.toLowerCase())) {
			sql.append("<foreach collection=\"list\" item=\"item\" open=\"begin\" close=\"end;\" >");
		} else {
			sql.append("<foreach collection=\"list\" item=\"item\" separator=\";\" >");
		}
		sql.append("update ");
		sql.append(table.getName());
		sql.append(" set ");

		boolean first = true;

		EntityColumn pkColumn = null;
		for (EntityColumn column : table.getEntityClassColumns()) {
			if (!first && !column.isId()) {
				sql.append(",");
			}
			if (column.isId()) {
				pkColumn = column;
				continue;
			}
			sql.append(column.getColumn());
			sql.append("=");
			sql.append("#{item.").append(column.getProperty()).append("}");
			first = false;
		}
		sql.append(" where ");
		sql.append(pkColumn.getColumn());
		sql.append("=");
		sql.append("#{item.").append(pkColumn.getProperty()).append("}");
		// oracle特殊处理
		if ( DBType != null	&& "oracle".equals( DBType)) {
			sql.append(";");
		}
		sql.append("</foreach>");
		return sql.toString();
	}*/

	/**
	 * 批量删除
	 *
	 * @param ms
	 */
	public String batchDelete(MappedStatement ms) {
		final Class<?> entityClass = getEntityClass(ms);
		EntityTable table = EntityHelper.getEntityTable(entityClass);
		// 获取主键列名
		EntityColumn pkColumn = null;
		for (EntityColumn column : table.getEntityClassColumns()) {
			if (column.isId()) {
				pkColumn = column;
				break;
			}
		}
		// 开始拼sql
		StringBuilder sql = new StringBuilder();
		sql.append("delete from ");
		sql.append(table.getName());
		sql.append(" where ");
		sql.append(pkColumn.getColumn());
		sql.append(" in ");
		sql.append(
				"<foreach collection=\"array\" index=\"index\" item=\"item\" open=\"(\" close=\")\" separator=\",\" >");
		sql.append("#{item}");
		sql.append("</foreach>");
		return sql.toString();
	}
}
