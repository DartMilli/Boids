package controller;

import veiw.BoidsWindow;

/**
 *
 * @author Milán
 */
public class Boids {
    
    public static final double WALLDRAWINGDISTANCE = 30;
    private static boolean isTemperaryWallOnlyHorizontalOrVertical = false;
    
    private Boids(){
    }
    
    public static void setTemperaryWallOnlyHorizontalOrVertical() {
        isTemperaryWallOnlyHorizontalOrVertical = !isTemperaryWallOnlyHorizontalOrVertical;
    }
    
    public static boolean isTemperaryWallOnlyHorizontalOrVertical(){
        return isTemperaryWallOnlyHorizontalOrVertical;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BoidsWindow();
            }
        });
    }

}
