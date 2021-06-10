package pacman.model.deplacement;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.image.Image;

import java.util.Objects;

public class FantomeGroup extends Group {
    public Fantome[] fantomes;

    /**
     * Créé un groupe de 4 fantômes différents et les ajoute à la scène
     */
    public FantomeGroup() {
        fantomes = new Fantome[4];

        fantomes[0] = new FantomeStalker();
        fantomes[1] = new FantomeSprinteur();
        fantomes[2] = new FantomeBrindille();
        fantomes[3] = new FantomeCampeur();
        addFantomeToScene();
    }

    /**
     * Affiche l'image des fantômes du groupe
     */
    public void addFantomeToScene() {
        for (Fantome fantome : this.fantomes) {
            Platform.runLater(() -> this.getChildren().add(fantome.getImageView()));
        }
    }

    /**
     * Réinitialise la position des fantômes du groupe
     */
    public void reinitialisePosition() {
        for (Fantome fantome : this.fantomes) {
            fantome.velocityMultiplicator = fantome.velocityMultiplicatorInitial;
            fantome.mort = false;
            fantome.listeCoordoneDeplacementFant.clear();
            fantome.compteur = 0;
            fantome.initPosition();
        }
    }

    /**
     * Met les fantômes qui ne sont pas morts ou dans le spawn en mode vulnérable
     */
    public void setVulnerable() {
        for (Fantome fantome : this.fantomes) {
            if (!fantome.estAuSpawn() && fantome.etat != Fantome.ValeurEtat.MORT) {
                fantome.etat = Fantome.ValeurEtat.APPEURE;
                fantome.velocityMultiplicator = fantome.VelocityMultiplicatorAppeure;
            }
        }
    }

    /**
     * Réinitialise le compteur des fantômes mangés et remet les fantômes qui ne sont pas en mode normal, morts ou dans le spawn en mode normal
     */
    public void stopVulnerable() {
        fantomes[0].pacman.compteurFantomeMange = 0;
        for (Fantome fantome : this.fantomes) {
            if (fantome.etat != Fantome.ValeurEtat.NORMAL && fantome.etat != Fantome.ValeurEtat.MORT && fantome.etat != Fantome.ValeurEtat.SPAWN) {
                fantome.etat = Fantome.ValeurEtat.NORMAL;
                fantome.velocityMultiplicator = fantome.velocityMultiplicatorInitial;
            }
        }
    }

    /**
     * Met tous les fantômes en mode clignotant ou non selon le booléen passé en paramètres
     * @param valeur true si il faut faire clignoter les fantômes et false si il faut arrêter de les faire clignoter
     */
    public void setClignotant(boolean valeur) {
        for (Fantome fantome : this.fantomes) {
            fantome.clignote = valeur;
        }
    }

    /**
     * Initialise le numéro de chaque fantôme et lui associe son image gelée selon son numéro
     */
    public void initNumFantome() {
        for (int i=0; i<fantomes.length; i++) {
            fantomes[i].numFantome = i+1;
            fantomes[i].imageFantomeGele = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/ghost" + fantomes[i].numFantome + "Gele.gif")));
        }
    }

    /**
     * Met l'image gelée de chaque fantôme selon son mode
     */
    public void freezeFantomes() {
        for (Fantome fantome : fantomes) {
            switch (fantome.etat) {
                case MORT:
                    fantome.setImageView(fantome.imageYeuxGeles);
                    break;
                case APPEURE:
                    fantome.setImageView(fantome.imageBlueGhostGele);
                    break;
                default:
                    fantome.setImageView(fantome.imageFantomeGele);
                    break;
            }
        }
    }

    /**
     * Enlève l'effet de glace de chaque fantôme et met son image en mode normal
     */
    public void unfreezeFantomes() {
        fantomes[0].pacman.freeze = false;
        for (Fantome fantome : fantomes) {
            switch (fantome.etat) {
                case MORT:
                    fantome.setImageView(fantome.imageMort);
                    break;
                case APPEURE:
                    if (fantome.clignote) fantome.setImageView(fantome.imageBlueGhostClignote);
                    else fantome.setImageView(fantome.imageBlueGhost);
                    break;
                default:
                    fantome.setImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/ghost" + fantome.numFantome + ".gif"))));
                    break;
            }
        }
    }
}
