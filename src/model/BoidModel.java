package model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import javax.swing.Timer;

/**
 *
 * @author Milán
 */
public class BoidModel {

    private ArrayList<Boid> boids;
    private Map map;
    private double neighbourDistance = 100.0;
    private double privateDistance = 50.0;
    private double rule_1_factor = 1.0;
    private double rule_2_factor = 1.0;
    private double rule_3_factor = 1.0;
    private double windSpeed = 0.0;
    private double windDirection = -Math.PI;
    private int population = 0;
    private double populationMass = 0;
    private int step = 0;
    private int objectSession = 0;
    private int maximumObjectSession = 50;
    private Point2D.Double objectPosition = null;
    private Point2D.Double mousePosition = null;
    private boolean scarryObject = false;
    private boolean attractiveMouse = false;
    private boolean scarryMouse = false;
    private boolean isRulesOn = true;

    private Timer timer = new Timer(40, new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent ae) {
            step();
        }
    });

    public BoidModel(Map map) {
        this.map = map;
        boids = new ArrayList<>();
        timer.start();
    }

    public int getpopulation() {
        return this.population;
    }

    public double getPopulationMass() {
        return this.populationMass;
    }

    public int getStep() {
        return this.step;
    }

    public Map getMap() {
        return this.map;
    }

    public Boid getBoid(int index) {
        Boid ki = null;
        if (index >= 0 && index < boids.size()) {
            ki = boids.get(index);
        }
        return ki;
    }

    public double getPrivateDistance() {
        return this.privateDistance;
    }

    public double getNeighbourhoodDistance() {
        return this.neighbourDistance;
    }

    public double getWindSpeed() {
        return this.windSpeed;
    }

    public double getWindDirection() {
        return this.windDirection;
    }

    public double getWindDirectionInDeg() {
        double deg;
        if (windDirection >= 0) {
            deg = windDirection / Math.PI * 180;
        } else {
            deg = 360 + windDirection / Math.PI * 180;
        }
        return deg;
    }

    public Point2D.Double getObjectPosition() {
        return objectPosition;
    }

    public boolean isObjectScarry() {
        return scarryObject;
    }

    public int getActualObjectSession() {
        return this.objectSession;
    }

    public int getMaximumObjectSession() {
        return this.maximumObjectSession;
    }

    public int getTimeFrame() {
        int frame;
        if (timer.isRunning()) {
            frame = 1000 / timer.getDelay();
        } else {
            frame = 0;
        }
        return frame;
    }

    public double getMassRuleFactor() {
        return rule_1_factor;
    }

    public double getDistanceRuleFactor() {
        return rule_2_factor;
    }

    public double getVelocityRuleFactor() {
        return rule_3_factor;
    }

    public boolean isRulesOn() {
        return isRulesOn;
    }

    public double getMaximumSpeed() {
        double max = 0, temp;
        for (int i = 0; i < boids.size(); i++) {
            temp = boids.get(i).getMaximumSpeed();
            if (temp > max) {
                max = temp;
            }
        }
        return max;
    }

    public boolean isScarryMouse() {
        return scarryMouse;
    }

    public boolean isAttractiveMouse() {
        return attractiveMouse;
    }

    public void addBoid(Boid b) {
        boids.add(b);
        int index = boids.indexOf(b);
        b.setBoidIndex(index);
        population++;
        populationMass += b.getMass();
    }

    public void addBoid() {
        Boid b = new Boid(map, new Point2D.Double(
                Math.random() * map.getWidth(),
                Math.random() * map.getHeight()
        ));
        addBoid(b);
    }

    public void removeBoid() {
        int lastIndex = boids.size() - 1;
        if (lastIndex > 0) {
            population--;
            populationMass -= boids.get(lastIndex).getMass();
            boids.remove(lastIndex);
        }
    }

    public void setBoidsSpeed(double max, double min) {
        for (int i = 0; i < boids.size(); i++) {
            boids.get(i).setSpeedLimitation(max, min);
        }
    }

    public void setNeighbourDistance(double distance) {
        neighbourDistance = distance > 0.0 ? distance : neighbourDistance;
    }

    public void setPrivateDistance(double distance) {
        privateDistance = distance > 0.0 ? distance : privateDistance;
    }

    public void setInfinity(boolean infinity) {
        map.setInfinity(infinity);
    }

    public void setObjectSession(int sessionFps) {
        if (sessionFps > 0) {
            this.maximumObjectSession = sessionFps;
        }
    }

    public void setWind(double speed, double direction) {
        windSpeed = speed >= 0.0 ? speed : windSpeed;
        if (direction > 2 * Math.PI) {
            windDirection = direction - 2 * Math.PI;
        } else if (direction < 0) {
            windDirection = direction + 2 * Math.PI;
        } else {
            windDirection = direction;
        }
    }

    public void setRule1(double r1f) {
        if (r1f > 1.0) {
            rule_1_factor = 1.0;
        } else if (r1f < 0.0) {
            rule_1_factor = 0.0;
        } else {
            rule_1_factor = r1f;
        }
    }

    public void setRule2(double r2f) {
        if (r2f > 1.0) {
            rule_2_factor = 1.0;
        } else if (r2f < 0.0) {
            rule_2_factor = 0.0;
        } else {
            rule_2_factor = r2f;
        }
    }

    public void setRule3(double r3f) {
        if (r3f > 1.0) {
            rule_3_factor = 1.0;
        } else if (r3f < 0.0) {
            rule_3_factor = 0.0;
        } else {
            rule_3_factor = r3f;
        }
    }

    public void setRule5(Point2D.Double point, boolean scarry) {
        this.objectPosition = point;
        this.objectSession = maximumObjectSession;
        this.scarryObject = scarry;
    }

    public void setRule6(Point2D.Double point, boolean be) {
        if (be) {
            mousePosition = point;
        } else {
            mousePosition = null;
            scarryMouse = false;
            attractiveMouse = false;
        }
    }

    public void setRules(boolean on) {
        if (!on) {
            setRule1(0.0);
            setRule2(0.0);
            setRule3(0.0);
            isRulesOn = false;
        } else {
            setRule1(1.0);
            setRule2(1.0);
            setRule3(1.0);
            isRulesOn = true;
        }
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

    public void setScarryMouse(boolean on) {
        if (on) {
            attractiveMouse = false;
            scarryMouse = true;
        } else {
            scarryMouse = false;
        }
    }

    public void setAttractiveMouse(boolean on) {
        if (on) {
            scarryMouse = false;
            attractiveMouse = true;
        } else {
            attractiveMouse = false;
        }
    }

    public void step() {
        setBoidsNewPosition();
        step++;
        objectSession--;
        if (objectSession < 1) {
            objectPosition = null;
            objectSession = 0;
        }
    }

    private void setBoidsNewPosition() {
        Boid b;
        BoidVelocity[] newVelocity = new BoidVelocity[6];
        for (int i = 0; i < boids.size(); i++) {
            b = boids.get(i);
            int[] neighbourhood = getBoidNeighborhood(b);
            newVelocity[0] = rule1_mass(b, neighbourhood);
            newVelocity[1] = rule2_distance(b, neighbourhood);
            newVelocity[2] = rule3_velocities(b, neighbourhood);
            newVelocity[3] = rule4_wind(windSpeed, windDirection);
            newVelocity[4] = rule5_object(b);
            newVelocity[5] = rule6_mouse(b);
            b.setVelocity(newVelocity);
            b.fly();
        }
    }

    public int[] getBoidNeighborhood(Boid b) {
        double distance, xn, yn;
        int db = 0;
        double xb = b.getActualPosition().getX();
        double yb = b.getActualPosition().getY();
        Boid neibourBoid;
        Straight neighbourDistanceStraight;
        Straight wall;
        boolean isBoidBeyondTheWall;
        int[] possibleBoidIndexes = new int[boids.size()];
        for (int i = 0; i < boids.size(); i++) {
            neibourBoid = boids.get(i);
            xn = neibourBoid.getActualPosition().getX();
            yn = neibourBoid.getActualPosition().getY();
            neighbourDistanceStraight = new Straight(xb, yb, xn, yn);
            distance = neighbourDistanceStraight.getLength();
            if (distance < neighbourDistance && b != neibourBoid) {
                isBoidBeyondTheWall = false;
                for (int j = 0; j < map.getWalls().length; j++) {
                    wall = map.getWalls()[j];
                    if (neighbourDistanceStraight.getIntersection(wall).length > 1) {
                        isBoidBeyondTheWall = true;
                    }
                }
                if (!isBoidBeyondTheWall) {
                    possibleBoidIndexes[db] = i;
                    db++;
                }
            }
        }
        int[] out = new int[db];
        System.arraycopy(possibleBoidIndexes, 0, out, 0, out.length);
        return out;
    }

    private BoidVelocity rule1_mass(Boid b, int[] neighbours) {
        double x = b.getActualPosition().getX();
        double y = b.getActualPosition().getY();
        double mass, sMass, factor;
        BoidVelocity out = new BoidVelocity();
        Boid neighbour;
        mass = b.getMass();
        sMass = getPopulationMass();
        factor = mass / sMass;
        for (int i = 0; i < neighbours.length; i++) {
            neighbour = boids.get(neighbours[i]);
            x = (factor * x + (1 - factor) * neighbour.getActualPosition().getX());
            y = (factor * y + (1 - factor) * neighbour.getActualPosition().getY());
        }
        double vx, vy;
        vx = x - b.getActualPosition().getX();
        vy = y - b.getActualPosition().getY();
        out.setKoordinates(vx, vy);
        double fi, length;
        fi = out.getFi();
        length = out.getLength() * rule_1_factor;
        out.setPolarKoordinates(fi, length);
        return out;
    }

    private BoidVelocity rule2_distance(Boid b, int[] neighbours) {
        Boid neighbour;
        double distance, vx = 0, vy = 0, pieces = 0;

        for (int i = 0; i < neighbours.length; i++) {
            neighbour = boids.get(neighbours[i]);
            BoidVelocity neiboursDirection = new BoidVelocity();
            neiboursDirection.setKoordinates(
                    neighbour.getActualPosition().getX() - b.getActualPosition().getX(),
                    neighbour.getActualPosition().getY() - b.getActualPosition().getY()
            );
            distance = neiboursDirection.getLength();
            if (distance < privateDistance) {
                double fi = neiboursDirection.getFi() + Math.PI;
                double length = privateDistance - neiboursDirection.getLength();
                neiboursDirection.setPolarKoordinates(fi, length);
                vx += neiboursDirection.getxValue();
                vy += neiboursDirection.getyValue();
                pieces++;
            }
        }
        BoidVelocity out = new BoidVelocity();
        if (pieces != 0.0) {
            vx = vx / pieces * b.getVelocity().getLength();
            vy = vy / pieces * b.getVelocity().getLength();
            out.setKoordinates(vx, vy);
        }
        double fi, length;
        fi = out.getFi();
        length = out.getLength() * rule_2_factor;
        out.setPolarKoordinates(fi, length);
        return out;
    }

    private BoidVelocity rule3_velocities(Boid b, int[] neighbours) {
        BoidVelocity out = new BoidVelocity();
        Boid neighbour;
//        double vx = b.getVelocity().getxValue();
//        double vy = b.getVelocity().getyValue();
        double vx = 0, vy = 0, pieces = 0, fi, length;
        for (int i = 0; i < neighbours.length; i++) {
            neighbour = boids.get(neighbours[i]);
            vx += neighbour.getVelocity().getxValue();
            vy += neighbour.getVelocity().getyValue();
            if (neighbour.getVelocity().getLength() != 0) {
                pieces++;
            }
        }
        if (pieces != 0.0) {
            vx = vx / pieces * b.getVelocity().getLength();
            vy = vy / pieces * b.getVelocity().getLength();
        }
        out.setKoordinates(vx, vy);
        fi = out.getFi();
        length = out.getLength() * rule_3_factor;
        out.setPolarKoordinates(fi, length);
        return out;
    }

    private BoidVelocity rule4_wind(double speed, double direction) {
        BoidVelocity out = new BoidVelocity();
        out.setPolarKoordinates(direction, speed);
        return out;
    }

    private BoidVelocity rule5_object(Boid b) {
        BoidVelocity out = new BoidVelocity();
        double fi, length;
        if (objectSession > 0 && objectPosition != null) {
            out.setKoordinates(
                    objectPosition.x - b.getActualPosition().x,
                    objectPosition.y - b.getActualPosition().y
            );
            if (scarryObject) {
                fi = out.getFi() + Math.PI;
            } else {
                fi = out.getFi();
            }
            length = b.getTheoreticalSpeed().getLength();
            out.setPolarKoordinates(fi, length);
        }
        return out;
    }

    private BoidVelocity rule6_mouse(Boid b) {
        BoidVelocity out = new BoidVelocity();
        double fi, length;
        if (mousePosition != null) {
            out.setKoordinates(
                    mousePosition.x - b.getActualPosition().x,
                    mousePosition.y - b.getActualPosition().y
            );
            length = b.getTheoreticalSpeed().getLength();
            if (attractiveMouse) {
                fi = out.getFi();
            } else if (scarryMouse) {
                fi = out.getFi() + Math.PI;
            } else {
                fi = 0;
                length = 0;
            }
            out.setPolarKoordinates(fi, length);
        }
        return out;
    }

}
