package pl.edu.agh.wwwrsrm.consumer;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import pl.edu.agh.wwwrsrm.model.Car;
import proto.model.CarMessage;

import java.util.LinkedList;

import static pl.edu.agh.wwwrsrm.consumer.config.TopicConfiguration.*;

@Slf4j
//@Service
public class Consumer {
    @Getter
    private final LinkedList<Car> cars = new LinkedList<>();

    @KafkaListener(topics = CARS_TOPIC, groupId = CARS_TOPIC, batch = "true")
    void carsListener(ConsumerRecords<String, CarMessage> records) {
        log.info("Start batch processing");
        for (ConsumerRecord<String, CarMessage> cr : records) {
            log.info("Received car [key:{}, partition:{}, offset:{}]:{}",
                    cr.key(), cr.partition(), cr.offset(), cr.value().getCarId());
            CarMessage carMessage = cr.value();
            Car car = Car.builder()
                    .carId(carMessage.getCarId())
                    .length(carMessage.getLength())
                    .acceleration(carMessage.getAcceleration())
                    .speed(carMessage.getSpeed())
                    .maxSpeed(carMessage.getMaxSpeed())
                    .laneId(carMessage.getLaneId())
                    .positionOnLane(carMessage.getPositionOnLane())
                    .build();
            this.cars.add(car);
        }
        log.info("End batch processing");
        System.out.println("Current cars size : " + this.cars.size());
    }

    @KafkaListener(topics = JUNCTIONS_TOPIC, groupId = JUNCTIONS_TOPIC)
    void junctionsListener(ConsumerRecord<String, String> data) {
        log.info("Received junction [key:{}, partition:{}, offset:{}]:{}",
                data.key(), data.partition(), data.offset(), data.value());
    }

    @KafkaListener(topics = LANES_TOPIC, groupId = LANES_TOPIC)
    void lanesListener(ConsumerRecord<String, String> data) {
        log.info("Received lane [key:{}, partition:{}, offset:{}]:{}",
                data.key(), data.partition(), data.offset(), data.value());
    }

}
