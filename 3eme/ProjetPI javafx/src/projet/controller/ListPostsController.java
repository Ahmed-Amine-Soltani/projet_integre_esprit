/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.controller;

import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import javafx.stage.Stage;
import javafx.stage.StageStyle;
import projet.models.forum;
import projet.service.CommentService;
import projet.service.ForumService;


/**
 * FXML Controller class
 *
 * @author Moez
 */
public class ListPostsController implements Initializable {

    public TableView<forum> listPosts;

    @FXML
    private TableColumn<?, ?> title;
    @FXML
    private TableColumn<?, ?> id;
    @FXML
    private TableColumn<?, ?> description;

    public static forum f = new forum();

    ForumService forumService = new ForumService();

    public int idCategorieRecuperer;
    @FXML
    private BorderPane borderpane;
    @FXML
    private JFXTextField rechercheBar;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ArrayList arrayList = (ArrayList) forumService.selectAll(idCategorieRecuperer);
        ObservableList<forum> observableList = FXCollections.observableArrayList(arrayList);

        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        listPosts.setItems(observableList);
        listPosts.setOnMouseClicked((MouseEvent event) -> {
            forum fo = listPosts.getSelectionModel().getSelectedItem();
            /* try {
                modifier(event, fo);
            } catch (IOException ex) {
                Logger.getLogger(ListController.class.getName()).log(Level.SEVERE, null, ex);
            }*/

            try {
                afficherPost(event, fo);
            } catch (IOException ex) {
                Logger.getLogger(ListController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        // TODO
    }

    private int index() {
        int selectedItem = listPosts.getSelectionModel().getSelectedItem().getId();
        int selectedIndex = listPosts.getSelectionModel().getSelectedIndex();
        System.out.println(selectedItem);
        return selectedItem;
    }

    @FXML
    private void supprimer(ActionEvent event) {
        int x = index();
        Alert a1 = new Alert(Alert.AlertType.WARNING);
        a1.setTitle("Supprimer post");
        a1.setContentText("Vous voulez vraiment supprimer cet post?");
        Optional<ButtonType> result = a1.showAndWait();
        if (result.get() == ButtonType.OK) {
            forumService.supprimer(x);
            Alert a2 = new Alert(Alert.AlertType.INFORMATION);
            a2.setTitle("Supprimer post");
            a2.setContentText("Post supprimé avec succés!");
            a2.show();

            listPosts.getItems().clear();
            listPosts.getItems().addAll(forumService.selectAll(idCategorieRecuperer));

        } else {
            a1.close();
        }
    }

    /*  private void modifier(MouseEvent event, forum fo) throws IOException {

            if (event.getButton().equals(MouseButton.PRIMARY)) {
                if (event.getClickCount() == 2) {

                FXMLLoader Loader = new FXMLLoader();
                Loader.setLocation(getClass().getResource("ModifierPost.fxml"));
                Parent p = Loader.load();
                Scene pp = new Scene(p);
                ModifierPostController display = Loader.getController();
                display.setForum(fo);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(pp);
                stage.show();
                }   
            }
        }   */
    private void afficherPost(MouseEvent event, forum fo) throws IOException {
        CommentService cs = new CommentService();
        forum f = listPosts.getSelectionModel().getSelectedItem();
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            if (event.getClickCount() == 2) {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/projet/presentation/afficherPost.fxml"));
                Parent root = loader.load();
                AfficherPostController controller = (AfficherPostController) loader.getController();
                
                controller.setTest(fo);
                
                controller.idForumRecuperer = f.getId();
                //controller.forum = fo.getTitle();
                controller.ListView_commentaire.getItems().setAll(cs.afficherCommentaire(f.getId()));

                Stage primaryStage = new Stage();
                Scene scene = new Scene(root);
                scene.getStylesheets().add(getClass().getResource("/projet/style/login.css").toExternalForm());
                primaryStage.setScene(scene);
                primaryStage.show();
            }
        }

    }

    @FXML
    private void modifier(ActionEvent event) throws IOException {

        forum fo = listPosts.getSelectionModel().getSelectedItem();
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("/projet/presentation/ModifierPost.fxml"));
        Parent p = Loader.load();

        ModifierPostController display = Loader.getController();
        display.setForum(fo);
        Dialog dialog = new Dialog();
        dialog.getDialogPane().setContent(p);
        dialog.initStyle(StageStyle.UNDECORATED);
        dialog.show();
    }

    @FXML
    private void ajouter(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/projet/presentation/AjouterPosts.fxml"));
        Dialog dialog = new Dialog();
        dialog.getDialogPane().setContent(root);
        dialog.initStyle(StageStyle.UNDECORATED);
        dialog.show();
    }

    
}
