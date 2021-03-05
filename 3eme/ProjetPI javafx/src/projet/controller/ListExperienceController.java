/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import projet.models.Experience;
import projet.service.ExperienceService;

/**
 * FXML Controller class
 *
 * @author houba
 */
public class ListExperienceController implements Initializable {

    @FXML
    private TableColumn<?, ?> id;
    @FXML
    private TableColumn<?, ?> nom;
    @FXML
    private TableColumn<?, ?> desc;
    @FXML
    private TableView<Experience> tableview;
    @FXML
    private TableColumn<?, ?> lieu;
    @FXML
    private TableColumn<?, ?> cat;
    @FXML
    private TableColumn<?, ?> date;
    @FXML
    private TextField rechercheBar;
    @FXML
    private TableColumn<?, ?> user;

    ExperienceService experienceService = new ExperienceService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        afficher();
    }

    private void afficher() {

        List<Experience> myList = experienceService.selectAll();
        ObservableList<Experience> myObservableList = FXCollections.observableArrayList();

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        desc.setCellValueFactory(new PropertyValueFactory<>("desc"));
        lieu.setCellValueFactory(new PropertyValueFactory<>("lieu"));
        cat.setCellValueFactory(new PropertyValueFactory<>("catExp"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        user.setCellValueFactory(new PropertyValueFactory<>("user"));

        myList.forEach(e -> {
            myObservableList.add(e);
            tableview.setItems(myObservableList);
        });

    }

    @FXML
    private void refresh(ActionEvent event) throws IOException {
        tableview.getItems().clear();
        tableview.getItems().addAll(experienceService.selectAll());
    }

    private int index() {
        int selectedItem = tableview.getSelectionModel().getSelectedItem().getId();
        int selectedIndex = tableview.getSelectionModel().getSelectedIndex();
        System.out.println(selectedItem);
        return selectedItem;
    }

    @FXML
    private void supprimer(ActionEvent event) {
        int x = index();
        Alert a1 = new Alert(Alert.AlertType.WARNING);
        a1.setTitle("Supprimer Experience");
        a1.setContentText("Vous voulez vraiment supprimer cet Experience ?");
        Optional<ButtonType> result = a1.showAndWait();
        if (result.get() == ButtonType.OK) {
            experienceService.supprimer(x);
            Alert a2 = new Alert(Alert.AlertType.INFORMATION);
            a2.setTitle("Supprimer Experience");
            a2.setContentText("Experience supprimé avec succés!");
            a2.show();

            tableview.getItems().clear();
            tableview.getItems().addAll(experienceService.selectAll());

        } else {
            a1.close();
        }
    }

    private void modifier(MouseEvent event, Experience exp) throws IOException {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            if (event.getClickCount() == 2) {
                FXMLLoader Loader = new FXMLLoader();
                Loader.setLocation(getClass().getResource("/projet/presentation/Modifier.fxml"));
                Parent p = Loader.load();
                Scene pp = new Scene(p);
                ModifierController display = Loader.getController();
              //  display.setExperience(exp);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(pp);
                stage.showAndWait();
            }
        }
    }

    private void ajouter(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/projet/presentation/Ajouter.fxml"));
        Dialog dialog = new Dialog();
        dialog.getDialogPane().setContent(root);
        dialog.initStyle(StageStyle.UNDECORATED);
        dialog.show();
        tableview.getItems().clear();
        tableview.getItems().addAll(experienceService.selectAll());
    }

    @FXML
    private void rechercher(KeyEvent event) {
        if (!rechercheBar.getText().isEmpty()) {
            tableview.setVisible(true);

            List<Experience> myList = experienceService.rechercheExperiences(rechercheBar.getText());
            ObservableList<Experience> observableList = FXCollections.observableArrayList();

            id.setCellValueFactory(new PropertyValueFactory<>("id"));
            nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            desc.setCellValueFactory(new PropertyValueFactory<>("desc"));
            lieu.setCellValueFactory(new PropertyValueFactory<>("lieu"));
            cat.setCellValueFactory(new PropertyValueFactory<>("catExp"));
            date.setCellValueFactory(new PropertyValueFactory<>("date"));
            user.setCellValueFactory(new PropertyValueFactory<>("user"));

            myList.forEach(e -> {
                observableList.add(e);
                System.out.println(observableList);
            });
            tableview.setItems(observableList);
        } else {
            if (rechercheBar.getText().isEmpty()) {
                tableview.getItems().clear();
                tableview.getItems().addAll(experienceService.selectAll());
            }
        }
    }

    int clicked = 0;

    private void test(ActionEvent event) {
        clicked++;
        System.out.println(clicked);
    }

    @FXML
    private void ajouterDialog(MouseEvent event) {
    }

    @FXML
    private void exportXLS(MouseEvent event) {
    }

}
