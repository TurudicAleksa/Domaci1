package gridrunner;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Pad extends Rectangle {

    private double centerX, centerY;
    private double speedMultiplier;

    public Pad ( double size, double centerX, double centerY, double speedMultiplier, Color fillColor, Color strokeColor ) {
        super ( size, size, fillColor );
        this.centerX = centerX;
        this.centerY = centerY;
        this.speedMultiplier = speedMultiplier;

        setTranslateX ( centerX - size / 2 );
        setTranslateY ( centerY - size / 2 );
        setStroke ( strokeColor );
    }

    public double getCenterX ( ) {
        return centerX;
    }

    public double getCenterY ( ) {
        return centerY;
    }

    public double getSpeedMultiplier ( ) {
        return speedMultiplier;
    }

    public double getSize ( ) {
        return getWidth ( );
    }
}