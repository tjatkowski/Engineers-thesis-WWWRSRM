package pl.edu.agh.wwwrsrm.window.menu;

import javafx.scene.control.Label;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edu.agh.wwwrsrm.window.map.Map;

import javax.annotation.PostConstruct;

import static pl.edu.agh.wwwrsrm.window.Style.MENU_WIDTH;

@Component
@RequiredArgsConstructor
public class TimeScrollBarLabel extends Label {

    private final Map visualizationMap;

    @PostConstruct
    public void init() {
        setOrUpdateVisualizationSpeed();
        this.setPrefWidth(MENU_WIDTH);
    }

    public void setOrUpdateVisualizationSpeed() {
        this.setText("Step time: " + visualizationMap.getVisualizationSpeed());
    }

}
