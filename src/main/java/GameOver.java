import java.awt.Image;

/**
 * 
 * @author
 */
public class GameOver extends Sprite implements Commons {

	private int width;

	/*
	 * Constructor
	 */
	public GameOver() {
		//Bridge: set the image according to theme
		Image gameOverImage = Sprite.getTheme().getGameOverImage();

		setWidth(gameOverImage.getWidth(null));

		setImage(gameOverImage);
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
