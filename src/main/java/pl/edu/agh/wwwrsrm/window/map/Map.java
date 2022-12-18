package pl.edu.agh.wwwrsrm.window.map;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import org.springframework.stereotype.Component;
import pl.edu.agh.wwwrsrm.graph.GraphOSM;
import pl.edu.agh.wwwrsrm.graph.NodeOSM;
import pl.edu.agh.wwwrsrm.model.Car;
import pl.edu.agh.wwwrsrm.utils.constants.Zoom;
import pl.edu.agh.wwwrsrm.utils.window.MapView;
import pl.edu.agh.wwwrsrm.visualization.MapDraggingHandler;
import pl.edu.agh.wwwrsrm.visualization.MapZoomHandler;
import pl.edu.agh.wwwrsrm.visualization.execution.VisualizationTimer;
import pl.edu.agh.wwwrsrm.window.Style;
import proto.model.Node;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

@Component
public class Map extends Canvas {
    private MapView mapView;

    private final GraphOSM graphOSM;

    private final java.util.Map<String, Car> cars = new HashMap<>();

    private boolean isMapResized;

    public Map(GraphOSM graphOSM) {
        super(Style.WINDOW_WIDTH - Style.MENU_WIDTH, Style.WINDOW_HEIGHT);
        this.widthProperty().addListener(observable -> isMapResized = true);
        this.heightProperty().addListener(observable -> isMapResized = true);
        this.setEventHandler(MouseEvent.ANY, new MapDraggingHandler(this));
        this.setEventHandler(ScrollEvent.ANY, new MapZoomHandler(this));
        this.graphOSM = graphOSM;
        this.mapView = new MapView(graphOSM, cars, (int) getWidth(), (int) getHeight(), null);
        VisualizationTimer visualizationTimer = new VisualizationTimer(this);
        visualizationTimer.start();
    }

    @Override
    public boolean isResizable() {
        return true;
    }

    public void clearCars() {
        for (Iterator<Entry<String, Car>> it = cars.entrySet().iterator(); it.hasNext(); ) {
            Entry<String, Car> entry = it.next();
            if (entry.getValue().isToDelete()) {
                it.remove();
            } else {
                entry.getValue().setToDelete();
            }
        }
    }

    //TODO remove, it's not Map functionality
    public void addNodes(List<Node> nodes) {
        for (Node node : nodes)
            graphOSM.addNode(new NodeOSM(node));
    }

    public void updateCar(Car car) {
        Car carToUpdate = cars.get(car.getCarId());
        if (carToUpdate != null)
            carToUpdate.update(car);
        else
            cars.put(car.getCarId(), car);
    }

    public void draw(double delta) {
        GraphicsContext gc = this.getGraphicsContext2D();
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0, 0, getWidth(), getHeight());
        if (isMapResized) {
            mapView = new MapView(graphOSM, cars, (int) getWidth(), (int) getHeight(), mapView);
            isMapResized = false;
        }
        mapView.getLayers()
                .forEach(layer -> layer.draw(gc, delta));
    }

    /**
     * Method which moves map boundaries.
     */
    public void dragMapViewByVector(double xDelta, double yDelta) {
        mapView.getMapWindow().dragMapWindowByVector(xDelta, yDelta);
    }


    /**
     * Method which zoom in and zoom out map boundaries.
     */
    public void zoomMapView(Zoom zoom) {
        mapView.getMapWindow().zoomMapWindow(zoom);
    }
}
