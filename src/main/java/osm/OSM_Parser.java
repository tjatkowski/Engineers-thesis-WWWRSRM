package osm;

import crosby.binary.osmosis.OsmosisReader;
import graph.OSM_Edge;
import graph.OSM_Graph;
import graph.OSM_Node;
import org.openstreetmap.osmosis.core.domain.v0_6.Node;
import org.openstreetmap.osmosis.core.domain.v0_6.Tag;
import org.openstreetmap.osmosis.core.domain.v0_6.Way;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * OSM_Parser class parses nodes and ways read by OsmReader and creates road graph.
 * Only ways with 'highway' key parameter and values indicating possibility of car driving on them are considered.
 */
public class OSM_Parser {
    /**
     * main road types
     */
    public static Set<String> roadTypes = new HashSet<>(Arrays.asList("motorway", "trunk", "primary", "secondary",
            "tertiary", "unclassified", "residential"));
    /**
     * link road types
     */
    public static Set<String> linkRoadTypes = new HashSet<>(Arrays.asList("motorway_link", "trunk_link",
            "primary_link", "secondary_link", "tertiary_link"));
    /**
     * special road types
     */
    public static Set<String> specialRoadTypes = new HashSet<>(Arrays.asList("living_street", "service", "pedestrian",
            "track", "bus_guideway", "escape", "raceway", "road", "busway"));

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
            for (Tag tag : wayTags) {
                if (tag.getKey().equals("highway")) {
                    String roadType;
                    if (roadTypes.contains(tag.getValue())) {
                        roadType = "road";
                    } else if (linkRoadTypes.contains(tag.getValue())) {
                        roadType = "linkRoad";
                    } else if (specialRoadTypes.contains(tag.getValue())) {
                        roadType = "specialRoad";
                    } else {
                        break;
                    }
                    OSM_Node firstNode = new OSM_Node(allNodes.get(way.getWayNodes().get(0).getNodeId()));
                    osm_graph.addNode(firstNode);
                    for (int i = 1; i < way.getWayNodes().size(); i++) {
                        OSM_Node secondNode = new OSM_Node(allNodes.get(way.getWayNodes().get(i).getNodeId()));
                        osm_graph.addEdge(new OSM_Edge(way.getId(), firstNode, secondNode, roadType));
                        osm_graph.addNode(secondNode);
                        firstNode = secondNode;
                    }
                }
            }
        }
        return osm_graph;
    }
}
