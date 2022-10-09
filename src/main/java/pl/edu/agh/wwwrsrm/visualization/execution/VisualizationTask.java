package pl.edu.agh.wwwrsrm.visualization.execution;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.edu.agh.wwwrsrm.connection.consumer.CarsConsumer;
import pl.edu.agh.wwwrsrm.model.Car;
import pl.edu.agh.wwwrsrm.visualization.drawing.MapDrawer;

import java.util.LinkedList;

@Slf4j
@Component
@RequiredArgsConstructor
public class VisualizationTask implements Runnable {

    private final CarsConsumer consumer;
    private final MapDrawer mapDrawer;

    @Override
    public void run() {
        log.debug("Start consuming cars");
        LinkedList<Car> cars = consumer.getCars();
        log.info("Consumed cars size: {}", cars.size());
        if (!cars.isEmpty()) {
            mapDrawer.clearCars();
        }
        while (!cars.isEmpty()) {
            Car car = cars.poll();
//                        log.info("Consumed carId : {}", car.getCarId());
            mapDrawer.updateCar(car);
        }
        log.info("End consuming cars");
    }
}
