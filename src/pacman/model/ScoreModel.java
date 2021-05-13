package pacman.model;

import java.io.*;
import java.util.Scanner;

public class ScoreModel {

    public Object[][] tab;
    public int meilleurScore;
    public int scoreActuel;


    public void lectureTxt() {
        try {
            FileInputStream file = new FileInputStream("src/pacman/model/Score.txt");
            Scanner scanner = new Scanner(file);

            tab = new Object[5][2];

            for (int i = 0; i < 5; i++) {
                String txt = scanner.nextLine();
                String[] ligne = txt.split(",", 2);

                String nom = ligne[0];
                int score = Integer.parseInt(ligne[1]);

                tab[i][0] = nom;
                tab[i][1] = score;

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void triTab() {
        int t = 0;
        String n;
        boolean p;

        do {
            p = false;
            for (int i = 0; i < tab.length - 1; i++) {
                if ((int) tab[i][1] < (int) tab[i + 1][1]) {
                    t = (int) tab[i][1];
                    n = (String) tab[i][0];
                    tab[i][1] = tab[i + 1][1];
                    tab[i][0] = tab[i+1][0];
                    tab[i + 1][1] = t;
                    tab[i+1][0] = n;
                    p = true;
                }
            }
        } while (p);

        for (int i = 0; i< tab.length;i++){
//            System.out.println("Tableau trié : " + tab[i][0] + " : " + tab[i][1] );
        }
    }

    /*public void TriScore(){
        Pacman pac = new Pacman();
        scoreActuel = pac.score;
    }
     */

    public void reecritureTxt() throws IOException {

        try {
            FileWriter fw = new FileWriter("src/pacman/model/Score.txt");
            BufferedWriter bw = new BufferedWriter(fw);

            for (int i = 0; i < tab.length; i++) {
                bw.write(String.valueOf(tab[i][0]));
                bw.write(",");
                bw.write(String.valueOf(tab[i][1]));
                bw.newLine();
            }
            bw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int getMeilleurScore() {
        meilleurScore = (int) tab[0][1];
        return meilleurScore;
    }
}