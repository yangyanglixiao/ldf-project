package org.loushang.framework.mybatis.mapper.api;

import org.apache.ibatis.annotations.SelectProvider;
import org.loushang.framework.mybatis.mapper.provider.EntityGetProvider;

/**
 * 通用Mapper接口,根据实体主键查询实体
 * 
 * @param <T> 不能为空
 * @author 框架产品组
 */
public interface GetByPrimaryKeyMapper<T> {

    /**
     * 根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号
     *
     * @param key
     * @return
     */
    @SelectProvider(type = EntityGetProvider.class, method = "dynamicSQL")
    T getByPrimaryKey(Object key);

}