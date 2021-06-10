package pacman.model.deplacement;

import pacman.model.Map;

public class Teleporteur {
    public Map map;

    public int posX;
    public int posY;
    private final Pacman pacman;

    /**
     * Créé un téléporteur
     * @param pacman le pacman lié au téléporteur
     * @param map la map où se trouve le téléporteur
     */
    public Teleporteur(Pacman pacman, Map map) {
        this.pacman = pacman;
        this.map = map;
    }

    /**
     * Modifie les coordonnées de pacman pour le mettre sur le téléporteur puis supprime le téléporteur
     */
    public void teleporte() {
        this.pacman.setPosX((this.posX*20)+1);
        this.pacman.setPosY((this.posY*20)+1);

        supprimeTeleporteur();
    }

    /**
     * Pose le téléporteur aux coordonnées données
     * @param posX la position X
     * @param posY la position Y
     */
    public void poseTeleporteur(int posX, int posY) {
        this.posX = posX/20;
        this.posY = posY/20;

        map.grid[this.posX][this.posY] = Map.ValeurCase.TELEPORTEUR;

    }

    /**
     * Supprime le téléporteur de la map
     */
    protected void supprimeTeleporteur() {
        map.grid[posX][posY] = Map.ValeurCase.VIDE;
        pacman.teleporteurPose = false;

    }

}
