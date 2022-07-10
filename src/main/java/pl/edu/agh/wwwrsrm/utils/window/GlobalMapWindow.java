package pl.edu.agh.wwwrsrm.utils.window;

import lombok.Getter;
import lombok.Setter;
import pl.edu.agh.wwwrsrm.utils.Vec2D;
import pl.edu.agh.wwwrsrm.utils.constants.Zoom;
import pl.edu.agh.wwwrsrm.utils.coordinates.GlobalXYCoordinate;

/**
 * GlobalMapWindow is class used by MapWindow class which delegates operations
 * calculated on global XY coordinates system
 */
@Getter
public class GlobalMapWindow {
    @Setter
    private GlobalXYCoordinate globalTopLeftPoint;
    @Setter
    private GlobalXYCoordinate globalBottomRightPoint;

    private int globalWindowWidth;
    private int globalWindowHeight;

    public GlobalMapWindow(GlobalXYCoordinate globalTopLeftPoint, GlobalXYCoordinate globalBottomRightPoint) {
        this.globalTopLeftPoint = globalTopLeftPoint;
        this.globalBottomRightPoint = globalBottomRightPoint;
        updateGlobalWindowSize();
    }

    /**
     * Method which moves map boundaries.
     */
    public void dragMapWindowByPercentage(double xPercentageShift, double yPercentageShift) {
        int xShift = (int) (xPercentageShift * globalWindowWidth);
        int yShift = (int) (yPercentageShift * globalWindowHeight);

        this.globalTopLeftPoint = globalTopLeftPoint.add(new Vec2D(xShift, yShift));
        this.globalBottomRightPoint = globalBottomRightPoint.add(new Vec2D(xShift, yShift));
        updateGlobalWindowSize();
    }

    /**
     * Method which zoom in and zoom out map boundaries.
     */
    public void zoomMapWindow(Zoom zoom) {
        int widthZoomFactor;
        int heightZoomFactor;
        switch (zoom) {
            case IN -> {
                widthZoomFactor = globalWindowWidth / 4;
                heightZoomFactor = globalWindowHeight / 4;
                this.globalTopLeftPoint = globalTopLeftPoint.add(new Vec2D(widthZoomFactor, heightZoomFactor));
                this.globalBottomRightPoint = globalBottomRightPoint.subtract(new Vec2D(widthZoomFactor, heightZoomFactor));
            }
            case OUT -> {
                widthZoomFactor = globalWindowWidth / 2;
                heightZoomFactor = globalWindowHeight / 2;
                this.globalTopLeftPoint = globalTopLeftPoint.subtract(new Vec2D(widthZoomFactor, heightZoomFactor));
                this.globalBottomRightPoint = globalBottomRightPoint.add(new Vec2D(widthZoomFactor, heightZoomFactor));
            }
        }
        updateGlobalWindowSize();
    }

    public void updateGlobalWindowSize() {
        this.globalWindowWidth = globalBottomRightPoint.getX() - globalTopLeftPoint.getX();
        this.globalWindowHeight = globalBottomRightPoint.getY() - globalTopLeftPoint.getY();
    }
}
