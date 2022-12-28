package pl.edu.agh.wwwrsrm.graph;

import lombok.Getter;
import pl.edu.agh.wwwrsrm.utils.coordinates.LonLatCoordinate;
import proto.model.Coordinates;
import proto.model.Node;

/**
 * NodeOSM implements road graph node
 */
@Getter
public class NodeOSM {
    private final String id;
    private final LonLatCoordinate coordinate;

    public NodeOSM(long id, LonLatCoordinate coordinate) {
        this.id = Long.toString(id);
        this.coordinate = coordinate;
    }

    public NodeOSM(String id, LonLatCoordinate coordinate) {
        this.id = id;
        this.coordinate = coordinate;
    }

    public NodeOSM(Node node) {
        this.id = node.getNodeId();
        Coordinates coords = node.getCoordinates();
        this.coordinate = new LonLatCoordinate(coords.getLongitude(), coords.getLatitude());
    }
}
