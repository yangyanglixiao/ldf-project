package org.loushang.ldf.mybatis.mapper;

import org.loushang.ldf.mybatis.mapper.api.BatchDeleteMapper;
import org.loushang.ldf.mybatis.mapper.api.BatchInsertMapper;
import org.loushang.ldf.mybatis.mapper.api.DeleteByPrimaryKeyMapper;
import org.loushang.ldf.mybatis.mapper.api.DeleteMapper;
import org.loushang.ldf.mybatis.mapper.api.ExistsWithPrimaryKeyMapper;
import org.loushang.ldf.mybatis.mapper.api.GetAllMapper;
import org.loushang.ldf.mybatis.mapper.api.GetByPrimaryKeyMapper;
import org.loushang.ldf.mybatis.mapper.api.GetMapper;
import org.loushang.ldf.mybatis.mapper.api.GetTotalCountMapper;
import org.loushang.ldf.mybatis.mapper.api.InsertMapper;
import org.loushang.ldf.mybatis.mapper.api.QueryMapper;
import org.loushang.ldf.mybatis.mapper.api.UpdateByPrimaryKeyMapper;

public interface EntityMapper<T>
		extends DeleteMapper<T>, ExistsWithPrimaryKeyMapper<T>, GetMapper<T>, GetAllMapper<T>, GetByPrimaryKeyMapper<T>,
		QueryMapper<T>, GetTotalCountMapper<T>, InsertMapper<T>, UpdateByPrimaryKeyMapper<T>, BatchInsertMapper<T>,
		BatchDeleteMapper<T>, DeleteByPrimaryKeyMapper<T>/*
															 * , BatchUpdateMapper<T>, BatchSaveMapper<T>
															 */ {

}
