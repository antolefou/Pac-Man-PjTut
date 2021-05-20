package pacman.model;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Model {

//     tableau d'images et gifs
    private String[] imagesMenu;
    private String[] imagesJouer;
    private String[] imagesCommunes;



    public Model(){
        imagesMenu = new String[]{"pacman_fleche.png", "image_pac_fantome.png"};
        imagesJouer = new String[]{"labyrinthe/Mur.png"};
        imagesCommunes = new String[]{"pacman_logo.png", "BoutonMenu.png"};
    }

    /**
     * Permet d'importer les images du menu
     * @param index_image
     * @return
     */
    public ImageIcon importImagesMenu(int index_image) {
        BufferedImage myPicture = null;
        ImageIcon icon = null;
        String image = this.imagesMenu[index_image];
        try{
            myPicture = ImageIO.read(new File(System.getProperty("user.dir") + "/src/pacman/ressources/image/Menu/" + image));
            icon = new ImageIcon(myPicture);
        }catch(IOException exp){
            System.out.println("L'image n'a pas été trouvé");
        }
        return icon;
    }

    /**
     * Permet d'importer les images de jouer
     * @param index_image
     * @return
     */
    public ImageIcon importImagesJouer(int index_image) {
        BufferedImage myPicture = null;
        ImageIcon icon = null;
        String image = this.imagesJouer[index_image];
        try{
            myPicture = ImageIO.read(new File(System.getProperty("user.dir") + "/src/pacman/ressources/image/Menu/" + image));
            icon = new ImageIcon(myPicture);
        }catch(IOException exp){
            System.out.println("L'image n'a pas été trouvé");
        }
        return icon;
    }

    public ImageIcon importImagesCommunes(int index_image) {
        BufferedImage myPicture = null;
        ImageIcon icon = null;
        String image = this.imagesCommunes[index_image];
        try{
            myPicture = ImageIO.read(new File(System.getProperty("user.dir") + "/src/pacman/ressources/image/Commun_scenes/" + image));
            icon = new ImageIcon(myPicture);
        }catch(IOException exp){
            System.out.println("L'image n'a pas été trouvé");
        }
        return icon;
    }
}


