
import java.awt.Color;
import java.awt.Image;

//Bridge: implementer implementation
public class HelloKittyTheme implements Theme {

    public Image getAlienImage() {
        return imageFlyweight.loadImage("/img/kitty/alien.png");
    }

    public Image getPlayerImage() {
        return imageFlyweight.loadImage("/img/kitty/craft.png");
    }

    public Image getBombImage() {
        return imageFlyweight.loadImage("/img/kitty/bomb.png");
    }

    public Image getShotImage() {
        return imageFlyweight.loadImage("/img/kitty/shot.png");
    }

    public Color getBackgroundColor() {
        return Color.PINK;
    }

    public Image getWonImage(){
        return imageFlyweight.loadImage("/img/kitty/won.jpg");
    } 

    public Image getGameOverImage(){
        return imageFlyweight.loadImage("/img/kitty/gameover.jpg");
    } 
}
