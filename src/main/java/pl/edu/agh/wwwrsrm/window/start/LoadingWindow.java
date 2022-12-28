package pl.edu.agh.wwwrsrm.window.start;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import static pl.edu.agh.wwwrsrm.window.Style.WINDOW_HEIGHT;
import static pl.edu.agh.wwwrsrm.window.Style.WINDOW_WIDTH;

public class LoadingWindow extends BorderPane {

    public LoadingWindow() {
        this.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setCenter(new Label("Loading map in progress ..."));
    }

}
