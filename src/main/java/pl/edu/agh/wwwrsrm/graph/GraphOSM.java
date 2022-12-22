package pl.edu.agh.wwwrsrm.graph;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.edu.agh.wwwrsrm.utils.coordinates.LonLatCoordinate;

import java.util.*;

/**
 * GraphOSM implements road graph
 */
@Getter
@AllArgsConstructor
public class GraphOSM {
    private final Map<Long, NodeOSM> nodes;
    private final Map<Long, WayOSM> ways;
    private final Map<NodeIdPairKey, Long> nodePairToWayMap;

    /**
     * @return road graph max Latitude coordinate
     */
    public Double getTopBound() {
        NodeOSM topNode = Collections.max(this.nodes.values(), Comparator.comparing(nodeOSM -> nodeOSM.getCoordinate().getLatitude()));
        return topNode.getCoordinate().getLatitude();
    }

    /**
     * @return road graph min Longitude coordinate
     */
    public Double getLeftBound() {
        NodeOSM leftNode = Collections.min(this.nodes.values(), Comparator.comparing(nodeOSM -> nodeOSM.getCoordinate().getLongitude()));
        return leftNode.getCoordinate().getLongitude();
    }

    /**
     * @return road graph min Latitude coordinate
     */
    public Double getBottomBound() {
        NodeOSM bottomNode = Collections.min(this.nodes.values(), Comparator.comparing(nodeOSM -> nodeOSM.getCoordinate().getLatitude()));
        return bottomNode.getCoordinate().getLatitude();
    }

    /**
     * @return road graph max Longitude coordinate
     */
    public Double getRightBound() {
        NodeOSM rightNode = Collections.max(this.nodes.values(), Comparator.comparing(nodeOSM -> nodeOSM.getCoordinate().getLongitude()));
        return rightNode.getCoordinate().getLongitude();
    }

    /**
     * @return LonLatCoordinate (min Longitude, max Latitude)
     */
    public LonLatCoordinate getTopLeftBound() {
        Double topBound = this.getTopBound();
        Double leftBound = this.getLeftBound();
        return new LonLatCoordinate(leftBound, topBound);
    }

    /**
     * @return LonLatCoordinate (max Longitude, min Latitude)
     */
    public LonLatCoordinate getBottomRightBound() {
        Double bottomBound = this.getBottomBound();
        Double rightBound = this.getRightBound();
        return new LonLatCoordinate(rightBound, bottomBound);
    }
}
