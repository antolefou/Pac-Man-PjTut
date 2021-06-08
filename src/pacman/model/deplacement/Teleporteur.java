package pacman.model.deplacement;

import pacman.model.Map;

public class Teleporteur {
    public Map map;

    public int posX;
    public int posY;
    private final Pacman pacman;

    public Teleporteur(Pacman pacman, Map map) {

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
