package pl.edu.agh.wwwrsrm.render.layers;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.springframework.stereotype.Component;
import pl.edu.agh.wwwrsrm.graph.EdgeOSM;
import pl.edu.agh.wwwrsrm.graph.GraphOSM;
import pl.edu.agh.wwwrsrm.graph.NodeOSM;
import pl.edu.agh.wwwrsrm.graph.WayOSM;
import pl.edu.agh.wwwrsrm.model.Car;
import pl.edu.agh.wwwrsrm.osm.WayParameters;
import pl.edu.agh.wwwrsrm.osm.osmParser;
import pl.edu.agh.wwwrsrm.render.Layer;
import pl.edu.agh.wwwrsrm.utils.coordinates.WindowXYCoordinate;
import pl.edu.agh.wwwrsrm.utils.window.MapWindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Component
public class CarsLayer extends Layer {

    private final GraphOSM osm_graph;
    private final MapWindow mapWindow;

    private final Map<String, WayOSM> wayIdsMapper = new HashMap<>();

    private final Map<String, Car> cars;

    public CarsLayer(double width, double height, GraphOSM osm_graph, MapWindow mapWindow, Map<String, Car> cars) {
        super(width, height);
        this.mapWindow = mapWindow;
        this.osm_graph = osm_graph;
        this.cars = cars;
    }

    @Override
    public void draw(GraphicsContext gc, double delta) {
        for (Car car : this.cars.values()) {
            this.drawCar(gc, car);
        }

    }

    /**
     * drawCar method draw car on the MapPane
     */
    // TODO change
    public void drawCar(GraphicsContext gc, Car car) {
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

        this.drawNode(gc, carX, carY, Color.RED);
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
    public void drawNode(GraphicsContext gc, double x, double y, Color color) {
        gc.setFill(color);
        gc.fillRect(x, y, 10, 10);
    }
}
