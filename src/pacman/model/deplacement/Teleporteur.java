package pacman.model.deplacement;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pacman.model.Map;

import java.util.Objects;

public class Teleporteur {
    public Map map;
    private Image image;
    private ImageView imageView;

    public int posX;
    public int posY;
    private Pacman pacman;

    public Teleporteur(Pacman pacman, Map map) {

        this.image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/teleporteur.gif")));
        this.imageView = new ImageView();

        this.pacman = pacman;
        this.map = map;
    }

    public void teleporte() {
        this.pacman.setPosX((this.posX*20)+1);
        this.pacman.setPosY((this.posY*20)+1);

        supprimeTeleporteur();
    }

    public void poseTeleporteur(int posX, int posY) {
        this.posX = posX/20;
        this.posY = posY/20;

        map.grid[this.posX][this.posY] = Map.ValeurCase.TELEPORTEUR;

    }

    protected void supprimeTeleporteur() {

        map.grid[posX][posY] = Map.ValeurCase.VIDE;
        pacman.teleporteurPose = false;

    }

}
