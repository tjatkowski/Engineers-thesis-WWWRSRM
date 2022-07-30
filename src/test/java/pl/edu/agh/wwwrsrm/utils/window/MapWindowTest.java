package pl.edu.agh.wwwrsrm.utils.window;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pl.edu.agh.wwwrsrm.utils.coordinates.LonLatCoordinate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;

public class MapWindowTest {

    private static MapWindow mapWindow;

    @BeforeAll
    static void init() {
        LonLatCoordinate topLeftPoint = new LonLatCoordinate(-30.0, 30.0);
        LonLatCoordinate bottomRightPoint = new LonLatCoordinate(30.0, -30.0);
        mapWindow = new MapWindow(topLeftPoint, bottomRightPoint, anyInt(), anyInt());
    }

    @ParameterizedTest
    @CsvSource({"0.0, 0.0", "-15.0, 15.0", "15.0, 15.0", "-15.0, -15.0", "15.0, -15.0"})
    public void is_Inside_Window_True(double longitude, double latitude) {
        assertTrue(mapWindow.isInsideWindow(new LonLatCoordinate(longitude, latitude)));
    }

    @ParameterizedTest
    @CsvSource({"-45.0, 45.0", "-45.0, 15.0", "-45.0, -15.0", "-45.0, -45.0", "-15.0, -45.0",
            "15.0, -45.0", "45.0, -45.0", "45.0, -15.0", "45.0, 15.0", "45.0, 45.0", "15.0, 45.0", "-15.0, 45.0"})
    public void is_Inside_Window_False(double longitude, double latitude) {
        assertFalse(mapWindow.isInsideWindow(new LonLatCoordinate(longitude, latitude)));
    }
}
