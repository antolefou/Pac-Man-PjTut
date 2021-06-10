package pacman.model;


import pacman.model.deplacement.Pacman;
import java.io.*;
import java.util.Scanner;

public class ScoreModel {

    public Object[][] tab;
    public int meilleurScore;
    public int scoreActuel;

    /**
     * Lis le fichier des scores
     */
    public void lectureTxt() {
        try {
            FileInputStream file = new FileInputStream("src/pacman/model/Score.txt");
            Scanner scanner = new Scanner(file);

            this.tab = new Object[5][2];

            for (int i = 0; i < 5; i++) {
                String txt = scanner.nextLine();
                String[] ligne = txt.split(",", 2);

                String nom = ligne[0];
                int score = Integer.parseInt(ligne[1]);

                this.tab[i][0] = nom;
                this.tab[i][1] = score;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * trie le tableau des scores dans l'ordre décroissant des scores
     */
    public void triTab() {
        int t;
        String n;
        boolean p;
        do {
            p = false;
            for (int i = 0; i < this.tab.length - 1; i++) {
                if ((int) this.tab[i][1] < (int) this.tab[i + 1][1]) {
                    t = (int) this.tab[i][1];
                    n = (String) this.tab[i][0];
                    this.tab[i][1] = this.tab[i + 1][1];
                    this.tab[i][0] = this.tab[i+1][0];
                    this.tab[i + 1][1] = t;
                    this.tab[i+1][0] = n;
                    p = true;
                }
            }
        } while (p);
    }

    /**
     * Réecris les lignes du scores
     * @throws IOException retourne une exception si probleme avec l'écriture
     */
    public void reecritureTxt() throws IOException {

        try {
            FileWriter fw = new FileWriter("src/pacman/model/Score.txt");
            BufferedWriter bw = new BufferedWriter(fw);

            for (Object[] objects : tab) {
                bw.write(String.valueOf(objects[0]));
                bw.write(",");
                bw.write(String.valueOf(objects[1]));
                bw.newLine();
            }
            bw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Permet de savoir quelle ligne est à remplacer
     * @param pac prend pacman en parametre
     * @return l'élèment à remplacer
     */
    public int Trouverindice(Pacman pac){
        this.scoreActuel = pac.score;
        for (int k = 0; k< this.tab.length-1;k++){
            if ((int) this.tab[k][1] < this.scoreActuel){
                return k;
            }
        }
        return -1;
    }

    /**
     * Prend en compte l'indice du score de pacman et rentre le score et le nom associé dans le tableau
     * @param pac prend pacman en parametre
     * @throws IOException retourne une exception si probleme lors de la lecture
     */
    public void TriScore(Pacman pac) throws IOException {
        this.lectureTxt();
        this.triTab();
        this.scoreActuel = pac.score;
        if (Trouverindice(pac) != -1){
            for (int i = this.tab.length-1; i>Trouverindice(pac);i--){
                this.tab[i][0] = this.tab[i-1][0];
                this.tab[i][1] = this.tab[i-1][1];
            }
            this.tab[Trouverindice(pac)][0] = "pseudoUt";
            this.tab[Trouverindice(pac)][1] = this.scoreActuel;
        }

        if (this.scoreActuel < (int) tab[3][1] && this.scoreActuel > (int) tab[4][1]){
            this.tab[4][0] = "pseudoUt";
            this.tab[4][1] = this.scoreActuel;
        }
        this.reecritureTxt();
    }

    /**
     * Associe le meilleur score et la première ligne du tableau
     * @return meilleur score
     */
    public int getMeilleurScore() {
        this.meilleurScore = (int) tab[0][1];
        return this.meilleurScore;
    }
}