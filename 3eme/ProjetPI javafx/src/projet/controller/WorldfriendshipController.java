/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.controller;

import CarrouselEvenement.GNCarousel;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import projet.models.Evenement;
import projet.models.Panier;
import projet.models.Utilisateur;
import projet.service.PanierService;
import projet.service.ServiceEvenement;

/**
 * FXML Controller class
 *
 * @author yassine
 */
public class WorldfriendshipController implements Initializable {

    @FXML
    private BorderPane borderpane;
    @FXML
    private HBox bl_search;
    @FXML
    private Button button_search;
    @FXML
    private Circle userImage;
    @FXML
    private Circle notification;
    @FXML
    private MenuItem user_nav;

    private boolean visible = false;

    public static Utilisateur recupererUtilisateurConnecte;

    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        
          Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/projet/presentation/AccueilPrincipal.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
       borderpane.setCenter(root);
  

        // end carrousel inisialisation
        Image image = new Image("projet/images/user.png", false);
        userImage.setFill(new ImagePattern(image));

    }

    public void setUser(Utilisateur user) {
        recupererUtilisateurConnecte = user;
        user_nav.setText(recupererUtilisateurConnecte.getNom_Utilisateur());

        PanierService service_panier = new PanierService();
        Panier panier = service_panier.getPanier();

        notification.setVisible(false);
        if (panier != null) {

            if (service_panier.getLignePanier(panier.getId()).size() > 0) {
                notification.setVisible(true);
            }
        }
    }

    @FXML
    private void produitAction(MouseEvent event) {
        loadUI("Produit");
    }

    @FXML
    private void solde(MouseEvent event) {

        loadUI("solde");
    }

    @FXML
    private void experienceAction(MouseEvent event) {
        loadUI("Home2");
    }

    @FXML
    private void forumAction(MouseEvent event) {

        loadUI("List");
    }

    @FXML
    private void evenementAction(MouseEvent event) {

        loadUI("AccueilEvenementGUI");
    }

    public void loadUI(String ui) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/projet/presentation/" + ui + ".fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(WorldfriendshipController.class.getName()).log(Level.SEVERE, null, ex);
        }

        borderpane.setCenter(root);
    }

    @FXML
    private void openPanier(MouseEvent event) throws IOException {

        if (visible == true) {
            Parent sidebar = FXMLLoader.load(getClass().getResource("/projet/presentation/SideBarPanier.fxml"));
            borderpane.setRight(sidebar);
            visible = false;
        } else {
            borderpane.setRight(null);
            visible = true;
        }

    }

    @FXML
    private void quitter(ActionEvent event) {
        // get a handle to the stage
        Stage stage = (Stage) borderpane.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @FXML
    private void AccueilAction(MouseEvent event) {
        loadUI("AccueilPrincipal");
    }

}
