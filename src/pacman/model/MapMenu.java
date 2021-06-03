//package pacman.model;
//
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//
//import java.io.*;
//import java.util.Objects;
//import java.util.Scanner;
//
//public class MapMenu {
//
//    public char[][] mapMenuSTR;
//    public ImageView[][] mapMenu;
//
//    private final Image imageMur;
//
//    public MapMenu() {
//        imageMur = new Image(Objects.requireNonNull(getClass().getResourceAsStream("src/pacman/ressources/image/Ecran_jouer/labyrinthe/wall.png")));
//    }
//
//    public void initialiseMap() throws IOException {
//        File file = new File("src/pacman/model/mapMenu.txt");
//
//        FileReader fr = new FileReader(file);
//        BufferedReader br = new BufferedReader(fr);
//        int c = 0;
//
//        mapMenuSTR = new char[30][40];
//
//        int cpt = 0;
//
//        while ((c = br.read()) != -1) {
//            char ch = (char) c;
//            for (int ligne = 0; ligne < 30; ligne++) {
//                for (int colonne = 0; colonne < 40; colonne++) {
//                    mapMenuSTR[ligne][colonne] = ch;
////                    System.out.println("ligne " + ligne + " : " + mapMenuSTR[ligne][colonne]);
////                    System.out.println("Colonne " + colonne + " : " + mapMenuSTR[ligne][colonne]);
//                }
//            }
//        }
//    }
//
//    public void initialiseMapIMG() throws IOException {
//        initialiseMap();
//        mapMenu = new ImageView[30][40];
//        for (int ligne = 0; ligne < 30; ligne++) {
//            for (int colonne = 0; colonne < 40; colonne++) {
//                if (mapMenuSTR[ligne][colonne] == 'M') {
//                    this.mapMenu[ligne][colonne].setImage(this.imageMur);
//                }
//                else if (mapMenuSTR[ligne][colonne] == 'V'){
//                    this.mapMenu[ligne][colonne].setImage(null);
//                }
//            }
//
//        }
//    }
//}
