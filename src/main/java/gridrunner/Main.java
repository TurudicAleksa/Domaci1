package gridrunner;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    @Override
    public void start ( Stage stage ) {
        Group root = new Group ( );
        int lives=3;
        Input input = new Input();
        Image bgImage = new Image ( getClass ( ).getResourceAsStream ( "/img/ground.jpg" ) );
        ImageView background = new ImageView ( bgImage );
        background.setFitWidth ( Constants.WINDOW_WIDTH );
        background.setFitHeight ( Constants.WINDOW_HEIGHT );
        root.getChildren ( ).add ( background );
        Level level = new Level (
                Constants.MAP,
                Constants.TILE_SIZE,
                Constants.WALL_FILL_COLOR,
                Constants.WALL_STROKE_COLOR,
                Constants.START_COLOR,
                Constants.GOAL_COLOR
        );
        root.getChildren ( ).add ( level );

        Player player = new Player (
               Constants.PLAYER_RADIUS,
               level.getStartX ( ) + Constants.TILE_SIZE / 2. - Constants.PLAYER_RADIUS,
               level.getStartY ( ) + Constants.TILE_SIZE / 2. - Constants.PLAYER_RADIUS,
               8,
               Constants.PLAYER_FILL_COLOR,
               Constants.PLAYER_STROKE_COLOR
        );
        root.getChildren ( ).add ( player );

        Enemy enemy = new Enemy(
                Constants.TILE_SIZE-10,
                200,
                300,
                Color.RED,
                Color.DARKRED
        );
        Fan fan = new Fan(650,200,150,10,Color.PURPLE);
        fan.setSpeed(Constants.PLAYER_SPEED);
        root.getChildren().add(fan);
        enemy.setSpeed(1);
        root.getChildren().add(enemy);

        Rectangle box = new Rectangle(600,300,Color.GRAY);
        box.setX(Constants.WINDOW_WIDTH/8);
        box.setY(Constants.WINDOW_HEIGHT/4);
        box.setOpacity(0);
        root.getChildren().add(box);

        Text win = new Text("YOU WIN");
        win.setX(Constants.WINDOW_WIDTH/2-20);
        win.setY(Constants.WINDOW_HEIGHT/2);
        win.setFill(Color.GREENYELLOW);
        win.setOpacity(0);
        win.setScaleX(8);
        win.setScaleY(8);
        root.getChildren().add(win);

        Text loss = new Text("GAME OVER");
        loss.setX(Constants.WINDOW_WIDTH/2-20);
        loss.setY(Constants.WINDOW_HEIGHT/2);
        loss.setFill(Color.RED);
        loss.setOpacity(0);
        loss.setScaleX(8);
        loss.setScaleY(8);
        root.getChildren().add(loss);
        Group liv = new Group();
        List<Heart> ls= new ArrayList<>();
        for(int i=player.getLives();i>0;i--){
            ls.add(new Heart(30,i*50,20,Color.INDIANRED,Color.PALEVIOLETRED));
        }
        for(Heart i:ls){
            liv.getChildren().add(i);
        }
        root.getChildren().add(liv);

        Scene scene = new Scene ( root, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT );

       // scene.setFill ( Constants.BACKGROUND_COLOR );

        scene.setOnKeyPressed ( input::keyPressed );
        scene.setOnKeyReleased ( input::keyReleased );

        Text time = new Text("00:00");
        time.setX(Constants.WINDOW_WIDTH/2);
        time.setY(20);
        time.setScaleX(3.0);
        time.setScaleY(3.0);
        time.setFill(Color.WHITE);
        root.getChildren().add(time);
        AnimationTimer timer = new AnimationTimer ( ) {
            private double last;
            private double timer=0;
            private int seconds=0;
            private int minutes=0;
            boolean walkable=false;
            @Override
            public void handle ( long now ) {
                if ( this.last == 0 ) {
                    this.last = now;
                }
                if(input.isDown(KeyCode.R)){
                    double x = level.getStartX ( ) + Constants.TILE_SIZE / 2. ;
                    double y = level.getStartY ( ) + Constants.TILE_SIZE / 2. ;
                    player.reset(x,y);
                }

                double dt = ( now - this.last ) / 10e8;
                timer+=dt;
                fan.update(dt);
                if(timer>1)
                {
                    timer-=1;
                    seconds++;
                    if(seconds%10==0){
                        level.setOpacity();
                        walkable=!walkable;
                    }
                    if(seconds>=60){
                        minutes++;
                        seconds-=60;
                    }
                    String formattedSec = String.format("%02d", seconds);
                    String formattedMin = String.format("%02d", minutes);
                    time.setText(formattedMin+":"+formattedSec);
                }
                this.last = now;

                if ( player.touches ( level.getGoal ( ) ) ) {
                    box.setOpacity(1);
                    win.setOpacity(1);
                    box.setStroke(Color.GREENYELLOW);
                    return;
                }
                if(player.touches(enemy.getCenterX(),enemy.getCenterY(),enemy.getWidth())){
                    double x = level.getStartX ( ) + Constants.TILE_SIZE / 2. ;
                    double y = level.getStartY ( ) + Constants.TILE_SIZE / 2. ;
                    player.loseLives();
                    ls.get(ls.size()-1).setOpacity(0);
                    ls.remove(ls.size()-1);
                    player.reset(x,y);
                }
                if(player.getLives()==0){
                    box.setOpacity(1);
                    loss.setOpacity(1);
                    box.setStroke(Color.RED);
                    return;
                }
                if(walkable){
                    player.update ( dt, Constants.PLAYER_SPEED, input, level.getTrueWalls ( ) );
                    enemy.update(level.getTrueWalls ( ));
                }
                else {
                    player.update ( dt, Constants.PLAYER_SPEED, input, level.getWalls ( ) );
                    enemy.update(level.getWalls ( ));
                }
            }
        };
        timer.start ( );

        stage.setTitle ( "Do cilja" );
        stage.setScene ( scene );
        stage.setResizable ( false );
        stage.show ( );
    }

    public static void main ( String[] args ) {
        launch ( args );
    }
}