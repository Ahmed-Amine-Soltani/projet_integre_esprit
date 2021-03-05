/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.controller;

import CarrouselEvenement.GNCarousel;
import java.io.FileInputStream;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import projet.models.Evenement;
import projet.service.ServiceEvenement;

/**
 * FXML Controller class
 *
 * @author solta
 */
public class AccueilPrincipalController implements Initializable {

    @FXML
    private StackPane stackAccueil;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // carrousel inisialisation
        try {
            GNCarousel carousel = new GNCarousel();

            carousel.setArrows(true);
            carousel.setAutoRide(true);
            List<Evenement> listEvenement = new ArrayList<Evenement>();
            ResultSet resultSet = null;

            listEvenement = ServiceEvenement.afficherTtEvenement();

            for (Evenement event : listEvenement) {
                Image image = new Image(new FileInputStream("C:\\wamp64\\www\\worldfriendship\\web\\Evenement\\image\\affiches\\" + event.getAffiche_evenement()));
                ImageView affiche = new ImageView(image);

                affiche.setFitHeight(600);
                affiche.setFitWidth(600);

                affiche.getStyleClass().add("image-accueil");
                // Label lb1 = new Label(event.getNom_evenement());
                // lb1.setTextFill(Color.web("white"));
                // lb1.setFont(new Font("Arial", 30));
                // lb1.setScaleX(1.5);
                // lb1.setScaleY(1.5);

                VBox v1 = new VBox(affiche);
                v1.setAlignment(Pos.CENTER);
                v1.setStyle(" -fx-background-color: linear-gradient(to bottom, repeat, #0099ff , #66ccff  );");

                carousel.getItems().add(v1);

            }

            stackAccueil.getChildren().addAll(carousel);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
