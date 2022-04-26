package graph;

import javafx.geometry.Point2D;

import java.util.*;

/**
 * OSM_Graph implements road graph
 */
public class OSM_Graph {
    private final ArrayList<OSM_Edge> OSM_Edges = new ArrayList<>();
    private final Map<Long, OSM_Node> OSM_nodes = new HashMap<>();

    public OSM_Graph() {
    }

    public void addNode(OSM_Node node) {
        this.OSM_nodes.put(node.getId(), node);
    }

    public void addEdge(OSM_Edge edge) {
        this.OSM_Edges.add(edge);
    }

    /**
     * @return road graph max Latitude coordinate
     */
    public Double getTopBound() {
        return Collections.max(this.OSM_nodes.values(), Comparator.comparing(OSM_Node::getLatitude)).getLatitude();
    }

    /**
     * @return road graph min Longitude coordinate
     */
    public Double getLeftBound() {
        return Collections.min(this.OSM_nodes.values(), Comparator.comparing(OSM_Node::getLongitude)).getLongitude();
    }

    /**
     * @return road graph min Latitude coordinate
     */
    public Double getBottomBound() {
        return Collections.min(this.OSM_nodes.values(), Comparator.comparing(OSM_Node::getLatitude)).getLatitude();
    }

    /**
     * @return road graph max Longitude coordinate
     */
    public Double getRightBound() {
        return Collections.max(this.OSM_nodes.values(), Comparator.comparing(OSM_Node::getLongitude)).getLongitude();
    }

    /**
     * @return Point2D as [min Longitude, max Latitude]
     */
    public Point2D getTopLeftBound() {
        Double topBound = this.getTopBound();
        Double leftBound = this.getLeftBound();
        return new Point2D(leftBound, topBound);
    }

    /**
     * @return Point2D as [max Longitude, min Latitude]
     */
    public Point2D getBottomRightBound() {
        Double bottomBound = this.getBottomBound();
        Double rightBound = this.getRightBound();
        return new Point2D(rightBound, bottomBound);
    }


    public Map<Long, OSM_Node> getNodes() {
        return this.OSM_nodes;
    }

    public ArrayList<OSM_Edge> getEdges() {
        return this.OSM_Edges;
    }


}
