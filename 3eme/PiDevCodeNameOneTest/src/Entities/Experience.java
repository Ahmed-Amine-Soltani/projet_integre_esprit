/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

/**
 *
 * @author houba
 */
public class Experience {

    private int id, cat, user;
    private String nom, desc, lieu, date, image_exp;
    private Utilisateur userExp;

    public Experience(int user, String nom, String desc, String lieu, String date, int cat, String image_exp) {
        this.user = user;
        this.nom = nom;
        this.desc = desc;
        this.lieu = lieu;
        this.date = date;
        this.cat = cat;
        this.image_exp = image_exp;
    }

    public Experience(int id, int user, String nom, String desc, String lieu, String date, String image_exp) {
        this.id = id;
        this.user = user;
        this.nom = nom;
        this.desc = desc;
        this.lieu = lieu;
        this.date = date;
        this.image_exp = image_exp;

    }

    public Experience() {
    }

    public Experience(int id, String nom, String lieu, String date, String desc, String image_exp) {
        this.id = id;
        this.nom = nom;
        this.lieu = lieu;
        this.date = date;
        this.desc = desc;
        this.image_exp = image_exp;
    }
    
    
    public Experience(int id, int user, String nom, String desc, String lieu, String image_exp) {
        this.id = id;
        this.user = user;
        this.nom = nom;
        this.desc = desc;
        this.lieu = lieu;
        this.image_exp = image_exp;
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

   

    public Utilisateur getUserExp() {
        return userExp;
    }

    public void setUserExp(Utilisateur userExp) {
        this.userExp = userExp;
    }

    @Override
    public String toString() {
        return "Experience{" + "id=" + id + ", cat=" + cat + ", user=" + user + ", nom=" + nom + ", desc=" + desc + ", lieu=" + lieu + ", date=" + date + ", image_exp=" + image_exp + ", userExp=" + userExp + '}';
    }



}
