package pl.edu.agh.wwwrsrm.connection.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.jetbrains.annotations.NotNull;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import pl.edu.agh.wwwrsrm.connection.config.TopicConfiguration;
import proto.model.RUNNING_STATE;
import proto.model.VisualizationStateChangeMessage;

@Slf4j
@Service
@RequiredArgsConstructor
public class VisualizationStateChangeProducer {

    private final KafkaTemplate<String, VisualizationStateChangeMessage> kafkaVisualizationStateTemplate;

    public void sendStateChangeMessage(RUNNING_STATE running_state) {
        VisualizationStateChangeMessage simulationStateChangeMessage = VisualizationStateChangeMessage.newBuilder()
                .setStateChange(running_state)
                .build();

        var record = new ProducerRecord<String, VisualizationStateChangeMessage>(TopicConfiguration.SIMULATION_STATE_CHANGE_TOPIC,
                simulationStateChangeMessage);
        ListenableFuture<SendResult<String, VisualizationStateChangeMessage>> future = kafkaVisualizationStateTemplate.send(record);

        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, VisualizationStateChangeMessage
                    > result) {
                log.info("VisualizationStateChangeMessage send: {}", simulationStateChangeMessage.getStateChange());
            }

            @Override
            public void onFailure(@NotNull Throwable ex) {
                log.info("Error while sending message: {}", simulationStateChangeMessage + " " + ex.getMessage());
            }
        });
    }
}
