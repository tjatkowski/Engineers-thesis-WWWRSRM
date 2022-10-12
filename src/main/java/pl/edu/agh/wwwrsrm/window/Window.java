package pl.edu.agh.wwwrsrm.window;

import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import org.springframework.stereotype.Component;
import pl.edu.agh.wwwrsrm.window.menu.Menu;
import pl.edu.agh.wwwrsrm.window.map.Map;

@Component
public class Window  {
    private final Scene scene;

    public Window(Menu menu, Map map) {
        HBox hbox = new HBox();
        hbox.setPrefSize(Style.windowWidth, Style.windowHeight);

        hbox.getChildren().add(menu);
        hbox.getChildren().add(map);

        scene = new Scene(hbox);
    }

    public Scene getScene() {
        return scene;
    }
}
