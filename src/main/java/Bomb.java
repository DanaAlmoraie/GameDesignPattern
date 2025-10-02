import javax.swing.ImageIcon;

/**
 * 
 * @author 
 */
public class Bomb extends Sprite  {

	private final String bomb = "/img/bomb.png";
	private boolean destroyed;

	/*
	 * Constructor
	 */
	public Bomb(int x, int y) {
		setDestroyed(true);
		this.x = x;
		this.y = y;
		ImageIcon ii = new ImageIcon(this.getClass().getResource(bomb));
		setImage(ii.getImage());
	}

	public void setDestroyed(boolean destroyed) {
		this.destroyed = destroyed;
	}

	public boolean isDestroyed() {
		return destroyed;
	}
//Clone method 
	@Override
	public Sprite makeCopy() {
		Bomb bombObject=null;   // Create a variable to hold the cloned Bomb object
		try {
			bombObject= (Bomb) super.clone(); // Perform a copy of the  using Object.clone()
		} catch (CloneNotSupportedException e) {
            e.printStackTrace();	  // Print error if cloning is not supported
			}
			return bombObject;  // Return the cloned Bomb object
	}

}
