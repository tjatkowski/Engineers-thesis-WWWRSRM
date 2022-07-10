package pl.edu.agh.wwwrsrm.utils.coordinates;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import pl.edu.agh.wwwrsrm.utils.window.MapWindow;

@Getter
@ToString
@RequiredArgsConstructor
public class LonLatCoordinate extends AbstractCoordinateConverter {

    private final double longitude;
    private final double latitude;

    public GlobalXYCoordinate convertToGlobalXY(int zoomLevel) {
        return new GlobalXYCoordinate(convertLongitudeToGlobalX(longitude, zoomLevel), (convertLatitudeToGlobalY(latitude, zoomLevel)));
    }

    public WindowXYCoordinate convertToWindowXY(MapWindow mapWindow) {
        GlobalXYCoordinate globalXYCoordinate = convertToGlobalXY(mapWindow.getZoomLevel());
        return new WindowXYCoordinate(convertGlobalXToWindowX(globalXYCoordinate.getX(), mapWindow),
                convertGlobalYToWindowY(globalXYCoordinate.getY(), mapWindow));
    }
}
