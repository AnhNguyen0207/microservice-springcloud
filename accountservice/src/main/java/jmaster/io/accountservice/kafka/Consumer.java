package jmaster.io.accountservice.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class Consumer {
    @KafkaListener(topics = "my-topic-1", groupId = "my-group", containerFactory = "kafkaListenerContainerFactory")
    public void consumer(User user) {
        log.info("Received User: {}", user);
    }
}
