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
import pl.edu.agh.wwwrsrm.window.map.Map;
import proto.model.VisualizationStateChangeMessage;
import proto.model.VisualizationStateChangeMessage.ROIRegion;

import java.util.Optional;

import static pl.edu.agh.wwwrsrm.connection.config.TopicConfiguration.VISUALIZATION_STATE_CHANGE_TOPIC;
import static proto.model.RUNNING_STATE.STARTED;
import static proto.model.VisualizationStateChangeMessage.ZOOM_LEVEL.CARS;

@Slf4j
@Service
@RequiredArgsConstructor
public class VisualizationStateChangeProducer {

    private final KafkaTemplate<String, VisualizationStateChangeMessage> kafkaVisualizationStateTemplate;

    public VisualizationStateChangeMessage createVisualizationStateChangeMessage(Map visualizationMap) {
        return VisualizationStateChangeMessage.newBuilder()
                .setStateChange(Optional.ofNullable(visualizationMap.getVisualizationRunningState()).orElse(STARTED))
                .setZoomLevel(Optional.ofNullable(visualizationMap.getZoomLevel()).orElse(CARS))
                .setRoiRegion(Optional.ofNullable(visualizationMap.getMapView().getRoiRegion()).orElseGet(ROIRegion::getDefaultInstance))
                .setVisualizationSpeed(visualizationMap.getVisualizationSpeed())
                .build();
    }

    public void sendStateChangeMessage(Map visualizationMap) {
        VisualizationStateChangeMessage visualizationStateChangeMessage = createVisualizationStateChangeMessage(visualizationMap);
        var record = new ProducerRecord<String, VisualizationStateChangeMessage>(VISUALIZATION_STATE_CHANGE_TOPIC, visualizationStateChangeMessage);
        ListenableFuture<SendResult<String, VisualizationStateChangeMessage>> future = kafkaVisualizationStateTemplate.send(record);

        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, VisualizationStateChangeMessage> result) {
                log.info("VisualizationStateChangeMessage send: stateChange={}, ROIRegion={}, ZoomLevel={}, VisualizationSpeed={}",
                        visualizationStateChangeMessage.getStateChange(), visualizationStateChangeMessage.getRoiRegion(),
                        visualizationStateChangeMessage.getZoomLevel(), visualizationStateChangeMessage.getVisualizationSpeed());
            }

            @Override
            public void onFailure(@NotNull Throwable ex) {
                log.info("Error while sending message: {} {}", visualizationStateChangeMessage, ex.getMessage());
            }
        });
    }
}
