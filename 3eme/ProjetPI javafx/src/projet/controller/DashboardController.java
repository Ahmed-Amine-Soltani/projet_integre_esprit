/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.MenuButton;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import projet.models.Utilisateur;
import projet.service.PanierService;

/**
 * FXML Controller class
 *
 * @author yassine
 */
public class DashboardController implements Initializable {

    @FXML
    private BorderPane borderpane;
    @FXML
    private Circle circleImage;
    @FXML
    private Circle notification;
    @FXML
    private MenuButton user_nav;
    
    public static Utilisateur recupererUtilisateurConnecte;

    private PanierService service = new PanierService();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
       Image image = new Image("projet/images/user.png",false);
       circleImage.setFill(new ImagePattern(image));
       Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/projet/presentation/HomeDashboard.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
       borderpane.setCenter(root);
       System.out.print(service.visitCount());
       if (service.visitCount() == 0){
         notification.setVisible(false);
       }
       
    }    

    public void setUser(Utilisateur user){
        recupererUtilisateurConnecte = user;
        user_nav.setText(recupererUtilisateurConnecte.getNom_Utilisateur());
    }
  
    @FXML
    private void homedashboard(MouseEvent event) {
        loadUI("HomeDashboard");
    }

    @FXML
    private void evenement(MouseEvent event) {
        loadUI("DashBoardEvenementGUI");
    }

    @FXML
    private void forum(MouseEvent event) {
        loadUI("CategorieForum");
    }

    @FXML
    private void experience(MouseEvent event) {
        loadUI("ListExperience");
    }

    @FXML
    private void produit(MouseEvent event) {
        loadUI("GestionProduit");
    }
    
    @FXML
    private void categorieProduit(MouseEvent event) {
        loadUI("GestionCategorie");
    }
   @FXML
    private void solde(MouseEvent event) {
         loadUI("FXML");
    }

    @FXML
    private void sousCategorieProduit(MouseEvent event) {
        loadUI("GestionSousCategorie");
    }
    
    @FXML
    private void commande(MouseEvent event) {
        loadUI("gestionCommande");
        
        if(service.visitCommande()){
            notification.setVisible(false);
        }
        
    }
    @FXML
    private void question(MouseEvent event) {
        loadUI("ListQuestion");
    }

    @FXML
    private void commentaire(MouseEvent event) {
        loadUI("ListComentaire");
    }

    private void loadUI(String ui){
       
       Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/projet/presentation/"+ui+".fxml"));
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
       borderpane.setCenter(root);
    }

    @FXML
    private void quitter(ActionEvent event) {
        // get a handle to the stage
        Stage stage = (Stage) borderpane.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

   

 
   
    

    

   
    
}
