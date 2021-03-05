/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.service;

import java.util.HashMap;
import java.util.List;
import projet.models.SousCategorieProduit;

/**
 *
 * @author yassine
 */
public interface ISousCategorieProduitService {
    
        public List<SousCategorieProduit> listSousCateogries();

        public HashMap<String,Integer> getSousCategorie(int id);
        
        public SousCategorieProduit getSousCategorieById(int id);
        
        public boolean ajouterSousCategorie(SousCategorieProduit s);
    
        public boolean modifierSousCategorie(SousCategorieProduit s);

        public boolean supprimerSousCategorie(int id);

        public List<SousCategorieProduit> search(String libelle);
        
        public boolean exportXLS();
}
