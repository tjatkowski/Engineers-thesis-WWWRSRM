package pl.edu.agh.wwwrsrm.connection.consumer;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import proto.model.CarMessage;
import proto.model.CarsMessage;

import java.util.Date;
import java.util.LinkedList;

import static pl.edu.agh.wwwrsrm.connection.config.TopicConfiguration.CARS_TOPIC;

@Slf4j
@Service
public class CarsConsumer {
    @Getter
    private final LinkedList<CarMessage> carMessages = new LinkedList<>();

    @KafkaListener(topics = CARS_TOPIC, groupId = CARS_TOPIC, batch = "true", properties = {
            "specific.protobuf.value.type: proto.model.CarsMessage"
    })
    void carsListener(ConsumerRecords<String, CarsMessage> records) {
        log.info("Start batch processing");

        synchronized (this) {
            for (ConsumerRecord<String, CarsMessage> cr : records) {
                log.info("Received cars [data:{}, partition:{}, offset:{}]",
                        cr.value().getClass().getSimpleName(), cr.partition(), cr.offset());
                var d = new Date(cr.timestamp());
                log.info(String.valueOf(d));
                CarsMessage carsMessage = cr.value();
                log.info("IterationNumber={}", carsMessage.getIterationNumber());
                this.carMessages.addAll(carsMessage.getCarsMessagesList());

            }
        log.info("End batch processing");
        System.out.println("Current cars size : " + this.carMessages.size());
        }
    }

    public void clearMessages() {
        this.carMessages.clear();
    }
}
