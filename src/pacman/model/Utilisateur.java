package pacman.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Utilisateur {
//    vitesse de pacman
    private int fps;
    private int threadRender;
//     Vitesse de Jeu
    private final int THREAD_UPDATE = (int) ((1.0/50)*1000);
//    Volume son

//    Pseudo
    static String pseudoUtilisateur;

    public Utilisateur() {
        Utilisateur.pseudoUtilisateur = "PLAYER";
        this.fps = 60;
        this.threadRender  = (int) ((1.0/this.fps)*1000);
    }


    public void initUtilisateur() {
        try {
            FileInputStream file = new FileInputStream("src/pacman/model/Utilisateur.txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()){
                String txt = scanner.nextLine();
                String[] ligne = txt.split(",");

                if (ligne[0].equals("fps")) {
                    fps = Integer.parseInt(ligne[1]);
                } else if (ligne[0].equals("son")) {
                    //
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void saveUtilisateur() {

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
