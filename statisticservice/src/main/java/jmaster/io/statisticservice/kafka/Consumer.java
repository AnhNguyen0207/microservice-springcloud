package jmaster.io.statisticservice.kafka;

import jmaster.io.statisticservice.model.StatisticDTO;
import jmaster.io.statisticservice.service.StatisticService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class Consumer {

    private final StatisticService statisticService;
    private final Producer producer;

    @KafkaListener(topics = "my-topic", groupId = "my-group", containerFactory = "kafkaListenerContainerFactory")
    public void consumer(User user) {
        log.info("Received User: {}", user);
        StatisticDTO statisticDTO = new StatisticDTO(1l , user.getKey(), user.getCreatedDate());
        statisticService.add(statisticDTO);
        producer.sendMessage(user, "reply");
    }
}
