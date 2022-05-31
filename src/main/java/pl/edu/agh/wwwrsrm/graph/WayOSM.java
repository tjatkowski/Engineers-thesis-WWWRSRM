package pl.edu.agh.wwwrsrm.graph;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.openstreetmap.osmosis.core.domain.v0_6.Way;
import org.openstreetmap.osmosis.core.domain.v0_6.WayNode;
import pl.edu.agh.wwwrsrm.osm.WayParameters;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class WayOSM {
    private final List<EdgeOSM> edges = new ArrayList<>();
    private final boolean isClosed;
    private final WayParameters edgeParameter;

    public static boolean checkIfClosed(Way way) {
        List<WayNode> wayNodes = way.getWayNodes();
        return wayNodes.get(0).getNodeId() == wayNodes.get(wayNodes.size() - 1).getNodeId();
    }

    public void addEdge(EdgeOSM edge) {
        this.edges.add(edge);
    }
}
