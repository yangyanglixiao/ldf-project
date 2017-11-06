package org.loushang.ldf.mybatis.mapper.api;

import org.apache.ibatis.annotations.DeleteProvider;
import org.loushang.ldf.mybatis.mapper.provider.EntityDeleteProvider;

/**
 * 通用Mapper接口,删除
 *
 * @param <T> 不能为空
 * @author 框架产品组
 */
public interface DeleteByPrimaryKeyMapper<T> {

    /**
     * 根据主键字段进行删除，方法参数必须包含完整的主键属性
     *
     * @param key
     * @return
     */
    @DeleteProvider(type = EntityDeleteProvider.class, method = "dynamicSQL")
    int deleteByPrimaryKey(Object key);

}