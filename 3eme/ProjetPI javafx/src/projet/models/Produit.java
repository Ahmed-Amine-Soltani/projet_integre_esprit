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
import javafx.scene.image.ImageView;
import projet.controller.GestionProduitController;
import projet.service.ProduitService;

/**
 *
 * @author yassine
 */
public class Produit {
    private int id;
    private int souscategorie_id;
    private String souscategorie;
    private String libelle;
    private String description;
    private double prix;
    private int stock;
    private String firstimg;
    private String secondimg;
    private String thirdimg;
    private String color;
    private String taille;
    private Timestamp created;
    private ImageView Image;
    private Button btn_delete;
    

    public Produit() {
    }
    
     public Produit(int souscategorie_id, String libelle, String description, double prix, int stock, String firstimg, String secondimg, String thirdimg, String color, Timestamp created) {
        this.souscategorie_id = souscategorie_id;
        this.libelle = libelle;
        this.description = description;
        this.prix = prix;
        this.stock = stock;
        this.firstimg = firstimg;
        this.secondimg = secondimg;
        this.thirdimg = thirdimg;
        this.color = color;
        this.created = created;
    }
     
    public Produit(int id, int souscategorie_id, String libelle, String description, double prix, int stock,String taille, String firstimg, String secondimg, String thirdimg, String color, Timestamp created) {
        this.id = id;
        this.souscategorie_id = souscategorie_id;
        this.libelle = libelle;
        this.description = description;
        this.prix = prix;
        this.stock = stock;
        this.firstimg = firstimg;
        this.secondimg = secondimg;
        this.thirdimg = thirdimg;
        this.color = color;
        this.taille = taille;
        this.created = created;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSouscategorie_id() {
        return souscategorie_id;
    }

    public void setSouscategorie_id(int souscategorie_id) {
        this.souscategorie_id = souscategorie_id;
    }

    public String getSouscategorie() {
        return souscategorie;
    }

    public void setSouscategorie(String souscategorie) {
        this.souscategorie = souscategorie;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getTaille() {
        return taille;
    }

    public void setTaille(String taille) {
        this.taille = taille;
    }
    
    

    public String getFirstimg() {
        return firstimg;
    }

    public void setFirstimg(String firstimg) {
        this.firstimg = firstimg;
    }

    public String getSecondimg() {
        return secondimg;
    }

    public void setSecondimg(String secondimg) {
        this.secondimg = secondimg;
    }

    public String getThirdimg() {
        return thirdimg;
    }

    public void setThirdimg(String thirdimg) {
        this.thirdimg = thirdimg;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public ImageView getImage() {
        return Image;
    }

    public void setImage(ImageView Image) {
        this.Image = Image;
    }

    
    public Button getBtn_delete() {
        return btn_delete;
    }

    public void setBtn_delete(Button btn_delete) {
        this.btn_delete = btn_delete;
        
        this.btn_delete.setOnAction(event -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Vous voulez vraiment supprimer cet produit");
               
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    ProduitService service = new ProduitService();
                    if(service.supprimerProduit(id)){
                         GestionProduitController gestionproduit = new GestionProduitController();
                         gestionproduit.observableList.remove(this);
                    }
                } else {
                    
                }
            });
    }

    @Override
    public String toString() {
        return "Produit{" + "id=" + id + ", souscategorie_id=" + souscategorie_id + ", souscategorie=" + souscategorie + ", libelle=" + libelle + ", description=" + description + ", prix=" + prix + ", stock=" + stock + ", firstimg=" + firstimg + ", secondimg=" + secondimg + ", thirdimg=" + thirdimg + ", color=" + color + ", created=" + created + ", Image=" + Image + ", btn_delete=" + btn_delete + '}';
    }
    
    
    

    
}