package me.bob;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class KafkaReceiver {

    @KafkaListener(topics = "goods")
    public void receive(ConsumerRecord<?, ?> record) {
        Optional<?> message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            Object msg = message.get();
            System.out.println("msg = " + msg);
        }

    }
}
