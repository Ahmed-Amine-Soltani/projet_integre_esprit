/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.controller;

import com.jfoenix.controls.JFXButton;
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
import javafx.scene.control.TextField;
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
public class PanierController implements Initializable {

    @FXML
    private VBox content;
    
    private Panier panier;
    
    private ObservableList<LigneDePanier> observableList;
    @FXML
    private Label sous_Total;
    @FXML
    private Label total;
    
    @FXML
    private JFXButton commander;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        getPanier();
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
                    Label prix = new Label(String.format("%.2f",produit.getPrix())+" DNT");
                    prix.getStyleClass().add("prix_prod");

                    VBox detail = new VBox();
                    detail.getStyleClass().add("detail_prod");
                    detail.getChildren().addAll(libelle,prix,color,taille);

                    ImageView img_delete = new ImageView("projet/images/trash.png");
                    img_delete.setFitWidth(18);
                    img_delete.setFitHeight(18);
                    
                    Button btn_delete = new Button("",img_delete);
                    btn_delete.getStyleClass().add("btn_delete");
                    btn_delete.setOnAction(event -> {
                              deleteLigne(ligne, panier, produit);
                    });
                    
                    TextField qte = new TextField();
                    qte.setText(String.valueOf(ligne.getQuantite()));
                    
                    qte.setOnKeyTyped(event-> {
                         String ch = event.getCharacter();
                         char c = ch.charAt(0);

                         if(!Character.isDigit(c)){
                           event.consume();
                         }
                         qte.setOnMouseExited(e -> {
                             
                             if(!qte.getText().equals("") && Integer.valueOf(qte.getText()) > 0){
                                 
                                    quantiteEvent(Integer.valueOf(qte.getText()), ligne, produit);
                             }else {
                              qte.setText(String.valueOf(ligne.getQuantite()));
  
                             }
                             
                         });
                    });
                    double tot_prod = produit.getPrix() * Integer.valueOf(qte.getText());
                    
                    Label total_prod = new Label(String.format("%.2f",tot_prod)+" DNT");
                    total_prod.getStyleClass().add("total_prix");
                    HBox item = new HBox();
                    item.getStyleClass().add("item_prod_panier");
                    item.getChildren().addAll(imageView,detail,qte,total_prod,btn_delete);
                    content.getChildren().add(item);

                } catch (FileNotFoundException ex) {
                    Logger.getLogger(SideBarPanierController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        
            if(observableList.size() == 0) {
                Label no_items = new Label("Il n'y a plus d'articles dans votre panier");
                no_items.setStyle("-fx-font-size: 18");
                content.getChildren().add(no_items);
                commander.setDisable(true);
            }
            
            sous_Total.setText(String.format("%.2f",panier.getTotal()-7)+" DNT");
            total.setText(String.format("%.2f",panier.getTotal())+" DNT");
           

        } else {
            Label no_items = new Label("Il n'y a plus d'articles dans votre panier");
            no_items.setStyle("-fx-font-size: 18");
            content.getChildren().add(no_items);
            commander.setDisable(true);
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

    private void quantiteEvent(int qte,LigneDePanier ligne,Produit produit){
         
          PanierService ps = new PanierService();
          if(ps.updateLignePanier(ligne, produit, qte)){
            
              observableList.clear();
              observableList.addAll(ps.getLignePanier(panier.getId()));
          
              getPanier();
          }
    }
    @FXML
    private void commande(MouseEvent event) {
           
            BorderPane border_pane = (BorderPane) ((Node) event.getSource()).getScene().getRoot();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/projet/presentation/Commander.fxml"));

            Parent root = null;
             try {
                  root = loader.load();
             } catch (IOException ex) {
                 Logger.getLogger(ProduitController.class.getName()).log(Level.SEVERE, null, ex);
             }
            border_pane.setCenter(root);
       
    }

    @FXML
    private void continuerAchat(MouseEvent event) {
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
