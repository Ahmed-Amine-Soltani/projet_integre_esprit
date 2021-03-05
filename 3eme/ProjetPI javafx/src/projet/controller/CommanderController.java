/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.controller;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Accordion;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import projet.models.Adresse;
import projet.models.Commande;
import projet.models.LigneDePanier;
import projet.models.Panier;
import projet.models.Produit;
import projet.models.Utilisateur;
import projet.service.AdresseUserService;
import projet.service.PanierService;
import projet.service.ProduitService;

/**
 * FXML Controller class
 *
 * @author yassine
 */
public class CommanderController implements Initializable {

    @FXML
    private VBox content;
    @FXML
    private Label sous_Total;
    @FXML
    private Label total;
    @FXML
    private TitledPane info;
    @FXML
    private TitledPane adresses;
    @FXML
    private TitledPane paiement;
    @FXML
    private Accordion accordion;
    @FXML
    private Label countArticle;
    @FXML
    private Label paiement_sousTot;
    @FXML
    private Label paiement_total;
    @FXML
    private RadioButton chèque;
    @FXML
    private ToggleGroup payer;
    @FXML
    private RadioButton bancaire;
      
    private Panier panier;
    
    private ObservableList<LigneDePanier> observableList;
    
    private ProduitService service_produit = new ProduitService();
    private PanierService service_panier = new PanierService();
    @FXML
    private ComboBox<String> pays;
    @FXML
    private TextField nom;
    @FXML
    private TextField email;
    @FXML
    private Label errorsAdresse;
    @FXML
    private Label errorsVille;
    @FXML
    private Label errorsEtat;
    @FXML
    private TextField adresse;
    @FXML
    private TextField ville;
    @FXML
    private TextField etat;
    
    private Utilisateur user = WorldfriendshipController.recupererUtilisateurConnecte;

    private AdresseUserService service = new AdresseUserService();


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
  
        nom.setText(user.getNom_Utilisateur());
        email.setText(user.getEmail());
        
        accordion.setExpandedPane(info);
        adresses.setDisable(true);
        paiement.setDisable(true);
        
        getPanier();

    }    
    
    private void getPanier(){
        
        panier = service_panier.getPanier();
        
         
        observableList = FXCollections.observableArrayList(service_panier.getLignePanier(panier.getId()));

        content.getChildren().clear();

        for (LigneDePanier ligne : observableList) {

            try {

                Produit produit = service_produit.getProduit(ligne.getProduit_id());

                Image image= new Image( new FileInputStream("C:\\wamp64\\www\\worldfriendship\\web\\assets\\img\\product\\"+produit.getFirstimg()));
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(100);
                imageView.setFitHeight(100);

                Label libelle = new Label(produit.getLibelle());

                Label prix = new Label(String.format("%.2f",produit.getPrix())+" DNT");

                ImageView img_delete = new ImageView("projet/images/trash.png");
                img_delete.setFitWidth(18);
                img_delete.setFitHeight(18);

                Label qte = new Label(String.valueOf(ligne.getQuantite()));

                double tot_prod = produit.getPrix() * ligne.getQuantite();

                Label total_prod = new Label(String.format("%.2f",tot_prod)+" DNT");

                HBox item = new HBox();
                item.getStyleClass().add("content_commande");

                item.getChildren().addAll(imageView,libelle,prix,qte,total_prod);
                content.getChildren().add(item);

            } catch (FileNotFoundException ex) {
                Logger.getLogger(SideBarPanierController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        countArticle.setText(panier.getNbr_produit()+" article");
        sous_Total.setText(String.format("%.2f",panier.getTotal()-7)+" DNT");
        total.setText(String.format("%.2f",panier.getTotal())+" DNT");
        
        paiement_sousTot.setText(String.format("%.2f",panier.getTotal()-7)+" DNT");
        paiement_total.setText(String.format("%.2f",panier.getTotal())+" DNT");
        

       
    }


    @FXML
    private void continuerInfo(MouseEvent event) {
        
        ImageView image = new ImageView("projet/images/check.png");
        image.setFitWidth(19);
        image.setFitHeight(16);
        info.setGraphic(image);
        info.setText("INFORMATIONS PERSONNELLES");
        adresses.setDisable(false);
        accordion.setExpandedPane(adresses);
        Adresse adr = service.getAdresse();
        if(adr != null){
            pays.setValue(adr.getPays());
            adresse.setText(adr.getAdresse());
            ville.setText(adr.getVille());
            etat.setText(adr.getEtat());           
        }
        getPays();
    }

    @FXML
    private void continuerAdresses(MouseEvent event) {
        
        if(adresse.getText().equals("")){
            errorsAdresse.setText("Adresse field is required");
        }else if (ville.getText().equals("")){
            errorsVille.setText("Ville field is required");
        }else if (etat.getText().equals("")){
            errorsEtat.setText("Etat field is required");
        }else {
            
            
            Adresse adr = new Adresse(pays.getValue(), adresse.getText(), ville.getText(), etat.getText());
            if(service.updateAdresse(adr)){
                
                ImageView image = new ImageView("projet/images/check.png");
                image.setFitWidth(19);
                image.setFitHeight(16);
                adresses.setGraphic(image);
                adresses.setText("ADRESSES");
                paiement.setDisable(false);
                accordion.setExpandedPane(paiement); 
            }
            
        }
        
    }

    @FXML
    private void panier(MouseEvent event) {
        
        BorderPane border_pane = (BorderPane) ((Node) event.getSource()).getScene().getRoot();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/projet/presentation/Panier.fxml"));

        Parent root = null;
         try {
              root = loader.load();
         } catch (IOException ex) {
             Logger.getLogger(ProduitController.class.getName()).log(Level.SEVERE, null, ex);
         }
        border_pane.setCenter(root);
    }

    @FXML
    private void confirmer(MouseEvent event) {
        
        String paiement = "chèque";
        
        if(bancaire.isSelected()){
            paiement = "bancaire";
        }
        int id =service_panier.getLastCommand();
        
        String file = "Facture"+id+".pdf";
        
        Commande commande = new Commande();
        commande.setFile(file);
        commande.setPayer(paiement);
       
        if ( service_panier.confirmerPanier(commande)){
            
             
              PDFGenerate pdf = new PDFGenerate();
              pdf.generate(id);
              
            //  Email email = new Email();
            //  email.sendMail(file, id);
              
              service_panier.deleteAllLignePanier();
             
              ImageView image = new ImageView("projet/images/cart.png");
              image.setFitWidth(60);
              image.setFitHeight(60);
              Notifications notification = Notifications.create()
                     .title("Success")
                     .text("Commande à été enregistre")
                     .graphic(image)
                     .hideAfter(Duration.seconds(5))
                     .position(Pos.TOP_RIGHT);
               notification.show();
               
              
                BorderPane border_pane = (BorderPane) ((Node) event.getSource()).getScene().getRoot();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/projet/presentation/Produit.fxml"));

                Parent root = null;
                 try {
                      root = loader.load();
                 } catch (IOException ex) {
                     Logger.getLogger(ProduitController.class.getName()).log(Level.SEVERE, null, ex);
                 }
                border_pane.setCenter(root);
        }
        
        
    }
    
    private void getPays(){
        
        JSONParser parser = new JSONParser();
 
        try {
 
        FileReader fileReader  = new FileReader("C:\\Users\\solta\\Desktop\\ProjetPI\\src\\projet\\files\\countries.json");
 
           
        JSONArray countries = (JSONArray)parser.parse(fileReader);
        
        for(Object obj : countries){

            JSONObject tmpObj = (JSONObject) obj;
            pays.getItems().add(tmpObj.get("name").toString());
        }
 
        pays.setValue("Tunisie");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void adresseEvent(KeyEvent event) {
        errorsAdresse.setText("");
    }

    @FXML
    private void villeEvent(KeyEvent event) {
        errorsVille.setText("");
    }

    @FXML
    private void etatEvent(KeyEvent event) {
        errorsEtat.setText("");
    }
    
}
