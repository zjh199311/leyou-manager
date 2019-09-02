package com.leyou.item.controller;

import com.leyou.item.mapper.SpecParamMapper;
import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import com.leyou.item.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author river
 * @title: SpecificationController
 * @projectName leyou
 * @description: 根据分类id查询规格组
 * @date 2019/8/715:36
 */
@RestController
@RequestMapping("/spec")
public class SpecificationController {
    @Autowired
    private SpecificationService specificationService;

    @GetMapping("/group/{cid}")
    public ResponseEntity<List<SpecGroup>> queryGroupByCid(@PathVariable("cid") Long cid) {
        return ResponseEntity.ok(specificationService.queryGroupCid(cid));
    }


    /**
     * 　　* @description: 查询参数集合
     * 　　* @param gid 组id  cid 分类id  searching 是否搜索
     * 　　* @return ${return_type}
     * 　　* @throws
     * 　　* @author river
     * 　　* @date 2019/8/7 16:22
     *
     */
    @GetMapping("/params")
    public ResponseEntity<List<SpecParam>> queryParamByGid(
            @RequestParam(value = "gid", required = false) Long gid,
            @RequestParam(value = "cid",required = false)Long cid,
            @RequestParam(value = "id",required = false)Long id,
            @RequestParam(value = "searching",required = false) Boolean searching
    ) {
        return ResponseEntity.ok(specificationService.queryParamList(gid,cid,id,searching));
    }

    @GetMapping("{cid}")
    public ResponseEntity<List<SpecGroup>> querySpecsByCid(@PathVariable("cid") Long cid){
        List<SpecGroup> list = this.specificationService.querySpecsByCid(cid);
        if(list == null || list.size() == 0){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(list);
    }
}
