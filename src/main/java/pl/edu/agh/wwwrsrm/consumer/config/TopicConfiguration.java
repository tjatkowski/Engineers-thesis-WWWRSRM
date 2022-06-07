package pl.edu.agh.wwwrsrm.consumer.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicConfiguration {

    public static final String CARS_TOPIC = "cars";
    public static final String JUNCTIONS_TOPIC = "junctions";
    public static final String LANES_TOPIC = "lanes";

    @Bean
    public NewTopic carsTopic() {
        return TopicBuilder.name(CARS_TOPIC).partitions(1).replicas(1).build();
    }

    @Bean
    public NewTopic junctionsTopic() {
        return TopicBuilder.name(JUNCTIONS_TOPIC).partitions(1).replicas(1).build();
    }

    @Bean
    public NewTopic lanesTopic() {
        return TopicBuilder.name(LANES_TOPIC).partitions(1).replicas(1).build();
    }
}

