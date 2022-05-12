package osm;

import crosby.binary.osmosis.OsmosisReader;
import graph.OSM_Edge;
import graph.OSM_Graph;
import graph.OSM_Node;
import javafx.scene.paint.Color;
import org.openstreetmap.osmosis.core.domain.v0_6.Node;
import org.openstreetmap.osmosis.core.domain.v0_6.Tag;
import org.openstreetmap.osmosis.core.domain.v0_6.Way;
import org.openstreetmap.osmosis.core.domain.v0_6.WayNode;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Map;

/**
 * OSM_Parser class parses nodes and ways read by OsmReader and creates road graph.
 * Only ways with 'highway' key parameter and values indicating possibility of car driving on them are considered.
 */
public class OSM_Parser {
    public static final Map<String, EdgeParameter> roadZooms = Map.ofEntries(
            // roads
            Map.entry("motorway", new EdgeParameter(5, 3, Color.SANDYBROWN)),
            Map.entry("trunk", new EdgeParameter(9, 3, Color.SANDYBROWN)),
            Map.entry("primary", new EdgeParameter(10, 3, Color.SANDYBROWN)),
            Map.entry("secondary", new EdgeParameter(11, 3, Color.SANDYBROWN)),
            Map.entry("tertiary", new EdgeParameter(12, 3, Color.SANDYBROWN)),
            Map.entry("unclassified", new EdgeParameter(14, 3, Color.SANDYBROWN)),
            Map.entry("residential", new EdgeParameter(15, 3, Color.SANDYBROWN)),
            // link roads
            Map.entry("motorway_link", new EdgeParameter(11, 3, Color.SALMON)),
            Map.entry("trunk_link", new EdgeParameter(12, 3, Color.SALMON)),
            Map.entry("primary_link", new EdgeParameter(13, 3, Color.SALMON)),
            Map.entry("secondary_link", new EdgeParameter(14, 3, Color.SALMON)),
            Map.entry("tertiary_link", new EdgeParameter(15, 3, Color.SALMON)),
            // special roads
            Map.entry("living_street", new EdgeParameter(17, 3, Color.PAPAYAWHIP)),
            Map.entry("service", new EdgeParameter(16, 3, Color.PAPAYAWHIP)),
            Map.entry("pedestrian", new EdgeParameter(16, 3, Color.PAPAYAWHIP)),
            Map.entry("track", new EdgeParameter(15, 3, Color.PAPAYAWHIP)),
            Map.entry("bus_guideway", new EdgeParameter(16, 3, Color.PAPAYAWHIP)),
            Map.entry("escape", new EdgeParameter(16, 3, Color.PAPAYAWHIP)),
            Map.entry("raceway", new EdgeParameter(16, 3, Color.PAPAYAWHIP)),
            Map.entry("road", new EdgeParameter(16, 3, Color.PAPAYAWHIP)),
            Map.entry("busway", new EdgeParameter(13, 3, Color.PAPAYAWHIP))
    );

    /**
     * CreateGraph method creates road graph based on nodes and ways read by OsmReader
     *
     * @param path path to OSM data .pbf file
     * @return road graph
     */
    public static OSM_Graph CreateGraph(String path) {
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

        OSM_Graph osm_graph = new OSM_Graph();

        for (Way way : allWays.values()) {
            Collection<Tag> wayTags = way.getTags();
            String roadType = way.getTags().stream()
                    .filter(entry -> entry.getKey().equals("highway") && roadZooms.containsKey(entry.getValue()))
                    .map(Tag::getValue)
                    .findAny()
                    .orElse(null);

            if (roadType == null) {
                continue;
            }

            way.getWayNodes().stream().map(WayNode::getNodeId).map(allNodes::get).map(OSM_Node::new)
                    .reduce((node1, node2) -> {
                        osm_graph.addNode(node1);
                        osm_graph.addNode(node2);
                        osm_graph.addEdge(new OSM_Edge(way.getId(), node1, node2, roadZooms.get(roadType)));
                        return node2;
                    });
        }
        return osm_graph;
    }
}
