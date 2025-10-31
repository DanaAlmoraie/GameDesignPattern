import javax.swing.ImageIcon;

/**
 * 
 * @author 
 */
public class Alien extends Sprite {

    private Sprite bomb; // changed declaration type to Sprite
    private final String alien = "/img/alien.png";

    /*
     * Constructor
     */
    public Alien(int x, int y) {
        this.x = x;
        this.y = y;

        bomb = Sprite.createSprite("bomb", x, y); // used factory instead of new Bomb()
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
    
    // Clone method (deep copy needed because of Bomb)
    @Override
    public Sprite makeCopy(){
        Alien AlienObject=null;//Creates a variable to store the cloned object
        try{
           AlienObject=(Alien)super.clone();// Perform a copy of the  using Object.clone()
           AlienObject.bomb = ((Bomb) this.bomb).makeCopy();//Create a new copy of the bomb from the original Alien, and set it as the bomb of the cloned Alien.
        }
        catch(CloneNotSupportedException e){
            e.printStackTrace(); // Print error if cloning is not supported
    }
            return AlienObject;// return the cloned Alien
    }

}