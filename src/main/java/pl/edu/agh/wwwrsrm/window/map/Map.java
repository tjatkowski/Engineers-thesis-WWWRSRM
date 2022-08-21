package pl.edu.agh.wwwrsrm.window.map;

import java.util.Vector;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.animation.AnimationTimer;

import pl.edu.agh.wwwrsrm.render.Layer;
import pl.edu.agh.wwwrsrm.render.layers.*;

public class Map extends Canvas {
    private Vector<Layer> layers = new Vector<Layer>();

    private AnimationTimer timer;

    public Map(double width, double height) {
        super(width, height);
        setTimer();
        addLayers();
    
    }
    
    private void addLayers() {
        layers.add(new DebugLayer(getWidth(), getHeight()));
        
    }

    private void setTimer() {
        timer = new AnimationTimer() {
            private long previous = 0;
            @Override
            public void handle(long now) {
                double delta = ((double)(now - previous))/1000000000.0;
                previous = now;
                draw(delta);
            }
        };
        timer.start();
    }

    public void draw(double delta) {
        GraphicsContext gc = this.getGraphicsContext2D();
        gc.setFill(Color.PINK);
        gc.fillRect(0, 0, getWidth(), getHeight());
        for (Integer i = 0; i < this.layers.size(); i++) {
            this.layers.get(i).draw(gc, delta);
        }
    }
}
