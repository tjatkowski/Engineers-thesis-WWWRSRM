package pl.edu.agh.wwwrsrm.connection.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import pl.edu.agh.wwwrsrm.events.CarBatchReadyEvent;
import proto.model.CarMessage;
import proto.model.CarsMessage;

import java.util.LinkedList;

import static pl.edu.agh.wwwrsrm.connection.config.TopicConfiguration.CARS_TOPIC;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarsConsumer {

    private final ApplicationContext applicationContext;

    private final LinkedList<CarMessage> carMessages = new LinkedList<>();
    private int currentIterationNumber;

    @KafkaListener(topics = CARS_TOPIC, groupId = CARS_TOPIC, batch = "true", properties = {
            "specific.protobuf.value.type: proto.model.CarsMessage"
    })
    void carsListener(ConsumerRecords<String, CarsMessage> records) {
        log.info("Start batch processing");
        for (ConsumerRecord<String, CarsMessage> cr : records) {
            CarsMessage carsMessage = cr.value();
            int iterationNumber = (int) carsMessage.getIterationNumber();
            log.info("Received [cars:{}, iteration: {}, partition:{}, offset:{}]", carsMessage.getCarsMessagesCount(),
                    iterationNumber, cr.partition(), cr.offset());
            if (iterationNumber > currentIterationNumber) {
                currentIterationNumber = iterationNumber;
                applicationContext.publishEvent(new CarBatchReadyEvent(this, carMessages.stream().toList()));
                carMessages.clear();
            } else if (iterationNumber < currentIterationNumber) {
                continue;
            }
            this.carMessages.addAll(carsMessage.getCarsMessagesList());
        }
        log.info("End car batch processing");
    }
}
