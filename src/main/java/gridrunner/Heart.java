package gridrunner;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Heart extends Polygon {

    public Heart ( double size, double centerX, double centerY, Color fillColor, Color strokeColor ) {
        double[] shape = {
                0.00, -0.20,   // center dip between the two lobes
                0.50, -0.50,   // right lobe peak
                0.55,  0.00,   // right side
                0.00,  0.55,   // bottom point
                -0.55,  0.00,   // left side
                -0.50, -0.50    // left lobe peak
        };

        for ( int i = 0; i < shape.length; i += 2 ) {
            double x = centerX + shape[i]     * size;
            double y = centerY + shape[i + 1] * size;
            getPoints ( ).addAll ( x, y );
        }

        setFill ( fillColor );
        setStroke ( strokeColor );
        setStrokeWidth ( size * 0.03 );
    }
}