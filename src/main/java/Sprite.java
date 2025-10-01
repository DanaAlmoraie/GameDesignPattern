import java.awt.Image;

/**
 * 
 * @author
 */
public class Sprite implements Cloneable{ // to tell the  java compiler  it is okay to copy instance of this class 

        private boolean visible;
        private Image image;
        protected int x;
        protected int y;
        protected boolean dying;
        protected int dx;

        /*
         * Constructor
         */
        public Sprite() {
            visible = true;
        }
         
       // Clone method for Prototype Pattern

        public Sprite makeCopy() {
            return null;
        }
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
}