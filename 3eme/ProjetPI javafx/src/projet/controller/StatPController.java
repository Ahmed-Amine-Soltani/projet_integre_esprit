/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import projet.controller.ProduitController;
import projet.service.promo;

/**
 * FXML Controller class
 *
 * @author Samplon
 */
public class StatPController implements Initializable {

    @FXML
    private BarChart<?, ?> vente;
    @FXML
    private NumberAxis y;
    @FXML
    private CategoryAxis x;
  List<Integer> listdd = new ArrayList <Integer>();
   List<String> listddd = new ArrayList <String>();
  private promo cp = new promo();
    @FXML
    private Button retour;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      XYChart.Series series1 = new XYChart.Series();
         listdd=cp.getState();
        for(int i=0;i<listdd.size();i++)
        {
          String g= cp.getState1(listdd.get(i));
          int gg=cp.getState12(listdd.get(i));
               series1.getData().add(new XYChart.Data(g,gg));
               
        }
      vente.getData().addAll(series1);
   
        
         
    }    

    @FXML
    private void retour(ActionEvent event) {
        
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
