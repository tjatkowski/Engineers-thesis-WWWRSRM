module SimulationVisualization {
    requires javafx.controls;
    requires javafx.fxml;
    requires osmosis.pbf;
    requires osmosis.core;


    opens pl.edu.agh.wwwrsrm.visualization to javafx.fxml;
    exports pl.edu.agh.wwwrsrm.visualization;
    exports pl.edu.agh.wwwrsrm.graph;
}
