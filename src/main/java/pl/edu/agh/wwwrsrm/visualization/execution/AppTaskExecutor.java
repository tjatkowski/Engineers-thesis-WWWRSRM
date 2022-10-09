package pl.edu.agh.wwwrsrm.visualization.execution;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;
import pl.edu.agh.wwwrsrm.events.ApplicationClosedEvent;
import pl.edu.agh.wwwrsrm.events.ApplicationResumedEvent;
import pl.edu.agh.wwwrsrm.events.ApplicationStartedEvent;
import pl.edu.agh.wwwrsrm.events.ApplicationStoppedEvent;

import java.util.concurrent.ScheduledFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class AppTaskExecutor {

    @Value("${visualization_delay}")
    private Integer delay;

    private ScheduledFuture<?> scheduledFuture;
    private final TaskScheduler taskScheduler;
    private final VisualizationTask visualizationTask;

    @EventListener(ApplicationStartedEvent.class)
    public void onApplicationStartedEvent(ApplicationStartedEvent event) {
        log.info("Application started");
        scheduledFuture = taskScheduler.scheduleWithFixedDelay(visualizationTask, delay);
    }

    @SneakyThrows
    @EventListener(ApplicationStoppedEvent.class)
    public synchronized void onApplicationStoppedEvent(ApplicationStoppedEvent event) {
        log.info("Application stopped");
        scheduledFuture.cancel(false);
    }

    @EventListener(ApplicationResumedEvent.class)
    public synchronized void onApplicationResumedEvent(ApplicationResumedEvent event) {
        log.info("Application resumed");
        if (scheduledFuture.isDone()) {
            scheduledFuture = taskScheduler.scheduleWithFixedDelay(visualizationTask, delay);
        }
    }

    @EventListener(ApplicationClosedEvent.class)
    public void onApplicationClosedEvent(ApplicationClosedEvent event) {
        log.info("Application closed");
        scheduledFuture.cancel(true);
    }
}