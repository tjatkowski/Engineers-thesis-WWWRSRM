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
        List<Node> nodes = simulationNewNodesConsumer.getNewNodesList();
        map.addNodes(nodes);
        nodes.clear();
    }

    private void consumeCars() {
        LinkedList<Car> cars = consumer.getCars();
        if (!cars.isEmpty()) {
            map.clearCars();
        }
        while (!cars.isEmpty()) {
            Car car = cars.poll();
            if(car != null)
                map.updateCar(car);
        }
    }
}
