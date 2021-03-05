/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.models;

import java.sql.Timestamp;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import projet.controller.GestionSousCategorieController;
import projet.service.SousCategorieProduitService;

/**
 *
 * @author yassine
 */
public class SousCategorieProduit {
    
    private int id;
    private int categorie_id;
    private String categorie;
    private String libelle;
    private Timestamp Created;
    private Button btn_delete;


    public SousCategorieProduit() {
    }

    public SousCategorieProduit(int id, int categorie_id, String libelle, Timestamp Created) {
        this.id = id;
        this.categorie_id = categorie_id;
        this.libelle = libelle;
        this.Created = Created;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategorie_id() {
        return categorie_id;
    }

    public void setCategorie_id(int categorie_id) {
        this.categorie_id = categorie_id;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    
    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Timestamp getCreated() {
        return Created;
    }

    public void setCreated(Timestamp Created) {
        this.Created = Created;
    }

    public Button getBtn_delete() {
        return btn_delete;
    }

    public void setBtn_delete(Button btn_delete) {
        this.btn_delete = btn_delete;
        
        this.btn_delete.setOnAction(event -> {
               Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
               alert.setTitle("Confirmation");
               alert.setHeaderText("Vous voulez vraiment supprimer cet sous categorie");

               Optional<ButtonType> result = alert.showAndWait();
               if (result.get() == ButtonType.OK){
                   SousCategorieProduitService service = new SousCategorieProduitService();
                   if(service.supprimerSousCategorie(id)){
                        GestionSousCategorieController gestionsouscategorie = new GestionSousCategorieController();
                        gestionsouscategorie.observableList.remove(this);
                   }
               } else {

               }
           });
    }

    @Override
    public String toString() {
        return "SousCategorieProduit{" + "id=" + id + ", categorie_id=" + categorie_id + ", categorie=" + categorie + ", libelle=" + libelle + ", Created=" + Created + ", btn_delete=" + btn_delete + '}';
    }
    
    
    
    
}
