package pl.edu.agh.wwwrsrm.render;

import javafx.scene.canvas.GraphicsContext;

public class Layer {
    private double width, height;

    public Layer(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public void draw(GraphicsContext gc, double delta) {
        
    }
}
