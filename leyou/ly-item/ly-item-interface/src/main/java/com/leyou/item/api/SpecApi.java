package com.leyou.item.api;

import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author river
 * @title: SpecApi
 * @projectName leyou
 * @description: TODO
 * @date 2019/8/1215:35
 */
@RequestMapping("spec")
public interface SpecApi {

    @GetMapping("params")
    List<SpecParam> querySpecParams(
            @RequestParam(value = "gid", required = false) Long gid,
            @RequestParam(value = "cid", required = false) Long cid,
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam(value = "searching", required = false) Boolean searching
    );

    @GetMapping("groups/{cid}")
    List<SpecGroup> querySpecGroupByCid(@PathVariable("cid") Long cid);


    // 查询规格参数组，及组内参数
    @GetMapping("/{cid}")
    List<SpecGroup> querySpecsByCid(@PathVariable("cid") Long cid);

}
