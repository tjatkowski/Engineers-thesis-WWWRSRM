package pl.edu.agh.wwwrsrm.events;

import org.springframework.context.ApplicationEvent;

public class ApplicationResumedEvent extends ApplicationEvent {

    public ApplicationResumedEvent(Object source) {
        super(source);
    }
}
