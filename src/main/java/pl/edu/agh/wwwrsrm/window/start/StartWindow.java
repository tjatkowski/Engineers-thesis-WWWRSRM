package pl.edu.agh.wwwrsrm.window.start;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import pl.edu.agh.wwwrsrm.Visualization;

import static pl.edu.agh.wwwrsrm.window.Style.*;

public class StartWindow extends BorderPane {

    private final Visualization visualization;

    public StartWindow(Visualization visualization) {
        this.visualization = visualization;
        this.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setCenter(new StartWindowBox());
    }

    private class StartWindowBox extends VBox {
        MapFileLabel mapFileLabel = new MapFileLabel();
        MapFileTextInput mapFileTextInput = new MapFileTextInput();
        MapFileButton mapFileButton = new MapFileButton();

        public StartWindowBox() {
            this.setPrefSize(START_WINDOW_WIDTH, START_WINDOW_HEIGHT);
            this.setPadding(new Insets(10));
            this.setSpacing(10);
            this.setAlignment(Pos.CENTER);

            this.getChildren().addAll(mapFileLabel, mapFileTextInput, mapFileButton);
        }

        private class MapFileLabel extends Label {
            public MapFileLabel() {
                this.setPrefWidth(START_WINDOW_WIDTH);
                this.setText("Enter OSM map file path (.pbf)");
            }
        }

        private class MapFileTextInput extends TextField {
            public MapFileTextInput() {
                this.setMaxWidth(START_WINDOW_WIDTH);
            }
        }

        private class MapFileButton extends Button {
            public MapFileButton() {
                this.setText("Submit");
                this.setPrefWidth(SUBMIT_BUTTON_WIDTH);
                this.setOnMouseClicked(this::onMouseClicked);
            }

            private void onMouseClicked(MouseEvent mouseEvent) {
                if (!MouseButton.PRIMARY.equals(mouseEvent.getButton())) {
                    return;
                }
                visualization.setMapFilePath(mapFileTextInput.getText());
                visualization.setLoadingWindow();
            }
        }
    }
}
