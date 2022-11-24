package pl.edu.agh.wwwrsrm.visualization.execution;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.edu.agh.wwwrsrm.connection.consumer.CarsConsumer;
import pl.edu.agh.wwwrsrm.connection.consumer.SimulationNewNodesConsumer;
import pl.edu.agh.wwwrsrm.utils.CarsManager;
import pl.edu.agh.wwwrsrm.utils.TrafficDensity;
import proto.model.CarMessage;

import java.util.LinkedList;

@Slf4j
@Component
@RequiredArgsConstructor
public class VisualizationTask implements Runnable {

    private final CarsConsumer consumer;

    private final CarsManager carsManager;

    private final TrafficDensity trafficDensity;

    private final SimulationNewNodesConsumer simulationNewNodesConsumer;

    @Override
    public void run() {
//        consumeNewNodes();
        consumeCars();
    }

    private void consumeNewNodes() {
//        log.debug("Start consuming new nodes");
//        List<Node> nodes = simulationNewNodesConsumer.getNewNodesList();
//        log.info("Consumed {} new nodes", nodes.size());
//        map.addNodes(nodes);
//        nodes.clear();
    }

    private void consumeCars() {
        LinkedList<CarMessage> carMessages = consumer.getCarMessages();
        if (carMessages.isEmpty())
            return;
        log.info("Consumed: {} cars messages", carMessages.size());

        synchronized (consumer) {
            synchronized (carsManager) {
                carsManager.nextBatch();
                carMessages.forEach(carsManager::processCarMessage);
            }
            synchronized (trafficDensity) {
                trafficDensity.nextBatch();
                carMessages.forEach(trafficDensity::processCarMessage);
            }
            consumer.clearMessages();
        }

        log.info("End consuming cars messages");
    }
}
