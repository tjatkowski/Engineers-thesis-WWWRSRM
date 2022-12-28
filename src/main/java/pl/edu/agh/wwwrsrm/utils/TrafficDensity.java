package pl.edu.agh.wwwrsrm.utils;

import lombok.Getter;
import org.springframework.stereotype.Component;
import pl.edu.agh.wwwrsrm.graph.GraphOSM;
import pl.edu.agh.wwwrsrm.graph.NodeIdPairKey;
import pl.edu.agh.wwwrsrm.graph.WayOSM;
import pl.edu.agh.wwwrsrm.model.Road;
import proto.model.CarMessage;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class TrafficDensity {

    @Getter
    private final Map<String, Road> roads;

    private final GraphOSM osm_graph;

    public TrafficDensity(GraphOSM osm_graph) {
        this.osm_graph = osm_graph;
        this.roads = osm_graph.getWays().values().stream()
                .map(WayOSM::getWayId)
                .collect(Collectors.toMap(Function.identity(), Road::new));
    }

    public void nextBatch() {
        roads.values().forEach(Road::clearDensity);
    }

    public void processCarMessage(CarMessage carMessage) {
        String startNode = carMessage.getNode1Id();
        String endNode = carMessage.getNode2Id();

        String wayId = osm_graph.getNodePairToWayMap().get(new NodeIdPairKey(startNode, endNode));
        if(wayId == null)
            wayId = osm_graph.getNodePairToWayMap().get(new NodeIdPairKey(endNode, startNode));
        if(wayId == null)
            return;
        Road road = roads.get(wayId);
        if (road == null) {
            roads.put(wayId, new Road(wayId));
        } else {
            road.increaseDensity();
        }
    }
}
