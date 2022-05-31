package pl.edu.agh.wwwrsrm.visualization;

import lombok.Setter;
import pl.edu.agh.wwwrsrm.graph.EdgeOSM;
import pl.edu.agh.wwwrsrm.graph.GraphOSM;
import pl.edu.agh.wwwrsrm.graph.NodeOSM;
import pl.edu.agh.wwwrsrm.graph.WayOSM;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import pl.edu.agh.wwwrsrm.osm.WayParameters;
import pl.edu.agh.wwwrsrm.utils.CoordinatesConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static pl.edu.agh.wwwrsrm.utils.ZoomDetector.getZoomLevel;

/**
 * MapPane class does visualization of the road graph
 */
public class MapPane extends Pane {
    private GraphOSM osm_graph;
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

    public MapPane(GraphOSM osm_graph) {
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
        for (WayOSM way : this.osm_graph.getWays()) {
            WayParameters wayParameters = way.getEdgeParameter();
            int wayMinZoomLevel = wayParameters.getZoomLevel();
            int wayWidth = wayParameters.getWayWidth();
            Color wayColor = wayParameters.getColor();

            if (wayMinZoomLevel > this.zoomLevel) {
                continue;
            }

            if (!way.isClosed()) {
                for (EdgeOSM edge : way.getEdges()) {
                    NodeOSM startNode = edge.getStartNode();
                    NodeOSM endNode = edge.getEndNode();
                    if (this.isInsideWindow(startNode) || this.isInsideWindow(endNode)) {
                        double startNodeX = CoordinatesConverter.convertLongitudeToX(startNode.getLongitude(), this.zoomLevel);
                        double startNodeY = CoordinatesConverter.convertLatitudeToY(startNode.getLatitude(), this.zoomLevel);
                        double endNodeX = CoordinatesConverter.convertLongitudeToX(endNode.getLongitude(), this.zoomLevel);
                        double endNodeY = CoordinatesConverter.convertLatitudeToY(endNode.getLatitude(), this.zoomLevel);

                        // drawing in canvas
                        this.drawLineInCanvas(startNodeX, startNodeY, endNodeX, endNodeY, wayWidth, wayColor);
                    }
                }
            } else {
                List<Double> xPoints = new ArrayList<>();
                List<Double> yPoints = new ArrayList<>();
                NodeOSM firstNode = way.getEdges().get(0).getStartNode();
                if (!this.isInsideWindow(firstNode)) {
                    continue;
                }

                xPoints.add(CoordinatesConverter.convertLongitudeToX(firstNode.getLongitude(), this.zoomLevel));
                yPoints.add(CoordinatesConverter.convertLatitudeToY(firstNode.getLatitude(), this.zoomLevel));

                Stream<NodeOSM> nodes = way.getEdges().stream().map(EdgeOSM::getEndNode);
                if (!nodes.allMatch(this::isInsideWindow)) {
                    continue;
                }

                xPoints.addAll(way.getEdges().stream().map(edge -> edge.getEndNode().getLongitude())
                        .map(longitude -> CoordinatesConverter.convertLongitudeToX(longitude, this.zoomLevel))
                        .collect(Collectors.toList()));
                yPoints.addAll(way.getEdges().stream().map(edge -> edge.getEndNode().getLatitude())
                        .map(latitude -> CoordinatesConverter.convertLatitudeToY(latitude, this.zoomLevel))
                        .collect(Collectors.toList()));

                this.drawPolygonInCanvas(xPoints.stream().mapToDouble(Double::doubleValue).toArray(),
                        yPoints.stream().mapToDouble(Double::doubleValue).toArray(), wayColor);
            }

        }
        this.getChildren().add(this.map);
    }

    public void drawLineInCanvas(double startX, double startY, double endX, double endY, int width, Color color) {
        double x1 = CoordinatesConverter.scaleXToFitWindow(startX, this.minBound, this.maxBound, MAP_WIDTH, this.zoomLevel);
        double y1 = CoordinatesConverter.scaleYToFitWindow(startY, this.minBound, this.maxBound, MAP_HEIGHT, this.zoomLevel);
        double x2 = CoordinatesConverter.scaleXToFitWindow(endX, this.minBound, this.maxBound, MAP_WIDTH, this.zoomLevel);
        double y2 = CoordinatesConverter.scaleYToFitWindow(endY, this.minBound, this.maxBound, MAP_HEIGHT, this.zoomLevel);
        this.gc.setStroke(color);
        this.gc.setLineWidth(width);
        this.gc.strokeLine(x1, y1, x2, y2);
    }

    public void drawPolygonInCanvas(double[] xPoints, double[] yPoints, Color color) {
        int n = xPoints.length;

        double[] xScaled = Arrays.stream(xPoints).map(x -> CoordinatesConverter.scaleXToFitWindow(x, this.minBound, this.maxBound, MAP_WIDTH, this.zoomLevel)).toArray();
        double[] yScaled = Arrays.stream(yPoints).map(y -> CoordinatesConverter.scaleYToFitWindow(y, this.minBound, this.maxBound, MAP_HEIGHT, this.zoomLevel)).toArray();

        this.gc.setFill(color);
        this.gc.fillPolygon(xScaled, yScaled, n);
    }

    /**
     * Util method which checks if node in degree coordinates is inside map boundaries.
     *
     * @param osm_node NodeOSM
     * @return true if node is inside map boundaries
     */
    public boolean isInsideWindow(NodeOSM osm_node) {
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
        for (NodeOSM node : this.osm_graph.getNodes().values()) {
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
