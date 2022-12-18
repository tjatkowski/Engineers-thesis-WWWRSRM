package pl.edu.agh.wwwrsrm.window.menu;

import org.springframework.stereotype.Component;
import pl.edu.agh.wwwrsrm.connection.producer.VisualizationStateChangeProducer;
import pl.edu.agh.wwwrsrm.window.map.Map;
import proto.model.RUNNING_STATE;

import static proto.model.RUNNING_STATE.RESUMED;

@Component
public class ResumeButton extends MenuButton {

    public ResumeButton(VisualizationStateChangeProducer visualizationStateChangeProducer, Map visualizationMap) {
        super(visualizationStateChangeProducer, visualizationMap);
    }

    @Override
    protected String getButtonName() {
        return "Resume";
    }

    @Override
    protected RUNNING_STATE getRunningState() {
        return RESUMED;
    }
}
