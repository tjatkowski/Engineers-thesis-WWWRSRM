package pl.edu.agh.wwwrsrm.utils.coordinates;

import pl.edu.agh.wwwrsrm.utils.window.MapWindow;

public abstract class AbstractCoordinateConverter {

    protected int convertLongitudeToGlobalX(double longitude, int zoomLevel) {
        return (int) (((longitude + 180) / 360) * 256 * Math.pow(2, zoomLevel));
    }

    protected int convertLatitudeToGlobalY(double latitude, int zoomLevel) {
        double sinLatitude = Math.sin(latitude * Math.PI / 180);
        return (int) ((0.5 - Math.log((1 + sinLatitude) / (1 - sinLatitude)) / (4 * Math.PI)) * 256 * Math.pow(2, zoomLevel));
    }

    protected double convertGlobalXToLongitude(int x, int zoomLevel) {
        return (((double)x / (256 * Math.pow(2, zoomLevel))) - 0.5) * 360;
    }

    protected double convertGlobalYToLatitude(int y, int zoomLevel) {
        return 90 - 360 * Math.atan(Math.exp(-(0.5 - ((double)y / (256 * Math.pow(2, zoomLevel)))) * 2 * Math.PI)) / Math.PI;
    }

    protected int convertGlobalXToWindowX(int x, MapWindow mapWindow) {
        double minGlobalX = mapWindow.getGlobalMapWindow().getGlobalTopLeftPoint().getX();
        double globalWindowWidth = mapWindow.getGlobalMapWindow().getGlobalWindowWidth();
        return (int) (((x - minGlobalX) / (globalWindowWidth)) * mapWindow.getWindowWidth());
    }

    protected int convertGlobalYToWindowY(double y, MapWindow mapWindow) {
        double minGlobalY = mapWindow.getGlobalMapWindow().getGlobalTopLeftPoint().getY();
        double globalWindowHeight = mapWindow.getGlobalMapWindow().getGlobalWindowHeight();
        return (int) (((y - minGlobalY) / (globalWindowHeight)) * mapWindow.getWindowHeight());
    }
}
