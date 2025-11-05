import java.awt.Color;
import java.awt.Image;

//Bridge: implementer interface
public interface Theme {
    Image getAlienImage();
    Image getPlayerImage();
    Image getBombImage();
    Image getShotImage(); 
    Color getBackgroundColor();
    Image getWonImage(); 
    Image getGameOverImage();
}
