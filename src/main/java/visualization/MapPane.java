package visualization;

import graph.OSM_Edge;
import graph.OSM_Graph;
import graph.OSM_Node;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import utils.CoordinatesConverter;
import static utils.ZoomDetector.getZoomLevel;
/**
 * MapPane class does visualization of the road graph
 */
public class MapPane extends Pane {
    private OSM_Graph osm_graph;
    private Point2D minBound;
    private Point2D maxBound;
    private static final int MAP_WIDTH = 1280;
    private static final int MAP_HEIGHT = 720;
    private static final int MIN_ZOOM_LEVEL = 1;
    private static final int MAX_ZOOM_LEVEL = 23;
    private int zoomLevel;
    private final GraphicsContext gc;
    private final Canvas map;

    public MapPane() {
        this.setPrefSize(MAP_WIDTH, MAP_HEIGHT);
        this.setStyle("-fx-background-color: #808080;");
        this.setEventHandler(MouseEvent.ANY, new MapDraggingHandler(this));
        this.setEventHandler(ScrollEvent.ANY, new MapZoomHandler(this));
        this.map = new Canvas(MAP_WIDTH, MAP_HEIGHT);
        this.gc = map.getGraphicsContext2D();
    }

    public MapPane(OSM_Graph osm_graph) {
        this();
        this.osm_graph = osm_graph;
        this.minBound = this.osm_graph.getTopLeftBound();
        this.maxBound = this.osm_graph.getBottomRightBound();
        this.zoomLevel = getZoomLevel(this.minBound, this.maxBound, MAP_WIDTH, MAP_HEIGHT);
    }

    /**
     * drawLines method draws all the road graph edges on the MapPane
     */
    public void drawLines() {
        this.gc.clearRect(0, 0, MAP_WIDTH, MAP_HEIGHT);
        for (OSM_Edge osm_edge : this.osm_graph.getEdges()) {
            String roadType = osm_edge.getRoadType();
            int edgeMinZoomLevel = 0;
            switch (roadType){
                case "road":
                    edgeMinZoomLevel = MIN_ZOOM_LEVEL;
                    break;
                case "linkRoad":
                    edgeMinZoomLevel = 16;
                    break;
                case "specialRoad":
                    edgeMinZoomLevel = 16;
                    break;
            }
            if (edgeMinZoomLevel > this.zoomLevel){
                continue;
            }

            OSM_Node startNode = osm_edge.getStartNode();
            OSM_Node endNode = osm_edge.getEndNode();
            if (this.isInsideWindow(startNode) || this.isInsideWindow(endNode)) {
                double startNodeX = CoordinatesConverter.convertLongitudeToX(startNode.getLongitude(), this.zoomLevel);
                double startNodeY = CoordinatesConverter.convertLatitudeToY(startNode.getLatitude(), this.zoomLevel);
                double endNodeX = CoordinatesConverter.convertLongitudeToX(endNode.getLongitude(), this.zoomLevel);
                double endNodeY = CoordinatesConverter.convertLatitudeToY(endNode.getLatitude(), this.zoomLevel);

                // drawing in canvas
                this.drawLineInCanvas(this.map, startNodeX, startNodeY, endNodeX, endNodeY, roadType);
            }
        }
        this.getChildren().add(this.map);
    }

    public void drawLineInCanvas(Canvas map, double startX, double startY, double endX, double endY, String roadType){
        double x1 = CoordinatesConverter.scaleXToFitWindow(startX, this.minBound, this.maxBound, MAP_WIDTH, this.zoomLevel);
        double y1 = CoordinatesConverter.scaleYToFitWindow(startY, this.minBound, this.maxBound, MAP_HEIGHT, this.zoomLevel);
        double x2 = CoordinatesConverter.scaleXToFitWindow(endX, this.minBound, this.maxBound, MAP_WIDTH, this.zoomLevel);
        double y2 = CoordinatesConverter.scaleYToFitWindow(endY, this.minBound, this.maxBound, MAP_HEIGHT, this.zoomLevel);

        switch (roadType) {
            case "road":
                this.gc.setStroke(Color.SANDYBROWN);
                this.gc.setLineWidth(3);
                break;
            case "linkRoad":
                this.gc.setStroke(Color.SALMON);
                this.gc.setLineWidth(2);
                break;
            case "specialRoad":
                this.gc.setStroke(Color.PAPAYAWHIP);
                this.gc.setLineWidth(1);
                break;
        }
        this.gc.strokeLine(x1, y1, x2, y2);
    }

    /**
     * Util method which checks if node in degree coordinates is inside map boundaries.
     *
     * @param osm_node OSM_Node
     * @return true if node is inside map boundaries
     */
    public boolean isInsideWindow(OSM_Node osm_node) {
        return osm_node.getLongitude() >= this.minBound.getX()
                && osm_node.getLatitude() <= this.minBound.getY()
                && osm_node.getLongitude() <= this.maxBound.getX()
                && osm_node.getLatitude() >= this.maxBound.getY();
    }

    /**
     * Method which moves map boundaries.
     *
     * @param xDelta x delta in XY coordinates
     * @param yDelta y delta in XY coordinates
     */
    public void dragMapViewByVector(double xDelta, double yDelta) {
        double xPercentageShift = xDelta / MAP_WIDTH;
        double yPercentageShift = yDelta / MAP_HEIGHT;

        double minBoundX = CoordinatesConverter.convertLongitudeToX(this.minBound.getX(), this.zoomLevel);
        double minBoundY = CoordinatesConverter.convertLatitudeToY(this.minBound.getY(), this.zoomLevel);
        double maxBoundX = CoordinatesConverter.convertLongitudeToX(this.maxBound.getX(), this.zoomLevel);
        double maxBoundY = CoordinatesConverter.convertLatitudeToY(this.maxBound.getY(), this.zoomLevel);

        double viewWidthInPixels = maxBoundX - minBoundX;
        double viewHeightInPixels = maxBoundY - minBoundY;

        minBoundX -= xPercentageShift * viewWidthInPixels;
        minBoundY -= yPercentageShift * viewHeightInPixels;
        maxBoundX -= xPercentageShift * viewWidthInPixels;
        maxBoundY -= yPercentageShift * viewHeightInPixels;

        this.setMinBound(minBoundX, minBoundY);
        this.setMaxBound(maxBoundX, maxBoundY);

        this.getChildren().clear();
        this.drawLines();
    }


    /**
     * Method which zoom in and zoom out map boundaries.
     *
     * @param zoomSign if zooming in : 1 else -1
     */
    public void zoomMapView(int zoomSign) {
        int newZoomLevel = this.zoomLevel + zoomSign;
        if (newZoomLevel >= MIN_ZOOM_LEVEL && newZoomLevel <= MAX_ZOOM_LEVEL) {
            this.zoomLevel = newZoomLevel;
        } else {
            return;
        }

        double minBoundX = CoordinatesConverter.convertLongitudeToX(this.minBound.getX(), this.zoomLevel);
        double minBoundY = CoordinatesConverter.convertLatitudeToY(this.minBound.getY(), this.zoomLevel);
        double maxBoundX = CoordinatesConverter.convertLongitudeToX(this.maxBound.getX(), this.zoomLevel);
        double maxBoundY = CoordinatesConverter.convertLatitudeToY(this.maxBound.getY(), this.zoomLevel);

        double viewWidthInPixels = maxBoundX - minBoundX;
        double viewHeightInPixels = maxBoundY - minBoundY;

        if (zoomSign < 0) {
            minBoundX -= viewWidthInPixels / 2;
            minBoundY -= viewHeightInPixels / 2;
            maxBoundX += viewWidthInPixels / 2;
            maxBoundY += viewHeightInPixels / 2;
        } else {
            minBoundX += viewWidthInPixels / 4;
            minBoundY += viewHeightInPixels / 4;
            maxBoundX -= viewWidthInPixels / 4;
            maxBoundY -= viewHeightInPixels / 4;
        }

        this.setMinBound(minBoundX, minBoundY);
        this.setMaxBound(maxBoundX, maxBoundY);

        this.getChildren().clear();
        this.drawLines();
    }

    /**
     * drawNodes method draws all the road graph nodes on the MapPane
     */
    public void drawNodes() {
        for (OSM_Node node : this.osm_graph.getNodes().values()) {
            double nodeX = CoordinatesConverter.convertLongitudeToX(node.getLongitude(), this.zoomLevel);
            double nodeY = CoordinatesConverter.convertLatitudeToY(node.getLatitude(), this.zoomLevel);
            this.drawNode(nodeX, nodeY);
        }
    }

    /**
     * drawNode method draws one node
     *
     * @param x node x coordinate
     * @param y node y coordinate
     */
    public void drawNode(double x, double y) {
        double x1 = CoordinatesConverter.scaleXToFitWindow(x, this.minBound, this.maxBound, MAP_WIDTH, this.zoomLevel);
        double y1 = CoordinatesConverter.scaleYToFitWindow(y, this.minBound, this.maxBound, MAP_HEIGHT, this.zoomLevel);
        Rectangle rectangle = new Rectangle(x1, y1, 5, 5);
        this.getChildren().add(rectangle);
    }


    private void setMinBound(double minBoundX, double minBoundY) {
        double newMinBoundLongitude = CoordinatesConverter.convertXToLongitude(minBoundX, this.zoomLevel);
        double newMinBoundLatitude = CoordinatesConverter.convertYToLatitude(minBoundY, this.zoomLevel);
        this.minBound = new Point2D(newMinBoundLongitude, newMinBoundLatitude);
    }


    private void setMaxBound(double maxBoundX, double maxBoundY) {
        double newMaxBoundLongitude = CoordinatesConverter.convertXToLongitude(maxBoundX, this.zoomLevel);
        double newMaxBoundLatitude = CoordinatesConverter.convertYToLatitude(maxBoundY, this.zoomLevel);
        this.maxBound = new Point2D(newMaxBoundLongitude, newMaxBoundLatitude);
    }
}
