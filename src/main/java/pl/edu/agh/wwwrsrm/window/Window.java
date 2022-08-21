package pl.edu.agh.wwwrsrm.window;

import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import pl.edu.agh.wwwrsrm.window.menu.Menu;
import pl.edu.agh.wwwrsrm.window.map.Map;

public class Window  {
    private HBox hbox;
    private Scene scene;

    public Window() {
        hbox = new HBox();
        hbox.setPrefSize(Style.windowWidth, Style.windowHeight);

        hbox.getChildren().add(new Menu(Style.menuWidth));
        hbox.getChildren().add(new Map(Style.windowWidth - Style.menuWidth, Style.windowHeight));

        scene = new Scene(hbox);
    }

    public Scene getScene() {
        return scene;
    }
}
