package pl.edu.agh.wwwrsrm.window.menu;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edu.agh.wwwrsrm.window.Style;

import javax.annotation.PostConstruct;


@Component
@RequiredArgsConstructor
public class Menu extends VBox {

    private final StartButton startButton;
    private final ResumeButton resumeButton;
    private final StopButton stopButton;
    private final EndButton endButton;
    private final TimeScrollBarLabel timeScrollBarLabel;
    private final TimeScrollBar timeScrollBar;

    @PostConstruct
    private void init() {
        this.setPrefWidth(Style.MENU_WIDTH);
        this.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        this.getChildren().addAll(startButton, resumeButton, stopButton, endButton, timeScrollBarLabel, timeScrollBar);
    }

    public void refresh() {
        timeScrollBarLabel.setOrUpdateVisualizationSpeed();
    }

}
