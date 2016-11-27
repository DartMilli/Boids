package model;

/**
 *
 * @author Milán
 */
public class BoidVelocity {

    private double xValue;
    private double yValue;
    private double fi;
    private double length;

    public BoidVelocity() {
        this.xValue = 0.0;
        this.yValue = 0.0;
        this.fi = 0.0;
        this.length = 0.0;
    }

    public double getxValue() {
        return xValue;
    }

    public double getyValue() {
        return yValue;
    }

    public double getFi() {
        while (fi > 2 * Math.PI) {
            fi = fi - 2 * Math.PI;
        }
        while (fi < 0) {
            fi = fi + 2 * Math.PI;
        }
        return fi;
    }

    public double getFiInDeg() {
        double fiInDegrees;
        fiInDegrees = getFi() / Math.PI * 180;
        if (fiInDegrees < 0) {
            fiInDegrees += 360;
        } else if (360 < fiInDegrees) {
            fiInDegrees -= 360;
        }
        return fiInDegrees;
    }

    public double getLength() {
        return length;
    }

    public void setKoordinates(double xValue, double yValue) {
        this.xValue = xValue;
        this.yValue = yValue;
        this.length = Math.pow(Math.pow(xValue, 2.0) + Math.pow(yValue, 2.0), 0.5);
        //http://hu.wikipedia.org/wiki/Pol%C3%A1rkoordin%C3%A1ta-rendszer
        if (xValue > 0.0) {
            fi = Math.atan(yValue / xValue);
        } else if (xValue < 0.0 && yValue >= 0.0) {
            fi = Math.atan(yValue / xValue) + Math.PI;
        } else if (xValue < 0.0 && yValue < 0.0) {
            fi = Math.atan(yValue / xValue) - Math.PI;
        } else if (xValue == 0.0 && yValue < 0.0) {
            fi = Math.PI / 2.0;
        } else if (xValue == 0.0 && yValue > 0.0) {
            fi = -1.0 * Math.PI / 2.0;
        }
    }

    public void setPolarKoordinates(double fi, double length) {
        double calcFi;
        do {
            if (fi >= Math.PI) {
                calcFi = fi - Math.PI * 2.0;
            } else if (fi <= -1.0 * Math.PI) {
                calcFi = fi + Math.PI * 2.0;
            } else {
                calcFi = fi;
            }
        } while (fi > Math.PI && fi < -1.0 * Math.PI);
        this.fi = calcFi;
        this.length = length;
        this.xValue = length * Math.cos(fi);
        this.yValue = length * Math.sin(fi);
    }

}
