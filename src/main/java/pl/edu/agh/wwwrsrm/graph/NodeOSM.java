package pl.edu.agh.wwwrsrm.graph;

import lombok.Getter;
import pl.edu.agh.wwwrsrm.utils.coordinates.LonLatCoordinate;

/**
 * NodeOSM implements road graph node
 */
@Getter
public class NodeOSM {
    private final long id;
    private final LonLatCoordinate coordinate;

    public NodeOSM(long id, LonLatCoordinate coordinate) {
        this.id = id;
        this.coordinate = coordinate;
    }
}
