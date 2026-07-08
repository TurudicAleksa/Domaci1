package gridrunner;

import javafx.scene.paint.Color;

public class Constants {
    public static final String[] MAP = {
            "####################",
            "#S.................#",
            "#..................#",
            "#...########.......#",
            "#...P..............#",
            "#...P..............#",
            "#...########.......#",
            "#..................W",
            "W..................#",
            "#.......######PP...#",
            "#.......#..........#",
            "#.......#..........#",
            "#.......########...#",
            "#................G.#",
            "####################"
    };
    public static final String[] MAP2 = new String[]{
            "####################",
            "#.................S#",
            "#..................#",
            "#...########.......#",
            "#...#......###..####",
            "#...#..............#",
            "#...#.........#....#",
            "#PP####.......#....#",
            "#..................#",
            "##...#############P#",
            "#....#.............#",
            "#.......#######....#",
            "#.......#..........#",
            "#.......#G.........#",
            "####################"
    };
    public static final String[] MAP3 = new String[]{
            "####################",
            "#..................#",
            "#..................#",
            "#..................#",
            "#..................#",
            "#..................#",
            "#..................#",
            "#S................G#",
            "#..................#",
            "#..................#",
            "#..................#",
            "#..................#",
            "#..................#",
            "#..................#",
            "####################"
    };

    public static final int TILE_SIZE = 40;

    public static final int WINDOW_WIDTH  = Constants.MAP[0].length ( ) * Constants.TILE_SIZE;
    public static final int WINDOW_HEIGHT = Constants.MAP.length * Constants.TILE_SIZE;

    public static final Color BACKGROUND_COLOR    = Color.web ( "#c8c8c8" );
    public static final Color START_COLOR         = Color.web ( "#88c8ff" );
    public static final Color GOAL_COLOR          = Color.web ( "#44dd88" );
    public static final Color WALL_FILL_COLOR     = Color.web ( "#334466" );
    public static final Color WALL_STROKE_COLOR   = Color.web ( "#223355" );
    public static final Color PLAYER_FILL_COLOR   = Color.web ( "#2255cc" );
    public static final Color PLAYER_STROKE_COLOR = Color.web ( "#1133aa" );

    public static final double PLAYER_RADIUS = Constants.TILE_SIZE * 0.75 / 2;
    public static final double PLAYER_SPEED  = 180; // pixels per second
}
