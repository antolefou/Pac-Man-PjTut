package pacman.model;

import java.io.*;
import java.util.Scanner;
import java.util.Arrays;

public class ScoreModel {

    public int MeilleurScore;
    private int ScoreActuel;

    private int score;
    private String nom;

    private int n;
    private String[] ligne;

    public Object[][] tab;


    //Compter nombre de ligne du fichier txt et l'attribuer à n
    public void compterNombrelignes() throws FileNotFoundException {
        String FILENAME = "src/pacman/model/Score.txt";
        try (BufferedReader bufferedreader = new BufferedReader(new FileReader(FILENAME))) {
            String strCurrentLine;
            while ((strCurrentLine = bufferedreader.readLine()) != null) {
                n++;
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    //Lire fichier fxml + séparer le nom et le score
    public void lectureFichierTxt() throws IOException {

        compterNombrelignes();
        try {
            FileInputStream file = new FileInputStream("src/pacman/model/Score.txt");
            Scanner scanner = new Scanner(file);

            //System.out.println("Nombre ligne fichier : " + n);
            tab = new Object[n][2];

            for (int i = 0; i < n; i++) {
                String txt = scanner.nextLine();
                ligne = txt.split(",", 2);
                nom = ligne[0];
                score = Integer.parseInt(ligne[1]);
                tab[i][0] = nom;
                tab[i][1] = score;
            }
            scanner.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private class AlphaComparator implements java.util.Comparator {
        public int compare(Object o1, Object o2) {
            return ((Integer) ((Object[]) o1)[1]).compareTo((Integer) ((Object[]) o2)[1]);
        }
    }

    //Trier les différents scores dans l'ordre décroissant
    public void triScores() {
        Arrays.sort(tab, new AlphaComparator());
    }


    //Une fois le score trié, meilleur score = dernière position dans le tableau
    public int  getMeilleurScore() {
        MeilleurScore = (int) tab[tab.length - 1][1];
        return MeilleurScore;

    }

    //Réecrire les différents scores et noms dans le fichier txt
    public void reecritureFichierTxt() throws IOException {

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

    public Object[][] getTab() {
        return tab;
    }

    /*public void afficheTab(){
        for (int i =0; i<tab.length;i++)
            System.out.println(tab[i][0] + " : " + tab[i][1]);
    }
     */


}
