import java.awt.Image;

/**
 * 
 * @author
 */
//changed the class to abstract
public abstract class Sprite implements Cloneable{ // to tell the  java compiler  it is okay to copy instance of this class 

        private boolean visible;
        private Image image;
        protected int x;
        protected int y;
        protected boolean dying;
        protected int dx;
        private static Alien alienPrototype = null;
        protected static Theme theme = new ClassicTheme();

        /*
         * Constructor
         */
        public Sprite() {
            visible = true;
        }

        public static void setTheme(Theme newTheme) {
        theme = newTheme;
        }

        public static Theme getTheme() {
            return theme;
        }
         
       // Clone method for Prototype Pattern
        public abstract Sprite makeCopy();

        public void die() {
            visible = false;
        }

        public boolean isVisible() {
            return visible;
        }

        protected void setVisible(boolean visible) {
            this.visible = visible;
        }

        public void setImage(Image image) {
            this.image = image;
        }

        public Image getImage() {
            return image;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }
        public int getY() {
            return y;
        }

        public int getX() {
            return x;
        }

        public void setDying(boolean dying) {
            this.dying = dying;
        }

        public boolean isDying() {
            return this.dying;
        }

        public static Sprite createSprite(String type, int x, int y) {
            switch (type.toLowerCase()) {
                case "alien":
                    if (alienPrototype == null) {
                        alienPrototype = new Alien(0, 0);
                    }
                    Alien clone = (Alien) alienPrototype.makeCopy();
                    clone.setX(x);
                    clone.setY(y);
                    return clone;
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