package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import model.BoidModel;
import model.Straight;
import veiw.BoidSpace;

/**
 *
 * @author Milán
 */
public class BoidMouseListener implements MouseListener {

    private BoidModel model;
    private BoidSpace view;
    private Straight way;
    private Straight dx;
    private Straight dy;

    public BoidMouseListener(BoidModel model, BoidSpace view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        Point2D.Double p = new Point2D.Double(me.getPoint().x, me.getPoint().y);
        if (!view.isWallDrawingMode()) {
            if (me.getButton() == MouseEvent.BUTTON3) {
                model.setRule5(p, true);
            } else {
                model.setRule5(p, false);
            }
        } else {
            for (int i = 0; i < model.getMap().getWallsPoints().length; i++) {
                way = new Straight(p, model.getMap().getWallsPoints()[i]);
                if (way.getLength() < Boids.WALLDRAWINGDISTANCE) {
                    p = model.getMap().getWallsPoints()[i];
                }
            }
            if (!view.isTemperaryWallP1Set()) {
                view.setTemperaryWallP1(p);
            } else {
                if (Boids.isTemperaryWallOnlyHorizontalOrVertical()) {
                    dx = new Straight(
                            (int) view.getTemperaryWallP1().x,
                            (int) view.getTemperaryWallP1().y,
                            (int) p.x,
                            (int) view.getTemperaryWallP1().y);
                    dy = new Straight(
                            (int) view.getTemperaryWallP1().x,
                            (int) view.getTemperaryWallP1().y,
                            (int) view.getTemperaryWallP1().x,
                            (int) p.y);
                    if (dx.getLength() > dy.getLength()) {
                        p = dx.getP2();
                    } else {
                        p = dy.getP2();
                    }
                }
                view.setTemperaryWallP2(p);
                model.getMap().addWall(new Straight(view.getTemperaryWallP1(), p));
                view.finalizeTemperaryWall();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

}
