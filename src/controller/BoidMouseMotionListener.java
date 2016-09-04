package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import model.BoidModel;
import model.Straight;
import veiw.BoidSpace;

/**
 *
 * @author Milán
 */
public class BoidMouseMotionListener implements MouseMotionListener {

    private BoidModel model;
    private BoidSpace view;
    private Straight way;
    private Straight dx;
    private Straight dy;

    public BoidMouseMotionListener(BoidModel model, BoidSpace view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void mouseDragged(MouseEvent me) {
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        Point2D.Double p = new Point2D.Double(me.getPoint().x, me.getPoint().y);
        if (view.isWallDrawingMode()) {
            boolean mark = false;
            for (int i = 0; i < model.getMap().getWallsPoints().length; i++) {
                way = new Straight(p, model.getMap().getWallsPoints()[i]);
                if (way.getLength() < Boids.WALLDRAWINGDISTANCE) {
                    p = model.getMap().getWallsPoints()[i];
                    view.setMarkCircle(p);
                    mark = true;
                }
            }
            if (!mark) {
                view.unSetMarkCircle();
            }
            if (view.isTemperaryWallP1Set()) {
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
            }
        } else {
            model.setRule6(p, true);
        }
    }

}
