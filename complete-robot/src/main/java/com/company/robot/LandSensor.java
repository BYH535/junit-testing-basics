package robot;

import java.util.*;

public class LandSensor {

    protected Map<Coordinates, Land> carte;
    private Coordinates boxTop;
    private Coordinates boxBottom;

    private Random random;
    private final int PORTEE = 4;

    public LandSensor(Random random) {
        carte = new HashMap<Coordinates, Land>();
        this.random = random;
    }

    public double getPointToPointEnergyCoefficient(Coordinates coordinate1, Coordinates coordinate2) throws LandSensorDefaillance, InaccessibleCoordinate {
        Land terrain1 = lazyGet(coordinate1);
        Land terrain2 = lazyGet(coordinate2);
        return (terrain1.coefficient + terrain2.coefficient) / 2.0;
    }

    private Land lazyGet(Coordinates coordinate1) throws LandSensorDefaillance {
        if (carte.get(coordinate1) == null)
            try {
                Land land;
                if (random.nextInt() % 10 == 0) land = Land.Infranchissable;
                else land = Land.getLandByOrdinal(random.nextInt(Land.countLand() - 1));
                carte.put(coordinate1, land);
            } catch (TerrainNonRepertorieException e) {
                throw new LandSensorDefaillance();
            }
        return carte.get(coordinate1);
    }

    public boolean isAccessible(Coordinates coordinates) {
        if (carte.get(coordinates) != null)
            return carte.get(coordinates) != Land.Infranchissable;
        return false;
    }

    public void cartographier(Coordinates landPosition) throws LandSensorDefaillance {
        if (boxTop == null) boxTop = new Coordinates(landPosition.getX() - PORTEE, landPosition.getY() - PORTEE);
        else if (boxTop.getX() > landPosition.getX() - PORTEE && boxTop.getY() > landPosition.getY() - PORTEE)
            boxTop = new Coordinates(landPosition.getX() - PORTEE, landPosition.getY() - PORTEE);
        else if (boxTop.getX() > landPosition.getX() - PORTEE)
            boxTop = new Coordinates(landPosition.getX() - PORTEE, boxTop.getY());
        else if (boxTop.getY() > landPosition.getY() - PORTEE)
            boxTop = new Coordinates(boxTop.getX(), landPosition.getY() - PORTEE);
        if (boxBottom == null) boxBottom = new Coordinates(landPosition.getX() + PORTEE, landPosition.getY() + PORTEE);
        else if (boxBottom.getX() < landPosition.getX() + PORTEE && boxBottom.getY() < landPosition.getY() + PORTEE)
            boxBottom = new Coordinates(landPosition.getX() + PORTEE, landPosition.getY() - PORTEE);
        else if (boxBottom.getX() < landPosition.getX() + PORTEE)
            boxBottom = new Coordinates(landPosition.getX() + PORTEE, boxBottom.getY());
        else if (boxBottom.getY() < landPosition.getY() + PORTEE)
            boxBottom = new Coordinates(boxBottom.getX(), landPosition.getY() + PORTEE);
        for (int i = landPosition.getX() - PORTEE; i < landPosition.getX() + PORTEE + 1; i++) {
            for (int j = landPosition.getY() - PORTEE; j < landPosition.getY() + PORTEE + 1; j++) {
                lazyGet(new Coordinates(i, j));
            }
        }
    }

    public List<String> carte() {
        List<String> grille = new ArrayList<String>();
        grille.add(boxTop + "<->"+ boxBottom);
        for (int i = boxTop.getY(); i < boxBottom.getY() + 1; i++) {
            StringBuilder ligne = new StringBuilder();
            ligne.append(i).append("\t|\t");
            for (int j = boxTop.getX(); j < boxBottom.getX() + 1; j++) {
                Land land = carte.get(new Coordinates(j, i));
                if (land == null)
                    ligne.append(" |\t");
                else switch (land) {
                    case Infranchissable:
                        ligne.append("\u2610|\t");
                        break;
                    case Roche:
                        ligne.append("\u203B|\t");
                        break;
                    case Boue:
                        ligne.append("\u2744|\t");
                        break;
                    case Sable:
                        ligne.append("\u2652|\t");
                        break;
                    case Terre:
                        ligne.append("-|\t");
                        break;
                    default:
                        ligne.append(" |\t");
                }
            }
            grille.add(ligne.toString());
        }
        StringBuilder builder = new StringBuilder();
        builder.append("Légende : ").append("Infranchissable ").append('\u2610').append("\tRoche ").append('\u203B');
        builder.append("\tBoue ").append('\u2744').append("\tSable ").append('\u2652').append("\tTerre ").append('-');
        grille.add(builder.toString());
        return grille;
    }

    public Coordinates getTop() {
        return boxTop;
    }

    public int getXBottom() {
        return boxBottom.getX();
    }
}
