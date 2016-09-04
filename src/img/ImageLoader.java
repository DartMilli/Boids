package img;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author Milán
 */
public final class ImageLoader {

    private static boolean[][] image = new boolean[][]{
            {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true},
            {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true},
            {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true},
            {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true},
            {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true},
            {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true},
            {false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true},
            {false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true},
            {false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true},
            {false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true},
            {false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true},
            {false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true},
            {false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true},
            {false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true},
            {false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true},
            {false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true},
            {false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, false},
            {false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, false, false, false, false},
            {false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, false, false, false, false, false},
            {false, false, false, false, false, false, true, true, true, true, true, true, true, true, false, false, false, false, false, false},
            {false, false, false, false, false, false, true, true, true, true, true, true, true, true, false, false, false, false, false, false},
            {false, false, false, false, false, false, true, true, true, true, true, true, true, true, false, false, false, false, false, false},
            {false, false, false, false, false, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false},
            {false, false, false, false, false, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false},
            {false, false, false, false, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false},
            {false, false, false, false, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false},
            {false, false, false, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false},
            {false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false},
            {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false},
            {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false},
            {false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false},
            {false, false, false, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false},
            {false, false, false, false, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false},
            {false, false, false, false, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false},
            {false, false, false, false, false, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false},
            {false, false, false, false, false, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false},
            {false, false, false, false, false, false, true, true, true, true, true, true, true, true, false, false, false, false, false, false},
            {false, false, false, false, false, false, true, true, true, true, true, true, true, true, false, false, false, false, false, false},
            {false, false, false, false, false, false, true, true, true, true, true, true, true, true, false, false, false, false, false, false},
            {false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, false, false, false, false, false},
            {false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, false, false, false, false},
            {false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, false},
            {false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true},
            {false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true},
            {false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true},
            {false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true},
            {false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true},
            {false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true},
            {false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true},
            {false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true},
            {false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true},
            {false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true},
            {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true},
            {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true},
            {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true},
            {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true},
            {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true},
            {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true}
        };
    
    private static String[] imageNames = {
        "/img/01.gif",
        "/img/03.gif",
        "/img/05.gif",
        "/img/07.gif",
        "/img/09.gif",
        "/img/11.gif",
        "/img/13.gif",
        "/img/15.gif",
        "/img/17.gif",
        "/img/19.gif",
        "/img/21.gif",
        "/img/23.gif"};
    
    private static ImageIcon[] boidPictures;
    
    public ImageLoader() {
        loadImages();
    }   
    
    public static boolean[][] getImage(){
        return image;
    }
    
    public static String[] getImageNames(){
        return imageNames;
    }
    
    public void loadImages(){
        boidPictures = new ImageIcon[imageNames.length];
        BufferedImage icon;
        for (int i = 0; i < imageNames.length; i++) {
            try {
                icon = ImageIO.read(this.getClass().getResource(imageNames[i]));
                boidPictures[i] = new ImageIcon(icon);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public static ImageIcon[] getBoidPictures(){
        return boidPictures;
    }

}
