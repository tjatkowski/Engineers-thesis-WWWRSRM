package pl.edu.agh.wwwrsrm.utils.window;

import lombok.Getter;
import lombok.Setter;
import pl.edu.agh.wwwrsrm.utils.Vec2D;
import pl.edu.agh.wwwrsrm.utils.constants.Zoom;
import pl.edu.agh.wwwrsrm.utils.coordinates.LonLatCoordinate;

@Getter
public class MapWindow extends AbstractDistanceCalculator {

    private static final int MIN_ZOOM_LEVEL = 1;
    private static final int MAX_ZOOM_LEVEL = 23;
    private static final double PI = Math.PI;
    private static final int R = 6378137;

    private LonLatCoordinate topLeftPoint;
    private LonLatCoordinate bottomRightPoint;

    @Setter
    private int windowWidth;
    @Setter
    private int windowHeight;

    private int zoomLevel;
    private GlobalMapWindow globalMapWindow;

    public MapWindow(LonLatCoordinate topLeftPoint, LonLatCoordinate bottomRightPoint, int windowWidth, int windowHeight) {
        this.topLeftPoint = topLeftPoint;
        this.bottomRightPoint = bottomRightPoint;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.zoomLevel = detectZoomLevel();
        this.globalMapWindow = new GlobalMapWindow(topLeftPoint.convertToGlobalXY(zoomLevel), bottomRightPoint.convertToGlobalXY(zoomLevel));
    }

    /**
     * Method which checks if LonLat coordinate is inside map window.
     */
    public boolean isInsideWindow(LonLatCoordinate coordinate) {
        return coordinate.getLongitude() >= this.topLeftPoint.getLongitude()
                && coordinate.getLatitude() <= this.topLeftPoint.getLatitude()
                && coordinate.getLongitude() <= this.bottomRightPoint.getLongitude()
                && coordinate.getLatitude() >= this.bottomRightPoint.getLatitude();
    }

    /**
     * Method which moves map boundaries.
     */
    public void dragMapWindowByVector(double xDelta, double yDelta) {
        double xPercentageShift = xDelta / windowWidth;
        double yPercentageShift = yDelta / windowHeight;
        globalMapWindow.dragMapWindowByPercentage(xPercentageShift, yPercentageShift);
        updateBoundaries();
    }

    /**
     * Method which zoom in and zoom out map boundaries.
     */
    public void zoomMapWindow(Zoom zoom) {
        switch (zoom) {
            case IN -> {
                if (zoomLevel + 1 <= MAX_ZOOM_LEVEL) {
                    zoomLevel++;
                } else {
                    return;
                }
            }
            case OUT -> {
                if (zoomLevel - 1 >= MIN_ZOOM_LEVEL) {
                    zoomLevel--;
                } else {
                    return;
                }
            }
            default -> throw new IllegalStateException("Unexpected value: " + zoom);
        }
        this.globalMapWindow = new GlobalMapWindow(topLeftPoint.convertToGlobalXY(zoomLevel), bottomRightPoint.convertToGlobalXY(zoomLevel));
        globalMapWindow.zoomMapWindow(zoom);
        updateBoundaries();
    }

    private void updateBoundaries() {
        this.topLeftPoint = globalMapWindow.getGlobalTopLeftPoint().convertToLonLatCoordinate(zoomLevel);
        this.bottomRightPoint = globalMapWindow.getGlobalBottomRightPoint().convertToLonLatCoordinate(zoomLevel);
    }

    private int detectZoomLevel() {
        double windowDiagonalInMeters = metersDistanceFromLonLat(topLeftPoint, bottomRightPoint);
        double windowDiagonal = distanceXY(new Vec2D(0, 0), new Vec2D(windowWidth, windowHeight));

        double minDistanceDelta = Double.MAX_VALUE;
        int zoomLevel = MIN_ZOOM_LEVEL;
        while (zoomLevel <= MAX_ZOOM_LEVEL) {
            double groundResolution = groundResolution(topLeftPoint.getLatitude(), zoomLevel);
            double calculatedWindowDiagonalInMeters = groundResolution * windowDiagonal;
            double distanceDelta = Math.abs(windowDiagonalInMeters - calculatedWindowDiagonalInMeters);
            if (distanceDelta < minDistanceDelta) {
                minDistanceDelta = distanceDelta;
                zoomLevel++;
            } else {
                return zoomLevel - 1;
            }
        }
        return zoomLevel;
    }
}
