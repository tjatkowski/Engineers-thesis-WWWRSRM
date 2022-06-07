package pl.edu.agh.wwwrsrm.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import static pl.edu.agh.wwwrsrm.consumer.config.TopicConfiguration.*;

@Slf4j
@Component
public class Consumer {

    @KafkaListener(topics = CARS_TOPIC, groupId = CARS_TOPIC)
    void carsListener(ConsumerRecord<String, String> data) {
        log.info("Received car[{}]:{}",data.offset(), data.value());
    }

    @KafkaListener(topics = JUNCTIONS_TOPIC, groupId = JUNCTIONS_TOPIC)
    void junctionsListener(ConsumerRecord<String, String> data) {
        log.info("Received junction[{}]:{}",data.offset(), data.value());
    }

    @KafkaListener(topics = LANES_TOPIC, groupId = LANES_TOPIC)
    void lanesListener(ConsumerRecord<String, String> data) {
        log.info("Received lane[{}]:{} ", data.offset(), data.value());
    }
}
