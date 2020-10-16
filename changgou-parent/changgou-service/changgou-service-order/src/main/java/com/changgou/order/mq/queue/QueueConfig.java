package com.changgou.order.mq.queue;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Dylan Guo
 * @date 10/16/2020 15:24
 * @description 延时队列配置
 */
@Configuration
public class QueueConfig {

    /**
     * 创建 Queue 1，延时队列，会过期，过期后将数据发送给 Queue 2
     *
     * @return
     */
    @Bean
    public Queue orderDelayQueue() {
        return QueueBuilder
                .durable("orderDelayQueue")
                .withArgument("x-dead-letter-exchange", "orderListenerExchange")
                .withArgument("x-dead-letter-routing-key", "orderListenerQueue")
                .build();
    }

    /**
     * 创建 Queue 2
     *
     * @return
     */
    @Bean
    public Queue orderListenerQueue() {
        return new Queue("orderListenerQueue", true);
    }

    /**
     * 创建交换机
     *
     * @return
     */
    @Bean
    public Exchange orderListenerExchange() {
        return new DirectExchange("orderListenerExchange");
    }

    /**
     * Queue 2 绑定 Exchange
     *
     * @param orderListenerQueue
     * @param orderListenerExchange
     * @return
     */
    @Bean
    public Binding orderListenerBinding(Queue orderListenerQueue, Exchange orderListenerExchange) {
        return BindingBuilder.bind(orderListenerQueue).to(orderListenerExchange).with("orderListenerQueue").noargs();
    }
}
