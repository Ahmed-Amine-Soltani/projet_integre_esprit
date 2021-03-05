package projet.controller;

import API.JasperReportAPI;
import API.QrCodeMailApi;
import API.SpeechApi;
import CarrouselEvenement.GNCarousel;

import projet.models.CommentaireEvenement;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDatePicker;

import projet.models.Evenement;
import projet.models.ReservationEvenement;
import projet.models.Utilisateur;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.events.JFXDialogEvent;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.InfoOverlay;
import org.controlsfx.control.PopOver;
import org.controlsfx.control.textfield.TextFields;
import static projet.controller.WorldfriendshipController.recupererUtilisateurConnecte;

import projet.service.ServiceEvenement;
import projet.service.ServiceReservationEvenement;

public class EvenementController implements Initializable {

    @FXML
    public VBox content;

    PanneauDeControle pc = new PanneauDeControle();
    /**
     * modifier evenement *
     */
    @FXML
    private ComboBox<String> type_evenement_fx;
    @FXML
    private TextField id_evenement_fx;

    @FXML
    private TextField nom_evenement_fx;

    @FXML
    private TextField lieu_evenement_fx;

    @FXML
    private TextField description_evenement_fx;

    @FXML
    private TextField capacite_evenement_fx;
    @FXML
    private JFXDatePicker date_debut_evenement_fx;
    @FXML
    private JFXDatePicker date_fin_evenement_fx;
    @FXML
    private ImageView imageView;
    private File file = null;
    @FXML
    private AnchorPane GUI;
    /**
     * end modifier evenement *
     */

    /* TableView all events */
    @FXML
    private TableView<Evenement> tableView;
    @FXML
    private TableColumn<Evenement, String> col_nom_ev;
    @FXML
    private TableColumn<Evenement, String> col_lieu_ev;
    @FXML
    private TableColumn<Evenement, Integer> col_capacite_ev;
    @FXML
    private TableColumn<Evenement, String> col_type_ev;
    @FXML
    private TableColumn<Evenement, Date> col_date_debut;
    @FXML
    private TableColumn<Evenement, Date> col_date_fin;
    @FXML
    private TableColumn<Evenement, ImageView> col_image;
    /* end tableview */

 /* TableView my events */
    @FXML
    private TableView<Evenement> tableView_mesEvenement;
    @FXML
    private TableColumn<Evenement, Integer> col_id_ev_mesEvenement;
    @FXML
    private TableColumn<Evenement, String> col_nom_ev_mesEvenement;
    @FXML
    private TableColumn<Evenement, String> col_lieu_ev_mesEvenement;
    @FXML
    private TableColumn<Evenement, String> col_type_ev_mesEvenement;
    @FXML
    private TableColumn<Evenement, Integer> col_capacite_ev_mesEvenement;
    @FXML
    private TableColumn<Evenement, Date> col_date_debut_mesEvenement;
    @FXML
    private TableColumn<Evenement, Date> col_date_fin_mesEvenement;
    @FXML
    private TableColumn<Evenement, ImageView> col_image_mesEvenement;
    /* end tableview */

 /* tableview participation */
    @FXML
    private TableView<Evenement> tableView_participer;

    @FXML
    private TableColumn<Evenement, String> col_nom_ev_participer;

    @FXML
    private TableColumn<Evenement, String> col_lieu_ev_participer;

    @FXML
    private TableColumn<Evenement, String> col_type_ev_participer;

    @FXML
    private TableColumn<Evenement, Integer> col_capacite_ev_participer;

    @FXML
    private TableColumn<Evenement, Date> col_date_debut_participer;

    @FXML
    private TableColumn<Evenement, Date> col_date_fin_participer;

    @FXML
    private TableColumn<Evenement, ImageView> col_image_participer;

    /* end table view participation */
 /* bouton pages */
 /*  @FXML
    public void ajouterEvenementGUI(ActionEvent even) throws IOException {
        pc.afficherPage("AjouterEvenementGUI.fxml", "Ajouter Evenement");
    }

    @FXML
    public void afficherTsEvenementGUI(ActionEvent even) throws IOException {
        pc.afficherPage("AfficherTsEvenementsGUI.fxml", "Tous les Evenements Disponible");
    }

    @FXML
    public void afficherMesEvenementGUI(ActionEvent even) throws IOException {
        pc.afficherPage("AfficherMesEvenementGUI.fxml", "Tous Mes Evenements");
    }

    @FXML
    public void afficherMesReservationGUI(ActionEvent even) throws IOException {
        pc.afficherPage("AfficherMesReservationGUI.fxml", "Tous Mes Reservation");
    }
     */
    /**
     * Stackpane pour dialogJFX *
     */
    @FXML
    private StackPane afficherMesEvenementStackPane;
    @FXML
    private StackPane afficherTsEvenementStackPane;

    @FXML
    private StackPane accueilStackPane;
    @FXML
    private StackPane modifierEvenementStackPane;
    @FXML
    private StackPane afficherReservationStackPane;

    @FXML
    private StackPane infoOverlayImageStackPane;

    // API JASPERREPORT
    @FXML
    private void jasperReportTsEvenement() {
        Evenement evenSelect = tableView.getSelectionModel().getSelectedItem();
        if (evenSelect == null) {
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            JFXButton button = new JFXButton("OKAY");
            button.getStyleClass().add("dialog-button");
            JFXDialog dialog = new JFXDialog(afficherTsEvenementStackPane, dialogLayout, JFXDialog.DialogTransition.TOP);
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) -> {
                dialog.close();
            });
            dialogLayout.setHeading(new Label("Imprime"));
            dialogLayout.setBody(new Label("il faut selectionner un evenement d'abord"));
            dialogLayout.setActions(button);
            dialog.show();
            dialog.setOnDialogClosed((JFXDialogEvent event1) -> {
                afficherTsEvenementStackPane.setEffect(null);
            });
            return;

        }

        JasperReportAPI.jasperReportTsEvenement();
    }

    @FXML
    private void jasperReportTsEvenementSelectionnerGUI() {
        Evenement evenSelect = tableView.getSelectionModel().getSelectedItem();
        if (evenSelect == null) {
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            JFXButton button = new JFXButton("OKAY");
            button.getStyleClass().add("dialog-button");
            JFXDialog dialog = new JFXDialog(afficherTsEvenementStackPane, dialogLayout, JFXDialog.DialogTransition.TOP);
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) -> {
                dialog.close();
            });
            dialogLayout.setHeading(new Label("Imprime"));
            dialogLayout.setBody(new Label("il faut selectionner un evenement d'abord"));
            dialogLayout.setActions(button);
            dialog.show();
            dialog.setOnDialogClosed((JFXDialogEvent event1) -> {
                afficherTsEvenementStackPane.setEffect(null);
            });
            return;
        }
        JasperReportAPI.jasperReportEvenementSelectionner(evenSelect.getId_Evenement());
    }

    @FXML
    private void jasperReportMesEvenementSelectionnerGUI() {
        Evenement evenSelect = tableView_mesEvenement.getSelectionModel().getSelectedItem();
        if (evenSelect == null) {
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            JFXButton button = new JFXButton("OKAY");
            button.getStyleClass().add("dialog-button");
            JFXDialog dialog = new JFXDialog(afficherMesEvenementStackPane, dialogLayout, JFXDialog.DialogTransition.TOP);
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) -> {
                dialog.close();
            });
            dialogLayout.setHeading(new Label("Imprime"));
            dialogLayout.setBody(new Label("il faut selectionner un evenement d'abord"));
            dialogLayout.setActions(button);
            dialog.show();
            dialog.setOnDialogClosed((JFXDialogEvent event1) -> {
                afficherMesEvenementStackPane.setEffect(null);
            });
            return;
        }
        JasperReportAPI.jasperReportEvenementSelectionner(evenSelect.getId_Evenement());
    }

    @FXML
    private void jasperReportTsMesEvenementSelectionnerGUI() {
        Evenement evenSelect = tableView_mesEvenement.getSelectionModel().getSelectedItem();
        if (evenSelect == null) {
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            JFXButton button = new JFXButton("OKAY");
            button.getStyleClass().add("dialog-button");
            JFXDialog dialog = new JFXDialog(afficherMesEvenementStackPane, dialogLayout, JFXDialog.DialogTransition.TOP);
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) -> {
                dialog.close();
            });
            dialogLayout.setHeading(new Label("Imprime"));
            dialogLayout.setBody(new Label("il faut selectionner un evenement d'abord"));
            dialogLayout.setActions(button);
            dialog.show();
            dialog.setOnDialogClosed((JFXDialogEvent event1) -> {
                afficherMesEvenementStackPane.setEffect(null);
            });
            return;
        }
        JasperReportAPI.jasperReportTsMesEvenement(recupererUtilisateurConnecte.getId_Utilisateur());
    }

    @FXML
    private void jasperReportMesReservationSelectionnerGUI() {
        Evenement evenSelect = tableView_participer.getSelectionModel().getSelectedItem();
        if (evenSelect == null) {
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            JFXButton button = new JFXButton("OKAY");
            button.getStyleClass().add("dialog-button");
            JFXDialog dialog = new JFXDialog(afficherReservationStackPane, dialogLayout, JFXDialog.DialogTransition.TOP);
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) -> {
                dialog.close();
            });
            dialogLayout.setHeading(new Label("Imprime"));
            dialogLayout.setBody(new Label("il faut selectionner un evenement d'abord"));
            dialogLayout.setActions(button);
            dialog.show();
            dialog.setOnDialogClosed((JFXDialogEvent event1) -> {
                afficherReservationStackPane.setEffect(null);
            });
            return;
        }
        JasperReportAPI.jasperReportEvenementSelectionner(evenSelect.getId_Evenement());
    }

    @FXML
    private void jasperReportTsMesReservationSelectionnerGUI() {
        Evenement evenSelect = tableView_participer.getSelectionModel().getSelectedItem();
        if (evenSelect == null) {
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            JFXButton button = new JFXButton("OKAY");
            button.getStyleClass().add("dialog-button");
            JFXDialog dialog = new JFXDialog(afficherReservationStackPane, dialogLayout, JFXDialog.DialogTransition.TOP);
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) -> {
                dialog.close();
            });
            dialogLayout.setHeading(new Label("Imprime"));
            dialogLayout.setBody(new Label("il faut selectionner un evenement d'abord"));
            dialogLayout.setActions(button);
            dialog.show();
            dialog.setOnDialogClosed((JFXDialogEvent event1) -> {
                afficherReservationStackPane.setEffect(null);
            });
            return;
        }
        JasperReportAPI.jasperReportTsMesEvenement(recupererUtilisateurConnecte.getId_Utilisateur());
    }
    // API JASPERREPORT END
    /*end bouton page */

 /* accueil evenement*/
    @FXML
    private TextField even_chercher_fx;
    @FXML
    private HBox info_evenement_fx;
    @FXML
    private Text nom_even_ac_fx;
    @FXML
    private ImageView affiche_even_ac_fx;
    /* end accueil evenement*/


 /* commentaire */
    @FXML
    private ListView<String> ListView_commentaire;
    @FXML
    private TextArea commentaire_text_fx;

    @FXML
    private MenuItem modifier_commentaire_fx;

    @FXML
    private MenuItem supprimer_commentaire_fx;

    ObservableList<CommentaireEvenement> dataComment = FXCollections.observableArrayList();

    /*end commentaire */
    /**
     * Utilisateur Connecter *
     */
    /*public void recupererUtilisateurConnecte(Utilisateur utilisateurConnecte){
       nom_utilisateur_ac_fx.setText("salut "+utilisateurConnecte.getNom_Utilisateur());
    }*/
    //j'utilise cette variable pour ajouter l id d'evenement selectionné au commentaire
    public int idEvenementSelectionner;
    public static Utilisateur recupererUtilisateurConnecte;

    //nom_utilisateur_ac_fx.setText("salut"+recupererUtilisateurConnecte.getNom_Utilisateur());
    /**
     * End Utilisateur Connecter *
     */
    @FXML
    private StackPane stackAccueil;

    ServiceEvenement evenementmanipulerdb;

    static ObservableList<Evenement> data = FXCollections.observableArrayList();
    ObservableList<Evenement> dataMyEvenet = FXCollections.observableArrayList();
    //j'utilise cette list pour personnaliser l'affichange "https://www.youtube.com/watch?v=yRXHxhOzkxA&list=PLhs1urmduZ29jTcE1ca8Z6bZNvH_39ayL&index=15"
    ObservableList<Evenement> dataDetails = FXCollections.observableArrayList();

    // j'utilise cette list pour les accueils des evenements 
    ObservableList<Evenement> dataAccueilEven = FXCollections.observableArrayList();

    @FXML
    private TextField rechercheParNomEvenement;

    @FXML
    private TextField rechercheParLieuEvenement;
    //Menu
    @FXML
    private JFXHamburger hamburger;

    @FXML
    private JFXDrawer drawer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        recupererUtilisateurConnecte = WorldfriendshipController.recupererUtilisateurConnecte;
        try {
            Connection con = ServiceEvenement.creationConnexion();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        refreshAccueil();
/*
        // carrousel inisialisation
              
        try {
            GNCarousel carousel = new GNCarousel();

            carousel.setArrows(true);
            carousel.setAutoRide(true);
            List<Evenement> listEvenement = new ArrayList<Evenement>();
            ResultSet resultSet = null;

            listEvenement = ServiceEvenement.afficherTtEvenement();

            for (Evenement event : listEvenement) {
                Image image = new Image(new FileInputStream("C:\\wamp64\\www\\worldfriendship\\web\\Evenement\\image\\affiches\\"  + event.getAffiche_evenement()));
                ImageView affiche = new ImageView(image);

                affiche.setFitHeight(400);
                affiche.setFitWidth(400);

                affiche.getStyleClass().add("image-accueil");
               // Label lb1 = new Label(event.getNom_evenement());
               // lb1.setTextFill(Color.web("white"));
               // lb1.setFont(new Font("Arial", 30));
               // lb1.setScaleX(1.5);
               // lb1.setScaleY(1.5);

                VBox v1 = new VBox(affiche);
                v1.setAlignment(Pos.CENTER);
                v1.setStyle(" -fx-background-color: linear-gradient(to bottom, repeat, #0099ff , #66ccff  );");

                carousel.getItems().add(v1);

            }

            System.out.println("test");

            stackAccueil.getChildren().addAll(carousel);
        } catch (Exception e) {
            e.printStackTrace();
        }
         
        // end carrousel inisialisation
        */
        /* RECHERCHER par nom*/
        // https://www.youtube.com/watch?v=6kL2pQ9KABg
        try {

            List<String> listEvenementAutoCompletetion = new ArrayList<String>();
            listEvenementAutoCompletetion.clear();
            List<Evenement> listEvenement = new ArrayList<Evenement>();

            listEvenement = ServiceEvenement.afficherTtEvenement();

            for (Evenement evenement : listEvenement) {
                listEvenementAutoCompletetion.add(evenement.getNom_evenement());
            }
            TextFields.bindAutoCompletion(rechercheParNomEvenement, listEvenementAutoCompletetion);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {

            FilteredList<Evenement> filterlistData = new FilteredList<>(data, e -> true);
            rechercheParNomEvenement.setOnKeyReleased(e -> {
                rechercheParNomEvenement.textProperty().addListener((observableValue, oldValue, newValue) -> {
                    filterlistData.setPredicate((Predicate<? super Evenement>) evenement -> {
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }
                        String lowerCaseFilter = newValue.toLowerCase();
                        if (evenement.getNom_evenement().toLowerCase().contains(lowerCaseFilter)) {
                            return true;
                        }
                        return false;
                    });
                });
                SortedList<Evenement> sortedData = new SortedList<>(filterlistData);
                sortedData.comparatorProperty().bind(tableView.comparatorProperty());
                tableView.setItems(sortedData);

            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        /* END RECHERCHER par nom*/

 /* RECHERCHER par lieu*/
        try {
            String[] listeGouvernerat = {"Ariana", "Beja", "Ben Arous", "Bizerte", "Gabes", "Gafsa", "Jendouba",
                "Kairouan", "Kasserine", "Kebili", "Le Kef", "Mahdia", "La Manouba", "Medenine", "Monastir",
                "Nabeul", "Sfax", "Sidi Bouzid", "Siliana", "Sousse", "Tataouine", "Tozeur", "Tunis", "Zaghouan"};
            TextFields.bindAutoCompletion(rechercheParLieuEvenement, listeGouvernerat);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {

            FilteredList<Evenement> filterlistData = new FilteredList<>(data, e -> true);
            rechercheParLieuEvenement.setOnKeyReleased(e -> {
                rechercheParLieuEvenement.textProperty().addListener((observableValue, oldValue, newValue) -> {
                    filterlistData.setPredicate((Predicate<? super Evenement>) evenement -> {
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }
                        String lowerCaseFilter = newValue.toLowerCase();
                        if (evenement.getLieu_evenement().toLowerCase().contains(lowerCaseFilter)) {
                            return true;
                        }
                        return false;
                    });
                });
                SortedList<Evenement> sortedData = new SortedList<>(filterlistData);
                sortedData.comparatorProperty().bind(tableView.comparatorProperty());
                tableView.setItems(sortedData);

            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        /* END RECHERCHER par lieu*/
        //Drawer Menu
        try {
            VBox menu = FXMLLoader.load(getClass().getResource("/projet/presentation/MenuEvenementGUI.fxml"));

            drawer.setSidePane(menu);
            drawer.setDefaultDrawerSize(200);
            HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hamburger);
            transition.setRate(-1);
            hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
                transition.setRate(transition.getRate() * -1);
                transition.play();

                if (drawer.isOpened()) {
                    drawer.close();
                } else {
                    drawer.open();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        PopOver popOver1 = ServiceEvenement.popNotification("vous pouvez effecuter des recherches en utilisant les noms des événements");
        rechercheParNomEvenement.setOnMouseEntered(mouseEvent -> {
            popOver1.show(rechercheParNomEvenement);
        });

        rechercheParNomEvenement.setOnMouseExited(mouseEvent -> {
            popOver1.hide();
        });

        PopOver popOver2 = ServiceEvenement.popNotification("vous pouvez effecuter des recherches en utilisant les lieux des événements");
        rechercheParLieuEvenement.setOnMouseEntered(mouseEvent -> {
            popOver2.show(rechercheParLieuEvenement);
        });

        rechercheParLieuEvenement.setOnMouseExited(mouseEvent -> {
            popOver2.hide();
        });
    }

    @FXML
    private VBox accueilVbox;

    public void setDataAccueil() {
        System.out.println("Testttttttttttttttttt");
        content.getChildren().clear();
        accueilVbox.getChildren().clear();
        refreshAccueil();

    }

    ObservableList<Evenement> dataAccueil = FXCollections.observableArrayList();

    public void refreshAccueil() {
        dataAccueil.clear();

        try {

            dataAccueil.addAll(ServiceEvenement.afficherTtEvenement());
            HBox hbox = new HBox();
            content.getChildren().add(hbox);

            int index = 0;

            for (Evenement evenement : dataAccueil) {
                String info = "Bienvenue chez :" + evenement.getNom_evenement()
                        + "\nDate debut d'evenement :" + evenement.getAffi_date_debut()
                        + "\nDate fin d'evenement +" + evenement.getAffi_date_fin()
                        + "\nDescription : " + evenement.getDescription_evenement();
                System.out.println(evenement.getId_Evenement());
                if (index % 3 == 0) {
                    hbox = new HBox();
                    content.getChildren().add(hbox);
                }
                Image image = new Image(new FileInputStream("C:\\wamp64\\www\\worldfriendship\\web\\Evenement\\image\\affiches\\" + evenement.getAffiche_evenement()));
                ImageView affiche = new ImageView(image);
                affiche.setFitHeight(250);
                affiche.setFitWidth(250);

                InfoOverlay infoOverlay = new InfoOverlay(affiche, info);
                VBox vb = new VBox();
                // Text nom = new Text(evenement.getNom_evenement());
                vb.getChildren().addAll(infoOverlay);
                vb.getStyleClass().add("image-accueil");
                hbox.getChildren().add(vb);
                index++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void lireDescriptionEvenement(ActionEvent event) {
        Evenement evenSelect = tableView.getSelectionModel().getSelectedItem();
        if (evenSelect == null) {
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            JFXButton button = new JFXButton("OKAY");
            button.getStyleClass().add("dialog-button");
            JFXDialog dialog = new JFXDialog(afficherTsEvenementStackPane, dialogLayout, JFXDialog.DialogTransition.TOP);
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) -> {
                dialog.close();
            });
            dialogLayout.setHeading(new Label("Ecouter Description"));
            dialogLayout.setBody(new Label("il faut selectionner un evenement d'abord"));
            dialogLayout.setActions(button);
            dialog.show();
            dialog.setOnDialogClosed((JFXDialogEvent event1) -> {
                afficherTsEvenementStackPane.setEffect(null);
            });
            return;
        }

        try {

            SpeechApi.speechApi(evenSelect.getDescription_evenement());

        } catch (Exception ex) {
            Logger.getLogger(EvenementController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /* private void initDrawer() throws IOException {
        VBox menu = FXMLLoader.load(getClass().getResource("MenuEvenementGUI.fxml"));
        drawer.setSidePane(menu);
        HamburgerSlideCloseTransition task = new HamburgerSlideCloseTransition(hamburger);
        task.setRate(-1);
        hamburger.addEventHandler(EventType.ROOT,new EventHandler<Event>(){
        @Override
        public void handle(Event event){
            task.setRate(task.getRate() * -1);
            task.play();
            if(drawer.isHover()){
                drawer.open();
            }else
                drawer.close();
        }
        });
 
    }*/
    @FXML
    private void afficherMesEvenement(ActionEvent event) throws FileNotFoundException {
        afficherMesEvenementDonnees();
    }

    //pour charger les données
    private void afficherMesEvenementDonnees() throws FileNotFoundException {

        //pour refrechir la table
        dataMyEvenet.clear();
        List<Evenement> listEvenement = ServiceEvenement.afficherMyEvenement(recupererUtilisateurConnecte.getId_Utilisateur());

        for (Evenement evenement : listEvenement) {
            Image image = new Image(new FileInputStream("C:\\wamp64\\www\\worldfriendship\\web\\Evenement\\image\\affiches\\" + evenement.getAffiche_evenement()));
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);
            dataMyEvenet.add(new Evenement(evenement.getId_Evenement(), evenement.getNom_evenement(), evenement.getLieu_evenement(),
                    evenement.getCapacite_evenement(), evenement.getDescription_evenement(), evenement.getType_evenement(), evenement.getAffi_date_debut(), evenement.getAffi_date_fin(),
                    imageView, evenement.getAffiche_evenement()));

        }
        // ReadOnlyStringWrapper pour éviter l'erreur Type mismatch: cannot convert from String to ObservableValue<String>
        col_id_ev_mesEvenement.setCellValueFactory(dataMyEvenet -> new ReadOnlyObjectWrapper<Integer>(dataMyEvenet.getValue().getId_Evenement()));
        col_nom_ev_mesEvenement.setCellValueFactory(dataMyEvenet -> new ReadOnlyStringWrapper(dataMyEvenet.getValue().getNom_evenement()));
        col_lieu_ev_mesEvenement.setCellValueFactory(dataMyEvenet -> new ReadOnlyStringWrapper(dataMyEvenet.getValue().getLieu_evenement()));
        col_type_ev_mesEvenement.setCellValueFactory(dataMyEvenet -> new ReadOnlyStringWrapper(dataMyEvenet.getValue().getType_evenement()));
        col_capacite_ev_mesEvenement.setCellValueFactory(dataMyEvenet -> new ReadOnlyObjectWrapper<Integer>(dataMyEvenet.getValue().getCapacite_evenement()));
        col_date_debut_mesEvenement.setCellValueFactory(dataMyEvenet -> new ReadOnlyObjectWrapper<Date>(dataMyEvenet.getValue().getAffi_date_debut()));
        col_date_fin_mesEvenement.setCellValueFactory(dataMyEvenet -> new ReadOnlyObjectWrapper<Date>(dataMyEvenet.getValue().getAffi_date_fin()));
        col_image_mesEvenement.setCellValueFactory(dataMyEvenet -> new ReadOnlyObjectWrapper<ImageView>(dataMyEvenet.getValue().getImage_evenement()));
        tableView_mesEvenement.setItems(dataMyEvenet);
    }

    @FXML
    void afficherTsEvenement(ActionEvent event) throws FileNotFoundException {
        afficherTsEvenementDonnees();
    }

    public void afficherTsEvenementDonnees() throws FileNotFoundException {
        data.clear();
        List<Evenement> listEvenement = ServiceEvenement.afficherTtEvenement();

        for (Evenement evenement : listEvenement) {
            Image image = new Image(new FileInputStream("C:\\wamp64\\www\\worldfriendship\\web\\Evenement\\image\\affiches\\" + evenement.getAffiche_evenement()));
            ImageView imageView = new ImageView(image);
            System.out.println(evenement.getAffiche_evenement());
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);
            data.add(new Evenement(evenement.getId_Evenement(), evenement.getNom_evenement(), evenement.getLieu_evenement(),
                    evenement.getCapacite_evenement(), evenement.getDescription_evenement(), evenement.getType_evenement(), evenement.getAffi_date_debut(), evenement.getAffi_date_fin(),
                    imageView, evenement.getAffiche_evenement()));
        }

        // ReadOnlyStringWrapper pour éviter l'erreur Type mismatch: cannot convert from String to ObservableValue<String>
        col_nom_ev.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getNom_evenement()));
        col_lieu_ev.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getLieu_evenement()));
        col_type_ev.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getType_evenement()));
        col_capacite_ev.setCellValueFactory(data -> new ReadOnlyObjectWrapper<Integer>(data.getValue().getCapacite_evenement()));
        col_date_debut.setCellValueFactory(data -> new ReadOnlyObjectWrapper<Date>(data.getValue().getAffi_date_debut()));

        col_date_fin.setCellValueFactory(data -> new ReadOnlyObjectWrapper<Date>(data.getValue().getAffi_date_fin()));
        col_image.setCellValueFactory(data -> new ReadOnlyObjectWrapper<ImageView>(data.getValue().getImage_evenement()));
        tableView.setItems(data);
    }

    /* @FXML
    private void afficherInfoEvenement(ActionEvent event) {
        String nomEvenement = even_chercher_fx.getText();
        Evenement evenement = ServiceEvenement.chercherEvenement(nomEvenement);
        if (evenement != null) {
            nom_even_ac_fx.setText(evenement.getNom_evenement());
            File file = new File(evenement.getAffiche_evenement());
            Image image = new Image(file.toURI().toString());
            affiche_even_ac_fx.setImage(image);
        } else {
            System.out.println("objet fera8");
        }
    }*/
    @FXML
    private void supprimerEvenement(ActionEvent event) {
        //récuprer la ligne selectionné
        Evenement evenSelect = tableView_mesEvenement.getSelectionModel().getSelectedItem();
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
        dataMyEvenet.remove(evenSelect);
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

    @FXML
    private void afficherValeurSelectionner(ActionEvent event) {
        Evenement evenSelect = tableView_mesEvenement.getSelectionModel().getSelectedItem();
        if (evenSelect == null) {
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            JFXButton button = new JFXButton("OKAY");
            button.getStyleClass().add("dialog-button");
            JFXDialog dialog = new JFXDialog(afficherMesEvenementStackPane, dialogLayout, JFXDialog.DialogTransition.TOP);
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) -> {
                dialog.close();
            });
            dialogLayout.setHeading(new Label("Modification d'événement"));
            dialogLayout.setBody(new Label("il faut selectionner un evenement"));
            dialogLayout.setActions(button);
            dialog.show();
            dialog.setOnDialogClosed((JFXDialogEvent event1) -> {
                afficherMesEvenementStackPane.setEffect(null);
            });
            return;
        }
        try {
            //pour que les champs soient rempli à la place de  // Parent root = FXMLLoader.load(getClass().getResource("AjouterEvenementGUI.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/projet/presentation/ModifierEvenementGUI.fxml"));
            Parent root = loader.load();
            ModifierEvenementController controller = (ModifierEvenementController) loader.getController();
            controller.id_evenement = evenSelect.getId_Evenement();
            controller.nom_evenement_fx.setText(evenSelect.getNom_evenement());
            controller.lieu_evenement_fx.setText(evenSelect.getLieu_evenement());
            controller.description_evenement_fx.setText(evenSelect.getDescription_evenement());
            controller.type_evenement_fx.setValue(evenSelect.getType_evenement());
            // ici j'ai converti la capacité en string parceque la méthode setText n'accepte pas des entiers
            controller.capacite_evenement_fx.setText(Integer.toString(evenSelect.getCapacite_evenement()));
            // puisque j'utilise deux date une pour le sauvgarde et une pour l'affichage donc j'ai converti la date d'affichage au type localdate pour que je puisse l'affiche dans date_debut_evenement_fx
            controller.date_debut_evenement_fx.setValue(Instant.ofEpochMilli(evenSelect.getAffi_date_debut().getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
            controller.date_fin_evenement_fx.setValue(Instant.ofEpochMilli(evenSelect.getAffi_date_fin().getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
            File file = new File("C:\\wamp64\\www\\worldfriendship\\web\\Evenement\\image\\affiches\\" + evenSelect.getAffiche_evenement());
            Image image = new Image(file.toURI().toString());
            //controller.file = new File(evenSelect.getAffiche_evenement());
            controller.imageView.setImage(image);
            controller.nomAfficher = evenSelect.getAffiche_evenement();
            // controller.date_debut_evenement_fx.setValue();
            ///controller.date_debut_evenement_fx.setValue(evenSelect.getDate_debut_evenement());
            // System.out.print(evenSelect.getAffiche_evenement());
            Stage primaryStage = new Stage();

            primaryStage.setTitle("Modifier Evenement");
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/projet/style/EvenementCss.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setMaxWidth(670);
            primaryStage.setMaxHeight(750);
            primaryStage.setMinWidth(570);
            primaryStage.setMinHeight(688);
            primaryStage.show();

            // pour que apres la modification les données s'actualiser automatiquement
            primaryStage.setOnCloseRequest((e) -> {
                try {
                    afficherMesEvenement(new ActionEvent());
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(EvenementController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

        } catch (IOException ex) {
            Logger.getLogger(EvenementController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private void afficherDetailsEvenement(ActionEvent event) {
        Evenement evenSelect = tableView.getSelectionModel().getSelectedItem();
        if (evenSelect == null) {
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            JFXButton button = new JFXButton("OKAY");
            button.getStyleClass().add("dialog-button");
            JFXDialog dialog = new JFXDialog(afficherTsEvenementStackPane, dialogLayout, JFXDialog.DialogTransition.TOP);
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) -> {
                dialog.close();
            });
            dialogLayout.setHeading(new Label("Details Evenement"));
            dialogLayout.setBody(new Label("il faut selectionner un evenement d'abord"));
            dialogLayout.setActions(button);
            dialog.show();
            dialog.setOnDialogClosed((JFXDialogEvent event1) -> {
                afficherTsEvenementStackPane.setEffect(null);
            });
            return;
        }

        try {
            /* Evenement evenement = ServiceEvenement.chercherEvenement(evenSelect.getId_Evenement());
             List<String> listEvenement = new ArrayList<>();
              listEvenement.add(evenement);
          for (Evenement ev : listEvenement) {
            dataDetails.add(new Evenement(ev.getId_Evenement(), ev.getNom_evenement(), ev.getLieu_evenement(),
                    ev.getCapacite_evenement(), ev.getDescription_evenement(), ev.getAffi_date(),
                    new ImageView("file:///" + ev.getAffiche_evenement()), ev.getAffiche_evenement()));
        }*/

 /*System.out.println(evenement.getNom_evenement());
              System.out.println(evenement.getDescription_evenement());
              System.out.println(evenement.getAffiche_evenement());*/
            //pour que les champs soient rempli à la place de  // Parent root = FXMLLoader.load(getClass().getResource("AjouterEvenementGUI.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/projet/presentation/DetailsEvenementGUI.fxml"));
            Parent root = loader.load();
            DetailsEvenementController controller = (DetailsEvenementController) loader.getController();
            controller.nom_evenement_detailsEvenement.setText(evenSelect.getNom_evenement());
            controller.ListView_detailsEvenement.getItems().setAll(ServiceEvenement.chercherEvenement(evenSelect.getId_Evenement()));

            /*List<CommentaireEvenement> listCommentaire = new ArrayList<>();
            List<String> listCommentaireContenue = new ArrayList<>();
            listCommentaire = ServiceCommentaireEvenement.afficherCommentaire(evenSelect.getId_Evenement());
              for (CommentaireEvenement comEvent : listCommentaire) {
            dataComment.add(new CommentaireEvenement(comEvent.getId_commentaire_evenement(),comEvent.getId_evenement(),comEvent.getId_user(),comEvent.getDate_commentaire_affi(),comEvent.getContenu_commentaire()));
        }*/
            // controller.lieu_evenement_fx.setText(evenSelect.getLieu_evenement());
            // controller.description_evenement_fx.setText(evenSelect.getDescription_evenement());
            // ici j'ai converti la capacité en string parceque la méthode setText n'accepte pas des entiers
            // controller.capacite_evenement_fx.setText(Integer.toString(evenSelect.getCapacite_evenement()));
            // puisque j'utilise deux date une pour le sauvgarde et une pour l'affichage donc j'ai converti la date d'affichage au type localdate pour que je puisse l'affiche dans date_debut_evenement_fx
            // controller.date_debut_evenement_fx.setValue(Instant.ofEpochMilli(evenSelect.getAffi_date().getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
            Image image = new Image(new FileInputStream("C:\\wamp64\\www\\worldfriendship\\web\\Evenement\\image\\affiches\\" + evenSelect.getAffiche_evenement()));
            controller.affiche_evenement_detailsEvenement.setImage(image);

            controller.setDataCommentaire(evenSelect.getId_Evenement());
            //InfoOverlay infoOverlay = new InfoOverlay(overlayImage,evenSelect.getDescription_evenement());
            //controller.infoOverlayImageStackPane = new StackPane(infoOverlay);
            // controller.date_debut_evenement_fx.setValue();
            ///controller.date_debut_evenement_fx.setValue(evenSelect.getDate_debut_evenement());
            // System.out.print(evenSelect.getAffiche_evenement());
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Details Evenement");
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            primaryStage.initStyle(StageStyle.TRANSPARENT);
            scene.getStylesheets().add(getClass().getResource("/projet/style/EvenementCss.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException ex) {
            Logger.getLogger(EvenementController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * ****************************************** COMMENTAIRE EVENEMENT
     * *************************************************
     */
    /* @FXML
    public void ajouterCommentaireEvenement() {

        String contenueCommentaireEvenement = commentaire_text_fx.getText();
        // ListView_commentaire.getItems().add(LocalDate.now() + " Ajouter par " + recupererUtilisateurConnecte.getNom_Utilisateur() + ": \"" + contenueCommentaireEvenement + "\"");

        //recupererUtilisateurConnecte.getNom_Utilisateur()+" : "+contenueCommentaireEvenement a la place de requette qui n'a pas marché 
        CommentaireEvenement commentaire = new CommentaireEvenement(idEvenementSelectionner, recupererUtilisateurConnecte.getId_Utilisateur(), LocalDate.now(), recupererUtilisateurConnecte.getNom_Utilisateur() + " : \"" + contenueCommentaireEvenement + "\"");
        //commentaire.setContenu_commentaire(contenueCommentaireEvenement);

        ServiceCommentaireEvenement.ajouterCommentaireEvenement(commentaire);
 
        commentaire_text_fx.clear();
    }*/
    //pour modifier / supprimer commentaire
    /*  @FXML
    private void afficherCommentaireSelectionner(ActionEvent event) {
            commentaire_text_fx.clear();
        String commSelected = ListView_commentaire.getSelectionModel().getSelectedItem();
        if (commSelected == null) {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setHeaderText(null);
            alert1.setContentText(" il faut selectionner votre commentaire ");
            alert1.showAndWait();
            return;
        }
    
       commentaire_text_fx.setText(commSelected);
          
    }*/
    /**
     * participation *
     */
    @FXML
    public void ajouterReservationEvenement() {

        Evenement evenSelect = tableView.getSelectionModel().getSelectedItem();

        if (evenSelect == null) {
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            JFXButton button = new JFXButton("OKAY");
            button.getStyleClass().add("dialog-button");
            JFXDialog dialog = new JFXDialog(afficherTsEvenementStackPane, dialogLayout, JFXDialog.DialogTransition.TOP);
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) -> {
                dialog.close();
            });
            dialogLayout.setHeading(new Label("Reservation Evenement"));
            dialogLayout.setBody(new Label("il faut selectionner un evenement d'abord"));
            dialogLayout.setActions(button);
            dialog.show();
            dialog.setOnDialogClosed((JFXDialogEvent event1) -> {
                afficherTsEvenementStackPane.setEffect(null);
            });
            return;
        }
        // pour tester si l'utilisateur est déja inscrit ou non 
        //ReservationEvenement rs = ServiceReservationEvenement.verificationReservation(recupererUtilisateurConnecte.getId_Utilisateur() ,evenSelect.getId_Evenement() );

        if (ServiceReservationEvenement.verificationReservation(recupererUtilisateurConnecte.getId_Utilisateur(), evenSelect.getId_Evenement())) {
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            JFXButton button = new JFXButton("OKAY");
            button.getStyleClass().add("dialog-button");
            JFXDialog dialog = new JFXDialog(afficherTsEvenementStackPane, dialogLayout, JFXDialog.DialogTransition.TOP);
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) -> {
                dialog.close();
            });
            dialogLayout.setHeading(new Label("Reservation Evenement"));
            dialogLayout.setBody(new Label("vous etes déja inscrit à cet evenement"));
            dialogLayout.setActions(button);
            dialog.show();
            dialog.setOnDialogClosed((JFXDialogEvent event1) -> {
                afficherTsEvenementStackPane.setEffect(null);
            });
            return;

        }

        Evenement evenement = new Evenement();
        ReservationEvenement reservation = new ReservationEvenement(evenSelect.getId_Evenement(), recupererUtilisateurConnecte.getId_Utilisateur(), "Confirmé", "1");
        int status = ServiceReservationEvenement.ajouterReservation(reservation);
        if (status > 0) {
            //sms   
            // SmsApi.sms();
            evenement = ServiceEvenement.chercherEvenementById(evenSelect.getId_Evenement());
            evenement.setCapacite_evenement(evenSelect.getCapacite_evenement() - 1);
            ServiceEvenement.modifierEvenementCapacite(evenement);
            // QR CODE
            QrCodeMailApi.envoyerQrCode(recupererUtilisateurConnecte.getNom_Utilisateur(), evenSelect.getNom_evenement(), evenSelect.getAffi_date_debut());
            Node graphic = null;
            ServiceEvenement.notification("Notification de Reservation", Pos.BOTTOM_RIGHT, graphic, "Evenement Reservé");
            ServiceEvenement.notificationBuilder.show();
            return;
        } else {
            Node graphic = null;
            ServiceEvenement.notification("Notification de Reservation", Pos.BOTTOM_RIGHT, graphic, "Erreur de Reservation");
            ServiceEvenement.notificationBuilder.show();
            return;
        }
    }

    ObservableList<Evenement> dataReservation = FXCollections.observableArrayList();

    @FXML
    public void afficherMesReservation(ActionEvent event) throws FileNotFoundException {
        afficherMesReservationDonnees();
    }

    public void afficherMesReservationDonnees() throws FileNotFoundException {
        dataReservation.clear();
        List<Evenement> listEvenement = new ArrayList<>();
        listEvenement = ServiceReservationEvenement.mesReservation(recupererUtilisateurConnecte.getId_Utilisateur());
        for (Evenement evenement : listEvenement) {
            Image image = new Image(new FileInputStream("C:\\wamp64\\www\\worldfriendship\\web\\Evenement\\image\\affiches\\"+ evenement.getAffiche_evenement()));
            ImageView imageView = new ImageView(image);

            System.out.println(evenement.getAffiche_evenement());
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);
            dataReservation.add(new Evenement(evenement.getId_Evenement(), evenement.getNom_evenement(), evenement.getLieu_evenement(),
                    evenement.getCapacite_evenement(), evenement.getDescription_evenement(), evenement.getType_evenement(), evenement.getAffi_date_debut(), evenement.getAffi_date_fin(),
                    imageView, evenement.getAffiche_evenement()));
        }
        col_nom_ev_participer.setCellValueFactory(dataReservation -> new ReadOnlyStringWrapper(dataReservation.getValue().getNom_evenement()));
        col_lieu_ev_participer.setCellValueFactory(dataReservation -> new ReadOnlyStringWrapper(dataReservation.getValue().getLieu_evenement()));
        col_type_ev_participer.setCellValueFactory(dataReservation -> new ReadOnlyStringWrapper(dataReservation.getValue().getType_evenement()));
        col_capacite_ev_participer.setCellValueFactory(dataReservation -> new ReadOnlyObjectWrapper<Integer>(dataReservation.getValue().getCapacite_evenement()));
        col_date_debut_participer.setCellValueFactory(dataReservation -> new ReadOnlyObjectWrapper<Date>(dataReservation.getValue().getAffi_date_debut()));
        col_date_fin_participer.setCellValueFactory(dataReservation -> new ReadOnlyObjectWrapper<Date>(dataReservation.getValue().getAffi_date_fin()));
        col_image_participer.setCellValueFactory(dataReservation -> new ReadOnlyObjectWrapper<ImageView>(dataReservation.getValue().getImage_evenement()));
        tableView_participer.setItems(dataReservation);
    }

    @FXML
    public void annulerReservation() {
        Evenement reservationSelectionner = tableView_participer.getSelectionModel().getSelectedItem();

        if (reservationSelectionner == null) {
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            JFXButton button = new JFXButton("OKAY");
            button.getStyleClass().add("dialog-button");
            JFXDialog dialog = new JFXDialog(afficherReservationStackPane, dialogLayout, JFXDialog.DialogTransition.TOP);
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) -> {
                dialog.close();
            });
            dialogLayout.setHeading(new Label("Supprission de reservation"));
            dialogLayout.setBody(new Label("il faut selectionner une reservation"));
            dialogLayout.setActions(button);
            dialog.show();
            dialog.setOnDialogClosed((JFXDialogEvent event1) -> {
                afficherReservationStackPane.setEffect(null);
            });
            return;

        }

        Boolean resultat = ServiceReservationEvenement.supprimerReservation(recupererUtilisateurConnecte.getId_Utilisateur(), reservationSelectionner.getId_Evenement());
        //pour refraichir le tableView
        dataReservation.remove(reservationSelectionner);
        if (resultat) {
            Evenement evenement = new Evenement();
            evenement = ServiceEvenement.chercherEvenementById(reservationSelectionner.getId_Evenement());
            evenement.setCapacite_evenement(reservationSelectionner.getCapacite_evenement() + 1);
            ServiceEvenement.modifierEvenementCapacite(evenement);
            //envoyer mail
            QrCodeMailApi.envoyerMailAnnulation(recupererUtilisateurConnecte.getNom_Utilisateur(), reservationSelectionner.getNom_evenement());
            Node graphic = null;
            ServiceEvenement.notification("Notification de Reservation", Pos.BOTTOM_RIGHT, graphic, "Reservation annulé");
            ServiceEvenement.notificationBuilder.show();
            return;
        } else {
            Node graphic = null;
            ServiceEvenement.notification("Notification de Reservation", Pos.BOTTOM_RIGHT, graphic, "erreur d'annulation");
            ServiceEvenement.notificationBuilder.show();
            return;
        }

    }
    
    
      public void setUser(Utilisateur user){
        recupererUtilisateurConnecte = user;
      }

}
