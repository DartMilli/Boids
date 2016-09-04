package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import model.BoidModel;
import veiw.BoidSpace;

/**
 *
 * @author Milán
 */
public class BoidKeyListener implements KeyListener {

    private BoidModel model;
    private BoidSpace space;

    public BoidKeyListener(BoidModel model, BoidSpace space) {
        this.model = model;
        this.space = space;
    }

    private static int keyCharToMenuNumber(char key) {
        int out = 99;
        if (key == 'h') {
            out = 0;
        } else if (key == 'o') {
            out = 2;
        } else if (key == 'b' || key == 'B') {
            out = 4;
        } else if (key == 'f' || key == 'F' || key == ' ') {
            out = 5;
        } else if (key == 'i') {
            out = 6;
        } else if (key == 'p' || key == 'P') {
            out = 7;
        } else if (key == 'n' || key == 'N') {
            out = 8;
        } else if (key == 'm' || key == 'M') {
            out = 9;
        } else if (key == 'd' || key == 'D') {
            out = 10;
        } else if (key == 'v' || key == 'V') {
            out = 11;
        } else if (key == 'r') {
            out = 12;
        } else if (key == 's' || key == 'S') {
            out = 13;
        } else if (key == 'w' || key == 'W') {
            out = 14;
        } else if (key == 'e' || key == 'E') {
            out = 15;
        } else if (key == 'k') {
            out = 16;
        } else if (key == 'l') {
            out = 17;
        } else if (key == 'x') {
            out = 18;
        } else if (key == 'j') {
            out = 19;
        } else if (key == 'c') {
            out = 20;
        } else if (key == 'a') {
            out = 21;
        } else if (key == 'q' || key == 'Q') {
            out = 23;
        } else if (key == 'z' || key == 'Z') {
            out = 25;
        } else if (key == 'y') {
            out = 27;
        }
        return out;
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        char key = ke.getKeyChar();
        if (key == 'h') {
            space.changeStringMenuStyle();
        } else if (key == 'o') {
            int btype = space.getBoidType();
            if (btype >= 3) {
                space.setBoidType(0);
            } else {
                space.setBoidType(btype + 1);
            }
        } else if (key == 'r') {
            if (model.isRulesOn()) {
                model.setRules(false);
            } else {
                model.setRules(true);
            }
        } else if (key == ' ') {
            if (model.getTimeFrame() != 0) {
                model.setTimeFrame(0);
                space.setTimeFrame(1);
            } else {
                model.setTimeFrame(25);
                space.setTimeFrame(25);
            }
        } else if (key == 'i') {
            if (model.getMap().isInfinit()) {
                model.getMap().setInfinity(false);
            } else {
                model.getMap().setInfinity(true);
            }
        } else if (key == 'k') {
            if (space.isShowPrivateDistance()) {
                space.setShowPrivateDistance(false);
            } else {
                space.setShowPrivateDistance(true);
            }
        } else if (key == 'l') {
            if (space.isShowNeighbourDistance()) {
                space.setShowNeighbourhoodDistance(false);
            } else {
                space.setShowNeighbourhoodDistance(true);
            }
        } else if (key == 'x') {
            if (space.isShowIndexes()) {
                space.setShowIndexes(false);
            } else {
                space.setShowIndexes(true);
            }
        } else if (key == 'j') {
            if (space.isShowTrajectory()) {
                space.setShowTrajectory(false);
            } else {
                space.setShowTrajectory(true);
            }
        } else if (key == 'c') {
            if (model.isScarryMouse()) {
                model.setScarryMouse(false);
            } else {
                model.setScarryMouse(true);
            }
        } else if (key == 'a') {
            if (model.isAttractiveMouse()) {
                model.setAttractiveMouse(false);
            } else {
                model.setAttractiveMouse(true);
            }
        } else if (key == 'B') {
            model.removeBoid();
        } else if (key == 'b') {
            model.addBoid();
        } else if (key == 'F') {
            int frame = model.getTimeFrame();
            model.setTimeFrame(frame - 1);
        } else if (key == 'f') {
            int frame = model.getTimeFrame();
            model.setTimeFrame(frame + 1);
        } else if (key == 'P') {
            double pdist = model.getPrivateDistance();
            model.setPrivateDistance(pdist - 1);
        } else if (key == 'p') {
            double pdist = model.getPrivateDistance();
            model.setPrivateDistance(pdist + 1);
        } else if (key == 'N') {
            double ndist = model.getNeighbourhoodDistance();
            model.setNeighbourDistance(ndist - 1);
        } else if (key == 'n') {
            double ndist = model.getNeighbourhoodDistance();
            model.setNeighbourDistance(ndist + 1);
        } else if (key == 'M') {
            double factor = model.getMassRuleFactor();
            model.setRule1(factor - 0.05);
        } else if (key == 'm') {
            double factor = model.getMassRuleFactor();
            model.setRule1(factor + 0.05);
        } else if (key == 'D') {
            double factor = model.getDistanceRuleFactor();
            model.setRule2(factor - 0.05);
        } else if (key == 'd') {
            double factor = model.getDistanceRuleFactor();
            model.setRule2(factor + 0.05);
        } else if (key == 'V') {
            double factor = model.getVelocityRuleFactor();
            model.setRule3(factor - 0.05);
        } else if (key == 'v') {
            double factor = model.getVelocityRuleFactor();
            model.setRule3(factor + 0.05);
        } else if (key == 'S') {
            double speed = model.getMaximumSpeed();
            model.setBoidsSpeed(speed - 1.0, 1.0);
        } else if (key == 's') {
            double speed = model.getMaximumSpeed();
            model.setBoidsSpeed(speed + 1.0, 1.0);
        } else if (key == 'W') {
            double speed = model.getWindSpeed();
            double direction = model.getWindDirection();
            model.setWind(speed - 1.0, direction);
        } else if (key == 'w') {
            double speed = model.getWindSpeed();
            double direction = model.getWindDirection();
            model.setWind(speed + 1.0, direction);
        } else if (key == 'E') {
            double speed = model.getWindSpeed();
            double direction = model.getWindDirection();
            model.setWind(speed, direction - 5 * Math.PI / 180.0);
        } else if (key == 'e') {
            double speed = model.getWindSpeed();
            double direction = model.getWindDirection();
            model.setWind(speed, direction + 5 * Math.PI / 180.0);
        } else if (key == 'y') {
            space.nextWatched();
        } else if (key == 'Q') {
            int session = model.getMaximumObjectSession();
            model.setObjectSession(session - 1);
        } else if (key == 'q') {
            int session = model.getMaximumObjectSession();
            model.setObjectSession(session + 1);
        } else if (key == 'z') {
            if (space.isWallDrawingMode()) {
                space.setWallDrawingmode(false);
            } else {
                space.setWallDrawingmode(true);
            }
        } else if (key == 'Z') {
            model.getMap().removeWall();
        }
        space.setActiveMenu(keyCharToMenuNumber(key));
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if (ke.getKeyCode() == KeyEvent.VK_CONTROL) {
            Boids.setTemperaryWallOnlyHorizontalOrVertical();
            space.setActiveMenu(keyCharToMenuNumber('z'));
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
    }

}
