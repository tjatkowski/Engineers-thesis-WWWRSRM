package pl.edu.agh.wwwrsrm.window.menu;

import javafx.geometry.Orientation;
import javafx.scene.control.ScrollBar;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.edu.agh.wwwrsrm.connection.producer.VisualizationStateChangeProducer;

import javax.annotation.PostConstruct;

import static pl.edu.agh.wwwrsrm.window.Style.MENU_WIDTH;

@Slf4j
@Component
@RequiredArgsConstructor
public class TimeScrollBar extends ScrollBar {

    private final VisualizationStateChangeProducer visualizationStateChangeProducer;

    @PostConstruct
    public void init() {
        this.setPrefWidth(MENU_WIDTH);
        this.setOrientation(Orientation.HORIZONTAL);
        this.setMin(0);
        this.setMax(2);
        this.setValue(1);
        this.setUnitIncrement(0.2);
        this.setBlockIncrement(0.2);
        this.valueProperty().addListener((observableValue, oldValue, newValue) -> onBarScrolled(newValue.doubleValue()));
    }

    //TODO implement user time change
    public void onBarScrolled(double time) {
        log.info("TimeScrollBar changed time value: {}", time);
    }

}
