/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.controller;

import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import projet.models.CategorieProduit;
import projet.models.Produit;
import projet.service.CategorieProduitService;
import projet.service.ProduitService;
import projet.service.SousCategorieProduitService;

/**
 * FXML Controller class
 *
 * @author yassine
 */
public class ModifierCategorieProduitController implements Initializable {

    @FXML
    private Label errorsLibelle;
    @FXML
    private JFXTextField libelle;
    @FXML
    private ImageView imageView;
    @FXML
    private Label errorsImage;
  
    private CategorieProduit categorie;
    
     private FileChooser filechooser;
  
    private Image image;
    
    File file;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void closeDialog(MouseEvent event) {
        Button btn = (Button) event.getSource();
        Stage stage =  (Stage) btn.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void libelleEvent(KeyEvent event) {
        errorsLibelle.setText("");
    }
    
     public void setData(CategorieProduit categorieProduit) throws FileNotFoundException {
        
        FileInputStream imageStream;
        Image image;
        
        this.categorie =  categorieProduit;
      
        
        libelle.setText(categorieProduit.getLibelle());
      
        
        imageStream = new FileInputStream("C:\\wamp64\\www\\worldfriendship\\web\\assets\\img\\categorie\\"+categorieProduit.getPath());
        image = new Image (imageStream);
        imageView.setImage(image);
    
   
        
        
        
    }

    @FXML
    private void uploadimg(MouseEvent event) {
         filechooser = new FileChooser();
         filechooser.getExtensionFilters().addAll(
                            new FileChooser.ExtensionFilter("image files","*.png","*.jpg","*.jpeg"));
         
         file = filechooser.showOpenDialog(null);
         if(file != null){
             Image image = new Image(file.toURI().toString());
             imageView.setImage(image);
             errorsImage.setText("");
             
         }else {
             errorsImage.setText("Choose Type :  PNG JPEG JPG");
         }
    }

    @FXML
    private void modifierCategorie(MouseEvent event) {
        
        if(libelle.getText().equals("")){
            errorsLibelle.setText("Libelle field is required");
        }
        
        else if(imageView==null && categorie.getPath().equals("")){
            errorsImage.setText("First image field is required");
        }
        else {
            
            String path;
            
            String lib = libelle.getText();
          
            if(file==null){
              path = categorie.getPath();
            }else {
              path = replaceFile(file.getAbsolutePath());
            }
           
           
            CategorieProduit c = new CategorieProduit(categorie.getId(),lib,path);
     
            CategorieProduitService service = new CategorieProduitService();
            
            if(service.modifierCategorie(c)){
                
                closeDialog(event);
                ObservableList observableList = FXCollections.observableArrayList( service.listCateogries());
                GestionCategorieController gestion = new GestionCategorieController();
                
                gestion.observableList.clear();
                gestion.observableList.addAll(observableList);
                ImageView image = new ImageView("projet/images/success.png");
                image.setFitWidth(70);
                image.setFitHeight(70);
                Notifications notification = Notifications.create()
                        .title("Success")
                        .text("Modifier avec succès")
                        .graphic(image)
                        .hideAfter(Duration.seconds(5))
                        .position(Pos.TOP_RIGHT);
                notification.show();
            }
             
        }
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
            Path targetDirectory = Paths.get("C:\\wamp64\\www\\worldfriendship\\web\\assets\\img\\categorie\\"+filename);
            try {
                //copy source to target using Files Class
                Files.copy(sourceDirectory, targetDirectory);
            } catch (IOException ex) {
                Logger.getLogger(AjouterProduitController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return filename;
    }
   
}
