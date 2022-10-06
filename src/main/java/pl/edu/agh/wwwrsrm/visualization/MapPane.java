package pl.edu.agh.wwwrsrm.visualization;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import pl.edu.agh.wwwrsrm.graph.EdgeOSM;
import pl.edu.agh.wwwrsrm.graph.GraphOSM;
import pl.edu.agh.wwwrsrm.graph.NodeOSM;
import pl.edu.agh.wwwrsrm.graph.WayOSM;
import pl.edu.agh.wwwrsrm.model.Car;
import pl.edu.agh.wwwrsrm.osm.WayParameters;
import pl.edu.agh.wwwrsrm.utils.constants.Zoom;
import pl.edu.agh.wwwrsrm.utils.coordinates.WindowXYCoordinate;
import pl.edu.agh.wwwrsrm.utils.window.MapWindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;


/**
 * MapPane class does visualization of the road graph
 */
public class MapPane extends Pane {
    private static final int MAP_WIDTH = 1100;
    private static final int MAP_HEIGHT = 800;
    private final GraphOSM osm_graph;
    private final MapWindow mapWindow;
    private final GraphicsContext gc;
    private final Canvas map;

    private Pane configPane;

    private final Map<String, WayOSM> wayIdsMapper = new HashMap<>();
    private final Map<String, Car> cars = new HashMap<>();

    public MapPane(GraphOSM osm_graph) {
        this.setPrefSize(MAP_WIDTH, MAP_HEIGHT);
        this.setStyle("-fx-background-color: #808080;");
        this.setEventHandler(MouseEvent.ANY, new MapDraggingHandler(this));
        this.setEventHandler(ScrollEvent.ANY, new MapZoomHandler(this));
        this.osm_graph = osm_graph;
        this.mapWindow = new MapWindow(osm_graph.getTopLeftBound(), osm_graph.getBottomRightBound(), MAP_WIDTH, MAP_HEIGHT);
        this.map = new Canvas(MAP_WIDTH, MAP_HEIGHT);
        this.gc = map.getGraphicsContext2D();
    }


    /**
     * drawLines method draws all the road graph edges on the MapPane
     */
    public void drawLines() {
        gc.clearRect(0, 0, MAP_WIDTH, MAP_HEIGHT);
        for (WayOSM way : osm_graph.getWays()) {
            WayParameters wayParameters = way.getEdgeParameter();
            int wayMinZoomLevel = wayParameters.getZoomLevel();
            int wayWidth = wayParameters.getWayWidth();
            Color wayColor = wayParameters.getColor();

            if (wayMinZoomLevel > mapWindow.getZoomLevel()) {
                continue;
            }

            if (!way.isClosed()) {
                for (EdgeOSM edge : way.getEdges()) {
                    NodeOSM startNode = edge.getStartNode();
                    NodeOSM endNode = edge.getEndNode();
                    if (mapWindow.isInsideWindow(startNode.getCoordinate()) || mapWindow.isInsideWindow(endNode.getCoordinate())) {
                        WindowXYCoordinate startNodeWindowXY = startNode.getCoordinate().convertToWindowXY(mapWindow);
                        WindowXYCoordinate endNodeWindowXY = endNode.getCoordinate().convertToWindowXY(mapWindow);
                        // drawing in canvas
                        this.drawLineInCanvas(startNodeWindowXY, endNodeWindowXY, wayWidth, wayColor);
                    }
                }
            } else {
                NodeOSM firstNode = way.getEdges().get(0).getStartNode();
                if (!mapWindow.isInsideWindow(firstNode.getCoordinate())) {
                    continue;
                }

                List<WindowXYCoordinate> points = new ArrayList<>();
                points.add(firstNode.getCoordinate().convertToWindowXY(mapWindow));

                Stream<NodeOSM> nodes = way.getEdges().stream().map(EdgeOSM::getEndNode);
                if (!nodes.map(NodeOSM::getCoordinate).allMatch(mapWindow::isInsideWindow)) {
                    continue;
                }

                points.addAll(way.getEdges().stream()
                        .map(EdgeOSM::getEndNode)
                        .map(NodeOSM::getCoordinate)
                        .map(coordinate -> coordinate.convertToWindowXY(mapWindow)).toList());

                this.drawPolygonInCanvas(points.stream().map(WindowXYCoordinate::getX).mapToDouble(Integer::doubleValue).toArray(),
                        points.stream().map(WindowXYCoordinate::getY).mapToDouble(Integer::doubleValue).toArray(), wayColor);
            }
        }
        this.drawCars();
        this.getChildren().add(this.map);
    }

    public void drawLineInCanvas(WindowXYCoordinate startPoint, WindowXYCoordinate endPoint, int width, Color color) {
        this.gc.setStroke(color);
        this.gc.setLineWidth(width);
        this.gc.strokeLine(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());
    }

    public void drawPolygonInCanvas(double[] xPoints, double[] yPoints, Color color) {
        int n = xPoints.length;
        this.gc.setFill(color);
        this.gc.fillPolygon(xPoints, yPoints, n);
    }

    /**
     * Method which moves map boundaries.
     */
    public void dragMapViewByVector(double xDelta, double yDelta) {
        mapWindow.dragMapWindowByVector(xDelta, yDelta);
        this.getChildren().clear();
        this.drawLines();
    }

    /**
     * Method which zoom in and zoom out map boundaries.
     */
    public void zoomMapView(Zoom zoom) {
        mapWindow.zoomMapWindow(zoom);
        this.getChildren().clear();
        this.drawLines();
    }

    /**
     * drawNodes method draws all the road graph nodes on the MapPane
     */
    public void drawNodes() {
        for (NodeOSM node : this.osm_graph.getNodes().values()) {
            WindowXYCoordinate nodeWindowXY = node.getCoordinate().convertToWindowXY(mapWindow);
            this.drawNode(nodeWindowXY.getX(), nodeWindowXY.getY(), Color.BLUE);
        }
    }

    // TODO change
    public void drawCars() {
        for (Car car : this.cars.values()) {
            this.drawCar(car);
        }
    }

    // TODO change
    public void updateCar(Car car) {
        this.cars.put(car.getCarId(), car);
    }

    public void clearCars() {
        this.cars.clear();
    }

    /**
     * drawCar method draw car on the MapPane
     */
    // TODO change
    public void drawCar(Car car) {
        String laneId = car.getLaneId();
        double positionOnLane = car.getPositionOnLane();

        if (!this.wayIdsMapper.containsKey(laneId)) {
            WayOSM randomWayOSM;
            do {
                int randomWayIdx = getRandomIndex(0, this.osm_graph.getWays().size());
                randomWayOSM = this.osm_graph.getWays().get(randomWayIdx);
            } while (randomWayOSM.isClosed());
            this.wayIdsMapper.put(laneId, randomWayOSM);
        }

        WayOSM wayOSM = this.wayIdsMapper.get(laneId);
        NodeOSM startNode = wayOSM.getEdges().get(0).getStartNode();
        NodeOSM endNode = wayOSM.getEdges().get(wayOSM.getEdges().size() - 1).getEndNode();

        WindowXYCoordinate startNodeWindowXY = startNode.getCoordinate().convertToWindowXY(mapWindow);
        WindowXYCoordinate endNodeWindowXY = endNode.getCoordinate().convertToWindowXY(mapWindow);

        double carX = startNodeWindowXY.getX() + positionOnLane * (endNodeWindowXY.getX() - startNodeWindowXY.getX());
        double carY = startNodeWindowXY.getY() + positionOnLane * (endNodeWindowXY.getY() - startNodeWindowXY.getY());

        this.drawNode(carX, carY, Color.RED);
    }

    // TODO remove
    public static int getRandomIndex(int min, int max) {
        return ((int) (Math.random() * (max - min))) + min;
    }


    /**
     * drawNode method draws one node
     *
     * @param x node x coordinate
     * @param y node y coordinate
     */
    public void drawNode(double x, double y, Color color) {
        this.gc.setFill(color);
        this.gc.fillRect(x, y, 10, 10);
    }
}
