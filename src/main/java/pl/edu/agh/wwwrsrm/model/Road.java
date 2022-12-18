package pl.edu.agh.wwwrsrm.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Road {
    int density;
    long node1Id;
    long node2Id;
    long wayId;

    public void increaseDensity() {
        density++;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (!(o instanceof Road road))
            return false;

        return node1Id == road.node1Id && node2Id == road.node2Id;
    }
}
