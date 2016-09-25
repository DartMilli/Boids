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

    public Straight(Point2D.Double P1, Point2D.Double P2) {
        this.p1x = P1.x;
        this.p2x = P2.x;
        this.p1y = P1.y;
        this.p2y = P2.y;
        normalVector = new double[2];
        directionVector = new double[2];
        calculate();
    }

    public Straight(Point2D.Double P1, double[] Vector, boolean isNormalVector) {
        this.p1x = P1.x;
        this.p1y = P1.y;
        if (isNormalVector) {
            this.p2x = p1x - Vector[1];
            this.p2y = Vector[0] + p1y;
        } else {
            this.p2x = Vector[0] + p1x;
            this.p2y = Vector[0] + p1y;
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

    public void setP1(Point2D.Double P1) {
        p1x = P1.x;
        p1y = P1.y;
        calculate();
    }

    public void setP2(Point2D.Double P2) {
        p2x = P2.x;
        p2y = P2.y;
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
        Point2D.Double out = new Point2D.Double(p1x, p1y);
        return out;
    }

    public Point2D.Double getP2() {
        Point2D.Double out = new Point2D.Double(p2x, p2y);
        return out;
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

    public double getLinePointY(double X) {
        double out;
        if (isVertical) {
            out = 0;
        } else if (isHorisontal) {
            out = p1y;
        } else {
            out = m * X + b;
        }
        return out;
    }

    public double getLinePointX(double Y) {
        double out;
        if (isVertical) {
            out = p1x;
        } else if (isHorisontal) {
            out = 0;
        } else {
            out = (Y - b) / m;
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
        out = out > Math.PI / 2.0 ? Math.PI - out : out;
        return out;
    }

    public static void main(String[] args) {
        Straight e = new Straight(2, 6, 7, 2);
        Straight f = new Straight(4, 1, 4, 9);
        Straight g = new Straight(2, 8, 6, 7);
        Straight h = new Straight(1, 3, 7, 3);
        Straight j = new Straight(9, 3, 13, 5);
        System.out.println("e meredekség: " + e.getM());
        System.out.println("f meredekség: " + f.getM());
        System.out.println("g meredekség: " + g.getM());
        System.out.println("h meredekség: " + h.getM());
        System.out.println("e metszet: " + e.getB());
        System.out.println("f metszet: " + f.getB());
        System.out.println("g metszet: " + g.getB());
        System.out.println("h metszet: " + h.getB());
        if (e.getIntersection(f).length > 1) {
            System.out.println("e és f metszéspontja: (" + e.getIntersection(f)[0] + "; " + e.getIntersection(f)[1] + ")");
            System.out.println("hajlásszöge: " + e.getPitchAngle(f) * 180 / Math.PI);
        } else {
            System.out.println("e és f nem metsz.");
        }
        if (f.getIntersection(g).length > 1) {
            System.out.println("f és g metszéspontja: (" + f.getIntersection(g)[0] + "; " + f.getIntersection(g)[1] + ")");
            System.out.println("hajlásszöge: " + f.getPitchAngle(g) * 180 / Math.PI);
        } else {
            System.out.println("f és g nem metsz.");
        }
        if (e.getIntersection(g).length > 1) {
            System.out.println("e és g metszéspontja: (" + e.getIntersection(g)[0] + "; " + e.getIntersection(g)[1] + ")");
            System.out.println("hajlásszöge: " + e.getPitchAngle(g) * 180 / Math.PI);
        } else {
            System.out.println("e és g nem metsz.");
        }
        if (e.getIntersection(h).length > 1) {
            System.out.println("e és h metszéspontja: (" + e.getIntersection(h)[0] + "; " + e.getIntersection(h)[1] + ")");
            System.out.println("hajlásszöge: " + e.getPitchAngle(h) * 180 / Math.PI);
        } else {
            System.out.println("e és h nem metsz.");
        }
        if (f.getIntersection(h).length > 1) {
            System.out.println("f és h metszéspontja: (" + f.getIntersection(h)[0] + "; " + f.getIntersection(h)[1] + ")");
            System.out.println("hajlásszöge: " + f.getPitchAngle(h) * 180 / Math.PI);
        } else {
            System.out.println("f és h nem metsz.");
        }
        if (h.getIntersection(g).length > 1) {
            System.out.println("h és g metszéspontja: (" + h.getIntersection(g)[0] + "; " + h.getIntersection(g)[1] + ")");
            System.out.println("hajlásszöge: " + h.getPitchAngle(g) * 180 / Math.PI);
        } else {
            System.out.println("h és g nem metsz.");
        }
        if (j.getIntersection(f).length > 1) {
            System.out.println("j és f metszéspontja: (" + j.getIntersection(f)[0] + "; " + j.getIntersection(f)[1] + ")");
            System.out.println("hajlásszöge: " + j.getPitchAngle(f) * 180 / Math.PI);
        } else {
            System.out.println("j és f nem metsz.");
        }
        System.out.println("e normál vektor: " + e.getNormalVector()[0] + "," + +e.getNormalVector()[1]);
        System.out.println("f normál vektor: " + f.getNormalVector()[0] + "," + +f.getNormalVector()[1]);
        System.out.println("g normál vektor: " + g.getNormalVector()[0] + "," + +g.getNormalVector()[1]);
        System.out.println("h normál vektor: " + h.getNormalVector()[0] + "," + +h.getNormalVector()[1]);
        System.out.println("j normál vektor: " + j.getNormalVector()[0] + "," + +j.getNormalVector()[1]);
        System.out.println("e irány vektor: " + e.getDirectionVector()[0] + "," + +e.getDirectionVector()[1]);
        System.out.println("f irány vektor: " + f.getDirectionVector()[0] + "," + +f.getDirectionVector()[1]);
        System.out.println("g irány vektor: " + g.getDirectionVector()[0] + "," + +g.getDirectionVector()[1]);
        System.out.println("h irány vektor: " + h.getDirectionVector()[0] + "," + +h.getDirectionVector()[1]);
        System.out.println("j irány vektor: " + j.getDirectionVector()[0] + "," + +j.getDirectionVector()[1]);
    }
}
