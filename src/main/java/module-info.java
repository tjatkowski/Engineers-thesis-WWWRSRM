module SimulationVisualization {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires osmosis.pbf;
    requires osmosis.core;


    opens pl.edu.agh.wwwrsrm.visualization to javafx.fxml;
    exports pl.edu.agh.wwwrsrm.visualization;
    exports pl.edu.agh.wwwrsrm.graph;
    exports pl.edu.agh.wwwrsrm.osm;
}
