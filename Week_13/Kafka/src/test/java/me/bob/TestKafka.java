package me.bob;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestKafka {

    @Autowired
    private KafkaSender kafkaSender;

    @Autowired
    private KafkaReceiver kafkaReceiver;

    @Test
    void testKafka() {
        kafkaSender.send();
    }
}
