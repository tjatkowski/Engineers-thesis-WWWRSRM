package pl.edu.agh.wwwrsrm.visualization.execution;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.edu.agh.wwwrsrm.connection.consumer.CarsConsumer;
import pl.edu.agh.wwwrsrm.connection.consumer.SimulationNewNodesConsumer;
import pl.edu.agh.wwwrsrm.model.Car;
import pl.edu.agh.wwwrsrm.window.map.Map;
import proto.model.Node;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class VisualizationTask implements Runnable {

    private final CarsConsumer consumer;

    private final SimulationNewNodesConsumer simulationNewNodesConsumer;

    private final Map map;

    @Override
    public void run() {
        consumeNewNodes();
        consumeCars();
    }

    private void consumeNewNodes() {
        log.debug("Start consuming new nodes");
        List<Node> nodes = simulationNewNodesConsumer.getNewNodesList();
        log.info("Consumed {} new nodes", nodes.size());
        map.addNodes(nodes);
        nodes.clear();
    }

    private void consumeCars() {
        log.debug("Start consuming cars");
        LinkedList<Car> cars = consumer.getCars();
        log.info("Consumed cars size: {}", cars.size());
        if (!cars.isEmpty()) {
            map.clearCars();
        }
        while (!cars.isEmpty()) {
            Car car = cars.poll();
//                        log.info("Consumed carId : {}", car.getCarId());
            if(car != null)
                map.updateCar(car);
        }
        log.info("End consuming cars");
    }
}
