package pl.edu.agh.wwwrsrm.graph;

import javafx.geometry.Point2D;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;

/**
 * GraphOSM implements road graph
 */
@Getter
@NoArgsConstructor
public class GraphOSM {
    private final Map<Long, NodeOSM> nodes = new HashMap<>();
    private final List<WayOSM> ways = new ArrayList<>();

    public void addNode(NodeOSM node) {
        this.nodes.put(node.getId(), node);
    }

    public void addWay(WayOSM way) {
        this.ways.add(way);
    }

    /**
     * @return road graph max Latitude coordinate
     */
    public Double getTopBound() {
        return Collections.max(this.nodes.values(), Comparator.comparing(NodeOSM::getLatitude)).getLatitude();
    }

    /**
     * @return road graph min Longitude coordinate
     */
    public Double getLeftBound() {
        return Collections.min(this.nodes.values(), Comparator.comparing(NodeOSM::getLongitude)).getLongitude();
    }

    /**
     * @return road graph min Latitude coordinate
     */
    public Double getBottomBound() {
        return Collections.min(this.nodes.values(), Comparator.comparing(NodeOSM::getLatitude)).getLatitude();
    }

    /**
     * @return road graph max Longitude coordinate
     */
    public Double getRightBound() {
        return Collections.max(this.nodes.values(), Comparator.comparing(NodeOSM::getLongitude)).getLongitude();
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
}
