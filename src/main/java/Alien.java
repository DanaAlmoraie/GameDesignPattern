import javax.swing.ImageIcon;

/**
 * 
 * @author 
 */
public class Alien extends Sprite implements Cloneable{

    private Sprite bomb; // changed declaration type to Sprite
    private final String alien = "/img/alien.png";

    /*
     * Constructor
     */
    public Alien(int x, int y) {
        this.x = x;
        this.y = y;

        bomb = SpriteFactory.createSprite("bomb", x, y); // used factory instead of new Bomb()
        ImageIcon ii = new ImageIcon(this.getClass().getResource(alien));
        setImage(ii.getImage());

    }

    public void act(int direction) {
        this.x += direction;
    }

    /*
     * Getters & Setters
     */
    
	public Sprite getBomb() {
		return (Bomb) bomb; // added cast (Bomb)
	}

    @Override
    public Alien clone(){
        Alien AlienObject;
try{
    AlienObject=(Alien)super.clone();
    AlienObject.bomb = ((Bomb) this.bomb).clone();
}
    catch(CloneNotSupportedException e){
        e.printStackTrace();
        return null;
    }
    return AlienObject;
    }

}