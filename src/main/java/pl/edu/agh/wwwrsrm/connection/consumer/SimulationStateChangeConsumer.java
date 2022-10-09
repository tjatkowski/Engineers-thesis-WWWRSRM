package pl.edu.agh.wwwrsrm.connection.consumer;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import pl.edu.agh.wwwrsrm.events.ApplicationClosedEvent;
import pl.edu.agh.wwwrsrm.events.ApplicationResumedEvent;
import pl.edu.agh.wwwrsrm.events.ApplicationStartedEvent;
import pl.edu.agh.wwwrsrm.events.ApplicationStoppedEvent;
import proto.model.SimulationStateChangeMessage;

import static pl.edu.agh.wwwrsrm.connection.config.TopicConfiguration.SIMULATION_STATE_CHANGE_TOPIC;

@Slf4j
@Service
@RequiredArgsConstructor
public class SimulationStateChangeConsumer {

    private final ApplicationContext applicationContext;

    @KafkaListener(topics = SIMULATION_STATE_CHANGE_TOPIC, groupId = SIMULATION_STATE_CHANGE_TOPIC, properties = {
            "specific.protobuf.value.type: proto.model.SimulationStateChangeMessage"
    })
    void stateChangeListener(ConsumerRecord<String, SimulationStateChangeMessage> record) {
        SimulationStateChangeMessage simulationStateChangeMessage = record.value();
        log.info("Consumed SimulationStateChangeMessage:{}, partition:{}, offset:{}", simulationStateChangeMessage.getStateChange(),
                record.partition(), record.offset());

        switch (simulationStateChangeMessage.getStateChange()) {
            case STARTED -> applicationContext.publishEvent(new ApplicationStartedEvent(this));
            case RESUMED -> applicationContext.publishEvent(new ApplicationResumedEvent(this));
            case STOPPED -> applicationContext.publishEvent(new ApplicationStoppedEvent(this));
            case CLOSED -> applicationContext.publishEvent(new ApplicationClosedEvent(this));
        }
    }
}
