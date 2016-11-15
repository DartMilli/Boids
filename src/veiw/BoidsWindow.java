package veiw;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import javax.swing.JFrame;
import java.awt.geom.Point2D;
import model.Boid;
import model.BoidModel;
import model.Map;
import model.Straight;

/**
 *
 * @author Milán
 */
public class BoidsWindow extends JFrame {

    private BoidSpace veiw;
    private BoidModel model;

    public BoidsWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension dmsn = new Dimension(
                GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getSize()
        );
        setSize(new Dimension(dmsn));
        setTitle("Boids 1.3_beta");
        setLayout(null);
        setResizable(false);
        int margin = 0;
        Dimension spaceDmsn = new Dimension(
                (int) (dmsn.getWidth() - 2 * margin) - 5,
                (int) (dmsn.getHeight() - 2 * margin) - 30
        );
        model = new BoidModel(new Map(spaceDmsn, false));
        intiWallsAndBoids();
        veiw = new BoidSpace(model);
        setBackground(Color.BLACK);
        veiw.setBounds(margin, margin, (int) spaceDmsn.getWidth(), (int) spaceDmsn.getHeight());
        add(veiw);
        setVisible(true);
    }

    private void intiWallsAndBoids() {
        int testMode = 0, initialBoidNumber = 1;
        Map map = model.getMap();
        switch (testMode) {            
            case 4:
            case 3:
                initialBoidNumber = 150;
            case 1:
                map.addWall(new Straight(200, 100, 200, 600));
                map.addWall(new Straight(1100, 600, 1100, 100));
                map.addWall(new Straight(300, 100, 1000, 100));
                map.addWall(new Straight(1000, 600, 300, 600));
                break;          
            case 2:
                map.addWall(new Straight(200, 300, 600, 100));
                map.addWall(new Straight(700, 100, 1100, 300));
                map.addWall(new Straight(1100, 400, 700, 600));
                map.addWall(new Straight(600, 600, 200, 400));
                break;
            default:
            case 0:
                initialBoidNumber = 150;
                break;
        }
        Boid b;
        for (int i = 0; i < initialBoidNumber; i++) {
            b = new Boid(map, new Point2D.Double(
                    Math.random() * map.getWidth(),
                    Math.random() * map.getHeight()
            ));
            model.addBoid(b);
        }
    }
}
