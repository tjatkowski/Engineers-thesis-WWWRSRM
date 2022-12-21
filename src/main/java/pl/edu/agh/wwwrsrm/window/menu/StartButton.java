package pl.edu.agh.wwwrsrm.window.menu;

import org.springframework.stereotype.Component;
import pl.edu.agh.wwwrsrm.connection.producer.VisualizationStateChangeProducer;
import pl.edu.agh.wwwrsrm.window.map.Map;
import proto.model.RUNNING_STATE;

import java.util.Objects;
import java.util.Optional;

import static proto.model.RUNNING_STATE.STARTED;

@Component
public class StartButton extends MenuButton {

    public StartButton(VisualizationStateChangeProducer visualizationStateChangeProducer, Map visualizationMap) {
        super(visualizationStateChangeProducer, visualizationMap);
    }

    @Override
    protected String getButtonName() {
        return "Start";
    }

    @Override
    protected Optional<RUNNING_STATE> getVisualizationStateToSend() {
        if (Objects.nonNull(visualizationMap.getVisualizationRunningState())) {
            return Optional.empty();
        }
        return Optional.of(STARTED);
    }
}
