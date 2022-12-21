package pl.edu.agh.wwwrsrm.visualization.execution;

import javafx.animation.AnimationTimer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edu.agh.wwwrsrm.window.map.Map;
import pl.edu.agh.wwwrsrm.window.menu.Menu;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class VisualizationTimer extends AnimationTimer {

    private final Map map;
    private final Menu menu;
    private long previous;

    @Override
    public void handle(long now) {
        double deltaTime = ((double) (now - previous)) / 1000000000.0;
        previous = now;
        map.draw(deltaTime);
        menu.refresh();
    }

    @PostConstruct
    public void init() {
        start();
    }
}
