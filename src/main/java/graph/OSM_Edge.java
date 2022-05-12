package graph;

/**
 * OSM_Edge class implements road graph edge
 */
public class OSM_Edge {
    private final long id;
    private final OSM_Node startNode;
    private final OSM_Node endNode;

    public OSM_Edge(long id, OSM_Node startNode, OSM_Node endNode) {
        this.id = id;
        this.startNode = startNode;
        this.endNode = endNode;
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
}
