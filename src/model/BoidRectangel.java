package model;

import java.awt.Rectangle;
import java.awt.geom.Point2D;

/**
 *
 * @author Milán
 */
public class BoidRectangel {

    private double zerusX;
    private double zerusY;
    private double width;
    private double height;
    private double topRightX;
    private double topRightY;
    private double bottomRightX;
    private double bottomRightY;
    private double bottomLeftX;
    private double bottomLeftY;

    public BoidRectangel(Rectangle rect) {
        this.zerusX = rect.getX();
        this.zerusY = rect.getY();
        this.width = rect.getWidth();
        this.height = rect.getHeight();
        calculate();
    }

    public BoidRectangel(double zerusX, double zerusY, double width, double height) {
        this.zerusX = zerusX;
        this.zerusY = zerusY;
        this.width = width;
        this.height = height;
        calculate();
    }

    private void calculate() {
        topRightX = zerusX + width;
        topRightY = zerusY;
        bottomRightX = zerusX + width;
        bottomRightY = zerusY + height;
        bottomLeftX = zerusX;
        bottomLeftY = zerusY + height;
    }

    public boolean contains(Point2D.Double P) {
        boolean out = false;
        Straight up = new Straight(zerusX, zerusY, topRightX, topRightY);
        Straight down = new Straight(bottomLeftX, bottomLeftY, bottomRightX, bottomRightY);
        Straight left = new Straight(zerusX, zerusY, bottomLeftX, bottomLeftX);
        Straight right = new Straight(topRightX, topRightY, bottomRightX, bottomRightY);
        if (left.getLinePointX(P.y) < P.x
                && right.getLinePointX(P.y) > P.x
                && up.getLinePointY(P.x) < P.y
                && down.getLinePointY(zerusX) > P.y) {
            out = true;
        }
        return out;
    }
    
    public void rotate(double angle){
        
    }

}
