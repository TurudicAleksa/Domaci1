package gridrunner;

import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.List;

public class SelectionScreen extends Group {

    private final List<String> options;
    private final Text[] labels;
    private final Rectangle[] boxes;
    private int selectedIndex = 0;

    public SelectionScreen ( String title, List<String> options, double width, double height ) {
        this.options = options;

        Text titleText = new Text ( title );
        titleText.setFont ( Font.font ( 32 ) );
        titleText.setFill ( Color.WHITE );
        titleText.setX ( width / 2 - titleText.getBoundsInLocal ( ).getWidth ( ) / 2 );
        titleText.setY ( 80 );
        getChildren ( ).add ( titleText );

        int count = options.size ( );
        double boxWidth = 150;
        double spacing = 40;
        double totalWidth = count * boxWidth + ( count - 1 ) * spacing;
        double startX = width / 2 - totalWidth / 2;
        double boxY = height / 2 - 75;

        boxes = new Rectangle[count];
        labels = new Text[count];

        for ( int i = 0; i < count; i++ ) {
            double x = startX + i * ( boxWidth + spacing );

            Rectangle box = new Rectangle ( x, boxY, boxWidth, 150 );
            box.setFill ( Color.DARKSLATEGRAY );
            box.setStroke ( Color.WHITE );
            box.setStrokeWidth ( 2 );
            boxes[i] = box;

            Text label = new Text ( options.get ( i ) );
            label.setFont ( Font.font ( 18 ) );
            label.setFill ( Color.WHITE );
            label.setX ( x + boxWidth / 2 - label.getBoundsInLocal ( ).getWidth ( ) / 2 );
            label.setY ( boxY + 150 / 2 );
            labels[i] = label;

            getChildren ( ).addAll ( box, label );
        }

        updateSelectionVisuals ( );
    }

    public void handleKey ( KeyCode code ) {
        if ( code == KeyCode.LEFT || code == KeyCode.A ) {
            selectedIndex = ( selectedIndex - 1 + options.size ( ) ) % options.size ( );
            updateSelectionVisuals ( );
        } else if ( code == KeyCode.RIGHT || code == KeyCode.D ) {
            selectedIndex = ( selectedIndex + 1 ) % options.size ( );
            updateSelectionVisuals ( );
        }
        // Also allow direct number-key selection (1, 2, 3)
        else if ( code == KeyCode.DIGIT1 && options.size ( ) > 0 ) { selectedIndex = 0; updateSelectionVisuals ( ); }
        else if ( code == KeyCode.DIGIT2 && options.size ( ) > 1 ) { selectedIndex = 1; updateSelectionVisuals ( ); }
        else if ( code == KeyCode.DIGIT3 && options.size ( ) > 2 ) { selectedIndex = 2; updateSelectionVisuals ( ); }
    }

    private void updateSelectionVisuals ( ) {
        for ( int i = 0; i < boxes.length; i++ ) {
            boxes[i].setFill ( i == selectedIndex ? Color.MEDIUMPURPLE : Color.DARKSLATEGRAY );
            boxes[i].setStrokeWidth ( i == selectedIndex ? 4 : 2 );
        }
    }

    public int getSelectedIndex ( ) {
        return selectedIndex;
    }

    public String getSelectedOption ( ) {
        return options.get ( selectedIndex );
    }
}