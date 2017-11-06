package org.loushang.ldf.mybatis.mapper.api;

import org.apache.ibatis.annotations.SelectProvider;
import org.loushang.ldf.mybatis.mapper.provider.EntityGetProvider;

/**
 * 通用Mapper接口,查询
 *
 * @param <T> 不能为空
 * @author 框架产品组
 */
public interface ExistsWithPrimaryKeyMapper<T> {

    /**
     * 根据主键字段查询是否存在，方法参数必须包含完整的主键属性，查询条件使用等号
     *
     * @param key
     * @return
     */
    @SelectProvider(type = EntityGetProvider.class, method = "dynamicSQL")
    boolean existsWithPrimaryKey(Object key);

}