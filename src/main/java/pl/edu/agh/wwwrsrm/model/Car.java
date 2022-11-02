package pl.edu.agh.wwwrsrm.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class Car {

    private final String carId;
    private final double length;
    private final double acceleration;
    private final double speed;
    private final double maxSpeed;
    private final String node1Id;
    private final String node2Id;
    private final double positionOnLane;

}
