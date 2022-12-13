package pl.edu.agh.wwwrsrm.utils.window;


import lombok.Getter;
import pl.edu.agh.wwwrsrm.graph.GraphOSM;
import pl.edu.agh.wwwrsrm.model.Car;
import pl.edu.agh.wwwrsrm.render.Layer;
import pl.edu.agh.wwwrsrm.render.layers.CarsLayer;
import pl.edu.agh.wwwrsrm.render.layers.DebugLayer;
import pl.edu.agh.wwwrsrm.render.layers.DecorationsLayer;
import pl.edu.agh.wwwrsrm.render.layers.RoadsLayer;

import java.util.List;
import java.util.Map;

@Getter
public class MapView {

    private final MapWindow mapWindow;
    private final List<Layer> layers;

    public MapView(GraphOSM graphOSM, Map<String, Car> cars, int width, int height) {
        this.mapWindow = new MapWindow(graphOSM.getTopLeftBound(), graphOSM.getBottomRightBound(), width, height);
        this.layers = List.of(
                new DecorationsLayer(width, height, graphOSM, mapWindow),
                new RoadsLayer(width, height, graphOSM, mapWindow),
                new CarsLayer(width, height, graphOSM, mapWindow, cars),
                new DebugLayer(width, height)
        );
    }

}
