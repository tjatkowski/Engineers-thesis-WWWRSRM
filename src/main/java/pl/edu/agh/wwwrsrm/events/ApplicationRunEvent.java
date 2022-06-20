package pl.edu.agh.wwwrsrm.events;

import org.springframework.context.ApplicationEvent;
import pl.edu.agh.wwwrsrm.visualization.MapPane;

public class ApplicationRunEvent extends ApplicationEvent {

    public ApplicationRunEvent(MapPane mapPane) {
        super(mapPane);
    }

    public MapPane getMapPane() {
        return (MapPane) getSource();
    }
}
