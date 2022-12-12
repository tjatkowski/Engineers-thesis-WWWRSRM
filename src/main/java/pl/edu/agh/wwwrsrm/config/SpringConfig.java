package pl.edu.agh.wwwrsrm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import pl.edu.agh.wwwrsrm.graph.GraphOSM;
import pl.edu.agh.wwwrsrm.osm.OsmParser;

@Configuration
public class SpringConfig {

    @Bean
    public TaskScheduler taskScheduler() {
        return new ThreadPoolTaskScheduler();
    }

    @Bean
    public GraphOSM graphOSM() {
        return OsmParser.CreateGraph("src/main/resources/osm/half_cracow.pbf", "src/main/resources/osm/parameters/way_parameters.json");
    }

}
