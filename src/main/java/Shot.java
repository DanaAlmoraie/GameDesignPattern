

public class Shot extends Sprite {

    private final int H_SPACE = 6;
    private final int V_SPACE = 1;

    public Shot() {
    }

    public Shot(int x, int y) {

        //Bridge: set the image according to theme
        setImage(Sprite.getTheme().getShotImage());
        setX(x + H_SPACE);
        setY(y - V_SPACE);
    }

    // override the method 
    @Override
	public Sprite makeCopy(){
		return null;
	}

}