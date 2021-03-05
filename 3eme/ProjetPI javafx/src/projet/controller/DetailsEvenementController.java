/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.controller;

import projet.models.CommentaireEvenement;
import static projet.controller.EvenementController.recupererUtilisateurConnecte;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.events.JFXDialogEvent;
import com.jfoenix.effects.JFXDepthManager;
import java.io.FileInputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import projet.models.Evenement;
import projet.service.ServiceCommentaireEvenement;

/**
 *
 * @author solta
 */
public class DetailsEvenementController implements Initializable {


    /* details Evenement */
    @FXML
    public Label nom_evenement_detailsEvenement;

    @FXML
    public ImageView affiche_evenement_detailsEvenement;

    @FXML
    public JFXListView<String> ListView_detailsEvenement;

    @FXML
    private StackPane DetailsEvenementStackPane;

    @FXML
    private AnchorPane GUI;

    /*end details Evenement */
 /* commentaire */
    @FXML
    public ListView<String> ListView_commentaire;
    @FXML
    private TextArea commentaire_text_fx;

    @FXML
    private MenuItem modifier_commentaire_fx;

    @FXML
    private MenuItem supprimer_commentaire_fx;

    @FXML
    private VBox content;

    public List<CommentaireEvenement> listCommentaireController;
    /*end commentaire */
    @FXML
    private StackPane afficherTsEvenementStackPane;

    @FXML
    private ScrollPane scrollPaneCommentaire;

    public int idEvenement;
    
    
        double x = 0;
    double y = 0;

    @FXML
    void dragged(MouseEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);

    }

    @FXML
    void pressed(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }


    public void setDataCommentaire(int idEvenement) {
        this.idEvenement = idEvenement;
        content.getChildren().clear();
        commentaires();

    }


    ObservableList<CommentaireEvenement> dataComment = FXCollections.observableArrayList();

    public void commentaires() {
        dataComment.clear();
        try {
            dataComment.addAll(ServiceCommentaireEvenement.afficherCommentaire(idEvenement));
            Image image = new Image(new FileInputStream("src/projet/images/Evenement/avatar.png"));

            HBox hbox = new HBox();
            content.getChildren().add(hbox);
            int index = 0;

            JFXDepthManager.setDepth(hbox, 0);

            for (CommentaireEvenement commentaire : dataComment) {

                if (index % 1 == 0) {
                    hbox = new HBox();
                    content.getChildren().add(hbox);
                }

                ImageView avatar = new ImageView(image);
                avatar.setFitHeight(30);
                avatar.setFitWidth(30);

                Label c = new Label();
                c.setText(commentaire.getDate_commentaire_affi() + " " + commentaire.getContenu_commentaire());

                HBox hb = new HBox();
                hb.getChildren().addAll(avatar, c);
                hb.setMargin(avatar, new Insets(1, 1, 1, 1));
                hb.setMargin(c, new Insets(1, 1, 1, 1));

                hbox.getChildren().add(hb);
                index++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public void ajouterCommentaireEvenement() {
        String contenueCommentaireEvenement = commentaire_text_fx.getText();
        BoxBlur blur = new BoxBlur(2, 2, 2);
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        JFXButton button = new JFXButton("OKAY");
        button.getStyleClass().add("dialog-button");
        JFXDialog dialog = new JFXDialog(DetailsEvenementStackPane, dialogLayout, JFXDialog.DialogTransition.TOP);
        button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) -> {
            dialog.close();
        });
        if (contenueCommentaireEvenement.isEmpty()) {
            dialogLayout.setHeading(new Label("Champ commentaire est vide"));
            dialogLayout.setBody(new Label("vous devez ecrir un commentaire"));
            dialogLayout.setActions(button);
            dialog.show();
            dialog.setOnDialogClosed((JFXDialogEvent event1) -> {
                GUI.setEffect(null);
            });
            GUI.setEffect(blur);
            return;
        }
        /* ListView_commentaire.getItems().add(LocalDate.now() + " Ajouter par " + recupererUtilisateurConnecte.getNom_Utilisateur() +
                ": \"" + contenueCommentaireEvenement + "\"");*/
        //recupererUtilisateurConnecte.getNom_Utilisateur()+" : "+contenueCommentaireEvenement a la place de requette qui n'a pas marché 
        CommentaireEvenement commentaire = new CommentaireEvenement(idEvenement, recupererUtilisateurConnecte.getId_Utilisateur(), LocalDate.now(), recupererUtilisateurConnecte.getNom_Utilisateur() + " : \"" + contenueCommentaireEvenement + "\"");
        //commentaire.setContenu_commentaire(contenueCommentaireEvenement);

        ServiceCommentaireEvenement.ajouterCommentaireEvenement(commentaire);
        setDataCommentaire(idEvenement);
        commentaire_text_fx.clear();
    }

    @FXML
    void close(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void max(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    }

    @FXML
    void min(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

}
