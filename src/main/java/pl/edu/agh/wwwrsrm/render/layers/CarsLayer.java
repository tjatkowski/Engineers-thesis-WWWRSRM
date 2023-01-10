package pl.edu.agh.wwwrsrm.render.layers;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import pl.edu.agh.wwwrsrm.graph.GraphOSM;
import pl.edu.agh.wwwrsrm.model.Car;
import pl.edu.agh.wwwrsrm.render.Layer;
import pl.edu.agh.wwwrsrm.utils.CarsManager;
import pl.edu.agh.wwwrsrm.utils.window.MapWindow;

import java.util.HashMap;
import java.util.Map;

public class CarsLayer extends Layer {

    private final GraphOSM osm_graph;
    private final MapWindow mapWindow;

    private final CarsManager carsManager;

    private final Map<Integer, Double> resolution = new HashMap<>();

    private double currentResolution = 1.0;

    public CarsLayer(double width, double height, GraphOSM osm_graph, MapWindow mapWindow, CarsManager carsManager) {
        super(width, height);
        this.mapWindow = mapWindow;
        this.osm_graph = osm_graph;
        this.carsManager = carsManager;
        for(int i = MapWindow.MIN_ZOOM_LEVEL; i <= MapWindow.MAX_ZOOM_LEVEL; i++) {
            double r = mapWindow.groundResolution((osm_graph.getBottomBound() + osm_graph.getTopBound())/2.0, i);
            resolution.put(i, r);
        }
    }

    @Override
    public void draw(GraphicsContext gc, double delta) {
        this.currentResolution = resolution.get(mapWindow.getZoomLevel());
        for (Car car : this.carsManager.getCars().values()) {
            this.drawCar(gc, car, delta);
        }
    }

    /**
     * drawCar method draw car on the MapPane
     */
    // TODO change
    public void drawCar(GraphicsContext gc, Car car, double delta) {
        Pair<Point2D, Double> positionRotation = car.getPositionRotation(mapWindow, osm_graph);
        if(positionRotation == null)
            return;
        car.progress(delta);

        Point2D position = positionRotation.getKey();
        double rotation = positionRotation.getValue();

        int argb = car.getCarId().hashCode();
        int b = (argb)&0xFF;
        int g = (argb>>8)&0xFF;
        int r = (argb>>16)&0xFF;
        int a = (argb>>24)&0xFF;

        this.drawNode(gc, position.getX(), position.getY(), rotation, car.getLength(), new Color((float)r/255.0, (float)g/255.0, (float)b/255.0, 1.0));
    }

    /**
     * drawNode method draws one node
     *
     * @param x node x coordinate
     * @param y node y coordinate
     */
    public void drawNode(GraphicsContext gc, double x, double y, double r, double l, Color color) {
        double length = (1*l) / currentResolution;
        double width = 2 / currentResolution;
        x -= length / 2.0;
        y -= width / 2.0;

        x += Math.cos((r+90)*3.14/180.0)*3.0/ currentResolution;
        y += Math.sin((r+90)*3.14/180.0)*3.0/ currentResolution;


        gc.save();
        gc.translate(x + (length/2.0), y + (width/2.0));
        gc.rotate(r);
        gc.translate(-(x + (length/2.0)), -(y + (width/2.0)));
        gc.setFill(color);
        gc.fillRoundRect(x, y, length, width, 4/ currentResolution, 4/ currentResolution);
        gc.restore();
    }
}
