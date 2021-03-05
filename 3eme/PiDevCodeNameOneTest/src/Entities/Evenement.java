package Entities;

import java.util.Date;

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
    private String etat_evenement;
    private String prix_evenement;
    private Date date_debut_evenement;
    private Date date_fin_evenement;

    public Evenement() {
    }

    public Evenement(int id_user) {
        this.id_user = id_user;
    }

    public Evenement(int id_user, int id_evenement, String nom_evenement, String type_evenement,
            String lieu_evenement, int capacite_evenement, String description_evenement,
            String affiche_evenement, Date date_debut_evenement, Date date_fin_evenement) {

        this.id_user = id_user;
        this.id_evenement = id_evenement;
        this.nom_evenement = nom_evenement;
        this.type_evenement = type_evenement;
        this.lieu_evenement = lieu_evenement;
        this.capacite_evenement = capacite_evenement;
        this.description_evenement = description_evenement;
        this.affiche_evenement = affiche_evenement;
        this.date_debut_evenement = date_debut_evenement;
        this.date_fin_evenement = date_fin_evenement;
    }

    public Evenement(int id_user, String nom_evenement, String type_evenement,
            String lieu_evenement, int capacite_evenement, String description_evenement,
            String affiche_evenement, Date date_debut_evenement, Date date_fin_evenement) {

        this.id_user = id_user;

        this.nom_evenement = nom_evenement;

        this.type_evenement = type_evenement;

        this.lieu_evenement = lieu_evenement;

        this.capacite_evenement = capacite_evenement;

        this.description_evenement = description_evenement;

        this.affiche_evenement = affiche_evenement;

        this.date_debut_evenement = date_debut_evenement;

        this.date_fin_evenement = date_fin_evenement;
    }

    public Evenement(String nom_evenement, String affiche_evenement) {
        this.nom_evenement = nom_evenement;
        this.affiche_evenement = affiche_evenement;
    }

    public Evenement(int id_evenement, String nom_evenement, String affiche_evenement) {
        this.id_evenement = id_evenement;
        this.nom_evenement = nom_evenement;
        this.affiche_evenement = affiche_evenement;
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

    public Date getDate_debut_evenement() {
        return date_debut_evenement;
    }

    public void setDate_debut_evenement(Date dateDebutEvenement) {
        this.date_debut_evenement = dateDebutEvenement;
    }

    public Date getDate_fin_evenement() {
        return date_fin_evenement;
    }

    public void setDate_fin_evenement(Date dateFinEvenement) {
        this.date_fin_evenement = dateFinEvenement;
    }

    public String getAffiche_evenement() {
        return affiche_evenement;
    }

    public void setAffiche_evenement(String affiche_evenement) {
        this.affiche_evenement = affiche_evenement;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    @Override
    public String toString() {
        return "Evenement{" + "id_evenement=" + id_evenement + ", id_user=" + id_user + ", nom_evenement=" + nom_evenement + ", type_evenement=" + type_evenement + ", type_reservation=" + type_reservation + ", duree_evenement=" + duree_evenement + ", lieu_evenement=" + lieu_evenement + ", capacite_evenement=" + capacite_evenement + ", description_evenement=" + description_evenement + ", affiche_evenement=" + affiche_evenement + ", etat_evenement=" + etat_evenement + ", prix_evenement=" + prix_evenement + ", date_debut_evenement=" + date_debut_evenement + ", date_fin_evenement=" + date_fin_evenement + '}';
    }

}
