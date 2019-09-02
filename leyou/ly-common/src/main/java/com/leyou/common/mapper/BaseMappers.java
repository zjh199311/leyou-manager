package com.leyou.common.mapper;

import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.Mapper;


/**
 * @author river
 * @title: BaseMapper
 * @projectName leyou
 * @description: TODO
 * @date 2019/8/817:26
 */
@RegisterMapper
public interface BaseMappers<T,PK> extends Mapper<T>,IdListMapper<T,PK> ,InsertListMapper<T>,DeleteByIdListMapper<T,PK>,SelectByIdListMapper<T,PK>{
}
