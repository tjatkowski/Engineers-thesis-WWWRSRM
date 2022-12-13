package pl.edu.agh.wwwrsrm.window.menu;

import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.springframework.stereotype.Component;
import pl.edu.agh.wwwrsrm.connection.producer.VisualizationStateChangeProducer;
import proto.model.VisualizationStateChangeMessage;

import static pl.edu.agh.wwwrsrm.window.Style.MENU_WIDTH;
import static proto.model.RUNNING_STATE.STOPPED;

@Component
public class StopButton extends Button {

    private final VisualizationStateChangeProducer visualizationStateChangeProducer;

    public StopButton(VisualizationStateChangeProducer visualizationStateChangeProducer) {
        this.visualizationStateChangeProducer = visualizationStateChangeProducer;
        this.setText("Stop");
        this.setPrefWidth(MENU_WIDTH);
        this.setOnMouseClicked(this::onMouseClicked);
    }

    public void onMouseClicked(MouseEvent mouseEvent) {
        if (!MouseButton.PRIMARY.equals(mouseEvent.getButton())) {
            return;
        }
        VisualizationStateChangeMessage visualizationStateChangeMessage = VisualizationStateChangeMessage.newBuilder()
                .setStateChange(STOPPED)
                .build();
        visualizationStateChangeProducer.sendStateChangeMessage(visualizationStateChangeMessage);
    }
}
