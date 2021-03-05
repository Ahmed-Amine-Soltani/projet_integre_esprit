/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.controller;

import projet.models.CommentaireEvenement;
import projet.models.Evenement;
import projet.models.Utilisateur;
import static projet.controller.EvenementController.recupererUtilisateurConnecte;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.events.JFXDialogEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import projet.service.ServiceCommentaireEvenement;
import projet.service.ServiceEvenement;

/**
 * FXML Controller class
 *
 * @author solta
 */
public class DashBoardEvenementController implements Initializable {

    public static Utilisateur recupererUtilisateurConnecte;

    @FXML
    private StackPane DashBoardStackPane;

    @FXML
    private AnchorPane accueilGUI;
    @FXML
    private StackPane afficherMesEvenementStackPane;
    @FXML
    private TableView<Evenement> tableView_DashBoeard;
    @FXML
    private TableColumn<Evenement, Integer> col_id_ev_DashBoeard;
    @FXML
    private TableColumn<Evenement, String> col_nom_ev_DashBoeard;
    @FXML
    private TableColumn<Evenement, String> col_lieu_ev_DashBoeard;
    @FXML
    private TableColumn<Evenement, String> col_type_ev_DashBoeard;
    @FXML
    private TableColumn<Evenement, Integer> col_capacite_ev_DashBoeard;
    @FXML
    private TableColumn<Evenement, Date> col_date_debut_DashBoeard;
    @FXML
    private TableColumn<Evenement, Date> col_date_fin_DashBoeard;
    @FXML
    private TableColumn<Evenement, ImageView> col_image_DashBoeard;

    /**
     * Initializes the controller class.
     */
    ObservableList<Evenement> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        data.clear();
        List<Evenement> listEvenement = ServiceEvenement.afficherTtEvenement();
        try {
            for (Evenement evenement : listEvenement) {
                Image image = new Image(new FileInputStream("C:\\wamp64\\www\\worldfriendship\\web\\Evenement\\image\\affiches\\" + evenement.getAffiche_evenement()));
                ImageView affiche = new ImageView(image);

                affiche.setFitHeight(100);
                affiche.setFitWidth(100);
                data.add(new Evenement(evenement.getId_Evenement(), evenement.getNom_evenement(), evenement.getLieu_evenement(),
                        evenement.getCapacite_evenement(), evenement.getDescription_evenement(), evenement.getType_evenement(), evenement.getAffi_date_debut(), evenement.getAffi_date_fin(),
                        affiche, evenement.getAffiche_evenement()));

                // ReadOnlyStringWrapper pour éviter l'erreur Type mismatch: cannot convert from String to ObservableValue<String>
                col_nom_ev_DashBoeard.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getNom_evenement()));
                col_lieu_ev_DashBoeard.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getLieu_evenement()));
                col_type_ev_DashBoeard.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getType_evenement()));
                col_capacite_ev_DashBoeard.setCellValueFactory(data -> new ReadOnlyObjectWrapper<Integer>(data.getValue().getCapacite_evenement()));
                col_date_debut_DashBoeard.setCellValueFactory(data -> new ReadOnlyObjectWrapper<Date>(data.getValue().getAffi_date_debut()));

                col_date_fin_DashBoeard.setCellValueFactory(data -> new ReadOnlyObjectWrapper<Date>(data.getValue().getAffi_date_fin()));
                col_image_DashBoeard.setCellValueFactory(data -> new ReadOnlyObjectWrapper<ImageView>(data.getValue().getImage_evenement()));
                tableView_DashBoeard.setItems(data);
            }
        } catch (Exception ex) {
            Logger.getLogger(DashBoardEvenementController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void supprimerEvenement(ActionEvent event) {
        //récuprer la ligne selectionné
        Evenement evenSelect = tableView_DashBoeard.getSelectionModel().getSelectedItem();
        if (evenSelect == null) {
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            JFXButton button = new JFXButton("OKAY");
            button.getStyleClass().add("dialog-button");
            JFXDialog dialog = new JFXDialog(afficherMesEvenementStackPane, dialogLayout, JFXDialog.DialogTransition.TOP);
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) -> {
                dialog.close();
            });
            dialogLayout.setHeading(new Label("Suprrission d'événement"));
            dialogLayout.setBody(new Label("il faut selectionner un evenement"));
            dialogLayout.setActions(button);
            dialog.show();
            dialog.setOnDialogClosed((JFXDialogEvent event1) -> {
                afficherMesEvenementStackPane.setEffect(null);
            });
            return;
        }

        Boolean resultat = ServiceEvenement.supprimerEvenement(evenSelect);
        //pour refraichir le tableView
        data.remove(evenSelect);
        if (resultat) {
            Node graphic = null;
            ServiceEvenement.notification("Notification de Supprission", Pos.BOTTOM_RIGHT, graphic, "Evenement Supprimer");
            ServiceEvenement.notificationBuilder.show();
            return;
        } else {
            Node graphic = null;
            ServiceEvenement.notification("Notification de Supprission", Pos.BOTTOM_RIGHT, graphic, "Evenement " + evenSelect.getNom_evenement() + " est supprimé");
            ServiceEvenement.notificationBuilder.show();
            return;
        }

    }

}
