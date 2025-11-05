import java.awt.Image;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;

public class imageFlyweight{
    private static final Map<String, Image> cache = new HashMap<>();

    public static Image loadImage(String path) {

        Image image = (Image)cache.get(path);

        if (image == null) {
            // load th image for the first time
            ImageIcon icon = new ImageIcon(imageFlyweight.class.getResource(path));
            image = icon.getImage();
            cache.put(path, image);
        }
  
        return image;
    }
}
