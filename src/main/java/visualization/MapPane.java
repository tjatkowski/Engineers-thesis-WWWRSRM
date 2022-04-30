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

/**
 * MapPane class does visualization of the road graph
 */
public class MapPane extends Pane {
    private OSM_Graph osm_graph;
    private Point2D minBound;
    private Point2D maxBound;
    private final int mapWidth = 1280;
    private final int mapHeight = 720;
    private final int minZoomLevel = 1;
    private final int maxZoomLevel = 23;
    private int zoomLevel;
    private GraphicsContext gc;
    private Canvas map;

    public MapPane() {
        this.setPrefSize(this.mapWidth, this.mapHeight);
        this.setStyle("-fx-background-color: #808080;");
        this.setEventHandler(MouseEvent.ANY, new MapDraggingHandler(this));
        this.setEventHandler(ScrollEvent.ANY, new MapZoomHandler(this));
        this.zoomLevel = 18; // hardcoded value between minZoomLevel:maxZoomLevel
        this.map = new Canvas(this.mapWidth, this.mapHeight);
        this.gc = map.getGraphicsContext2D();
    }

    public MapPane(OSM_Graph osm_graph) {
        this();
        this.osm_graph = osm_graph;
        this.minBound = this.osm_graph.getTopLeftBound();
        this.maxBound = this.osm_graph.getBottomRightBound();
    }

    /**
     * drawLines method draws all the road graph edges on the MapPane
     */
    public void drawLines() {
        this.gc.clearRect(0, 0, this.mapWidth, this.mapHeight);
        for (OSM_Edge osm_edge : this.osm_graph.getEdges()) {
            String roadType = osm_edge.getRoadType();
            int edgeMinZoomLevel = 0;
            switch (roadType){
                case "road":
                    edgeMinZoomLevel = this.minZoomLevel;
                    break;
                case "linkRoad":
                    edgeMinZoomLevel = 20;
                    break;
                case "specialRoad":
                    edgeMinZoomLevel = 20;
                    break;
            }
            if (edgeMinZoomLevel > this.zoomLevel){
                continue;
            }

            OSM_Node startNode = osm_edge.getStartNode();
            OSM_Node endNode = osm_edge.getEndNode();
            if (this.isInsideWindow(startNode) || this.isInsideWindow(endNode)) {
//                String roadType = osm_edge.getRoadType();
                double startNodeX = this.convertLongitudeToX(startNode.getLongitude());
                double startNodeY = this.convertLatitudeToY(startNode.getLatitude());
                double endNodeX = this.convertLongitudeToX(endNode.getLongitude());
                double endNodeY = this.convertLatitudeToY(endNode.getLatitude());
//                this.drawLine(startNodeX, startNodeY, endNodeX, endNodeY, roadType);

                // drawing in canvas
                this.drawLineInCanvas(this.map, startNodeX, startNodeY, endNodeX, endNodeY, roadType);
            }
        }
        this.getChildren().add(this.map);
    }

    public void drawLineInCanvas(Canvas map, double startX, double startY, double endX, double endY, String roadType){
        double x1 = this.convertXToFitWindow(startX);
        double y1 = this.convertYToFitWindow(startY);
        double x2 = this.convertXToFitWindow(endX);
        double y2 = this.convertYToFitWindow(endY);

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
     * drawLine method draws one line based on the specified road type
     *
     * @param startX   start point x coordinate
     * @param startY   start point y coordinate
     * @param endX     end point x coordinate
     * @param endY     end point y coordinate
     * @param roadType road type of the specified edge being drawn
     */
    public void drawLine(double startX, double startY, double endX, double endY, String roadType) {
        double x1 = this.convertXToFitWindow(startX);
        double y1 = this.convertYToFitWindow(startY);
        double x2 = this.convertXToFitWindow(endX);
        double y2 = this.convertYToFitWindow(endY);
        Line line = new Line(x1, y1, x2, y2);
        switch (roadType) {
            case "road":
                line.setStroke(Color.SANDYBROWN);
                line.setStrokeWidth(3);
                break;
            case "linkRoad":
                line.setStroke(Color.SALMON);
                line.setStrokeWidth(2);
                break;
            case "specialRoad":
                line.setStroke(Color.PAPAYAWHIP);
                line.setStrokeWidth(1);
                //MOCCASIN
                break;
        }
        this.getChildren().add(line);
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
        double xPercentageShift = xDelta / this.mapWidth;
        double yPercentageShift = yDelta / this.mapHeight;

        double minBoundX = this.convertLongitudeToX(this.minBound.getX());
        double minBoundY = this.convertLatitudeToY(this.minBound.getY());
        double maxBoundX = this.convertLongitudeToX(this.maxBound.getX());
        double maxBoundY = this.convertLatitudeToY(this.maxBound.getY());

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
        double newZoomLevel = this.zoomLevel + zoomSign;
        if (newZoomLevel >= this.minZoomLevel && newZoomLevel <= this.maxZoomLevel) {
            this.zoomLevel += zoomSign;
        } else {
            return;
        }

        double minBoundX = this.convertLongitudeToX(this.minBound.getX());
        double minBoundY = this.convertLatitudeToY(this.minBound.getY());
        double maxBoundX = this.convertLongitudeToX(this.maxBound.getX());
        double maxBoundY = this.convertLatitudeToY(this.maxBound.getY());

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
            double nodeX = this.convertLongitudeToX(node.getLongitude());
            double nodeY = this.convertLatitudeToY(node.getLatitude());
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
        double x1 = this.convertXToFitWindow(x);
        double y1 = this.convertYToFitWindow(y);
        Rectangle rectangle = new Rectangle(x1, y1, 5, 5);
        this.getChildren().add(rectangle);
    }


    public double convertLongitudeToX(double longitude) {
        return ((longitude + 180) / 360) * 256 * Math.pow(2, this.zoomLevel);
    }


    public double convertLatitudeToY(double latitude) {
        double sinLatitude = Math.sin(latitude * Math.PI / 180);
        return (0.5 - Math.log((1 + sinLatitude) / (1 - sinLatitude)) / (4 * Math.PI)) * 256 * Math.pow(2, this.zoomLevel);
    }


    public double convertXToLongitude(double x) {
        return ((x / (256 * Math.pow(2, this.zoomLevel))) - 0.5) * 360;
    }


    public double convertYToLatitude(double y) {
        return 90 - 360 * Math.atan(Math.exp(-(0.5 - (y / (256 * Math.pow(2, this.zoomLevel)))) * 2 * Math.PI)) / Math.PI;
    }


    /**
     * Method which fits X global coordinate to window size
     *
     * @param x global coordinate
     * @return x view local coordinate
     */
    public double convertXToFitWindow(double x) {
        double minBoundX = this.convertLongitudeToX(this.minBound.getX());
        double maxBoundX = this.convertLongitudeToX(this.maxBound.getX());
        return ((x - minBoundX) / (maxBoundX - minBoundX)) * this.mapWidth;
    }


    /**
     * Method which fits Y global coordinate to window size
     *
     * @param y global coordinate
     * @return y view local coordinate
     */
    public double convertYToFitWindow(double y) {
        double minBoundY = this.convertLatitudeToY(this.minBound.getY());
        double maxBoundY = this.convertLatitudeToY(this.maxBound.getY());
        return ((y - minBoundY) / (maxBoundY - minBoundY)) * this.mapHeight;
    }


    private void setMinBound(double minBoundX, double minBoundY) {
        double newMinBoundLongitude = this.convertXToLongitude(minBoundX);
        double newMinBoundLatitude = this.convertYToLatitude(minBoundY);
        this.minBound = new Point2D(newMinBoundLongitude, newMinBoundLatitude);
    }


    private void setMaxBound(double maxBoundX, double maxBoundY) {
        double newMaxBoundLongitude = this.convertXToLongitude(maxBoundX);
        double newMaxBoundLatitude = this.convertYToLatitude(maxBoundY);
        this.maxBound = new Point2D(newMaxBoundLongitude, newMaxBoundLatitude);
    }
}
