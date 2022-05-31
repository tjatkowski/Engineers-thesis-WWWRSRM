package pl.edu.agh.wwwrsrm.visualization;

import javafx.event.EventHandler;
import javafx.scene.input.ScrollEvent;
import lombok.AllArgsConstructor;

/**
 * MapZoomHandler handles Scroll event to zoom in and zoom out the map view.
 */
@AllArgsConstructor
public class MapZoomHandler implements EventHandler<ScrollEvent> {
    private final MapPane mapPane;

    @Override
    public void handle(ScrollEvent scrollEvent) {
        if (scrollEvent.getEventType().equals(ScrollEvent.SCROLL)){
            double deltaY = scrollEvent.getDeltaY();
            this.mapPane.zoomMapView((deltaY > 0) ? 1 : -1);
        }
    }
}

