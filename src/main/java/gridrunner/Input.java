package gridrunner;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class Input {
    private Set<KeyCode> held;

    public Input ( ) {
        this.held = new HashSet<> ( );
    }

    public void keyPressed ( KeyEvent event ) {
        this.held.add ( event.getCode ( ) );
    }

    public void keyReleased ( KeyEvent event ) {
        if(event.getCode()==KeyCode.getKeyCode("R")){

        }
        this.held.remove ( event.getCode ( ) );
    }

    public boolean isDown ( KeyCode keyCode ) {
        return this.held.contains ( keyCode );
    }

    public boolean up ( ) {
        return this.isDown ( KeyCode.UP ) || isDown ( KeyCode.W );
    }

    public boolean down ( ) {
        return this.isDown ( KeyCode.DOWN ) || this.isDown ( KeyCode.S );
    }

    public boolean left ( ) {
        return this.isDown ( KeyCode.LEFT ) || this.isDown ( KeyCode.A );
    }

    public boolean right ( ) {
        return this.isDown ( KeyCode.RIGHT ) || this.isDown ( KeyCode.D );
    }
}