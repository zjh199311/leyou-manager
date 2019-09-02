package com.leyou.search.repository;

import com.leyou.search.pojo.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author river
 * @title: GoodsRepository
 * @projectName leyou
 * @description: TODO
 * @date 2019/8/1215:50
 */
public interface GoodsRepository extends ElasticsearchRepository<Goods,Long>{

}
