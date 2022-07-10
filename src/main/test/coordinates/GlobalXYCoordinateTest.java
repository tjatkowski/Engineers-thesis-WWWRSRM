package coordinates;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pl.edu.agh.wwwrsrm.utils.coordinates.GlobalXYCoordinate;
import pl.edu.agh.wwwrsrm.utils.coordinates.LonLatCoordinate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GlobalXYCoordinateTest {

    @ParameterizedTest
    @CsvSource({"19.9027296, 50.003417600000006, 4548, 2778, 5",
            "20.056621800000002, 50.0054351, 9104, 5556, 6",
            "19.9234833, 50.078865900000004, 145579, 88815, 10",
            "19.913504, 50.0882997, 4658322, 2841748, 15",
            "19.928648300000003, 50.021321, 149077605, 91013743, 20"})
    public void test_Convert_To_LonLat_Coordinate(double longitude, double latitude, int x, int y, int zoomLevel) {
        GlobalXYCoordinate globalXYCoordinate = new GlobalXYCoordinate(x, y);
        LonLatCoordinate lonLatCoordinate = globalXYCoordinate.convertToLonLatCoordinate(zoomLevel);
        assertEquals(longitude, lonLatCoordinate.getLongitude(), 0.005 * longitude);
        assertEquals(latitude, lonLatCoordinate.getLatitude(), 0.005 * latitude);
    }
}
