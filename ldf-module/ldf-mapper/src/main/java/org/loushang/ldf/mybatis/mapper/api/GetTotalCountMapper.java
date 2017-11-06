package org.loushang.ldf.mybatis.mapper.api;

import org.apache.ibatis.annotations.SelectProvider;
import org.loushang.ldf.mybatis.mapper.provider.EntityGetProvider;

/**
 * 通用Mapper接口,查询
 *
 * @param <T> 不能为空
 * @author 框架产品组
 */
public interface GetTotalCountMapper<T> {

    /**
     * 根据实体中的属性查询总数，查询条件使用等号
     *
     * @param record
     * @return
     */
    @SelectProvider(type = EntityGetProvider.class, method = "dynamicSQL")
    int getTotalCount(T record);

}