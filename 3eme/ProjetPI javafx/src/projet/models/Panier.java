/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.models;

import java.util.List;

/**
 *
 * @author yassine
 */
public class Panier {
    
    private int id;
    private int user_id;
    private double total;
    private int nbr_produit;

    public Panier() {
    }

    public Panier(int user_id, double total, int nbr_produit) {
        this.user_id = user_id;
        this.total = total;
        this.nbr_produit = nbr_produit;
    }

    public Panier(int id,int user_id, double total, int nbr_produit) {
        this.id = id;
        this.user_id = user_id;
        this.total = total;
        this.nbr_produit = nbr_produit;
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

   

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getNbr_produit() {
        return nbr_produit;
    }

    @Override
    public String toString() {
        return "Panier{" + "id=" + id + ", user_id=" + user_id + ", total=" + total + ", nbr_produit=" + nbr_produit + '}';
    }

   

   
    
    
    
    
}
