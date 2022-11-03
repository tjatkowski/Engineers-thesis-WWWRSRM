package pl.edu.agh.wwwrsrm.window.menu;

import javafx.scene.layout.VBox;
import org.springframework.stereotype.Component;
import pl.edu.agh.wwwrsrm.window.Style;

import javax.annotation.PostConstruct;

@Component
public class Menu extends VBox {
    public Menu() {
        setPrefWidth(Style.menuWidth);
        this.setStyle("-fx-background-color: #FF00FF;");
    }
}
