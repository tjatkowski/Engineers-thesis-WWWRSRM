package pl.edu.agh.wwwrsrm;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * SimulationVisualization class is the main class in the program
 */
@SpringBootApplication
public class SimulationVisualization {

    public static void main(String[] args) {
        Application.launch(Visualization.class, args);
    }
}