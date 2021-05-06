package pacman.view;

import java.util.* ;
import java.util.ArrayList;

public class MapGenerator {

    public int[][] map = new int[13][30];
    public String[][] mapfinal = new String[25][30];
    public ArrayList<Integer> constructorX = new ArrayList<Integer>();
    public ArrayList<Integer> constructorY = new ArrayList<Integer>();
    public ArrayList<String> constructorDir = new ArrayList<String>();


    public MapGenerator() {
        initMap();
        creerTeleportateur();
        creerCarreMilieu();

        creerConstructeur();
        initConstruction();
        while(constructorX.size() != 0)
            construction();
		/*construction();
		construction();
		construction();
		construction();
		construction();
		construction();*/

        affiche();

        creerMapFinal();

        afficheFinal();
        afficheTab();
        System.out.println(constructorX);
        System.out.println(constructorY);
        System.out.println(constructorDir);
    }


    private void affiche() {
        for (int i=0; i<map[0].length; i++) {
            for (int j=0; j<map.length; j++) {
                switch(map[j][i]) {
                    case 0:
                        // Pas défini
                        System.out.print("\033[41m \033[0m");
                        break;
                    case 1:
                        // Mur
                        System.out.print("\033[44m \033[0m");
                        break;
                    case 2:
                        // Chemin
                        System.out.print("\033[40m \033[0m");
                        break;
                    case 3:
                        // rien pour le moment
                        //System.out.print("\033[47m \033[0m");
                        break;
                    default:
                        // Default
                        System.out.print("\033[44m \033[0m");

                }
            }
            System.out.println("");
        }
        System.out.println("");
    }
    private void afficheFinal() {
        for (int i=0; i<mapfinal[0].length; i++) {
            for (int j=0; j<mapfinal.length; j++) {
                switch(mapfinal[j][i]) {
                    case "M":
                        // Mur 44
                        System.out.print("\033[44m \033[0m");
                        break;
                    case "G":
                        // Gomme
                        System.out.print("\033[40m \033[0m");
                        break;
                    case "Autre":
                        // Pas encore defini
                        System.out.print("\033[47m \033[0m");
                        break;
                    default:
                        // Default
                        System.out.print("\033[44m \033[0m");

                }
            }
            System.out.println("");
        }
    }
    private void afficheTab() {
        for (int i=0; i<mapfinal[0].length; i++) {
            for (int j=0; j<mapfinal.length; j++) {
                System.out.print(mapfinal[j][i]);
            }
            System.out.println("");
        }
    }

    private void initMap() {
        for (int i=0; i<map.length; i++) {
            for (int j=0; j<map[0].length; j++) {
                if (i == 0 || i == 24 || j == 0 || j == 29 )
                    map[i][j] = 1;
                else
                    map[i][j] = 0;
            }
        }
    }

    private void creerTeleportateur() {
        double nbTeleportateur = Math.random();
        int teleportateur1 = 0;
        int teleportateur2 = 0;
        if (nbTeleportateur < 0.6) {
            teleportateur1 = (int) (Math.random()*(25-5+1))+5;
            map[0][teleportateur1] = 2;
            map[1][teleportateur1] = 2;
            constructorX.add(1);
            constructorY.add(teleportateur1);
            constructorDir.add("none");

        } else {
            while(Math.abs(teleportateur2 - teleportateur1) < 4) {
                teleportateur1 = (int) (Math.random()*(25-5+1))+5;
                teleportateur2 = (int) (Math.random()*(25-5+1))+5;
            }
            map[0][teleportateur1] = 2;
            map[1][teleportateur1] = 2;
            constructorX.add(1);
            constructorY.add(teleportateur1);
            constructorDir.add("none");
            map[0][teleportateur2] = 2;
            map[1][teleportateur2] = 2;
            constructorX.add(1);
            constructorY.add(teleportateur2);
            constructorDir.add("none");
        }

    }
    private void creerCarreMilieu() {
        map[10][12] =  2;
        map[11][12] =  2;
        map[12][12] =  2;
        map[9][13] = 2;
        map[9][14] = 2;
        map[9][15] = 2;
        map[10][16] = 2;
        map[11][16] = 2;
        map[12][16] = 2;
        map[10][13] =  1;
        map[11][13] =  1;
        map[12][13] =  1;
        map[10][14] =  1;
        map[10][15] =  1;
        map[11][15] =  1;
        map[12][15] =  1;
        map[11][14] = 2;
        map[12][14] = 2;
    }


    private void creerConstructeur() {
        int x,y;
        //ajour manuel de constructeur
        //map[12][12] = 2;
        map[1][1] = 2;
        constructorX.add(1);
        constructorY.add(1);
        constructorDir.add("none");
        map[1][28] = 2;
        constructorX.add(1);
        constructorY.add(28);
        constructorDir.add("none");
        map[9][12] = 2;
        constructorX.add(9);
        constructorY.add(12);
        constructorDir.add("none");
        map[9][16] = 2;
        constructorX.add(9);
        constructorY.add(16);
        constructorDir.add("none");
        x = (int) (Math.random()*(12-10+1))+10;
        y = (int) (Math.random()*(27-22+1))+22;
        map[x][y] = 2;
        constructorX.add(x);
        constructorY.add(y);
        constructorDir.add("none");
        x = (int) (Math.random()*(8-3+1))+3;
        y = (int) (Math.random()*(9-5+1))+5;
        map[x][y] = 2;
        constructorX.add(x);
        constructorY.add(y);
        constructorDir.add("none");
        x = ((int) (Math.random()*(6-5+1))+5)*2;
        y = (int) (Math.random()*(3-1+1))+1;
        map[x][y] = 2;
        constructorX.add(x);
        constructorY.add(y);
        constructorDir.add("none");/*
		map[6][23] = 2;
		constructorX.add(6);
		constructorY.add(23);
		constructorDir.add("none");*/
    }

    private void initConstruction() {
        // nombre de boucles de constructions:
        int nbConstructeur = constructorX.size();
        for (int i=0; i<nbConstructeur; i++) {
            int x = constructorX.get(0);
            int y = constructorY.get(0);
            if (x<12 && map[x+1][y] != 1) {
                constructorX.add(x+1);
                constructorY.add(y);
                constructorDir.add("d");
                map[x+1][y] = 2;
            }
            if (x>1 && map[x-1][y] != 1) {
                constructorX.add(x-1);
                constructorY.add(y);
                constructorDir.add("g");
                map[x-1][y] = 2;
            }
            if (y<28 && map[x][y+1] != 1) {
                constructorX.add(x);
                constructorY.add(y+1);
                constructorDir.add("b");
                map[x][y+1] = 2;
            }
            if (y>1 && map[x][y-1] != 1) {
                constructorX.add(x);
                constructorY.add(y-1);
                constructorDir.add("h");
                map[x][y-1] = 2;
            }
            constructorX.remove(0);
            constructorY.remove(0);
            constructorDir.remove(0);
        }
    }

    private void construction() {
        // nombre de boucles de constructions:
        int nbConstructeur = constructorX.size();
        for (int i=0; i<nbConstructeur; i++) {
            int x = constructorX.get(0);
            int y = constructorY.get(0);
            int nbDeplacement = (int) (Math.random()*(6-4+1))+4;
            outerloop:
            switch(constructorDir.get(0)) {
                case "d":
                    if (x>11 || map[x+1][y] == 2 || map[x+1][y] == 1)
                        break outerloop;
                    for (int j=0; j<nbDeplacement; j++) {
                        if (x<12 && map[x+1][y] != 1) {
                            map[x+1][y] = 2;
                            x ++;
                        }
                    }
                    constructorX.add(x);
                    constructorY.add(y);
                    if (Math.random() > 0.5 || y>28 || map[x][y+1] == 1)
                        constructorDir.add("h");
                    else
                        constructorDir.add("b");
                    break;
                case "b":
                    if (map[x][y+1] == 2 || map[x][y+1] == 1)
                        break outerloop;
                    for (int j=0; j<nbDeplacement; j++) {
                        if (y<28 && map[x][y+1] != 1) {
                            map[x][y+1] = 2;
                            y ++;
                        }
                    }
                    constructorX.add(x);
                    constructorY.add(y);
                    if (Math.random() > 0.5 || x<1 || map[x-1][y] == 1)
                        constructorDir.add("d");
                    else
                        constructorDir.add("g");
                    break;
                case "g":
                    if (map[x-1][y] == 2 || map[x-1][y] == 1)
                        break outerloop;
                    for (int j=0; j<nbDeplacement; j++) {
                        if (x>1 && map[x-1][y] != 1) {
                            map[x-1][y] = 2;
                            x --;
                        }
                    }
                    constructorX.add(x);
                    constructorY.add(y);
                    if (Math.random() > 0.5 || y>28 || map[x][y+1] == 1)
                        constructorDir.add("h");
                    else
                        constructorDir.add("b");
                    break;
                case "h":
                    if (map[x][y-1] == 2 || map[x][y-1] == 1)
                        break outerloop;
                    for (int j=0; j<nbDeplacement; j++) {
                        if (y>1 && map[x][y-1] != 1) {
                            map[x][y-1] = 2;
                            y --;
                        }
                    }
                    constructorX.add(x);
                    constructorY.add(y);
                    if (Math.random() > 0.5 || x<1 || map[x-1][y] == 1)
                        constructorDir.add("d");
                    else
                        constructorDir.add("g");
                    break;
            }


            constructorX.remove(0);
            constructorY.remove(0);
            constructorDir.remove(0);
        }
    }

    private void creerMapFinal() {
        for (int i=0; i<map.length; i++) {
            for (int j=0; j<map[0].length; j++) {
                if (map[i][j] == 0 || map[i][j] == 1) {
                    mapfinal[i][j] = "M";
                    mapfinal[mapfinal.length-i-1][j] =  "M";
                } else if (map[i][j] == 2) {
                    mapfinal[i][j] = "G";
                    mapfinal[mapfinal.length-i-1][j] =  "G";
                } else {
                    mapfinal[i][j] = "M";
                    mapfinal[mapfinal.length-i-1][j] =  "M";
                }
            }
        }
        mapfinal[12][13] = "G";
    }

    private void amelioreMap() {
        for (int i=0; i<map.length-3; i++) {
            for (int j=0; j<map[0].length-2; j++) {
                if (map[i][j] == 2 && map[i+1][j] == 2
                        && map[i+2][j] == 2 && map[i+3][j] == 2
                        && map[i][j+1] == 2 && map[i+1][j+1] == 2
                        && map[i+2][j+1] == 2 && map[i+3][j+1] == 2
                        && map[i][j+2] == 2 && map[i+1][j+2] == 2
                        && map[i+2][j+2] == 2 && map[i+3][j+2] == 2) {
                    map[i+1][j+1] = 1;
                    map[i+2][j+1] = 1;
                }
            }
        }
        for (int i=0; i<map.length-3; i++) {
            for (int j=0; j<map[0].length-2; j++) {
                if(map[i][j] == 2 && map[i+1][j] == 2
                        && map[i+2][j] == 2
                        && map[i][j+1] == 1 && map[i+1][j+1] == 2
                        && map[i+2][j+1] == 2
                        && map[i][j+2] == 2 && map[i+1][j+2] == 2
                        && map[i+2][j+2] == 2) {
                    map[i+2][j+1] = 1;
                }
            }
        }
        for (int i=0; i<map.length-3; i++) {
            for (int j=0; j<map[0].length-2; j++) {
                if(map[i][j] == 2 && map[i+1][j] == 2
                        && map[i+2][j] == 2
                        && map[i][j+1] == 1 && (map[i+1][j+1] == 2 || map[i+1][j+1] == 1)
                        && map[i+2][j+1] == 1
                        && map[i][j+2] == 2 && map[i+1][j+2] == 2
                        && map[i+2][j+2] == 2) {
                    map[i+2][j+1] = 1;
                }
            }
        }
    }


    public void initObjet() {

    }



    public String[][] getMap() {
        return this.mapfinal;
    }
}