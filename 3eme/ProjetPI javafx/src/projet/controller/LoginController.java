package projet.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import projet.models.Utilisateur;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import projet.service.ServiceLogin;

public class LoginController {

    @FXML
    private AnchorPane GUI;
    /* utilisateur inscri + login */
    @FXML
    private TextField inscription_nom_utilisateur_fx;

    @FXML
    private PasswordField inscription_mot_de_passe_utilisateur_fx;

    @FXML
    private TextField inscription_email_utilisateur_fx;

    @FXML
    private TextField login_nom_utilisateur_fx;

    @FXML
    private PasswordField login_mot_de_passe_utilisateur_fx;

    /* end utilisateur */
    /**
     * design login *
     */
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

    /**
     * end design login *
     */
    @FXML
    void Inscription(ActionEvent event) throws IOException {

        int status = 0;
        
        String nomUtilisateur = inscription_nom_utilisateur_fx.getText();
        String motDePasseUtilisateur = inscription_mot_de_passe_utilisateur_fx.getText();

        String emailUtilisateur = inscription_email_utilisateur_fx.getText();

        Utilisateur Uti = new Utilisateur();
        Uti.setNom_Utilisateur(nomUtilisateur);
        Uti.setMotDePasse_Utilisateur(motDePasseUtilisateur);
        Uti.setEmail(emailUtilisateur);
        
        Utilisateur existenceUtilisateur = new Utilisateur();
        existenceUtilisateur = ServiceLogin.getUtilisateur(nomUtilisateur);

        if (existenceUtilisateur == null) {
            status = ServiceLogin.Inscription(Uti);
            
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("le nom est déja utilisé");
            alert.showAndWait();
            return;

        }

        if (status > 0) {
            
            Stage stage = (Stage) GUI.getScene().getWindow();
            stage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/projet/presentation/worldfriendship.fxml"));
            Parent root = loader.load();
            WorldfriendshipController controller = (WorldfriendshipController) loader.getController();
            existenceUtilisateur = ServiceLogin.getUtilisateur(nomUtilisateur);
            controller.setUser(existenceUtilisateur);

            Stage primaryStage = new Stage();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
            return;

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("ERREUR D'inscription");
            alert.showAndWait();
        }
    }

    @FXML
    void connexionUtilisateur(ActionEvent event) throws IOException {

        if (login_nom_utilisateur_fx.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("nom d'utilisateur est vide");
            alert.showAndWait();
            return;

        } else if (login_mot_de_passe_utilisateur_fx.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("mot de passe est vide");
            alert.showAndWait();
        }

        List<Utilisateur> listUtilisateur = ServiceLogin.getTtUtilisateur();
        HashMap<String, String> hashmapUtilisateur = new HashMap<>();

        for (Utilisateur utilisateur : listUtilisateur) {
            hashmapUtilisateur.put(utilisateur.getNom_Utilisateur(), utilisateur.getMotDePasse_Utilisateur());
        }

      
        String nomUtilisater = login_nom_utilisateur_fx.getText();
        String motDePasseUtilisateur = login_mot_de_passe_utilisateur_fx.getText();
        for (Map.Entry<String, String> Uti : hashmapUtilisateur.entrySet()) {
            if (nomUtilisater.equals(Uti.getKey())) {
                if (ServiceLogin.testMotDePasse(motDePasseUtilisateur, Uti.getValue())) {
                    Utilisateur utilisateur = ServiceLogin.getUtilisateur(Uti.getKey());
                    if (utilisateur.getRole_Utilisateur().equals("a:0:{}")) {
                        
                        Stage stage = (Stage) GUI.getScene().getWindow();
                        stage.close();
                      
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/projet/presentation/worldfriendship.fxml"));
                        Parent root = loader.load();
                        WorldfriendshipController controller = (WorldfriendshipController) loader.getController();
                        controller.setUser(ServiceLogin.getUtilisateur(Uti.getKey()));

                        Stage primaryStage = new Stage();
                        Scene scene = new Scene(root);
                        primaryStage.setScene(scene);
                        primaryStage.show();
                        return;
                    } else {
                      
                        Stage stage = (Stage) GUI.getScene().getWindow();
                        stage.close();
                        
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/projet/presentation/Dashboard.fxml"));
                        Parent root = loader.load();
                        DashboardController controller = (DashboardController) loader.getController();
                        controller.setUser(ServiceLogin.getUtilisateur(Uti.getKey()));

                        Stage primaryStage = new Stage();
                        Scene scene = new Scene(root);
                        primaryStage.setScene(scene);
                        primaryStage.show();
                        return;
                    }

                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("mot de passe incorrect");
                    alert.showAndWait();
                    return;
                }
            }
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("nom incorrect");
        alert.showAndWait();

    }
    
    

     @FXML
    public void connexionLoad(ActionEvent even) throws IOException {
        try {
            
            Stage stage = (Stage) GUI.getScene().getWindow();
            stage.close();
            
            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/projet/presentation/LoginGUI.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
           
            primaryStage.initStyle(StageStyle.UNDECORATED);

            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void CreerCompte(ActionEvent even) throws IOException {
        try {
            
            Stage stage = (Stage) GUI.getScene().getWindow();
            stage.close();
            
            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/projet/presentation/InscriptionGUI.fxml"));
            Scene scene = new Scene(root);
       
            primaryStage.setScene(scene);
            
            primaryStage.initStyle(StageStyle.UNDECORATED);
            
            primaryStage.show();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void quitter() {
        // get a handle to the stage
        Stage stage = (Stage) GUI.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
}
