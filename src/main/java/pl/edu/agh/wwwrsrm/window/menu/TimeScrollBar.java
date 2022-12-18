package pl.edu.agh.wwwrsrm.window.menu;

import javafx.geometry.Orientation;
import javafx.scene.control.ScrollBar;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;
import pl.edu.agh.wwwrsrm.connection.producer.VisualizationStateChangeProducer;
import pl.edu.agh.wwwrsrm.window.map.Map;

import javax.annotation.PostConstruct;
import java.math.RoundingMode;

import static pl.edu.agh.wwwrsrm.window.Style.MENU_WIDTH;

@Slf4j
@Component
@RequiredArgsConstructor
public class TimeScrollBar extends ScrollBar {

    private final VisualizationStateChangeProducer visualizationStateChangeProducer;
    private final Map visualizationMap;

    @PostConstruct
    public void init() {
        this.setPrefWidth(MENU_WIDTH);
        this.setOrientation(Orientation.HORIZONTAL);
        this.setMin(0);
        this.setMax(2);
        this.setValue(1);
        this.setUnitIncrement(0.2);
        this.setVisibleAmount(1);
        this.valueProperty().addListener((observableValue, oldValue, newValue) -> onBarScrolled(newValue.doubleValue()));
        visualizationMap.setVisualizationSpeed(1000);
    }

    public void onBarScrolled(double time) {
        int timeInMilliSeconds = NumberUtils.toScaledBigDecimal(time, 3, RoundingMode.DOWN)
                .movePointRight(3)
                .intValue();
        log.info("Set visualizationSpeed to {} milliseconds", timeInMilliSeconds);
        visualizationMap.setVisualizationSpeed(timeInMilliSeconds);
        if (null == visualizationMap.getVisualizationRunningState()) {
            return;
        }
        visualizationStateChangeProducer.sendStateChangeMessage(visualizationMap);
    }

}
