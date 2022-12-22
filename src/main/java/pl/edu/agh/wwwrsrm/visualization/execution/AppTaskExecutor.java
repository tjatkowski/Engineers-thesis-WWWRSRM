package pl.edu.agh.wwwrsrm.visualization.execution;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.edu.agh.wwwrsrm.events.*;
import pl.edu.agh.wwwrsrm.graph.GraphOSM;
import pl.edu.agh.wwwrsrm.graph.NodeOSM;
import pl.edu.agh.wwwrsrm.utils.CarsManager;
import pl.edu.agh.wwwrsrm.utils.TrafficDensity;
import proto.model.CarMessage;
import proto.model.Node;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class AppTaskExecutor {

    private final CarsManager carsManager;
    private final TrafficDensity trafficDensity;
    private final GraphOSM graphOSM;

    @EventListener(ApplicationStartedEvent.class)
    public void onApplicationStartedEvent(ApplicationStartedEvent event) {
        log.info("Application started");
    }

    @SneakyThrows
    @EventListener(ApplicationStoppedEvent.class)
    public synchronized void onApplicationStoppedEvent(ApplicationStoppedEvent event) {
        log.info("Application stopped");
    }

    @EventListener(ApplicationResumedEvent.class)
    public synchronized void onApplicationResumedEvent(ApplicationResumedEvent event) {
        log.info("Application resumed");
    }

    @EventListener(ApplicationClosedEvent.class)
    public void onApplicationClosedEvent(ApplicationClosedEvent event) {
        log.info("Application closed");
    }

    @EventListener(CarBatchReadyEvent.class)
    public void consumeCars(CarBatchReadyEvent event) {
        List<CarMessage> carMessageList = event.getCarMessageList();
        if (carMessageList.isEmpty()) {
            return;
        }
        carsManager.nextBatch();
        carMessageList.forEach(carsManager::processCarMessage);
        trafficDensity.nextBatch();
        carMessageList.forEach(trafficDensity::processCarMessage);
    }
}