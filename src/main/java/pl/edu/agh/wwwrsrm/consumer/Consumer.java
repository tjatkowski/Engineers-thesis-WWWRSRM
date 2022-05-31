package pl.edu.agh.wwwrsrm.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
//@Component
public class Consumer {

    @KafkaListener(topics = "${topic.cars.name}", groupId = "${topic.cars.group}")
    void carsListener(ConsumerRecord<String, String> data) {
        log.info("Received car:" + data.value());
    }

    @KafkaListener(topics = "${topic.junctions.name}", groupId = "${topic.junctions.group}")
    void junctionsListener(ConsumerRecord<String, String> data) {
        log.info("Received junction: " + data.value());
    }

    @KafkaListener(topics = "${topic.lanes.name}", groupId = "${topic.lanes.group}")
    void lanesListener(ConsumerRecord<String, String> data) {
        log.info("Received lane: " + data.value());
    }
}
