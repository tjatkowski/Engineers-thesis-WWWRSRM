package pl.edu.agh.wwwrsrm.parser.pbf;


import lombok.Getter;
import org.openstreetmap.osmosis.core.container.v0_6.EntityContainer;
import org.openstreetmap.osmosis.core.container.v0_6.NodeContainer;
import org.openstreetmap.osmosis.core.container.v0_6.WayContainer;
import org.openstreetmap.osmosis.core.domain.v0_6.Node;
import org.openstreetmap.osmosis.core.domain.v0_6.Way;
import org.openstreetmap.osmosis.core.task.v0_6.Sink;

import java.util.HashMap;
import java.util.Map;

/**
 * PbfFormatSink class is implementing Sink interface of the osmosis library.
 * It reads OSM data in .pbf format and stores all the nodes and ways which
 * can be accesses by getNodes and gedWays methods.
 */
@Getter
public class PbfFormatSink implements Sink {
    private final Map<Long, Node> nodes = new HashMap<>();
    private final Map<Long, Way> ways = new HashMap<>();


    @Override
    public void process(EntityContainer entityContainer) {
        if (entityContainer instanceof NodeContainer) {
            Node node = ((NodeContainer) entityContainer).getEntity();
            this.nodes.put(node.getId(), node);
        } else if (entityContainer instanceof WayContainer) {
            Way way = ((WayContainer) entityContainer).getEntity();
            this.ways.put(way.getId(), way);
        }
    }

    @Override
    public void initialize(Map<String, Object> map) {
    }

    @Override
    public void complete() {
    }

    @Override
    public void close() {
    }


}
