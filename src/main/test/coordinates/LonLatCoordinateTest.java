package coordinates;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pl.edu.agh.wwwrsrm.utils.coordinates.GlobalXYCoordinate;
import pl.edu.agh.wwwrsrm.utils.coordinates.LonLatCoordinate;
import pl.edu.agh.wwwrsrm.utils.coordinates.WindowXYCoordinate;
import pl.edu.agh.wwwrsrm.utils.window.MapWindow;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LonLatCoordinateTest {

    @ParameterizedTest
    @CsvSource({"19.9027296, 50.003417600000006, 4548, 2778, 5",
            "20.056621800000002, 50.0054351, 9104, 5556, 6",
            "19.9234833, 50.078865900000004, 145579, 88815, 10",
            "19.913504, 50.0882997, 4658322, 2841748, 15",
            "19.928648300000003, 50.021321, 149077605, 91013743, 20"})
    public void test_Convert_To_Global_XY(double longitude, double latitude, int x, int y, int zoomLevel) {
        LonLatCoordinate lonLatCoordinate = new LonLatCoordinate(longitude, latitude);
        GlobalXYCoordinate globalXYCoordinate = lonLatCoordinate.convertToGlobalXY(zoomLevel);
        assertEquals(x, globalXYCoordinate.getX());
        assertEquals(y, globalXYCoordinate.getY());
    }

    @ParameterizedTest
    @CsvSource({
            "19.839228300000002, 50.107182900000005, 20.084703100000002, 49.9862812, 19.9027296, 50.003417600000006, 284, 686, 1100, 800",
            "19.839228300000002, 50.107182900000005, 20.084703100000002, 49.9862812, 19.9223812, 50.0936851, 373, 89, 1100, 800",
            "19.839228300000002, 50.107182900000005, 20.084703100000002, 49.9862812, 20.0063731, 50.0214845, 749, 567, 1100, 800",
            "19.839228300000002, 50.107182900000005, 20.084703100000002, 49.9862812, 19.9027296, 50.003417600000006, 77, 85, 300, 100",
            "19.839228300000002, 50.107182900000005, 20.084703100000002, 49.9862812, 20.0566998, 50.005407500000004, 265, 84, 300, 100",
            "19.839228300000002, 50.107182900000005, 20.084703100000002, 49.9862812, 19.9027296, 50.003417600000006, 517, 858, 2000, 1000",
            "19.839228300000002, 50.107182900000005, 20.084703100000002, 49.9862812, 20.039580100000002, 50.053748600000006, 1633, 442, 2000, 1000"})
    public void test_Convert_To_Window_XY(double topLeftLongitude, double topLeftLatitude, double bottomRightLongitude,
                                          double bottomRightLatitude, double longitude, double latitude, int x, int y,
                                          int mapWidth, int mapHeight) {
        LonLatCoordinate lonLatCoordinate = new LonLatCoordinate(longitude, latitude);
        MapWindow mapWindow = new MapWindow(new LonLatCoordinate(topLeftLongitude, topLeftLatitude),
                new LonLatCoordinate(bottomRightLongitude, bottomRightLatitude),
                mapWidth, mapHeight);
        WindowXYCoordinate windowXYCoordinate = lonLatCoordinate.convertToWindowXY(mapWindow);
        assertEquals(x, windowXYCoordinate.getX());
        assertEquals(y, windowXYCoordinate.getY());
    }
}
