package pl.edu.agh.wwwrsrm.window.menu;

import org.springframework.stereotype.Component;
import pl.edu.agh.wwwrsrm.connection.producer.VisualizationStateChangeProducer;
import pl.edu.agh.wwwrsrm.window.map.Map;
import proto.model.RUNNING_STATE;

import java.util.Optional;

import static proto.model.RUNNING_STATE.CLOSED;

@Component
public class EndButton extends MenuButton {

    public EndButton(VisualizationStateChangeProducer visualizationStateChangeProducer, Map visualizationMap) {
        super(visualizationStateChangeProducer, visualizationMap);
    }

    @Override
    protected String getButtonName() {
        return "End";
    }

    @Override
    protected Optional<RUNNING_STATE> getVisualizationStateToSend() {
        if (CLOSED.equals(visualizationMap.getVisualizationRunningState())) {
            return Optional.empty();
        }
        return Optional.of(CLOSED);
    }
}
