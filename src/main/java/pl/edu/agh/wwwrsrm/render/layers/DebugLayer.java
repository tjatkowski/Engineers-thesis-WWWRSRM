package pl.edu.agh.wwwrsrm.render.layers;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import pl.edu.agh.wwwrsrm.render.Layer;

public class DebugLayer extends Layer {
    public DebugLayer(double width, double height) {
        super(width, height);
    }

    @Override
    public void draw(GraphicsContext gc, double delta) {
        gc.setFill(Color.RED);
        gc.fillText(String.valueOf((int)(1.0/delta)), 5, 15);
    }
    
}
