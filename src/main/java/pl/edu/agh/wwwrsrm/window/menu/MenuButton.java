package pl.edu.agh.wwwrsrm.window.menu;

import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import lombok.RequiredArgsConstructor;
import pl.edu.agh.wwwrsrm.connection.producer.VisualizationStateChangeProducer;
import pl.edu.agh.wwwrsrm.window.map.Map;
import proto.model.RUNNING_STATE;

import javax.annotation.PostConstruct;

import static pl.edu.agh.wwwrsrm.window.Style.MENU_WIDTH;

@RequiredArgsConstructor
abstract public class MenuButton extends Button {

    private final VisualizationStateChangeProducer visualizationStateChangeProducer;
    private final Map visualizationMap;

    @PostConstruct
    protected void init() {
        this.setText(getButtonName());
        this.setPrefWidth(MENU_WIDTH);
        this.setOnMouseClicked(this::onMouseClicked);
    }

    protected void onMouseClicked(MouseEvent mouseEvent) {
        if (!MouseButton.PRIMARY.equals(mouseEvent.getButton())) {
            return;
        }
        visualizationMap.setVisualizationRunningState(getRunningState());
        visualizationStateChangeProducer.sendStateChangeMessage(visualizationMap);
    }

    protected abstract String getButtonName();

    protected abstract RUNNING_STATE getRunningState();
}
