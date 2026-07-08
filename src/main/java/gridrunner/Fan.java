package gridrunner;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Fan extends Group {

    private Rectangle center;
    private Rectangle blade;
    private double rotation;
    private double speed=50;
    private double width;
    private double length;
    private double x;
    private double y;

    public double getX(){
        return this.x;

    }
    public double getY(){
        return this.y;
    }
    public double getLength()
    {
        return length;
    }
    public double getWidth(){
        return width;
    }
    public Fan(double centerX, double centerY, double length, double width, Color color){
        this.x=centerX;
        this.y=centerY;
        center = new Rectangle(30,30,color);
        center.setTranslateX ( centerX - 30 / 2 );
        center.setTranslateY ( centerY - 30 / 2 );
        center.setRotate ( 45 );
        center.setStroke(Color.MEDIUMPURPLE);
        blade = new Rectangle ( width, length, color );
        blade.setTranslateX ( centerX - width / 2 );
        blade.setTranslateY ( centerY - length / 2 );
        blade.setStroke(Color.MEDIUMPURPLE);
        this.length=length;
        this.width=width;
        getChildren ( ).addAll ( blade, center );
    }
    public void setSpeed(double p){
        this.speed=p;
    }
    public void update ( double dt ) {
        rotation += speed * dt;
        if ( rotation >= 360 ) {
            rotation -= 360;
        }
        blade.setRotate ( rotation );
    }
    public double getRotation ( ) {
        return rotation;
    }

    public Rectangle getBlade ( ) {
        return blade;
    }

    public Rectangle getCenter ( ) {
        return center;
    }
}
