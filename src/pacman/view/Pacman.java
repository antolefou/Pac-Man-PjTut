package pacman.view;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Pacman extends Group {
    // image pacman
    public Image imagePacmanHaut;
    public Image imagePacmanDroite;
    public Image imagePacmanBas;
    public Image imagePacmanGauche;
    public ImageView imageView;

    public int velocity = 2;
    public int sleepThread = 30;
    public int score = 0;
    public int nbVie = 3;
    // attribut pacman
    public boolean enVie = true;
    public boolean peutManger = false;

    // objets bonus de pacman
    // objet bonus: boost
    private boolean powerBoost;
    private long debutPowerBoost;
    // objet bonus: super pac-gomme
    private boolean powerSuperPacGomme;
    private long debutSuperPacGomme;

    public Pacman() {
        // mets les gifs de pacman
        this.imagePacmanHaut = new Image(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/pacmanUp.gif"));
        this.imagePacmanDroite = new Image(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/pacmanRight.gif"));
        this.imagePacmanBas = new Image(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/pacmanDown.gif"));
        this.imagePacmanGauche = new Image(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/pacmanLeft.gif"));
        affichePacman();
    }

    /**
     * Affiche pacman sur la map avec comme position initiale (12;16)
     */
    public void affichePacman() {
        // ajoute les gifs au imageView puis l'ajoute à la scène
        imageView = new ImageView(this.imagePacmanGauche);
        imageView.setX(241);
        imageView.setY(321);
        imageView.setFitWidth(Map.TAILLE_CASE-2);
        imageView.setFitHeight(Map.TAILLE_CASE-2);
        this.getChildren().add(imageView);
    }

    /**
     * Avance pacman vers la droite. Un test doit être effectué au préalable pour voir si cela est possible
     */
    public void avanceDroite() {
        this.imageView.setImage(this.imagePacmanDroite);
        if (this.imageView.getX() == 501)
            this.imageView.setX(1);
        this.imageView.setX(this.imageView.getX() + this.velocity);
//        System.out.println("X:  " + this.imageView.getX() + "    Y:  " + this.imageView.getY());
    }
    /**
     * Avance pacman vers le bas. Un test doit être effectué au préalable pour voir si cela est possible
     */
    public void avanceBas() {
        this.imageView.setImage(this.imagePacmanBas);
        this.imageView.setY(this.imageView.getY() + this.velocity);
//        System.out.println("X:  " + this.imageView.getX() + "    Y:  " + this.imageView.getY());
    }
    /**
     * Avance pacman vers la gauche. Un test doit être effectué au préalable pour voir si cela est possible
     */
    public void avanceGauche() {
        this.imageView.setImage(this.imagePacmanGauche);
//        System.out.println("pacman pos x " +this.imageView.getX());
        if (this.imageView.getX() == 1)
            this.imageView.setX(501);
        this.imageView.setX(this.imageView.getX() - this.velocity);
//        System.out.println("X:  " + this.imageView.getX() + "    Y:  " + this.imageView.getY());
    }
    /**
     * Avance pacman vers le haut. Un test doit être effectué au préalable pour voir si cela est possible
     */
    public void avanceHaut() {
        this.imageView.setImage(this.imagePacmanHaut);
        this.imageView.setY(this.imageView.getY() - this.velocity);
//        System.out.println("X:  " + this.imageView.getX() + "    Y:  " + this.imageView.getY());
    }

    /**
     * Retourne la position X de pacman
     * @return position X
     */
    public double getPacmanX() {
        return this.imageView.getX();
    }

    /**
     * Retourne la position Y de pacman
     * @return position Y
     */
    public double getPacmanY() {
        return this.imageView.getY();
    }

    /**
     * Initialise la position de pacman lors d'un nouveau niveau
     */
    public void initPosition() {
        imageView.setX(241);
        imageView.setY(321);
    }

    /**
     * Initialise pacman avec les attributs de l'objet bonus: boost
     */
    public void initPowerBoost() {
        velocity = 4;
        debutPowerBoost = System.currentTimeMillis();
        powerBoost = true;
    }
    /**
     * Initialise pacman avec les attributs de l'objet bonus: super pac-gomme
     */
    public void initSuperPacGomme() {
        peutManger = true;
        debutSuperPacGomme = System.currentTimeMillis();
        powerSuperPacGomme = true;
    }

    /**
     * Stoppe tous les objets bonus qui ont atteinds le temps d'effet
     */
    public void stopPower() {
        if (powerBoost) {
            if (System.currentTimeMillis()-debutPowerBoost > 1000 * 5) {  // durée 5 sec
                velocity = 2;   //multiple de 2, sinon bug !!!
                powerBoost = false;
            }
        } else if (powerSuperPacGomme) {
            if (System.currentTimeMillis()-debutPowerBoost > 1000 * 12) {  // durée 12 sec
                peutManger = false;
                powerSuperPacGomme = false;
            }
        }
    }
}
