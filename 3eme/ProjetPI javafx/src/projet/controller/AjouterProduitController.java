/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.controller;


import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import projet.models.Produit;
import projet.service.CategorieProduitService;
import projet.service.ProduitService;
import projet.service.SousCategorieProduitService;

/**
 * FXML Controller class
 *
 * @author yassine
 */
public class AjouterProduitController implements Initializable {

    @FXML
    private JFXComboBox<String> categorie_Combo;
    @FXML
    private JFXComboBox<String> sousCategorie_Combo;
    @FXML
    private Label errorsCategorie;
    @FXML
    private Label errorsSousCategorie;
    @FXML
    private Label errorsLibelle;
    @FXML
    private Label errorsPrix;
    @FXML
    private Label errorsStock;
    @FXML
    private Label errorsDescription;
    @FXML
    private Label errorsFirstImg;
    @FXML
    private Label errorsSecondImg;
    @FXML
    private Label errorsThirdImg;
    @FXML
    private ImageView firstImage;
    @FXML
    private ImageView secondImage;
    @FXML
    private ImageView ThirdImage;
    @FXML
    private JFXTextField libelle;
    @FXML
    private JFXTextField prix;
    @FXML
    private JFXTextField stock;
    @FXML
    private JFXColorPicker color;
    @FXML
    private JFXTextArea description;
    
    private FileChooser filechooser;
    
    private Image image;
    
    private File firstFile,secondFile,thirdFile;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          
      CategorieProduitService categorieproduitservice = new CategorieProduitService();
      HashMap<String,Integer> mapCategorie = categorieproduitservice.getAllCategorie();
      
      for (String s : mapCategorie.keySet()) {
         categorie_Combo.getItems().add(s);
      }
        
        
    }    

    @FXML
    private void closeDialog(MouseEvent event) {
            Button btn = (Button) event.getSource();
            Stage stage =  (Stage) btn.getScene().getWindow();
            stage.close();
    }

    @FXML
    private void uploadFirst(MouseEvent event) {
         filechooser = new FileChooser();
         filechooser.getExtensionFilters().addAll(
                            new FileChooser.ExtensionFilter("image files","*.png","*.jpg","*.jpeg"));
         
         firstFile = filechooser.showOpenDialog(null);
         
         if(firstFile != null){
             image = new Image(firstFile.toURI().toString());
             firstImage.setImage(image);
             errorsFirstImg.setText("");
             
         }else {
             errorsFirstImg.setText("Choose Type :  PNG JPEG JPG");
         }

    }

    @FXML
    private void uploadSecond(MouseEvent event) {
         filechooser = new FileChooser();
         filechooser.getExtensionFilters().addAll(
                            new FileChooser.ExtensionFilter("image files","*.png","*.jpg","*.jpeg"));
         
         secondFile = filechooser.showOpenDialog(null);
         
         if(secondFile != null){
             image = new Image(secondFile.toURI().toString());
             secondImage.setImage(image);
             errorsSecondImg.setText("");
             
         }else {
             errorsSecondImg.setText("Choose Type :  PNG JPEG JPG");
         }
    }

    @FXML
    private void uploadThird(MouseEvent event) {
         filechooser = new FileChooser();
         filechooser.getExtensionFilters().addAll(
                            new FileChooser.ExtensionFilter("image files","*.png","*.jpg","*.jpeg"));
         
         thirdFile = filechooser.showOpenDialog(null);
         
         if(thirdFile != null){
             image = new Image(thirdFile.toURI().toString());
             ThirdImage.setImage(image);
             errorsThirdImg.setText("");
             
         }else {
            errorsThirdImg.setText("Choose Type :  PNG JPEG JPG");
         }
    }


    @FXML
    private void ajouter(MouseEvent event) throws IOException {
        
        
        if(categorie_Combo.getValue()==null){
            errorsCategorie.setText("Category field is required");
        }
        else if(sousCategorie_Combo.getValue()==null) {
            errorsSousCategorie.setText("SubCategory field is required");
        }
        else if(libelle.getText().equals("")){
            errorsLibelle.setText("Libelle field is required");
        }
        else if(prix.getText().equals("")){
            errorsPrix.setText("Prix field is required");
        }
        else if(stock.getText().equals("")){
            errorsStock.setText("Stock field is required");
        }
        else if(description.getText().equals("")){
            errorsDescription.setText("Description field is required");
        }
        else if(firstFile==null){
            errorsFirstImg.setText("First image field is required");
        }
        else if(secondFile==null){
            errorsSecondImg.setText("Second image field is required");
        }
        else if(thirdFile==null){
            errorsThirdImg.setText("Third image field is required");
        }else {
            
            String lib = libelle.getText();
            String desc = description.getText();
            Double price = Double.parseDouble(prix.getText());
            Integer qte = Integer.parseInt(stock.getText());
           
            
            String col =  color.getValue().toString();
            col = "#"+ col.substring(2, col.length());

            String firstimg = replaceFile(firstFile.getAbsolutePath());
            
            String secondimg = replaceFile(secondFile.getAbsolutePath());
            
            String thirdimg = replaceFile(thirdFile.getAbsolutePath());
            
            Timestamp date = new Timestamp(System.currentTimeMillis());
             
            /* get Id sous categorie */
            
            CategorieProduitService categorie = new CategorieProduitService();
            HashMap<String,Integer> mapCategorie = categorie.getAllCategorie();
            int id_Categorie = mapCategorie.get(categorie_Combo.getValue());

            SousCategorieProduitService souscategorie = new SousCategorieProduitService();
            HashMap<String,Integer> mapSousCategorie = souscategorie.getSousCategorie(id_Categorie);

            int id_SousCategorie = mapSousCategorie.get(sousCategorie_Combo.getValue());

            
            Produit p = new Produit(id_SousCategorie,lib,desc,price,qte,firstimg,secondimg,thirdimg,col,date);
     
            ProduitService ps = new ProduitService();
            
            if(ps.ajouterProduit(p)){
                
                closeDialog(event);
                ObservableList observableList = FXCollections.observableArrayList( ps.listProduits());
                GestionProduitController gestionproduit = new GestionProduitController();
               
                gestionproduit.observableList.clear();
                gestionproduit.observableList.addAll(observableList);
               
                ImageView image = new ImageView("projet/images/success.png");
                image.setFitWidth(70);
                image.setFitHeight(70);
                Notifications notification = Notifications.create()
                        .title("Success")
                        .text("Ajouter avec succès")
                        .graphic(image)
                        .hideAfter(Duration.seconds(5))
                        .position(Pos.TOP_RIGHT);
                notification.show();
            }
        }
        
    }

    @FXML
    private void CategorieEvent(ActionEvent event) {
        
         
         CategorieProduitService categorie = new CategorieProduitService();
         HashMap<String,Integer> mapCategorie = categorie.getAllCategorie();
         int id_Categorie = mapCategorie.get(categorie_Combo.getValue());
         
         SousCategorieProduitService souscategorie = new SousCategorieProduitService();
         HashMap<String,Integer> mapSousCategorie = souscategorie.getSousCategorie(id_Categorie);
    
         sousCategorie_Combo.getItems().clear();
         
         for (String s : mapSousCategorie.keySet()) {
              sousCategorie_Combo.getItems().add(s);
         }
         
         errorsCategorie.setText("");

    }

    @FXML
    private void SousCategoireEvent(ActionEvent event) {     
        errorsSousCategorie.setText("");      
    }

    @FXML
    private void libelleEvent(KeyEvent event) {
        errorsLibelle.setText("");
    }
    
    @FXML
    private void PrixEvent(KeyEvent event) {
        String ch = event.getCharacter();
        char c = ch.charAt(0);
        
        if(!Character.isDigit(c)){
            event.consume();
        }else {
            errorsPrix.setText("");
        }
        
        
    }

    @FXML
    private void StockEvent(KeyEvent event) {
        
        String ch = event.getCharacter();
        char c = ch.charAt(0);
        
        if(!Character.isDigit(c)){
            event.consume();
        }else {
            errorsStock.setText("");
        }
    }
  
    @FXML
    private void descriptionEvent(KeyEvent event) {
        errorsDescription.setText("");
    }

   

    
    private String generateFileName(){
        
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 25) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    private String replaceFile(String file) {
        

            String extension = file.substring(file.lastIndexOf("."), file.length());
            
            String filename = generateFileName()+extension;
            
            Path sourceDirectory = Paths.get(file);
            Path targetDirectory = Paths.get("C:\\wamp64\\www\\worldfriendship\\web\\assets\\img\\product\\"+filename);
            try {
                //copy source to target using Files Class
                Files.copy(sourceDirectory, targetDirectory);
            } catch (IOException ex) {
                Logger.getLogger(AjouterProduitController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return filename;
    }
   
   
    
}
