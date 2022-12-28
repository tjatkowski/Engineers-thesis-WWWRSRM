package pl.edu.agh.wwwrsrm.osm;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import crosby.binary.osmosis.OsmosisReader;
import lombok.extern.slf4j.Slf4j;
import org.openstreetmap.osmosis.core.domain.v0_6.Node;
import org.openstreetmap.osmosis.core.domain.v0_6.Tag;
import org.openstreetmap.osmosis.core.domain.v0_6.Way;
import org.openstreetmap.osmosis.core.domain.v0_6.WayNode;
import pl.edu.agh.wwwrsrm.exceptions.MapFilePathException;
import pl.edu.agh.wwwrsrm.graph.*;
import pl.edu.agh.wwwrsrm.utils.coordinates.LonLatCoordinate;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * OsmParser class parses nodes and ways read by OsmReader and creates road graph.
 * Only ways with 'highway' key parameter and values indicating possibility of car driving on them are considered.
 */
@Slf4j
public class OsmParser {

    /**
     * CreateGraph method creates road graph based on nodes and ways read by OsmReader
     *
     * @param path path to OSM data .pbf file
     * @return road graph
     */
    @SuppressWarnings({"UnstableApiUsage"})
    public static GraphOSM CreateGraph(String path, String parametersPath) throws MapFilePathException {
        FileInputStream inputStream = null;
        Map<String, WayParameters> roadZooms = null;
        try {
            inputStream = new FileInputStream(path);
            String parametersJson = new String(Files.readAllBytes(Paths.get(parametersPath)));
            Type mapType = new TypeToken<Map<String, WayParameters>>() {
            }.getType();
            roadZooms = new Gson().fromJson(parametersJson, mapType);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new MapFilePathException(e.getMessage());
        }

        OsmReader myOsmReader = new OsmReader();
        OsmosisReader osmosisReader = new OsmosisReader(inputStream);
        osmosisReader.setSink(myOsmReader);
        osmosisReader.run();

        Map<Long, Node> allNodes = myOsmReader.getNodes();
        Map<Long, Way> allWays = myOsmReader.getWays();

        Map<String, NodeOSM> nodes = new HashMap<>();
        Map<String, WayOSM> ways = new HashMap<>();
        Map<NodeIdPairKey, String> nodeIdPairToWayIdMap = new HashMap<>();

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
            way.getWayNodes().stream().map(WayNode::getNodeId).map(allNodes::get)
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
        return new GraphOSM(nodes, ways, nodeIdPairToWayIdMap);
    }

    private static boolean checkIfClosed(Way way) {
        List<WayNode> wayNodes = way.getWayNodes();
        return wayNodes.get(0).getNodeId() == wayNodes.get(wayNodes.size() - 1).getNodeId();
    }

}
