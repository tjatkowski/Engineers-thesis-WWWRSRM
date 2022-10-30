package pl.edu.agh.wwwrsrm.window.menu;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.wwwrsrm.connection.producer.VisualizationStateChangeProducer;
import pl.edu.agh.wwwrsrm.window.Style;
import proto.model.RUNNING_STATE;

import javax.annotation.PostConstruct;

@Component
public class Menu extends VBox {
    @Autowired
    VisualizationStateChangeProducer stateChanger;

    public Menu() {
        setPrefWidth(Style.menuWidth);
        this.setStyle("-fx-background-color: #FF00FF;");
        this.setAlignment(Pos.TOP_CENTER);


        Button startButton = new Button("Start");
        Button stopButton = new Button("Stop");
        this.getChildren().add(startButton);
        this.getChildren().add(stopButton);

        startButton.onMouseClickedProperty().addListener((obs, oldValue, newValue) -> {
            stateChanger.sendStateChangeMessage(RUNNING_STATE.STARTED);
        });

        stopButton.onMouseClickedProperty().addListener((obs, oldValue, newValue) -> {
            stateChanger.sendStateChangeMessage(RUNNING_STATE.STOPPED);
        });
    }
}
