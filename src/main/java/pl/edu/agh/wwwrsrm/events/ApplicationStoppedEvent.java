package pl.edu.agh.wwwrsrm.events;

import org.springframework.context.ApplicationEvent;

public class ApplicationStoppedEvent extends ApplicationEvent {

    public ApplicationStoppedEvent(Object source) {
        super(source);
    }
}
