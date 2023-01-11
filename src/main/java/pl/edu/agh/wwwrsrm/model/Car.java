package pl.edu.agh.wwwrsrm.model;

import javafx.geometry.Point2D;
import javafx.util.Pair;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.edu.agh.wwwrsrm.graph.GraphOSM;
import pl.edu.agh.wwwrsrm.graph.NodeOSM;
import pl.edu.agh.wwwrsrm.utils.coordinates.WindowXYCoordinate;
import pl.edu.agh.wwwrsrm.utils.window.MapWindow;

import static pl.edu.agh.wwwrsrm.utils.Interpolation.lerp;

@Getter
@Setter
@Builder
public class Car {

    private static WindowXYCoordinate getXYCoordinate(String node, GraphOSM osm_graph, MapWindow mapWindow) {
        NodeOSM nodeOSM = osm_graph.getNodes().get(node);
        if (nodeOSM == null)
            return null;
        return nodeOSM.getCoordinate().convertToWindowXY(mapWindow);
    }

    private static Point2D positionOnLaneToPosition(double progress, WindowXYCoordinate start, WindowXYCoordinate end) {
        double x = lerp(start.getX(), end.getX(), progress);
        double y = lerp(start.getY(), end.getY(), progress);
        return new Point2D(x, y);
    }

    private static Point2D progressToPosition(double progress, Point2D start, Point2D end) {
        double x = lerp(start.getX(), end.getX(), progress);
        double y = lerp(start.getY(), end.getY(), progress);
        return new Point2D(x, y);
    }

    private static double getRotation(WindowXYCoordinate start, WindowXYCoordinate end) {
        double x1 = start.getX();
        double y1 = start.getY();
        double x2 = end.getX();
        double y2 = end.getY();
        double angle = Math.toDegrees(Math.atan2(y2 - y1, x2 - x1));
        if (angle < 0)
            angle += 360;
        return angle;
    }

    private static double getAngle(double lastRotation, double targetRotation, double progress) {
        if(targetRotation - lastRotation > 180)
            targetRotation -= 360;
        else if(targetRotation - lastRotation < -180)
            targetRotation += 360;
        return lerp(lastRotation, targetRotation, progress);
    }

    public Pair<Point2D, Double> getPositionRotation(MapWindow mapWindow, GraphOSM osm_graph) {
        WindowXYCoordinate startNodeWindowXY = getXYCoordinate(getNode1Id(), osm_graph, mapWindow);
        WindowXYCoordinate endNodeWindowXY = getXYCoordinate(getNode2Id(), osm_graph, mapWindow);
        if (startNodeWindowXY == null || endNodeWindowXY == null)
            return null;
        Point2D targetPosition = positionOnLaneToPosition(getPositionOnLane(), startNodeWindowXY, endNodeWindowXY);
        double targetRotation = getRotation(startNodeWindowXY, endNodeWindowXY);

        WindowXYCoordinate lastStartNodeWindowXY = getXYCoordinate(getLastNode1Id(), osm_graph, mapWindow);
        WindowXYCoordinate lastEndNodeWindowXY = getXYCoordinate(getLastNode2Id(), osm_graph, mapWindow);
        if (lastStartNodeWindowXY == null || lastEndNodeWindowXY == null)
            return new Pair<Point2D, Double>(targetPosition, targetRotation);
        Point2D lastPosition = positionOnLaneToPosition(getLastPositionOnLane(), lastStartNodeWindowXY, lastEndNodeWindowXY);
        double lastRotation = getRotation(lastStartNodeWindowXY, lastEndNodeWindowXY);

        Point2D currentPosition = progressToPosition(getProgressBounded(), lastPosition, targetPosition);

        return new Pair<Point2D, Double>(currentPosition, getAngle(lastRotation, targetRotation, getProgressBounded()));
    }

    private String carId;
    private double length;
    private double acceleration;
    private double speed;
    private double maxSpeed;
    private String node1Id;
    private String node2Id;
    private double positionOnLane;

    private String lastNode1Id;
    private String lastNode2Id;
    private double lastPositionOnLane;

    private double multiplier = 1.71;
    private double progress = 0;

    public double getProgressBounded() {
        return Math.max(0.0, Math.min(progress, 1.0));
    }

    public void progress(double delta) {
        progress += delta * multiplier;
    }

}
