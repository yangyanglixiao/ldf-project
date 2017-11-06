package org.loushang.ldf.mybatis.mapper.api;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.UpdateProvider;
import org.loushang.ldf.mybatis.mapper.provider.EntityUpdateProvider;

/**
 * 通用Mapper接口,更新
 *
 * @param <T> 不能为空
 * @author 框架产品组
 */
public interface UpdateByPrimaryKeyMapper<T> {

    /**
     * 根据主键更新属性不为null的值
     *
     * @param record
     * @return
     */
    @UpdateProvider(type = EntityUpdateProvider.class, method = "dynamicSQL")
    @Options(useCache = false, useGeneratedKeys = false)
    int update(T record);

}