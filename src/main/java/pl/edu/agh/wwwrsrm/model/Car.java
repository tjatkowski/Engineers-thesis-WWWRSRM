package pl.edu.agh.wwwrsrm.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
//@RequiredArgsConstructor
public class Car {

    private String carId;
    private double length;
    private double acceleration;
    private double speed;
    private double maxSpeed;
    private String node1Id;
    private String node2Id;
    private double positionOnLane;

    private String lastNode1Id;
    private String lastNode2Id;
    private double lastPositionOnLane;

    private double progress = 0;

    public boolean toDelete = false;

    public void update(Car car) {
        toDelete = false;

        this.lastNode1Id = this.node1Id;
        this.lastNode2Id = this.node2Id;
        this.lastPositionOnLane = this.positionOnLane;
        this.progress = 0;

        this.carId = car.getCarId();
        this.length = car.getLength();
        this.acceleration = car.getAcceleration();
        this.speed = car.getSpeed();
        this.maxSpeed = car.getMaxSpeed();
        this.node1Id = car.getNode1Id();
        this.node2Id = car.getNode2Id();
        this.positionOnLane = car.getPositionOnLane();
    }
}
