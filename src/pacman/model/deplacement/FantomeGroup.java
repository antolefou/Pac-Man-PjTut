package pacman.model.deplacement;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.image.Image;

import java.util.Objects;

public class FantomeGroup extends Group {
    public Fantome[] fantomes;

    public FantomeGroup() {
        fantomes = new Fantome[4];

        fantomes[0] = new FantomeSardoche();
        fantomes[1] = new FantomeSprinteur();
        fantomes[2] = new FantomeBrindille();
        fantomes[3] = new FantomeCampeur();
        addFantomeToScene();
    }

    public void addFantomeToScene() {
        for (Fantome fantome : this.fantomes) {
            Platform.runLater(() -> this.getChildren().add(fantome.getImageView()));
        }
    }

    public void reinitialisePosition() {
        for (Fantome fantome : this.fantomes) {
            fantome.velocityMultiplicator = fantome.velocityMultiplicatorInitial;
            fantome.mort = false;
            fantome.etat = Fantome.ValeurEtat.SPAWN;
            fantome.listeCoordoneDeplacementFant.clear();
            fantome.compteur = 0;
            fantome.initPosition();
        }
    }

    public void setVulnerable() {
        for (Fantome fantome : this.fantomes) {
            if (!fantome.estAuSpawn() && fantome.etat != Fantome.ValeurEtat.MORT) {
                fantome.etat = Fantome.ValeurEtat.APPEURE;
                fantome.velocityMultiplicator = fantome.VelocityMultiplicatorAppeure;
            }
        }
    }

    public void stopVulnerable() {
        fantomes[0].pacman.compteurFantomeMange = 0;
        for (Fantome fantome : this.fantomes) {
            if (fantome.etat != Fantome.ValeurEtat.NORMAL && fantome.etat != Fantome.ValeurEtat.MORT && fantome.etat != Fantome.ValeurEtat.SPAWN) {
                fantome.etat = Fantome.ValeurEtat.NORMAL;
                fantome.velocityMultiplicator = fantome.velocityMultiplicatorInitial;
            }
        }
    }

    public void setClignotant(boolean valeur) {
        for (Fantome fantome : this.fantomes) {
            fantome.clignote = valeur;
        }
    }

    public void initNumFantome() {
        for (int i=0; i<fantomes.length; i++) {
            fantomes[i].numFantome = i+1;
            fantomes[i].imageFantomeGele = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/ghost" + fantomes[i].numFantome + "Gele.gif")));
        }
    }

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
