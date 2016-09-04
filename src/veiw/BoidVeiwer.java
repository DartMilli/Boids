package veiw;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import javax.swing.JPanel;
import javax.swing.Timer;
import model.Boid;

/**
 *
 * @author Milán
 */
public class BoidVeiwer extends JPanel {

    private Boid boid;
    private boolean isVisible;

    private Timer timer = new Timer(40, new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent ae) {
            repaint();
        }
    });

    public BoidVeiwer(Boid Boid, Dimension dmsn, boolean isInMenu) {
        this.boid = Boid;
        this.setPreferredSize(dmsn);
        if (isInMenu) {
            timer.start();
        }
        isVisible = true;

    }

    public void setBoid(Boid b) {
        this.boid = b;
    }

    public void setTimeFrame(int frame) {
        if (frame == 0) {
            timer.stop();
        } else if (frame > 0 && frame <= 25) {
            if (!timer.isRunning()) {
                timer.start();
            }
            timer.setDelay(1000 / frame);
        }
    }

    public void setVisible() {
        isVisible = !isVisible;
    }

    public boolean getVisibility() {
        return isVisible;
    }

    @Override
    public void paint(Graphics g) {
        if (isVisible) {
            //super.paint(g);
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            g.setColor(Color.yellow);
            g.drawRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);
            Point2D.Double center = new Point2D.Double(
                    this.getPreferredSize().getWidth() / 2,
                    this.getPreferredSize().getHeight() / 2
            );
            for (int i = 0; i < boid.getVelocityComponents().length; i++) {
                if (boid.getVelocityComponents()[i] != null) {
                    Color arrowColor;
                    switch (i) {
                        case 0:
                            arrowColor = Color.YELLOW;
                            break;
                        case 1:
                            arrowColor = Color.WHITE;
                            break;
                        case 2:
                            arrowColor = Color.BLUE;
                            break;
                        case 3:
                            arrowColor = Color.GREEN;
                            break;
                        case 4:
                            arrowColor = Color.ORANGE;
                            break;
                        case 5:
                            arrowColor = Color.PINK;
                            break;
                        default:
                            arrowColor = Color.RED;
                            break;
                    }
                    veiw.BoidSpace.drawBoid(g, boid.getVelocityComponents()[i], 0.3, center, arrowColor, 0);
                }
            }
            veiw.BoidSpace.drawBoidToMap(
                    g,
                    boid,
                    center,
                    boid.getColor()
            );
        }
    }
}
