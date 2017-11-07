package org.loushang.framework.mybatis.mapper;

import org.loushang.framework.mybatis.mapper.api.BatchDeleteMapper;
import org.loushang.framework.mybatis.mapper.api.BatchInsertMapper;
import org.loushang.framework.mybatis.mapper.api.DeleteByPrimaryKeyMapper;
import org.loushang.framework.mybatis.mapper.api.DeleteMapper;
import org.loushang.framework.mybatis.mapper.api.ExistsWithPrimaryKeyMapper;
import org.loushang.framework.mybatis.mapper.api.GetAllMapper;
import org.loushang.framework.mybatis.mapper.api.GetByPrimaryKeyMapper;
import org.loushang.framework.mybatis.mapper.api.GetMapper;
import org.loushang.framework.mybatis.mapper.api.GetTotalCountMapper;
import org.loushang.framework.mybatis.mapper.api.InsertMapper;
import org.loushang.framework.mybatis.mapper.api.QueryMapper;
import org.loushang.framework.mybatis.mapper.api.UpdateByPrimaryKeyMapper;

public interface EntityMapper<T>
        extends
            DeleteMapper<T>,
            ExistsWithPrimaryKeyMapper<T>,
            GetMapper<T>,
            GetAllMapper<T>,
            GetByPrimaryKeyMapper<T>,
            QueryMapper<T>,
            GetTotalCountMapper<T>,
            InsertMapper<T>,
            UpdateByPrimaryKeyMapper<T>,
            BatchInsertMapper<T>,
            BatchDeleteMapper<T>,
            DeleteByPrimaryKeyMapper<T> //,BatchUpdateMapper<T>, BatchSaveMapper<T>
            {

}
