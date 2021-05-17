package pacman.controller;


import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.control.Label;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import pacman.model.*;
import pacman.model.FantomeGroup;

import java.util.Random;

public class ControllerDeplacement extends Thread {
    private boolean enGame;
    Map map;
    Pacman pacman;
    FantomeGroup fantomeGroup;
    ControllerJouer controller;
    Thread scoreThread;
    Label scoreLabel;

    public enum Deplacements {AUCUN, HAUT, DROITE, BAS, GAUCHE}

    public Deplacements deplacementActuel;
    public Deplacements deplacementFutur;

    public int compteur = 0;
    public int random = 0;
    public Deplacements ancienDeplacementFantome = Deplacements.AUCUN;
    public Random rand = new Random();


    public ControllerDeplacement(Map map, Pacman pacman, FantomeGroup fantomeGroup, ControllerJouer controller) {
        this.map = map;
        this.pacman = pacman;
        this.fantomeGroup = fantomeGroup;
        this.controller = controller;
        enGame = true;
        deplacementActuel = Deplacements.DROITE;
        deplacementFutur = Deplacements.AUCUN;
    }

    public void run() {
        boolean peutAvancer;
        while (pacman.enVie) {
            updateScore();
            if (map.aGagne()) {
                map.recommence();
                pacman.initPosition();
                fantomeGroup.setMap(map);
            }
            peutAvancer = false;
            try {
                if (deplacementFutur != Deplacements.AUCUN) {
                    switch (deplacementFutur) {
                        case HAUT:
                            if (peutAvancerVerticalement(-1)) {
                                pacman.avanceHaut();
                                peutAvancer = true;
                            }
                            break;
                        case DROITE:
                            if (peutAvancerHorizontalement(1)) {
                                pacman.avanceDroite();
                                peutAvancer = true;
                            }
                            break;
                        case BAS:
                            if (peutAvancerVerticalement(1)) {
                                pacman.avanceBas();
                                peutAvancer = true;
                            }
                            break;
                        case GAUCHE:
                            if (peutAvancerHorizontalement(-1)) {
                                pacman.avanceGauche();
                                peutAvancer = true;
                            }
                            break;
                    }
                }
                if (peutAvancer) {
                    deplacementActuel = deplacementFutur;
                    deplacementFutur = Deplacements.AUCUN;
                } else {
                    switch (deplacementActuel) {
                        case HAUT:
                            if (peutAvancerVerticalement(-1))
                                pacman.avanceHaut();
                            break;
                        case DROITE:
                            if (peutAvancerHorizontalement(1))
                                pacman.avanceDroite();
                            break;
                        case BAS:
                            if (peutAvancerVerticalement(1))
                                pacman.avanceBas();
                            break;
                        case GAUCHE:
                            if (peutAvancerHorizontalement(-1))
                                pacman.avanceGauche();
                            break;
                    }
                }
                double pacmanX = pacman.getPacmanX();
                double pacmanY = pacman.getPacmanY();


                // ---------------------------- interractions de pacman ------------------------
                if (pacmanX % 20 == 1 && pacmanY % 20 == 1) {
                    if (map.grid[(((int) pacmanX / 20) + 25) % 25][(int) pacmanY / 20] == Map.ValeurCase.GOMME) {
                        map.grid[(((int) pacmanX / 20) + 25) % 25][(int) pacmanY / 20] = Map.ValeurCase.VIDE;
                        map.caseMap[(((int) pacmanX / 20) + 25) % 25][(int) pacmanY / 20].setImage(null);
                        pacman.score += 10;
                    } else if (map.grid[(((int) pacmanX / 20) + 25) % 25][(int) pacmanY / 20] == Map.ValeurCase.SUPERGOMME) {
                        map.grid[(((int) pacmanX / 20) + 25) % 25][(int) pacmanY / 20] = Map.ValeurCase.VIDE;
                        map.caseMap[(((int) pacmanX / 20) + 25) % 25][(int) pacmanY / 20].setImage(null);
                        pacman.score += 100;
                        pacman.initSuperPacGomme();
                        fantomeGroup.setFantomeVulnerable();
                        // il faudra réduire la vitesse des fantomes et les mettre mangeables
                    } else if (map.grid[(((int) pacmanX / 20) + 25) % 25][(int) pacmanY / 20] == Map.ValeurCase.BOOST) {
                        map.grid[(((int) pacmanX / 20) + 25) % 25][(int) pacmanY / 20] = Map.ValeurCase.VIDE;
                        map.caseMap[(((int) pacmanX / 20) + 25) % 25][(int) pacmanY / 20].setImage(null);
                        pacman.score += 200;
                        pacman.initPowerBoost();
                    }
                }
                pacman.stopPower();
                fantomeGroup.stopVulnerabilite();

                // ---------------------------- Interractions des fantomes ----------------------------
                // les fantomes vont le plus haut à droite
                for (int i = 0; i < 3; i++) {
                    if (fantomeGroup.fantomes[i].peutAvancerVerticalement(map, -1))
                        fantomeGroup.fantomes[i].avanceHaut();
                    else if (fantomeGroup.fantomes[i].peutAvancerHorizontalement(map, 1))
                        fantomeGroup.fantomes[i].avanceDroite();
                    else if (fantomeGroup.fantomes[i].peutAvancerVerticalement(map, 1))
                        fantomeGroup.fantomes[i].avanceBas();
                    else if (fantomeGroup.fantomes[i].peutAvancerHorizontalement(map, -1))
                        fantomeGroup.fantomes[i].avanceGauche();
                }


                //IA fantôme campeur

                if (vueSurPacman(fantomeGroup.fantomes[3], pacmanX, pacmanY, Deplacements.HAUT) || vueSurPacman(fantomeGroup.fantomes[3], pacmanX, pacmanY, Deplacements.GAUCHE) || vueSurPacman(fantomeGroup.fantomes[3], pacmanX, pacmanY, Deplacements.DROITE) || vueSurPacman(fantomeGroup.fantomes[3], pacmanX, pacmanY, Deplacements.BAS)) {
                    String coordFantome = Math.round(fantomeGroup.fantomes[3].imageView.getX() * 0.0499) + "/" + Math.round(fantomeGroup.fantomes[3].imageView.getY() * 0.0499);
                    String coordPacman = Math.round(pacmanX * 0.0499) + "/" + Math.round(pacmanY*0.0499);

                    //System.out.println(DijkstraShortestPath.findPathBetween(map.g, coordFantome, coordPacman).getVertexList());
                }

                else if (fantomeGroup.fantomes[3].imageView.getX() > 247 || fantomeGroup.fantomes[3].imageView.getY() > 241) {
                    if (fantomeGroup.fantomes[3].peutAvancerVerticalement(map, -1) && ancienDeplacementFantome != Deplacements.BAS) {
                        fantomeGroup.fantomes[3].avanceHaut();
                        ancienDeplacementFantome = Deplacements.HAUT;
                    } else if (fantomeGroup.fantomes[3].peutAvancerHorizontalement(map, -1) && ancienDeplacementFantome != Deplacements.DROITE) {
                        fantomeGroup.fantomes[3].avanceGauche();
                        ancienDeplacementFantome = Deplacements.GAUCHE;
                    } else if (fantomeGroup.fantomes[3].peutAvancerHorizontalement(map, 1) && ancienDeplacementFantome != Deplacements.GAUCHE) {
                        fantomeGroup.fantomes[3].avanceDroite();
                        ancienDeplacementFantome = Deplacements.DROITE;
                    } else if (fantomeGroup.fantomes[3].peutAvancerVerticalement(map, 1) && ancienDeplacementFantome != Deplacements.HAUT) {
                        fantomeGroup.fantomes[3].avanceBas();
                        ancienDeplacementFantome = Deplacements.BAS;
                    }
                } else {

                    random = rand.nextInt(4);

                    switch (random) {
                        case 0:
                            if (fantomeGroup.fantomes[3].peutAvancerVerticalement(map, -1)) {
                                if (ancienDeplacementFantome != Deplacements.BAS) {
                                    fantomeGroup.fantomes[3].avanceHaut();
                                    ancienDeplacementFantome = Deplacements.HAUT;
                                }
                            } else {
                                ancienDeplacementFantome();
                            }
                            break;

                        case 1:
                            if (fantomeGroup.fantomes[3].peutAvancerHorizontalement(map, -1)) {
                                if (ancienDeplacementFantome != Deplacements.DROITE) {
                                    fantomeGroup.fantomes[3].avanceGauche();
                                    ancienDeplacementFantome = Deplacements.GAUCHE;
                                }
                            } else {
                                ancienDeplacementFantome();
                                break;
                            }
                            break;

                        case 2:
                            if (fantomeGroup.fantomes[3].peutAvancerVerticalement(map, 1)) {
                                if (ancienDeplacementFantome != Deplacements.HAUT) {
                                    fantomeGroup.fantomes[3].avanceBas();
                                    ancienDeplacementFantome = Deplacements.BAS;
                                }
                            } else {
                                ancienDeplacementFantome();
                            }
                            break;

                        case 3:
                            if (fantomeGroup.fantomes[3].peutAvancerHorizontalement(map, 1)) {
                                if (ancienDeplacementFantome != Deplacements.GAUCHE) {
                                    fantomeGroup.fantomes[3].avanceDroite();
                                    ancienDeplacementFantome = Deplacements.DROITE;
                                }
                            } else {
                                ancienDeplacementFantome();
                            }
                            break;
                    }
                }
                // ------------------------------------ fin -------------------------
                sleep(pacman.sleepThread);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void ancienDeplacementFantome() {
        switch (ancienDeplacementFantome) {
            case BAS:
                if (fantomeGroup.fantomes[3].peutAvancerVerticalement(map, 1))
                    fantomeGroup.fantomes[3].avanceBas();
                break;
            case DROITE:
                if (fantomeGroup.fantomes[3].peutAvancerHorizontalement(map, 1))
                    fantomeGroup.fantomes[3].avanceDroite();
                break;
            case HAUT:
                if (fantomeGroup.fantomes[3].peutAvancerVerticalement(map, -1))
                    fantomeGroup.fantomes[3].avanceHaut();
                break;
            case GAUCHE:
                if (fantomeGroup.fantomes[3].peutAvancerHorizontalement(map, -1))
                    fantomeGroup.fantomes[3].avanceGauche();
                break;
        }
        return;
    }

    private boolean peutAvancerVerticalement(int i) { //haut -> -1 bas -> 1
        double pacmanX = pacman.getPacmanX();
        double pacmanY = pacman.getPacmanY();
        if (pacmanX % 20 == 1) {
            if ((pacmanY % 20 != 1) || ((map.grid[((int) pacmanX / 20) % 25][(((int) pacmanY / 20) + i) % 30] != Map.ValeurCase.MUR) && (map.grid[((int) pacmanX / 20)][((int) pacmanY / 20) + i] != Map.ValeurCase.INTERDIT))) {
                return true;
            }
        }
        return false;
    }

    private boolean peutAvancerHorizontalement(int i) {
        double pacmanX = pacman.getPacmanX();
        double pacmanY = pacman.getPacmanY();
        if (pacmanY % 20 == 1) {
//            System.out.println("x " + ((((int)pacmanX/20)+i)+24)%24);
//            System.out.println("y " + ((int)pacmanY/20));
            if (((pacmanX % 20 != 1) || (map.grid[((((int) pacmanX / 20) + i) + 25) % 25][((int) pacmanY / 20)] != Map.ValeurCase.MUR))) {
                return true;
            }
        }
        return false;
    }

    public void updateScore() {
        scoreThread = new Thread(() -> {
            Platform.runLater(() -> {
                controller.affichageScore.setText(String.valueOf(pacman.score));
            });
        });
        scoreThread.start();
    }

    public boolean vueSurPacman(Fantome fantome, double pacmanX, double pacmanY, Deplacements deplacement) {

        for (int i=0; i < 23; i++) {
                switch (deplacement) {
                    case HAUT:
                        if (map.grid[(int) (Math.round(fantome.imageView.getX()) * 0.0499)][(int) (Math.round(fantome.imageView.getY()) * 0.0499)-i] == Map.ValeurCase.MUR){
                            return false;
                        }
                        else if (map.grilleGraph[(int) (Math.round(fantome.imageView.getX()) * 0.0499)][(int) (Math.round(fantome.imageView.getY()) * 0.0499)-i].equals(Math.round(pacmanX * 0.0499) + "/" + Math.round(pacmanY*0.0499))) {
                            return true;
                        }
                        break;
                    case GAUCHE:
                        if (map.grid[(int) ((Math.round(fantome.imageView.getX()) * 0.0499)-i+25)%25][(int) (Math.round(fantome.imageView.getY()) * 0.0499)] == Map.ValeurCase.MUR) {
                            return false;
                        }
                        else if (map.grilleGraph[((int) (Math.round(fantome.imageView.getX()) * 0.0499)-i+25)%25][(int) (Math.round(fantome.imageView.getY()) * 0.0499)].equals(Math.round(pacmanX * 0.0499) + "/" + Math.round(pacmanY*0.0499))) {
                            return true;
                        }
                        break;
                    case DROITE:
                        if (map.grid[(int) ((Math.round(fantome.imageView.getX()) * 0.0499)+i+25)%25][(int) (Math.round(fantome.imageView.getY()) * 0.0499)] == Map.ValeurCase.MUR || (Math.round(fantome.imageView.getX() * 0.0499)+i)%25 == 0.0) {
                            return false;
                        }
                        else if (map.grilleGraph[((int) (Math.round(fantome.imageView.getX()) * 0.0499)+i+25)%25][(int) (Math.round(fantome.imageView.getY()) * 0.0499)].equals(Math.round(pacmanX * 0.0499) + "/" + Math.round(pacmanY*0.0499))) {
                            return true;
                        }
                        break;
                    case BAS:
                        if (map.grid[(int) (Math.round(fantome.imageView.getX()) * 0.0499)][(int) (Math.round(fantome.imageView.getY()) * 0.0499)+i] == Map.ValeurCase.MUR){
                            return false;
                        }
                        else if (map.grilleGraph[(int) (Math.round(fantome.imageView.getX()) * 0.0499)][(int) (Math.round(fantome.imageView.getY()) * 0.0499)+i].equals(Math.round(pacmanX * 0.0499) + "/" + Math.round(pacmanY*0.0499))) {
                            return true;
                        }
                        break;
                    default:
                        return false;
                }
        }

        return false;

    }
}
