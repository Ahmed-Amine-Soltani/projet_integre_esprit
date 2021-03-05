/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.service;

import java.util.HashMap;
import java.util.List;
import projet.models.CategorieProduit;

/**
 *
 * @author yassine
 */
public interface ICategorieProduitService {
    
    public List<CategorieProduit> listCateogries();
    public HashMap<String,Integer> getAllCategorie();
    public CategorieProduit getCategorieWithSousCategorie(int id);
    public CategorieProduit getCategorie(int id);

    
    public boolean ajouterCategorie(CategorieProduit c);
    
    public boolean modifierCategorie(CategorieProduit c);
    
    public boolean supprimerCategorie(int id);
    
    public List<CategorieProduit> search(String libelle);

    public boolean exportXLS();
    
    public List<CategorieProduit> listBestCategorie();
}
