package gridrunner;


import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Main extends Application {
    int score=0;
    int level=0;
    int player=0;
    enum GameState { PLAYER_SELECT, LEVEL_SELECT, PLAYING }
    @Override
    public void start ( Stage stage ) {
        final GameState[] state = {GameState.PLAYER_SELECT};
        Group root = new Group();
        Scene scene = new Scene(root, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);

        SelectionScreen playerSelect = new SelectionScreen(
                "Choose Your Player",
                Arrays.asList("Triangle", "Square", "Octagon"),
                Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT
        );
        root.getChildren().add(playerSelect);
        final int[] chosenPlayerType = {0};
        final int[] chosenLevel = {0};
        scene.setOnKeyReleased(ReleaseEvent -> {
            KeyCode code = ReleaseEvent.getCode();
            if (state[0] == GameState.PLAYER_SELECT) {
                if (code == KeyCode.ENTER || code == KeyCode.SPACE) {
                    chosenPlayerType[0] = playerSelect.getSelectedIndex();
                    root.getChildren().remove(playerSelect);

                    SelectionScreen levelSelect = new SelectionScreen(
                            "Choose Your Level",
                            Arrays.asList("Level 1", "Level 2", "Level 3"),
                            Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT
                    );
                    root.getChildren().add(levelSelect);
                    state[0] = GameState.LEVEL_SELECT;

                    // Swap key handler to control the level screen now
                    scene.setOnKeyPressed(levelEvent -> {
                        KeyCode levelCode = levelEvent.getCode();
                        if (levelCode == KeyCode.ENTER || levelCode == KeyCode.SPACE) {
                            chosenLevel[0] = levelSelect.getSelectedIndex();
                            root.getChildren().remove(levelSelect);
                            state[0] = GameState.PLAYING;
                            System.out.println(chosenLevel[0]);
                            startGame(stage, root, scene, chosenPlayerType[0], chosenLevel[0]);
                        } else {
                            levelSelect.handleKey(levelCode);
                        }
                    });
                } else {
                    playerSelect.handleKey(code);
                }
            }
        });
        stage.setTitle ( "Do cilja" );
        stage.setScene ( scene );
        stage.setResizable ( false );
        stage.show ( );
        //int score=0;
    }
    double speed=Constants.PLAYER_SPEED;
    public void startGame(Stage stage, Group root, Scene scene, int playerType, int levelIndex){
        Input input = new Input();
        int sides=3;
        int lives =3;
        double fanX=0;
        double fanY=0;
        System.out.println(levelIndex);
        Image bgImage = new Image ( getClass ( ).getResourceAsStream ( "/img/ground.jpg" ) );
        ImageView background = new ImageView ( bgImage );
        background.setFitWidth ( Constants.WINDOW_WIDTH );
        background.setFitHeight ( Constants.WINDOW_HEIGHT );
        root.getChildren ( ).add ( background );
        String[] lvl=Constants.MAP;
        switch(levelIndex){
            case 0: lvl=Constants.MAP;
                fanX=650;
                fanY=200;
            break;
            case 1: lvl=Constants.MAP2;
                fanX=365;
                fanY=270;
            break;
            case 2: lvl=Constants.MAP3;
                fanX=Constants.WINDOW_WIDTH/2;
                fanY=Constants.WINDOW_HEIGHT/2;
        }

        Level level = new Level(
                lvl,
                Constants.TILE_SIZE,
                Constants.WALL_FILL_COLOR,
                Constants.WALL_STROKE_COLOR,
                Constants.START_COLOR,
                Constants.GOAL_COLOR
        );
        root.getChildren ( ).add ( level );
        switch(playerType){
            case 0: sides=3;
            lives=2;
            speed=speed*1.4;
            break;
            case 1: sides=4;
            lives=4;
            speed=speed*0.75;
            break;
            case 2: sides=8;
            lives=3;
        }
        List<Pad> pads = new ArrayList<>();
        pads.add (new Pad (Constants.TILE_SIZE, 420, 300, 0.7, Color.SADDLEBROWN, Color.BLACK ) );
        pads.add (new Pad (Constants.TILE_SIZE, 420, 100, 1.4, Color.ORANGE, Color.BLACK ) );
        pads.add (new Pad (Constants.TILE_SIZE, 220, 400, 1.4, Color.ORANGE, Color.BLACK ) );
        Group padGroup = new Group ();
        for (Pad p:pads)
            padGroup.getChildren().add(0,p);
        root.getChildren().add(padGroup);
        Player player = new Player (
                Constants.PLAYER_RADIUS,
                level.getStartX ( ) + Constants.TILE_SIZE / 2. - Constants.PLAYER_RADIUS,
                level.getStartY ( ) + Constants.TILE_SIZE / 2. - Constants.PLAYER_RADIUS,
                sides,
                lives,
                Constants.PLAYER_FILL_COLOR,
                Constants.PLAYER_STROKE_COLOR
        );
        root.getChildren ( ).add ( player );
        Group ens = new Group();
        List<Enemy> enemies = new ArrayList<>();
        if(levelIndex==2){
            for(int i=0;i<9;i++){
                Enemy a =new Enemy(
                        Constants.TILE_SIZE-10,
                        100+i*70,
                        60+i*50,
                        Color.RED,
                        Color.DARKRED
                );
                Enemy b = new Enemy(
                        Constants.TILE_SIZE-10,
                        100+i*70,
                        Constants.WINDOW_HEIGHT-i*50-70,
                        Color.RED,
                        Color.DARKRED
                );
                a.setSpeed(3);
                b.setSpeed(3);
                b.setStartDir(-1);
                enemies.add(a);
                enemies.add(b);
                ens.getChildren().add(a);
                ens.getChildren().add(b);
            }
        }
        else{
            Enemy enemy = new Enemy(
                    Constants.TILE_SIZE-10,
                    200*levelIndex+100,
                    150*levelIndex+150,
                    Color.RED,
                    Color.DARKRED
            );
            enemy.setSpeed(1);
            enemies.add(enemy);
            ens.getChildren().add(enemy);
        }

        Fan fan = new Fan(fanX,fanY,100*levelIndex+150,10,Color.PURPLE);
        fan.setSpeed(Constants.PLAYER_SPEED);
        root.getChildren().add(fan);

        root.getChildren().add(ens);

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
            ls.add(new Heart(30,i*50+30,20,Color.INDIANRED,Color.PALEVIOLETRED));
        }
        for(Heart i:ls){
            liv.getChildren().add(i);
        }
        root.getChildren().add(liv);


        Group coins = new Group();
        List<Coin> cs = new ArrayList<>();
        for(int i=0;i<2;i++){
            Coin a = new Coin(0,0);
            a.getRandomPos(level.getWalls());
            cs.add(a);
            coins.getChildren().add(a);
        }
        root.getChildren().add(coins);


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

        Text sc= new Text("0");
        sc.setX(15);
        sc.setY(25);
        sc.setScaleX(2.0);
        sc.setScaleY(2.0);
        sc.setFill(Color.WHITE);
        root.getChildren().add(sc);


        Circle shd = new Circle(0,0,Constants.PLAYER_RADIUS,Color.BLUE);
        shd.setOpacity(0);
        shd.setFill(Color.TRANSPARENT);
        shd.setStrokeWidth(5);
        shd.setStroke(Color.BLUE);
        root.getChildren().add(shd);



        List<Shield> RShields = new ArrayList<>();
        List<Heart> RHearts = new ArrayList<>();


        AnimationTimer timer = new AnimationTimer ( ) {
            private double last;
            private int immunity=0;
            private int invinc=0;
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
                for(SideCannon a:level.getCannons()){
                    if(seconds%10==0 && !a.inAir) {
                        a.shoot(root);
                    }
                    a.update(root);
                }
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
                    if(invinc>0) invinc--;
                    double seed = Math.random()*10;
                    if(seed>9.5){
                        Shield s = new Shield(0,0,10);
                        s.getRandomPos(level.getWalls());
                        RShields.add(s);
                        root.getChildren().add(s);
                    }
                    if(seed<0.5){
                        Heart h = new Heart(30,0,0,Color.RED,Color.PALEVIOLETRED);
                        h.getRandomPos(level.getWalls());
                        RHearts.add(h);
                        root.getChildren().add(h);
                    }
                }
                this.last = now;

                if ( player.touches ( level.getGoal ( ) ) ) {
                    box.setOpacity(1);
                    win.setOpacity(1);
                    box.setStroke(Color.GREENYELLOW);
                    box.toFront();
                    win.toFront();
                    this.stop();
                    return;
                }
                if(!cs.isEmpty()){
                    Iterator<Coin> it = cs.iterator ( );
                    while ( it.hasNext ( ) ) {
                        Coin a = it.next ( );
                        if ( player.touches ( a.getX ( ), a.getY ( ), 7 ) ) {
                            score++;
                            it.remove ();
                            coins.getChildren().remove(a);
                            sc.setText (String.valueOf(score));
                        }
                    }
                }
                if(!RShields.isEmpty()){
                    Iterator<Shield> it = RShields.iterator ( );
                    while ( it.hasNext ( ) ) {
                        Shield a = it.next ( );
                        if ( player.touches ( a.getX ( ), a.getY ( ), 10 ) ) {
                            immunity=1;
                            it.remove ();
                            root.getChildren().remove(a);
                            shd.setOpacity(1);

                        }
                    }
                }
                if(!RHearts.isEmpty()){
                    Iterator<Heart> it = RHearts.iterator ( );
                    while ( it.hasNext ( ) ) {
                        Heart a = it.next ( );
                        if ( player.touches ( a.getX ( ), a.getY ( ), a.getSize() ) ) {
                            player.getLife();
                            it.remove ();
                            root.getChildren().remove(a);
                            int i = ls.size()-1;
                            Heart k = new Heart(30,(i+2)*50+30,20,Color.INDIANRED,Color.PALEVIOLETRED);
                            ls.add(k);
                            root.getChildren().add(k);
                        }
                    }
                }
                if(invinc==0){
                    for(Enemy a:enemies){
                        if(player.touches(a.getCenterX(),a.getCenterY(),a.getWidth())){
                            if(immunity==0) {
                                double x = level.getStartX() + Constants.TILE_SIZE / 2.;
                                double y = level.getStartY() + Constants.TILE_SIZE / 2.;
                                player.loseLives();
                                ls.get(ls.size() - 1).setOpacity(0);
                                ls.remove(ls.size() - 1);
                                player.reset(x, y);
                            }
                            else{
                                immunity=0;
                                invinc=2;
                                shd.setOpacity(0);
                            }
                        }
                    }
                    if(player.touchesBlade(fan.getX(),fan.getY(),fan.getWidth(),fan.getLength(),fan.getRotation())){
                        if(immunity==0){
                            double x = level.getStartX ( ) + Constants.TILE_SIZE / 2. ;
                            double y = level.getStartY ( ) + Constants.TILE_SIZE / 2. ;
                            player.loseLives();
                            ls.get(ls.size()-1).setOpacity(0);
                            ls.remove(ls.size()-1);
                            player.reset(x,y);
                        }else{
                            immunity=0;
                            invinc=2;
                            shd.setOpacity(0);
                        }

                    }
                    for(SideCannon a: level.getCannons()){
                        if(a.getProjectile()==null) break;
                        double xx = a.getProjectile().getCenterX();
                        double yy = a.getProjectile().getCenterY();
                        if(player.touches(xx,yy,10)){
                            if(immunity==0) {
                                box.setOpacity(1);
                                loss.setOpacity(1);
                                box.setStroke(Color.RED);
                                box.toFront();
                                loss.toFront();
                                this.stop();
                                return;
                            }
                            else{
                                immunity=0;
                                invinc=2;
                                shd.setOpacity(0);
                            }
                        }
                    }
                }
                if(player.getLives()==0){
                    box.setOpacity(1);
                    loss.setOpacity(1);
                    box.setStroke(Color.RED);
                    box.toFront();
                    loss.toFront();
                    this.stop();
                    return;

                }
                double speedMultiplier = 1.0;
                for ( Pad pad : pads ) {
                    if ( player.touches ( pad.getCenterX ( ), pad.getCenterY ( ), pad.getSize ( ) ) ) {
                        speedMultiplier = pad.getSpeedMultiplier ( );
                        break;
                    }

                }
                if(walkable){
                    player.update ( dt, speed*speedMultiplier, input, level.getTrueWalls ( ) );
                    for(Enemy a:enemies)
                        a.update(level.getTrueWalls ( ));
                }
                else {
                    player.update ( dt, speed*speedMultiplier, input, level.getWalls ( ) );
                    for(Enemy a:enemies)
                        a.update(level.getWalls ( ));
                }
                shd.setCenterX(player.getCenterX());
                shd.setCenterY(player.getCenterY());
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