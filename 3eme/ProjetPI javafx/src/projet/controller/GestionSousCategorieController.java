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
import projet.models.SousCategorieProduit;
import projet.service.SousCategorieProduitService;

/**
 * FXML Controller class
 *
 * @author yassine
 */
public class GestionSousCategorieController implements Initializable {

    @FXML
    private JFXTextField search;
    @FXML
    private TableView<SousCategorieProduit> table;
    @FXML
    private TableColumn<SousCategorieProduit, String> libelle;
    @FXML
    private TableColumn<SousCategorieProduit, String> created;
    @FXML
    private TableColumn<SousCategorieProduit, String> action;
    @FXML
    private TableColumn<SousCategorieProduit, Integer> id;
    @FXML
    private TableColumn<SousCategorieProduit, String> categorie;

    public static ObservableList<SousCategorieProduit> observableList;
    
    private SousCategorieProduitService cs = new SousCategorieProduitService();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        observableList = FXCollections.observableArrayList( cs.listSousCateogries());
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        libelle.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        categorie.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        created.setCellValueFactory(new PropertyValueFactory<>("created"));
        action.setCellValueFactory(new PropertyValueFactory<>("btn_delete"));
        
        table.setItems(observableList);
        
        table.setOnMouseClicked((MouseEvent event)->{
            SousCategorieProduit souscategorie = table.getSelectionModel().getSelectedItem();
            modifierDialog(souscategorie);
            
        });
    }    

    @FXML
    private void ajouterDialog(MouseEvent event) throws IOException {
        
        Parent root = FXMLLoader.load(getClass().getResource("/projet/presentation/AjouterSousCategorieProduit.fxml"));
        Dialog dialog = new Dialog();
        dialog.getDialogPane().setContent(root);
        dialog.initStyle(StageStyle.UNDECORATED);
        dialog.show();
    }

    private void modifierDialog(SousCategorieProduit souscategorie) {

        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/projet/presentation/ModifierSousCategorieProduit.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(GestionProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ModifierSousCategorieProduitController controller = loader.<ModifierSousCategorieProduitController>getController();
        
        try {
            controller.setData(souscategorie);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GestionSousCategorieController.class.getName()).log(Level.SEVERE, null, ex);
        }


        Dialog dialog = new Dialog();
        dialog.getDialogPane().setContent(root);
        dialog.initStyle(StageStyle.UNDECORATED);
        dialog.show();
   
    }
    
    @FXML
    private void search(KeyEvent event) {
        
        String libelle = search.getText();
           
        ObservableList<SousCategorieProduit> listsouscategorie = cs.search(libelle);
        
        observableList.clear();
        observableList.addAll(listsouscategorie);
    }

    @FXML
    private void exportXLS(MouseEvent event) {
        
        if(cs.exportXLS()){
           try {
               Runtime.getRuntime().exec("rundll32 url.dll, FileProtocolHandler " + "C:\\Users\\lhbya\\OneDrive\\Documents\\NetBeansProjects\\Projet\\src\\projet\\files\\souscategorie.xls");
           } catch (IOException ex) {
                Logger.getLogger(GestionSousCategorieController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    
    
}
