/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.service;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import projet.models.CategorieProduit;
import projet.models.SousCategorieProduit;
import projet.utils.DbConnection;

/**
 *
 * @author yassine
 */
public class SousCategorieProduitService implements ISousCategorieProduitService{

    static Statement statement = null;
    PreparedStatement pst;

    DbConnection cnx = DbConnection.getInstance();
    Connection connection = cnx.getConnection();
    
    
    
     @Override
    public ObservableList<SousCategorieProduit> listSousCateogries() {

    ObservableList<SousCategorieProduit> listsouscategorie = FXCollections.observableArrayList();
       
        String req = "SELECT s.*,c.libelle FROM categorie_produit c ,sous_categorie_produit s WHERE s.categorie_id=c.id ORDER BY(s.id) DESC";
        try {
                statement = connection.createStatement();
                ResultSet res = statement.executeQuery(req);
                
                while (res.next()) {
                   SousCategorieProduit s = new SousCategorieProduit(res.getInt(1),res.getInt(2), res.getString(3),res.getTimestamp(4));
                   s.setBtn_delete(new Button("Supprimer"));
                   s.setCategorie(res.getString(5));
                   listsouscategorie.add(s);
                }

        } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }

        return listsouscategorie;
    }
    
    @Override
    public HashMap<String, Integer> getSousCategorie(int id) {
        
        HashMap<String,Integer> mapSousCategorie = new HashMap<String,Integer>();
        
        String req = "SELECT * FROM sous_categorie_produit sc,categorie_produit c WHERE sc.categorie_id=c.id AND sc.categorie_id = "+id;
	
        try {
                statement = connection.createStatement();
                ResultSet res = statement.executeQuery(req);
                SousCategorieProduit souscategorie;
                while (res.next()) {
                   souscategorie = new SousCategorieProduit(res.getInt(1), res.getInt(2), res.getString(3),res.getTimestamp(4));
                   souscategorie.setBtn_delete(new Button("Supprimer"));
                   mapSousCategorie.put(souscategorie.getLibelle(),souscategorie.getId());
                }

        } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }

        return mapSousCategorie;
    }

    @Override
    public SousCategorieProduit getSousCategorieById(int id) {
        SousCategorieProduit souscategorie= null;
        
        String req = "SELECT * FROM sous_categorie_produit WHERE id = "+id;
	
        try {
                statement = connection.createStatement();
                ResultSet res = statement.executeQuery(req);
                while (res.next()) {
                   souscategorie = new SousCategorieProduit(res.getInt(1), res.getInt(2), res.getString(3),res.getTimestamp(4));
                   souscategorie.setBtn_delete(new Button("Supprimer"));
                }

        } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }

        return souscategorie;
    }

    @Override
    public boolean ajouterSousCategorie(SousCategorieProduit s) {
          String req = "INSERT INTO sous_categorie_produit(categorie_id,libelle,created) VALUES (?,?,?)";
        try {
            pst = connection.prepareStatement(req);	
            pst.setInt(1, s.getCategorie_id());
            pst.setString(2, s.getLibelle());
            pst.setTimestamp(3, s.getCreated());


            int res = pst.executeUpdate();

            if(res > 0) {
                    return true;
            }
			
        } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
        }   
        return false;
    }

    @Override
    public boolean modifierSousCategorie(SousCategorieProduit s) {
         String req = "UPDATE sous_categorie_produit SET categorie_id= ?,libelle= ? WHERE id= ?";
        try {
            pst = connection.prepareStatement(req);	
            pst.setInt(1, s.getCategorie_id());
            pst.setString(2, s.getLibelle());
            pst.setInt(3, s.getId());
         


            int res = pst.executeUpdate();

            if(res > 0) {
                    return true;
            }	
        } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
        }   
        return false;
    }

    @Override
    public boolean supprimerSousCategorie(int id) {
        
        String req="DELETE FROM sous_categorie_produit WHERE id="+id;
              
        try {
            pst = connection.prepareStatement(req);
            int res = pst.executeUpdate();

            if(res > 0) {
                    return true;
            }	
        } catch (SQLException e1) {
                e1.printStackTrace();
        }   
        return false;
    }

    @Override
    public ObservableList<SousCategorieProduit> search(String libelle) {
    
        ObservableList<SousCategorieProduit> listsouscategorie = FXCollections.observableArrayList();
        
        String req = "SELECT s.*,c.libelle FROM categorie_produit c ,sous_categorie_produit s WHERE s.categorie_id=c.id AND s.libelle LIKE '%"+libelle+"%' ORDER BY(s.id) DESC";
	
        try {
                statement = connection.createStatement();
                ResultSet res = statement.executeQuery(req);
                
                while (res.next()) {
                   SousCategorieProduit sc = new SousCategorieProduit(res.getInt(1),res.getInt(2),res.getString(3),res.getTimestamp(4));
                   sc.setBtn_delete(new Button("Supprimer"));
                   sc.setCategorie(res.getString(5));
                   listsouscategorie.add(sc);
                }

        } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }

        return listsouscategorie;
    }

    @Override
    public boolean exportXLS() {
         try {
            
            ObservableList<SousCategorieProduit> list = FXCollections.observableArrayList(listSousCateogries());
            
            File file = new File("C:\\Users\\solta\\Desktop\\ProjetPI\\src\\projet\\files\\souscategorie.xls");
            WritableWorkbook myexcel = Workbook.createWorkbook(file);
            WritableSheet mysheet = myexcel.createSheet("souscategorie", 0);
            Label id = new Label(0,0,"id");
            Label libelle = new Label(1,0,"libelle");
            Label categorie = new Label(2,0,"categorie");
        
            
            mysheet.addCell(id);
            mysheet.addCell(libelle);
            mysheet.addCell(categorie);
          
            int i = 1;
            for (SousCategorieProduit sc : list) {
                
                id = new Label(0,i,String.valueOf(sc.getId()));
                libelle = new Label(1,i,sc.getLibelle());
                categorie = new Label(2,i,sc.getCategorie());
                 
                mysheet.addCell(id);
                mysheet.addCell(libelle);
                mysheet.addCell(categorie);

                i++;
            }
            
            myexcel.write();
            myexcel.close();
            
            return true;
            
        } catch (IOException ex) {
            Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (WriteException ex) {
            Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
      

   

 
}
