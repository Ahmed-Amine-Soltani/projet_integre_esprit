/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.models;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import projet.controller.GestionCategorieController;
import projet.controller.GestionProduitController;
import projet.service.CategorieProduitService;
import projet.service.ProduitService;

/**
 *
 * @author yassine
 */
public class CategorieProduit {
    
    private int id;
    private String libelle;
    private ImageView image;
    private String path;
    private Button btn_delete;
    
    public CategorieProduit() {
    }

    public CategorieProduit(int id, String libelle,String path) {
        this.id = id;
        this.libelle = libelle;
        this.path = path;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    

    public Button getBtn_delete() {
        return btn_delete;
    }

    public void setBtn_delete(Button btn_delete) {
        this.btn_delete = btn_delete;
        
        this.btn_delete.setOnAction(event -> {
               Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
               alert.setTitle("Confirmation");
               alert.setHeaderText("Vous voulez vraiment supprimer cet categorie");

               Optional<ButtonType> result = alert.showAndWait();
               if (result.get() == ButtonType.OK){
                   CategorieProduitService service = new CategorieProduitService();
                   if(service.supprimerCategorie(id)){
                        GestionCategorieController gestioncategorie = new GestionCategorieController();
                        gestioncategorie.observableList.remove(this);
                   }
               } else {

               }
           });
    }

    @Override
    public String toString() {
        return "CategorieProduit{" + "id=" + id + ", libelle=" + libelle + ", image=" + image + ", btn_delete=" + btn_delete + '}';
    }
    
    
}
