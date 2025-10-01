import java.awt.Image;

public class SpriteFactory {

    private static Alien alienPrototype;

    public void initPrototypeAlien(int x, int y, Image alienImage) {
        Alien proto = new Alien(x, y);// Create a new Alien object
        proto.setImage(alienImage); // Set its image
        alienPrototype = proto;
    }

    public Sprite createSprite(String type, int x, int y) {
        switch (type.toLowerCase()) {

            case "alien":
                if (alienPrototype != null) {  // Check if the Alien prototype has been initialized
                    Sprite clone = alienPrototype.makeCopy(); 
                    clone.setX(x);
                    clone.setY(y);
                    return clone;
                } else {
                    
                    return new Alien(x, y); // If prototype not initialized, create a new Alien 

                }
            case "bomb":
                return new Bomb(x, y);
            case "player":
                return new Player(); 
            case "shot":
                return new Shot(x, y);
            case "gameover":
                return new GameOver();
            case "won":
                return new Won();
            default:
                throw new IllegalArgumentException("Unknown sprite type: " + type);
        }
    }
}
