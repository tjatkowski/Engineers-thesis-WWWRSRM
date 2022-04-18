package visualization;

import graph.OSM_Graph;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import osm.OSM_Parser;

/**
 * App class is the main class in the program
 */
public class App extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        OSM_Graph osm_graph = OSM_Parser.CreateGraph("src\\main\\resources\\osm\\agh_student_town.pbf");

        MapPane mapPane = new MapPane(osm_graph);
//        mapPane.drawNodes();
        mapPane.drawLines();

        Scene scene = new Scene(mapPane);
        stage.setScene(scene);
        stage.show();
    }
}