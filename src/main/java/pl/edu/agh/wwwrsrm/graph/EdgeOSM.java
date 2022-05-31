package pl.edu.agh.wwwrsrm.graph;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * EdgeOSM class implements road graph edge
 */
@Getter
@RequiredArgsConstructor
public class EdgeOSM {
    private final long id;
    private final NodeOSM startNode;
    private final NodeOSM endNode;
}
