package pl.edu.agh.wwwrsrm;


import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.edu.agh.wwwrsrm.visualization.App;

@SpringBootApplication
public class SimulationVisualization {

    public static void main(String[] args) {
        Application.launch(App.class, args);
    }
}
