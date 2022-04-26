package visualization;

import graph.OSM_Edge;
import graph.OSM_Graph;
import graph.OSM_Node;
import javafx.geometry.Point2D;
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
            String roadType = osm_edge.getRoadType();
            Point2D startNodeCoordinatesXY = this.convertNodeCoordinatesToXY(new Point2D(startNode.getLongitude(), startNode.getLatitude()));
            Point2D endNodeCoordinatesXY = this.convertNodeCoordinatesToXY(new Point2D(endNode.getLongitude(), endNode.getLatitude()));
            this.drawLine(startNodeCoordinatesXY.getX(), startNodeCoordinatesXY.getY(),
                    endNodeCoordinatesXY.getX(), endNodeCoordinatesXY.getY(), roadType);

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
}
