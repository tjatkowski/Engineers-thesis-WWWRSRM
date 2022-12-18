package pl.edu.agh.wwwrsrm.utils.window;


import lombok.Getter;
import pl.edu.agh.wwwrsrm.graph.GraphOSM;
import pl.edu.agh.wwwrsrm.render.Layer;
import pl.edu.agh.wwwrsrm.render.layers.*;
import pl.edu.agh.wwwrsrm.utils.CarsManager;
import pl.edu.agh.wwwrsrm.utils.TrafficDensity;

import java.util.List;

@Getter
public class MapView {

    private final MapWindow mapWindow;
    private final List<Layer> closeZoomLayers;
    private final List<Layer> farZoomLayers;

    public MapView(CarsManager carsManager, TrafficDensity trafficDensity, GraphOSM graphOSM, int width, int height) {
        this.mapWindow = new MapWindow(graphOSM.getTopLeftBound(), graphOSM.getBottomRightBound(), width, height);

        DecorationsLayer decorationsLayer = new DecorationsLayer(width, height, graphOSM, mapWindow);
        RoadsLayer roadsLayer = new RoadsLayer(width, height, graphOSM, mapWindow);
        DensityRoadsLayer densityRoadsLayer = new DensityRoadsLayer(width, height, graphOSM, mapWindow, trafficDensity);
        CarsLayer carsLayer = new CarsLayer(width, height, graphOSM, mapWindow, carsManager);
        DebugLayer debugLayer = new DebugLayer(width, height);

        this.closeZoomLayers = List.of(decorationsLayer, roadsLayer, carsLayer, debugLayer);
        this.farZoomLayers = List.of(decorationsLayer, densityRoadsLayer, debugLayer);
    }

    public List<Layer> getLayers() {
        if(mapWindow.getZoomLevel() > 15)
            return closeZoomLayers;
        return farZoomLayers;
    }

}
