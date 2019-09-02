package com.leyou.item.service;

import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;

import java.util.List;

/**
 * @author river
 * @title: SpecificationService
 * @projectName leyou
 * @description: TODO
 * @date 2019/8/715:35
 */
public interface SpecificationService {
    List<SpecGroup> queryGroupCid(Long cid);

    List<SpecParam> queryParamList(Long gid, Long cid,Long id,Boolean searching);

    List<SpecGroup> querySpecsByCid( Long cid);
}
