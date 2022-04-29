package visualization;

import javafx.event.EventHandler;
import javafx.scene.input.ScrollEvent;

/**
 * MapZoomHandler handles Scroll event to zoom in and zoom out the map view.
 */
public class MapZoomHandler implements EventHandler<ScrollEvent> {
    private final MapPane mapPane;

    public MapZoomHandler(MapPane mapPane){
        this.mapPane = mapPane;
    }

    @Override
    public void handle(ScrollEvent scrollEvent) {
        if (scrollEvent.getEventType().equals(ScrollEvent.SCROLL)){
            double deltaY = scrollEvent.getDeltaY();
            this.mapPane.zoomMapView((deltaY > 0) ? 1 : -1);
        }
    }
}

