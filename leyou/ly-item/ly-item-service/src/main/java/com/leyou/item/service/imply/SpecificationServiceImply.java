package com.leyou.item.service.imply;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.item.mapper.SpecGroupMapper;
import com.leyou.item.mapper.SpecParamMapper;
import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import com.leyou.item.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author river
 * @title: SpecificationServiceImply
 * @projectName leyou
 * @description: TODO
 * @date 2019/8/715:36
 */
@Service
public class SpecificationServiceImply implements SpecificationService {
    @Autowired
    private SpecGroupMapper specGroupMapper;
    @Autowired
    private SpecParamMapper specParamMapper;

    @Override
    public List<SpecGroup> queryGroupCid(Long cid) {
        SpecGroup specGroup = new SpecGroup();
        specGroup.setCid(cid);
        List<SpecGroup> specGroups = specGroupMapper.select(specGroup);
        if (CollectionUtils.isEmpty(specGroups)){
            throw new LyException(ExceptionEnum.SPEC_GROUP_NOT_FOUND);
        }
        return  specGroups;
    }

   @Override
    public List<SpecParam> queryParamList(Long gid, Long cid,Long id,Boolean searching) {
        SpecParam specParam = new SpecParam();
        specParam.setId(id);
        specParam.setGroupId(gid);
        specParam.setCid(cid);
        specParam.setSearching(searching);
        //select根据非空字段进行查询
        List<SpecParam> specParams = specParamMapper.select(specParam);
        if (CollectionUtils.isEmpty(specParams)){
            throw  new LyException(ExceptionEnum.SPEC_PARAM_NOT_FOUND);
        }
        return specParams;
    }

    @Override
    public List<SpecGroup> querySpecsByCid(Long cid) {
        //查询规格组
        List<SpecGroup> specGroups = queryGroupCid(cid);
        //查询当前分类下的规格参数
        List<SpecParam> specParams = queryParamList(null, cid, null, null);
        //先把规格参数变成map，map的key是规格组的id，map的值是组下的所有参数
        Map<Long, List<SpecParam>> map = new HashMap<>();
        for (SpecParam specParam : specParams) {
             if(!map.containsKey(specParam.getGroupId())){
                //这个组id在map中不存在，新增一个list
                map.put(specParam.getGroupId(),new ArrayList<>());
            }
            map.get(specParam.getGroupId()).add(specParam);
        }
        //填充specparam到group
        for (SpecGroup specGroup : specGroups) {
              specGroup.setParams(map.get(specGroup.getId()));
        }
        return  specGroups;
    }
}
