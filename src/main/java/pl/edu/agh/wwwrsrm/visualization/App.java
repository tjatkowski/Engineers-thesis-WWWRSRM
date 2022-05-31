package pl.edu.agh.wwwrsrm.visualization;

import org.springframework.stereotype.Service;
import pl.edu.agh.wwwrsrm.graph.GraphOSM;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.edu.agh.wwwrsrm.osm.osmParser;

/**
 * App class is the main class in the program
 */
@Service
public class App extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        GraphOSM osm_graph = osmParser.CreateGraph("src\\main\\resources\\osm\\cracow.pbf");

        MapPane mapPane = new MapPane(osm_graph);
//        mapPane.drawNodes();
        mapPane.drawLines();

        Scene scene = new Scene(mapPane);
        stage.setScene(scene);

        stage.show();
    }
}