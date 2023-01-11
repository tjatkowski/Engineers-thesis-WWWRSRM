package pl.edu.agh.wwwrsrm.parser.osm;

import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.model.iface.*;
import de.topobyte.osm4j.xml.dynsax.OsmXmlIterator;
import pl.edu.agh.wwwrsrm.graph.EdgeOSM;
import pl.edu.agh.wwwrsrm.graph.NodeIdPairKey;
import pl.edu.agh.wwwrsrm.graph.NodeOSM;
import pl.edu.agh.wwwrsrm.graph.WayOSM;
import pl.edu.agh.wwwrsrm.parser.parameters.WayParameters;
import pl.edu.agh.wwwrsrm.utils.coordinates.LonLatCoordinate;

import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class OsmFormatReader {
    public static void read(InputStream is, Map<String, NodeOSM> nodes, Map<String, WayOSM> ways,
                     Map<NodeIdPairKey, String> nodeIdPairToWayIdMap, Map<String, WayParameters> roadZooms) {
        OsmIterator iterator = new OsmXmlIterator(is, false);
        Map<Long, OsmNode> allNodes = new HashMap<>();
        Map<Long, OsmWay> allWays = new HashMap<>();
        for (EntityContainer container : iterator) {
            if (container.getType() == EntityType.Node) {
                OsmNode node = (OsmNode) container.getEntity();
                allNodes.put(node.getId(), node);
            } else if (container.getType() == EntityType.Way) {
                OsmWay way = (OsmWay) container.getEntity();
                allWays.put(way.getId(), way);
            }
        }
        for (OsmWay way : allWays.values()) {
            if (way.getNumberOfNodes() < 2) {
                continue;
            }

            WayParameters wayParameters = IntStream.range(0, way.getNumberOfTags()).boxed()
                    .map(way::getTag)
                    .map(OsmTag::getValue)
                    .filter(roadZooms::containsKey)
                    .map(roadZooms::get)
                    .findAny()
                    .orElse(null);

            if (wayParameters == null) {
                continue;
            }
            String wayId = Long.toString(way.getId());
            List<EdgeOSM> edges = new LinkedList<>();
            IntStream.range(0, way.getNumberOfNodes()).boxed()
                    .map(way::getNodeId)
                    .map(allNodes::get)
                    .map(node -> new NodeOSM(node.getId(), new LonLatCoordinate(node.getLongitude(), node.getLatitude())))
                    .reduce((node1, node2) -> {
                        nodes.putIfAbsent(node1.getId(), node1);
                        nodes.putIfAbsent(node2.getId(), node2);
                        edges.add(new EdgeOSM(wayId, node1, node2));
                        nodeIdPairToWayIdMap.put(new NodeIdPairKey(node1.getId(), node2.getId()), wayId);
                        return node2;
                    });

            boolean isWayClosed = !wayParameters.getType().equals("highway") && checkIfClosed(way);
            WayOSM osmWay = new WayOSM(wayId, edges, wayParameters, isWayClosed);
            ways.put(wayId, osmWay);
        }
    }

    private static boolean checkIfClosed(OsmWay way) {
        int numberOfNodes = way.getNumberOfNodes();
        return way.getNodeId(0) == way.getNodeId(numberOfNodes - 1);
    }
}
