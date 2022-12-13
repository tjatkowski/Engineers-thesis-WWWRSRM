package pl.edu.agh.wwwrsrm.osm;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import crosby.binary.osmosis.OsmosisReader;
import org.openstreetmap.osmosis.core.domain.v0_6.Node;
import org.openstreetmap.osmosis.core.domain.v0_6.Tag;
import org.openstreetmap.osmosis.core.domain.v0_6.Way;
import org.openstreetmap.osmosis.core.domain.v0_6.WayNode;
import pl.edu.agh.wwwrsrm.graph.EdgeOSM;
import pl.edu.agh.wwwrsrm.graph.GraphOSM;
import pl.edu.agh.wwwrsrm.graph.NodeOSM;
import pl.edu.agh.wwwrsrm.graph.WayOSM;
import pl.edu.agh.wwwrsrm.utils.coordinates.LonLatCoordinate;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

/**
 * OsmParser class parses nodes and ways read by OsmReader and creates road graph.
 * Only ways with 'highway' key parameter and values indicating possibility of car driving on them are considered.
 */
public class OsmParser {

    /**
     * CreateGraph method creates road graph based on nodes and ways read by OsmReader
     *
     * @param path path to OSM data .pbf file
     * @return road graph
     */
    @SuppressWarnings({"UnstableApiUsage"})
    public static GraphOSM CreateGraph(String path, String parametersPath) {
        FileInputStream inputStream = null;
        Map<String, WayParameters> roadZooms = null;
        try {
            System.out.println(path);
            inputStream = new FileInputStream(path);
            String parametersJson = new String(Files.readAllBytes(Paths.get(parametersPath)));
            Type mapType = new TypeToken<Map<String, WayParameters>>() {
            }.getType();
            roadZooms = new Gson().fromJson(parametersJson, mapType);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        OsmReader myOsmReader = new OsmReader();
        OsmosisReader osmosisReader = new OsmosisReader(inputStream);
        osmosisReader.setSink(myOsmReader);
        osmosisReader.run();

        Map<Long, Node> allNodes = myOsmReader.getNodes();
        Map<Long, Way> allWays = myOsmReader.getWays();

        GraphOSM osm_graph = new GraphOSM();

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

            WayOSM osmWay;
            if (!wayParameters.getType().equals("highway") && WayOSM.checkIfClosed(way)) {
                osmWay = new WayOSM(true, wayParameters);
            } else {
                osmWay = new WayOSM(false, wayParameters);
            }

            way.getWayNodes().stream().map(WayNode::getNodeId).map(allNodes::get)
                    .map(node -> new NodeOSM(node.getId(), new LonLatCoordinate(node.getLongitude(), node.getLatitude())))
                    .reduce((node1, node2) -> {
                        osm_graph.addNode(node1);
                        osm_graph.addNode(node2);
                        osmWay.addEdge(new EdgeOSM(way.getId(), node1, node2));
                        return node2;
                    });
            osm_graph.addWay(osmWay);
        }
        return osm_graph;
    }

}
