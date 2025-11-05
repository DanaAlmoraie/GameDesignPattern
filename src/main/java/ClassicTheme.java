import java.awt.Color;
import java.awt.Image;

//Bridge: implementer implementation
public class ClassicTheme implements Theme {

    public Image getAlienImage() {
        return imageFlyweight.loadImage("/img/classic/alien.png");
    }

    public Image getPlayerImage() {
        return imageFlyweight.loadImage("/img/classic/craft.png");
    }

    public Image getBombImage() {
        return imageFlyweight.loadImage("/img/classic/bomb.png");
    }

    public Image getShotImage() {
        return imageFlyweight.loadImage("/img/classic/shot.png"); 
    }

    public Color getBackgroundColor() {
        return Color.BLACK; 
    }

    public Image getWonImage(){
        return imageFlyweight.loadImage("/img/classic/won.jpg");
    } 

    public Image getGameOverImage(){
        return imageFlyweight.loadImage("/img/classic/gameover.png");
    } 
}
