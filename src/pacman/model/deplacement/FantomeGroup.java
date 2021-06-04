package pacman.model.deplacement;

import javafx.application.Platform;
import javafx.scene.Group;

import java.util.ArrayList;

public class FantomeGroup extends Group {
    public Fantome[] fantomes;

    public FantomeGroup() {
        fantomes = new Fantome[4];

        fantomes[0] = new FantomeCampeur();
        fantomes[1] = new FantomeBrindille();
        fantomes[2] = new FantomeSprinteur();
        fantomes[3] = new FantomeSardoche();
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
                fantome.listeCoordoneDeplacementFant.clear();
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
}
