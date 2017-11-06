package org.loushang.ldf.mybatis.mapper.provider;

import org.apache.ibatis.mapping.MappedStatement;
import org.loushang.ldf.mybatis.util.DBTypeUtil;

import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.entity.EntityTable;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

/**
 * BaseGetProvider实现类，基础方法实现类
 *
 * @author 框架产品组
 */
public class EntityGetProvider extends MapperTemplate {

    public EntityGetProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    /**
     * 查询
     *
     * @param ms
     * @return
     */
    public String get(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        //修改返回值类型为实体类型
        setResultType(ms, entityClass);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.selectAllColumns(entityClass));
        sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
        sql.append(SqlHelper.whereAllIfColumns(entityClass, isNotEmpty()));
        sql.append(SqlHelper.orderByDefault(entityClass));
        return sql.toString();
    }


    /**
     * 根据主键进行查询
     *
     * @param ms
     */
    public String getByPrimaryKey(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        //将返回值修改为实体类型
        setResultType(ms, entityClass);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.selectAllColumns(entityClass));
        sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
        sql.append(SqlHelper.wherePKColumns(entityClass));
        return sql.toString();
    }

    /**
     * 查询总数
     *
     * @param ms
     * @return
     */
    public String getTotalCount(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.selectCount(entityClass));
        sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
        sql.append(SqlHelper.whereAllIfColumns(entityClass, isNotEmpty()));
        return sql.toString();
    }

    /**
     * 根据主键查询总数
     *
     * @param ms
     * @return
     */
    public String existsWithPrimaryKey(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.selectCountExists(entityClass));
        sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
        sql.append(SqlHelper.wherePKColumns(entityClass));
        return sql.toString();
    }

    /**
     * 查询全部结果
     *
     * @param ms
     * @return
     */
    public String getAll(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        //修改返回值类型为实体类型
        setResultType(ms, entityClass);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.selectAllColumns(entityClass));
        sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
        sql.append(SqlHelper.orderByDefault(entityClass));
        return sql.toString();
    }
    
    /**
	 * 查询全部结果
	 *
	 * @param ms
	 * @return
	 */
	public String query(MappedStatement ms) {
		final Class<?> entityClass = getEntityClass(ms);
		// 修改返回值类型为实体类型
        setResultType(ms, entityClass);
		//获取表
        EntityTable table = EntityHelper
                .getEntityTable(entityClass);
        String DBType=DBTypeUtil.getDBTypeByDBId( ms);
		// 开始拼sql
		StringBuilder sql = new StringBuilder();
		sql.append(SqlHelper.selectAllColumns(entityClass));
        sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
		//Where 条件
		sql.append(" <where> ");
		for (EntityColumn column : table.getEntityClassColumns()) {
	           sql.append("<if test=\"").append(column.getProperty()).append(" != null\">");
	           if("sqlserver".equals(DBType)){
	        	   sql.append(" and ").append(column.getColumn()).append(" like ").append("'%#{").append(column.getProperty()).append("}%'");
	           }else if("oracle".equals(DBType)){
	        	   sql.append(" and ").append(column.getColumn()).append(" like ").append("CONCAT(").append("CONCAT('%',#{").append(column.getProperty()).append("}),'%')");
	           }else{
	        	   sql.append(" and ").append(column.getColumn()).append(" like ").append("CONCAT('%',#{").append(column.getProperty()).append("},'%')");
	           }
               sql.append("</if>");
       }
	   sql.append(" </where> ");	
	   sql.append(SqlHelper.orderByDefault(entityClass));
       return sql.toString();
	}
}
