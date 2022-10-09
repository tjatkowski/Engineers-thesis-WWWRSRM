package pl.edu.agh.wwwrsrm.visualization.drawing;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edu.agh.wwwrsrm.model.Car;
import pl.edu.agh.wwwrsrm.visualization.MapPane;

@Component
@RequiredArgsConstructor
public class MapDrawer {

    @Getter
    private final MapPane mapPane;

    public void drawLines() {
        mapPane.drawLines();
    }

    public void drawNodes() {
        mapPane.drawNodes();
    }

    public void updateCar(Car car) {
        mapPane.updateCar(car);
    }

    public void clearCars() {
        mapPane.clearCars();
    }
}
