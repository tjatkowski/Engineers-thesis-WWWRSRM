package pl.edu.agh.wwwrsrm.visualization;

import javafx.scene.layout.Pane;

public class ConfigPane extends Pane {

    private static final int CONFIG_WIDTH = 1100;
    private static final int CONFIG_HEIGHT = 800;

    public ConfigPane() {
        this.setStyle("-fx-background-color: #adadad;");
        this.setPrefSize(CONFIG_WIDTH,CONFIG_HEIGHT);
        // Non-responsive translation
        this.setTranslateX(1100);
    }

    public void loadConfig(){

    }
}
