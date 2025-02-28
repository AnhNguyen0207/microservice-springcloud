package jmaster.io.statisticservice.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Producer {
    private static final String TOPIC = "my-topic-1";


    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessage(Object object, String key) {
        kafkaTemplate.send(TOPIC, key, object);
    }
}
