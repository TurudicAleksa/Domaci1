package gridrunner;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;

import java.util.List;

public class SideCannon extends Rectangle {
    private boolean left;
    private int dir=1;
    public boolean inAir=false;
    private Circle projectile;
    private double x,y;
    public SideCannon(double height, double width,boolean left){
        super(height,width);
        super.setFill(Color.DARKGRAY);
        this.left=left;

    }
    public Circle getProjectile(){
        return projectile;

    }
    public void shoot(Group root){
        x=getBoundsInParent().getCenterX();
        y=getBoundsInParent().getCenterY();

        if(projectile!=null) {
            root.getChildren().remove(projectile);
            projectile=null;
        }
        if(left){
            dir=-1;
            projectile = new Circle(x-20,y,10, Color.BLACK);
        } else{
            projectile = new Circle(x+20,y,10,Color.BLACK);
        }
        root.getChildren().add(projectile);
        inAir=true;

    }
    public void update(Group root){
        if(projectile==null) return;
        projectile.setCenterX(projectile.getCenterX()+(Constants.PLAYER_SPEED*dir/50));
        if (projectile.getCenterX() < 0 ||
                projectile.getCenterX() > Constants.WINDOW_WIDTH) {
            inAir = false;
            root.getChildren().remove(projectile);
            projectile=null;
        }
    }

    }

