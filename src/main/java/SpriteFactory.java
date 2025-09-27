import java.awt.Image;

public class SpriteFactory {

    private static Alien alienPrototype;

    public static void initPrototypeAlien(int x, int y, Image alienImage) {
        Alien proto = new Alien(x, y);
        proto.setImage(alienImage); 
        alienPrototype = proto;
    }

    public static Sprite createSprite(String type, int x, int y) {
        switch (type.toLowerCase()) {

            case "alien":
                if (alienPrototype != null) {
                    Alien clone = alienPrototype.clone(); 
                    clone.setX(x);
                    clone.setY(y);
                    return clone;
                } else {
                    
                    return new Alien(x, y);
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
