package pl.edu.agh.wwwrsrm.parser.parameters;

import javafx.scene.paint.Color;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WayParameters {
    private final int zoomLevel;
    private final int wayWidth;
    private final Color color;
    private final String type;
}
