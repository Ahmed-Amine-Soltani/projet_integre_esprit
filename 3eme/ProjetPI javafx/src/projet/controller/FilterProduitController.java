/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.controller;

import com.jfoenix.controls.JFXComboBox;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import projet.models.Produit;
import projet.service.CategorieProduitService;
import projet.service.ProduitService;
import projet.service.SousCategorieProduitService;

/**
 * FXML Controller class
 *
 * @author yassine
 */
public class FilterProduitController implements Initializable {

    @FXML
    private Label title_filter;
    
    @FXML
    private JFXComboBox<String> classement_combo;

    @FXML
    private JFXComboBox<String> categorie_combo;

    @FXML
    private JFXComboBox<String> souscategorie_combo;

    @FXML
    private JFXComboBox<String> tri_combo;
    
    @FXML
    private VBox content_product;
    
    private ObservableList<Produit> listProduit; 
    
    private HBox row;
    
    private String ref_combo;
    
    private String filter;
    
    private CategorieProduitService categorieproduitservice = new CategorieProduitService();
    
    private ProduitService ps = new ProduitService();

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
      
        classement_combo.getItems().addAll("Produits présentés","Nouveautés et tendances","Meilleures Produit");
        tri_combo.getItems().addAll("Nom, A à Z","Nom, Z à A","Prix,croissant","Prix, décroissant");
                      
    } 
    
    private void getProduit(){
        
        content_product.getChildren().clear();
        
        int index = 0;    
       
        for (Produit produit : listProduit) {
               if(index % 5 == 0) {
                   row = new HBox();
                   row.getStyleClass().add("content-item");
                   content_product.getChildren().add(row);
               }
               
            VBox content = new VBox();
            
             Image image=null;
            try {
                image = new Image( new FileInputStream("C:\\wamp64\\www\\worldfriendship\\web\\assets\\img\\product\\"+produit.getFirstimg()));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FilterProduitController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            ImageView imageView = new ImageView(image);
            
            Label title = new Label(produit.getLibelle());
            title.getStyleClass().add("title_prod");

            title.setStyle("-fx-font-weight: bold");
            Label prix = new Label(String.format("%.2f",produit.getPrix())+" TND");            
            imageView.setFitHeight(255);
            imageView.setFitWidth(246);

            
            content.getChildren().addAll(imageView,title,prix);
            Button item = new Button("",content);
            item.setOnAction(event -> {
                detailProduit(event, produit);
            });
            
            row.getChildren().add(item);
            
            index++;
        }
        
    }
    public void setData(String filter,String ref_combo){

        getCategorie();

        if(ref_combo.equals("categorie_combo")){  
            categorie_combo.setValue(filter);
            this.filter = "Produits présentés";
            title_filter.setText("Produits présentés");
            classement_combo.setValue("Produits présentés");
            getSousCategorie();
        }
        else {     
              this.filter = filter;
              title_filter.setText(filter);
              classement_combo.setValue(filter);
              this.ref_combo = ref_combo;     
        }
        
        filter();
        
    }

    private void filter(){
        
        String categorie = categorie_combo.getValue();
        String classement = "all";
        String sous_categorie = souscategorie_combo.getValue();
        String tri = tri_combo.getValue();
        
        if(classement_combo.getValue().equals("Nouveautés et tendances")){
            classement = "new";
        }else if(classement_combo.getValue().equals("Meilleures Produit")){
            classement = "best";         
        }

        if(tri_combo.getValue() != null) {
         
            switch (tri_combo.getValue()) {
                case "Nom, A à Z":
                    tri = "nom_asc";
                    break;
                case "Nom, Z à A":
                    tri = "nom_desc";
                    break;
                case "Prix,croissant":        
                    tri = "prix_asc";
                    break;
                case "Prix, décroissant":
                    tri = "prix_desc";
                    break;
                default:
                    break;
            }
        }
       
        
        listProduit= FXCollections.observableArrayList(ps.getListProduitsFilter(classement,categorie,sous_categorie,tri));  
        getProduit();
    }
    
    
    @FXML
    private void reinitialiser(MouseEvent event) {
        
        tri_combo.getItems().clear();
        tri_combo.getItems().addAll("Nom, A à Z","Nom, Z à A","Prix,croissant","Prix, décroissant");

        souscategorie_combo.getItems().clear();
        getCategorie();
        
        title_filter.setText(filter);
        classement_combo.setValue(filter);
        
    }

    @FXML
    private void CagetorieEvent(ActionEvent event) {
         
         getSousCategorie();
         filter();

    }
    
    private void getCategorie(){
        
        categorie_combo.getItems().clear();
        
        HashMap<String,Integer> mapCategorie = categorieproduitservice.getAllCategorie();

        for (String s : mapCategorie.keySet()) {
           categorie_combo.getItems().add(s);
        }
    }
    
    private void getSousCategorie(){
     
        if(categorie_combo.getValue()!= null){
          
            HashMap<String,Integer> mapCategorie = categorieproduitservice.getAllCategorie();

            int id_Categorie = mapCategorie.get(categorie_combo.getValue());

            SousCategorieProduitService souscategorie = new SousCategorieProduitService();
            HashMap<String,Integer> mapSousCategorie = souscategorie.getSousCategorie(id_Categorie);

            souscategorie_combo.getItems().clear();

            for (String s : mapSousCategorie.keySet()) {
                 souscategorie_combo.getItems().add(s);
            }  
        }
       
    }
     
    private void detailProduit (ActionEvent event,Produit p){
       BorderPane border_pane = (BorderPane) ((Node) event.getSource()).getScene().getRoot();
       FXMLLoader loader = new FXMLLoader(getClass().getResource("/projet/presentation/DetailProduit.fxml"));
       
       Parent root = null;
        try {
             root = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(ProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
       border_pane.setCenter(root);
       
       DetailProduitController controller = loader.<DetailProduitController>getController();
       
        try {
            controller.setData(p);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
         

    @FXML
    private void classementEvent(ActionEvent event) {
        
        title_filter.setText(classement_combo.getValue());
        filter();
    }

    @FXML
    private void SousCategorieEvent(ActionEvent event) {
        filter();
    }

    @FXML
    private void TriEvent(ActionEvent event) {
        filter();
    }
    
    

  
}
    

