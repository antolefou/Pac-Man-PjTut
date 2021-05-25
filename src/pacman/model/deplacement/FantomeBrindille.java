package pacman.model.deplacement;

import javafx.scene.image.Image;

import java.util.Objects;
import java.util.Random;

public class FantomeBrindille extends Fantome {

    private int compteur = 0;
    private int random = 0;
    private final Random rand = new Random();

    public FantomeBrindille() {
        super(221, 281);
        this.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/ghost3.gif"))));
        this.initialisation();
    }

    public void ia() {

        if (compteur % 300 == 0) {
            random = rand.nextInt(10);
        }
        if (random % 10 != -1) {
            if (this.getPosY() <= pacman.getPosY() && this.peutAvancerVerticalement(map, -1) && deplacementActuel != deplacements.BAS) {
                this.avanceHaut();
                deplacementActuel = deplacements.HAUT;
            } else if (this.getPosY() >= pacman.getPosY() && this.peutAvancerVerticalement(map, 1) && deplacementActuel != deplacements.HAUT) {
                this.avanceBas();
                deplacementActuel = deplacements.BAS;
            } else if (this.getPosX() <= pacman.getPosX() && this.peutAvancerHorizontalement(map, -1) && deplacementActuel != deplacements.DROITE) {
                this.avanceGauche();
                deplacementActuel = deplacements.GAUCHE;
            } else if (this.getPosX() >= pacman.getPosX() && this.peutAvancerHorizontalement(map, 1) && deplacementActuel != deplacements.GAUCHE) {
                this.avanceDroite();
                deplacementActuel = deplacements.DROITE;
            } else {
                if (this.peutAvancerVerticalement(map, -1) && deplacementActuel != deplacements.BAS) {
                    this.avanceHaut();
                    deplacementActuel = deplacements.HAUT;
                } else if (this.peutAvancerVerticalement(map, 1) && deplacementActuel != deplacements.HAUT) {
                    this.avanceBas();
                    deplacementActuel = deplacements.BAS;
                } else if (this.peutAvancerHorizontalement(map, -1) && deplacementActuel != deplacements.DROITE) {
                    this.avanceGauche();
                    deplacementActuel = deplacements.GAUCHE;
                } else if (this.peutAvancerHorizontalement(map, 1) && deplacementActuel != deplacements.GAUCHE) {
                    this.avanceDroite();
                    deplacementActuel = deplacements.DROITE;
                } else {
                    ancienDeplacementFantome();
                }
            }
        } else {
            //dikjtra
        }
        compteur++;
    }
}
