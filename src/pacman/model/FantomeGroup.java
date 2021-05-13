package pacman.model;

import javafx.scene.Group;
import pacman.model.Fantome;

public class FantomeGroup extends Group {

    public Fantome[] fantomes;
    public boolean vulnerable;
    public long debutVulnerabilite;
    public int velocity = 2;

    public FantomeGroup(){
        fantomes = new Fantome[4];
        for (int i=0; i<4; i++)
            fantomes[i] = new Fantome(i+1, this);
        afficheFantome();
    }

    private void afficheFantome() {
        for (int i=0; i<4; i++)
            this.getChildren().add(fantomes[i].imageView);
    }

    public void setFantomeVulnerable() {
        debutVulnerabilite = System.currentTimeMillis();
        vulnerable = true;
        for (int i=0; i<4; i++) {
            fantomes[i].imageView.setImage(fantomes[i].imageFantomeVulnerable);
        }
    }

    public void stopVulnerabilite() {
        if (vulnerable) {
            if (System.currentTimeMillis()-debutVulnerabilite > 1000 * 6) {  // durée 6 sec
                vulnerable = false;
                for (int i=0; i<4; i++) {
                    fantomes[i].imageView.setImage(fantomes[i].imageFantome);
                }
            }
        }
    }
}
