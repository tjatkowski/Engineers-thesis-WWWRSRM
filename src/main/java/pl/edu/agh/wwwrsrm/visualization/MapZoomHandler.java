package pl.edu.agh.wwwrsrm.visualization;

import javafx.event.EventHandler;
import javafx.scene.input.ScrollEvent;
import lombok.AllArgsConstructor;
import pl.edu.agh.wwwrsrm.utils.constants.Zoom;
import pl.edu.agh.wwwrsrm.window.map.Map;

/**
 * MapZoomHandler handles Scroll event to zoom in and zoom out the map view.
 */
@AllArgsConstructor
public class MapZoomHandler implements EventHandler<ScrollEvent> {
    private final Map map;

    @Override
    public void handle(ScrollEvent scrollEvent) {
        if (scrollEvent.getEventType().equals(ScrollEvent.SCROLL)){
            double deltaY = scrollEvent.getDeltaY();
            map.zoomMapView((deltaY > 0) ? Zoom.IN : Zoom.OUT);
        }
    }
}

