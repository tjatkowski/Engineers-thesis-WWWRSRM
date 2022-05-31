package pl.edu.agh.wwwrsrm.utils;

import javafx.geometry.Point2D;

public interface CoordinatesConverter {

    static double convertLongitudeToX(double longitude, int zoomLevel) {
        return ((longitude + 180) / 360) * 256 * Math.pow(2, zoomLevel);
    }

    static double convertLatitudeToY(double latitude, int zoomLevel) {
        double sinLatitude = Math.sin(latitude * Math.PI / 180);
        return (0.5 - Math.log((1 + sinLatitude) / (1 - sinLatitude)) / (4 * Math.PI)) * 256 * Math.pow(2, zoomLevel);
    }

    static double convertXToLongitude(double x, int zoomLevel) {
        return ((x / (256 * Math.pow(2, zoomLevel))) - 0.5) * 360;
    }

    static double convertYToLatitude(double y, int zoomLevel) {
        return 90 - 360 * Math.atan(Math.exp(-(0.5 - (y / (256 * Math.pow(2, zoomLevel)))) * 2 * Math.PI)) / Math.PI;
    }

    /**
     * Method which scales X global coordinate to window size
     *
     * @param x global coordinate
     * @return x view local coordinate
     */
    static double scaleXToFitWindow(double x, Point2D minBound, Point2D maxBound, int mapWidth, int zoomLevel) {
        double minBoundX = convertLongitudeToX(minBound.getX(), zoomLevel);
        double maxBoundX = convertLongitudeToX(maxBound.getX(), zoomLevel);
        return ((x - minBoundX) / (maxBoundX - minBoundX)) * mapWidth;
    }

    /**
     * Method which scales Y global coordinate to window size
     *
     * @param y global coordinate
     * @return y view local coordinate
     */
    static double scaleYToFitWindow(double y, Point2D minBound, Point2D maxBound, int mapHeight, int zoomLevel) {
        double minBoundY = convertLatitudeToY(minBound.getY(), zoomLevel);
        double maxBoundY = convertLatitudeToY(maxBound.getY(), zoomLevel);
        return ((y - minBoundY) / (maxBoundY - minBoundY)) * mapHeight;
    }
}
