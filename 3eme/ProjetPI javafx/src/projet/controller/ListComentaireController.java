/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.controller;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import projet.models.comment;
import projet.service.CommentService;

/**
 * FXML Controller class
 *
 * @author Moez
 */
public class ListComentaireController implements Initializable {

    @FXML
    private BorderPane borderpane;

    public TableView<comment> listCommentaires;
    @FXML
    private TableColumn<?, ?> id;
    @FXML
    private TableColumn<?, ?> id_Ques;
    @FXML
    private TableColumn<?, ?> id_User;
    @FXML
    private TableColumn<?, ?> description;
    @FXML
    private TableColumn<?, ?> date;
    CommentService commentService = new CommentService();
    @FXML
    private JFXTextField rechercheBar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        CommentService commentService = new CommentService();
        ArrayList arrayList = (ArrayList) commentService.ListCommentaire();

        ObservableList observableList = FXCollections.observableArrayList(arrayList);

        id.setCellValueFactory(new PropertyValueFactory<>("idCommentaire"));
        id_Ques.setCellValueFactory(new PropertyValueFactory<>("idForum"));
        id_User.setCellValueFactory(new PropertyValueFactory<>("user"));
        description.setCellValueFactory(new PropertyValueFactory<>("contenu"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));

        listCommentaires.setItems(observableList);
    }

    private int index() {
        int selectedItem = listCommentaires.getSelectionModel().getSelectedItem().getIdCommentaire();
        int selectedIndex = listCommentaires.getSelectionModel().getSelectedIndex();
        System.out.println(selectedItem);
        return selectedItem;
    }

    @FXML
    private void supprimer(ActionEvent event) {
        int x = index();
        Alert a1 = new Alert(Alert.AlertType.WARNING);
        a1.setTitle("Supprimer commentaire");
        a1.setContentText("Vous voulez vraiment supprimer cet commentaire ?");
        Optional<ButtonType> result = a1.showAndWait();
        if (result.get() == ButtonType.OK) {
            commentService.supprimer(x);
            Alert a2 = new Alert(Alert.AlertType.INFORMATION);
            a2.setTitle("Supprimer commentaire");
            a2.setContentText("Commentaire supprimé avec succés!");
            a2.show();

            listCommentaires.getItems().clear();
            listCommentaires.getItems().addAll(commentService.ListCommentaire());

        } else {
            a1.close();
        }

    }

    @FXML
    private void rechercher(KeyEvent event) {

        if (!rechercheBar.getText().isEmpty()) {
            listCommentaires.setVisible(true);
            List<comment> myList = commentService.rechercheCommentaires(rechercheBar.getText());
            ObservableList<comment> observableList = FXCollections.observableArrayList();

            id.setCellValueFactory(new PropertyValueFactory<>("idCommentaire"));
            id_Ques.setCellValueFactory(new PropertyValueFactory<>("idForum"));
            id_User.setCellValueFactory(new PropertyValueFactory<>("user"));
            description.setCellValueFactory(new PropertyValueFactory<>("contenu"));
            date.setCellValueFactory(new PropertyValueFactory<>("date"));

            myList.forEach(e -> {

                observableList.add(e);
                System.out.println(observableList);
            });
            listCommentaires.setItems(observableList);
        } else {
            if (rechercheBar.getText().isEmpty()) {
                listCommentaires.getItems().clear();
                listCommentaires.getItems().addAll(commentService.ListCommentaire());
            }

        }
    }
}
