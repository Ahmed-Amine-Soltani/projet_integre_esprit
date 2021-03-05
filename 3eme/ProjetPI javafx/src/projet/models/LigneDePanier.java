/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.models;

import java.sql.Timestamp;

/**
 *
 * @author yassine
 */
public class LigneDePanier {
    private int id;
    private int produit_id;
    private int panier_id;
    private int quantite;
    private Timestamp created;

    public LigneDePanier() {
    }

    public LigneDePanier(int produit_id, int panier_id, int quantite, Timestamp created) {
        this.produit_id = produit_id;
        this.panier_id = panier_id;
        this.quantite = quantite;
        this.created = created;
    }

    public LigneDePanier(int id, int produit_id, int panier_id, int quantite, Timestamp created) {
        this.id = id;
        this.produit_id = produit_id;
        this.panier_id = panier_id;
        this.quantite = quantite;
        this.created = created;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProduit_id() {
        return produit_id;
    }

    public void setProduit_id(int produit_id) {
        this.produit_id = produit_id;
    }

    public int getPanier_id() {
        return panier_id;
    }

    public void setPanier_id(int panier_id) {
        this.panier_id = panier_id;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "LigneDePanier{" + "id=" + id + ", produit_id=" + produit_id + ", panier_id=" + panier_id + ", quantite=" + quantite + ", created=" + created + '}';
    }

    

    
    
    
    
}
