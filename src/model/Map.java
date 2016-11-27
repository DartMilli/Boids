package model;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 *
 * @author Milán
 */
public class Map {

    private Dimension dmsn;
    private Rectangle mapRect;
    private ArrayList<Straight> walls;
    private boolean infinity;

    public Map(Dimension dmsn, boolean infinity) {
        this.dmsn = dmsn;
        mapRect = new Rectangle(dmsn);
        walls = new ArrayList();
        this.infinity = infinity;
    }

    public void addWall(Straight wall) {
        if (mapRect.contains(wall.getP1()) && mapRect.contains(wall.getP2())) {
            walls.add(wall);
        }
    }

    public void removeWall() {
        if (!walls.isEmpty()) {
            walls.remove(walls.size() - 1);
        }
    }

    public void removeWall(int index) {
        if (index < walls.size() - 1 && index > 0) {
            walls.remove(index);
        }
    }

    public Straight[] getWalls() {
        Straight[] out = new Straight[walls.size()];
        for (int i = 0; i < out.length; i++) {
            out[i] = walls.get(i);
        }
        return out;
    }

    public Point2D.Double[] getWallsPoints() {
        Point2D.Double[] out = new Point2D.Double[walls.size() * 2];
        for (int i = 0; i < out.length; i++) {
            if (i % 2 == 0) {
                out[i] = new Point2D.Double(
                        walls.get(i / 2).getP1().x,
                        walls.get(i / 2).getP1().y);
            } else {
                out[i] = new Point2D.Double(
                        walls.get((i - 1) / 2).getP2().x, 
                        walls.get((i - 1) / 2).getP2().y );
            }
        }
        return out;
    }

    public void setInfinity(boolean inf) {
        this.infinity = inf;
    }

    public double getWidth() {
        return this.dmsn.getWidth();
    }

    public double getHeight() {
        return this.dmsn.getHeight();
    }

    public Dimension getDimension() {
        return this.dmsn;
    }

    public Rectangle getMapRect() {
        return mapRect;
    }

    public boolean isInfinit() {
        return this.infinity;
    }
}
