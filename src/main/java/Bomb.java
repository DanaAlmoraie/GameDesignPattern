import javax.swing.ImageIcon;

/**
 * 
 * @author 
 */
public class Bomb extends Sprite implements Cloneable {

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

	@Override
    public Bomb clone() {
        try {
            return (Bomb) super.clone();
        } catch(CloneNotSupportedException e){
        e.printStackTrace();
		return null;
    }
    }
}
