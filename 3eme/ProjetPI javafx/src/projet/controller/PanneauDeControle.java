package projet.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.events.JFXDialogEvent;
import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import projet.service.ServiceEvenement;

public class PanneauDeControle {

    @FXML
    Label label_EtatBaseDonnees;
    @FXML
    private StackPane panneauControleStackPane;

    @FXML
    private AnchorPane panneauControleAnchorPane;

    @FXML
    public void etatBaseDonnees(ActionEvent even) throws SQLException {

        BoxBlur blur = new BoxBlur(3, 3, 3);
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        JFXButton button = new JFXButton("OKAY");
        button.getStyleClass().add("dialog-button");
        JFXDialog dialog = new JFXDialog(panneauControleStackPane, dialogLayout, JFXDialog.DialogTransition.TOP);
        button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) -> {

            dialog.close();
        });
        if (!ServiceEvenement.creationConnexion().isClosed()) {
            dialogLayout.setHeading(new Label("heading"));
            dialogLayout.setBody(new Label("Connecte"));
        } else {
            dialogLayout.setBody(new Text("Probleme de connexion"));
        }

        dialogLayout.setActions(button);
        dialog.show();
        dialog.setOnDialogClosed((JFXDialogEvent event1) -> {
            panneauControleAnchorPane.setEffect(null);
        });
        panneauControleAnchorPane.setEffect(blur);

    }

    public void afficherPage(String nomPage, String titre) throws IOException {

        Stage primaryStage = new Stage();
        primaryStage.setTitle(titre);
        Parent root = FXMLLoader.load(getClass().getResource("/projet/presentation/"+nomPage));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/projet/style/EvenementCss.css").toExternalForm());
        primaryStage.setScene(scene);

        /**/
        primaryStage.setMinWidth(450);
        primaryStage.setMinHeight(300);

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());

        primaryStage.setMaximized(true);
        /**/
        primaryStage.show();
    }

    @FXML
    public void login(ActionEvent even) throws IOException {
        afficherPage("LoginGUI.fxml", "Login");
    }

    @FXML
    public void CreerCompte(ActionEvent even) throws IOException {
        afficherPage("InscriptionGUI.fxml", "Créer Votre Compte");
    }

    @FXML
    public void accueilEvenement(ActionEvent even) throws IOException {
        afficherPage("AccueilEvenementGUI.fxml", "Accueil d'Evenement");
    }

      @FXML
    public void dashBoardEvenement(ActionEvent even) throws IOException {
        afficherPage("DashBoardEvenementGUI.fxml", "DashBoard d'Evenement");
    }
    /**
     * test
     */
    @FXML
    void afficherMesEvenementGUI(ActionEvent event) {

    }

    @FXML
    void afficherMesReservationGUI(ActionEvent event) {

    }

    @FXML
    void afficherTsEvenementGUI(ActionEvent event) {
        
    }

    @FXML
    public void ajouterEvenementGUI(ActionEvent even) throws IOException {
         Stage primaryStage = new Stage();
        primaryStage.setTitle("Ajouter Evenement");
        Parent root = FXMLLoader.load(getClass().getResource("/projet/presentation/AjouterEvenementGUI.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setMaxWidth(570);
        primaryStage.setMaxHeight(730);
        primaryStage.setMinWidth(570);
        primaryStage.setMinHeight(730);
        primaryStage.show();
    }
    }


