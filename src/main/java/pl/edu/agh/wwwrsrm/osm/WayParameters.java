package pl.edu.agh.wwwrsrm.osm;

import javafx.scene.paint.Color;

public class WayParameters {
    private final int zoomLevel;
    private final int wayWidth;
    private final Color color;
    private final String type;

    public WayParameters(int zoomLevel, int wayWidth, Color color, String type) {
        this.zoomLevel = zoomLevel;
        this.wayWidth = wayWidth;
        this.color = color;
        this.type = type;
    }

    public int getZoomLevel() {
        return this.zoomLevel;
    }

    public int getWayWidth() {
        return this.wayWidth;
    }

    public Color getColor() {
        return this.color;
    }

    public String getType() {
        return this.type;
    }
}
