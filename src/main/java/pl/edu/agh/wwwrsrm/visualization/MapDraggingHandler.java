package pl.edu.agh.wwwrsrm.visualization;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * MapDraggingHandler handles mouse dragging on the map.
 * It computes delta X and delta Y to shift map view.
 */
public class MapDraggingHandler implements EventHandler<MouseEvent> {
    private final MapPane mapPane;
    private double lastMouseX;
    private double lastMouseY;
    private double currMouseX;
    private double currMouseY;

    public MapDraggingHandler(MapPane mapPane){
        this.mapPane = mapPane;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_PRESSED)){
            this.currMouseX = mouseEvent.getX();
            this.currMouseY = mouseEvent.getY();
        } else if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_DRAGGED)){
            this.lastMouseX = this.currMouseX;
            this.lastMouseY = this.currMouseY;
            this.currMouseX = mouseEvent.getX();
            this.currMouseY = mouseEvent.getY();
            dragMap();
        }
    }

    public void dragMap(){
        double xDelta = this.currMouseX - this.lastMouseX;
        double yDelta = this.currMouseY - this.lastMouseY;
        this.mapPane.dragMapViewByVector(xDelta, yDelta);
    }
}
