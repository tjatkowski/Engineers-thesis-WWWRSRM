package pl.edu.agh.wwwrsrm.render.layers;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import pl.edu.agh.wwwrsrm.graph.EdgeOSM;
import pl.edu.agh.wwwrsrm.graph.GraphOSM;
import pl.edu.agh.wwwrsrm.graph.NodeOSM;
import pl.edu.agh.wwwrsrm.graph.WayOSM;
import pl.edu.agh.wwwrsrm.model.Road;
import pl.edu.agh.wwwrsrm.parser.parameters.WayParameters;
import pl.edu.agh.wwwrsrm.utils.TrafficDensity;
import pl.edu.agh.wwwrsrm.utils.coordinates.WindowXYCoordinate;
import pl.edu.agh.wwwrsrm.utils.window.MapWindow;

import java.util.*;

public class DensityRoadsLayer extends DrawerLayer {

    private final GraphOSM osm_graph;
    private final MapWindow mapWindow;
    private final Map<Integer, Double> resolution = new HashMap<Integer, Double>();
    private final TrafficDensity trafficDensity;

    public DensityRoadsLayer(double width, double height, GraphOSM osm_graph, MapWindow mapWindow, TrafficDensity trafficDensity) {
        super(width, height);
        this.mapWindow = mapWindow;
        this.osm_graph = osm_graph;
        this.trafficDensity = trafficDensity;
        for(int i = MapWindow.MIN_ZOOM_LEVEL; i <= MapWindow.MAX_ZOOM_LEVEL; i++) {
            double r = mapWindow.groundResolution((osm_graph.getBottomBound() + osm_graph.getTopBound())/2.0, i);
            resolution.put(i, r);
        }
    }

    @Override
    public void draw(GraphicsContext gc, double delta) {
        trafficDensity.getRoads().values().stream()
                .sorted(Comparator.comparingInt(Road::getDensity))
                .forEach(road -> {
                    WayOSM wayOSM = osm_graph.getWays().get(road.getWayId());
                    WayParameters wayParameters = wayOSM.getWayParameters();
                    double wayWidth = (double) wayParameters.getWayWidth() / resolution.get(mapWindow.getZoomLevel());
                    if (wayWidth < 1.0 || wayOSM.isClosed() || wayParameters.getType().equals("waterway")) {
                        return;
                    }
                    int density = road.getDensity();
                    drawWay(wayOSM, gc, delta, wayWidth, new Color(1.0, Math.max(0.0, 1.0 - 0.2 * density), Math.max(0.0, 1.0 - 0.2 * density), 1.0));
                });
    }

    void drawWay(WayOSM way, GraphicsContext gc, double delta, double width, Color color) {
        List<Double> xs = new ArrayList<>();
        List<Double> ys = new ArrayList<>();
        int size = 0;
        boolean first = true;

        for(EdgeOSM edge : way.getEdges()) {
            NodeOSM startNode = edge.getStartNode();
            NodeOSM endNode = edge.getEndNode();
            if (mapWindow.isInsideWindow(startNode.getCoordinate()) || mapWindow.isInsideWindow(endNode.getCoordinate())) {
                WindowXYCoordinate startNodeWindowXY = startNode.getCoordinate().convertToWindowXY(mapWindow);
                WindowXYCoordinate endNodeWindowXY = endNode.getCoordinate().convertToWindowXY(mapWindow);
                // drawing in canvas
                if(first) {
                    first = false;
                    xs.add((double)startNodeWindowXY.getX());
                    ys.add((double)startNodeWindowXY.getY());
                    size++;
                }
                xs.add((double)endNodeWindowXY.getX());
                ys.add((double)endNodeWindowXY.getY());
                size++;
            }

        }
        drawPath(gc, xs, ys, size, width, color);

    }
}
