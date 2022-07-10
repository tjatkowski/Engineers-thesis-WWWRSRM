package pl.edu.agh.wwwrsrm.utils.coordinates;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class WindowXYCoordinate extends AbstractCoordinateConverter {

    private final int x;
    private final int y;

}
