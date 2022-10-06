package pl.edu.agh.wwwrsrm;

import javafx.concurrent.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.edu.agh.wwwrsrm.consumer.Consumer;
import pl.edu.agh.wwwrsrm.events.ApplicationRunEvent;
import pl.edu.agh.wwwrsrm.model.Car;
import pl.edu.agh.wwwrsrm.visualization.MapPane;

import java.util.LinkedList;

import static java.lang.Thread.sleep;

@Component
public class AppTaskExecutor {

    private MapPane mapPane;

    private final Task<Void> carsConsumingTask;

    @Autowired
    private Consumer consumer;


    public AppTaskExecutor() {
        this.carsConsumingTask = this.consumingCarsTask();
    }

    @EventListener(ApplicationRunEvent.class)
    public void onApplicationEvent(ApplicationRunEvent event) {
        this.mapPane = event.getMapPane();

        new Thread(this.carsConsumingTask).start();
    }

    @EventListener(ContextStoppedEvent.class)
    public void onStopEvent() {
        this.carsConsumingTask.cancel();
    }

    private Task<Void> consumingCarsTask() {
        return new Task<>() {
            @Override
            protected Void call() throws Exception {
                while (true) {
                    System.out.println("Start consuming cars");
                    LinkedList<Car> cars = consumer.getCars();
                    System.out.println("Consumed cars size : " + cars.size());
                    if(!cars.isEmpty()) {
                        mapPane.clearCars();
                    }
                    while (!cars.isEmpty()) {
                        Car car = cars.poll();
//                        System.out.println("Consumed carId : " + car.getCarId());
                        mapPane.updateCar(car);
                    }

                    System.out.println("End consuming cars");
                    sleep(1000);
                }
            }
        };
    }
}
