package pl.edu.agh.wwwrsrm.graph;

import org.openstreetmap.osmosis.core.domain.v0_6.Way;
import org.openstreetmap.osmosis.core.domain.v0_6.WayNode;
import pl.edu.agh.wwwrsrm.osm.WayParameters;

import java.util.ArrayList;
import java.util.List;

public class OSM_Way {
    private List<OSM_Edge> edges = new ArrayList<>();
    private final boolean closed;
    private final WayParameters edgeParameter;

    public OSM_Way(boolean closed, WayParameters edgeParameter) {
        this.closed = closed;
        this.edgeParameter = edgeParameter;
    }

    public OSM_Way(List<OSM_Edge> edges, boolean closed, WayParameters edgeParameter) {
        this.edges = edges;
        this.closed = closed;
        this.edgeParameter = edgeParameter;
    }

    public static boolean checkIfClosed(Way way) {
        List<WayNode> wayNodes = way.getWayNodes();
        return wayNodes.get(0).getNodeId() == wayNodes.get(wayNodes.size() - 1).getNodeId();
    }

    public void addEdge(OSM_Edge edge) {
        this.edges.add(edge);
    }

    public List<OSM_Edge> getEdges() {
        return this.edges;
    }

    public WayParameters getEdgeParameter() {
        return this.edgeParameter;
    }

    public boolean isClosed() {
        return this.closed;
    }
}
