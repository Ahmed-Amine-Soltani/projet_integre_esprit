/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

/**
 *
 * @author solta
 */
public class promoo {
    private int id;
    private String nom;
    private float pourcentage;
    private float prix_initiale;
    private float prix_promo;

    public promoo() {
    }

    public promoo(int id, String nom, float pourcentage, float prix_initiale, float prix_promo) {
        this.id = id;
        this.nom = nom;
        this.pourcentage = pourcentage;
        this.prix_initiale = prix_initiale;
        this.prix_promo = prix_promo;
    }

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
    
    
}
