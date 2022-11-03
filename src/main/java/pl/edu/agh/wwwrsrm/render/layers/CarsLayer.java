package pl.edu.agh.wwwrsrm.render.layers;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.apache.commons.lang3.math.NumberUtils;
import pl.edu.agh.wwwrsrm.graph.GraphOSM;
import pl.edu.agh.wwwrsrm.graph.NodeOSM;
import pl.edu.agh.wwwrsrm.model.Car;
import pl.edu.agh.wwwrsrm.render.Layer;
import pl.edu.agh.wwwrsrm.utils.coordinates.WindowXYCoordinate;
import pl.edu.agh.wwwrsrm.utils.window.MapWindow;

import java.util.HashMap;
import java.util.Map;

public class CarsLayer extends Layer {

    private final GraphOSM osm_graph;
    private final MapWindow mapWindow;

    private final Map<String, Car> cars;

    private final Map<Integer, Double> resolution = new HashMap<Integer, Double>();

    private double currentResolution = 1.0;

    public CarsLayer(double width, double height, GraphOSM osm_graph, MapWindow mapWindow, Map<String, Car> cars) {
        super(width, height);
        this.mapWindow = mapWindow;
        this.osm_graph = osm_graph;
        this.cars = cars;
        for(int i = MapWindow.MIN_ZOOM_LEVEL; i <= MapWindow.MAX_ZOOM_LEVEL; i++) {
            double r = mapWindow.groundResolution((osm_graph.getBottomBound() + osm_graph.getTopBound())/2.0, i);
            resolution.put(i, r);
        }
    }

    @Override
    public void draw(GraphicsContext gc, double delta) {
        this.currentResolution = resolution.get(mapWindow.getZoomLevel());
        for (Car car : this.cars.values()) {
            this.drawCar(gc, car);
        }

    }

    private float getAngle(double x1, double y1, double x2, double y2) {
        float angle = (float) Math.toDegrees(Math.atan2(y2 - y1, x2 - x1));

        if(angle < 0){
            angle += 360;
        }

        return angle;
    }

    /**
     * drawCar method draw car on the MapPane
     */
    // TODO change
    public void drawCar(GraphicsContext gc, Car car) {
        String node1Id = car.getNode1Id();
        String node2Id = car.getNode2Id();
        if (!NumberUtils.isCreatable(node1Id) || !NumberUtils.isCreatable(node2Id)) {
            return;
        }
        NodeOSM startNode = this.osm_graph.getNodes().get(Long.parseLong(node1Id));
        NodeOSM endNode = this.osm_graph.getNodes().get(Long.parseLong(node2Id));
        if (startNode == null || endNode == null) {
            return;
        }

        double positionOnLane = car.getPositionOnLane();
        WindowXYCoordinate startNodeWindowXY = startNode.getCoordinate().convertToWindowXY(mapWindow);
        WindowXYCoordinate endNodeWindowXY = endNode.getCoordinate().convertToWindowXY(mapWindow);

        double carX = startNodeWindowXY.getX() + positionOnLane * (endNodeWindowXY.getX() - startNodeWindowXY.getX());
        double carY = startNodeWindowXY.getY() + positionOnLane * (endNodeWindowXY.getY() - startNodeWindowXY.getY());

        this.drawNode(gc,
                    carX,
                    carY,
                    getAngle(endNodeWindowXY.getX(), endNodeWindowXY.getY(), startNodeWindowXY.getX(), startNodeWindowXY.getY()),
                    car.getLength(),
                    Color.RED);
    }

    /**
     * drawNode method draws one node
     *
     * @param x node x coordinate
     * @param y node y coordinate
     */
    public void drawNode(GraphicsContext gc, double x, double y, double r, double l, Color color) {
        double length = (3.0*l) / currentResolution;
        double width = 5.0 / currentResolution;
        x -= length / 2.0;
        y -= width / 2.0;

        x -= Math.cos((r+90)*3.14/180.0)*3.0/ currentResolution;
        y -= Math.sin((r+90)*3.14/180.0)*3.0/ currentResolution;


        gc.save();
        gc.translate(x + (length/2.0), y + (width/2.0));
        gc.rotate(r);
        gc.translate(-(x + (length/2.0)), -(y + (width/2.0)));
        gc.setFill(color);
        gc.fillRoundRect(x, y, length, width, 4/ currentResolution, 4/ currentResolution);
        gc.restore();

    }
}
