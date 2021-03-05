/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.controller;

import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import projet.models.categorie;
import projet.service.CategorieService;

/**
 *
 * @author Moez
 */
public class AjouterController implements Initializable {

    public TableView<categorie> listeCategorie;
    @FXML
    private Label errorsNom;
     
    @FXML
    private Button closeButton;
    @FXML
    private JFXTextField nom;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void ajouter(ActionEvent event) throws IOException {
        
        if (nom.getText().equals("")) {
            errorsNom.setText("Saisir un Nom");
        } else {
        
        categorie c=new categorie(nom.getText());
        CategorieService categorieService=new CategorieService();
        categorieService.insert(c);
        
        new Alert(Alert.AlertType.INFORMATION, "sucess").show();
        
        
        }
    }
    
    @FXML
    private void closeButtonAction(ActionEvent event) {
        // get a handle to the stage
        Stage stage = (Stage) closeButton.getScene().getWindow();
        // do what you have to do

        stage.close();
    }
    
    private void titleEvent(KeyEvent event) {
        errorsNom.setText("");

    }

    
}
