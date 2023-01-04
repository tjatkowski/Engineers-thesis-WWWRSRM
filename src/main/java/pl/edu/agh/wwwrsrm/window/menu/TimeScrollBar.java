package pl.edu.agh.wwwrsrm.window.menu;

import javafx.geometry.Orientation;
import javafx.scene.control.ScrollBar;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;
import pl.edu.agh.wwwrsrm.connection.producer.VisualizationStateChangeProducer;
import pl.edu.agh.wwwrsrm.window.map.Map;

import javax.annotation.PostConstruct;
import java.math.RoundingMode;
import java.util.Optional;

import static pl.edu.agh.wwwrsrm.window.constants.Style.MENU_WIDTH;
import static proto.model.RUNNING_STATE.STOPPED;

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
        this.setMin(0.1);
        this.setMax(2.0);
        this.setValue(1.0);
        this.setUnitIncrement(0.5);
        this.setVisibleAmount(1.0);
        this.valueProperty().addListener((observableValue, oldValue, newValue) -> onBarScrolled(newValue.doubleValue()));
        visualizationMap.setTimeMultiplier((int) (this.getValue()));
    }

    public void onBarScrolled(double value) {
        double realTimeMultiplier = NumberUtils.toScaledBigDecimal(value, 1, RoundingMode.HALF_EVEN).doubleValue();
        log.info("Set real value multiplier to {}", realTimeMultiplier);
        visualizationMap.setTimeMultiplier(realTimeMultiplier);
        Optional.ofNullable(visualizationMap.getVisualizationRunningState())
                .filter(runningState -> ObjectUtils.notEqual(STOPPED, runningState))
                .ifPresent(runningState -> visualizationStateChangeProducer.sendStateChangeMessage(visualizationMap));
    }

}
