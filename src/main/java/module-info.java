module SimulationVisualization {
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.core;
    requires spring.beans;
    requires spring.kafka;
    requires javafx.controls;
    requires javafx.fxml;
    requires osmosis.pbf;
    requires osmosis.core;
    requires static lombok;
    requires org.slf4j;
    requires kafka.clients;
    requires java.sql;
    requires com.google.protobuf;
    requires java.annotation;


    opens pl.edu.agh.wwwrsrm.visualization to javafx.fxml, spring.core;
    opens pl.edu.agh.wwwrsrm;
    opens pl.edu.agh.wwwrsrm.consumer.config to spring.core;
    opens pl.edu.agh.wwwrsrm.consumer to spring.core;
    exports pl.edu.agh.wwwrsrm.consumer to spring.beans;
    exports pl.edu.agh.wwwrsrm.consumer.config to spring.beans, spring.context;
    exports pl.edu.agh.wwwrsrm.visualization;
    exports pl.edu.agh.wwwrsrm.utils.coordinates;
    exports pl.edu.agh.wwwrsrm.utils.window;
    exports pl.edu.agh.wwwrsrm.utils.constants;
    exports pl.edu.agh.wwwrsrm.utils;
    exports pl.edu.agh.wwwrsrm.graph;
    exports pl.edu.agh.wwwrsrm.osm;
    exports pl.edu.agh.wwwrsrm;
    exports pl.edu.agh.wwwrsrm.model;
    exports pl.edu.agh.wwwrsrm.events;
    exports proto.model;
}
