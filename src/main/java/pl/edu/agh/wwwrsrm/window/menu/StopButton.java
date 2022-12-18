package pl.edu.agh.wwwrsrm.window.menu;

import org.springframework.stereotype.Component;
import pl.edu.agh.wwwrsrm.connection.producer.VisualizationStateChangeProducer;
import pl.edu.agh.wwwrsrm.window.map.Map;
import proto.model.RUNNING_STATE;

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
    protected RUNNING_STATE getRunningState() {
        return STOPPED;
    }
}
