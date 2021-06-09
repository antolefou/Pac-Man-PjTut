package pacman.model;

import pacman.controller.ControllerOption;

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
    public int niveauCompetenceTirer;
    public int niveauCompetenceFreeze;
    public int niveauCompetenceTeleporteur;
    public int pointJoueur;
//    tableau de competences
    public String[][] tabCompetence;


    public Utilisateur() {
        lectureUtilisateur();
        initTabCompetence();
    }

    public void initTabCompetence() {
        tabCompetence = new String[5][12];

        /* -- Niveau 0 -- */

        // --------- Projectile -----------
        tabCompetence[0][0] = "Cette compétence permet de tirer sur un fantôme pour qu'il se retourne.";
        tabCompetence[0][1] = "10000";
        tabCompetence[0][2] = "20";
        tabCompetence[0][3] = "10";

        // --------- Freeze -----------
        // description
        tabCompetence[0][4] = "Cette compétence permet de geler/immobiliser les fantômes pendant 2 secondes.";
        // Coût d'achat
        tabCompetence[0][5] = "10000";
        //Coût d'utilisation
        tabCompetence[0][6] = "100";
        //Durée de recharge
        tabCompetence[0][7] = "15";

        // --------- Téléporteur -----------
        tabCompetence[0][8] = "Cette compétence lors de la première utilisation dépose un téléporteur, lors de la deuxième utilisation Pac-Man est téléporté sur le téléporteur.";
        tabCompetence[0][9] = "10000";
        tabCompetence[0][10] = "500";
        tabCompetence[0][11] = "30";

        /* -- Niveau 1 -- */

        // --------- Projectile -----------
        tabCompetence[1][0] = "Cette amélioration de la compétence projectile réduit le temps de recharge de 2 secondes";
        tabCompetence[1][1] = "4000";
        tabCompetence[1][2] = "20";
        tabCompetence[1][3] = "8";

        // --------- Freeze -----------
        // description
        tabCompetence[1][4] = "Cette amélioration de la compétence freeze réduit le temps de recharge de 2 secondes";
        // Coût d'achat
        tabCompetence[1][5] = "5000";
        //Coût d'utilisation
        tabCompetence[1][6] = "100";
        //Durée de recharge
        tabCompetence[1][7] = "13";



        // --------- Téléporteur -----------
        tabCompetence[1][8] = "Cette amélioration de la compétence téléporteur réduit le coût d'utilisation de 50 points";
        tabCompetence[1][9] = "3000";
        tabCompetence[1][10] = "450";
        tabCompetence[1][11] = "30";

        /* -- Niveau 2 -- */

        // --------- Projectile -----------
        tabCompetence[2][0] = "Cette amélioration de la compétence projectile réduit le temps de recharge de 2 secondes";
        tabCompetence[2][1] = "4000";
        tabCompetence[2][2] = "20";
        tabCompetence[2][3] = "8";

        // --------- Freeze -----------
        // description
        tabCompetence[2][4] = "Cette amélioration de la compétence freeze réduit le temps de recharge de 2 secondes";
        // Coût d'achat
        tabCompetence[2][5] = "5000";
        //Coût d'utilisation
        tabCompetence[2][6] = "100";
        //Durée de recharge
        tabCompetence[2][7] = "13";



        // --------- Téléporteur -----------
        tabCompetence[2][8] = "Cette amélioration de la compétence téléporteur réduit le coût d'utilisation de 50 points";
        tabCompetence[2][9] = "3000";
        tabCompetence[2][10] = "450";
        tabCompetence[2][11] = "30";

        /* -- Niveau 3 -- */

        // --------- Projectile -----------
        tabCompetence[3][0] = "Cette amélioration de la compétence projectile réduit le temps de recharge de 2 secondes";
        tabCompetence[3][1] = "4000";
        tabCompetence[3][2] = "20";
        tabCompetence[3][3] = "6";

        // --------- Freeze -----------
        // description
        tabCompetence[3][4] = "Cette amélioration de la compétence freeze réduit le coût d'utilisation de 20 points";
        // Coût d'achat
        tabCompetence[3][5] = "5000";
        //Coût d'utilisation
        tabCompetence[3][6] = "80";
        //Durée de recharge
        tabCompetence[3][7] = "13";



        // --------- Téléporteur -----------
        tabCompetence[3][8] = "Cette amélioration de la compétence téléporteur réduit le coût d'utilisation de 8 secondes";
        tabCompetence[3][9] = "7000";
        tabCompetence[3][10] = "450";
        tabCompetence[3][11] = "22";

        // fin d'amelioration
        // --------- Projectile -----------
        tabCompetence[4][0] = "Cette amélioration est au niveau maximum";
        tabCompetence[4][1] = "4000";
        tabCompetence[4][2] = "20";
        tabCompetence[4][3] = "6";
        // --------- Freeze -----------
        // description
        tabCompetence[4][4] = "Cette amélioration est au niveau maximum";
        // Coût d'achat
        tabCompetence[4][5] = "5 000";
        //Coût d'utilisation
        tabCompetence[4][6] = "80";
        //Durée de recharge
        tabCompetence[4][7] = "13";
        // --------- Téléporteur -----------
        tabCompetence[4][8] = "Cette amélioration est au niveau maximum";
        tabCompetence[4][9] = "7000";
        tabCompetence[4][10] = "450";
        tabCompetence[4][11] = "22";

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
                    case "niveauCompetenceTirer":
                        this.niveauCompetenceTirer = Integer.parseInt(ligne[1]);
                        break;
                    case "niveauCompetenceFreeze":
                        this.niveauCompetenceFreeze = Integer.parseInt(ligne[1]);
                        break;
                    case "niveauCompetenceTeleporteur":
                        this.niveauCompetenceTeleporteur = Integer.parseInt(ligne[1]);
                        break;
                    case "pointJoueur":
                        this.pointJoueur = Integer.parseInt(ligne[1]);
                        break;
                    default:
                        System.out.println("Erreur dans fichier utilisateur :");
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
            writer.println("niveauCompetenceTirer," + niveauCompetenceTirer);
            writer.println("niveauCompetenceFreeze," + niveauCompetenceFreeze);
            writer.println("niveauCompetenceTeleporteur," + niveauCompetenceTeleporteur);
            writer.println("pointJoueur," + pointJoueur);

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reinitialiseCompetencesUtilisateur() {
        niveauCompetenceTirer = -1;
        niveauCompetenceFreeze = -1;
        niveauCompetenceTeleporteur = -1;
        pointJoueur = 0;
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
        this.threadRender = (int) ((1.0/this.fps)*1000);
    }

    public int getSon(){ return son; }

    public void setSon(int son){ this.son = son; }

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
