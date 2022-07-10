package window;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.agh.wwwrsrm.utils.constants.Zoom;
import pl.edu.agh.wwwrsrm.utils.coordinates.GlobalXYCoordinate;
import pl.edu.agh.wwwrsrm.utils.window.GlobalMapWindow;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GlobalMapWindowTest {

    private GlobalMapWindow globalMapWindow;

    @BeforeEach
    public void init() {
        GlobalXYCoordinate topLeftPoint = new GlobalXYCoordinate(500, 500);
        GlobalXYCoordinate bottomRightPoint = new GlobalXYCoordinate(1000, 1000);
        globalMapWindow = new GlobalMapWindow(topLeftPoint, bottomRightPoint);
    }

    @Test
    public void test_Drag_Map_By_Vector_Left() {
        globalMapWindow.dragMapWindowByPercentage(-0.1, 0);

        GlobalXYCoordinate expectedTopLeftPoint = new GlobalXYCoordinate(450, 500);
        GlobalXYCoordinate expectedBottomRightPoint = new GlobalXYCoordinate(950, 1000);
        assertEquals(expectedTopLeftPoint, globalMapWindow.getGlobalTopLeftPoint());
        assertEquals(expectedBottomRightPoint, globalMapWindow.getGlobalBottomRightPoint());
    }

    @Test
    public void test_Drag_Map_By_Vector_Right() {
        globalMapWindow.dragMapWindowByPercentage(0.1, 0);

        GlobalXYCoordinate expectedTopLeftPoint = new GlobalXYCoordinate(550, 500);
        GlobalXYCoordinate expectedBottomRightPoint = new GlobalXYCoordinate(1050, 1000);
        assertEquals(expectedTopLeftPoint, globalMapWindow.getGlobalTopLeftPoint());
        assertEquals(expectedBottomRightPoint, globalMapWindow.getGlobalBottomRightPoint());
    }

    @Test
    public void test_Drag_Map_By_Vector_Up() {
        globalMapWindow.dragMapWindowByPercentage(0, -0.1);

        GlobalXYCoordinate expectedTopLeftPoint = new GlobalXYCoordinate(500, 450);
        GlobalXYCoordinate expectedBottomRightPoint = new GlobalXYCoordinate(1000, 950);
        assertEquals(expectedTopLeftPoint, globalMapWindow.getGlobalTopLeftPoint());
        assertEquals(expectedBottomRightPoint, globalMapWindow.getGlobalBottomRightPoint());
    }

    @Test
    public void test_Drag_Map_By_Vector_Down() {
        globalMapWindow.dragMapWindowByPercentage(0, 0.1);

        GlobalXYCoordinate expectedTopLeftPoint = new GlobalXYCoordinate(500, 550);
        GlobalXYCoordinate expectedBottomRightPoint = new GlobalXYCoordinate(1000, 1050);
        assertEquals(expectedTopLeftPoint, globalMapWindow.getGlobalTopLeftPoint());
        assertEquals(expectedBottomRightPoint, globalMapWindow.getGlobalBottomRightPoint());
    }

    @Test
    public void test_Drag_Map_By_Vector_Left_Up() {
        globalMapWindow.dragMapWindowByPercentage(-0.1, -0.1);

        GlobalXYCoordinate expectedTopLeftPoint = new GlobalXYCoordinate(450, 450);
        GlobalXYCoordinate expectedBottomRightPoint = new GlobalXYCoordinate(950, 950);
        assertEquals(expectedTopLeftPoint, globalMapWindow.getGlobalTopLeftPoint());
        assertEquals(expectedBottomRightPoint, globalMapWindow.getGlobalBottomRightPoint());
    }

    @Test
    public void test_Drag_Map_By_Vector_Left_Down() {
        globalMapWindow.dragMapWindowByPercentage(-0.1, 0.1);

        GlobalXYCoordinate expectedTopLeftPoint = new GlobalXYCoordinate(450, 550);
        GlobalXYCoordinate expectedBottomRightPoint = new GlobalXYCoordinate(950, 1050);
        assertEquals(expectedTopLeftPoint, globalMapWindow.getGlobalTopLeftPoint());
        assertEquals(expectedBottomRightPoint, globalMapWindow.getGlobalBottomRightPoint());
    }

    @Test
    public void test_Drag_Map_By_Vector_Right_Up() {
        globalMapWindow.dragMapWindowByPercentage(0.1, -0.1);

        GlobalXYCoordinate expectedTopLeftPoint = new GlobalXYCoordinate(550, 450);
        GlobalXYCoordinate expectedBottomRightPoint = new GlobalXYCoordinate(1050, 950);
        assertEquals(expectedTopLeftPoint, globalMapWindow.getGlobalTopLeftPoint());
        assertEquals(expectedBottomRightPoint, globalMapWindow.getGlobalBottomRightPoint());
    }

    @Test
    public void test_Drag_Map_By_Vector_Right_Down() {
        globalMapWindow.dragMapWindowByPercentage(0.1, 0.1);

        GlobalXYCoordinate expectedTopLeftPoint = new GlobalXYCoordinate(550, 550);
        GlobalXYCoordinate expectedBottomRightPoint = new GlobalXYCoordinate(1050, 1050);
        assertEquals(expectedTopLeftPoint, globalMapWindow.getGlobalTopLeftPoint());
        assertEquals(expectedBottomRightPoint, globalMapWindow.getGlobalBottomRightPoint());
    }

    @Test
    public void test_Zoom_MapWindow_In() {
        globalMapWindow.zoomMapWindow(Zoom.IN);

        GlobalXYCoordinate expectedTopLeftPoint = new GlobalXYCoordinate(625, 625);
        GlobalXYCoordinate expectedBottomRightPoint = new GlobalXYCoordinate(875, 875);
        assertEquals(expectedTopLeftPoint, globalMapWindow.getGlobalTopLeftPoint());
        assertEquals(expectedBottomRightPoint, globalMapWindow.getGlobalBottomRightPoint());
    }

    @Test
    public void test_Zoom_MapWindow_Out() {
        globalMapWindow.zoomMapWindow(Zoom.OUT);

        GlobalXYCoordinate expectedTopLeftPoint = new GlobalXYCoordinate(250, 250);
        GlobalXYCoordinate expectedBottomRightPoint = new GlobalXYCoordinate(1250, 1250);
        assertEquals(expectedTopLeftPoint, globalMapWindow.getGlobalTopLeftPoint());
        assertEquals(expectedBottomRightPoint, globalMapWindow.getGlobalBottomRightPoint());
    }
}
