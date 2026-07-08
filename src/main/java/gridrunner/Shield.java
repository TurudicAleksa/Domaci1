package gridrunner;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;

import java.util.List;

public class Shield extends Polygon {
    private double centerX,centerY,width;
    private Translate position;
    public Shield(double cx, double cy, double width){
        super.setFill(Color.BLUE);
        super.setStroke(Color.DARKBLUE);
        double angleOffset = 0;

        for ( int i = 0; i < 6; i++ ) {
            double angle = angleOffset + i * ( 2 * Math.PI / 6 );
            double x = width * Math.cos ( angle );
            double y = width * Math.sin ( angle );
            getPoints ( ).addAll ( x, y );
        }
        this.centerX = cx + width;
        this.centerY = cy + width;



        this.position = new Translate( this.centerX, this.centerY );
        super.getTransforms ( ).addAll (
                this.position
        );
    }

    public double getX() {
        return centerX;
    }

    public double getY() {
        return centerY;
    }

    public double getWidth() {
        return width;
    }
    public void getRandomPos(List<Rectangle> walls){
        boolean corr = false;
        boolean pass = false;
        double cx,cy=0;
        cx=0;
        while(!corr){
            cx = (Math.random()*(Constants.WINDOW_WIDTH-2*Constants.TILE_SIZE))+Constants.TILE_SIZE+14;
            cy = (Math.random()*(Constants.WINDOW_HEIGHT-2*Constants.TILE_SIZE))+Constants.TILE_SIZE+20;
            pass=true;
            for(Rectangle wall:walls){
                double left  = wall.getBoundsInParent ( ).getMinX ( );
                double right = left + wall.getWidth ( );
                double up    = wall.getBoundsInParent ( ).getMinY ( );
                double down  = up + wall.getHeight ( );

                double closestX = Math.max ( left, Math.min ( cx, right ) );
                double closestY = Math.max ( up, Math.min ( cy, down ) );

                double dx = cx - closestX;
                double dy = cy - closestY;

                if ( dx * dx + dy * dy <= 7*7 ) {
                    pass=false;
                }
            }
            if(pass) corr=true;
        }
        this.centerY=cy;
        this.centerX=cx;
        this.position = new Translate( this.centerX, this.centerY );
        super.getTransforms ( ).addAll (
                this.position
        );
    }
}
