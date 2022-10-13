package pl.edu.agh.wwwrsrm.window.map;

import java.util.HashMap;
import java.util.Vector;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.animation.AnimationTimer;

import org.springframework.stereotype.Component;
import pl.edu.agh.wwwrsrm.graph.GraphOSM;
import pl.edu.agh.wwwrsrm.graph.WayOSM;
import pl.edu.agh.wwwrsrm.model.Car;
import pl.edu.agh.wwwrsrm.osm.osmParser;
import pl.edu.agh.wwwrsrm.render.Layer;
import pl.edu.agh.wwwrsrm.render.layers.*;
import pl.edu.agh.wwwrsrm.utils.constants.Zoom;
import pl.edu.agh.wwwrsrm.utils.window.MapWindow;
import pl.edu.agh.wwwrsrm.visualization.MapDraggingHandler;
import pl.edu.agh.wwwrsrm.visualization.MapZoomHandler;
import pl.edu.agh.wwwrsrm.window.Style;

import javax.annotation.PostConstruct;

@Component
public class Map extends Canvas {
    private Vector<Layer> layers = new Vector<Layer>();

    private AnimationTimer timer;

    private MapWindow mapWindow;

    private GraphOSM osm_graph;

    private final java.util.Map<String, Car> cars = new HashMap<>();

    public Map() {
        super(Style.windowWidth - Style.menuWidth, Style.windowHeight);
        this.setEventHandler(MouseEvent.ANY, new MapDraggingHandler(this));
        this.setEventHandler(ScrollEvent.ANY, new MapZoomHandler(this));
        osm_graph = osmParser.CreateGraph("src/main/resources/osm/cracow.pbf");
        this.mapWindow = new MapWindow(osm_graph.getTopLeftBound(), osm_graph.getBottomRightBound(), (int)getWidth(), (int)getHeight());
        setTimer();
        addLayers();
    }
    
    private void addLayers() {
        layers.add(new RoadsLayer(getWidth(), getHeight(), osm_graph, mapWindow));
        layers.add(new CarsLayer(getWidth(), getHeight(), osm_graph, mapWindow, cars));
        layers.add(new DebugLayer(getWidth(), getHeight()));
    }

    private void setTimer() {
        timer = new AnimationTimer() {
            private long previous = 0;
            @Override
            public void handle(long now) {
                double delta = ((double)(now - previous))/1000000000.0;
                previous = now;
                draw(delta);
            }
        };
        timer.start();
    }

    public void clearCars() {
        cars.clear();
    }

    public void updateCar(Car car) {
        cars.put(car.getCarId(), car);
    }

    public void draw(double delta) {
        GraphicsContext gc = this.getGraphicsContext2D();
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0, 0, getWidth(), getHeight());
        for (Layer layer : this.layers) {
            layer.draw(gc, delta);
        }
    }

    /**
     * Method which moves map boundaries.
     */
    public void dragMapViewByVector(double xDelta, double yDelta) {
        mapWindow.dragMapWindowByVector(xDelta, yDelta);
    }


    /**
     * Method which zoom in and zoom out map boundaries.
     */
    public void zoomMapView(Zoom zoom) {
        mapWindow.zoomMapWindow(zoom);
    }
}
