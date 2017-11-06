package org.loushang.ldf.mybatis.mapper.api;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.SelectProvider;
import org.loushang.ldf.mybatis.mapper.provider.EntityGetProvider;

/**
 * 通用Mapper接口,其他接口继承该接口即可
 * 查询全部结果，支持分页
 *
 * @param <T> 不能为空
 * @author 框架产品组
 */
public interface QueryMapper<T> {

    /**
     * 查询全部结果
     * @param map key分别为：start[起始位置] limit[显示记录条数]
     * @return
     */
    @SelectProvider(type = EntityGetProvider.class, method = "dynamicSQL")
    List<T> query(Map map);
}
