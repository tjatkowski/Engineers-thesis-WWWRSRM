package pl.edu.agh.wwwrsrm.connection.consumer;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import pl.edu.agh.wwwrsrm.model.Car;
import proto.model.CarsMessage;

import java.util.LinkedList;

import static pl.edu.agh.wwwrsrm.connection.config.TopicConfiguration.CARS_TOPIC;

@Slf4j
@Service
public class CarsConsumer {
    @Getter
    private final LinkedList<Car> cars = new LinkedList<>();

    @KafkaListener(topics = CARS_TOPIC, groupId = CARS_TOPIC, batch = "true", properties = {
            "specific.protobuf.value.type: proto.model.CarsMessage"
    })
    void carsListener(ConsumerRecords<String, CarsMessage> records) {
        log.info("Start car batch processing");
        for (ConsumerRecord<String, CarsMessage> cr : records) {
            CarsMessage carsMessage = cr.value();
            log.info("Received [cars:{}, iteration: {}, partition:{}, offset:{}]", carsMessage.getCarsMessagesCount(),
                    carsMessage.getIterationNumber(), cr.partition(), cr.offset());
            carsMessage.getCarsMessagesList().forEach(carMessage -> {
                Car car = Car.builder()
                        .carId(carMessage.getCarId())
                        .length(carMessage.getLength())
                        .acceleration(carMessage.getAcceleration())
                        .speed(carMessage.getSpeed())
                        .maxSpeed(carMessage.getMaxSpeed())
                        .node1Id(carMessage.getNode1Id())
                        .node2Id(carMessage.getNode2Id())
                        .positionOnLane(carMessage.getPositionOnLane())
                        .build();
                this.cars.add(car);
            });
        }
        log.info("End car batch processing");
    }

}
