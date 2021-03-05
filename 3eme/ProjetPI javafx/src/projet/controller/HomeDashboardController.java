/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import projet.service.ExperienceService;
import projet.service.ForumService;
import projet.service.ProduitService;
import projet.service.ServiceEvenement;

/**
 * FXML Controller class
 *
 * @author yassine
 */
public class HomeDashboardController implements Initializable {

    @FXML
    private Label countproduit;
    @FXML
    private Label countevent;
    @FXML
    private Label countexp;
    @FXML
    private Label countforum;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        ProduitService ps = new ProduitService();
        countproduit.setText(String.valueOf(ps.listProduits().size()));
        
        ServiceEvenement se = new ServiceEvenement();
        countevent.setText(String.valueOf(se.afficherTtEvenement().size()));
        
        ForumService fs = new ForumService();
        countforum.setText(String.valueOf(fs.ListQuestion().size()));
        
        ExperienceService es = new ExperienceService();
        countexp.setText(String.valueOf(es.selectAll().size()));
        
        
    }    
    
}
