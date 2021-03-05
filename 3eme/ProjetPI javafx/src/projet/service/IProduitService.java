/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.service;

import java.util.List;
import projet.models.Produit;

/**
 *
 * @author yassine
 */
public interface IProduitService {
    
    public List<Produit> listProduits();
    
    public List<Produit> getListProduitsFilter(String filter,String categorie,String sous_categorie,String tri);
    
    public Produit getProduit(int id);
    
    public boolean ajouterProduit(Produit p);
    
    public boolean modifierProduit(Produit p);
    
    public boolean supprimerProduit(int id);
    
    public List<Produit> search(String libelle);
    
    public boolean exportXLS();
    
}
