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

    private double lerp(double a, double b, double f) {
        return a + f * (b - a);
    }

    private double parabola(double t, double k) {
        return lerp(0.5, 0.85, Math.pow(4.0 * t * (1.0 - t), k));
    }

    /**
     * drawCar method draw car on the MapPane
     */
    // TODO change
    public void drawCar(GraphicsContext gc, Car car) {
        String node1Id = car.getNode1Id();
        String node2Id = car.getNode2Id();
        String lastNode1Id = car.getLastNode1Id();
        String lastNode2Id = car.getLastNode2Id();
        boolean noHistory = !NumberUtils.isCreatable(lastNode1Id) || !NumberUtils.isCreatable(lastNode2Id);
        if (!NumberUtils.isCreatable(node1Id) || !NumberUtils.isCreatable(node2Id)) {
            return;
        }
        NodeOSM startNode = this.osm_graph.getNodes().get(Long.parseLong(node1Id));
        NodeOSM endNode = this.osm_graph.getNodes().get(Long.parseLong(node2Id));
        NodeOSM lastStartNode = null;
        NodeOSM lastEndNode = null;
        if(!noHistory) {
            lastStartNode = this.osm_graph.getNodes().get(Long.parseLong(lastNode1Id));
            lastEndNode = this.osm_graph.getNodes().get(Long.parseLong(lastNode2Id));
        }
        if(lastStartNode == null || lastEndNode == null) {
            noHistory = true;
        }
        if (startNode == null || endNode == null) {
            return;
        }

        double positionOnLane = car.getPositionOnLane();
        WindowXYCoordinate startNodeWindowXY = startNode.getCoordinate().convertToWindowXY(mapWindow);
        WindowXYCoordinate endNodeWindowXY = endNode.getCoordinate().convertToWindowXY(mapWindow);
        WindowXYCoordinate lastStartNodeWindowXY = null;
        WindowXYCoordinate lastEndNodeWindowXY = null;
        double lastPositionOnLane = car.getLastPositionOnLane();
        if(!noHistory) {
            lastStartNodeWindowXY = lastStartNode.getCoordinate().convertToWindowXY(mapWindow);
            lastEndNodeWindowXY = lastEndNode.getCoordinate().convertToWindowXY(mapWindow);
        }

        double endX = startNodeWindowXY.getX() + positionOnLane * (endNodeWindowXY.getX() - startNodeWindowXY.getX());
        double endY = startNodeWindowXY.getY() + positionOnLane * (endNodeWindowXY.getY() - startNodeWindowXY.getY());
        double startX;
        double startY;
        if(!noHistory) {
            startX = lastStartNodeWindowXY.getX() + lastPositionOnLane * (lastEndNodeWindowXY.getX() - lastStartNodeWindowXY.getX());
            startY = lastStartNodeWindowXY.getY() + lastPositionOnLane * (lastEndNodeWindowXY.getY() - lastStartNodeWindowXY.getY());
        }
        else {
            startX = endX;
            startY = endY;
        }

        double progress = car.getProgress();
        if(progress > 1.0) {
            progress = 1.0;
        }

        double currentX = startX + progress*(endX - startX);
        double currentY = startY + progress*(endY - startY);
        car.setProgress(progress + (parabola(progress, 2))/50.0);

        double endR = getAngle(endNodeWindowXY.getX(), endNodeWindowXY.getY(), startNodeWindowXY.getX(), startNodeWindowXY.getY());
        double startR;
        if(!noHistory)
            startR = getAngle(lastEndNodeWindowXY.getX(), lastEndNodeWindowXY.getY(), lastStartNodeWindowXY.getX(), lastStartNodeWindowXY.getY());
        else
            startR = endR;
        if (endR - startR > (endR+360)-startR)
            startR += 360;

        this.drawNode(gc,
                    currentX,
                    currentY,
                    startR + progress*(endR - startR),
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
