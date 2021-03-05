/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import projet.models.Experience;
import projet.service.ExperienceService;

/**
 * FXML Controller class
 *
 * @author houba
 */
public class AjouterExperienceController implements Initializable {

    @FXML
    private Label errorsCategorie;
    @FXML
    private Label errorsSousCategorie;
    @FXML
    private Label errorsLibelle;
    @FXML
    private Label errorsPrix;
    @FXML
    private Label errorsStock;
    @FXML
    private Label errorsDescription;
    @FXML
    private ImageView firstImage;
    @FXML
    private Label errorsFirstImg;
    @FXML
    private ImageView secondImage;
    @FXML
    private Label errorsSecondImg;
    @FXML
    private ImageView ThirdImage;
    @FXML
    private Label errorsThirdImg;
    @FXML
    private JFXDatePicker date;
    @FXML
    private JFXComboBox<String> categorie;
    @FXML
    private JFXTextField nom;
    @FXML
    private JFXTextField lieu;
    @FXML
    private JFXTextArea desc;
    @FXML
    private JFXButton btn_partager;


    private FileChooser filechooser;
    private File file;
    private ExperienceService service = new ExperienceService();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        HashMap<String, Integer> mapCategorie = service.getCategorie();

        for (String s : mapCategorie.keySet()) {
            categorie.getItems().add(s);
        }
    }


    @FXML
    private void uploadImage(ActionEvent event) {
        filechooser = new FileChooser();
        filechooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("image files", "*.png", "*.jpg", "*.jpeg"));

        file = filechooser.showOpenDialog(null);
    }

    @FXML
    private void ajouter(ActionEvent event) throws IOException {
        String datee = date.getValue().toString();

        Path sourceDirectory = Paths.get(file.getAbsolutePath());
        Path targetDirectory = Paths.get("C:\\wamp64\\www\\worldfriendship\\web\\assets\\img\\experience\\" + file.getName());
        //copy source to target using Files Class
        Files.copy(sourceDirectory, targetDirectory);

        HashMap<String, Integer> mapCategorie = service.getCategorie();
        int id = mapCategorie.get(categorie.getValue());
        Experience e = new Experience(WorldfriendshipController.recupererUtilisateurConnecte.getId_Utilisateur() ,nom.getText(), desc.getText(), lieu.getText(), datee, id, file.getName());
        ExperienceService experienceService = new ExperienceService();
        experienceService.insert(e);

        
        new Alert(Alert.AlertType.INFORMATION, "Partagée avec succès!").show();
    }


}
