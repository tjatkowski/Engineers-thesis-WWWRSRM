package pl.edu.agh.wwwrsrm.window.map;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import pl.edu.agh.wwwrsrm.connection.producer.VisualizationStateChangeProducer;
import pl.edu.agh.wwwrsrm.graph.GraphOSM;
import pl.edu.agh.wwwrsrm.graph.NodeOSM;
import pl.edu.agh.wwwrsrm.model.Car;
import pl.edu.agh.wwwrsrm.utils.constants.Zoom;
import pl.edu.agh.wwwrsrm.utils.window.MapView;
import pl.edu.agh.wwwrsrm.visualization.MapDraggingHandler;
import pl.edu.agh.wwwrsrm.visualization.MapZoomHandler;
import pl.edu.agh.wwwrsrm.window.Style;
import proto.model.Node;
import proto.model.RUNNING_STATE;
import proto.model.VisualizationStateChangeMessage.ZOOM_LEVEL;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

@Component
public class Map extends Canvas {
    @Getter
    private MapView mapView;

    private final GraphOSM graphOSM;

    private final java.util.Map<String, Car> cars = new HashMap<>();

    private boolean isMapResized;
    private boolean isRoiRegionChanged;

    private final VisualizationStateChangeProducer visualizationStateChangeProducer;

    @Getter
    @Setter
    private RUNNING_STATE visualizationRunningState;
    @Getter
    @Setter
    private int visualizationSpeed;
    @Getter
    @Setter
    private ZOOM_LEVEL zoomLevel;

    public Map(GraphOSM graphOSM, VisualizationStateChangeProducer visualizationStateChangeProducer) {
        super(Style.WINDOW_WIDTH - Style.MENU_WIDTH, Style.WINDOW_HEIGHT);
        this.graphOSM = graphOSM;
        this.visualizationStateChangeProducer = visualizationStateChangeProducer;
        this.mapView = new MapView(graphOSM, cars, (int) getWidth(), (int) getHeight(), null);
    }

    @PostConstruct
    public void init() {
        this.widthProperty().addListener(observable -> isMapResized = true);
        this.heightProperty().addListener(observable -> isMapResized = true);
        this.setEventHandler(MouseEvent.ANY, new MapDraggingHandler(this));
        this.setEventHandler(ScrollEvent.ANY, new MapZoomHandler(this));
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
        if (isRoiRegionChanged && null != visualizationRunningState) {
            visualizationStateChangeProducer.sendStateChangeMessage(this);
            isRoiRegionChanged = false;
        }
        mapView.getLayers()
                .forEach(layer -> layer.draw(gc, delta));
    }

    /**
     * Method which moves map boundaries.
     */
    public void dragMapViewByVector(double xDelta, double yDelta) {
        mapView.getMapWindow().dragMapWindowByVector(xDelta, yDelta);
        isRoiRegionChanged = true;
    }


    /**
     * Method which zoom in and zoom out map boundaries.
     */
    public void zoomMapView(Zoom zoom) {
        mapView.getMapWindow().zoomMapWindow(zoom);
        isRoiRegionChanged = true;
    }
}
