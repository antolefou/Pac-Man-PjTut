package pacman.model.deplacement;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.image.Image;

import java.util.ArrayList;
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
            Platform.runLater(() -> {
                this.getChildren().add(fantome.getImageView());
            });
        }
    }

    public void reinitialisePosition() {
        for (Fantome fantome : this.fantomes) {
            fantome.velocityMultiplicator = fantome.velocityMultiplicatorInitial;
            fantome.mort = false;
            fantome.etat = Fantome.ValeurEtat.SPAWN;
            fantome.listeCoordoneDeplacementFant.clear();
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
        }
    }

    public void freezeFantomes() {
        for (Fantome fantome : fantomes) {
            switch (fantome.etat) {
                case MORT -> {
                    fantome.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/yeuxGeles.gif"))));
                }
                case APPEURE -> {
                    fantome.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/blueghostGele.gif"))));
                }
                default -> {
                    fantome.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/ghost" + fantome.numFantome + "Gele.gif"))));
                }
            }
        }
    }

    public void unfreezeFantomes() {
        for (Fantome fantome : fantomes) {
            switch (fantome.etat) {
                case MORT -> {
                    fantome.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/yeux.gif"))));
                }
                case APPEURE -> {
                    fantome.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/blueghost.gif"))));
                }
                default -> {
                    fantome.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/ghost" + fantome.numFantome + ".gif"))));
                }
            }
        }
    }
}
