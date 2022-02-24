package com.example.demo.model;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;
import java.util.Scanner;

public class Utilisateur {
//    ------- CARACTERISTIQUES -------
//    Pseudo
static String pseudoUtilisateur;
//    vitesse de pacman
    private int fps = 60;
    private int threadRender  = (int) ((1.0/this.fps)*1000);
    //    Volume son
    public int son;
//    Competence du joueur
    public int niveauCompetenceTirer;
    public int niveauCompetenceFreeze;
    public int niveauCompetenceTeleporteur;
    public int pointJoueur;
    public int niveauUtilisateur;
//    tableau de competences
    public String[][] tabCompetence;


    public Utilisateur() {
        this.lectureUtilisateur();
        this.initTabCompetence();
    }

    public void initTabCompetence() {
        this.tabCompetence = new String[5][12];

        /* -- Niveau 0 -- */

        // --------- Projectile -----------
        this.tabCompetence[0][0] = "Cette compétence permet de tirer sur un fantôme pour qu'il se retourne.";
        this.tabCompetence[0][1] = "10000";
        this.tabCompetence[0][2] = "20";
        this.tabCompetence[0][3] = "10";

        // --------- Freeze -----------
        // description
        this.tabCompetence[0][4] = "Cette compétence permet de geler/immobiliser les fantômes pendant 5 secondes.";
        // Coût d'achat
        this.tabCompetence[0][5] = "10000";
        //Coût d'utilisation
        this.tabCompetence[0][6] = "100";
        //Durée de recharge
        this.tabCompetence[0][7] = "15";

        // --------- Téléporteur -----------
        this.tabCompetence[0][8] = "Cette compétence donne a pacman la possibilité de se teleporter, il pose une stelle et peut y retourner quand il veux.";
        this.tabCompetence[0][9] = "10000";
        this.tabCompetence[0][10] = "500";
        this.tabCompetence[0][11] = "30";

        /* -- Niveau 1 -- */

        // --------- Projectile -----------
        this.tabCompetence[1][0] = "Cette amélioration de la compétence projectile réduit le temps de recharge de 2 secondes";
        this.tabCompetence[1][1] = "4000";
        this.tabCompetence[1][2] = "25";
        this.tabCompetence[1][3] = "8";

        // --------- Freeze -----------
        // description
        this.tabCompetence[1][4] = "Cette amélioration de la compétence freeze réduit le temps de recharge de 2 secondes";
        // Coût d'achat
        this.tabCompetence[1][5] = "5000";
        //Coût d'utilisation
        this.tabCompetence[1][6] = "100";
        //Durée de recharge
        this.tabCompetence[1][7] = "13";



        // --------- Téléporteur -----------
        this.tabCompetence[1][8] = "Cette amélioration de la compétence téléporteur réduit le coût d'utilisation de 50 points";
        this.tabCompetence[1][9] = "3000";
        this.tabCompetence[1][10] = "450";
        this.tabCompetence[1][11] = "30";

        /* -- Niveau 2 -- */

        // --------- Projectile -----------
        this.tabCompetence[2][0] = "Cette amélioration de la compétence projectile réduit le temps de recharge de 2 secondes";
        this.tabCompetence[2][1] = "4000";
        this.tabCompetence[2][2] = "20";
        this.tabCompetence[2][3] = "6";

        // --------- Freeze -----------
        // description
        this.tabCompetence[2][4] = "Cette amélioration de la compétence freeze réduit le temps de recharge de 2 secondes";
        // Coût d'achat
        this.tabCompetence[2][5] = "5000";
        //Coût d'utilisation
        this.tabCompetence[2][6] = "100";
        //Durée de recharge
        this.tabCompetence[2][7] = "13";



        // --------- Téléporteur -----------
        this.tabCompetence[2][8] = "Cette amélioration de la compétence téléporteur réduit le coût d'utilisation de 50 points";
        this.tabCompetence[2][9] = "3000";
        this.tabCompetence[2][10] = "450";
        this.tabCompetence[2][11] = "30";

        /* -- Niveau 3 -- */

        // --------- Projectile -----------
        this.tabCompetence[3][0] = "Cette amélioration de la compétence projectile réduit le temps de recharge de 2 secondes";
        this.tabCompetence[3][1] = "4000";
        this.tabCompetence[3][2] = "20";
        this.tabCompetence[3][3] = "5";

        // --------- Freeze -----------
        // description
        this.tabCompetence[3][4] = "Cette amélioration de la compétence freeze réduit le coût d'utilisation de 20 points";
        // Coût d'achat
        this.tabCompetence[3][5] = "5000";
        //Coût d'utilisation
        this.tabCompetence[3][6] = "80";
        //Durée de recharge
        this.tabCompetence[3][7] = "13";



        // --------- Téléporteur -----------
        this.tabCompetence[3][8] = "Cette amélioration de la compétence téléporteur réduit le coût d'utilisation de 8 secondes";
        this.tabCompetence[3][9] = "7000";
        this.tabCompetence[3][10] = "450";
        this.tabCompetence[3][11] = "22";

        // fin d'amelioration
        // --------- Projectile -----------
        this.tabCompetence[4][0] = "Cette amélioration est au niveau maximum";
        this.tabCompetence[4][1] = "4000";
        this.tabCompetence[4][2] = "20";
        this.tabCompetence[4][3] = "4";
        // --------- Freeze -----------
        // description
        this.tabCompetence[4][4] = "Cette amélioration est au niveau maximum";
        // Coût d'achat
        this.tabCompetence[4][5] = "5000";
        //Coût d'utilisation
        this.tabCompetence[4][6] = "80";
        //Durée de recharge
        this.tabCompetence[4][7] = "13";
        // --------- Téléporteur -----------
        this.tabCompetence[4][8] = "Cette amélioration est au niveau maximum";
        this.tabCompetence[4][9] = "7000";
        this.tabCompetence[4][10] = "450";
        this.tabCompetence[4][11] = "22";

    }


    public void lectureUtilisateur() {
        URL resource = getClass().getResource("Utilisateur.txt");
        File file = new File("Utilisateur.txt");

        Scanner scanner = null;
        try {
            scanner = new Scanner(Objects.requireNonNull(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        if (scanner != null) {
            while (scanner.hasNextLine()){
                String txt = scanner.nextLine();
                String[] ligne = txt.split(",");

                switch (ligne[0]) {
                    case "pseudo" -> this.setPseudoUtilisateur(ligne[1]);
                    case "fps" -> this.fps = Integer.parseInt(ligne[1]);
                    case "son" -> this.son = Integer.parseInt(ligne[1]);
                    case "niveauCompetenceTirer" -> this.niveauCompetenceTirer = Integer.parseInt(ligne[1]);
                    case "niveauCompetenceFreeze" -> this.niveauCompetenceFreeze = Integer.parseInt(ligne[1]);
                    case "niveauCompetenceTeleporteur" -> this.niveauCompetenceTeleporteur = Integer.parseInt(ligne[1]);
                    case "pointJoueur" -> this.pointJoueur = Integer.parseInt(ligne[1]);
                    case "niveauUtilisateur" -> this.niveauUtilisateur = Integer.parseInt(ligne[1]);
                    default -> System.out.println("Erreur dans fichier utilisateur :");
                }
            }
        }

    }

    public void ecritureUtilisateur() {
        try {
            PrintWriter writer = new PrintWriter("Utilisateur.txt");
            writer.println("fps," + fps);
            writer.println("son," + son);
            writer.println("niveauCompetenceTirer," + niveauCompetenceTirer);
            writer.println("niveauCompetenceFreeze," + niveauCompetenceFreeze);
            writer.println("niveauCompetenceTeleporteur," + niveauCompetenceTeleporteur);
            writer.println("pointJoueur," + pointJoueur);
            writer.println("niveauUtilisateur," + niveauUtilisateur);

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reinitialiseCompetencesUtilisateur() {
        this.niveauCompetenceTirer = -1;
        this.niveauCompetenceFreeze = -1;
        this.niveauCompetenceTeleporteur = -1;
        this.pointJoueur = 0;
        this.niveauUtilisateur = 1;
    }

    public void updateFps(int fps) {
        this.fps = fps;
        this.threadRender = (int) ((1.0/this.fps)*1000);
    }

    ////// GETTER ET SETTER ////////

    public int getFps() {
        return fps;
    }

    public void setFps(int fps) {
        this.fps = fps;
    }

    public int getSon(){ return son; }

    public void setSon(int son){ this.son = son; }

    public int getThreadRender() {
        return threadRender;
    }

    public int getTHREAD_UPDATE() {
        //     Vitesse de Jeu
        return (int) ((1.0 / 50) * 1000);
    }

    public String getPseudoUtilisateur(){
        return Utilisateur.pseudoUtilisateur;
    }

    public void setPseudoUtilisateur(String pseudoUtilisateur){
        Utilisateur.pseudoUtilisateur = pseudoUtilisateur;
    }
}
