package pl.edu.agh.wwwrsrm.graph;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.edu.agh.wwwrsrm.parser.parameters.WayParameters;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class WayOSM {

    private final String wayId;
    private final List<EdgeOSM> edges;
    private final WayParameters wayParameters;
    private final boolean isClosed;

}
