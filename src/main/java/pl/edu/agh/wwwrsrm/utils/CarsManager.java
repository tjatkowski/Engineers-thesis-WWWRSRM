package pl.edu.agh.wwwrsrm.utils;

import lombok.Getter;
import org.springframework.stereotype.Component;
import pl.edu.agh.wwwrsrm.model.Car;
import proto.model.CarMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Getter
public class CarsManager {
    private Map<String, Car> cars = new HashMap<>();

    public void processCarMessageList(List<CarMessage> carMessageList) {
        Map<String, Car> newCars = new HashMap<>();
        carMessageList.forEach(carMessage -> {
            Car car;
            if (this.cars.containsKey(carMessage.getCarId())) {
                car = updateCar(carMessage, this.cars.get(carMessage.getCarId()));
            } else {
                car = buildCar(carMessage);
            }
            newCars.put(car.getCarId(), car);
        });
        this.cars = newCars;
    }

    private Car updateCar(CarMessage carMessage, Car car) {
        return Car.builder()
                .carId(carMessage.getCarId())
                .length(carMessage.getLength())
                .acceleration(carMessage.getAcceleration())
                .speed(carMessage.getSpeed())
                .maxSpeed(carMessage.getMaxSpeed())
                .node1Id(carMessage.getNode1Id())
                .node2Id(carMessage.getNode2Id())
                .positionOnLane(carMessage.getPositionOnLane())
                .lastNode1Id(car.getNode1Id())
                .lastNode2Id(car.getNode2Id())
                .lastPositionOnLane(car.getPositionOnLane())
                .multiplier(adjustMultiplier(car))
                .build();
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

    private double adjustMultiplier(Car car) {
        double offset = (1.0 - car.getProgress()) / 3.0;
        return Math.max(Math.min(car.getMultiplier() + offset, 10.0), 0.1);
    }
}
