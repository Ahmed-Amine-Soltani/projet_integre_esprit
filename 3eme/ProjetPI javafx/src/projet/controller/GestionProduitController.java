/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.controller;

import com.jfoenix.controls.JFXTextField;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.StageStyle;
import projet.models.Produit;
import projet.service.ProduitService;

/**
 * FXML Controller class
 *
 * @author yassine
 */
public class GestionProduitController implements Initializable {
    
  
    @FXML
    private TableView<Produit> table;

    @FXML
    private TableColumn<Produit, String> libelle;
    
    @FXML
    private TableColumn<Produit, String> model;
    
    @FXML
    private TableColumn<Produit, String>  description;

    @FXML
    private TableColumn<Produit, Float>  prix;

    @FXML
    private TableColumn<Produit, Integer>  stock;
    
    @FXML
    private JFXTextField search;

    @FXML
    private TableColumn<Produit, String> created;
    
    @FXML
    private TableColumn<Produit, String> action;

    public static ObservableList<Produit> observableList;
    
    private ProduitService ps = new ProduitService();  

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        observableList = FXCollections.observableArrayList( ps.listProduits());
        libelle.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        model.setCellValueFactory(new PropertyValueFactory<>("souscategorie"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        prix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        created.setCellValueFactory(new PropertyValueFactory<>("created"));
        action.setCellValueFactory(new PropertyValueFactory<>("btn_delete"));
        table.setItems(observableList);
        
        table.setOnMouseClicked((MouseEvent event)->{
            Produit produit = table.getSelectionModel().getSelectedItem();
            modifierDialog(produit);
            
        });
        
       

    }    
   
    @FXML
    private void ajouterDialog(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/projet/presentation/AjouterProduit.fxml"));
        Dialog dialog = new Dialog();
        dialog.getDialogPane().setContent(root);
        dialog.initStyle(StageStyle.UNDECORATED);
        dialog.show();
     
    }
    
     private void modifierDialog(Produit produit) {
     
        
 
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/projet/presentation/ModifierProduit.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(GestionProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ModifierProduitController controller = loader.<ModifierProduitController>getController();
        try {
            controller.setData(produit);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GestionProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }

        Dialog dialog = new Dialog();
        dialog.getDialogPane().setContent(root);
        dialog.initStyle(StageStyle.UNDECORATED);
        dialog.show();
   
       
     
    }

    @FXML
    private void search(KeyEvent event) {
        
        String libelle = search.getText();
           
        ObservableList<Produit> listproduits = ps.search(libelle);
        
        observableList.clear();
        observableList.addAll(listproduits);
    }

    @FXML
    private void exportXLS(MouseEvent event) {
        
       if(ps.exportXLS()){
           try {
               Runtime.getRuntime().exec("rundll32 url.dll, FileProtocolHandler " + "C:\\Users\\lhbya\\OneDrive\\Documents\\NetBeansProjects\\Projet\\src\\projet\\files\\produit.xls");
           } catch (IOException ex) {
               Logger.getLogger(GestionProduitController.class.getName()).log(Level.SEVERE, null, ex);
           }

       }
        
    }
    
}
