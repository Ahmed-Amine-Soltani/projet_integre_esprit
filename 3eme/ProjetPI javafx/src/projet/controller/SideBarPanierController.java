/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import projet.models.LigneDePanier;
import projet.models.Panier;
import projet.models.Produit;
import projet.service.PanierService;
import projet.service.ProduitService;

/**
 * FXML Controller class
 *
 * @author yassine
 */
public class SideBarPanierController implements Initializable {

    @FXML
    private VBox content;
    @FXML
    private Label sous_Total;
    @FXML
    private Label total;
    
    private Panier panier;
    
    private ObservableList<LigneDePanier> observableList;
   

   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         getPanier();
    }    

    @FXML
    private void closePanier(MouseEvent event) {
          BorderPane borderpane = (BorderPane) ((Node) event.getSource()).getScene().getRoot();
          borderpane.setRight(null);
    }
    
    
    private void getPanier(){

        ProduitService service_produit = new ProduitService();
        PanierService service_panier = new PanierService();
        
        panier = service_panier.getPanier();
        
        if(panier != null){
         
            observableList = FXCollections.observableArrayList(service_panier.getLignePanier(panier.getId()));

            content.getChildren().clear();

            for (LigneDePanier ligne : observableList) {
                    
                     
                try {

                    Produit produit = service_produit.getProduit(ligne.getProduit_id());
                    
                    System.out.print("efef"+produit.getFirstimg());

                    Image image= new Image( new FileInputStream("C:\\wamp64\\www\\worldfriendship\\web\\assets\\img\\product\\"+produit.getFirstimg()));
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(100);
                    imageView.setFitHeight(100);

                    Button libelle = new Button(produit.getLibelle());
                    libelle.setOnAction(event -> {
                        detailProduit(event, produit);
                    });
                    Rectangle rect_col = new Rectangle(17,17,Color.web(produit.getColor()));
                    Label col = new Label("Couleur: ");
                    HBox color = new HBox(col,rect_col);

                    Label taille = new Label("Taille: "+produit.getTaille());
                    
                    Label prix = new Label(String.format("%.2f",produit.getPrix())+" DNT  x "+ligne.getQuantite());
                    prix.getStyleClass().add("prix_prod");

                    VBox detail = new VBox();
                    detail.getStyleClass().add("detail_prod");
                    detail.getChildren().addAll(libelle,color,taille,prix);
                    
                    Button btn_delete = new Button("X");
                    btn_delete.getStyleClass().add("btn_delete");
                    btn_delete.setOnAction(event -> {
                              deleteLigne(ligne, panier, produit);
                    });
                    HBox item = new HBox();
                    item.getStyleClass().add("item_prod");
                    item.getChildren().addAll(imageView,detail,btn_delete);
                    content.getChildren().add(item);

                } catch (FileNotFoundException ex) {
                    Logger.getLogger(SideBarPanierController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        
            sous_Total.setText(String.format("%.2f",panier.getTotal()-7)+ " DNT");
            total.setText(String.format("%.2f",panier.getTotal())+ " DNT");

        } else {
            
            sous_Total.setText("0.00 DNT");
            total.setText("7.00 DNT");  
           
        }
       
    }
    
    private void deleteLigne(LigneDePanier ligne,Panier panier,Produit produit){
        
        PanierService service_panier = new PanierService();
        
        if(service_panier.deleteLignePanier(ligne,produit)) {
            
            observableList.clear();
            observableList.addAll(service_panier.getLignePanier(panier.getId()));
          
            getPanier();

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
    private void commande(MouseEvent event) {
        
           
       BorderPane border_pane = (BorderPane) ((Node) event.getSource()).getScene().getRoot();
       FXMLLoader loader = new FXMLLoader(getClass().getResource("/projet/presentation/Panier.fxml"));
       
       Parent root = null;
        try {
             root = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(ProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
       border_pane.setCenter(root);
       border_pane.setRight(null);
             
       
    }

 
    
}
