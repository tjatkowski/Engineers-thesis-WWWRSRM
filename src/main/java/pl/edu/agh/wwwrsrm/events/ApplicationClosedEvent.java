package pl.edu.agh.wwwrsrm.events;

import org.springframework.context.ApplicationEvent;

public class ApplicationClosedEvent extends ApplicationEvent {

    public ApplicationClosedEvent(Object source) {
        super(source);
    }
}
