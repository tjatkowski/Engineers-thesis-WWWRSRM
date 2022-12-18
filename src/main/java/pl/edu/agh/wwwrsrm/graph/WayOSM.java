package pl.edu.agh.wwwrsrm.graph;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.edu.agh.wwwrsrm.osm.WayParameters;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class WayOSM {

    private final Long wayId;
    private final List<EdgeOSM> edges;
    private final WayParameters edgeParameter;
    private final boolean isClosed;

}
