package com.debug.steadyjack.listener;

import com.debug.steadyjack.entity.OrderTradeRecord;
import com.debug.steadyjack.mapper.OrderTradeRecordMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;


@Component
public class RabbitMQListener {

    private final static Logger log= LoggerFactory.getLogger(RabbitMQListener.class);

    @Autowired
    private OrderTradeRecordMapper orderTradeRecordMapper;

    /*@RabbitListener(queues = "${register.queue.name}",containerFactory = "singleListenerContainer")
    public void test(@Payload User user){
        try {
            log.debug("消费者监听消费到消息： {} ",user);
        }catch (Exception e){
            log.error("消息体解析 发生异常； ",e.fillInStackTrace());
        }
    }*/

    //直接消费模式
    @RabbitListener(queues = "${register.queue.name}",containerFactory = "singleListenerContainer")
    public void consumeMessage(@Payload OrderTradeRecord record){
        try {
            log.info("消费者监听交易记录信息： {} ",record);

            //TODO：表示已经到ttl了，却还没付款，则需要处理为失效
            if (Objects.equals(1,record.getStatus())){
                record.setStatus(0);
                record.setUpdateTime(new Date());
                orderTradeRecordMapper.updateByPrimaryKeySelective(record);
            }
        }catch (Exception e){
            log.error("消息体解析 发生异常； ",e.fillInStackTrace());
        }
    }

}