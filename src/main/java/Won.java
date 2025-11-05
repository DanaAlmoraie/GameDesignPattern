import java.awt.Image;

/**
 * 
 * @author 
 */
public class Won extends Sprite implements Commons{
    private int width;

    /*
     * Constructor
     */
    public Won() {

        //Bridge: set the image according to theme
        Image wonImage = Sprite.getTheme().getWonImage();

        width = wonImage.getWidth(null); 

        setImage(wonImage);
        setX(0);
        setY(0);
    }

    /*
     * Getters & Setters
     */
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

    // override the method 
    @Override
	public Sprite makeCopy(){
		return null;
	} 

}
