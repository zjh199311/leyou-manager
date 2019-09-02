package com.leyou.search.listener;

import com.leyou.search.service.SearchService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author river
 * @title: ItemListener
 * @projectName leyou
 * @description: TODO
 * @date 2019/8/2016:33
 */
@Component
public class ItemListener {
    private SearchService searchService;
    @RabbitListener(bindings = @QueueBinding(
               value = @Queue(name = "search.item.insert.quenue",durable = "true"),
               exchange =@Exchange(name = "leyou.item.exchange",type = ExchangeTypes.TOPIC),
               //指定绑定关系
               key = {"item.insert","item.update","item.save"}
    ))
    public  void  listenerInsertOrUpdate(Long spuId){
         if (spuId==null){
              return;
         }
         //处理消息，对索引库进行新增或修改
         searchService.createOrUpdateIndex(spuId);
    }


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "search.item.delete.quenue",durable = "true"),
            exchange =@Exchange(name = "leyou.item.exchange",type = ExchangeTypes.TOPIC),
            //指定绑定关系
            key = {"item.delete"}
    ))
    public  void  listenerDelete(Long spuId){
        if (spuId==null){
            return;
        }
        //处理消息，对索引库进行新增或修改
        searchService.deleteIndex(spuId);
    }
}
