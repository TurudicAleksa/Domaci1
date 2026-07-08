package gridrunner;

import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;

import java.util.List;

public class Coin extends Ellipse {
    private double centerX, centerY;


    public double getX() {
        return centerX;
    }


    public double getY() {
        return centerY;
    }

    public Coin(double x, double y){
        super(x,y,7,10);
        centerX=x;
        centerY=y;
        super.setFill(Color.YELLOW);
        super.setStroke(Color.BLACK);
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
            super.setCenterX(cx);
            super.setCenterY(cy);
    }


}
