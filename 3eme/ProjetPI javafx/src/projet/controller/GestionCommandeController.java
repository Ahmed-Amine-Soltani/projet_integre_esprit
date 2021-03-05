/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import projet.models.Commande;
import projet.service.PanierService;

/**
 * FXML Controller class
 *
 * @author yassine
 */
public class GestionCommandeController implements Initializable {

    @FXML
    private TableView<Commande> table;
   
    @FXML
    private TableColumn<Commande, String> total;
    @FXML
    private TableColumn<Commande, String> created;
    @FXML
    private TableColumn<Commande, String> file;

    public static ObservableList<Commande> observableList;
    @FXML
    private TableColumn<?, ?> payer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        PanierService ps = new PanierService();
        observableList = FXCollections.observableArrayList(ps.getAllCommande());
   
        total.setCellValueFactory(new PropertyValueFactory<>("total"));
        created.setCellValueFactory(new PropertyValueFactory<>("created"));
        file.setCellValueFactory(new PropertyValueFactory<>("btn_file"));
        payer.setCellValueFactory(new PropertyValueFactory<>("payer"));
        table.setItems(observableList);
        
       
    }    
    

    
    
}
