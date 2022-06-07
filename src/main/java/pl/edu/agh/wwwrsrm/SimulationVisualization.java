package pl.edu.agh.wwwrsrm;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import pl.edu.agh.wwwrsrm.graph.GraphOSM;
import pl.edu.agh.wwwrsrm.osm.osmParser;
import pl.edu.agh.wwwrsrm.visualization.AppPane;
import pl.edu.agh.wwwrsrm.visualization.ConfigPane;
import pl.edu.agh.wwwrsrm.visualization.MapPane;


/**
 * SimulationVisualization class is the main class in the program
 */
@SpringBootApplication
public class SimulationVisualization extends Application {

    private ConfigurableApplicationContext context;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void init() {
        this.context = new SpringApplicationBuilder(SimulationVisualization.class).run();
    }


    // TODO: Add responsiveness (Actually working well on FullHD (1920x1080))
    @Override
    public void start(Stage stage) {
        GraphOSM osm_graph = osmParser.CreateGraph("src\\main\\resources\\osm\\cracow.pbf");
        stage.setTitle("WWWRSRM - Traffic Visualization");
        stage.setMaximized(true);
        AppPane appPane = new AppPane();

        MapPane mapPane = new MapPane(osm_graph);
//        mapPane.drawNodes();
        mapPane.drawLines();

        ConfigPane configPane = new ConfigPane();
        configPane.loadConfig();

        appPane.getChildren().addAll(mapPane, configPane);

        Scene scene = new Scene(appPane);
        stage.setScene(scene);

        stage.show();
    }

    @Override
    public void stop() throws Exception {
        this.context.stop();
        Platform.exit();
    }
}