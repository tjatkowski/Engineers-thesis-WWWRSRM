package pl.edu.agh.wwwrsrm.visualization;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import pl.edu.agh.wwwrsrm.window.map.Map;

/**
 * MapDraggingHandler handles mouse dragging on the map.
 * It computes delta X and delta Y to shift map view.
 */
public class MapDraggingHandler implements EventHandler<MouseEvent> {
    private final Map map;
    private double lastMouseX;
    private double lastMouseY;
    private double currMouseX;
    private double currMouseY;

    public MapDraggingHandler(Map mapPane){
        this.map = mapPane;
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
        double xDelta = this.lastMouseX - this.currMouseX;
        double yDelta = this.lastMouseY - this.currMouseY;
        this.map.dragMapViewByVector(xDelta, yDelta);
    }
}
