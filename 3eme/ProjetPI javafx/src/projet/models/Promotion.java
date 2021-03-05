/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package  projet.models;

import java.sql.Date;

/**
 *
 * @author Samplon
 */
public class Promotion {
    
    public int id;
    public String nom_promotion;
    public Date date_debut;
    public Date date_fin;
    public float pourcentage;
    public float prix_initiale;
    public float prix_promo;
    public String idProduit;
public Promotion(){
    
}
    public Promotion(int id, String nom_promotion, Date date_debut, Date date_fin, float pourcentage, float prix_initiale, float prix_promo, String idProduit) {
        this.id = id;
        this.nom_promotion = nom_promotion;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.pourcentage = pourcentage;
        this.prix_initiale = prix_initiale;
        this.prix_promo = prix_promo;
        this.idProduit = idProduit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom_promotion() {
        return nom_promotion;
    }

    public void setNom_promotion(String nom_promotion) {
        this.nom_promotion = nom_promotion;
    }

    public Date getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(Date date_debut) {
        this.date_debut = date_debut;
    }

    public Date getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(Date date_fin) {
        this.date_fin = date_fin;
    }

    public float getPourcentage() {
        return pourcentage;
    }

    public void setPourcentage(float pourcentage) {
        this.pourcentage = pourcentage;
    }

    public float getPrix_initiale() {
        return prix_initiale;
    }

    public void setPrix_initiale(float prix_initiale) {
        this.prix_initiale = prix_initiale;
    }

    public float getPrix_promo() {
        return prix_promo;
    }

    public void setPrix_promo(float prix_promo) {
        this.prix_promo = prix_promo;
    }

    public String getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(String idProduit) {
        this.idProduit = idProduit;
    }
    
    
    
}
