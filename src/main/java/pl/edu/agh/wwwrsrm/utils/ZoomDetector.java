package pl.edu.agh.wwwrsrm.utils;

import javafx.geometry.Point2D;

/**
 * ZoomDetector class detect zoom level when map is loaded.
 */
public class ZoomDetector {
    private static final double PI = Math.PI;
    private static final int R = 6378137;
    private static final int MIN_ZOOM_LEVEL = 1;
    private static final int MAX_ZOOM_LEVEL = 23;

    public static int getZoomLevel(Point2D minBound, Point2D maxBound, int mapWidth, int mapHeight){
        double longitude1 = minBound.getX();
        double latitude1 = minBound.getY();
        double longitude2 = maxBound.getX();
        double latitude2 = maxBound.getY();

        double distanceInMeters = distanceFromDegrees(longitude1, latitude1, longitude2, latitude2);
        double pixelsDistance = Math.sqrt(Math.pow(mapWidth, 2) + Math.pow(mapHeight, 2));

        double minDistanceDelta = Double.MAX_VALUE;
        int zoomLevel = MIN_ZOOM_LEVEL;
        while (zoomLevel <= MAX_ZOOM_LEVEL){
            double groundResolution = groundResolution(latitude1, zoomLevel);
            double distanceDelta = Math.abs(distanceInMeters - (groundResolution * pixelsDistance));
            if (distanceDelta < minDistanceDelta){
                minDistanceDelta = distanceDelta;
                zoomLevel++;
            } else{
                return zoomLevel - 1;
            }
        }
        return zoomLevel;
    }

    public static double distanceFromDegrees(double longitude1, double latitude1, double longitude2, double latitude2){
        double lon1Radians = Math.toRadians(longitude1);
        double lat1Radians = Math.toRadians(latitude1);
        double lon2Radians = Math.toRadians(longitude2);
        double lat2Radians = Math.toRadians(latitude2);
        double deltaLongitude = lon2Radians - lon1Radians;
        double deltaLatitude = lat2Radians - lat1Radians;

        double a = Math.pow(Math.sin(deltaLatitude / 2), 2)
                + Math.cos(lat1Radians) * Math.cos(lat2Radians)
                * Math.pow(Math.sin(deltaLongitude / 2),2);

        return 2 * R * Math.asin(Math.sqrt(a)); // distance in meters
    }

    public static double groundResolution(double latitude, int zoomLevel){
        int mapWidth = (int) (256 * Math.pow(2, zoomLevel));
        return (Math.cos((latitude * Math.PI) / 180) * 2 * PI * R) / mapWidth;  // [meters/pixel]
    }

    public static double pixelsDistance(double longitude1, double latitude1, double longitude2, double latitude2, int zoomLevel){
        double minBoundX = CoordinatesConverter.convertLongitudeToX(longitude1, zoomLevel);
        double minBoundY = CoordinatesConverter.convertLatitudeToY(latitude1, zoomLevel);
        double maxBoundX = CoordinatesConverter.convertLongitudeToX(longitude2, zoomLevel);
        double maxBoundY = CoordinatesConverter.convertLatitudeToY(latitude2, zoomLevel);

        return Math.sqrt(Math.pow(maxBoundX - minBoundX, 2) + Math.pow(maxBoundY - minBoundY, 2));
    }
}
