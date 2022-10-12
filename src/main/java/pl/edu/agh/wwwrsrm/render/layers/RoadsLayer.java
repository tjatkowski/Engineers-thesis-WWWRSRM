package pl.edu.agh.wwwrsrm.render.layers;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import pl.edu.agh.wwwrsrm.graph.EdgeOSM;
import pl.edu.agh.wwwrsrm.graph.GraphOSM;
import pl.edu.agh.wwwrsrm.graph.NodeOSM;
import pl.edu.agh.wwwrsrm.graph.WayOSM;
import pl.edu.agh.wwwrsrm.osm.WayParameters;
import pl.edu.agh.wwwrsrm.osm.osmParser;
import pl.edu.agh.wwwrsrm.render.Layer;
import pl.edu.agh.wwwrsrm.utils.coordinates.WindowXYCoordinate;
import pl.edu.agh.wwwrsrm.utils.window.MapWindow;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class RoadsLayer extends Layer {

    private final GraphOSM osm_graph;
    private final MapWindow mapWindow;
    public RoadsLayer(double width, double height, GraphOSM osm_graph, MapWindow mapWindow) {
        super(width, height);
        this.mapWindow = mapWindow;
        this.osm_graph = osm_graph;
    }

    @Override
    public void draw(GraphicsContext gc, double delta) {
        for (WayOSM way : osm_graph.getWays()) {
            WayParameters wayParameters = way.getEdgeParameter();
            int wayMinZoomLevel = wayParameters.getZoomLevel();
            int wayWidth = wayParameters.getWayWidth();
            Color wayColor = wayParameters.getColor();

            if (wayMinZoomLevel > mapWindow.getZoomLevel()) {
                continue;
            }

            if (!way.isClosed()) {
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
            else {
                NodeOSM firstNode = way.getEdges().get(0).getStartNode();
                if (!mapWindow.isInsideWindow(firstNode.getCoordinate())) {
                    continue;
                }

                List<WindowXYCoordinate> points = new ArrayList<>();
                points.add(firstNode.getCoordinate().convertToWindowXY(mapWindow));

                Stream<NodeOSM> nodes = way.getEdges().stream().map(EdgeOSM::getEndNode);
                if (!nodes.map(NodeOSM::getCoordinate).allMatch(mapWindow::isInsideWindow)) {
                    continue;
                }

                points.addAll(way.getEdges().stream()
                        .map(EdgeOSM::getEndNode)
                        .map(NodeOSM::getCoordinate)
                        .map(coordinate -> coordinate.convertToWindowXY(mapWindow)).toList());

                this.drawPolygonInCanvas(gc, points.stream().map(WindowXYCoordinate::getX).mapToDouble(Integer::doubleValue).toArray(),
                        points.stream().map(WindowXYCoordinate::getY).mapToDouble(Integer::doubleValue).toArray(), wayColor);
            }
        }
    }


    public void drawLineInCanvas(GraphicsContext gc, WindowXYCoordinate startPoint, WindowXYCoordinate endPoint, int width, Color color) {
        gc.setStroke(color);
        gc.setLineWidth(width);
        gc.strokeLine(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());
    }

    public void drawPolygonInCanvas(GraphicsContext gc, double[] xPoints, double[] yPoints, Color color) {
        int n = xPoints.length;
        gc.setFill(color);
        gc.fillPolygon(xPoints, yPoints, n);
    }
}
