package pl.edu.agh.wwwrsrm.visualization.execution;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.edu.agh.wwwrsrm.connection.consumer.CarsConsumer;
import pl.edu.agh.wwwrsrm.model.Car;
import pl.edu.agh.wwwrsrm.render.layers.CarsLayer;
import pl.edu.agh.wwwrsrm.visualization.drawing.MapDrawer;
import pl.edu.agh.wwwrsrm.window.map.Map;

import java.util.LinkedList;

@Slf4j
@Component
@RequiredArgsConstructor
public class VisualizationTask implements Runnable {

    private final CarsConsumer consumer;
    private final MapDrawer mapDrawer;

    private final Map map;

    @Override
    public void run() {
        log.debug("Start consuming cars");
        LinkedList<Car> cars = consumer.getCars();
        log.info("Consumed cars size: {}", cars.size());
        if (!cars.isEmpty()) {
            map.clearCars();
        }
        while (!cars.isEmpty()) {
            Car car = cars.poll();
//                        log.info("Consumed carId : {}", car.getCarId());
            map.updateCar(car);
        }
        log.info("End consuming cars");
    }
}
