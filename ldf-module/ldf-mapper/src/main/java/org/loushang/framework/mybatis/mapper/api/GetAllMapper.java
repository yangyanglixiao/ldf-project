package org.loushang.framework.mybatis.mapper.api;

import java.util.List;

import org.apache.ibatis.annotations.SelectProvider;
import org.loushang.framework.mybatis.mapper.provider.EntityGetProvider;

/**
 * 通用Mapper接口,其他接口继承该接口即可
 * 查询全部结果
 *
 * @param <T> 不能为空
 * @author 框架产品组
 */
public interface GetAllMapper<T> {

    /**
     * 查询全部结果
     *
     * @return
     */
    @SelectProvider(type = EntityGetProvider.class, method = "dynamicSQL")
    List<T> getAll();
}
