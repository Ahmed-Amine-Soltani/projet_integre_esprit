/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import projet.service.promo;
/**
 * FXML Controller class
 *
 * @author Samplon
 */
public class ModifierController implements Initializable {
  private promo cp = new promo();
    @FXML
    private Button enregistrer;
    @FXML
    private TextField nom;
    @FXML
    private TextField pourcentage;
    @FXML
    private DatePicker date_fin;
    @FXML
    private AnchorPane rootpane;
    @FXML
    private TextField invi;
    @FXML
    private Button retour;
    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private Label label3;
 List<Float> listd = new ArrayList <Float>();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        
    }    
 
    
    @FXML
    private void enregistrer(ActionEvent event) throws IOException {
        String id =invi.getText();
   
   int idd = Integer.parseInt(id);	
        if(validerChamps(idd))
        {
        Date a=java.sql.Date.valueOf(date_fin.getValue());      
String b=nom.getText();
float p=Float.valueOf(pourcentage.getText());
   
   
   
        
        
   
     
        //rootpane.visibleProperty(false);
                    Alert a1 = new Alert(Alert.AlertType.CONFIRMATION);
        a1.setTitle("Modifier une promotion");
        a1.setContentText("vous voulez vraiment Mdofier cette promotion?");
               Optional<ButtonType> result = a1.showAndWait();
  if (result.get() == ButtonType.OK) {
        cp.modifierPromotion(idd, b, a, p);
        
        
         String ap=cp.esmproduit(idd);
       
       
       
       
                 
  if (result.get() == ButtonType.OK) {

        
        
        
        listd=cp.getidproduit(idd);
               
     
              for(int i=0;i<listd.size();i++)
              {
              
              float prix_ini=cp.getprix2(idd);
              
              System.out.println(prix_ini);
               float y=(prix_ini*p)/100;
        float finale=prix_ini-y;
         System.out.println(prix_ini);
           System.out.println("-----------------------");
        cp. modifierPromotionprix(idd,finale);
              
              
              }
     
        
        
 
        
        
        
        
        
    BorderPane border_pane = (BorderPane) ((Node) event.getSource()).getScene().getRoot();
       FXMLLoader loader = new FXMLLoader(getClass().getResource("/projet/presentation/FXML.fxml"));
       
       Parent root = null;
        try {
             root = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(ProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
       border_pane.setCenter(root);
        
  }     
  }
  else if(result.get() == ButtonType.CANCEL){
   BorderPane border_pane = (BorderPane) ((Node) event.getSource()).getScene().getRoot();
       FXMLLoader loader = new FXMLLoader(getClass().getResource("/projet/presentation/FXML.fxml"));
       
       Parent root = null;
        try {
             root = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(ProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
       border_pane.setCenter(root);
        
  }

        
    }}
    
    public  void aff(int x) 
    {
           
        
         String nom_promo=cp.getnom_promotion(x);
         
         
        
         
         String pourcentagee=cp.getpourcentage(x);
        String hi=Integer.toString(x);
         String a=cp.getdate(x);
         
         
          String year=a.substring(0,4);
        
             
             int years= Integer.parseInt(year);
             String month=a.substring(5,7);
             int months= Integer.parseInt(month);
String day=a.substring(8,10);

             int days= Integer.parseInt(day);

         date_fin.setValue(LocalDate.of(years, months, days));
         
         
        
         nom.setText(nom_promo);
         pourcentage.setText(pourcentagee);
        invi.setText(hi);
         
    }
    
    
    
    
       private boolean validerChamps(int x) {
         
  String a=pourcentage.getText();
    
        Date f=cp.getdate2(x);
     
        
         Date f2=java.sql.Date.valueOf(date_fin.getValue());
         
        Alert alert = new Alert(Alert.AlertType.WARNING);
      
        alert.setTitle("Erreur de saisie");
        alert.setHeaderText("Erreur de saisie");
        if (nom.getText().isEmpty()) {
          label1.setText("Veuillez Saisir le nom du promotion");
            return false;
            
        }
        
        else if (pourcentage.getText().isEmpty()) {
            
          label2.setText("Veuillez Saisir le pourcentage  du promotion");
            return false;
            
        }
       
         else if (f2.compareTo(f)== -1) {
            
                  label3.setText("Veuillez Saisir une superieur  a la date precedante   du promotion");
            return false;
            
        }
        
             else if (date_fin.getValue()==null) {
            
              label3.setText("Veuillez date fin pour la promotion produit");
            return false;
            
        }
         else if (estUnEntier(a)) {
           
               label2.setText("Veuillez Saisir le un pourcentage !! du promotion");
            
            return false;
            
        }
       
        
        
        
        return true;
    }
       
        
    public boolean estUnEntier(String x) {
		try {

                        Float.parseFloat(x);
                        
		} catch (NumberFormatException e){
			return true;
		}
 
		return false;
	}

    @FXML
    private void retour(ActionEvent event) throws IOException {
        
            BorderPane border_pane = (BorderPane) ((Node) event.getSource()).getScene().getRoot();
       FXMLLoader loader = new FXMLLoader(getClass().getResource("/projet/presentation/FXML.fxml"));
       
       Parent root = null;
        try {
             root = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(ProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
       border_pane.setCenter(root);
        
        
    }

    @FXML
    private void yes1(KeyEvent event) {
        
          label1.setText("");
        
    }

    @FXML
    private void yes2(KeyEvent event) {
            label2.setText("");
        
    }

    @FXML
    private void yes3(KeyEvent event) {
          label3.setText("");
        
    }
    
    
    
}
