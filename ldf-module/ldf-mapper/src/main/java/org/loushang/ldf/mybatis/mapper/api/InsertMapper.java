package org.loushang.ldf.mybatis.mapper.api;

import org.apache.ibatis.annotations.InsertProvider;
import org.loushang.ldf.mybatis.mapper.provider.EntityInsertProvider;

/**
 * 通用Mapper接口,插入
 * 
 * @param <T> 不能为空
 * @author 框架产品组
 */
public interface InsertMapper<T> {

	/**
     * 保存一个实体，null的属性不会保存，会使用数据库默认值
     *
     * @param record
     * @return
     */
    @InsertProvider(type = EntityInsertProvider.class, method = "dynamicSQL")
    int insert(T record);
}
