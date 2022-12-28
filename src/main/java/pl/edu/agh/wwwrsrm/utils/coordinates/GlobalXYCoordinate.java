package pl.edu.agh.wwwrsrm.utils.coordinates;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import pl.edu.agh.wwwrsrm.utils.Vec2D;

@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
@RequiredArgsConstructor
public class GlobalXYCoordinate extends AbstractCoordinateConverter {

    private final int x;
    private final int y;

    public GlobalXYCoordinate add(Vec2D vec2D) {
        return new GlobalXYCoordinate(x + vec2D.getX(), y + vec2D.getY());
    }

    public GlobalXYCoordinate subtract(Vec2D vec2D) {
        return new GlobalXYCoordinate(x - vec2D.getX(), y - vec2D.getY());
    }

    public LonLatCoordinate convertToLonLatCoordinate(int zoomLevel) {
        return new LonLatCoordinate(convertGlobalXToLongitude(x, zoomLevel), convertGlobalYToLatitude(y, zoomLevel));
    }
}
