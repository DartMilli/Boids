package veiw;

import controller.BoidKeyListener;
import controller.BoidMouseListener;
import controller.BoidMouseMotionListener;
import controller.Boids;
import img.ImageLoader;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.Timer;
import model.Boid;
import model.BoidModel;
import model.BoidVelocity;

/**
 *
 * @author Milán
 */
public class BoidSpace extends JPanel {

    private static int BOIDSIZE = 6;
    private BoidModel model;
    private static int step = 0;
    private int stepBefore = 0;
    private int fps = 0;
    private boolean showPrivateDistance = false;
    private boolean showNeighbourhoodDistance = false;
    private boolean showIndexes = false;
    private boolean showTrajectory = false;
    private static int BOIDTYPE = 0;
    private StringMenu menu;
    private int activeMenuSession = 0;
    private int activeMenu;
    private boolean isWallDrawingMode = false;
    private boolean isTemperaryWallP1Seted = false;
    private boolean isTemperaryWallP2Seted = false;
    private Point2D.Double[] temperaryWall;
    private Point2D.Double markCircle;
    private boolean isSetMarkCircle = false;

    private Timer timer = new Timer(40, new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent ae) {
            repaint();
            if (activeMenuSession > 0) {
                activeMenuSession--;
            }
            step++;
        }
    });

    private Timer clock = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            fps = (step - stepBefore);
            stepBefore = step;
        }
    });

    public BoidSpace(BoidModel model) {
        this.model = model;
        this.setPreferredSize(model.getMap().getDimension());
        this.setFocusable(true);
        this.requestFocus();
        ImageLoader il = new ImageLoader();
        menu = new StringMenu(model, this);
        temperaryWall = new Point2D.Double[2];
        temperaryWall[0] = new Point2D.Double();
        temperaryWall[1] = new Point2D.Double();
        addMouseListener(new BoidMouseListener(model, this));
        addMouseMotionListener(new BoidMouseMotionListener(model, this));
        addKeyListener(new BoidKeyListener(model, this));
        timer.start();
        clock.start();
    }

    public void setShowPrivateDistance(boolean showPrivateDistance) {
        this.showPrivateDistance = showPrivateDistance;
    }

    public void setShowNeighbourhoodDistance(boolean showNeighbourhoodDistance) {
        this.showNeighbourhoodDistance = showNeighbourhoodDistance;
    }

    public void setShowIndexes(boolean showIndexes) {
        this.showIndexes = showIndexes;
    }

    public void setShowTrajectory(boolean showTrajectory) {
        this.showTrajectory = showTrajectory;
    }

    public static void setBoidType(int type) {
        BoidSpace.BOIDTYPE = type;
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

    public void setActiveMenu(int menuNumber) {
        if (menuNumber != 99) {
            activeMenuSession = 25;
            activeMenu = menuNumber;
        }
    }

    public void setWallDrawingmode(boolean isWallDrawing) {
        isWallDrawingMode = isWallDrawing;
        if (!isWallDrawingMode) {
            finalizeTemperaryWall();
        }
    }

    public void setTemperaryWallP1(Point2D.Double p1) {
        temperaryWall[0].setLocation(p1);
        isTemperaryWallP1Seted = true;
    }

    public void setTemperaryWallP2(Point2D.Double p1) {
        temperaryWall[1].setLocation(p1);
        isTemperaryWallP2Seted = true;
    }

    public void finalizeTemperaryWall() {
        isTemperaryWallP1Seted = false;
        isTemperaryWallP2Seted = false;
    }

    public void setMarkCircle(Point2D.Double center) {
        markCircle = center;
        isSetMarkCircle = true;
    }

    public void unSetMarkCircle() {
        isSetMarkCircle = false;
    }

    public int getBoidType() {
        return BOIDTYPE;
    }

    public int getFps() {
        return fps;
    }

    public Point2D.Double getTemperaryWallP1() {
        return temperaryWall[0];
    }

    public boolean isShowPrivateDistance() {
        return showPrivateDistance;
    }

    public boolean isShowNeighbourDistance() {
        return showNeighbourhoodDistance;
    }

    public boolean isShowIndexes() {
        return showIndexes;
    }

    public boolean isShowTrajectory() {
        return showTrajectory;
    }

    public boolean isWallDrawingMode() {
        return isWallDrawingMode;
    }

    public boolean isTemperaryWallP1Set() {
        return isTemperaryWallP1Seted;
    }

    public void nextWatched() {
        menu.nextWatched();
    }

    private void drawPrivateDist(Graphics g, Boid b) {
        if (showPrivateDistance) {
            double pDist = model.getPrivateDistance();
            g.setColor(new Color(125, 0, 0));
            g.drawOval((int) (b.getActualPosition().getX() - pDist),
                    (int) (b.getActualPosition().getY() - pDist),
                    (int) (2 * pDist),
                    (int) (2 * pDist)
            );
        }
    }

    private void drawNeighbourhoodDist(Graphics g, Boid b) {
        if (showNeighbourhoodDistance) {
            double nDist = model.getNeighbourhoodDistance();
            g.setColor(new Color(0, 255, 0, 5));
            g.fillOval((int) (b.getActualPosition().getX() - nDist),
                    (int) (b.getActualPosition().getY() - nDist),
                    (int) (2 * nDist),
                    (int) (2 * nDist)
            );
        }
    }

    private void drawIndexes(Graphics g, Boid b) {
        if (showIndexes) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Courier New", Font.PLAIN, 12));
            g.drawString(b.getBoidIndex() + "",
                    (int) b.getActualPosition().getX() - BOIDSIZE,
                    (int) b.getActualPosition().getY() - BOIDSIZE
            );
        }
    }

    public static void drawBoidToMap(Graphics g, Boid b, Point2D.Double position, Color c) {
        if (BOIDTYPE == 0) {
            g.setColor(c);
            g.fillOval((int) position.getX() - BOIDSIZE,
                    (int) position.getY() - BOIDSIZE,
                    2 * BOIDSIZE,
                    2 * BOIDSIZE
            );
        }
        drawBoid(g, b.getVelocity(), 4, position, c, BOIDTYPE);
    }

    private void drawTrajecotry(Graphics g, Boid b, Color c) {
        if (showTrajectory) {
            int red = c.getRed();
            int green = c.getGreen();
            int blue = c.getBlue();
            double x1, x2, y1, y2, length, factor;
            length = b.getPastPosition().length;
            for (int i = 1; i < length; i++) {
                factor = (length - i) / length;
                g.setColor(new Color(red, green, blue, (int) (factor * 255)));
                x1 = b.getPastPosition()[i - 1].x;
                x2 = b.getPastPosition()[i].x;
                y1 = b.getPastPosition()[i - 1].y;
                y2 = b.getPastPosition()[i].y;
                g.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
            }
        }
    }

    private void drawMenu(Graphics g) {
        int noteHeigth = 12;
        g.setFont(new Font("Courier New", Font.PLAIN, noteHeigth));
        for (int i = 0; i < menu.getStringMenuContent().length; i++) {
            if (i == activeMenu && activeMenuSession > 0) {
                g.setColor(menu.getActiveMenuColor());
            } else {
                g.setColor(menu.getMenuColor());
            }
            g.drawString(menu.getStringMenuContent()[i], 5, 15 + i * noteHeigth);
        }
    }

    public void changeStringMenuStyle() {
        menu.changeStringMenuStyle();
    }

    public static void drawBoid(
            Graphics g1,
            BoidVelocity v,
            double size,
            Point2D.Double position,
            Color c,
            int type
    ) {
        Graphics2D g = (Graphics2D) g1.create();
        g.setColor(c);

        double x1 = (int) position.getX();
        double y1 = (int) position.getY();
        double x2 = (int) (position.getX() + v.getxValue());
        double y2 = (int) (position.getY() + v.getyValue());

        double dx = x2 - x1;
        double dy = y2 - y1;
        double angle = Math.atan2(dy, dx);
        double realLength = Math.sqrt(dx * dx + dy * dy);

        int arrowSize = size < 1 ? 4 : (int) size;
        if (size < 1) {
            realLength *= size;
        }
        int length = (int) realLength;

        AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
        at.concatenate(AffineTransform.getRotateInstance(angle));
        g.transform(at);

        Polygon polygon;
        if (type == 0) {
            g.fillRect(0, -arrowSize / 4, length, arrowSize / 2);
            polygon = new Polygon(
                    new int[]{length + 3 * arrowSize, length, length},
                    new int[]{0, -arrowSize, arrowSize},
                    3
            );
            g.fillPolygon(polygon);
        } else if (type == 1) {
            g.fillOval(-3 * arrowSize, -2 * arrowSize, 6 * arrowSize, 4 * arrowSize);
            polygon = new Polygon(
                    new int[]{0, -4 * arrowSize, -6 * arrowSize, -6 * arrowSize, -4 * arrowSize, 0},
                    new int[]{-2 * arrowSize, -arrowSize / 2, -2 * arrowSize, 2 * arrowSize, arrowSize / 2, 2},
                    6
            );
            g.fillPolygon(polygon);
        } else if (type == 2) {
            int width = ImageLoader.getImage()[0].length;
            int height = ImageLoader.getImage().length;
            BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (ImageLoader.getImage()[i][j]) {
                        img.setRGB(j, i, c.getRGB());
                    }
                }
            }
            g.drawImage(img, -width / 2, -height / 2, null);
        } else if (type == 3) {
            int picNum = (step) % ImageLoader.getBoidPictures().length;
            Image img = ImageLoader.getBoidPictures()[picNum].getImage();
            g.drawImage(img, -img.getWidth(null) / 4 * 3, -img.getHeight(null) / 2, null);
        }
    }

    private void drawTemperaryWall(Graphics g) {
        g.setColor(Color.ORANGE);
        if (isWallDrawingMode) {
            if (isSetMarkCircle) {
                g.drawOval(
                        (int) (markCircle.x - Boids.WALLDRAWINGDISTANCE / 2.0),
                        (int) (markCircle.y - Boids.WALLDRAWINGDISTANCE / 2.0),
                        (int) Boids.WALLDRAWINGDISTANCE,
                        (int) Boids.WALLDRAWINGDISTANCE);
            }
            if (isTemperaryWallP1Seted && isTemperaryWallP2Seted) {
                g.drawLine(
                        (int) temperaryWall[0].x,
                        (int) temperaryWall[0].y,
                        (int) temperaryWall[1].x,
                        (int) temperaryWall[1].y);
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        drawMenu(g);
        drawTemperaryWall(g);
        g.setColor(Color.yellow);
        for (int i = 0; i < model.getMap().getWalls().length; i++) {
            g.drawLine((int) model.getMap().getWalls()[i].getP1().x,
                    (int) model.getMap().getWalls()[i].getP1().y,
                    (int) model.getMap().getWalls()[i].getP2().x,
                    (int) model.getMap().getWalls()[i].getP2().y);
        }
        Boid b;
        for (int i = 0; i < model.getpopulation(); i++) {
            b = model.getBoid(i);
            drawIndexes(g, b);
            drawNeighbourhoodDist(g, b);
            drawPrivateDist(g, b);
            drawBoidToMap(g, b, b.getActualPosition(), b.getColor());
            drawTrajecotry(g, b, b.getColor());
        }

        if (model.isObjectScarry()) {
            g.setColor(Color.RED);
        } else {
            g.setColor(Color.GREEN);
        }

        if (model.getObjectPosition() != null) {
            g.fillOval((int) model.getObjectPosition().x, (int) model.getObjectPosition().y, 2, 2);
            Color c = g.getColor();
            int red = c.getRed();
            int green = c.getGreen();
            int blue = c.getBlue();
            int alpha = 255 / model.getMaximumObjectSession() * model.getActualObjectSession();
            c = new Color(red, green, blue, alpha);
            g.setColor(c);
            g.drawOval(
                    (int) model.getObjectPosition().x - model.getActualObjectSession() / 2,
                    (int) model.getObjectPosition().y - model.getActualObjectSession() / 2,
                    (int) model.getActualObjectSession(),
                    (int) model.getActualObjectSession()
            );
        }
    }
}
