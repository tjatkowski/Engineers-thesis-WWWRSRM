module SimulationVisualization {
    requires javafx.controls;
    requires javafx.fxml;
    requires osmosis.pbf;
    requires osmosis.core;


    opens visualization to javafx.fxml;
    exports visualization;
}
