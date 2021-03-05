/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import projet.service.promo;

/**
 * FXML Controller class
 *
 * @author Samplon
 */
public class SoldeController implements Initializable {

    /**
     * Initializes the controller class.
     */
      private promo cp = new promo();
      private static SimpleDateFormat formater = null;
     private static java.util.Date Ddate=null;
     private static String Sdate=null;
     private static LocalDate LDdate=null;

    @FXML
    private VBox produit_promo;
     List<String> list = new ArrayList <String>();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      int a=0;
       
        
           HBox item = new HBox();
         produit_promo.getChildren().add(item);
           list=cp.esmproduit2();
       int o=list.size();
          for( int i=0;i<o;i++)
            {
                String hh=list.get(i);
                System.out.println(hh);
               float ap=cp.getidpromo(hh);
               
                
                      System.out.println(ap);

               
            
                    try {
                        if(i % 3 == 0){
                            item = new HBox();
                            produit_promo.getChildren().add(item);
                        }
                        VBox content = new VBox();
                        
                        
                        
                        
                      
                        
                        
                        
                        String img=cp.listerProduit(hh);
                        
                      
                        float xxx=cp.getprixpro(hh);
                        
                                    

                        Image image = new Image( new FileInputStream("C:\\wamp64\\www\\worldfriendship\\web\\assets\\img\\product\\"+img));
                        ImageView imageView = new ImageView(image);
                        Label title = new Label(hh);
                        
                        
                        
                        Label prix = new Label((String.valueOf(ap)+" TND"));
                        //prix.setStyle("-fx-strikethrough: true");
                        prix.getStyleClass().add("barre");
                        
                        Label prixpromo = new Label(String.valueOf(xxx)+" TND");
                        prixpromo.setStyle("-fx-font-weight: bold");
                        imageView.setFitHeight(255);
                        imageView.setFitWidth(246);
                        
                        content.getChildren().addAll(imageView,title,prixpromo,prix);
                        Button btn = new Button("",content);
                        
                        item.getChildren().add(btn);
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(SoldeController.class.getName()).log(Level.SEVERE, null, ex);
                    }
         
      
    
}
    }
       
     public static String Date_To_String(java.util.Date dateToConvert) {
        formater = new SimpleDateFormat("yyyy-MM-dd");
          Sdate = formater.format(dateToConvert);
        return Sdate;
    }
     public LocalDate aujourdhui() {
        
       return LocalDate.now();
}
      private int wa9t(LocalDate a , LocalDate b)
    {
        int nbre=-1;
       
        
       int year1= a.getYear();
       int month1=a.getMonthValue();
        int jour1=a.getDayOfMonth();
       
            
       int year2= b.getYear();
       int month2=b.getMonthValue();
        int jour2=b.getDayOfMonth();

     
       
        
       year1= a.getYear();
        month1=a.getMonthValue();
         jour1=a.getDayOfMonth();
       
            
        year2= b.getYear();
        month2=b.getMonthValue();
        jour2=b.getDayOfMonth();
        
        if(year1>year2)
        {
            return 1;
        }
        else if((year1==year2)&&(month1>month2))
        {
            return 1;
        }
        else if ((year1==year2)&&(month1==month2)&&(jour1>jour2))
        {
            return 1;
        }
        
      
         if ((year1==year2)&&(month1==month2)&&(jour1==jour2))
        {
            return 0;
        }
        
        
        
        return nbre;
    }
}
