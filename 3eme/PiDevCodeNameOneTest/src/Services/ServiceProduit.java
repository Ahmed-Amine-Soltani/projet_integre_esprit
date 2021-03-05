/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Lignedepanier;
import Entities.Panier;
import Entities.Produit;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author yassine
 */
public class ServiceProduit {
       
    
    public static ArrayList<Produit> getProduits() {
    
        ArrayList<Produit> listproduits = new ArrayList<Produit>();
        
        try {
            ConnectionRequest req = new ConnectionRequest();
            req.setUrl("http://localhost:3000/products");
            req.setPost(false);

            
            NetworkManager.getInstance().addToQueueAndWait(req);
            byte[] data = req.getResponseData();
            if (data == null) {
                throw new IOException("Network Err");
            }
            
            JSONParser parser = new JSONParser();
            Map response = parser.parseJSON(new InputStreamReader(new ByteArrayInputStream(data), "UTF-8"));            
            java.util.List<Map<String, Object>>  content =  (java.util.List<Map<String, Object>>) response.get("root");
            
            for(Map<String, Object> obj : content) {
                Produit p = new Produit();
                
                Double id = (Double) obj.get("id");
                
                p.setId(id.intValue());
                p.setLibelle((String)obj.get("libelle"));
                p.setFirstimg((String)obj.get("firstimg"));
                p.setPrix((double)obj.get("prix"));
                p.setDescription((String)obj.get("description"));
                listproduits.add(p);
            }



        } catch (Exception e) {
        }
        
        return listproduits;
           
   }
    
    
    
      public static Panier getPanier() {
    
        Panier panier = new Panier();
        
        try {
            ConnectionRequest req = new ConnectionRequest();
            req.setUrl("http://localhost:3000/products/panier");
            req.setPost(false);

            
            NetworkManager.getInstance().addToQueueAndWait(req);
            byte[] data = req.getResponseData();
            if (data == null) {
                throw new IOException("Network Err");
            }
            
            JSONParser parser = new JSONParser();
            Map response = parser.parseJSON(new InputStreamReader(new ByteArrayInputStream(data), "UTF-8"));            
            java.util.List<Map<String, Object>>  content =  (java.util.List<Map<String, Object>>) response.get("root");
            
            Double id = (Double) content.get(0).get("id");
            Double nbr = (Double) content.get(0).get("nbr_produit");
                

            panier.setId(id.intValue());       
            panier.setTotal((double) content.get(0).get("total"));
            panier.setNbr_produit(nbr.intValue());



        } catch (Exception e) {
        }
        
        return panier;
           
   }
      
      
      
      
    public static Produit getProduit(int produit_id) {
    
      Produit produit = new Produit();
        
       try {
            ConnectionRequest req = new ConnectionRequest();
            req.setUrl("http://localhost:3000/products/get/"+produit_id);
            req.setPost(false);


            NetworkManager.getInstance().addToQueueAndWait(req);
            byte[] data = req.getResponseData();
            if (data == null) {
                throw new IOException("Network Err");
            }

            JSONParser parser = new JSONParser();
            Map response = parser.parseJSON(new InputStreamReader(new ByteArrayInputStream(data), "UTF-8"));            
            java.util.List<Map<String, Object>>  content =  (java.util.List<Map<String, Object>>) response.get("root");

            
             
                Double id = (Double) content.get(0).get("id");

                
                produit.setId(id.intValue());
                produit.setLibelle((String) content.get(0).get("libelle"));
                produit.setFirstimg((String) content.get(0).get("firstimg"));
                produit.setPrix((double) content.get(0).get("prix"));
                produit.setDescription((String) content.get(0).get("description"));



        } catch (Exception e) {
        }

        return produit;
           
   }
      
      
    public static ArrayList<Lignedepanier> getLignePanier(int panier_id) {

      ArrayList<Lignedepanier> lignes = new ArrayList<Lignedepanier>();
        
        try {
            ConnectionRequest req = new ConnectionRequest();
            req.setUrl("http://localhost:3000/products/lignedepanier/"+panier_id);
            req.setPost(false);


            NetworkManager.getInstance().addToQueueAndWait(req);
            byte[] data = req.getResponseData();
            if (data == null) {
                throw new IOException("Network Err");
            }

            JSONParser parser = new JSONParser();
            Map response = parser.parseJSON(new InputStreamReader(new ByteArrayInputStream(data), "UTF-8"));            
            java.util.List<Map<String, Object>>  content =  (java.util.List<Map<String, Object>>) response.get("root");

            for(Map<String, Object> obj : content) {

                Lignedepanier ligne = new Lignedepanier();

                Double id = (Double) obj.get("id");
                Double produit_id = (Double) obj.get("produit_id");
                Double quantite = (Double) obj.get("quantite");

                ligne.setId(id.intValue());
                ligne.setProduit_id(produit_id.intValue()); 
                ligne.setQuantite(quantite.intValue()); 
                lignes.add(ligne);
            }



        } catch (Exception e) {
        }

        return lignes;


   }
    
   public static boolean deleteLigne(int ligne_id,int produit_id,int panier_id){
       
           try {
             
            ConnectionRequest req = new ConnectionRequest();
            
            req.setUrl("http://localhost:3000/products/delete/"+ligne_id+"/"+produit_id+"/"+panier_id);
            req.setPost(false);

            NetworkManager.getInstance().addToQueueAndWait(req);
           
            String data = new String (req.getResponseData());
  
            if(data.equals("delete avec success")){
                return true;
            }else {
               return false;  
            }
           
        } catch (Exception e) {
        }
        
       return false;
   }
      
   public static boolean modifierLigne(int ligne_id,int produit_id,int qte){
       
           try {
             
            ConnectionRequest req = new ConnectionRequest();
            
            req.setUrl("http://localhost:3000/products/update/"+ligne_id+"/"+produit_id+"/"+qte);
            req.setPost(false);

            NetworkManager.getInstance().addToQueueAndWait(req);
           
            String data = new String (req.getResponseData());
  
            if(data.equals("modifier avec success")){
                return true;
            }else {
               return false;  
            }
           
        } catch (Exception e) {
        }
        
       return false;
   }
      
      
    
    
    public boolean ajouterLigne(int produit_id){
        
         try {
             
            ConnectionRequest req = new ConnectionRequest();
            
            req.setUrl("http://localhost:3000/products/addPanier/"+produit_id+"/1");
            req.setPost(false);

            NetworkManager.getInstance().addToQueueAndWait(req);
           
            String data = new String (req.getResponseData());
  
            if(data.equals("insertion avec success")){
                             System.out.println("aa");

                return true;
            }else {
                             System.out.println("ff");

               return false;  
            }
           
        } catch (Exception e) {
        }
        
        
        return false;
    }
    
    
}

