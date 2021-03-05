/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import projet.service.promo;
import projet.models.Promotion;
import projet.utils.DbConnection;


/**
 * FXML Controller class
 *
 * @author Samplon
 */
public class FXMLController implements Initializable {

    private ListView<String> affiche_promo;
    
    
    private promo cp = new promo();
    @FXML
    private Button ajouter_promotion;
    @FXML
    private Button modifier_promotion;
    @FXML
    private Button supp_promotion;
private final ObservableList<Promotion> data = FXCollections.observableArrayList();
    
    
    
    @FXML
    private AnchorPane rootpane;
    @FXML
    private TableColumn<Promotion, String> nom;
    @FXML
    private TableColumn<Promotion, Date> date_debut;
    @FXML
    private TableColumn<Promotion, Date> date_fin;
    @FXML
    private TableColumn<Promotion, Float> pourcentage;
    @FXML
    private TableColumn<Promotion, Integer> produit;
    @FXML
    private TableView<Promotion> table;
    
 List<String> listd = new ArrayList <String>();
  List<String> listdd = new ArrayList <String>();
      List<Integer> id = new ArrayList <Integer>();
static Statement statement = null;
    PreparedStatement pst;

    DbConnection cnx = DbConnection.getInstance();
    Connection connection = cnx.getConnection();
    @FXML
    private Button statistique;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       
            
      
                         
 
 

            
        
           
                      
         affiche();
       
                         
         
    }    


    @FXML
    private void ajouter_promotion(ActionEvent event) throws IOException {
        
      BorderPane border_pane = (BorderPane) ((Node) event.getSource()).getScene().getRoot();
       FXMLLoader loader = new FXMLLoader(getClass().getResource("/projet/presentation/ajout.fxml"));
       
       Parent root = null;
        try {
             root = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(ProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
       border_pane.setCenter(root);
       
      
     
    }

    @FXML
    private void modifier_promotion(ActionEvent event) throws IOException {
                   int x=index();
           BorderPane border_pane = (BorderPane) ((Node) event.getSource()).getScene().getRoot();
       FXMLLoader loader = new FXMLLoader(getClass().getResource("/projet/presentation/Modifier.fxml"));
       
       Parent root = null;
        try {
             root = loader.load();
                Scene pp = new Scene(root);
        
     ModifierController display = loader.getController();
     display.aff(x);
      
        } catch (IOException ex) {
            Logger.getLogger(ProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
       border_pane.setCenter(root);
       
     
    }

    @FXML
    private void supp_promotion(ActionEvent event) {
        //item = table.getSelectionModel().getSelectedItem();
        //System.out.println(item.getValue().getId());
        
        
       int x=index();
   
       
       
       
       
                  Alert a1 = new Alert(Alert.AlertType.WARNING);
        a1.setTitle("supprimer un promotion");
        a1.setContentText("vous voulez vraiment supprimer cet promotion?");
               Optional<ButtonType> result = a1.showAndWait();
  if (result.get() == ButtonType.OK) {
          
             cp.supprimerPromotion(x);
       
        
    
        
        
        
   
   
             
             //////////////////////////////////////
            Alert a2 = new Alert(Alert.AlertType.INFORMATION);
            a2.setTitle("supprimer une Promotion");
            a2.setContentText("Promotion supprimé avec succés");
            a2.show();
            affiche();
            
            
            
            
 
  }
  else
  {
      a1.close();
  }
      

        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    private int index()
    {
        String a="aaa";
        
            int selectedItem = table.getSelectionModel().getSelectedItem().getId();
            
            int selectedIndex=table.getSelectionModel().getSelectedIndex();
            
            System.out.println(selectedItem);
            return selectedItem;
            
        
    }
    
     
         private void affiche()
    {
      ObservableList<Promotion> items =FXCollections.observableArrayList();
               String requete4="Select * from promotion";    
               
          Statement st2;
        try {
                 statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(requete4);
             
             while(rs.next())
            {
   
                items.add(new Promotion(rs.getInt(1),rs.getString(2),rs.getDate(3),rs.getDate(4),rs.getFloat(5),rs.getFloat(6),rs.getFloat(7),rs.getString(8)));
                
            }   
             
             
             
        } catch (SQLException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
           
              nom.setCellValueFactory(new PropertyValueFactory<Promotion,String >("nom_promotion"));
             date_debut.setCellValueFactory(new PropertyValueFactory<Promotion,Date>("date_debut"));
             date_fin.setCellValueFactory(new PropertyValueFactory<Promotion,Date>("date_fin"));
              pourcentage.setCellValueFactory(new PropertyValueFactory<Promotion,Float >("pourcentage"));
              //prix_initiale.setCellValueFactory(new PropertyValueFactory<Promotion,Float >("prix_initiale"));
              //prix_finale.setCellValueFactory(new PropertyValueFactory<Promotion,Float >("prix_promo"));
             // produit.setCellValueFactory(new PropertyValueFactory<Promotion,Integer >("idProduit"));

            table.setItems(items);
    }

    private void xxx(ActionEvent event) throws IOException {
        
        
      
   FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("produit.fxml"));
                
       Parent ppp = Loader.load();
        Scene pp = new Scene(ppp);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
      stage.setScene(pp);
      stage.showAndWait();
        
        
    }
    public String aujourdhui() {
        
       return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
}
    
    
    
    
    
    

 private int wa9t(String a , String b)
    {
        int nbre=-1;
        
        
      String year1= a.substring(0, 4); 
      int year11= Integer.parseInt(year1);
    
       
       
       String month1=a.substring(5, 7);
       int month11=Integer.parseInt(month1);
      
       String jour1=a.substring(8, 10);   
       int jour11=Integer.parseInt(jour1);
         
       
       /////////////////////////////////////////
       
         String year2= b.substring(0, 4); 
      int year22= Integer.parseInt(year2);
    
       
       
       String month2=b.substring(5, 7);
       int month22=Integer.parseInt(month2);
      
       String jour2=b.substring(8, 10);   
       int jour22=Integer.parseInt(jour2);
       
       
      
        
        if(year11>year22)
        {
            return 1;
        }
        else if((year11==year22)&&(month11>month22))
        {
            return 1;
        }
        else if ((year11==year22)&&(month11==month22)&&(jour11>jour22))
        {
            return 1;
        }
        //////////////////////
        
        
      
        else if ((year11==year22)&&(month11==month22)&&(jour11==jour22))
        {
            return 0;
        }
        
        
        
        return nbre;
    }

    @FXML
    private void stat(ActionEvent event) {
      BorderPane border_pane = (BorderPane) ((Node) event.getSource()).getScene().getRoot();
       FXMLLoader loader = new FXMLLoader(getClass().getResource("/projet/presentation/StatP.fxml"));
       
       Parent root = null;
        try {
             root = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(ProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
       border_pane.setCenter(root);
    }
}



   
         
         
         
         
         
         
         
         
         
         
         
    
    

