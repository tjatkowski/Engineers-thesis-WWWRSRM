package pl.edu.agh.wwwrsrm.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.agh.wwwrsrm.exceptions.MapFilePathException;
import pl.edu.agh.wwwrsrm.graph.GraphOSM;
import pl.edu.agh.wwwrsrm.osm.OsmParser;

@Configuration
@RequiredArgsConstructor
public class SpringConfig {

    @Value("${mapFilePath}")
    private String mapFilePath;

    @Bean
    public GraphOSM graphOSM() throws MapFilePathException {
        return OsmParser.CreateGraph(mapFilePath, "src/main/resources/osm/parameters/way_parameters.json");
    }

}
