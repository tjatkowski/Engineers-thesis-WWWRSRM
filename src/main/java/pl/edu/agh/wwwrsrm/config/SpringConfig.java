package pl.edu.agh.wwwrsrm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import pl.edu.agh.wwwrsrm.graph.GraphOSM;
import pl.edu.agh.wwwrsrm.osm.osmParser;
import pl.edu.agh.wwwrsrm.visualization.MapPane;

@Configuration
public class SpringConfig {

    @Bean
    public MapPane mapPane() {
        GraphOSM osm_graph = osmParser.CreateGraph("src/main/resources/osm/cracow.pbf");
        return new MapPane(osm_graph);
    }

    @Bean
    public TaskScheduler taskScheduler() {
        return new ThreadPoolTaskScheduler();
    }

}
