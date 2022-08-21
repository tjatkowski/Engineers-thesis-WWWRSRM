package pl.edu.agh.wwwrsrm;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import pl.edu.agh.wwwrsrm.events.ApplicationRunEvent;
import pl.edu.agh.wwwrsrm.graph.GraphOSM;
import pl.edu.agh.wwwrsrm.osm.osmParser;
import pl.edu.agh.wwwrsrm.visualization.AppPane;
import pl.edu.agh.wwwrsrm.visualization.ConfigPane;
import pl.edu.agh.wwwrsrm.visualization.MapPane;


public class Visualization extends Application {
    private AppPane appPane;
    private MapPane mapPane;
    private ConfigurableApplicationContext context;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void init() {
        // parse OSM map
        GraphOSM osm_graph = osmParser.CreateGraph("src/main/resources/osm/cracow.pbf");
        // App pane
        this.appPane = new AppPane();
        // Map pane
        this.mapPane = new MapPane(osm_graph);
        // Config pane
        ConfigPane configPane = new ConfigPane();

        // prepare
        this.mapPane.drawLines();
        configPane.loadConfig();

        appPane.getChildren().addAll(this.mapPane, configPane);

        this.context = new SpringApplicationBuilder(SimulationVisualization.class).run();
    }


    // TODO: Add responsiveness (Actually working well on FullHD (1920x1080))
    @Override
    public void start(Stage stage) {
        stage.setTitle("WWWRSRM - Traffic Visualization");
        stage.setMaximized(true);

        Scene scene = new Scene(this.appPane);
        stage.setScene(scene);
        stage.show();

        this.context.publishEvent(new ApplicationRunEvent(this.mapPane));
    }

    @Override
    public void stop() throws Exception {
        this.context.stop();
        Platform.exit();
    }
}
