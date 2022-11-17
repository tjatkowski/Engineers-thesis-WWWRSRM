package pl.edu.agh.wwwrsrm.window.menu;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Component;
import pl.edu.agh.wwwrsrm.connection.producer.VisualizationStateChangeProducer;
import pl.edu.agh.wwwrsrm.window.Style;
import proto.model.VisualizationStateChangeMessage;

import static proto.model.RUNNING_STATE.*;


@Component
public class Menu extends VBox {

    public Menu(VisualizationStateChangeProducer stateChanger) {
        setPrefWidth(Style.menuWidth);
        this.setStyle("-fx-background-color: #FF00FF;");
        this.setAlignment(Pos.TOP_CENTER);


        Button startButton = new Button("Start");
        Button resumeButton = new Button("Resume");
        Button stopButton = new Button("Stop");
        startButton.setPrefWidth(Style.menuWidth);
        resumeButton.setPrefWidth(Style.menuWidth);
        stopButton.setPrefWidth(Style.menuWidth);
        this.getChildren().add(startButton);
        this.getChildren().add(resumeButton);
        this.getChildren().add(stopButton);
        startButton.setOnMouseClicked(event -> {
            if (MouseButton.PRIMARY.equals(event.getButton())) {
                //TODO fill rest of the values
                VisualizationStateChangeMessage visualizationStateChangeMessage = VisualizationStateChangeMessage.newBuilder()
                        .setStateChange(STARTED)
                        .build();
                stateChanger.sendStateChangeMessage(visualizationStateChangeMessage);
            }
        });
        resumeButton.setOnMouseClicked(event -> {
            if (MouseButton.PRIMARY.equals(event.getButton())) {
                //TODO fill rest of the values
                VisualizationStateChangeMessage visualizationStateChangeMessage = VisualizationStateChangeMessage.newBuilder()
                        .setStateChange(RESUMED)
                        .build();
                stateChanger.sendStateChangeMessage(visualizationStateChangeMessage);
            }
        });
        stopButton.setOnMouseClicked(event -> {
            if (MouseButton.PRIMARY.equals(event.getButton())) {
                //TODO fill rest of the values
                VisualizationStateChangeMessage visualizationStateChangeMessage = VisualizationStateChangeMessage.newBuilder()
                        .setStateChange(STOPPED)
                        .build();
                stateChanger.sendStateChangeMessage(visualizationStateChangeMessage);
            }
        });
    }
}
