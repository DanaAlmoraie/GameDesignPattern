import java.awt.Image;

/**
 * 
 * @author
 */
public class Player extends Sprite implements Commons {

	private final int START_Y = 400;
	private final int START_X = 270;

	private int width;

	/*
	 * Constructor
	 */
	public Player() {
		//Bridge: set the image according to theme
		Image playerImage = Sprite.getTheme().getPlayerImage();

		width = playerImage.getWidth(null);

		setImage(playerImage);
		setX(START_X);
		setY(START_Y);
	}

	public void act() {
		x += dx;
		if (x <= 2)
			x = 2;
		if (x >= BOARD_WIDTH - 2 * width)
			x = BOARD_WIDTH - 2 * width;
	}

	
    // === Methods used by Command pattern ===
    public void moveLeft() {
        dx = -2;
    }

    public void moveRight() {
        dx = 2;
    }

    public void stopMoving() {
        dx = 0;
    }

	
	// override the method 
	@Override
	public Sprite makeCopy(){
		return null;
	}

}