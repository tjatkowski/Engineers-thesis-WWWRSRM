package pl.edu.agh.wwwrsrm.render.layers;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import pl.edu.agh.wwwrsrm.graph.EdgeOSM;
import pl.edu.agh.wwwrsrm.graph.GraphOSM;
import pl.edu.agh.wwwrsrm.graph.NodeOSM;
import pl.edu.agh.wwwrsrm.graph.WayOSM;
import pl.edu.agh.wwwrsrm.osm.WayParameters;
import pl.edu.agh.wwwrsrm.utils.coordinates.WindowXYCoordinate;
import pl.edu.agh.wwwrsrm.utils.window.MapWindow;

import java.util.HashMap;
import java.util.Map;

public class RoadsLayer extends DrawerLayer {

    private final GraphOSM osm_graph;
    private final MapWindow mapWindow;

    private final Map<Integer, Double> resolution = new HashMap<Integer, Double>();
    public RoadsLayer(double width, double height, GraphOSM osm_graph, MapWindow mapWindow) {
        super(width, height);
        this.mapWindow = mapWindow;
        this.osm_graph = osm_graph;
        for(int i = MapWindow.MIN_ZOOM_LEVEL; i <= MapWindow.MAX_ZOOM_LEVEL; i++) {
            double r = mapWindow.groundResolution((osm_graph.getBottomBound() + osm_graph.getTopBound())/2.0, i);
            resolution.put(i, r);
        }
    }

    @Override
    public void draw(GraphicsContext gc, double delta) {
        for (WayOSM way : osm_graph.getWays().values()) {
            WayParameters wayParameters = way.getEdgeParameter();
            int wayMinZoomLevel = wayParameters.getZoomLevel();

            double wayWidth = (double)wayParameters.getWayWidth() / resolution.get(mapWindow.getZoomLevel());
            Color wayColor = wayParameters.getColor();

            if (wayWidth < 1.0 ) {
                continue;
            }

            if (!way.isClosed() && !wayParameters.getType().equals("waterway")) {
                for (EdgeOSM edge : way.getEdges()) {
                    NodeOSM startNode = edge.getStartNode();
                    NodeOSM endNode = edge.getEndNode();
                    if (mapWindow.isInsideWindow(startNode.getCoordinate()) || mapWindow.isInsideWindow(endNode.getCoordinate())) {
                        WindowXYCoordinate startNodeWindowXY = startNode.getCoordinate().convertToWindowXY(mapWindow);
                        WindowXYCoordinate endNodeWindowXY = endNode.getCoordinate().convertToWindowXY(mapWindow);
                        // drawing in canvas
                        this.drawLineInCanvas(gc, startNodeWindowXY, endNodeWindowXY, wayWidth, wayColor);
                    }
                }
            }
        }
    }
}
