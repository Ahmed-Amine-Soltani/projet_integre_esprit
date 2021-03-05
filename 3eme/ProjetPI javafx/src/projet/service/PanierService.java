/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import projet.controller.WorldfriendshipController;
import projet.models.Commande;
import projet.models.LigneDePanier;
import projet.models.Panier;
import projet.models.Produit;
import projet.models.Utilisateur;
import projet.utils.DbConnection;

/**
 *
 * @author yassine
 */
public class PanierService implements IPanierService{

    static Statement statement = null;
    PreparedStatement pst;

    DbConnection cnx = DbConnection.getInstance();
    Connection connection = cnx.getConnection();
    
    private Utilisateur user = WorldfriendshipController.recupererUtilisateurConnecte;

	
    @Override
    public Panier getPanier() {
        
        Panier panier= null;
        
        String req = "SELECT * FROM panier p ,fos_user u WHERE p.user_id = u.id AND u.id="+user.getId_Utilisateur();
	
        try {
                statement = connection.createStatement();
                ResultSet res = statement.executeQuery(req);
                
                while (res.next()) {
                   
                   panier = new Panier(res.getInt(1),res.getInt(2),res.getDouble(3), res.getInt(4));
                   
                }

        } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }
        
        return panier;
    }
    
    @Override
    public ObservableList<LigneDePanier> getLignePanier(int id){
        
        ObservableList<LigneDePanier> list = FXCollections.observableArrayList();
        
        String req = "SELECT lp.* FROM panier p ,lignedepanier lp WHERE p.id = lp.panier_id AND p.id="+id;
	
        try {
                statement = connection.createStatement();
                ResultSet res = statement.executeQuery(req);
                
                while (res.next()) {
                    LigneDePanier ligne = new LigneDePanier(res.getInt(1),res.getInt(2), res.getInt(3), res.getInt(4), res.getTimestamp(5));
                    list.add(ligne);
                }

        } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }

     return list;
    }

    @Override
    public boolean deleteLignePanier(LigneDePanier ligne,Produit produit) {
        
        try {
            
            Panier panier = getPanier();
            
            String req="DELETE FROM lignedepanier WHERE id="+ligne.getId();
            
            pst = connection.prepareStatement(req);
            int res = pst.executeUpdate();
            
            if(res > 0){
                
                req = "UPDATE panier SET total= ?, nbr_produit= ? WHERE id=?";

                pst = connection.prepareStatement(req);
                pst.setDouble(1,panier.getTotal()- produit.getPrix()*ligne.getQuantite());
                pst.setInt(2, panier.getNbr_produit()-ligne.getQuantite());
                pst.setInt(3, panier.getId());

                res = pst.executeUpdate();
                
                if(res > 0) {
                    
                    req = "UPDATE produit SET stock= ? WHERE id=?";
            
                    pst = connection.prepareStatement(req);
                    pst.setInt(1,ligne.getQuantite() + produit.getStock());
                    pst.setInt(2, produit.getId());

                    res = pst.executeUpdate();
                    
                    if(res > 0) {
                        
                        return true;
                    }
                }
                
            }
           
            
            
           
            
        } catch (SQLException ex) {
            Logger.getLogger(PanierService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
         	
         
    }
    
    public void createPanier() {

        try {
            
            String req = "INSERT INTO panier(user_id,total,nbr_produit) VALUES(?,?,?)";
            
            pst = connection.prepareStatement(req);
            pst.setInt(1,user.getId_Utilisateur());
            pst.setInt(2,7);
            pst.setDouble(3,0);
            pst.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(PanierService.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }

    @Override
    public boolean ajouterLignePanier(int produit_id,int qte) {
        
          Panier panier = getPanier();
          
          if(panier == null){
              createPanier();
              panier = getPanier();
              
              
          }
          
          ProduitService ps = new ProduitService();
          Produit produit = ps.getProduit(produit_id);
          
          if(produit.getStock() < qte ){
              
               ImageView image = new ImageView("projet/images/notdispo.png");
               image.setFitWidth(60);
               image.setFitHeight(60);
               Notifications notification = Notifications.create()
                     .title("Stock")
                     .text("Stock insuffisant")
                     .graphic(image)
                     .hideAfter(Duration.seconds(5))
                     .position(Pos.TOP_RIGHT);
               notification.show();
               
               return false;
          }
                     

          try {
                Timestamp date = new Timestamp(System.currentTimeMillis());
                
                String req = "INSERT INTO  lignedepanier(produit_id,panier_id,quantite,created) VALUES (?,?,?,?)";

                pst = connection.prepareStatement(req);
                pst.setInt(1,produit_id);
                pst.setInt(2, panier.getId());
                pst.setInt(3, qte);
                pst.setTimestamp(4, date);

                int res = pst.executeUpdate();
                
                if(res > 0 ) {
                    
                    req = "UPDATE panier SET total = ?,nbr_produit= ? WHERE id=?";
            
                    pst = connection.prepareStatement(req);
                    pst.setDouble(1,panier.getTotal()+ (produit.getPrix()*qte));
                    pst.setInt(2,panier.getNbr_produit()+qte);
                    pst.setInt(3,panier.getId());

                    res = pst.executeUpdate();
                    
                    if(res > 0) {
                        
                        req = "UPDATE produit SET stock= ? WHERE id=?";
            
                        pst = connection.prepareStatement(req);
                        pst.setInt(1,produit.getStock() - qte);
                        pst.setInt(2, produit_id);

                        res = pst.executeUpdate();
                        
                        if(res > 0) {
                             return true;
                        }
                    }
                }
               
            } catch (SQLException ex) {
            Logger.getLogger(PanierService.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        
        return false;
    }

    @Override
    public boolean updateLignePanier(LigneDePanier ligne, Produit produit, int qte) {
        
        
         try {
            
            if(produit.getStock()+ligne.getQuantite() < qte ){
              
               ImageView image = new ImageView("projet/images/notdispo.png");
               image.setFitWidth(60);
               image.setFitHeight(60);
               Notifications notification = Notifications.create()
                     .title("Stock")
                     .text("Stock insuffisant")
                     .graphic(image)
                     .hideAfter(Duration.seconds(5))
                     .position(Pos.TOP_RIGHT);
               notification.show();
               
               return false;
            }
            
            Panier panier = getPanier();
            
            String req="UPDATE lignedepanier SET quantite = ? WHERE id= ?";
            
            pst = connection.prepareStatement(req);
            pst.setInt(1,qte);
            pst.setInt(2, ligne.getId());
           
            int res = pst.executeUpdate();
            
            if(res > 0){
                
                req = "UPDATE panier SET total= ?, nbr_produit= ? WHERE id=?";

                pst = connection.prepareStatement(req);
                pst.setDouble(1,panier.getTotal()- produit.getPrix()*ligne.getQuantite() + produit.getPrix() * qte);
                pst.setInt(2, panier.getNbr_produit()-ligne.getQuantite() + qte);
                pst.setInt(3, panier.getId());

                res = pst.executeUpdate();
                
                if(res > 0) {
                    
                    req = "UPDATE produit SET stock= ? WHERE id=?";
            
                    pst = connection.prepareStatement(req);
                    pst.setInt(1,ligne.getQuantite() + produit.getStock() - qte);
                    pst.setInt(2, produit.getId());

                    res = pst.executeUpdate();
                    
                    if(res > 0) {
                        
                        return true;
                    }
                }
                
            }
           
            
            
           
            
        } catch (SQLException ex) {
            Logger.getLogger(PanierService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
         	
    }

    @Override
    public int getLastCommand() {
        
        int index = 1;
        
        String req = "SELECT * FROM commande ORDER BY ID DESC LIMIT 1";
	
        try {
                statement = connection.createStatement();
                ResultSet res = statement.executeQuery(req);
                
                while (res.next()) {
                    index = res.getInt(1);
                }

        } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }
        
        return index;

    }

    @Override
    public boolean confirmerPanier(Commande commande) {
        
         try {
             
            Panier panier = getPanier();
            

            Date date = new Date();

            String req = "INSERT INTO commande(panier_id,file,created,visit,total,payer) VALUES(?,?,?,?,?,?)";
            
            pst = connection.prepareStatement(req);
            pst.setInt(1,panier.getId());
            pst.setString(2,commande.getFile());
            pst.setDate(3, new java.sql.Date( date.getTime()));
            pst.setInt(4,0);
            pst.setDouble(5, panier.getTotal());
            pst.setString(6,commande.getPayer());
            int res =  pst.executeUpdate();
            
            if(res > 0){
                
                venteProduit(panier);
                
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PanierService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    private void venteProduit(Panier panier){
        
        ObservableList<LigneDePanier> list = FXCollections.observableArrayList(getLignePanier(panier.getId()));
        
        for (LigneDePanier ligne : list) {
           
            try {
                String req = "INSERT INTO vente__produit(produit_id,nbr_produit) VALUES(?,?)";
                
                pst = connection.prepareStatement(req);
                pst.setInt(1,ligne.getProduit_id());
                pst.setInt(2,ligne.getQuantite());
                pst.executeUpdate();
                
            } catch (SQLException ex) {
                Logger.getLogger(PanierService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void deleteAllLignePanier() {
        
        try {
            
            Panier panier = getPanier();
            
            String req="DELETE FROM lignedepanier WHERE panier_id="+panier.getId();
            
            pst = connection.prepareStatement(req);
            int res = pst.executeUpdate();
            
            if(res > 0) {
                
                req = "UPDATE panier SET total= ?, nbr_produit= ? WHERE id=?";

                pst = connection.prepareStatement(req);
                pst.setDouble(1,7);
                pst.setInt(2,0);
                pst.setInt(3, panier.getId());

                res = pst.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(PanierService.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }

    @Override
    public ObservableList<Commande> getAllCommande() {
        
           ObservableList<Commande> listcommande= FXCollections.observableArrayList();
        
        String req = "SELECT * FROM  commande  ORDER BY(id) DESC";
	
        try {
                statement = connection.createStatement();
                ResultSet res = statement.executeQuery(req);
                
                while (res.next()) {
                   Commande c = new Commande(res.getInt(1),res.getInt(2),res.getString(3), res.getDate(4), res.getDouble(6),res.getString(7));
                   ImageView imageView = new ImageView("projet/images/pdf.png"); 
                   imageView.setFitWidth(43);
                   imageView.setFitHeight(43);
                   Button btn = new Button("",imageView);
                   btn.setStyle("-fx-background-color: transparent;-fx-padding: 0;-fx-cursor: hand");
                   c.setBtn_file(btn);
                   listcommande.add(c);
                }

        } catch (SQLException e) {
                e.printStackTrace();
        } 

        return listcommande;
    }

    @Override
    public boolean visitCommande() {
        
        try {
            String req = "UPDATE commande SET visit= ? WHERE visit=?";
            
            pst = connection.prepareStatement(req);
            pst.setDouble(1,1);
            pst.setInt(2,0);
            
            int res = pst.executeUpdate();
            
            if(res > 0){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PanierService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public int visitCount() {
        int nb = 0;
	
        try {
                String req = "SELECT COUNT(*) FROM  commande WHERE visit=0";

                statement = connection.createStatement();
                ResultSet res = statement.executeQuery(req);
                
                while (res.next()) {
                    nb = res.getInt(1);
                }

        } catch (SQLException e) {
                e.printStackTrace();
        } 
        return nb;
    }
    
}
