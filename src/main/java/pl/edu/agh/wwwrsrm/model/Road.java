package pl.edu.agh.wwwrsrm.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import pl.edu.agh.wwwrsrm.graph.WayOSM;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class Road {

    private final String wayId;

    private int density = 0;

    public Road(WayOSM wayOSM) {
        this.wayId = wayOSM.getWayId();
    }

    public void increaseDensity() {
        density++;
    }

    public void clearDensity() {
        density = 0;
    }

}
