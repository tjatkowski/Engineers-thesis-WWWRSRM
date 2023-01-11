package pl.edu.agh.wwwrsrm.parser;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import pl.edu.agh.wwwrsrm.exceptions.MapFilePathException;
import pl.edu.agh.wwwrsrm.graph.GraphOSM;
import pl.edu.agh.wwwrsrm.graph.NodeIdPairKey;
import pl.edu.agh.wwwrsrm.graph.NodeOSM;
import pl.edu.agh.wwwrsrm.graph.WayOSM;
import pl.edu.agh.wwwrsrm.parser.osm.OsmFormatReader;
import pl.edu.agh.wwwrsrm.parser.parameters.WayParameters;
import pl.edu.agh.wwwrsrm.parser.pbf.PbfFormatReader;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Parser class parses nodes and ways read by PbfFormatSink or OsmFormatReader and creates road graph.
 */
@Slf4j
public class Parser {

    /**
     * CreateGraph method creates road graph based on nodes and ways read by PbfFormatSink or OsmFormatReader
     *
     * @param path path to OSM data .pbf file
     * @return road graph
     */
    @SuppressWarnings({"UnstableApiUsage"})
    public static GraphOSM CreateGraph(String path, String parametersPath) throws MapFilePathException {
        if (path == null) {
            throw new MapFilePathException("Path is null");
        }
        InputStream is;
        Map<String, WayParameters> roadZooms;
        try {
            is = Files.newInputStream(Path.of(path));
            String parametersJson = new String(Files.readAllBytes(Paths.get(parametersPath)));
            Type mapType = new TypeToken<Map<String, WayParameters>>() {}.getType();
            roadZooms = new Gson().fromJson(parametersJson, mapType);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new MapFilePathException(e.getMessage());
        }

        String format = StringUtils.substringAfterLast(path, ".");

        if (!"pbf".equals(format) && !"osm".equals(format)) {
            throw new MapFilePathException("Map not in format .pbf or .osm");
        }

        Map<String, NodeOSM> nodes = new HashMap<>();
        Map<String, WayOSM> ways = new HashMap<>();
        Map<NodeIdPairKey, String> nodeIdPairToWayIdMap = new HashMap<>();
        if ("pbf".equals(format)) {
            PbfFormatReader.read(is, nodes, ways, nodeIdPairToWayIdMap, roadZooms);
        } else {
            OsmFormatReader.read(is, nodes, ways, nodeIdPairToWayIdMap, roadZooms);
        }
        return new GraphOSM(nodes, ways, nodeIdPairToWayIdMap);
    }

}
