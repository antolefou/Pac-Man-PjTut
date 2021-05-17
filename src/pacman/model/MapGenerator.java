package pacman.model;

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
        this.afficheMapPasFini2();
        creerTeleportateur();
        creerCarreMilieu();
        creerConstructeur();
        initConstruction();


        while(constructorX.size() != 0) construction();
//        affiche();
//        this.afficheMapPasFini2();
        affineMap();
//        affiche();
        creerMapFinal();
		/*construction();
		construction();
		construction();
		construction();
		construction();
		construction();

        affiche();

        afficheFinal();
        afficheTab();
        System.out.println(constructorX);
        System.out.println(constructorY);
        System.out.println(constructorDir);*/
    }


    private void afficheMapPasFini(){
        for (int i=0; i<map.length-1; i++) {
            for (int j = 0; j < map[0].length - 1; j++) {
                System.out.print("|"+this.map[i][j]);
            }
            System.out.println();
            System.out.println("-------------------------------");
        }
        System.out.println();
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
                        // interdit
                        System.out.print("\033[47m \033[0m");
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
    private void affiche2() {
        for (int i=0; i<map.length; i++) {
            for (int j=0; j<map[0].length; j++) {
                switch(map[i][j]) {
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
                        // interdit
                        System.out.print("\033[47m \033[0m");
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
//    public void afficheFinal() {
//        for (int i=0; i<mapfinal[0].length; i++) {
//            for (int j=0; j<mapfinal.length; j++) {
//                switch(mapfinal[j][i]) {
//                    case "M":
//                        // Mur 44
//                        System.out.print("\033[44m \033[0m");
//                        break;
//                    case "G":
//                        // Gomme
//                        System.out.print("\033[40m \033[0m");
//                        break;
//                    case "Autre":
//                        // Pas encore defini
//                        System.out.print("\033[46m \033[0m");
//                        break;
//                    default:
//                        // Default
//                        System.out.print("\033[47m \033[0m");
//
//                }
//            }
//            System.out.println("");
//        }
//    }

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
                if (i == 0 || i == 13 || j == 0 || j == 29 )
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
        map[9][12] = 2;
        map[9][13] = 2;
        map[9][14] = 2;
        map[9][15] = 2;
        map[9][16] = 2;
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
        map[11][14] = 3;
        map[12][14] = 3;
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
                } else if (map[i][j] == 3) {
                    mapfinal[i][j] = "I";
                    mapfinal[mapfinal.length-i-1][j] =  "I";
                } else {
                    mapfinal[i][j] = "M";
                    mapfinal[mapfinal.length-i-1][j] =  "M";
                }
            }
        }
        mapfinal[12][13] = "I";
    }


    private void affineMap(){
        int k ;
        int emplacemenHorizontal;
        int emplacemenVertical;
        for (int m = 0 ; m<3 ;m++){
//            affiche();
            for (int i=0; i<map.length-1; i++) {
                for (int j = 0; j < map[0].length - 1; j++) {
                    k = 0;
                    // amélioration horizontale
                    while (k+j<this.map[0].length-1 ){

                        // cas 00 et 010
                        if (k<2 && map[i][j+k]==0 ) k = this.map[0].length;

                        // cas 0110 et supérieur
                        else if (k>=2 && map[i][j+k]==0) {
                            for (int l = k ; l > 1 ; l--){
                                emplacemenHorizontal = j + l;
                                if (estRemplacableHorizontalement(i,emplacemenHorizontal)) {
                                    this.map[i][emplacemenHorizontal] = 0;
                                }
                                else break;
                            }
                        }

                        k++;
                    }

                    k = 0;
                    // amélioration vertical
                    while (k+i<this.map.length-1){
                        // cas 00 et 010
                        if (k<2 && map[i+k][j]==0 ) k = this.map.length;

                            // cas 0110 et 01110
                        else if (k>=2 && map[i+k][j]==0) {
                            for (int l = k ; l > 1 ; l--){
                                emplacemenVertical = i+l;
                                if (estRemplacableVerticalement(emplacemenVertical,j)) {
//                                    System.out.println("X : " + i + " | Y : "+ j);
//                                    System.out.println("emplacement Y placé : " + emplacemenVertical);
//                                    System.out.println("___________________________________");
                                    this.map[emplacemenVertical][j] = 0;
                                }
                                else break;
                            }
                        }
                        k++;
                    }
                }
            }
            creerCarreMilieu();

        }
    }

    private int[] ajouteBordure1D(){
        int[] tabAReturn = new int[420]; // tableau [30][14]
        int count = 0;
        for (int i=0; i<30; i++) {
            for (int j = 0; j < 13; j++) {
                tabAReturn[count] = this.map[i][j];
                count+=1;
            }
            tabAReturn[count] = 0; // position map[Y][14]
            count+=1;
        }
        return tabAReturn;

    }

    private int[][] ajouteBordure2D(){
        int[][] tabAReturn = new int[14][30]; // tableau

        for (int i=0; i<13; i++) {
            for (int j = 0; j < 30; j++) {
                tabAReturn[i][j] = this.map[i][j];

            }

        }
        for (int j = 0; j < 30; j++) {
            tabAReturn[13][j] = 1;
        }
        return tabAReturn;
    }

    private void enleveBordure2D(int[][] tabAReturn){
        for (int i=0; i<13; i++) {
            for (int j = 0; j < 30; j++) {
                this.map[i][j] = tabAReturn[i][j] ;
            }

        }
    }

    private void affineMapV4(){
        this.affineMap();
        creerCarreMilieu();
        int[][] mapPourAffine = this.ajouteBordure2D();

        for (int m = 0 ; m<16 ;m++){
            affiche();
            for (int i=1; i<mapPourAffine.length-1; i++) {
                for (int j = 1; j < mapPourAffine[0].length - 1; j++) {

                    if (estRemplacable(mapPourAffine,i,j)) {
                        mapPourAffine[i][j] = 0;
                    }

                }

//                    k = 0;
//                    // amélioration vertical
//                    while (k+j<mapPourAffine.length-1 ){
//                        if (mapPourAffine[i][j+k]==0) {
//                            for (int l = k ; l > 0 ; l--){
//                                emplacemenVertical = j+l;
//                                if (estRemplacable(mapPourAffine,i,emplacemenVertical)) {
//                                    mapPourAffine[i][emplacemenVertical] = 0;
//                                }
//                            }
//                        }
//                        k++;
//                    }
//                }
            }

            this.enleveBordure2D(mapPourAffine);
            creerCarreMilieu();

        }
        System.out.println("--------------------------------------");
    }

    private boolean estRemplacable(int x , int y){
        return (
                // ---------------------- cul de sac ------------------
                (
                        this.map[x][y+1] < 2       //cas1:NNN
                        && this.map[x+1][y] < 2    //     0X0
                        && this.map[x-1][y] < 2    //     N0N
                )

                ||

                (
                        this.map[x][y-1] < 2       //cas2:N0N
                        && this.map[x+1][y] < 2    //     0X0
                        && this.map[x-1][y] < 2    //     NNN
                )

                ||

                (
                        this.map[x][y-1] < 2
                        && this.map[x][y+1] < 2   //cas3:N0N
                                                    //     0XN
                        && this.map[x-1][y] < 2    //     N0N
                )
                ||

                (
                        this.map[x][y-1] < 2
                        && this.map[x][y+1] < 2    //cas4:N0N
                        && this.map[x+1][y] < 2    //     NX0
                                                    //     N0N
                )
            // ------------------- coude
                ||

                (
                        this.map[x+1][y] > 1            // cas 5:
                        && this.map[x-1][y-1] > 1       // 111
                        && this.map[x][y-1] > 1         // NX1
                        && this.map[x+1][y-1] > 1       // NNN
                )
                ||
                (
                        this.map[x-1][y] > 1            // cas 6:
                        && this.map[x-1][y-1] > 1       // 111
                        && this.map[x][y-1] > 1         // 1XN
                        && this.map[x+1][y-1] > 1       // NNN
                )
                ||
                (
                        this.map[x][y+1] > 1            // cas 7:
                        && this.map[x-1][y-1] > 1       // 1NN
                        && this.map[x-1][y] > 1         // 1XN
                        && this.map[x-1][y+1] > 1       // 11N
                )
                ||
                (
                        this.map[x-1][y] > 1            // cas 8:
                        && this.map[x-1][y-1] > 1       // 11N
                        && this.map[x][y-1] > 1         // 1XN
                        && this.map[x-1][y+1] > 1       // 1NN
                )
                ||

                (
                        this.map[x-1][y] > 1            // cas 9:
                        && this.map[x-1][y+1] > 1       // NNN
                        && this.map[x][y+1] > 1         // 1XN
                        && this.map[x+1][y+1] > 1       // 111
                )
                ||
                (
                        this.map[x+1][y] > 1            // cas 10:
                        && this.map[x-1][y+1] > 1       // NNN
                        && this.map[x][y+1] > 1         // NX1
                        && this.map[x+1][y+1] > 1       // 111
                )
                ||
                (
                        this.map[x][y-1] > 1            // cas 11:
                        && this.map[x+1][y-1] > 1       // N11
                        && this.map[x+1][y] > 1         // NX1
                        && this.map[x+1][y+1] > 1       // NN1
                )
                ||
                (
                        this.map[x][y+1] > 1           // cas 12:
                        && this.map[x+1][y-1] > 1      // NN1
                        && this.map[x+1][y] > 1        // NX1
                        && this.map[x+1][y+1] > 1      // N11
                )
        );
    }
    private boolean estRemplacable(int[][] mapAAffine,int x , int y){
        return (
                // ---------------------- cul de sac ------------------
                (
                        mapAAffine[x][y+1] < 2       //cas1:NNN
                        && mapAAffine[x+1][y] < 2    //     0X0
                        && mapAAffine[x-1][y] < 2    //     N0N
                )

                ||

                (
                        mapAAffine[x][y-1] < 2       //cas2:N0N
                        && mapAAffine[x+1][y] < 2    //     0X0
                        && mapAAffine[x-1][y] < 2    //     NNN
                )

                ||

                (
                        mapAAffine[x][y-1] < 2
                        && mapAAffine[x][y+1] < 2   //cas3:N0N
                                                    //     0XN
                        && mapAAffine[x-1][y] < 2    //     N0N
                )
                ||

                (
                        mapAAffine[x][y-1] < 2
                        && mapAAffine[x][y+1] < 2    //cas4:N0N
                        && mapAAffine[x+1][y] < 2    //     NX0
                                                    //     N0N
                )
            // ------------------- coude
                ||

                (
                        mapAAffine[x+1][y] > 1            // cas 5:
                        && mapAAffine[x-1][y-1] > 1       // 111
                        && mapAAffine[x][y-1] > 1         // NX1
                        && mapAAffine[x+1][y-1] > 1       // NNN
                )
                ||
                (
                        mapAAffine[x-1][y] > 1            // cas 6:
                        && mapAAffine[x-1][y-1] > 1       // 111
                        && mapAAffine[x][y-1] > 1         // 1XN
                        && mapAAffine[x+1][y-1] > 1       // NNN
                )
                ||
                (
                        mapAAffine[x][y+1] > 1            // cas 7:
                        && mapAAffine[x-1][y-1] > 1       // 1NN
                        && mapAAffine[x-1][y] > 1         // 1XN
                        && mapAAffine[x-1][y+1] > 1       // 11N
                )
                ||
                (
                        mapAAffine[x-1][y] > 1            // cas 8:
                        && mapAAffine[x-1][y-1] > 1       // 11N
                        && mapAAffine[x][y-1] > 1         // 1XN
                        && mapAAffine[x-1][y+1] > 1       // 1NN
                )
                ||

                (
                        mapAAffine[x-1][y] > 1            // cas 9:
                        && mapAAffine[x-1][y+1] > 1       // NNN
                        && mapAAffine[x][y+1] > 1         // 1XN
                        && mapAAffine[x+1][y+1] > 1       // 111
                )
                ||
                (
                        mapAAffine[x+1][y] > 1            // cas 10:
                        && mapAAffine[x-1][y+1] > 1       // NNN
                        && mapAAffine[x][y+1] > 1         // NX1
                        && mapAAffine[x+1][y+1] > 1       // 111
                )
                ||
                (
                        mapAAffine[x][y-1] > 1            // cas 11:
                        && mapAAffine[x+1][y-1] > 1       // N11
                        && mapAAffine[x+1][y] > 1         // NX1
                        && mapAAffine[x+1][y+1] > 1       // NN1
                )
                ||
                (
                        mapAAffine[x][y+1] > 1           // cas 12:
                        && mapAAffine[x+1][y-1] > 1      // NN1
                        && mapAAffine[x+1][y] > 1        // NX1
                        && mapAAffine[x+1][y+1] > 1      // N11
                )
        );
    }

    private boolean estRemplacableVerticalement(int y , int x){
        return (
                (
                    this.map[y-1][x] > 0
                    && this.map[y+1][x] == 0    //cas:N1N
                    && this.map[y][x+1] == 0    //    0X0
                    && this.map[y][x-1] == 0    //    N0N
                )

                ||

                (
                    this.map[y+1][x] > 0
                    && this.map[y-1][x-1] > 0       // 1NN
                    && this.map[y][x-1] > 0         // 1XN
                    && this.map[y+1][x-1] > 0       // 11N
                )
                ||
                (
                    this.map[y][x-1] > 0
                    && this.map[y-1][x-1] > 0       // 111
                    && this.map[y-1][x] > 0         // 1XN
                    && this.map[y-1][x+1] > 0       // NNN
                )
                ||
                (
                    this.map[y][x-1]       > 0
                    && this.map[y-1][x-1]  > 0      // 110
                    && this.map[y-1][x]    > 0      // 1X0
                    && this.map[y-1][x+1] == 0      // 000
                    && this.map[y][x+1]   == 0
                    && this.map[y+1][x+1] == 0
                    && this.map[y+1][x]   == 0
                    && this.map[y+1][x-1] == 0
                )
                ||
                (
                    this.map[y][x+1] > 0
                    && this.map[y-1][x-1] > 0       // 111
                    && this.map[y-1][x] > 0         // NX1
                    && this.map[y-1][x+1] > 0       // NNN
                )
                ||
                (
                    this.map[y-1][x-1] > 0          // 111
                    && this.map[y-1][x] > 0         // NXN
                    && this.map[y-1][x+1] > 0       // NNN
                )
        );
    }

    private boolean estRemplacableHorizontalement(int y , int x){
        return (
                (
                    this.map[y][x-1] > 0
                    && this.map[y][x+1] == 0    //cas:N0N
                    && this.map[y-1][x] == 0    //    1X0
                    && this.map[y+1][x] == 0    //    N0N
                )
                ||
                (
                        this.map[y][x-1]       > 0
                        && this.map[y-1][x-1]  > 0      // 110
                        && this.map[y-1][x]    > 0      // 1X0
                        && this.map[y-1][x+1] == 0      // 000
                        && this.map[y][x+1]   == 0
                        && this.map[y+1][x+1] == 0
                        && this.map[y+1][x]   == 0
                        && this.map[y+1][x-1] == 0
                )
                ||
                (
                    this.map[y-1][x] > 0
                    && this.map[y-1][x-1] > 0       // 11N
                    && this.map[y][x-1] > 0         // 1XN
                    && this.map[y+1][x-1] > 0       // 1NN
                )
                ||

                (
                    this.map[y+1][x] > 0
                    && this.map[y-1][x-1] > 0       // 1NN
                    && this.map[y][x-1] > 0         // 1XN
                    && this.map[y+1][x-1] > 0       // 11N
                )
                ||

                (
                    this.map[y-1][x-1] > 0          // 1NN
                    && this.map[y][x-1] > 0         // 1XN
                    && this.map[y+1][x-1] > 0       // 1NN
                )
        );
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

    /**
     * Ajoute les objets à la map
     */
    public void initObjet(int nbSuperGomme, int nbBoost) {
        int x,y;
        while (nbSuperGomme != 0 || nbBoost != 0) {
            if (nbSuperGomme != 0) {
                x = (int) (Math.random() * (24+1));
                y = (int) (Math.random() * (28-1+1) + 1);
                if (mapfinal[x][y].equals("G")) {
                    mapfinal[x][y] = "S";
                    nbSuperGomme--;
                }
            } else if (nbBoost != 0) {
                x = (int) (Math.random() * (24+1));
                y = (int) (Math.random() * (28-1+1) + 1);
                if (mapfinal[x][y].equals("G")) {
                    mapfinal[x][y] = "B";
                    nbBoost--;
                }
            }
        }
    }

    public String[][] getMap() {
        return this.mapfinal;
    }

    public void afficheMapPasFini2(){
        for (int i=0; i<map.length; i++) {
            for (int j=0; j<map[0].length; j++) {
                System.out.print(this.map[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

}