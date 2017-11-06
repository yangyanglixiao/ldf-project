package org.loushang.ldf.mybatis.mapper.api;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.loushang.ldf.mybatis.mapper.provider.BatchProvider;

/**
 * 通用Mapper接口,批量插入
 *
 * @param <T>
 *            不能为空
 * @author 框架产品组
 */
public interface BatchInsertMapper<T> {

	/**
	 * 批量插入，支持批量插入的数据库可以使用
	 *
	 * @param recordList
	 * @return
	 */
	@Options(useGeneratedKeys = false)
	@InsertProvider(type = BatchProvider.class, method = "dynamicSQL")
	int batchInsert(List<T> recordList);
}
