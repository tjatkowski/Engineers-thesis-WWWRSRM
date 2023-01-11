package pl.edu.agh.wwwrsrm.parser.pbf;

import crosby.binary.osmosis.OsmosisReader;
import org.openstreetmap.osmosis.core.domain.v0_6.Node;
import org.openstreetmap.osmosis.core.domain.v0_6.Tag;
import org.openstreetmap.osmosis.core.domain.v0_6.Way;
import org.openstreetmap.osmosis.core.domain.v0_6.WayNode;
import pl.edu.agh.wwwrsrm.graph.EdgeOSM;
import pl.edu.agh.wwwrsrm.graph.NodeIdPairKey;
import pl.edu.agh.wwwrsrm.graph.NodeOSM;
import pl.edu.agh.wwwrsrm.graph.WayOSM;
import pl.edu.agh.wwwrsrm.parser.parameters.WayParameters;
import pl.edu.agh.wwwrsrm.utils.coordinates.LonLatCoordinate;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PbfFormatReader {

    public static void read(InputStream is, Map<String, NodeOSM> nodes, Map<String, WayOSM> ways,
                     Map<NodeIdPairKey, String> nodeIdPairToWayIdMap, Map<String, WayParameters> roadZooms) {
        PbfFormatSink myOsmReader = new PbfFormatSink();
        OsmosisReader osmosisReader = new OsmosisReader(is);
        osmosisReader.setSink(myOsmReader);
        osmosisReader.run();

        Map<Long, Node> allNodes = myOsmReader.getNodes();
        Map<Long, Way> allWays = myOsmReader.getWays();

        for (Way way : allWays.values()) {
            if (way.getWayNodes().size() < 2) {
                continue;
            }

            WayParameters wayParameters = way.getTags().stream()
                    .map(Tag::getValue)
                    .filter(roadZooms::containsKey)
                    .map(roadZooms::get)
                    .findAny()
                    .orElse(null);

            if (wayParameters == null) {
                continue;
            }

            String wayId = Long.toString(way.getId());
            List<EdgeOSM> edges = new LinkedList<>();
            way.getWayNodes().stream()
                    .map(WayNode::getNodeId)
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

    private static boolean checkIfClosed(Way way) {
        List<WayNode> wayNodes = way.getWayNodes();
        return wayNodes.get(0).getNodeId() == wayNodes.get(wayNodes.size() - 1).getNodeId();
    }
}
