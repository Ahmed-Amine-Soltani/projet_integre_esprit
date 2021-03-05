/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.models;


/**
 *
 * @author houba
 */
public class Experience {

    private int id, cat, user;
    private String nom, desc, lieu, date, image_exp;
    private categorie catExp;
    private Utilisateur userExp;


    public Experience(int user, String nom, String desc, String lieu, String date, int cat, String image_exp) {
        this.user = user;
        this.nom = nom;
        this.desc = desc;
        this.lieu = lieu;
        this.date = date;
        this.cat=cat;
        this.image_exp = image_exp;
    }

    public Experience(int id, int user, String nom, String desc, String lieu, String date, categorie catExp, String image_exp) {
        this.id = id;
        this.user = user;
        this.nom = nom;
        this.desc = desc;
        this.lieu = lieu;
        this.date = date;
        this.catExp = catExp;
        this.image_exp = image_exp;

    }

    public Experience() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCat() {
        return cat;
    }

    public void setCat(int cat) {
        this.cat = cat;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage_exp() {
        return image_exp;
    }

    public void setImage_exp(String image_exp) {
        this.image_exp = image_exp;
    }

  
    public categorie getCatExp() {
        return catExp;
    }

    public void setCatExp(categorie catExp) {
        this.catExp = catExp;
    }

    public Utilisateur getUserExp() {
        return userExp;
    }

    public void setUserExp(Utilisateur userExp) {
        this.userExp = userExp;
    }
    
    

    @Override
    public String toString() {
        return "Experience{" + "id=" + id + ", cat=" + cat + ", user=" + user + ", nom=" + nom + ", desc=" + desc + ", lieu=" + lieu + ", date=" + date + ", image_exp=" + image_exp + ", catExp=" + catExp + ", userExp=" + userExp + '}';
    }

    

}
