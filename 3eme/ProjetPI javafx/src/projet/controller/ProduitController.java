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
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import projet.models.CategorieProduit;
import projet.models.Produit;
import projet.service.CategorieProduitService;
import projet.service.ProduitService;

/**
 * FXML Controller class
 *
 * @author yassine
 */
public class ProduitController implements Initializable {
    @FXML
    private VBox content;
    @FXML
    private VBox v_categorie1;
    @FXML
    private VBox v_categorie2;
    @FXML
    private VBox v_categorie3;
    @FXML
    private VBox v_categorie4;
    @FXML
    private VBox v_categorie5;
    @FXML
    private HBox new_product;
    @FXML
    private Button btn_categorie1;
    @FXML
    private Button btn_categorie2;
    @FXML
    private Button btn_categorie3;
    @FXML
    private Button btn_categorie4;
    @FXML
    private Button btn_categorie5;
    @FXML
    private ScrollPane scroll;
    @FXML
    private HBox meilleursProduit;
    @FXML
    private HBox presente_product;
        
    private ObservableList<Produit> listProduit; 
    private ObservableList<CategorieProduit> listCategorie; 
    private ProduitService ps = new ProduitService();
   
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){ 
        setTopCategorie();
        try {
            setNewProduct();
            setMeilleurProduct();
            setPresentProduct();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    

    private void setNewProduct() throws FileNotFoundException{
        listProduit= FXCollections.observableArrayList(ps.getListProduitsFilter("new",null,null,null));
        
        int index = 0;
        
        for (Produit produit : listProduit) {
            
            VBox content = new VBox();
            
            Image image = new Image( new FileInputStream("C:\\wamp64\\www\\worldfriendship\\web\\assets\\img\\product\\"+produit.getFirstimg()));
            ImageView imageView = new ImageView(image);
            Label title = new Label(produit.getLibelle());
            title.getStyleClass().add("title_prod");
            Label prix = new Label(String.format("%.2f",produit.getPrix())+" TND");            
            imageView.setFitHeight(255);
            imageView.setFitWidth(246);
            
            content.getChildren().addAll(imageView,title,prix);
            Button item = new Button("",content);
            item.setOnAction(event -> {
                detailProduit(event, produit);
            });
            
            new_product.getChildren().add(item);
            
            if(index == 4){
                break;
            }
            
            index++;
            
        }
    }
    
    private void setMeilleurProduct() throws FileNotFoundException{
        
        listProduit= FXCollections.observableArrayList(ps.getListProduitsFilter("best",null,null,null));
        
        int index = 0;
        
        for (Produit produit : listProduit) {
            
            VBox content = new VBox();
            
            Image image = new Image( new FileInputStream("C:\\wamp64\\www\\worldfriendship\\web\\assets\\img\\product\\"+produit.getFirstimg()));
            ImageView imageView = new ImageView(image);
            Label title = new Label(produit.getLibelle());
            title.getStyleClass().add("title_prod");
            Label prix = new Label(String.format("%.2f",produit.getPrix())+" TND");            
            imageView.setFitHeight(255);
            imageView.setFitWidth(246);
            
            content.getChildren().addAll(imageView,title,prix);
            Button item = new Button("",content);
            item.setOnAction(event -> {
                detailProduit(event, produit);
            });
            
            meilleursProduit.getChildren().add(item);
            
            if(index == 2){
                break;
            }
            
            index++;
        }
    }
    
     private void setPresentProduct() throws FileNotFoundException{
        
        listProduit= FXCollections.observableArrayList(ps.getListProduitsFilter("all",null,null,null));
        
        int index = 0;

        for (Produit produit : listProduit) {
            
            VBox content = new VBox();
            
            Image image = new Image( new FileInputStream("C:\\wamp64\\www\\worldfriendship\\web\\assets\\img\\product\\"+produit.getFirstimg()));
            ImageView imageView = new ImageView(image);
            Label title = new Label(produit.getLibelle());
            title.getStyleClass().add("title_prod");
            Label prix = new Label(String.format("%.2f",produit.getPrix())+" TND");            
            imageView.setFitHeight(255);
            imageView.setFitWidth(246);
            
            content.getChildren().addAll(imageView,title,prix);
            Button item = new Button("",content);
            item.setOnAction(event -> {
                detailProduit(event, produit);
            });
            
            presente_product.getChildren().add(item);
            
            if(index == 4){
                break;
            }
            
            index++;
        }
    }

    
    private void setTopCategorie() {
        CategorieProduitService cs = new CategorieProduitService();
        listCategorie= FXCollections.observableArrayList(cs.listBestCategorie());
        Label libelle;
        
        libelle = new Label(listCategorie.get(0).getLibelle());
        String url = "file:/C:/wamp64/www/worldfriendship/web/assets/img/categorie/"+listCategorie.get(0).getPath();
      
        btn_categorie1.setId(listCategorie.get(0).getLibelle());
        btn_categorie1.setStyle("-fx-background-image: url("+url+");");
        v_categorie1.getChildren().add(libelle);
        
        libelle = new Label(listCategorie.get(2).getLibelle());
        url = "file:/C:/wamp64/www/worldfriendship/web/assets/img/categorie/"+listCategorie.get(2).getPath();
      
        btn_categorie2.setId(listCategorie.get(2).getLibelle());
        btn_categorie2.setStyle("-fx-background-image: url("+url+");");
        v_categorie2.getChildren().add(libelle);
        
        
        libelle = new Label(listCategorie.get(1).getLibelle());
        url = "file:/C:/wamp64/www/worldfriendship/web/assets/img/categorie/"+listCategorie.get(1).getPath();
      
        btn_categorie3.setId(listCategorie.get(1).getLibelle());
        btn_categorie3.setStyle("-fx-background-image: url("+url+");");
        v_categorie3.getChildren().add(libelle);
        
        libelle = new Label(listCategorie.get(3).getLibelle());
        url = "file:/C:/wamp64/www/worldfriendship/web/assets/img/categorie/"+listCategorie.get(3).getPath();
       
        btn_categorie4.setId(listCategorie.get(3).getLibelle());
        btn_categorie4.setStyle("-fx-background-image: url("+url+");");
        v_categorie4.getChildren().add(libelle);

        libelle = new Label(listCategorie.get(4).getLibelle());
        url = "file:/C:/wamp64/www/worldfriendship/web/assets/img/categorie/"+listCategorie.get(4).getPath();
      
        btn_categorie5.setId(listCategorie.get(4).getLibelle());
        btn_categorie5.setStyle("-fx-background-image: url("+url+");");
        v_categorie5.getChildren().add(libelle);
    }

    @FXML
    private void newProduit(MouseEvent event){
       
       loadUI(event,"Nouveautés et tendances","classement_combo");
        
    }
    
    
    @FXML
    private void meilleurProduit(MouseEvent event) {
        
        loadUI(event,"Meilleures Produit","classement_combo");

    }
    
    @FXML
    private void tousProduit(MouseEvent event) {  
        loadUI(event,"Produits présentés","classement_combo");
    }
    
     @FXML
    private void btn_categorie1(MouseEvent event) {
        loadUI(event,btn_categorie1.getId(),"categorie_combo");
    }

    @FXML
    private void btn_categorie2(MouseEvent event) {        
        loadUI(event,btn_categorie2.getId(),"categorie_combo");      
    }

    @FXML
    private void btn_categorie3(MouseEvent event) {
      loadUI(event,btn_categorie3.getId(),"categorie_combo");      
    }

    @FXML
    private void btn_categorie4(MouseEvent event) {
          loadUI(event,btn_categorie4.getId(),"categorie_combo");      
    }

    @FXML
    private void btn_categorie5(MouseEvent event) {
          loadUI(event,btn_categorie5.getId(),"categorie_combo");      
    }
    
 
    
    private void loadUI(MouseEvent event,String filter,String ref_combo){
        
       BorderPane border_pane = (BorderPane) ((Node) event.getSource()).getScene().getRoot();
       FXMLLoader loader = new FXMLLoader(getClass().getResource("/projet/presentation/FilterProduit.fxml"));
       
       Parent root = null;
        try {
             root = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(ProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
       border_pane.setCenter(root);
       
       FilterProduitController controller = loader.<FilterProduitController>getController();
       
       controller.setData(filter,ref_combo);
        
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

   

    


   
}
