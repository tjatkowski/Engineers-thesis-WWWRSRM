package pl.edu.agh.wwwrsrm.utils.window;


import lombok.Getter;
import pl.edu.agh.wwwrsrm.graph.GraphOSM;
import pl.edu.agh.wwwrsrm.model.Car;
import pl.edu.agh.wwwrsrm.render.Layer;
import pl.edu.agh.wwwrsrm.render.layers.CarsLayer;
import pl.edu.agh.wwwrsrm.render.layers.DebugLayer;
import pl.edu.agh.wwwrsrm.render.layers.DecorationsLayer;
import pl.edu.agh.wwwrsrm.render.layers.RoadsLayer;
import pl.edu.agh.wwwrsrm.utils.coordinates.LonLatCoordinate;
import proto.model.Coordinates;
import proto.model.VisualizationStateChangeMessage;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Getter
public class MapView {

    private final MapWindow mapWindow;
    private final List<Layer> layers;

    public MapView(GraphOSM graphOSM, Map<String, Car> cars, int width, int height, MapView currentMapView) {
        Integer zoomLevel = getZoomLevel(currentMapView);
        LonLatCoordinate topLeftPoint = getTopLeftPoint(graphOSM, currentMapView);
        LonLatCoordinate bottomRightPoint = getBottomRightPoint(graphOSM, currentMapView);
        this.mapWindow = new MapWindow(topLeftPoint, bottomRightPoint, width, height, zoomLevel);
        this.layers = List.of(
                new DecorationsLayer(width, height, graphOSM, mapWindow),
                new RoadsLayer(width, height, graphOSM, mapWindow),
                new CarsLayer(width, height, graphOSM, mapWindow, cars),
                new DebugLayer(width, height)
        );
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
