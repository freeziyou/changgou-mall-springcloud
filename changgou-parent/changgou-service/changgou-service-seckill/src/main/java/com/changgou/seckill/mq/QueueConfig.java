package com.changgou.seckill.mq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Dylan Guo
 * @date 10/18/2020 16:48
 * @description 延时超时队列
 */
@Configuration
public class QueueConfig {

    /**
     * 延时队列，队列1
     *
     * @return
     */
    @Bean
    public Queue delaySeckillQueue() {
        return QueueBuilder.durable("delaySeckillQueue")
                // 当前队列的消息一旦过期，则进入死信交换机
                .withArgument("x-dead-letter-exchange", "seckillExchange")
                // 将死信队列的数据路由到队列2
                .withArgument("x-dead-letter-routing-key", "seckillQueue")
                .build();
    }

    /**
     * 真正监听队列，队列2
     *
     * @return
     */
    @Bean
    public Queue seckillQueue() {
        return new Queue("seckillQueue");
    }

    /**
     * 秒杀交换机
     *
     * @return
     */
    @Bean
    public Exchange seckillExchange() {
        return new DirectExchange("seckillExchange");
    }

    /**
     * 队列绑定交换机
     *
     * @param seckillQueue
     * @param seckillExchange
     * @return
     */
    @Bean
    public Binding seckillQueueBindingExchange(Queue seckillQueue, Exchange seckillExchange) {
        return BindingBuilder.bind(seckillQueue).to(seckillExchange).with("seckillQueue").noargs();
    }
}
