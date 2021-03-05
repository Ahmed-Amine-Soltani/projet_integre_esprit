/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package  projet.models;

import javafx.scene.control.CheckBox;

/**
 *
 * @author Samplon
 */
public class Produit1 {
    
    
    public int id;
    public String nom;
    public float prix;
    public float promotion;
    public String ref ;
    public  CheckBox CheckBox;
    public int idpromo;

    public Produit1(int id, String nom, float prix, float promotion, String ref, CheckBox CheckBox, int idpromo) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.promotion = promotion;
        this.ref = ref;
        this.CheckBox = CheckBox;
        this.idpromo = idpromo;
    }

    public int getIdpromo() {
        return idpromo;
    }

    public void setIdpromo(int idpromo) {
        this.idpromo = idpromo;
    }

 

    public CheckBox getCheckBox() {
        return CheckBox;
    }

    public void setCheckBox(CheckBox CheckBox) {
        this.CheckBox = CheckBox;
    }

 
public Produit1(){ }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public float getPromotion() {
        return promotion;
    }

    public void setPromotion(float promotion) {
        this.promotion = promotion;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }
    
    
    
    
    
    
}
