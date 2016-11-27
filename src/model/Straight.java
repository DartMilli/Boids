package model;

import java.awt.geom.Point2D;

/**
 *
 * @author Milán
 */
public class Straight {

    private double p1x;
    private double p2x;
    private double p1y;
    private double p2y;
    private double m;
    private double b;
    private double a;
    private double length;
    private double[] normalVector;
    private double[] directionVector;
    private boolean isVertical = false;
    private boolean isHorisontal = false;

    public Straight(double p1x, double p1y, double p2x, double p2y) {
        this.p1x = p1x;
        this.p2x = p2x;
        this.p1y = p1y;
        this.p2y = p2y;
        normalVector = new double[2];
        directionVector = new double[2];
        calculate();
    }

    public Straight(Point2D.Double p1, Point2D.Double p2) {
        this.p1x = p1.x;
        this.p2x = p2.x;
        this.p1y = p1.y;
        this.p2y = p2.y;
        normalVector = new double[2];
        directionVector = new double[2];
        calculate();
    }

    public Straight(Point2D.Double p1, double[] vector, boolean isNormalVector) {
        this.p1x = p1.x;
        this.p1y = p1.y;
        if (isNormalVector) {
            this.p2x = p1x - vector[1];
            this.p2y = vector[0] + p1y;
        } else {
            this.p2x = vector[0] + p1x;
            this.p2y = vector[0] + p1y;
        }
        normalVector = new double[2];
        directionVector = new double[2];
        calculate();
    }

    private void calculate() {
        if (Double.compare(p2x, p1x) == 0) {
            isVertical = true;
            a = p1x;
            m = getM();
            b = getB();
        } else {
            m = (p2y - p1y) / (p2x - p1x);
            b = -1.0 * m * p1x + p1y;
            if (Double.compare(p1y, p2y) == 0) {
                isHorisontal = true;
            }
        }
        normalVector[0] = p2y - p1y;
        normalVector[1] = p1x - p2x;
        directionVector[0] = p2x - p1x;
        directionVector[1] = p2y - p1y;
        length = Math.pow(Math.pow(directionVector[0], 2) + Math.pow(directionVector[1], 2), 0.5);
        for (int i = 0; i < 2; i++) {
            normalVector[i] /= length;
            directionVector[i] /= length;
        }
    }

    public void setP1(Point2D.Double p1) {
        p1x = p1.x;
        p1y = p1.y;
        calculate();
    }

    public void setP2(Point2D.Double p2) {
        p2x = p2.x;
        p2y = p2.y;
        calculate();
    }

    public double[] getIntersection(Straight other) {
        double[] out = new double[2];
        if (Double.compare(m, other.getM()) != 0) {
            if (other.isVertical) {
                out[0] = other.getA();
                out[1] = m * other.getA() + b;
            } else if (isVertical) {
                out[0] = a;
                out[1] = other.getM() * a + other.getB();
            } else if (other.isHorisontal) {
                out[0] = getLinePointX(other.getP1().y);
                out[1] = other.getP1().y;
            } else if (isHorisontal) {
                out[0] = other.getLinePointX(p1y);
                out[1] = p1y;
            } else {
                out[0] = (other.getB() - b) / (m - other.getM());
                out[1] = m * out[0] + b;
            }

            double p3x = other.getP1().x;
            double p3y = other.getP1().y;
            double p4x = other.getP2().x;
            double p4y = other.getP2().y;
            if (!(((p1x <= out[0] && out[0] <= p2x) || (p1x > out[0] && out[0] > p2x))
                    && ((p1y <= out[1] && out[1] <= p2y) || (p1y > out[1] && out[1] > p2y))
                    && ((p3x <= out[0] && out[0] <= p4x) || (p3x > out[0] && out[0] > p4x))
                    && ((p3y <= out[1] && out[1] <= p4y) || (p3y > out[1] && out[1] > p4y)))) {
                out = new double[1];
                out[0] = 0;
            }
        } else {
            out = new double[1];
            out[0] = 0;
        }
        return out;
    }

    public double[] getLineIntersection(Straight other) {
        double[] out = new double[2];
        if (Double.compare(m, other.getM()) != 0) {
            out[0] = (other.getB() - this.getB()) / (this.getM() - other.getM());
            out[1] = this.getM() * out[0] + this.getB();
        } else {
            out = new double[1];
            out[0] = 0;
        }
        return out;
    }

    public double getM() {
        if (isVertical) {
            return Math.tan(Math.PI / 2.0);
        } else {
            return m;
        }
    }

    public double getB() {
        if (isVertical) {
            return Math.tan(Math.PI / 2.0);
        } else {
            return b;
        }
    }

    public double getA() {
        return a;
    }

    public Point2D.Double getP1() {
        return new Point2D.Double(p1x, p1y);
    }

    public Point2D.Double getP2() {
        return new Point2D.Double(p2x, p2y);
    }

    public double[] getNormalVector() {
        return normalVector;
    }

    public double[] getDirectionVector() {
        return directionVector;
    }

    public double getLength() {
        return length;
    }

    public double getLinePointY(double x) {
        double out;
        if (isVertical) {
            out = 0;
        } else if (isHorisontal) {
            out = p1y;
        } else {
            out = m * x + b;
        }
        return out;
    }

    public double getLinePointX(double y) {
        double out;
        if (isVertical) {
            out = p1x;
        } else if (isHorisontal) {
            out = 0;
        } else {
            out = (y - b) / m;
        }
        return out;
    }

    public double getPitchAngle(Straight other) {
        double out;
        double x1, x2, y1, y2;
        x1 = directionVector[0];
        x2 = other.getDirectionVector()[0];
        y1 = directionVector[1];
        y2 = other.getDirectionVector()[1];
        out = Math.acos(
                (x1 * x2 + y1 * y2)
                / (Math.sqrt(Math.pow(x1, 2) + Math.pow(y1, 2))
                * Math.sqrt(Math.pow(x2, 2) + Math.pow(y2, 2)))
        );
        return out;
    }
}
