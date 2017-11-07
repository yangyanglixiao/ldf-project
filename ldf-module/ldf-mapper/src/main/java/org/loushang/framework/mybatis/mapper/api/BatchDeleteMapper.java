package org.loushang.framework.mybatis.mapper.api;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Options;
import org.loushang.framework.mybatis.mapper.provider.BatchProvider;

/**
 * 通用Mapper接口,批量删除
 *
 * @param <T>
 *            不能为空
 * @author 框架产品组
 */
public interface BatchDeleteMapper<T> {
	/**
	 * 批量删除
	 *
	 * @param values
	 * @return
	 */
	@Options(useGeneratedKeys = false)
	@DeleteProvider(type = BatchProvider.class, method = "dynamicSQL")
	int batchDelete(Object[] values);
}
