package pl.edu.agh.wwwrsrm.graph;

import lombok.Getter;
import org.openstreetmap.osmosis.core.domain.v0_6.Node;

/**
 * NodeOSM implements road graph node
 */
@Getter
public class NodeOSM {
    private final long id;
    private final double latitude;
    private final double longitude;


    public NodeOSM(Node node) {
        this.id = node.getId();
        this.latitude = node.getLatitude();
        this.longitude = node.getLongitude();
    }
}
