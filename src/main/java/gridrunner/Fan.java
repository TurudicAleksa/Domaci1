package gridrunner;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Fan extends Group {

    private Rectangle center;
    private Rectangle blade;
    private double rotation;
    private double speed=50;

    public Fan(double centerX, double centerY, double length, double width, Color color){
        center = new Rectangle(30,30,color);
        center.setTranslateX ( centerX - 30 / 2 );
        center.setTranslateY ( centerY - 30 / 2 );
        center.setRotate ( 45 );
        center.setStroke(Color.MEDIUMPURPLE);
        blade = new Rectangle ( width, length, color );
        blade.setTranslateX ( centerX - width / 2 );
        blade.setTranslateY ( centerY - length / 2 );
        blade.setStroke(Color.MEDIUMPURPLE);

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
