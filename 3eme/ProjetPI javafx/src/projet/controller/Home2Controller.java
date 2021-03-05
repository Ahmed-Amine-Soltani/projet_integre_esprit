/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.controller;

import com.jfoenix.controls.JFXTextField;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import projet.models.Experience;
import projet.models.Utilisateur;
import projet.service.ExperienceService;

/**
 * FXML Controller class
 *
 * @author houba
 */
public class Home2Controller implements Initializable {

    public static Utilisateur recupererUtilisateurConnecte;

    @FXML
    private HBox home;
    private JFXTextField rechercheBar;
    private Label nomUtilisateurConnecte;
    @FXML
    private BorderPane borderpane;
    private Circle circleImage;
    @FXML
    private Circle notification;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/projet/presentation/Home.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        borderpane.setCenter(root);

        ExperienceService experienceService = new ExperienceService();

        ArrayList<Experience> arrayList = experienceService.selectAll();

        //nomUtilisateurConnecte.setText(recupererUtilisateurConnecte.getNom_Utilisateur());
        int i = 0;

        HBox hbox = new HBox();

        for (Experience exp : arrayList) {

            try {
                if (i % 4 == 0) {
                    hbox = new HBox();
                    hbox.getStyleClass().add("row");

                    home.getChildren().add(hbox);
                }

                Image image = new Image(new FileInputStream("C:\\wamp64\\www\\worldfriendship\\web\\assets\\img\\experience\\" + exp.getImage_exp()));
                ImageView img = new ImageView(image);
                //ImageView img = new ImageView("images/hiking.jpg");
                img.setFitWidth(250);
                img.setFitHeight(125);

                Label label = new Label(exp.getNom());
                Label label2 = new Label(exp.getLieu());
                HBox detail = new HBox( label, label2);
                detail.getStyleClass().add("detail_btn");
                VBox vb = new VBox(img, detail);
                Button btn = new Button("", vb);

                hbox.getChildren().add(btn);
                i++;
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Home2Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void ajouter(ActionEvent event) throws IOException {

    }

    public void recuperer(ActionEvent event) {
        nomUtilisateurConnecte.setText(recupererUtilisateurConnecte.getNom_Utilisateur());
    }

    private void loadUI(String ui) {

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/projet/presentation/" + ui + ".fxml"));
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        borderpane.setCenter(root);
    }

    @FXML
    private void experience(MouseEvent event) {
        loadUI("Home");
    }

    @FXML
    private void categorieExp(MouseEvent event) {
        loadUI("TopExperiences");
    }

    @FXML
    private void ajouterExp(MouseEvent event) {
        loadUI("AjouterExperience");
    }

    @FXML
    private void mesExp(MouseEvent event) {
        loadUI("MesExperiences");
    }

}
