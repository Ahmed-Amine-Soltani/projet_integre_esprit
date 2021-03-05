package projet.models;

import java.time.LocalDate;
import java.util.Date;

import javafx.scene.image.ImageView;

public class Evenement {

    private int id_evenement;
    private int id_user;
    private String nom_evenement;
    private String type_evenement;
    private String type_reservation;
    private String duree_evenement;
    private String lieu_evenement;
    private int capacite_evenement;
    private String description_evenement;
    
    private String affiche_evenement;
    private ImageView image_evenement;

    private String etat_evenement;
    private String prix_evenement;
    // affi_date pour l'affichage 
    private Date affi_date_debut;
    private Date affi_date_fin;
    // date_debut_evenement pour le persist
    private LocalDate date_debut_evenement;
    private LocalDate date_fin_evenement;

    public Evenement() {
    }

 

    

       
	public Evenement(int id_evenement, String nom_evenement, String lieu_evenement, int capacite_evenement, String description_evenement,String type_evenement,
            Date affi_date_debut, Date affi_date_fin, String affiche_evenement) {
        this.id_evenement = id_evenement;
        this.nom_evenement = nom_evenement;
        this.lieu_evenement = lieu_evenement;
        this.capacite_evenement = capacite_evenement;
        this.description_evenement = description_evenement;
        this.type_evenement = type_evenement ;
        this.affi_date_debut = affi_date_debut;
        this.affi_date_fin = affi_date_fin;
        this.affiche_evenement = affiche_evenement;
    }

    //pour l'affichage (data)
    public Evenement(int id_evenement, String nom_evenement, String lieu_evenement, int capacite_evenement, String description_evenement,String type_evenement,
            Date affi_date_debut, Date affi_date_fin, ImageView image_evenement, String affiche_evenement) {
        this.id_evenement = id_evenement;
        this.nom_evenement = nom_evenement;
        this.lieu_evenement = lieu_evenement;
        this.description_evenement = description_evenement;
        this.capacite_evenement = capacite_evenement;
                this.type_evenement = type_evenement ;
        this.affi_date_debut = affi_date_debut;
        this.affi_date_fin = affi_date_fin;
        this.image_evenement = image_evenement;
        this.affiche_evenement = affiche_evenement;
    }

    @Override
    public String toString() {
        return "Evenement{" + "id_evenement=" + id_evenement + ", id_user=" + id_user + ", nom_evenement=" + nom_evenement + 
                ", type_evenement=" + type_evenement + ", type_reservation=" + type_reservation + ", duree_evenement=" + duree_evenement + ", lieu_evenement=" 
                + lieu_evenement + ", capacite_evenement=" + capacite_evenement + ", description_evenement=" + description_evenement + ", affiche_evenement=" 
                + affiche_evenement + ", image_evenement=" + image_evenement + ", etat_evenement=" + etat_evenement + ", prix_evenement=" + prix_evenement + 
                ", affi_date_debut=" + affi_date_debut + ", affi_date_fin=" + affi_date_fin + ", date_debut_evenement=" + date_debut_evenement + ", date_fin_evenement=" +
                date_fin_evenement + '}';
    }

    
    public int getId_Evenement() {
        return id_evenement;
    }

    public void setId_Evenement(int id_evenement) {
        this.id_evenement = id_evenement;
    }

    public String getNom_evenement() {
        return nom_evenement;
    }

    public void setNom_evenement(String nom_evenement) {
        this.nom_evenement = nom_evenement;
    }

    public String getLieu_evenement() {
        return lieu_evenement;
    }

    public void setLieu_evenement(String lieu_evenement) {
        this.lieu_evenement = lieu_evenement;
    }

    public int getCapacite_evenement() {
        return capacite_evenement;
    }

    public void setCapacite_evenement(int capacite_evenement) {
        this.capacite_evenement = capacite_evenement;
    }

    public String getDescription_evenement() {
        return description_evenement;
    }

    public void setDescription_evenement(String description_evenement) {
        this.description_evenement = description_evenement;
    }

    public String getType_evenement() {
        return type_evenement;
    }

    public void setType_evenement(String type_evenement) {
        this.type_evenement = type_evenement;
    }
    
    
    
    public LocalDate getDate_debut_evenement() {
        return date_debut_evenement;
    }

    public void setDate_debut_evenement(LocalDate dateDebutEvenement) {
        this.date_debut_evenement = dateDebutEvenement;
    }

    public LocalDate getDate_fin_evenement() {
        return date_fin_evenement;
    }

    public void setDate_fin_evenement(LocalDate dateFinEvenement) {
        this.date_fin_evenement = dateFinEvenement;
    }

    public Date getAffi_date_debut() {
        return affi_date_debut;
    }

    public void setAffi_date_debut(Date affi_date_debut) {
        this.affi_date_debut = affi_date_debut;
    }

    public Date getAffi_date_fin() {
        return affi_date_fin;
    }

    public void setAffi_date_fin(Date affi_date_fin) {
        this.affi_date_fin = affi_date_fin;
    }

    public String getAffiche_evenement() {
        return affiche_evenement;
    }

    public void setAffiche_evenement(String affiche_evenement) {
        this.affiche_evenement = affiche_evenement;
    }

    public ImageView getImage_evenement() {
        return image_evenement;
    }

    public void setImage_evenement(ImageView image_evenement) {
        this.image_evenement = image_evenement;
    }
    
       public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }
       

}
