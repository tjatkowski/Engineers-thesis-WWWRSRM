package pl.edu.agh.wwwrsrm.utils;

import org.springframework.stereotype.Component;
import pl.edu.agh.wwwrsrm.graph.EdgeOSM;
import pl.edu.agh.wwwrsrm.graph.GraphOSM;
import pl.edu.agh.wwwrsrm.model.Road;
import proto.model.CarMessage;

import java.util.HashMap;

@Component
public class TrafficDensity {

    private final HashMap<Long, HashMap<Long, Road>> roads = new HashMap<>();

    private GraphOSM osm_graph = null;

    public void setOsmGraph(GraphOSM osm_graph) {
        this.osm_graph = osm_graph;
    }

    public HashMap<Long, HashMap<Long, Road>> getRoads() {
        return roads;
    }

    private boolean isCarOnEdge(CarMessage car, EdgeOSM edge) {
        long startNode = edge.getStartNode().getId();
        long endNode = edge.getEndNode().getId();

        long node1 = Long.parseLong(car.getNode1Id());
        long node2 = Long.parseLong(car.getNode2Id());

        return (startNode == node1 && endNode == node2) || (startNode == node2 && endNode == node1);
    }

    public Road getRoad(Long k1, Long k2) {
        HashMap<Long, Road> map = roads.get(k1);
        if (map == null)
            return null;

        return map.get(k2);
    }

    private void putRoad(Long k1, Long k2, Road road) {
        HashMap<Long, Road> map = roads.get(k1);
        if (map == null) {
            map = new HashMap<>();
            roads.put(k1, map);
        }

        map.put(k2, road);
    }

    public void nextBatch() {
        if(osm_graph == null)
            return;
        for(HashMap<Long, Road> map : roads.values()) {
            map.clear();
        }
        roads.clear();
    }

    public void processCarMessage(CarMessage carMessage) {
        if(osm_graph == null)
            return;

        long startNode = Long.parseLong(carMessage.getNode1Id());
        long endNode = Long.parseLong(carMessage.getNode2Id());

        long minStartNode = Math.min(startNode, endNode);
        long minEndNode = Math.max(startNode, endNode);

        Road road = getRoad(minStartNode, minEndNode);
        if (road == null) {
            putRoad(minStartNode, minEndNode, Road.builder()
                    .density(1)
                    .node1Id(minStartNode)
                    .node2Id(minEndNode)
                    .build());
        } else {
//                    road.increaseDensity();
        }
//        for (WayOSM way : osm_graph.getWays()) {
//            for (EdgeOSM edge : way.getEdges()) {
//                if (!isCarOnEdge(carMessage, edge))
//                    continue;
//                long startNode = edge.getStartNode().getId();
//                long endNode = edge.getEndNode().getId();
//
//                long minStartNode = min(startNode, endNode);
//                long minEndNode = java.lang.Math.max(startNode, endNode);
//
//                Road road = getRoad(minStartNode, minEndNode);
//                if (road == null) {
//                    putRoad(minStartNode, minEndNode, Road.builder()
//                            .density(1)
//                            .node1Id(minStartNode)
//                            .node2Id(minEndNode)
////                            .way(way)
//                            .build());
//                } else {
////                    road.increaseDensity();
//                }
//            }
//        }
    }
}
