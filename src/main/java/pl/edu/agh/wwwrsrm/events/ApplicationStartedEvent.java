package pl.edu.agh.wwwrsrm.events;

import org.springframework.context.ApplicationEvent;

public class ApplicationStartedEvent extends ApplicationEvent {

    public ApplicationStartedEvent(Object source) {
        super(source);
    }
}
