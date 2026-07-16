package gridrunner;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;

import java.util.List;

public class Enemy extends Rectangle {

    private Translate position;
    private double centerX, centerY, radius,speed;
    private int dir=1;
    public void setStartDir(int d){
        dir=d;
    }
    public double getCenterX() {
        return centerX+radius/2;
    }

    public double getCenterY() {
        return centerY+radius/2;
    }

    public double getRadius() {
        return radius;
    }
    public void setSpeed(double speed){
        this.speed=speed;
    }

    public Enemy(double radius, double positionX, double positionY, Color fillColor, Color strokeColor){

        super(radius,radius,fillColor);
        this.centerX = positionX;
        this.centerY = positionY;
        this.radius  = radius;

        position = new Translate(centerX,centerY);

        super.setStroke(strokeColor);

        super.getTransforms ( ).addAll (
                this.position
        );

    }
    public void update(List<Rectangle> walls){
        moveAndResolve(speed,walls);
    }
    private void moveAndResolve ( double dy, List<Rectangle> walls ) {
        this.centerY += (dy*dir);
        this.position.setY ( this.centerY );
        boolean clear = true;

        for ( Rectangle wall : walls ) {
            if ( !overlaps ( wall ) ) {
                continue;
            }
            clear = false;
            dir=dir*(-1);
            break;

        }
        if ( !clear ) {
            this.centerY += (dir*dy);

        }

        this.position.setY ( this.centerY );
    }
    private boolean overlaps ( Rectangle rectangle ) {
        return this.getBoundsInParent().intersects(rectangle.getBoundsInParent());
    }

}
