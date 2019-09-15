package robot;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Explorer {
    public static void main(String[] args) {
        System.out.println("Consommation de base du robot d'exploration ");
        Scanner scanner = new Scanner(System.in);
        double energy = scanner.nextDouble();
        Robot robot = new Robot(energy, new Battery());
        boolean quitter = false;
        while (!quitter) {
            displayMenu();
            String commande;
            do {
                commande = scanner.nextLine();
            } while (commande.length() != 1);
            switch (commande.charAt(0)) {
                case 'A':
                    System.out.println("coordonnées colonne,ligne de dépose du robot");
                    Coordinates coord = lireCoordonnee(scanner);
                    try {
                        robot.land(coord, new LandSensor(new Random(10)));
                        System.out.println("Position actuelle : (" + robot.getXposition() + " ," + robot.getYposition() + ") - orientation : " + robot.getDirection());
                    } catch (UnlandedRobotException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    } catch (LandSensorDefaillance landSensorDefaillance) {
                        landSensorDefaillance.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                    break;
                case 'Z':
                    try {
                        robot.moveForward();
                        System.out.println("Position actuelle : (" + robot.getXposition() + " ," + robot.getYposition() + ") - orientation : " + robot.getDirection());
                    } catch (UnlandedRobotException e) {
                        System.out.println("Le robot est encore en l'air, il doit se poser d'abord");
                    } catch (InsufficientChargeException e) {
                        System.out.println("Oups, piles vides... soyez patient, le soleil fait son oeuvre");
                    } catch (LandSensorDefaillance landSensorDefaillance) {
                        System.out.println("Aie, le module de détection du terrain est défaillant. Abandon de l'exploration");
                        throw new RuntimeException(landSensorDefaillance);
                    } catch (InaccessibleCoordinate inaccessibleCoordinate) {
                        System.out.println("le terrain devant le robot n'est pas praticable");
                    }
                    break;
                case 'S':
                    try {
                        robot.moveBackward();
                        System.out.println("Position actuelle : (" + robot.getXposition() + " ," + robot.getYposition() + ") - orientation : " + robot.getDirection());
                    } catch (UnlandedRobotException e) {
                        System.out.println("Le robot est encore en l'air, il doit se poser d'abord");
                    } catch (InsufficientChargeException e) {
                        System.out.println("Oups, piles vides... soyez patient, le soleil fait son oeuvre");
                    } catch (LandSensorDefaillance landSensorDefaillance) {
                        System.out.println("Aie, le module de détection du terrain est défaillant. Abandon de l'exploration");
                        throw new RuntimeException(landSensorDefaillance);
                    } catch (InaccessibleCoordinate inaccessibleCoordinate) {
                        System.out.println("le terrain derrière le robot n'est pas praticable");
                    }
                    break;
                case 'Q':
                    try {
                        robot.turnLeft();
                        System.out.println("Position actuelle : (" + robot.getXposition() + " ," + robot.getYposition() + ") - orientation : " + robot.getDirection());
                    } catch (UnlandedRobotException e) {
                        System.out.println("Le robot est encore en l'air, il doit se poser d'abord");
                    } catch (InsufficientChargeException e) {
                        System.out.println("Oups, piles vides... soyez patient, le soleil fait son oeuvre");
                    }
                    break;
                case 'D':
                    try {
                        robot.turnRight();
                        System.out.println("Position actuelle : (" + robot.getXposition() + ", " + robot.getYposition() + ") - orientation : " + robot.getDirection());
                    } catch (UnlandedRobotException e) {
                        System.out.println("Le robot est encore en l'air, il doit se poser d'abord");
                    } catch (InsufficientChargeException e) {
                        System.out.println("Oups, piles vides... soyez patient, le soleil fait son oeuvre");
                    }
                    break;
                case 'L':
                    try {
                        robot.cartographier();
                    } catch (LandSensorDefaillance landSensorDefaillance) {
                        System.out.println("Impossible d'établir une cartographie");
                        break;
                    } catch (UnlandedRobotException e) {
                        System.out.println("Le robot est encore en l'air, il doit se poser d'abord");
                    }
                    for (String ligne : robot.carte()) {
                        System.out.println(ligne);
                    }
                    break;
                case 'M':
                    System.out.println("coordonnées colonne,ligne de la destination");
                    Coordinates destination = lireCoordonnee(scanner);
                    try {
                        robot.computeRoadTo(destination);
                    } catch (UnlandedRobotException e) {
                        System.out.println("Impossible d'établir une route, le robot est encore en l'air");
                        break;
                    } catch (LandSensorDefaillance landSensorDefaillance) {
                        System.out.println("Allo Houston, on a un problème. On a perdu le retour sol");
                        break;
                    } catch (UndefinedRoadbookException e) {
                        System.out.println("Impossible d'établir une route, la destination est inatteignable");
                    }
                    try {
                        List<CheckPoint> checkPoints = robot.letsGo();
                        for (CheckPoint point : checkPoints) {
                            System.out.println("Position : (" + point.position.getX() + " ," + point.position.getY() + ") - orientation : " + point.direction);
                        }
                    } catch (UnlandedRobotException e) {
                        System.out.println("Le robot est encore en l'air, il doit se poser d'abord");
                    } catch (UndefinedRoadbookException e) {
                        System.out.println("Il semblerait que le road book soit manquant");
                    } catch (InsufficientChargeException e) {
                        System.out.println("Désolé, je n'ai pas suffisamment d'énergie pour terminer mon parcours");
                        displayBlackBox(robot.blackBox);
                    } catch (LandSensorDefaillance landSensorDefaillance) {
                        System.out.println("Allo Houston, on a un problème. On a perdu le retour sol");
                        displayBlackBox(robot.blackBox);
                    } catch (InaccessibleCoordinate inaccessibleCoordinate) {
                        System.out.println("Je suis malheureusement dans l'impossibilité de rejoindre la destination demandée");
                        displayBlackBox(robot.blackBox);
                    }
                    break;
                case 'X':
                    quitter = true;
            }
        }
    }

    static Coordinates lireCoordonnee(Scanner scanner) {
        boolean conforme;
        int x = 0;
        int y = 0;
        do {
            conforme = true;
            String line = scanner.nextLine();
            String[] tokens = line.replace("(", "").replace(")", "").split(",");
            if (tokens.length != 2) {
                conforme = false;
                System.out.println("Format incorrect. c, l ou (c, l)");
            }
            else
                try {
                    x = Integer.valueOf(tokens[0].trim());
                    y = Integer.valueOf(tokens[1].trim());
                } catch (NumberFormatException e) {
                    conforme = false;
                }
        } while (!conforme);
        return new Coordinates(x, y);
    }


    private static void displayBlackBox(BlackBox blackBox) {
        System.out.println("Contenu de la boite noire");
        for (CheckPoint point : blackBox.getCheckPointList()) {
            System.out.println("Position : (" + point.position.getX() + " ," + point.position.getY() + ") - orientation : " + point.direction + " mode commande : " + (point.manualDirective ? "manuel" : "automatique"));
        }
    }

    private static void displayMenu() {
        System.out.println("Panneau de commandes");
        System.out.println("A : poser le robot");
        System.out.println("Z : avancer");
        System.out.println("Q : tourner à gauche");
        System.out.println("D : tourner à droite");
        System.out.println("S : reculer");
        System.out.println("L : cartographier autour du robot");
        System.out.println("M : donner une coordonnée à atteindre");
        System.out.println("X : Quitter");

    }
}
