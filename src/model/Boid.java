package model;

import java.awt.Color;
import java.awt.geom.Point2D;

/**
 *
 * @author Milán
 */
public class Boid {

    private Point2D.Double actualPosition;
    private Point2D.Double newPosition;
    private BoidVelocity velocity;
    private BoidVelocity[] veloctyComponents = new BoidVelocity[6];
    private BoidVelocity theoreticalSpeed;
    private Point2D.Double[] pastPositions;
    private Map map;
    private double maximumSpeed = 12.0;
    private double minimumSpeed = 1.0;
    private double mass = 1.0;
    private Color color;
    private int boidIndex = 0;

    public Boid(Map map, Point2D.Double atualPosition) {
        this.map = map;
        this.actualPosition = atualPosition;
        pastPositions = new Point2D.Double[30];
        for (int i = 0; i < pastPositions.length; i++) {
            pastPositions[i] = new Point2D.Double(actualPosition.x, actualPosition.y);
        }
        this.newPosition = actualPosition;
        velocity = new BoidVelocity();
        velocity.setPolarKoordinates(Math.random() * Math.PI * 2,
                Math.random() * (maximumSpeed - minimumSpeed) + minimumSpeed
        );
        int r = (int) (Math.random() * 256);
        int g = (int) (Math.random() * 256);
        int b = (int) (Math.random() * 256);
        color = new Color(r, g, b);
        theoreticalSpeed = new BoidVelocity();
    }

    public Point2D.Double getActualPosition() {
        return actualPosition;
    }

    public Point2D.Double getNewPosition() {
        return newPosition;
    }

    public BoidVelocity getVelocity() {
        return velocity;
    }

    public double getMass() {
        return this.mass;
    }

    public Color getColor() {
        return color;
    }

    public int getBoidIndex() {
        return this.boidIndex;
    }

    public BoidVelocity[] getVelocityComponents() {
        return this.veloctyComponents;
    }

    public Point2D.Double[] getPastPosition() {
        return this.pastPositions;
    }

    public double getMaximumSpeed() {
        return this.maximumSpeed;
    }

    public BoidVelocity getTheoreticalSpeed() {
        double vx = 0, vy = 0, fi, length = 0;
        int pieces = 0;
        for (int i = 0; i < veloctyComponents.length; i++) {
            if (veloctyComponents[i] != null) {
                vx += veloctyComponents[i].getxValue();
                vy += veloctyComponents[i].getyValue();
                length += veloctyComponents[i].getLength();
                if (Double.doubleToRawLongBits(veloctyComponents[i].getLength()) != 0.0) {
                    pieces++;
                }
            }
        }
        theoreticalSpeed.setKoordinates(vx, vy);
        if (pieces != 0) {
            fi = theoreticalSpeed.getFi();
            length = length / pieces;
            theoreticalSpeed.setPolarKoordinates(fi, length);
        }
        if (Double.doubleToRawLongBits(length) == 0.0) {
            fi = theoreticalSpeed.getFi();
            length = maximumSpeed;
            theoreticalSpeed.setPolarKoordinates(fi, length);
        }
        return theoreticalSpeed;
    }

    public void setActualPosition(Point2D.Double atualPosition) {
        this.actualPosition = atualPosition;
    }

    public void setNewPosition(Point2D.Double newPosition) {
        this.newPosition = newPosition;
    }

    public void setColor(Color c) {
        this.color = c;
    }

    public void setMass(double massfactor) {
        this.mass *= massfactor;
    }

    public void setSpeedLimitation(double max, double min) {
        if (max > min && min > 0.0) {
            maximumSpeed = max;
            minimumSpeed = min;
        }
        this.setVelocity(velocity);
    }

    public void setBoidIndex(int index) {
        this.boidIndex = index;
    }

    public void setVelocity(BoidVelocity velocity) {
        BoidVelocity newVelocity = new BoidVelocity();
        double length = velocity.getLength();
        double fi = velocity.getFi();
        if (length > maximumSpeed) {
            newVelocity.setPolarKoordinates(fi, maximumSpeed);
        } else if (length < minimumSpeed) {
            newVelocity.setPolarKoordinates(fi, minimumSpeed);
        } else {
            newVelocity.setPolarKoordinates(fi, length);
        }
        this.velocity = newVelocity;
    }

    public void setVelocity(BoidVelocity[] velocities) {
        BoidVelocity newVelocity = new BoidVelocity();
        double x = 0, y = 0, pieces = 0;

        if (velocities.length > 0) {
            for (int i = 0; i < velocities.length; i++) {
                if (i < veloctyComponents.length) {
                    veloctyComponents[i] = velocities[i];
                }
            }
            for (int i = 0; i < velocities.length; i++) {
                if (velocities[i].getLength() > 0.0) {
                    x += velocities[i].getxValue();
                    y += velocities[i].getyValue();
                    pieces++;
                }
            }
            x = x / pieces;
            y = y / pieces;
        }
        if (pieces > 0) {
            newVelocity.setKoordinates(x, y);
            setVelocity(newVelocity);
        }
    }

    private void setPastPositions() {
        double x, y, dx, dy;
        boolean correction = false;
        for (int i = pastPositions.length - 1; i > 0; i--) {
            x = pastPositions[i - 1].x;
            y = pastPositions[i - 1].y;
            pastPositions[i].setLocation(x, y);
        }
        pastPositions[0].setLocation(actualPosition.x, actualPosition.y);
        for (int i = 1; i < pastPositions.length; i++) {
            dx = Math.abs(pastPositions[i - 1].x - pastPositions[i].x) + maximumSpeed;
            dy = Math.abs(pastPositions[i - 1].y - pastPositions[i].y) + maximumSpeed;
            if (dx >= map.getWidth() || dy >= map.getHeight() || correction) {
                pastPositions[i].setLocation(
                        pastPositions[i - 1].x,
                        pastPositions[i - 1].y
                );
                correction = true;
            }
        }
    }

    private void boidPositionMapCorrection() {
        double newx, newy;
        newx = getActualPosition().getX();
        newy = getActualPosition().getY();
        Point2D.Double boid = new Point2D.Double(newx, newy);
        if (map.isInfinit()) {
            if (newx > map.getWidth()) {
                newx -= map.getWidth();
            } else if (newx < 0.0) {
                newx += map.getWidth();
            }
            if (newy > map.getHeight()) {
                newy -= map.getHeight();
            } else if (newy < 0.0) {
                newy += map.getHeight();
            }
        } else if (!map.getMapRect().contains(boid)) {
            double newfi = velocity.getFi();
            double newl = velocity.getLength();
            if (newx > map.getWidth()) {
                newx -= maximumSpeed;
                newfi = Math.PI - velocity.getFi();
            } else if (newx < 0.0) {
                newx += maximumSpeed;
                newfi = Math.PI - velocity.getFi();
            }
            if (newy > map.getHeight()) {
                newy -= maximumSpeed;
                newfi = 2.0 * Math.PI - velocity.getFi();
            } else if (newy < 0.0) {
                newy += maximumSpeed;
                newfi = 2.0 * Math.PI - velocity.getFi();
            }
            velocity.setPolarKoordinates(newfi, newl);
        }
        setActualPosition(new Point2D.Double(newx, newy));
    }

    private void boidPositionWallCorrection() {
        double newfi, newl, newx, newy, pitchAngle;
        Straight way, wall;
        BoidVelocity wallNormalVector = new BoidVelocity();
        BoidVelocity wayNormalVector = new BoidVelocity();
        for (int i = 0; i < map.getWalls().length; i++) {
            way = new Straight(pastPositions[0], actualPosition);
            wall = map.getWalls()[i];
            if (way.getIntersection(wall).length > 1) {
                wallNormalVector.setKoordinates(
                        wall.getNormalVector()[0],
                        wall.getNormalVector()[1]);
                wayNormalVector.setKoordinates(
                        way.getNormalVector()[0],
                        way.getNormalVector()[1]);
                pitchAngle = way.getPitchAngle(wall);
                if (wallNormalVector.getFi() > 0 && wallNormalVector.getFi() <= Math.PI / 2.0) {
                    newfi = 2.0 * Math.PI - velocity.getFi();
                } else if (wallNormalVector.getFi() > Math.PI / 2.0 && wallNormalVector.getFi() <= Math.PI) {
                    newfi = Math.PI - velocity.getFi();
                } else if (wallNormalVector.getFi() > Math.PI && wallNormalVector.getFi() <= Math.PI * 3.0 / 2.0) {
                    newfi = 2.0 * Math.PI - velocity.getFi();
                } else {
                    newfi = Math.PI - velocity.getFi();
                }

                newl = velocity.getLength();
                newx = pastPositions[0].x;
                newy = pastPositions[0].y;
                velocity.setPolarKoordinates(newfi, newl);
                actualPosition.setLocation(newx, newy);
            }
        }
    }

    private void boidPositionWallCorrection_old() {
        double newfi, newl, newx, newy, pitchAngle;
        Straight way, wall;
        BoidVelocity wallNormalVector = new BoidVelocity();
        BoidVelocity wayNormalVector = new BoidVelocity();
        for (int i = 0; i < map.getWalls().length; i++) {
            way = new Straight(pastPositions[0], actualPosition);
            wall = map.getWalls()[i];
            if (way.getIntersection(wall).length > 1) {
                wallNormalVector.setKoordinates(
                        wall.getNormalVector()[0],
                        wall.getNormalVector()[1]);
                wayNormalVector.setKoordinates(
                        way.getNormalVector()[0],
                        way.getNormalVector()[1]);
                pitchAngle = way.getPitchAngle(wall);
                if (wallNormalVector.getFi() > 0 && wallNormalVector.getFi() <= Math.PI / 2.0) {
                    if (wayNormalVector.getFi() > 0 && wayNormalVector.getFi() <= Math.PI / 2.0) {
                        newfi = wallNormalVector.getFi() + Math.PI;
                    } else if (wayNormalVector.getFi() > Math.PI / 2.0 && wayNormalVector.getFi() <= Math.PI) {
                        newfi = wallNormalVector.getFi();
                    } else if (wayNormalVector.getFi() > Math.PI && wayNormalVector.getFi() <= Math.PI * 3.0 / 2.0) {
                        newfi = wallNormalVector.getFi();
                    } else {
                        newfi = wallNormalVector.getFi() + Math.PI;
                    }
                } else if (wallNormalVector.getFi() > Math.PI / 2.0 && wallNormalVector.getFi() <= Math.PI) {
                    if (wayNormalVector.getFi() > 0 && wayNormalVector.getFi() <= Math.PI / 2.0) {
                        newfi = wallNormalVector.getFi() + Math.PI;
                    } else if (wayNormalVector.getFi() > Math.PI / 2.0 && wayNormalVector.getFi() <= Math.PI) {
                        newfi = wallNormalVector.getFi();
                    } else if (wayNormalVector.getFi() > Math.PI && wayNormalVector.getFi() <= Math.PI * 3.0 / 2.0) {
                        newfi = wallNormalVector.getFi();
                    } else {
                        newfi = wallNormalVector.getFi() + Math.PI;
                    }
                } else if (wallNormalVector.getFi() > Math.PI && wallNormalVector.getFi() <= Math.PI * 3.0 / 2.0) {
                    if (wayNormalVector.getFi() > 0 && wayNormalVector.getFi() <= Math.PI / 2.0) {
                        newfi = wallNormalVector.getFi();
                    } else if (wayNormalVector.getFi() > Math.PI / 2.0 && wayNormalVector.getFi() <= Math.PI) {
                        newfi = wallNormalVector.getFi() + Math.PI;
                    } else if (wayNormalVector.getFi() > Math.PI && wayNormalVector.getFi() <= Math.PI * 3.0 / 2.0) {
                        newfi = wallNormalVector.getFi() + Math.PI;
                    } else {
                        newfi = wallNormalVector.getFi();
                    }
                } else if (wayNormalVector.getFi() > 0 && wayNormalVector.getFi() <= Math.PI / 2.0) {
                    newfi = wallNormalVector.getFi();
                } else if (wayNormalVector.getFi() > Math.PI / 2.0 && wayNormalVector.getFi() <= Math.PI) {
                    newfi = wallNormalVector.getFi() + Math.PI;
                } else if (wayNormalVector.getFi() > Math.PI && wayNormalVector.getFi() <= Math.PI * 3.0 / 2.0) {
                    newfi = wallNormalVector.getFi() + Math.PI;
                } else {
                    newfi = wallNormalVector.getFi();
                }
                if (Double.compare(pastPositions[0].x, pastPositions[5].x) == 0
                        && Double.compare(pastPositions[0].y, pastPositions[5].y) == 0) {
                    newfi += Math.pow(-1, (int) (Math.random() * 2 + 1)) * (Math.PI / 2.0 + Math.random() * Math.PI / 2);
                }
                newl = velocity.getLength();
                newx = pastPositions[0].x;
                newy = pastPositions[0].y;
                velocity.setPolarKoordinates(newfi, newl);
                actualPosition.setLocation(newx, newy);
            }
        }
    }

    public void fly() {
        double x, y;
        x = actualPosition.getX() + this.velocity.getxValue();
        y = actualPosition.getY() + this.velocity.getyValue();
        this.actualPosition = this.newPosition;
        this.newPosition.setLocation(new Point2D.Double(x, y));
        boidPositionWallCorrection();
        boidPositionMapCorrection();
        setPastPositions();

    }
}
