package graph;

import osm.EdgeParameter;

/**
 * OSM_Edge class implements road graph edge
 */
public class OSM_Edge {
    private final long id;
    private final OSM_Node startNode;
    private final OSM_Node endNode;
    private final EdgeParameter edgeParameter;

    public OSM_Edge(long id, OSM_Node startNode, OSM_Node endNode, EdgeParameter edgeParameter) {
        this.id = id;
        this.startNode = startNode;
        this.endNode = endNode;
        this.edgeParameter = edgeParameter;
    }

    public long getId() {
        return this.id;
    }

    public OSM_Node getStartNode() {
        return this.startNode;
    }

    public OSM_Node getEndNode() {
        return this.endNode;
    }

    public EdgeParameter getEdgeParameter() {
        return this.edgeParameter;
    }
}
