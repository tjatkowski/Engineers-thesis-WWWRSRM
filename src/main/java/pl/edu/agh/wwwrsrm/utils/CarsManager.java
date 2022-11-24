package pl.edu.agh.wwwrsrm.utils;

import lombok.Getter;
import org.springframework.stereotype.Component;
import pl.edu.agh.wwwrsrm.model.Car;
import proto.model.CarMessage;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Getter
@Component
public class CarsManager {
    private final java.util.Map<String, Car> cars = new HashMap<>();

    public void nextBatch() {
        for(Iterator<Map.Entry<String, Car>> it = cars.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, Car> entry = it.next();
            if (entry.getValue().isToDelete()) {
                it.remove();
            }
            else {
                entry.getValue().setToDelete();
            }
        }
    }

    public void processCarMessage(CarMessage carMessage) {
        Car car = cars.get(carMessage.getCarId());
        if(car != null)
            car.update(carMessage);
        else
            cars.put(carMessage.getCarId(), buildCar(carMessage));
    }

    private Car buildCar(CarMessage carMessage) {
        return Car.builder()
                  .carId(carMessage.getCarId())
                  .length(carMessage.getLength())
                  .acceleration(carMessage.getAcceleration())
                  .speed(carMessage.getSpeed())
                  .maxSpeed(carMessage.getMaxSpeed())
                  .node1Id(carMessage.getNode1Id())
                  .node2Id(carMessage.getNode2Id())
                  .positionOnLane(carMessage.getPositionOnLane())
                  .build();
    }
}
