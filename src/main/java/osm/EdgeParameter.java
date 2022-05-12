package osm;

import javafx.scene.paint.Color;

public class EdgeParameter {
    private final int zoomLevel;
    private final int edgeWidth;
    private final Color color;

    public EdgeParameter(int zoomLevel, int edgeWidth, Color color){
        this.zoomLevel = zoomLevel;
        this.edgeWidth = edgeWidth;
        this.color = color;
    }

    public int getZoomLevel() {
        return this.zoomLevel;
    }

    public int getEdgeWidth() {
        return this.edgeWidth;
    }

    public Color getColor() {
        return this.color;
    }
}
