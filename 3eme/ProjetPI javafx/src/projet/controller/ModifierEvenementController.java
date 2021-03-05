package projet.controller;

import projet.models.Evenement;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.events.JFXDialogEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.PopOver;
import org.controlsfx.control.textfield.TextFields;
import projet.service.ServiceEvenement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author solta
 */
public class ModifierEvenementController implements Initializable {

    @FXML
    public StackPane modifierEvenementStackPane;
    /**
     * modifier evenement *
     */
    @FXML
    public JFXComboBox<String> type_evenement_fx;

    public static int id_evenement;
    public static String nomAfficher;

    @FXML
    public JFXTextField nom_evenement_fx;

    @FXML
    public JFXTextField lieu_evenement_fx;

    @FXML
    public JFXTextField description_evenement_fx;

    @FXML
    public JFXTextField capacite_evenement_fx;
    @FXML
    public JFXDatePicker date_debut_evenement_fx;
    @FXML
    public JFXDatePicker date_fin_evenement_fx;
    @FXML
    public ImageView imageView;
    public File file = null;
    @FXML
    public AnchorPane GUI;

    /**
     * end modifier evenement *
     */
    ObservableList<String> dataTypeEvenement = FXCollections.observableArrayList("Associatif", "Culturel", "Autres");
    public FileChooser fileChooser;

    @FXML
    public Stage stage;

    public Image image;

    @FXML
    private Button btnBrowser;

    @FXML
    private Button modifierButton;

    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            // pour le fileChooser
            fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All files", "*.*"),
                    new FileChooser.ExtensionFilter("Images", "*.*"),
                    new FileChooser.ExtensionFilter("Text File", "*.txt*"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            type_evenement_fx.setValue("Choisissez");
            type_evenement_fx.setItems(dataTypeEvenement);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // auto complete lieu en utilisant la librerie controlfx
        try {
            String[] listeGouvernerat = {"Ariana", "Beja", "Ben Arous", "Bizerte", "Gabes", "Gafsa", "Jendouba",
                "Kairouan", "Kasserine", "Kebili", "Le Kef", "Mahdia", "La Manouba", "Medenine", "Monastir",
                "Nabeul", "Sfax", "Sidi Bouzid", "Siliana", "Sousse", "Tataouine", "Tozeur", "Tunis", "Zaghouan"};
            TextFields.bindAutoCompletion(lieu_evenement_fx, listeGouvernerat);
        } catch (Exception e) {
            e.printStackTrace();
        }

        PopOver popOver1 = ServiceEvenement.popNotification("le nom ne doit pas contenir aucun caractére spécial");
        nom_evenement_fx.setOnMouseEntered(mouseEvent -> {
            popOver1.show(nom_evenement_fx);
        });
        nom_evenement_fx.setOnMouseExited(mouseEvent -> {
            popOver1.hide();
        });

        PopOver popOver2 = ServiceEvenement.popNotification("l'autocomplétion vous aidera à choisir le lieu");
        lieu_evenement_fx.setOnMouseEntered(mouseEvent -> {
            popOver2.show(lieu_evenement_fx);
        });
        lieu_evenement_fx.setOnMouseExited(mouseEvent -> {
            popOver2.hide();
        });

        PopOver popOver3 = ServiceEvenement.popNotification("la capacité doit étre un entier positif");
        capacite_evenement_fx.setOnMouseEntered(mouseEvent -> {
            popOver3.show(capacite_evenement_fx);
        });
        capacite_evenement_fx.setOnMouseExited(mouseEvent -> {
            popOver3.hide();
        });

        PopOver popOver4 = ServiceEvenement.popNotification("la date debut doit ètre superier à la date d'aujourd'hui");
        date_debut_evenement_fx.setOnMouseEntered(mouseEvent -> {
            popOver4.show(date_debut_evenement_fx);
        });
        date_debut_evenement_fx.setOnMouseExited(mouseEvent -> {
            popOver4.hide();
        });

        PopOver popOver5 = ServiceEvenement.popNotification("la date fin doit ètre superier à la date debut");
        date_fin_evenement_fx.setOnMouseEntered(mouseEvent -> {
            popOver5.show(date_fin_evenement_fx);
        });
        date_fin_evenement_fx.setOnMouseExited(mouseEvent -> {
            popOver5.hide();
        });

        PopOver popOver6 = ServiceEvenement.popNotification("cliquez ici pour choisir l'affiche de votre événement");
        btnBrowser.setOnMouseEntered(mouseEvent -> {
            popOver6.show(btnBrowser);
        });
        btnBrowser.setOnMouseExited(mouseEvent -> {
            popOver6.hide();
        });

    }

    @FXML
    private void modifierEvenement() {
        int idEvenement = id_evenement;
        String nomEvenement = nom_evenement_fx.getText();
        String lieuEvenement = lieu_evenement_fx.getText();
        String descriptionEvenement = description_evenement_fx.getText();
        String typeEvenement = type_evenement_fx.getValue();
        String capaciteEvenement = capacite_evenement_fx.getText();
        LocalDate dateDebutEvenement = date_debut_evenement_fx.getValue();
        LocalDate dateFinEvenement = date_fin_evenement_fx.getValue();
        String afficheEvenement;

        if (file==null) {
            afficheEvenement = nomAfficher;
        } else {
            afficheEvenement = replaceFile(file.getAbsolutePath());
        }

        BoxBlur blur = new BoxBlur(2, 2, 2);
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        JFXButton button = new JFXButton("OKAY");
        button.getStyleClass().add("dialog-button");
        JFXDialog dialog = new JFXDialog(modifierEvenementStackPane, dialogLayout, JFXDialog.DialogTransition.TOP);
        button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) -> {

            dialog.close();
        });
        if (nomEvenement.isEmpty() || lieuEvenement.isEmpty() || capaciteEvenement.isEmpty()
                || descriptionEvenement.isEmpty() || typeEvenement.equals("Choisissez") || dateDebutEvenement == null || dateFinEvenement == null || afficheEvenement.isEmpty()) {
            dialogLayout.setHeading(new Label("Champ Vide"));
            dialogLayout.setBody(new Label("tous les champs doivent être remplis"));

            dialogLayout.setActions(button);
            dialog.show();
            dialog.setOnDialogClosed((JFXDialogEvent event1) -> {
                GUI.setEffect(null);
            });
            GUI.setEffect(blur);
            return;
        } else if (!ServiceEvenement.validationChaineSimpleAvecEspace(nomEvenement)) {
            dialogLayout.setHeading(new Label("Nom d'événement"));
            dialogLayout.setBody(new Label("le nom d'evenement est non autorisé"));
            dialogLayout.setActions(button);
            dialog.show();
            dialog.setOnDialogClosed((JFXDialogEvent event1) -> {
                GUI.setEffect(null);
            });
            GUI.setEffect(blur);
            return;
        } else if (!ServiceEvenement.validationChaineSimpleAvecEspace(lieuEvenement)) {
            dialogLayout.setHeading(new Label("lieu d'événement"));
            dialogLayout.setBody(new Label("le lieu d'evenement est non autorisé"));
            dialogLayout.setActions(button);
            dialog.show();
            dialog.setOnDialogClosed((JFXDialogEvent event1) -> {
                GUI.setEffect(null);
            });
            GUI.setEffect(blur);
            return;
        } else if (!ServiceEvenement.validationChaineSimpleNombre(capaciteEvenement)) {
            dialogLayout.setHeading(new Label("Capacité d'événement"));
            dialogLayout.setBody(new Label("la capacité d'évenement doit être un nombre positif"));
            dialogLayout.setActions(button);
            dialog.show();
            dialog.setOnDialogClosed((JFXDialogEvent event1) -> {
                GUI.setEffect(null);
            });
            GUI.setEffect(blur);
            return;
        } else if (dateDebutEvenement.isBefore(LocalDate.now())) {
            dialogLayout.setHeading(new Label("Date debut événement"));
            dialogLayout.setBody(new Label("date debut évenement est invalide"));
            dialogLayout.setActions(button);
            dialog.show();
            dialog.setOnDialogClosed((JFXDialogEvent event1) -> {
                GUI.setEffect(null);
            });
            GUI.setEffect(blur);
            return;
        } else if (dateFinEvenement.isBefore(dateDebutEvenement)) {
            dialogLayout.setHeading(new Label("Date fin événement"));
            dialogLayout.setBody(new Label("date fin évenement est invalide"));
            dialogLayout.setActions(button);
            dialog.show();
            dialog.setOnDialogClosed((JFXDialogEvent event1) -> {
                GUI.setEffect(null);
            });
            GUI.setEffect(blur);
            return;
        }

        Evenement evenement = new Evenement();
        evenement.setId_Evenement(idEvenement);
        evenement.setNom_evenement(nomEvenement);
        evenement.setLieu_evenement(lieuEvenement);
        evenement.setDescription_evenement(descriptionEvenement);
        evenement.setType_evenement(typeEvenement);
        evenement.setCapacite_evenement(Integer.parseInt(capaciteEvenement));
        evenement.setDate_debut_evenement(dateDebutEvenement);
        evenement.setDate_fin_evenement(dateFinEvenement);
        evenement.setAffiche_evenement(afficheEvenement);

        int status = ServiceEvenement.modifierEvenement(evenement);;

        if (status > 0) {
            Node graphic = null;
            ServiceEvenement.notification("Notification de modification", Pos.BOTTOM_RIGHT, graphic, "Evenement Modifier");
            ServiceEvenement.notificationBuilder.show();
            return;

        } else {
            Node graphic = null;
            ServiceEvenement.notification("Notification de modification", Pos.BOTTOM_RIGHT, graphic, "Erreur de Modification");
            ServiceEvenement.notificationBuilder.show();
            return;

        }
    }

    @FXML
    private void handleBrowser(ActionEvent event) {
        stage = (Stage) GUI.getScene().getWindow();
        file = fileChooser.showOpenDialog(stage);

        // try { deskTOP.open(file); } catch (IOException e) { e.printStackTrace(); }
        if (file != null) {
            System.out.println("" + file.getAbsolutePath());
            image = new Image(file.getAbsoluteFile().toURI().toString(), imageView.getFitWidth(),
                    imageView.getFitHeight(), true, true);
            imageView.setImage(image);
            imageView.setPreserveRatio(true);
        }
    }

    @FXML
    private void Annuler(ActionEvent event) {

        Stage stage = (Stage) GUI.getScene().getWindow();
        stage.close();
    }

    private String generateFileName() {

        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 5) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return "Evenement_"+saltStr;
    }

    private String replaceFile(String file) {
        String extension = file.substring(file.lastIndexOf("."), file.length());
        String filename = generateFileName() + extension;
        Path sourceDirectory = Paths.get(file);
        Path targetDirectory = Paths.get("C:\\wamp64\\www\\worldfriendship\\web\\Evenement\\image\\affiches\\" + filename);
        try {
            Files.copy(sourceDirectory, targetDirectory);
        } catch (IOException ex) {
            Logger.getLogger(ModifierEvenementController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return filename;
    }

}
