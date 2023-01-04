package pl.edu.agh.wwwrsrm.window.menu;

import javafx.scene.control.Label;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edu.agh.wwwrsrm.window.map.Map;

import javax.annotation.PostConstruct;

import static pl.edu.agh.wwwrsrm.window.constants.Style.MENU_WIDTH;

@Component
@RequiredArgsConstructor
public class TimeScrollBarLabel extends Label {

    private final Map visualizationMap;

    @PostConstruct
    public void init() {
        setOrUpdateTimeMultiplier();
        this.setPrefWidth(MENU_WIDTH);
    }

    public void setOrUpdateTimeMultiplier() {
        this.setText("Time multiplier: " + visualizationMap.getTimeMultiplier());
    }

}
