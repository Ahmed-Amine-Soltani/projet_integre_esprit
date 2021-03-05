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
import java.util.List;
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
import projet.models.Produit;
import projet.utils.DbConnection;

/**
 *
 * @author yassine
 */
public class ProduitService implements IProduitService{
    
    static Statement statement = null;
    PreparedStatement pst;

    DbConnection cnx = DbConnection.getInstance();
    Connection connection = cnx.getConnection();
	
    
  
    @Override
    public ObservableList<Produit> listProduits() {
        ObservableList<Produit> listproduits = FXCollections.observableArrayList();
       
        String req = "SELECT p.*,s.libelle FROM produit p ,sous_categorie_produit s WHERE p.souscategorie_id=s.id ORDER BY(p.id) DESC";
	
        try {
                statement = connection.createStatement();
                ResultSet res = statement.executeQuery(req);
                
                while (res.next()) {
                   Produit p = new Produit(res.getInt(1),res.getInt(2), res.getString(3),res.getString(4),res.getFloat(5),res.getInt(6),res.getString(11),res.getString(7),res.getString(8),res.getString(9),res.getString(12),res.getTimestamp(13));
                   p.setBtn_delete(new Button("Supprimer"));
                   p.setSouscategorie(res.getString(16));
                   listproduits.add(p);
                }

        } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }

        return listproduits;
    }

    @Override
    public boolean ajouterProduit(Produit p) {
        
        String req = "INSERT INTO produit(souscategorie_id,libelle,description,prix,stock,firstimg,secondimg,thirdimg,taille,color,created,updated_at) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            pst = connection.prepareStatement(req);	
            pst.setInt(1, p.getSouscategorie_id());
            pst.setString(2, p.getLibelle());
            pst.setString(3, p.getDescription());
            pst.setDouble(4, p.getPrix());
            pst.setInt(5, p.getStock());
            pst.setString(6, p.getFirstimg());
            pst.setString(7, p.getSecondimg());
            pst.setString(8, p.getThirdimg());
            pst.setString(9, "none");
            pst.setString(10, p.getColor());
            pst.setTimestamp(11, p.getCreated());
            pst.setTimestamp(12, p.getCreated());


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
    public boolean modifierProduit(Produit p) {
         String req = "UPDATE produit SET souscategorie_id= ?,libelle= ?,description= ?,prix= ?,stock= ?,firstimg= ?,secondimg= ?,thirdimg= ?,color= ? WHERE id= ?";
        try {
            pst = connection.prepareStatement(req);	
            pst.setInt(1, p.getSouscategorie_id());
            pst.setString(2, p.getLibelle());
            pst.setString(3, p.getDescription());
            pst.setDouble(4, p.getPrix());
            pst.setInt(5, p.getStock());
            pst.setString(6, p.getFirstimg());
            pst.setString(7, p.getSecondimg());
            pst.setString(8, p.getThirdimg());
            pst.setString(9, p.getColor());
            pst.setInt(10, p.getId());
         


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
    public boolean supprimerProduit(int id) {
          
        String req="DELETE FROM produit WHERE id="+id;
              
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
    public ObservableList<Produit> search(String libelle) {
        
        ObservableList<Produit> listproduits = FXCollections.observableArrayList();
        
        String req = "SELECT p.*,s.libelle FROM produit p ,sous_categorie_produit s WHERE p.souscategorie_id=s.id AND p.libelle LIKE '%"+libelle+"%' ORDER BY(p.id) DESC";
	
        try {
                statement = connection.createStatement();
                ResultSet res = statement.executeQuery(req);
                
                while (res.next()) {
                   Produit p = new Produit(res.getInt(1),res.getInt(2), res.getString(3),res.getString(4),res.getFloat(5),res.getInt(6),res.getString(11),res.getString(7),res.getString(8),res.getString(9),res.getString(12),res.getTimestamp(13));
                   p.setBtn_delete(new Button("Supprimer"));
                   p.setSouscategorie(res.getString(16));
                   listproduits.add(p);
                }

        } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }

        return listproduits;
    }

    @Override
    public Produit getProduit(int id) {
        
        Produit produit= null;
        
        String req = "SELECT * FROM produit WHERE id ="+id;
	
        try {
                statement = connection.createStatement();
                ResultSet res = statement.executeQuery(req);
                
                while (res.next()) {
                   produit = new Produit(res.getInt(1),res.getInt(2), res.getString(3),res.getString(4),res.getFloat(5),res.getInt(6),res.getString(11),res.getString(7),res.getString(8),res.getString(9),res.getString(12),res.getTimestamp(13));
                }

        } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }

        return produit;
    }

    @Override
    public boolean exportXLS() {
        try {
            
            ObservableList<Produit> listproduits = FXCollections.observableArrayList(listProduits());
            
            File file = new File("C:\\Users\\solta\\Desktop\\ProjetPI\\src\\projet\\files\\produit.xls");
            WritableWorkbook myexcel = Workbook.createWorkbook(file);
            WritableSheet mysheet = myexcel.createSheet("produit", 0);
            Label id = new Label(0,0,"id");
            Label libelle = new Label(1,0,"libelle");
            Label model = new Label(2,0,"model");
            Label desc = new Label(3,0,"description");
            Label prix = new Label(4,0,"prix");
            Label stock = new Label(5,0,"stock");
            Label taille = new Label(6,0,"taille");
            Label created = new Label(7,0,"created");
            
            mysheet.addCell(id);
            mysheet.addCell(libelle);
            mysheet.addCell(model);
            mysheet.addCell(desc);
            mysheet.addCell(prix);
            mysheet.addCell(stock);
            mysheet.addCell(taille);
            mysheet.addCell(created);
          
            int i = 1;
            for (Produit produit : listproduits) {
                
                id = new Label(0,i,String.valueOf(produit.getId()));
                libelle = new Label(1,i,produit.getLibelle());
                model = new Label(2,i,produit.getSouscategorie());
                desc = new Label(3,i,produit.getDescription());
                prix = new Label(4,i,String.valueOf(produit.getPrix()));
                stock = new Label(5,i,String.valueOf(produit.getStock()));
                taille = new Label(6,i,String.valueOf(produit.getTaille()));
                created = new Label(7,i,String.valueOf(produit.getCreated()));
                
                mysheet.addCell(id);
                mysheet.addCell(libelle);
                mysheet.addCell(model);
                mysheet.addCell(desc);
                mysheet.addCell(prix);
                mysheet.addCell(stock);
                mysheet.addCell(taille);
                mysheet.addCell(created);

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
    public List<Produit> getListProduitsFilter(String filter,String categorie,String sous_categorie,String tri) {
        
        ObservableList<Produit> listproduits = FXCollections.observableArrayList();
        String req = null;

        switch (filter) {
            case "new":
                if(categorie == null){
                    
                    if(null == tri){
                        req = "SELECT p.* FROM produit p ,sous_categorie_produit s WHERE p.souscategorie_id=s.id ORDER BY(p.id) DESC";
                    }else switch (tri) {
                        case "nom_asc":
                            req = "SELECT p.* FROM produit p ,sous_categorie_produit s WHERE p.souscategorie_id=s.id ORDER BY(p.libelle) ASC";
                            break;
                        case "nom_desc":
                            req = "SELECT p.* FROM produit p ,sous_categorie_produit s WHERE p.souscategorie_id=s.id ORDER BY(p.libelle) DESC";
                            break;
                        case "prix_asc":
                            req = "SELECT p.* FROM produit p ,sous_categorie_produit s WHERE p.souscategorie_id=s.id ORDER BY(p.prix) ASC";
                            break;
                        case "prix_desc":
                            req = "SELECT p.* FROM produit p ,sous_categorie_produit s WHERE p.souscategorie_id=s.id ORDER BY(p.prix) DESC";
                            break;
                        default:
                            break;
                    }
                    
                }else {
                    
                    if(sous_categorie == null){
                        
                        if(null == tri){
                            req = "SELECT p.* FROM produit p ,sous_categorie_produit s,categorie_produit c WHERE p.souscategorie_id=s.id AND c.id = s.categorie_id AND c.libelle = '"+categorie+"' ORDER BY(p.id) DESC";
                        }else switch (tri) {
                            case "nom_asc":
                                req = "SELECT p.* FROM produit p ,sous_categorie_produit s,categorie_produit c WHERE p.souscategorie_id=s.id AND c.id = s.categorie_id AND c.libelle = '"+categorie+"' ORDER BY(p.libelle) ASC";
                                break;
                            case "nom_desc":
                                req = "SELECT p.* FROM produit p ,sous_categorie_produit s,categorie_produit c WHERE p.souscategorie_id=s.id AND c.id = s.categorie_id AND c.libelle = '"+categorie+"' ORDER BY(p.libelle) DESC";
                                break;
                            case "prix_asc":
                                req = "SELECT p.* FROM produit p ,sous_categorie_produit s,categorie_produit c WHERE p.souscategorie_id=s.id AND c.id = s.categorie_id AND c.libelle = '"+categorie+"' ORDER BY(p.prix) ASC";
                                break;
                            case "prix_desc":
                                req = "SELECT p.* FROM produit p ,sous_categorie_produit s,categorie_produit c WHERE p.souscategorie_id=s.id AND c.id = s.categorie_id AND c.libelle = '"+categorie+"' ORDER BY(p.prix) DESC";
                                break;
                            default:
                                break;
                        }
                        
                    }else {
                        
                        if(null == tri){
                            req = "SELECT p.* FROM produit p ,sous_categorie_produit s WHERE p.souscategorie_id=s.id AND s.libelle = '"+sous_categorie+"' ORDER BY(p.id) DESC";
                        }else switch (tri) {
                            case "nom_asc":
                                req = "SELECT p.* FROM produit p ,sous_categorie_produit s WHERE p.souscategorie_id=s.id AND s.libelle = '"+sous_categorie+"' ORDER BY(p.libelle) ASC";
                                break;
                            case "nom_desc":
                                req = "SELECT p.* FROM produit p ,sous_categorie_produit s WHERE p.souscategorie_id=s.id AND s.libelle = '"+sous_categorie+"' ORDER BY(p.libelle) DESC";
                                break;
                            case "prix_asc":
                                req = "SELECT p.* FROM produit p ,sous_categorie_produit s WHERE p.souscategorie_id=s.id AND s.libelle = '"+sous_categorie+"' ORDER BY(p.prix) ASC";
                                break;
                            case "prix_desc":
                                req = "SELECT p.* FROM produit p ,sous_categorie_produit s WHERE p.souscategorie_id=s.id AND s.libelle = '"+sous_categorie+"' ORDER BY(p.prix) DESC";
                                break;
                            default:
                                break;
                        }
                    }
                }   break;
                
            case "best":
                if(categorie == null){
                    
                    if(null == tri){
                        req = "SELECT p.*,SUM(v.nbr_produit) FROM produit p ,vente__produit v WHERE v.produit_id = p.id GROUP BY(p.id) ORDER BY SUM(v.nbr_produit) DESC ";
                    }else switch (tri) {
                        case "nom_asc":
                            req = "SELECT p.*,SUM(v.nbr_produit) FROM produit p ,vente__produit v WHERE v.produit_id = p.id GROUP BY(p.id) ORDER BY (p.libelle) ASC ";
                            break;
                        case "nom_desc":
                            req = "SELECT p.*,SUM(v.nbr_produit) FROM produit p ,vente__produit v WHERE v.produit_id = p.id GROUP BY(p.id) ORDER BY (p.libelle) DESC ";
                            break;
                        case "prix_asc":
                            req = "SELECT p.*,SUM(v.nbr_produit) FROM produit p ,vente__produit v WHERE v.produit_id = p.id GROUP BY(p.id) ORDER BY (p.prix) ASC ";
                            break;
                        case "prix_desc":
                            req = "SELECT p.*,SUM(v.nbr_produit) FROM produit p ,vente__produit v WHERE v.produit_id = p.id GROUP BY(p.id) ORDER BY (p.prix) DESC ";
                            break;
                        default:
                            break;
                    }

                }else{
                    
                    if(sous_categorie == null){
                         if(null == tri){
                            req = "SELECT p.*,SUM(v.nbr_produit) FROM produit p ,vente__produit v,sous_categorie_produit s,categorie_produit c WHERE v.produit_id = p.id AND "
                                + "p.souscategorie_id=s.id AND c.id = s.categorie_id AND c.libelle = '"+categorie+"' GROUP BY(p.id) ORDER BY SUM(v.nbr_produit) DESC ";
                         }else switch (tri) {
                            case "nom_asc":
                                req = "SELECT p.*,SUM(v.nbr_produit) FROM produit p ,vente__produit v,sous_categorie_produit s,categorie_produit c WHERE v.produit_id = p.id AND "
                                     + "p.souscategorie_id=s.id AND c.id = s.categorie_id AND c.libelle = '"+categorie+"' GROUP BY(p.id) ORDER BY (p.libelle) ASC ";                 
                                break;
                            case "nom_desc":
                                req = "SELECT p.*,SUM(v.nbr_produit) FROM produit p ,vente__produit v,sous_categorie_produit s,categorie_produit c WHERE v.produit_id = p.id AND "
                                     + "p.souscategorie_id=s.id AND c.id = s.categorie_id AND c.libelle = '"+categorie+"' GROUP BY(p.id) ORDER BY (p.libelle) DESC ";                    
                                break;
                            case "prix_asc":
                                req = "SELECT p.*,SUM(v.nbr_produit) FROM produit p ,vente__produit v,sous_categorie_produit s,categorie_produit c WHERE v.produit_id = p.id AND "
                                     + "p.souscategorie_id=s.id AND c.id = s.categorie_id AND c.libelle = '"+categorie+"' GROUP BY(p.id) ORDER BY (p.prix) ASC ";                        
                                break;
                            case "prix_desc":
                                req = "SELECT p.*,SUM(v.nbr_produit) FROM produit p ,vente__produit v,sous_categorie_produit s,categorie_produit c WHERE v.produit_id = p.id AND "
                                     + "p.souscategorie_id=s.id AND c.id = s.categorie_id AND c.libelle = '"+categorie+"' GROUP BY(p.id) ORDER BY (p.prix) DESC ";                          
                                break;
                            default:
                                break;
                         }
                    }else {
                         if(null == tri){
                            req = "SELECT p.*,SUM(v.nbr_produit) FROM produit p ,vente__produit v,sous_categorie_produit s WHERE v.produit_id = p.id AND "
                                + "p.souscategorie_id=s.id AND s.libelle = '"+sous_categorie+"' GROUP BY(p.id) ORDER BY SUM(v.nbr_produit) DESC ";             
                         }else switch (tri) {
                            case "nom_asc":
                                req = "SELECT p.* FROM produit p ,vente__produit v,sous_categorie_produit s WHERE v.produit_id = p.id AND "
                                 + "p.souscategorie_id=s.id AND s.libelle = '"+sous_categorie+"' GROUP BY(p.id) ORDER BY (p.libelle) ASC ";             
                                break;
                            case "nom_desc":
                                req = "SELECT p.* FROM produit p ,vente__produit v,sous_categorie_produit s WHERE v.produit_id = p.id AND "
                                    + "p.souscategorie_id=s.id AND s.libelle = '"+sous_categorie+"' GROUP BY(p.id) ORDER BY (p.libelle) DESC ";             
                                break;
                            case "prix_asc":
                                req = "SELECT p.*,SUM(v.nbr_produit) FROM produit p ,vente__produit v,sous_categorie_produit s WHERE v.produit_id = p.id AND "
                                    + "p.souscategorie_id=s.id AND s.libelle = '"+sous_categorie+"' GROUP BY(p.id) ORDER BY (p.prix) ASC ";             
                                break;
                            case "prix_desc":
                                req = "SELECT p.*,SUM(v.nbr_produit) FROM produit p ,vente__produit v,sous_categorie_produit s WHERE v.produit_id = p.id AND "
                                     + "p.souscategorie_id=s.id AND s.libelle = '"+sous_categorie+"' GROUP BY(p.id) ORDER BY (p.prix) DESC ";             
                                break;
                            default:
                                break;
                         }
                    }
                } 
                break;
                
            case "all":
                if(categorie == null){

                    if(null == tri){
                        req = "SELECT p.* FROM produit p ,sous_categorie_produit s WHERE p.souscategorie_id=s.id ORDER BY RAND()";
                    }else switch (tri) {
                        case "nom_asc":
                            req = "SELECT p.* FROM produit p ,sous_categorie_produit s WHERE p.souscategorie_id=s.id ORDER BY (p.libelle) ASC";
                            break;
                        case "nom_desc":
                            req = "SELECT p.* FROM produit p ,sous_categorie_produit s WHERE p.souscategorie_id=s.id ORDER BY (p.libelle) DESC";
                            break;
                        case "prix_asc":
                            req = "SELECT p.* FROM produit p ,sous_categorie_produit s WHERE p.souscategorie_id=s.id ORDER BY (p.prix) ASC";
                            break;
                        case "prix_desc":
                            req = "SELECT p.* FROM produit p ,sous_categorie_produit s WHERE p.souscategorie_id=s.id ORDER BY (p.prix) DESC";
                            break;
                        default:
                            break;
                    }

                }else {
                    
                    if(sous_categorie == null){

                        if(null == tri){
                              req = "SELECT p.* FROM produit p ,sous_categorie_produit s,categorie_produit c WHERE p.souscategorie_id=s.id AND c.id = s.categorie_id AND c.libelle = '"+categorie+"' ORDER BY RAND()";
                        }else switch (tri) {
                            case "nom_asc":
                                req = "SELECT p.* FROM produit p ,sous_categorie_produit s,categorie_produit c WHERE p.souscategorie_id=s.id AND c.id = s.categorie_id AND c.libelle = '"+categorie+"' ORDER BY (p.libelle) ASC";
                                break;
                            case "nom_desc":
                                req = "SELECT p.* FROM produit p ,sous_categorie_produit s,categorie_produit c WHERE p.souscategorie_id=s.id AND c.id = s.categorie_id AND c.libelle = '"+categorie+"' ORDER BY (p.libelle) DESC";
                                break;
                            case "prix_asc":
                                req = "SELECT p.* FROM produit p ,sous_categorie_produit s,categorie_produit c WHERE p.souscategorie_id=s.id AND c.id = s.categorie_id AND c.libelle = '"+categorie+"' ORDER BY (p.prix) ASC";
                                break;
                            case "prix_desc":
                                req = "SELECT p.* FROM produit p ,sous_categorie_produit s,categorie_produit c WHERE p.souscategorie_id=s.id AND c.id = s.categorie_id AND c.libelle = '"+categorie+"' ORDER BY (p.prix) DESC";
                                break;
                            default:
                                break;
                        }
                    }else {
                        
                        if(null == tri){
                               req = "SELECT p.* FROM produit p ,sous_categorie_produit s WHERE p.souscategorie_id=s.id AND s.libelle = '"+sous_categorie+"' ORDER BY RAND()";
                        }else switch (tri) {
                            case "nom_asc":
                                req = "SELECT p.* FROM produit p ,sous_categorie_produit s WHERE p.souscategorie_id=s.id AND s.libelle = '"+sous_categorie+"' ORDER BY (p.libelle) ASC";
                                break;
                            case "nom_desc":
                                req = "SELECT p.* FROM produit p ,sous_categorie_produit s WHERE p.souscategorie_id=s.id AND s.libelle = '"+sous_categorie+"' ORDER BY (p.libelle) DESC";
                                break;
                            case "prix_asc":
                                req = "SELECT p.* FROM produit p ,sous_categorie_produit s WHERE p.souscategorie_id=s.id AND s.libelle = '"+sous_categorie+"' ORDER BY (p.prix) ASC";
                                break;
                            case "prix_desc":
                                req = "SELECT p.* FROM produit p ,sous_categorie_produit s WHERE p.souscategorie_id=s.id AND s.libelle = '"+sous_categorie+"' ORDER BY (p.prix) DESC";
                                break;
                            default:
                                break;
                        }
                    }
                }   break;
            default:
                break;
        }
	
        try {
                statement = connection.createStatement();
                ResultSet res = statement.executeQuery(req);
                
                while (res.next()) {
                   Produit p = new Produit(res.getInt(1),res.getInt(2), res.getString(3),res.getString(4),res.getFloat(5),res.getInt(6),res.getString(11),res.getString(7),res.getString(8),res.getString(9),res.getString(12),res.getTimestamp(13));
                   listproduits.add(p);
                }

        } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }

        return listproduits;
    }

    
    
}
