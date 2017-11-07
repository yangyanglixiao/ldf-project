package org.loushang.framework.mybatis.mapper.api;

import java.util.List;

import org.apache.ibatis.annotations.SelectProvider;
import org.loushang.framework.mybatis.mapper.provider.EntityGetProvider;

/**
 * 通用Mapper接口,查询
 *
 * @param <T> 不能为空
 * @author 框架产品组
 */
public interface GetMapper<T> {

    /**
     * 根据实体中的属性值进行查询，查询条件使用等号
     *
     * @param record
     * @return
     */
    @SelectProvider(type = EntityGetProvider.class, method = "dynamicSQL")
    List<T> get(T record);

}