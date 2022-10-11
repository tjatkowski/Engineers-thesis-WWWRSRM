package pl.edu.agh.wwwrsrm;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import pl.edu.agh.wwwrsrm.events.ApplicationStartedEvent;
import pl.edu.agh.wwwrsrm.visualization.AppPane;
import pl.edu.agh.wwwrsrm.visualization.ConfigPane;
import pl.edu.agh.wwwrsrm.visualization.drawing.MapDrawer;
import pl.edu.agh.wwwrsrm.visualization.MapPane;
import pl.edu.agh.wwwrsrm.window.Window;


public class Visualization extends Application {
    private AppPane appPane;
    private ConfigurableApplicationContext context;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void init() {
        this.context = new SpringApplicationBuilder(SimulationVisualization.class).run();

        // App pane
        this.appPane = new AppPane();
        // Map pane
        MapDrawer mapDrawer = context.getBean(MapDrawer.class);
        // Config pane
        ConfigPane configPane = new ConfigPane();

        // prepare
        mapDrawer.drawLines();
        configPane.loadConfig();

        appPane.getChildren().addAll(mapDrawer.getMapPane(), configPane);
    }


    // TODO: Add responsiveness (Actually working well on FullHD (1920x1080))
    @Override
    public void start(Stage stage) {
        stage.setTitle("WWWRSRM - Traffic Visualization");
        stage.setMaximized(true);

//        Scene scene = new Scene(this.appPane);
//        stage.setScene(scene);
        Window window = new Window();
        stage.setScene(window.getScene());
        stage.show();

        // uncomment this to start consuming cars immediately after application start
        this.context.publishEvent(new ApplicationStartedEvent(this));
    }

    @Override
    public void stop() throws Exception {
        this.context.stop();
        Platform.exit();
    }
}
