package pl.edu.agh.wwwrsrm;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Setter;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.Lifecycle;
import pl.edu.agh.wwwrsrm.window.Window;
import pl.edu.agh.wwwrsrm.window.start.LoadingWindow;
import pl.edu.agh.wwwrsrm.window.start.StartWindow;

import java.util.Optional;


public class Visualization extends Application {
    private ConfigurableApplicationContext context;
    private Stage stage;

    @Setter
    private String mapFilePath;

    @Override
    @SneakyThrows
    public void start(Stage stage) {
        this.stage = stage;
        this.stage.setTitle("WWWRSRM - Traffic Visualization");
        stage.setMaximized(true);
        setStartWindow();
    }

    public void setStartWindow() {
        StartWindow startWindow = new StartWindow(this);
        stage.setScene(new Scene(startWindow));
        stage.show();
    }

    public void setLoadingWindow() {
        LoadingWindow loadingWindow = new LoadingWindow();
        stage.setScene(new Scene(loadingWindow));
        stage.show();
        Platform.runLater(this::loadWindow);
    }

    public void loadWindow() {
        this.context = new SpringApplicationBuilder(SimulationVisualization.class)
                .properties("mapFilePath=" + mapFilePath)
                .listeners(new ApplicationFailedListener())
                .run();
        setWindowScene();
    }

    public void setWindowScene() {
        Window window = this.context.getBean(Window.class);
        stage.setScene(window.getScene());
        stage.show();
    }

    @Override
    public void stop() {
        Optional.ofNullable(context).ifPresent(Lifecycle::stop);
        Platform.exit();
    }

    private class ApplicationFailedListener implements ApplicationListener<ApplicationFailedEvent> {
        @Override
        public void onApplicationEvent(@NotNull ApplicationFailedEvent event) {
            stop();
        }
    }
}
