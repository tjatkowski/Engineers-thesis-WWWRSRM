package pl.edu.agh.wwwrsrm.window.map;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import org.springframework.stereotype.Component;
import pl.edu.agh.wwwrsrm.window.menu.Menu;

import static pl.edu.agh.wwwrsrm.window.constants.Style.*;

@Component
public class Window {
    private final Scene scene;

    public Window(Menu menu, Map map) {
        BorderPane borderPane = new BorderPane();
        borderPane.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        borderPane.setCenter(map);
        map.widthProperty().bind(borderPane.widthProperty().subtract(MENU_WIDTH));
        map.heightProperty().bind(borderPane.heightProperty());

        borderPane.setRight(menu);

        scene = new Scene(borderPane);
    }

    public Scene getScene() {
        return scene;
    }
}
