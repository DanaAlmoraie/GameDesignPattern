import java.awt.Color;
import java.awt.Image;

public class BatmanTheme implements Theme{

    //Bridge: implementer implementation
    public BatmanTheme(){}

    public Image getAlienImage() {
        return imageFlyweight.loadImage("/img/batman/alien.png");
    }

    public Image getPlayerImage() {
        return imageFlyweight.loadImage("/img/batman/craft.png");
    }

    public Image getBombImage() {
        return imageFlyweight.loadImage("/img/batman/bomb.png");
    }

    public Image getShotImage() {
        return imageFlyweight.loadImage("/img/batman/shot.png");
    }

    public Color getBackgroundColor() {
        return Color.GRAY;
    }

    public Image getWonImage(){
        return imageFlyweight.loadImage("/img/batman/won.jpg");
    } 

    public Image getGameOverImage(){
        return imageFlyweight.loadImage("/img/batman/gameover.jpg");
    } 
}
