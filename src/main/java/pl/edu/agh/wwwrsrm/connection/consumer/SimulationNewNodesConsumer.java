package pl.edu.agh.wwwrsrm.connection.consumer;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import pl.edu.agh.wwwrsrm.graph.NodeOSM;
import proto.model.SimulationNewNodesTransferMessage;

import java.util.LinkedList;
import java.util.List;

import static pl.edu.agh.wwwrsrm.connection.config.TopicConfiguration.SIMULATION_NEW_NODES_TOPIC;

@Slf4j
@Service
public class SimulationNewNodesConsumer {

    @Getter
    private final List<NodeOSM> newNodesList = new LinkedList<>();

    private

    @KafkaListener(topics = SIMULATION_NEW_NODES_TOPIC, groupId = SIMULATION_NEW_NODES_TOPIC, properties = {
            "specific.protobuf.value.type: proto.model.SimulationNewNodesTransferMessage"
    })
    void simulationNewNodesListener(ConsumerRecord<String, SimulationNewNodesTransferMessage> record) {
        SimulationNewNodesTransferMessage simulationNewNodesTransferMessage = record.value();
        log.info("Consumed SimulationNewNodesTransferMessage:{} new nodes, partition:{}, offset:{}", simulationNewNodesTransferMessage.getNodesCount(),
                record.partition(), record.offset());

        List<NodeOSM> newNodeOSMList = simulationNewNodesTransferMessage.getNodesList().stream().map(NodeOSM::new).toList();
        newNodesList.addAll(newNodeOSMList);
    }
}
