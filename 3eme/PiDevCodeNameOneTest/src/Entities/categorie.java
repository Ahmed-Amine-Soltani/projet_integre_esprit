/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

/**
 *
 * @author Moez
 */
public class categorie {
   private  int id;
   private String nom ; 

   public categorie() {
    } 
   
   public categorie(int id_categorie, String nom) {
        this.id = id_categorie;
        this.nom = nom;
    }

    public categorie(String nom) {
        this.nom = nom;
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

    @Override
    public String toString() {
        return "categorie{" + "id=" + id + ", nom=" + nom + '}';
    }
   

   
    
}
