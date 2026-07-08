package gridrunner;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Level extends Group {

    private List<Rectangle> walls;
    private List<Rectangle> trueWalls;
    private List<SideCannon> cannons;
    private Rectangle goal;
    public double startX, startY;
    private Rectangle start;

    public Level ( String map[], double tileSize, Color wallFillColor, Color wallStrokeColor, Color startColor, Color goalColor ) {
        this.walls = new ArrayList<> ( );
        this.trueWalls = new ArrayList<> ( );
        this.cannons = new ArrayList<>();

        for ( int row = 0; row < map.length; row++ ) {
            for ( int column = 0; column < map[row].length ( ); column++ ) {
                double positionX = column * tileSize;
                double positionY = row * tileSize;

                switch ( map[row].charAt( column ) ) {
                    case '#': {
                        Rectangle wall = new Rectangle ( tileSize, tileSize );
                        wall.getTransforms ( ).addAll (
                                new Translate ( positionX, positionY )
                        );
                        wall.setFill ( wallFillColor );
                        wall.setStroke ( wallStrokeColor );
                        wall.setStrokeWidth ( tileSize * 0.04 );

                        this.walls.add ( wall );
                        this.trueWalls.add ( wall);

                        super.getChildren ( ).add ( wall );

                        break;
                    }

                    case 'S': {
                        this.start = new Rectangle ( tileSize, tileSize );
                        this.start.getTransforms ( ).addAll (
                                new Translate ( positionX, positionY )
                        );
                        this.start.setFill ( startColor );

                        super.getChildren ( ).add ( this.start );

                        this.startX = positionX;
                        this.startY = positionY;

                        break;
                    }
                    case 'G':{
                        this.goal = new Rectangle ( tileSize, tileSize );
                        this.goal.getTransforms ( ).addAll (
                                new Translate ( positionX, positionY )
                        );
                        this.goal.setFill ( goalColor );

                        super.getChildren ( ).add ( this.goal );

                        break;
                    }
                    case 'P':{
                        Rectangle wall = new Rectangle ( tileSize, tileSize );
                        wall.getTransforms ( ).addAll (
                                new Translate ( positionX, positionY )
                        );
                        wall.setFill ( wallFillColor );
                        wall.setStroke ( wallStrokeColor );
                        wall.setStrokeWidth ( tileSize * 0.04 );
                        wall.setOpacity(1);
                        wall.setId("p");
                        this.walls.add ( wall );

                        super.getChildren ( ).add ( wall );

                        break;
                    }
                    case 'W':{
                        boolean left=false;
                        if(column==0) left=false;
                        else if (column==map[row].length()-1) {
                            left=true;
                        } else if (map[row].charAt(column-1)=='#' || map[row].charAt(column-1)=='P') {
                            left=false;
                        } else left = true;
                        SideCannon a = new SideCannon(tileSize,tileSize,left);
                        a.getTransforms ( ).addAll (
                                new Translate ( positionX, positionY )
                        );
                        cannons.add(a);
                        super.getChildren().add(a);
                        break;
                    }
                }
            }
        }
    }

    public List<Rectangle> getWalls ( ) { return Collections.unmodifiableList ( this.walls ); }
    public List<Rectangle> getTrueWalls ( ) { return Collections.unmodifiableList ( this.trueWalls ); }
    public List<SideCannon> getCannons() {return Collections.unmodifiableList(this.cannons);}

    public void setOpacity(){
        for(Rectangle wall : walls){
            if(Objects.equals(wall.getId(), "p")){
                if(wall.getOpacity()==0.5)
                    wall.setOpacity(1);
                else wall.setOpacity(0.5);
            }
        }
    }

    public Rectangle getGoal ( ) { return this.goal; }

    public double getStartX ( ) { return this.startX; }
    public double getStartY ( ) { return this.startY; }
}