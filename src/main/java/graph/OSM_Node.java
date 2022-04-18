package graph;

import org.openstreetmap.osmosis.core.domain.v0_6.Node;

/**
 * OSM_Node implements road graph node
 */
public class OSM_Node {
    private final long id;
    private final double latitude;
    private final double longitude;


    public OSM_Node(Node node) {
        this.id = node.getId();
        this.latitude = node.getLatitude();
        this.longitude = node.getLongitude();
    }

    public long getId() {
        return this.id;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

}
