package me.bob.activemq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;

import javax.jms.Queue;
import javax.jms.Topic;

public class Producer {

    //注入点对点的模式（Queue模式）
    @Autowired
    private Queue queue;

    //注入订阅模式（Topic）的消息
    @Autowired
    private Topic topic;

    //注入springboot封装的工具类
    @Autowired
    private JmsMessagingTemplate jms;

    /**
     * 点对点模式（queue）模式发消息
     *
     * @param text
     */
    public String queueSend(String text) {
        //发送消息至消息中间件代理（Broker）
        jms.convertAndSend(queue, text);
        return "success";
    }

    /**
     * 订阅模式（topic）发送消息
     *
     * @param text
     * @return
     */
    public String topicSend(String text) {
        jms.convertAndSend(topic, text);
        return "topic 发送成功";
    }

}
