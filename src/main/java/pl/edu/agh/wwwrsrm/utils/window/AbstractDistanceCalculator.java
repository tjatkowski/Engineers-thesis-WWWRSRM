package pl.edu.agh.wwwrsrm.utils.window;

import pl.edu.agh.wwwrsrm.utils.Vec2D;
import pl.edu.agh.wwwrsrm.utils.coordinates.LonLatCoordinate;

public abstract class AbstractDistanceCalculator {

    private static final double PI = Math.PI;
    private static final int R = 6378137;

    protected double distanceXY(Vec2D vec1, Vec2D vec2) {
        return Math.sqrt(Math.pow(vec2.getX() - vec1.getX(), 2) + Math.pow(vec2.getY() - vec1.getY(), 2));
    }

    protected double metersDistanceFromLonLat(LonLatCoordinate coordinate1, LonLatCoordinate coordinate2) {
        double lon1Radians = Math.toRadians(coordinate1.getLongitude());
        double lat1Radians = Math.toRadians(coordinate1.getLatitude());
        double lon2Radians = Math.toRadians(coordinate2.getLongitude());
        double lat2Radians = Math.toRadians(coordinate2.getLatitude());
        double deltaLongitude = lon2Radians - lon1Radians;
        double deltaLatitude = lat2Radians - lat1Radians;

        double a = Math.pow(Math.sin(deltaLatitude / 2), 2)
                + Math.cos(lat1Radians) * Math.cos(lat2Radians)
                * Math.pow(Math.sin(deltaLongitude / 2), 2);

        return 2 * R * Math.asin(Math.sqrt(a));
    }

    /**
     * ground resolution [meter / pixel]
     */
    public double groundResolution(double latitude, int zoomLevel) {
        int mapWidth = (int) (256 * Math.pow(2, zoomLevel));
        return (Math.cos((latitude * Math.PI) / 180) * 2 * PI * R) / mapWidth;
    }
}
