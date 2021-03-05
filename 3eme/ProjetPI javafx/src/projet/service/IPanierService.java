/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.service;

import java.util.List;
import projet.models.Commande;
import projet.models.LigneDePanier;
import projet.models.Panier;
import projet.models.Produit;

/**
 *
 * @author yassine
 */
public interface IPanierService {
    
    public Panier getPanier();
    public List<LigneDePanier> getLignePanier(int id);
    public boolean deleteLignePanier(LigneDePanier ligne,Produit produit);
    public boolean ajouterLignePanier(int produit_id,int qte);
    public boolean updateLignePanier(LigneDePanier ligne,Produit produit,int qte);
    public int getLastCommand();
    public boolean confirmerPanier(Commande commande);
    public void deleteAllLignePanier();
    public List<Commande> getAllCommande();
    public int visitCount();
    public boolean visitCommande();



}
