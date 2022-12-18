package pl.edu.agh.wwwrsrm.utils;

import org.springframework.stereotype.Component;
import pl.edu.agh.wwwrsrm.graph.GraphOSM;
import pl.edu.agh.wwwrsrm.graph.NodeIdPairKey;
import pl.edu.agh.wwwrsrm.model.Road;
import proto.model.CarMessage;

import java.util.HashMap;

@Component
public class TrafficDensity {

    private final HashMap<Long, Road> roads = new HashMap<>();

    private final GraphOSM osm_graph;

    public TrafficDensity(GraphOSM osm_graph) {
        this.osm_graph = osm_graph;
    }

    public Road getRoad(long wayId) {
        return roads.get(wayId);
    }

    public void nextBatch() {
        roads.clear();
    }

    public void processCarMessage(CarMessage carMessage) {
        long startNode = Long.parseLong(carMessage.getNode1Id());
        long endNode = Long.parseLong(carMessage.getNode2Id());

        Long wayId = osm_graph.getNodePairToWayMap().get(new NodeIdPairKey(startNode, endNode));
        if(wayId == null)
            wayId = osm_graph.getNodePairToWayMap().get(new NodeIdPairKey(endNode, startNode));
        if(wayId == null)
            return;
        Road road = roads.get(wayId);
        if (road == null) {
            roads.put(wayId, Road.builder()
                    .density(1)
                    .node1Id(startNode)
                    .node2Id(endNode)
                    .wayId(wayId)
                    .build());
        } else {
            road.increaseDensity();
        }
    }
}
