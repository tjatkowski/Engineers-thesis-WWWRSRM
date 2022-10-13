package pl.edu.agh.wwwrsrm.osm;

import crosby.binary.osmosis.OsmosisReader;
import pl.edu.agh.wwwrsrm.graph.EdgeOSM;
import pl.edu.agh.wwwrsrm.graph.GraphOSM;
import pl.edu.agh.wwwrsrm.graph.NodeOSM;
import pl.edu.agh.wwwrsrm.graph.WayOSM;
import javafx.scene.paint.Color;
import org.openstreetmap.osmosis.core.domain.v0_6.Node;
import org.openstreetmap.osmosis.core.domain.v0_6.Tag;
import org.openstreetmap.osmosis.core.domain.v0_6.Way;
import org.openstreetmap.osmosis.core.domain.v0_6.WayNode;
import pl.edu.agh.wwwrsrm.utils.coordinates.LonLatCoordinate;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

/**
 * osmParser class parses nodes and ways read by OsmReader and creates road graph.
 * Only ways with 'highway' key parameter and values indicating possibility of car driving on them are considered.
 */
public class osmParser {
    public static final Map<String, WayParameters> roadZooms = Map.ofEntries(
            // roads
            Map.entry("motorway", new WayParameters(5, 30, Color.WHITE, "highway")),
            Map.entry("trunk", new WayParameters(9, 8, Color.WHITE, "highway")),
            Map.entry("primary", new WayParameters(10, 20, Color.WHITE, "highway")),
            Map.entry("secondary", new WayParameters(11, 17, Color.WHITE, "highway")),
            Map.entry("tertiary", new WayParameters(12, 13, Color.WHITE, "highway")),
            Map.entry("unclassified", new WayParameters(14, 8, Color.WHITE, "highway")),
            Map.entry("residential", new WayParameters(15, 8, Color.WHITE, "highway")),
            // link roads
            Map.entry("motorway_link", new WayParameters(11, 30, Color.WHITE, "highway")),
            Map.entry("trunk_link", new WayParameters(12, 8, Color.WHITE, "highway")),
            Map.entry("primary_link", new WayParameters(13, 20, Color.WHITE, "highway")),
            Map.entry("secondary_link", new WayParameters(14, 17, Color.WHITE, "highway")),
            Map.entry("tertiary_link", new WayParameters(15, 13, Color.WHITE, "highway")),
            // special roads
//            Map.entry("living_street", new WayParameters(17, 10, Color.PAPAYAWHIP, "highway")),
//            Map.entry("service", new WayParameters(16, 10, Color.PAPAYAWHIP, "highway")),
//            Map.entry("pedestrian", new WayParameters(16, 10, Color.PAPAYAWHIP, "highway")),
//            Map.entry("track", new WayParameters(15, 10, Color.PAPAYAWHIP, "highway")),
//            Map.entry("bus_guideway", new WayParameters(16, 10, Color.PAPAYAWHIP, "highway")),
//            Map.entry("escape", new WayParameters(16, 10, Color.PAPAYAWHIP, "highway")),
//            Map.entry("raceway", new WayParameters(16, 10, Color.PAPAYAWHIP, "highway")),
//            Map.entry("road", new WayParameters(16, 10, Color.PAPAYAWHIP, "highway")),
//            Map.entry("busway", new WayParameters(13, 10, Color.PAPAYAWHIP, "highway")),
//            // waterway
            Map.entry("river", new WayParameters(10, 60, Color.BLUE, "waterway")),
//            Map.entry("stream", new WayParameters(14, 15, Color.RED, "waterway"))
//            // accomodation
            Map.entry("house", new WayParameters(15, 5, Color.DARKGREY, "building")),
            Map.entry("apartments", new WayParameters(15, 5, Color.DARKGREY, "building")),
            Map.entry("barracks", new WayParameters(15, 5, Color.DARKGREY, "building")),
            Map.entry("bungalow", new WayParameters(15, 5, Color.DARKGREY, "building")),
            Map.entry("cabin", new WayParameters(15, 5, Color.DARKGREY, "building")),
            Map.entry("detached", new WayParameters(15, 5, Color.DARKGREY, "building")),
            Map.entry("dormitory", new WayParameters(15, 5, Color.DARKGREY, "building")),
            Map.entry("farm", new WayParameters(15, 5, Color.DARKGREY, "building")),
            Map.entry("hotel", new WayParameters(15, 5, Color.DARKGREY, "building"))
//            // commercial
////            Map.entry("commercial", new WayParameters(15, 3, Color.BURLYWOOD, "building")),
//            Map.entry("industrial", new WayParameters(15, 3, Color.DARKGREY, "building")),
//            Map.entry("kiosk", new WayParameters(15, 3, Color.DARKGREY, "building")),
//            Map.entry("office", new WayParameters(14, 3, Color.DARKGREY, "building")),
//            Map.entry("retail", new WayParameters(14, 3, Color.DARKGREY, "building")),
//            Map.entry("supermarket", new WayParameters(13, 3, Color.DARKGREY, "building")),
//            Map.entry("warehouse", new WayParameters(13, 3, Color.DARKGREY, "building"))
    );

    /**
     * CreateGraph method creates road graph based on nodes and ways read by OsmReader
     *
     * @param path path to OSM data .pbf file
     * @return road graph
     */
    public static GraphOSM CreateGraph(String path) {
        FileInputStream inputStream = null;
        try {
            System.out.println(path);
            inputStream = new FileInputStream(path);
        } catch (FileNotFoundException e) {
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
