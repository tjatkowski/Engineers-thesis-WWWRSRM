package pl.edu.agh.wwwrsrm.utils.window;


import lombok.Getter;
import pl.edu.agh.wwwrsrm.graph.GraphOSM;
import pl.edu.agh.wwwrsrm.render.Layer;
import pl.edu.agh.wwwrsrm.render.layers.*;
import pl.edu.agh.wwwrsrm.utils.CarsManager;
import pl.edu.agh.wwwrsrm.utils.TrafficDensity;
import pl.edu.agh.wwwrsrm.utils.coordinates.LonLatCoordinate;
import proto.model.Coordinates;
import proto.model.VisualizationStateChangeMessage;

import java.util.List;
import java.util.Optional;

public class MapView {

    @Getter
    private final MapWindow mapWindow;
    private final List<Layer> closeZoomLayers;
    private final List<Layer> farZoomLayers;

    public MapView(CarsManager carsManager, TrafficDensity trafficDensity, GraphOSM graphOSM, int width, int height, MapView currentMapView) {
        this.mapWindow = createMapWindow(graphOSM, width, height, currentMapView);

        DecorationsLayer decorationsLayer = new DecorationsLayer(width, height, graphOSM, mapWindow);
        RoadsLayer roadsLayer = new RoadsLayer(width, height, graphOSM, mapWindow);
        DensityRoadsLayer densityRoadsLayer = new DensityRoadsLayer(width, height, graphOSM, mapWindow, trafficDensity);
        CarsLayer carsLayer = new CarsLayer(width, height, graphOSM, mapWindow, carsManager);
        DebugLayer debugLayer = new DebugLayer(width, height);

        this.closeZoomLayers = List.of(decorationsLayer, roadsLayer, carsLayer, debugLayer);
        this.farZoomLayers = List.of(decorationsLayer, densityRoadsLayer, debugLayer);
    }

    private MapWindow createMapWindow(GraphOSM graphOSM, int width, int height, MapView currentMapView) {
        Integer zoomLevel = getZoomLevel(currentMapView);
        LonLatCoordinate topLeftPoint = getTopLeftPoint(graphOSM, currentMapView);
        LonLatCoordinate bottomRightPoint = getBottomRightPoint(graphOSM, currentMapView);
        return new MapWindow(topLeftPoint, bottomRightPoint, width, height, zoomLevel);
    }

    public List<Layer> getLayers() {
        if (mapWindow.getZoomLevel() > 15)
            return closeZoomLayers;
        return farZoomLayers;
    }

    private Integer getZoomLevel(MapView currentMapView) {
        return Optional.ofNullable(currentMapView)
                .map(MapView::getMapWindow)
                .map(MapWindow::getZoomLevel)
                .orElse(null);
    }

    private LonLatCoordinate getTopLeftPoint(GraphOSM graphOSM, MapView currentMapView) {
        return Optional.ofNullable(currentMapView)
                .map(MapView::getMapWindow)
                .map(MapWindow::getTopLeftPoint)
                .orElseGet(graphOSM::getTopLeftBound);
    }

    private LonLatCoordinate getBottomRightPoint(GraphOSM graphOSM, MapView currentMapView) {
        return Optional.ofNullable(currentMapView)
                .map(MapView::getMapWindow)
                .map(MapWindow::getBottomRightPoint)
                .orElseGet(graphOSM::getBottomRightBound);
    }

    public VisualizationStateChangeMessage.ROIRegion getRoiRegion() {
        return VisualizationStateChangeMessage.ROIRegion.newBuilder()
                .setTopLeftCoordinates(Coordinates.newBuilder()
                        .setLatitude(mapWindow.getTopLeftPoint().getLatitude())
                        .setLongitude(mapWindow.getTopLeftPoint().getLongitude())
                        .build())
                .setBottomRightCoordinates(Coordinates.newBuilder()
                        .setLatitude(mapWindow.getBottomRightPoint().getLatitude())
                        .setLongitude(mapWindow.getBottomRightPoint().getLongitude())
                        .build())
                .build();
    }

}
