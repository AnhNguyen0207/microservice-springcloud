package jmaster.io.accountservice.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Producer {
    private static final String TOPIC = "my-topic";


    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessage(Object content, String key) {
        kafkaTemplate.send(TOPIC, key, content);
    }
}
