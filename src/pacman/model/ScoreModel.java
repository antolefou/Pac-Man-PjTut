package pacman.model;


import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ScoreModel {

    public Object[][] tab;
    public int meilleurScore;
    public int scoreActuel;

    /**
     * Lis le fichier des scores
     */
    public void lectureScore() {
        int compteur = 0;
        try {
            //étape 1: charger la classe de driver
            Class.forName("com.mysql.jdbc.Driver");
            //étape 2: créer l'objet de connexion
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql:bddpacman.cwzqbwnooagp.ca-central-1.rds.amazonaws.com", "admin", "adminadmin");
            //étape 3: créer l'objet statement
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM score ORDER BY score LIMIT 5;");
            //étape 4: exécuter la requête
            while(res.next()) {
                this.tab[compteur][0] = res.getString(1);
                this.tab[compteur][1] = res.getInt(2);
                compteur ++;
            }
            //étape 5: fermez l'objet de connexion
            conn.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    /**
     * Réecris les lignes du scores
     * @throws IOException retourne une exception si probleme avec l'écriture
     */
    public void EcritureScore(String nom, int score) throws IOException {

        try {
            //étape 1: charger la classe de driver
            Class.forName("com.mysql.jdbc.Driver");
            //étape 2: créer l'objet de connexion
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql:bddpacman.cwzqbwnooagp.ca-central-1.rds.amazonaws.com", "admin", "adminadmin");
            //étape 3: créer l'objet statement
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery("INSERT INTO score (nom, score) VALUES (" + nom +"," + score + ");");
            //étape 4: exécuter la requête
            while(res.next())
                System.out.println(res.getInt(1)+"  "+res.getString(2)
                        +"  "+res.getString(3));
            //étape 5: fermez l'objet de connexion
            conn.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}