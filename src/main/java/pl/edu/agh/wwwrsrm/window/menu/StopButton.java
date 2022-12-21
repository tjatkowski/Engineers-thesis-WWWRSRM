package pl.edu.agh.wwwrsrm.window.menu;

import org.springframework.stereotype.Component;
import pl.edu.agh.wwwrsrm.connection.producer.VisualizationStateChangeProducer;
import pl.edu.agh.wwwrsrm.window.map.Map;
import proto.model.RUNNING_STATE;

import java.util.Optional;

import static proto.model.RUNNING_STATE.STOPPED;

@Component
public class StopButton extends MenuButton {

    public StopButton(VisualizationStateChangeProducer visualizationStateChangeProducer, Map visualizationMap) {
        super(visualizationStateChangeProducer, visualizationMap);
    }

    @Override
    protected String getButtonName() {
        return "Stop";
    }

    @Override
    protected Optional<RUNNING_STATE> getVisualizationStateToSend() {
        if (STOPPED.equals(visualizationMap.getVisualizationRunningState())) {
            return Optional.empty();
        }
        return Optional.of(STOPPED);
    }
}
