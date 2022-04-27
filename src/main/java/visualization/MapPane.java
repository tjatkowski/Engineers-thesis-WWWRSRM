package visualization;

import graph.OSM_Edge;
import graph.OSM_Graph;
import graph.OSM_Node;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
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
    private final int mapWidth = 900;
    private final int mapHeight = 600;

    public MapPane() {
        this.setPrefSize(this.mapWidth, this.mapHeight);
        this.setStyle("-fx-background-color: #808080;");
        this.setEventHandler(MouseEvent.ANY, new MapDraggingHandler(this));
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
        for (OSM_Edge osm_edge : this.osm_graph.getEdges()) {
            OSM_Node startNode = osm_edge.getStartNode();
            OSM_Node endNode = osm_edge.getEndNode();
            if (this.isInsideWindow(startNode) || this.isInsideWindow(endNode)) {
                String roadType = osm_edge.getRoadType();
                Point2D startNodeCoordinatesXY = this.convertNodeCoordinatesToXY(new Point2D(startNode.getLongitude(), startNode.getLatitude()));
                Point2D endNodeCoordinatesXY = this.convertNodeCoordinatesToXY(new Point2D(endNode.getLongitude(), endNode.getLatitude()));

                this.drawLine(startNodeCoordinatesXY.getX(), startNodeCoordinatesXY.getY(),
                        endNodeCoordinatesXY.getX(), endNodeCoordinatesXY.getY(), roadType);
            }
        }
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
        Line line = new Line(startX, startY, endX, endY);
        switch (roadType) {
            case "road":
                line.setStroke(Color.SANDYBROWN);
                line.setStrokeWidth(5);
                break;
            case "linkRoad":
                line.setStroke(Color.MOCCASIN);
                line.setStrokeWidth(3);
                break;
            case "specialRoad":
                line.setStroke(Color.PAPAYAWHIP);
                line.setStrokeWidth(1);
                break;
        }

        this.getChildren().add(line);
    }

    /**
     * Util method which checks if node in degree coordinates is inside map boundaries.
     * @param osm_node OSM_Node
     * @return true if node is inside map boundaries
     */
    public boolean isInsideWindow(OSM_Node osm_node) {
        if (osm_node.getLongitude() > this.minBound.getX()
                && osm_node.getLatitude() < this.minBound.getY()
                && osm_node.getLongitude() < this.maxBound.getX()
                && osm_node.getLatitude() > this.maxBound.getY()) {
            return true;
        }
        return false;
    }

    /**
     * Method which moves map boundaries.
     * @param xDelta x delta in XY coordinates
     * @param yDelta y delta in XY coordinates
     */
    public void dragWindowByVector(double xDelta, double yDelta) {
        Point2D minBoundXY = this.convertNodeCoordinatesToXY(this.minBound);
        Point2D maxBoundXY = this.convertNodeCoordinatesToXY(this.maxBound);

        double newMinBoundLongitude = this.convertXToLongitude(minBoundXY.getX() - xDelta);
        double newMinBoundLatitude = this.convertYToLatitude(minBoundXY.getY() - yDelta);

        double newMaxBoundLongitude = this.convertXToLongitude(maxBoundXY.getX() - xDelta);
        double newMaxBoundLatitude = this.convertYToLatitude(maxBoundXY.getY() - yDelta);

        this.minBound = new Point2D(newMinBoundLongitude, newMinBoundLatitude);
        this.maxBound = new Point2D(newMaxBoundLongitude, newMaxBoundLatitude);

        this.getChildren().clear();
        this.drawLines();
    }

    /**
     * drawNodes method draws all the road graph nodes on the MapPane
     */
    public void drawNodes() {
        for (OSM_Node node : this.osm_graph.getNodes().values()) {
            Point2D nodeCoordinatesXY = this.convertNodeCoordinatesToXY(new Point2D(node.getLongitude(), node.getLatitude()));
            this.drawNode(nodeCoordinatesXY.getX(), nodeCoordinatesXY.getY());
        }
    }

    /**
     * drawNode method draws one node
     *
     * @param x node x coordinate
     * @param y node y coordinate
     */
    public void drawNode(double x, double y) {
        Rectangle rectangle = new Rectangle(x, y, 5, 5);
        this.getChildren().add(rectangle);
    }

    /**
     * convertNodeCoordinatesToXY method converts coordinates in Longitude/Latitude to XY format
     *
     * @param nodeCoordinates node coordinates as Point2D [Longitude, Latitude]
     * @return node coordinates as Point2D [X, Y]
     */
    public Point2D convertNodeCoordinatesToXY(Point2D nodeCoordinates) {
        double x = ((nodeCoordinates.getX() - this.minBound.getX()) /
                (this.maxBound.getX() - this.minBound.getX())) * (this.mapWidth);
        double y = ((this.minBound.getY() - nodeCoordinates.getY()) /
                (this.minBound.getY() - this.maxBound.getY())) * (this.mapHeight);
        return new Point2D((int) x, (int) y);
    }


    public double convertXToLongitude(double x) {
        return this.minBound.getX() + ((x) / this.mapWidth) * (this.maxBound.getX() - this.minBound.getX());
    }

    public double convertYToLatitude(double y) {
        return this.minBound.getY() - ((y / this.mapHeight) * (this.minBound.getY() - this.maxBound.getY()));
    }
}
