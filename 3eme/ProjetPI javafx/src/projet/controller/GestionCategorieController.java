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
import projet.models.CategorieProduit;
import projet.service.CategorieProduitService;

/**
 * FXML Controller class
 *
 * @author yassine
 */
public class GestionCategorieController implements Initializable {

    @FXML
    private JFXTextField search;
    @FXML
    private TableView<CategorieProduit> table;
    @FXML
    private TableColumn<CategorieProduit, String> libelle;
    @FXML
    private TableColumn<CategorieProduit, String> image;
    @FXML
    private TableColumn<CategorieProduit, String> action;
    @FXML
    private TableColumn<CategorieProduit, Integer> id;
        
    public static ObservableList<CategorieProduit> observableList;
    
    private CategorieProduitService cs = new CategorieProduitService();  



    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        observableList = FXCollections.observableArrayList( cs.listCateogries());
        
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        libelle.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        image.setCellValueFactory(new PropertyValueFactory<>("image"));
        action.setCellValueFactory(new PropertyValueFactory<>("btn_delete"));
        table.setItems(observableList);
        
        table.setOnMouseClicked((MouseEvent event)->{
            CategorieProduit categorie = table.getSelectionModel().getSelectedItem();
            modifierDialog(categorie);
            
        });
    }    

    @FXML
    private void ajouterDialog(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/projet/presentation/AjouterCategorieProduit.fxml"));
        Dialog dialog = new Dialog();
        dialog.getDialogPane().setContent(root);
        dialog.initStyle(StageStyle.UNDECORATED);
        dialog.show();
    }
    
         private void modifierDialog(CategorieProduit categorie) {
     
        
 
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/projet/presentation/ModifierCategorieProduit.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(GestionProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ModifierCategorieProduitController controller = loader.<ModifierCategorieProduitController>getController();
      
        try {
            controller.setData(categorie);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GestionCategorieController.class.getName()).log(Level.SEVERE, null, ex);
        }

        Dialog dialog = new Dialog();
        dialog.getDialogPane().setContent(root);
        dialog.initStyle(StageStyle.UNDECORATED);
        dialog.show();
   
       
     
    }


    @FXML
    private void search(KeyEvent event) {
        
        String libelle = search.getText();
           
        ObservableList<CategorieProduit> listCategorie = cs.search(libelle);
        
        observableList.clear();
        observableList.addAll(listCategorie);
    }

    @FXML
    private void exportXLS(MouseEvent event) {
        
        if(cs.exportXLS()){
           try {
               Runtime.getRuntime().exec("rundll32 url.dll, FileProtocolHandler " + "C:\\Users\\lhbya\\OneDrive\\Documents\\NetBeansProjects\\Projet\\src\\projet\\files\\categorie.xls");
           } catch (IOException ex) {
                Logger.getLogger(GestionSousCategorieController.class.getName()).log(Level.SEVERE, null, ex);
            }
       }
    }
    
    
}
