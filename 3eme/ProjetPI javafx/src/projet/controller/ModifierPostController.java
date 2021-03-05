/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.controller;

import com.jfoenix.controls.JFXTextArea;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import projet.models.forum;
import projet.service.ForumService;

/**
 * FXML Controller class
 *
 * @author Moez
 */
public class ModifierPostController implements Initializable {

    @FXML
    private TextField title;
    @FXML
    private JFXTextArea description;
    @FXML
    private TextField idModifier;

    private forum forum;

    ForumService forumService = new ForumService();
    @FXML
    private Button mbutton;
    @FXML
    private Button closeButton;
    @FXML
    private Label errorsTitle;
    @FXML
    private Label errorsDescription;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
    }

    @FXML
    private void modifier(ActionEvent event) throws IOException {
        
        if (title.getText().equals("")) {
            errorsTitle.setText("Saisir un Title");
        } else if (description.getText().equals("")) {
            errorsDescription.setText("Saisir un Question");
        } else {

        ForumService fs = new ForumService();
        String a = title.getText();
        String b = description.getText();
        int id = Integer.valueOf(idModifier.getText());

        Alert a1 = new Alert(Alert.AlertType.CONFIRMATION);
        a1.setTitle("Modifier une experience");
        a1.setContentText("vous voulez vraiment Mdofier cette post?");
        Optional<ButtonType> result = a1.showAndWait();
        if (result.get() == ButtonType.OK) {
            //FXMLLoader loader = FXMLLoader.load(getClass().getResource("ListPosts.fxml"));
            fs.modifier(id, a, b);
            Stage stage = (Stage) mbutton.getScene().getWindow();
            stage.close();

        } else if (result.get() == ButtonType.CANCEL) {
            //FXMLLoader loader = FXMLLoader.load(getClass().getResource("ListPosts.fxml"));

        }
        }

    }

    public void setForum(forum fo) {
        forum = fo;
        idModifier.setText(String.valueOf(fo.getId()));
        description.setText(fo.getDescription());
        title.setText(fo.getTitle());

    }

    @FXML
    private void closeButtonAction(ActionEvent event) {
        // get a handle to the stage
        Stage stage = (Stage) closeButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
    
    private void titleEvent(KeyEvent event) {
        errorsTitle.setText("");

    }

    private void descriptionEvent(KeyEvent event) {
        errorsDescription.setText("");

    }

}
