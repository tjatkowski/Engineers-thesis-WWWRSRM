package pl.edu.agh.wwwrsrm.visualization;

import javafx.event.EventHandler;
import javafx.scene.input.ScrollEvent;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import pl.edu.agh.wwwrsrm.utils.constants.Zoom;
import pl.edu.agh.wwwrsrm.window.map.Map;

/**
 * MapZoomHandler handles Scroll event to zoom in and zoom out the map view.
 */
@RequiredArgsConstructor
public class MapZoomHandler implements EventHandler<ScrollEvent> {
    private final Map map;

    private long lastHandle = 0;

    @Override
    public void handle(ScrollEvent scrollEvent) {
        // Delay made for touchpad scorlling. It was too fast without that
        long current = System.currentTimeMillis();
        if (lastHandle + 100 > current)
            return;
        lastHandle = current;
        if (scrollEvent.getEventType().equals(ScrollEvent.SCROLL)){
            double deltaY = scrollEvent.getDeltaY();
            map.zoomMapView((deltaY > 0) ? Zoom.IN : Zoom.OUT);
        }
    }
}

