package pl.edu.agh.wwwrsrm;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import pl.edu.agh.wwwrsrm.window.Window;


public class Visualization extends Application {
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
        stage.setTitle("WWWRSRM - Traffic Visualization");
        stage.setMaximized(true);

        Window window = this.context.getBean(Window.class);
        stage.setScene(window.getScene());
        stage.show();

        // uncomment this to start consuming cars immediately after application start
//        this.context.publishEvent(new ApplicationStartedEvent(this));
    }

    @Override
    public void stop() {
        this.context.stop();
        Platform.exit();
    }
}
