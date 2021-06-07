package pacman.model;

import java.io.*;
import java.util.Scanner;

public class Utilisateur {
//    ------- CARACTERISTIQUES -------
//    Pseudo
static String pseudoUtilisateur;
//    vitesse de pacman
    private int fps = 60;
    private int threadRender  = (int) ((1.0/this.fps)*1000);
//     Vitesse de Jeu
    private final int THREAD_UPDATE = (int) ((1.0/50)*1000);
//    Volume son
    public int son;
//    Competence du joueur
    public String competenceA;
    public String competenceB;
    public String competenceC;
    public String niveauCompetenceA;
    public String niveauCompetenceB;
    public String niveauCompetenceC;



    public Utilisateur() {
        lectureUtilisateur();
    }


    public void lectureUtilisateur() {
        try {
            FileInputStream file = new FileInputStream("src/pacman/model/Utilisateur.txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()){
                String txt = scanner.nextLine();
                String[] ligne = txt.split(",");

                switch (ligne[0]) {
                    case "pseudo":
                        this.setPseudoUtilisateur(ligne[1]);
                        break;
                    case "fps":
                        this.fps = Integer.parseInt(ligne[1]);
                        break;
                    case "son":
                        this.son = Integer.parseInt(ligne[1]);
                        break;
                    case "CompetenceA":
                        this.competenceA = ligne[1];
                        break;
                    case "CompetenceB":
                        this.competenceB = ligne[1];
                        break;
                    case "CompetenceC":
                        this.competenceC = ligne[1];
                        break;
                    case "NiveauCompetenceA":
                        this.niveauCompetenceA = ligne[1];
                        break;
                    case "NiveauCompetenceB":
                        this.niveauCompetenceB = ligne[1];
                        break;
                    case "NiveauCompetenceC":
                        this.niveauCompetenceC = ligne[1];
                        break;
                    default:
                        System.out.println("Erreur dans fichier utilisateur");
                        break;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void ecritureUtilisateur() {
        try {
            PrintWriter writer = new PrintWriter("src/pacman/model/Utilisateur.txt");
            writer.println("fps," + fps);
            writer.println("son," + son);
            writer.println("CompetenceA," + competenceA);
            writer.println("CompetenceB," + competenceB);
            writer.println("CompetenceC," + competenceC);
            writer.println("NiveauCompetenceA," + niveauCompetenceA);
            writer.println("NiveauCompetenceB," + niveauCompetenceB);
            writer.println("NiveauCompetenceC," + niveauCompetenceC);

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reinitialiseUtilisateur() {
        try {
            FileWriter fw = new FileWriter("src/pacman/model/Utilisateur.txt");
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write("fps,60\n" +
                    "son,80\n" +
                    "CompetenceA,aucune\n" +
                    "CompetenceB,aucune\n" +
                    "CompetenceC,aucune\n" +
                    "NiveauCompetenceA,0\n" +
                    "NiveauCompetenceB,0\n" +
                    "NiveauCompetenceC,0");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ////// GETTER ET SETTER ////////

    public int getFps() {
        return fps;
    }

    public void setFps(int fps) {
        this.fps = fps;
    }

    public void updateFps(int fps) {
        this.fps = fps;
        this.threadRender = (int) ((1.0/threadRender)*1000);
    }

    public int getThreadRender() {
        return threadRender;
    }

    public void setThreadRender(int threadRender) {
        this.threadRender = threadRender;
    }

    public int getTHREAD_UPDATE() {
        return THREAD_UPDATE;
    }

    public String getPseudoUtilisateur(){
        return Utilisateur.pseudoUtilisateur;
    }

    public void setPseudoUtilisateur(String pseudoUtilisateur){
        Utilisateur.pseudoUtilisateur = pseudoUtilisateur;
    }
}
