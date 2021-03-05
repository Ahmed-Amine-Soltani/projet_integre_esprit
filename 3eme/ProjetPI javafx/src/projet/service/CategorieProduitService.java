/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import projet.models.CategorieProduit;
import projet.utils.DbConnection;

/**
 *
 * @author yassine
 */
public class CategorieProduitService implements ICategorieProduitService{
    
    static Statement statement = null;
    PreparedStatement pst;

    DbConnection cnx = DbConnection.getInstance();
    Connection connection = cnx.getConnection();

  
    @Override
    public ObservableList<CategorieProduit> listCateogries() {
        
        ObservableList<CategorieProduit> listCategories = FXCollections.observableArrayList();
       
        String req = "SELECT * FROM categorie_produit ORDER BY(id) DESC";	
        
        try {
                statement = connection.createStatement();
                ResultSet res = statement.executeQuery(req);
                while (res.next()) {
                   CategorieProduit categorie = new CategorieProduit(res.getInt(1), res.getString(2), res.getString(3));
        
                   Image image = new Image( new FileInputStream("C:\\wamp64\\www\\worldfriendship\\web\\assets\\img\\categorie\\"+res.getString(3)));
                   ImageView imageView = new ImageView(image); 
                   imageView.setFitWidth(50);
                   imageView.setFitHeight(50);
                   categorie.setImage(imageView);
                   categorie.setBtn_delete(new Button("Supprimer"));
                   listCategories.add(categorie);
                }

        } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CategorieProduitService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listCategories;
    

    }

    @Override
    public HashMap<String, Integer> getAllCategorie() {
        
        HashMap<String,Integer> mapCategorie = new HashMap<String,Integer>();
        
        String req = "SELECT * FROM categorie_produit";
	
        try {
                statement = connection.createStatement();
                ResultSet res = statement.executeQuery(req);
                CategorieProduit categorie;
                while (res.next()) {
                   categorie = new CategorieProduit(res.getInt(1), res.getString(2), res.getString(3));
                   mapCategorie.put(categorie.getLibelle(),categorie.getId());
                }

        } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }

        return mapCategorie;
    }

    @Override
    public CategorieProduit getCategorieWithSousCategorie(int id) {
     
        CategorieProduit categorie= null;
        String req = "SELECT c.* FROM categorie_produit c,sous_categorie_produit sc WHERE c.id=sc.categorie_id AND sc.id="+id;
	
        try {
                statement = connection.createStatement();
                ResultSet res = statement.executeQuery(req);
                
                while (res.next()) {
                   categorie = new CategorieProduit(res.getInt(1), res.getString(2), res.getString(3));
                   categorie.setBtn_delete(new Button("Supprimer"));
                }

        } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }

        return categorie;
    }

    @Override
    public boolean ajouterCategorie(CategorieProduit c) {
         
        Timestamp date = new Timestamp(System.currentTimeMillis());
        
        String req = "INSERT INTO categorie_produit(libelle,image,updated_at) VALUES (?,?,?)";
        try {
            pst = connection.prepareStatement(req);	
            pst.setString(1, c.getLibelle());
            pst.setString(2, c.getPath());
            pst.setTimestamp(3, date);
          
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
    public boolean modifierCategorie(CategorieProduit c) {
        String req = "UPDATE categorie_produit SET libelle= ?,image= ? WHERE id= ?";
        try {
            pst = connection.prepareStatement(req);	
            pst.setString(1,c.getLibelle());
            pst.setString(2, c.getPath());
            pst.setInt(3, c.getId());

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
    public boolean supprimerCategorie(int id) {
        
         String req="DELETE FROM categorie_produit WHERE id="+id;
              
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
    public ObservableList<CategorieProduit> search(String libelle) {
        
        ObservableList<CategorieProduit> listcategorie= FXCollections.observableArrayList();
        
        String req = "SELECT * FROM produit categorie_produit  WHERE libelle LIKE '%"+libelle+"%' ORDER BY(id) DESC";
	
        try {
                statement = connection.createStatement();
                ResultSet res = statement.executeQuery(req);
                
                while (res.next()) {
                   CategorieProduit c = new CategorieProduit(res.getInt(1),res.getString(2), res.getString(3));
                   c.setBtn_delete(new Button("Supprimer"));
                   listcategorie.add(c);
                }

        } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }

        return listcategorie;
    }

    @Override
    public CategorieProduit getCategorie(int id) {
        
        CategorieProduit categorie= null;
        String req = "SELECT * FROM categorie_produit WHERE id="+id;
	
        try {
                statement = connection.createStatement();
                ResultSet res = statement.executeQuery(req);
                while (res.next()) {
                   categorie = new CategorieProduit(res.getInt(1), res.getString(2), res.getString(3));
                }

        } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }

        return categorie;
    }

    @Override
    public boolean exportXLS() {
         try {
            
            ObservableList<CategorieProduit> list = FXCollections.observableArrayList(listCateogries());
            
            File file = new File("C:\\Users\\solta\\Desktop\\ProjetPI\\src\\projet\\files\\categorie.xls");
            WritableWorkbook myexcel = Workbook.createWorkbook(file);
            WritableSheet mysheet = myexcel.createSheet("categorie", 0);
            Label id = new Label(0,0,"id");
            Label libelle = new Label(1,0,"libelle");
        
            
            mysheet.addCell(id);
            mysheet.addCell(libelle);
          
            int i = 1;
            for (CategorieProduit c : list) {
                
                id = new Label(0,i,String.valueOf(c.getId()));
                libelle = new Label(1,i,c.getLibelle());
                 
                mysheet.addCell(id);
                mysheet.addCell(libelle);

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

    @Override
    public List<CategorieProduit> listBestCategorie() {
        
          ObservableList<CategorieProduit> listcategorie= FXCollections.observableArrayList();
        
        String req = "SELECT c.*,count(*) FROM vente__produit v,produit p,categorie_produit c,sous_categorie_produit s WHERE v.produit_id = p.id AND p.souscategorie_id = s.id AND s.categorie_id = c.id GROUP BY(c.id) ORDER BY COUNT(*) DESC";
        try {
                statement = connection.createStatement();
                ResultSet res = statement.executeQuery(req);
                
                while (res.next()) {
                   CategorieProduit c = new CategorieProduit(res.getInt(1),res.getString(2), res.getString(3));
                   listcategorie.add(c);
                }

        } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }

        return listcategorie;
    }
    
    
      
	
}
