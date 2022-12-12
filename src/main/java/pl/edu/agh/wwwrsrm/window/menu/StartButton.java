package pl.edu.agh.wwwrsrm.window.menu;

import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.springframework.stereotype.Component;
import pl.edu.agh.wwwrsrm.connection.producer.VisualizationStateChangeProducer;
import proto.model.VisualizationStateChangeMessage;

import static pl.edu.agh.wwwrsrm.window.Style.MENU_WIDTH;
import static proto.model.RUNNING_STATE.STARTED;

@Component
public class StartButton extends Button {

    private final VisualizationStateChangeProducer visualizationStateChangeProducer;

    public StartButton(VisualizationStateChangeProducer visualizationStateChangeProducer) {
        this.visualizationStateChangeProducer = visualizationStateChangeProducer;
        this.setText("Start");
        this.setPrefWidth(MENU_WIDTH);
        this.setOnMouseClicked(this::onMouseClicked);
    }

    public void onMouseClicked(MouseEvent mouseEvent) {
        if (!MouseButton.PRIMARY.equals(mouseEvent.getButton())) {
            return;
        }
        VisualizationStateChangeMessage visualizationStateChangeMessage = VisualizationStateChangeMessage.newBuilder()
                .setStateChange(STARTED)
                .build();
        visualizationStateChangeProducer.sendStateChangeMessage(visualizationStateChangeMessage);
    }

}
