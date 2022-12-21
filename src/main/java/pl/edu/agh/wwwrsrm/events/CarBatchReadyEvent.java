package pl.edu.agh.wwwrsrm.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import proto.model.CarMessage;

import java.util.List;

@Getter
public class CarBatchReadyEvent extends ApplicationEvent {

    private final List<CarMessage> carMessageList;
    public CarBatchReadyEvent(Object source, List<CarMessage> carMessageList) {
        super(source);
        this.carMessageList = carMessageList;
    }
}
