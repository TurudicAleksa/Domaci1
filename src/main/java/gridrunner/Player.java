package gridrunner;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

import java.util.List;

public class Player extends Polygon {

    private Translate position;
    private double centerX, centerY, radius;
    private int lives = 3;
    public Player ( double radius, double positionX, double positionY, int sides,int lives, Color fillColor, Color strokeColor ) {
        this.centerX = 0;
        this.centerY = 0;
        this.radius  = radius;
        this.lives=lives;
        double angleOffset = 0;

        for ( int i = 0; i < sides; i++ ) {
            double angle = angleOffset + i * ( 2 * Math.PI / sides );
            double x = radius * Math.cos ( angle );
            double y = radius * Math.sin ( angle );
            getPoints ( ).addAll ( x, y );
        }

        super.setFill ( fillColor );
        super.setStroke ( strokeColor );
        super.setStrokeWidth ( radius * 0.03 );

        this.centerX = positionX + radius;
        this.centerY = positionY + radius;



        this.position = new Translate ( this.centerX, this.centerY );

        super.getTransforms ( ).addAll (
                this.position
        );
    }

    public int getLives() {
        return lives;
    }
    public void incLives(){
        lives++;

    }
    public void loseLives(){
        lives--;
    }


    public double getCenterX(){
        return this.centerX;
    }
    public double getCenterY(){
        return this.centerY;
    }
    public double getRadius(){
        return this.getRadius();
    }
    public void reset(double x, double y){
        this.centerX=x;
        this.centerY=y;
    }

    public void update(double dt, double speed, Input input, List<Rectangle> walls) {
        double dx = 0;
        double dy = 0;

        if ( input.left ( ) )  { dx -= speed * dt; }
        if ( input.right ( ) ) { dx += speed * dt; }
        if ( input.up ( ) )    { dy -= speed * dt; }
        if ( input.down ( ) )  { dy += speed * dt; }

        // Keep consistent speed on diagonals
        if ( dx != 0 && dy != 0 ) {
            double factor = 1.0 / Math.sqrt(2.0);
            dx *= factor;
            dy *= factor;
        }

        // Resolve each axis independently to allow sliding along walls
        this.moveAndResolve ( dx, 0, walls );
        this.moveAndResolve ( 0, dy, walls );
    }

    private void moveAndResolve ( double dx, double dy, List<Rectangle> walls ) {
        this.centerX += dx;
        this.centerY += dy;
        boolean clear = true;
        for ( Rectangle wall : walls ) {
            if ( !overlaps ( wall ) ) {
                continue;
            }

            clear = false;
        }

        if ( !clear ) {
            this.centerX -= dx;
            this.centerY -= dy;
        }

        this.position.setX ( this.centerX );
        this.position.setY ( this.centerY );
    }

    private boolean overlaps ( Rectangle rectangle ) {
        double wallMinX = rectangle.getBoundsInParent ( ).getMinX ( );
        double wallMinY = rectangle.getBoundsInParent ( ).getMinY ( );

        double left  = wallMinX;
        double right = wallMinX + rectangle.getWidth ( );
        double up    = wallMinY;
        double down  = wallMinY + rectangle.getHeight ( );

        double closestX = Math.max ( left, Math.min ( this.centerX, right ) );
        double closestY = Math.max ( up, Math.min ( this.centerY, down ) );

        double dx = this.centerX - closestX;
        double dy = this.centerY - closestY;

        return dx * dx + dy * dy <= this.radius * this.radius;
    }

    public boolean touches ( Rectangle goal ) {
        double goalMinX = goal.getBoundsInParent ( ).getMinX ( );
        double goalMinY = goal.getBoundsInParent ( ).getMinY ( );

        double left  = goalMinX;
        double right = goalMinX + goal.getWidth ( );
        double up    = goalMinY;
        double down  = goalMinY + goal.getHeight ( );

        boolean horizontal = ( this.centerX - this.radius ) >= left && ( this.centerX + radius ) <= right;
        boolean vertical   = ( this.centerY - this.radius ) >= up && ( this.centerY + radius ) <= down;

        return horizontal && vertical;
    }
    public boolean touches(double xx, double yy, double width){
        double x = xx - width / 2;
        double y = yy - width / 2;

        double left  = x;
        double right = x + width;
        double up    = y;
        double down  = y + width;

        double closestX = Math.max ( left, Math.min ( this.centerX, right ) );
        double closestY = Math.max ( up, Math.min ( this.centerY, down ) );

        double dx = this.centerX - closestX;
        double dy = this.centerY - closestY;

        return dx * dx + dy * dy <= this.radius * this.radius;
    }
    public boolean touchesBlade( double rectCenterX, double rectCenterY,
                                    double width, double height, double angleDegrees ) {

        double dx = this.centerX - rectCenterX;
        double dy = this.centerY - rectCenterY;

        double angleRad = Math.toRadians ( -angleDegrees );
        double localX = dx * Math.cos ( angleRad ) - dy * Math.sin ( angleRad );
        double localY = dx * Math.sin ( angleRad ) + dy * Math.cos ( angleRad );

        double left  = -width  / 2;
        double right =  width  / 2;
        double up    = -height / 2;
        double down  =  height / 2;

        double closestX = Math.max ( left, Math.min ( localX, right ) );
        double closestY = Math.max ( up,   Math.min ( localY, down ) );

        double distX = localX - closestX;
        double distY = localY - closestY;

        return distX * distX + distY * distY <= this.radius * this.radius;
    }
}