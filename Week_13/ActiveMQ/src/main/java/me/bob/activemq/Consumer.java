package me.bob.activemq;

import org.springframework.jms.annotation.JmsListener;

public class Consumer {

    //queue模式的消费者
    @JmsListener(destination = "test-queue", containerFactory = "jmsListenerContainerQueue")
    public void readActiveQueue(String message) {
        System.out.println("queue接受到：" + message);
    }

    //topic模式的消费者
    @JmsListener(destination = "test-topic", containerFactory = "jmsListenerContainerTopic")
    public void readActiveTopic(String message) {
        System.out.println("topic接受到：" + message);
    }
}
