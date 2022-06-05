package pl.edu.agh.wwwrsrm.consumer.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicConfiguration {

    @Bean
    public NewTopic carsTopic() {
        return TopicBuilder.name("${topic.cars.name}").partitions(1).replicas(1).build();
    }

    @Bean
    public NewTopic junctionsTopic() {
        return TopicBuilder.name("${topic.junctions.name}").partitions(1).replicas(1).build();
    }

    @Bean
    public NewTopic lanesTopic() {
        return TopicBuilder.name("${topic.lanes.name}").partitions(1).replicas(1).build();
    }
}

