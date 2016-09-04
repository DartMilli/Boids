package veiw;

import java.awt.Color;
import java.util.Random;
import model.Boid;
import model.BoidModel;

/**
 *
 * @author Milán
 */
public class StringMenu {

    private BoidModel model;
    private BoidSpace veiw;
    private Boid watched;
    private String[] prefix;
    private String[] postfix;
    private String[] controls;
    private int piecesNeighbours;
    private int style = 0;
    private String styleName;
    private Color menuColor;
    private Color activeMenuColor;

    private static final String LINES = "-----------------------------";

    public StringMenu(BoidModel model, BoidSpace veiw) {
        this.model = model;
        this.veiw = veiw;
        watched = model.getBoid(0);
        menuColor = new Color(50, 50, 50);
        activeMenuColor = new Color(100, 100, 100);
        styleName = "LGray";
    }

    private void generatePrefix() {
        String boid;
        switch (veiw.getBoidType()) {
            case 0:
                boid = "Boid";
                break;
            case 1:
                boid = "Fish";
                break;
            case 2:
                boid = "Bat";
                break;
            case 3:
                boid = "Shark";
                break;
            default:
                boid = "N/A";
                break;
        }
        String[] num = new String[16];
        num[0] = round(watched.getVelocity().getFiInDeg());
        num[1] = round(watched.getVelocity().getLength());
        num[2] = round(watched.getTheoreticalSpeed().getFiInDeg());
        num[3] = round(watched.getTheoreticalSpeed().getLength());
        num[4] = round(watched.getVelocityComponents()[0].getFiInDeg());
        num[5] = round(watched.getVelocityComponents()[0].getLength());
        num[6] = round(watched.getVelocityComponents()[1].getFiInDeg());
        num[7] = round(watched.getVelocityComponents()[1].getLength());
        num[8] = round(watched.getVelocityComponents()[2].getFiInDeg());
        num[9] = round(watched.getVelocityComponents()[2].getLength());
        num[10] = round(watched.getVelocityComponents()[3].getFiInDeg());
        num[11] = round(watched.getVelocityComponents()[3].getLength());
        num[12] = round(watched.getVelocityComponents()[4].getFiInDeg());
        num[13] = round(watched.getVelocityComponents()[4].getLength());
        num[14] = round(watched.getVelocityComponents()[5].getFiInDeg());
        num[15] = round(watched.getVelocityComponents()[5].getLength());
        prefix = new String[]{
            "Menu:                        " + styleName,
            LINES,
            "Boid veiw:                   " + boid,
            "Aktual frames per sec:       " + veiw.getFps(),
            "Number of Boids:             " + model.getpopulation(),
            "Set frames per sec:          " + model.getTimeFrame(),
            "Map infinity:                " + model.getMap().isInfinit(),
            "Private distance:            " + (int) model.getPrivateDistance(),
            "Neighbourhood distance:      " + (int) model.getNeighbourhoodDistance(),
            "Mass rule:                   " + (int) (model.getMassRuleFactor() * 100),
            "Distance rule:               " + (int) (model.getDistanceRuleFactor() * 100),
            "Velocity rule:               " + (int) (model.getVelocityRuleFactor() * 100),
            "Rules:                       " + model.isRulesOn(),
            "Maximum speed:               " + (int) model.getMaximumSpeed(),
            "Wind speed:                  " + (int) model.getWindSpeed(),
            "Wind direction:              " + (int) model.getWindDirectionInDeg(),
            "Draw private distance:       " + veiw.isShowPrivateDistance(),
            "Draw neighbourhood distance: " + veiw.isShowNeighbourDistance(),
            "Draw indexes:                " + veiw.isShowIndexes(),
            "Draw trajectory:             " + veiw.isShowTrajectory(),
            "Scarry mouse:                " + model.isScarryMouse(),
            "Attractive mouse:            " + model.isAttractiveMouse(),
            "Oject is scarry:             " + model.isObjectScarry(),
            "Object session:              " + model.getMaximumObjectSession(),
            "Aktual object session:       " + model.getActualObjectSession(),
            "Wall drawing mode:           " + veiw.isWallDrawingMode(),
            "",
            "Watched boid:                " + watched.getBoidIndex(),
            LINES,
            "Number of neighbours:        " + piecesNeighbours,
            "Neighbours:                  " + neighbours(watched),
            LINES,
            "Aktual velocity:         Fi: " + num[0],
            "                     Length: " + num[1],
            LINES,
            "Theoretical speed:       Fi: " + num[2],
            "                     Length: " + num[3],
            LINES,
            "Velocity components:         ",
            "Mass                     Fi: " + num[4],
            "                     Length: " + num[5],
            "Distance                 Fi: " + num[6],
            "                     Length: " + num[7],
            "Velocities               Fi: " + num[8],
            "                     Length: " + num[9],
            "Wind                     Fi: " + num[10],
            "                     Length: " + num[11],
            "Object                   Fi: " + num[12],
            "                     Length: " + num[13],
            "Mouse                    Fi: " + num[14],
            "                     Length: " + num[15]

        };
    }

    private void generatePostfix() {
        postfix = new String[]{
            "-", "", "-", "fps", "pcs", "fps", "-", "px", "px", "%", "%", "%",
            "-", "px", "px", "°", "-", "-", "-", "-", "-", "-", "-", "fps", "fps",
            "-", "", "No.", "", "pcs", "", "", "°", "px", "", "°", "px", "", "",
            "°", "px", "°", "px", "°", "px", "°", "px", "°", "px", "°", "px", ""
        };
    }

    private void generateControls() {
        controls = new String[]{
            "h", "", "o", "", "add: b, remove: shift + b",
            "add: f, remove: shift + f, stop/start: Space",
            "i", "add: p, remove: shift + p", "add: n, remove: shift + n",
            "add: m, remove: shift + m", "add: d, remove: shift + d",
            "add: v, remove: shift + v", "r", "add: s, remove: shift + s",
            "add: w, remove: shift + w", "add: e, remove: shift + e", "k", "l",
            "x", "j", "c", "a", "", "add: q, remove: shift + q", "", 
            "set: z, remove: shift + z, vertical/horizontal wall: ctr", "",
            "y", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
            "", "", "", "", "", "", "", ""
        };
    }

    public void nextWatched() {
        int index = watched.getBoidIndex();
        int population = model.getpopulation();
        if (population > index + 1) {
            index += 1;
        } else {
            index = 0;
        }
        watched = model.getBoid(index);
    }

    public Boid getWached() {
        return watched;
    }

    private static String round(double number) {
        double outNumber = Math.round(number * 2.0) / 2.0;
        return Double.toString(outNumber);
    }

    private String neighbours(Boid b) {
        StringBuilder output = new StringBuilder();
        int[] neighbours = model.getBoidNeighborhood(b);
        piecesNeighbours = 0;
        if (neighbours.length != 0) {
            for (int i = 0; i < neighbours.length; i++) {
                output.append(Integer.toString(neighbours[i]));
                if (i != neighbours.length - 1) {
                    output.append(",");
                }
                piecesNeighbours++;
            }
        } else {
            output.append("N/A");
        }
        return output.toString();
    }

    private int maximumLength(String[] in) {
        int temp, max = 0;
        for (int i = 0; i < in.length; i++) {
            if (in[i].startsWith("Neighbours:")) {
                temp = 0;
            } else {
                temp = in[i].length();
            }
            if (temp > max) {
                max = temp;
            }
        }
        return max;
    }

    public Color getMenuColor() {
        return this.menuColor;
    }

    public Color getActiveMenuColor() {
        return activeMenuColor;
    }

    public void changeStringMenuStyle() {
        int max = 8;
        if (style < max) {
            style++;
        } else {
            style = 0;
        }
        switch (style) {
            case 1:
                menuColor = new Color(100, 100, 100);
                activeMenuColor = new Color(200, 200, 200);
                styleName = "Gray";
                break;
            case 2:
                menuColor = new Color(0, 50, 0);
                activeMenuColor = new Color(0, 100, 0);
                styleName = "LGreen";
                break;
            case 3:
                menuColor = new Color(0, 100, 0);
                activeMenuColor = new Color(0, 200, 0);
                styleName = "Green";
                break;
            case 4:
                menuColor = new Color(150, 150, 0);
                activeMenuColor = new Color(255, 255, 0);
                styleName = "CRT";
                break;

            case 5:
                menuColor = new Color(0, 100, 200);
                activeMenuColor = new Color(200, 100, 0);
                styleName = "Extra";
                break;
            case 6:
                Random rand = new Random();
                int[] rgb = new int[6];
                for (int i = 0; i < rgb.length; i++) {
                    rgb[i] = rand.nextInt(255) + 1;
                }
                menuColor = new Color(rgb[0], rgb[1], rgb[2]);
                activeMenuColor = new Color(rgb[3], rgb[4], rgb[5]);
                styleName = "Rand";
                break;
            case 7:
                menuColor = new Color(10, 10, 10);
                activeMenuColor = new Color(100, 100, 100);
                styleName = "Hidden";
                break;
            case 8:
                menuColor = new Color(0, 0, 0);
                activeMenuColor = new Color(100, 100, 100);
                styleName = "None";
                break;
            case 0:
            default:
                menuColor = new Color(50, 50, 50);
                activeMenuColor = new Color(100, 100, 100);
                styleName = "LGray";
                break;
        }
    }

    public String[] getStringMenuContent() {
        generatePrefix();
        generatePostfix();
        generateControls();
        String[] output = new String[prefix.length];
        int maximumPrefixLength = maximumLength(prefix);
        int maximumPostfixLength = maximumLength(postfix);
        int spaces1, spaces2;
        StringBuilder s1, s2;
        for (int i = 0; i < prefix.length; i++) {
            spaces1 = maximumPrefixLength - prefix[i].length();
            spaces2 = maximumPostfixLength - postfix[i].length();
            s1 = new StringBuilder(" ");
            s2 = new StringBuilder(" ");
            for (int j = 0; j < spaces1; j++) {
                s1.append(" ");
            }
            for (int j = 0; j < spaces2; j++) {
                s2.append(" ");
            }
            if (postfix[i] == "") {
                output[i] = prefix[i];
            } else {
                output[i] = prefix[i] + s1 + "[" + postfix[i] + "]" + s2 + controls[i];
            }
        }
        return output;
    }
}
