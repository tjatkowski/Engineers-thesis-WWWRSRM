package pl.edu.agh.wwwrsrm.render.layers;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import pl.edu.agh.wwwrsrm.render.Layer;
import pl.edu.agh.wwwrsrm.utils.coordinates.WindowXYCoordinate;

import java.util.List;

public class DrawerLayer extends Layer {

    public DrawerLayer(double width, double height) {
        super(width, height);
    }

    protected void drawPath(GraphicsContext gc, List<Double> xs, List<Double> ys, int size, double width, Color color) {
        gc.setLineCap(javafx.scene.shape.StrokeLineCap.ROUND);
        gc.setStroke(color);
        gc.setLineWidth(width);
        gc.strokePolyline(xs.stream().mapToDouble(Double::doubleValue).toArray(), ys.stream().mapToDouble(Double::doubleValue).toArray(), size);

    }

    protected void drawLineInCanvas(GraphicsContext gc, WindowXYCoordinate startPoint, WindowXYCoordinate endPoint, double width, Color color) {
        gc.setStroke(color);
        gc.setLineWidth(width);
        gc.setLineCap(javafx.scene.shape.StrokeLineCap.ROUND);
//        gc.setEffect(this.borderEffect);
        gc.strokeLine(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());
    }

    protected void drawPolygonInCanvas(GraphicsContext gc, double[] xPoints, double[] yPoints, Color color) {
        int n = xPoints.length;
        gc.setStroke(color);
        gc.setLineWidth(0);
        gc.setFill(color);
        gc.fillPolygon(xPoints, yPoints, n);
    }
}
