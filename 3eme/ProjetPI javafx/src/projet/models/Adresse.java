/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.models;

/**
 *
 * @author yassine
 */
public class Adresse {
    
    private int id;
    private int user_id;
    private String Pays;
    private String adresse;
    private String ville;
    private String etat;

    public Adresse() {
    }

    public Adresse(String Pays, String adresse, String ville, String etat) {
        this.Pays = Pays;
        this.adresse = adresse;
        this.ville = ville;
        this.etat = etat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getPays() {
        return Pays;
    }

    public void setPays(String Pays) {
        this.Pays = Pays;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    @Override
    public String toString() {
        return "Adresse{" + "id=" + id + ", user_id=" + user_id + ", Pays=" + Pays + ", adresse=" + adresse + ", ville=" + ville + ", etat=" + etat + '}';
    }
    
    
    
}
