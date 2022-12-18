package pl.edu.agh.wwwrsrm.window.map;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import org.springframework.stereotype.Component;
import pl.edu.agh.wwwrsrm.graph.GraphOSM;
import pl.edu.agh.wwwrsrm.model.Car;
import pl.edu.agh.wwwrsrm.utils.CarsManager;
import pl.edu.agh.wwwrsrm.utils.TrafficDensity;
import pl.edu.agh.wwwrsrm.utils.constants.Zoom;
import pl.edu.agh.wwwrsrm.utils.window.MapView;
import pl.edu.agh.wwwrsrm.visualization.MapDraggingHandler;
import pl.edu.agh.wwwrsrm.visualization.MapZoomHandler;
import pl.edu.agh.wwwrsrm.visualization.execution.VisualizationTimer;
import pl.edu.agh.wwwrsrm.window.Style;

import java.util.HashMap;

@Component
public class Map extends Canvas {
    private MapView mapView;

    private final GraphOSM graphOSM;

    private final TrafficDensity trafficDensity;

    private final CarsManager carsManager;

    private final java.util.Map<String, Car> cars = new HashMap<>();

    private boolean isMapResized;

    public Map(GraphOSM graphOSM, CarsManager carsManager, TrafficDensity trafficDensity) {
        super(Style.WINDOW_WIDTH - Style.MENU_WIDTH, Style.WINDOW_HEIGHT);
        this.widthProperty().addListener(observable -> isMapResized = true);
        this.heightProperty().addListener(observable -> isMapResized = true);
        this.setEventHandler(MouseEvent.ANY, new MapDraggingHandler(this));
        this.setEventHandler(ScrollEvent.ANY, new MapZoomHandler(this));
        this.graphOSM = graphOSM;
        this.carsManager = carsManager;
        this.trafficDensity = trafficDensity;
        createMapView();
        VisualizationTimer visualizationTimer = new VisualizationTimer(this);
        visualizationTimer.start();
    }

    private void createMapView() {
        this.mapView = new MapView(this.carsManager, this.trafficDensity, this.graphOSM, (int) getWidth(), (int) getHeight());
    }

    @Override
    public boolean isResizable() {
        return true;
    }

    public void draw(double delta) {
        GraphicsContext gc = this.getGraphicsContext2D();
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0, 0, getWidth(), getHeight());
        if (isMapResized) {
            createMapView();
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
