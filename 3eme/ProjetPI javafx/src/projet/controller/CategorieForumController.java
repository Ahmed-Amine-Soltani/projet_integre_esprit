/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.StageStyle;
import projet.models.categorie;
import projet.service.CategorieService;

/**
 * FXML Controller class
 *
 * @author Moez
 */
public class CategorieForumController implements Initializable {

    @FXML
    private BorderPane borderpane;
    
    public TableView<categorie> listeCategorie;
    @FXML
    private TableColumn<?, ?> id;
    @FXML
    private TableColumn<?, ?> nom;
    CategorieService categorieService = new CategorieService();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        CategorieService categorieService = new CategorieService();
        ArrayList arrayList = (ArrayList) categorieService.selectAll2();

        ObservableList observableList = FXCollections.observableArrayList(arrayList);

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        listeCategorie.setItems(observableList);

    }

    private int index() {
        int selectedItem = listeCategorie.getSelectionModel().getSelectedItem().getId();
        int selectedIndex = listeCategorie.getSelectionModel().getSelectedIndex();
        System.out.println(selectedItem);
        return selectedItem;
    }

    @FXML
    private void supprimer(ActionEvent event) {
        int x = index();
        Alert a1 = new Alert(Alert.AlertType.WARNING);
        a1.setTitle("Supprimer categorie");
        a1.setContentText("Vous voulez vraiment supprimer cet categorie ?");
        Optional<ButtonType> result = a1.showAndWait();
        if (result.get() == ButtonType.OK) {
            categorieService.supprimer(x);
            Alert a2 = new Alert(Alert.AlertType.INFORMATION);
            a2.setTitle("Supprimer categorie");
            a2.setContentText("Categorie supprimé avec succés!");
            a2.show();

            listeCategorie.getItems().clear();
            listeCategorie.getItems().addAll(categorieService.selectAll2());

        } else {
            a1.close();
        }
    }

    @FXML
    private void ajouter(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/projet/presentation/Ajouter.fxml"));
        Dialog dialog = new Dialog();
        dialog.getDialogPane().setContent(root);
        dialog.initStyle(StageStyle.UNDECORATED);
        dialog.show();
    }

}
