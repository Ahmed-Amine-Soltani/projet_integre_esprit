/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * @author solta
 */
public class CommentaireEvenement {

    private int id_commentaire_evenement;
    private int id_evenement;
    private int id_user;
    private LocalDate date_commentaire;
        private Date date_commentaire_affi;
    private String contenu_commentaire;

    public CommentaireEvenement() {

    }

    public CommentaireEvenement( int id_evenement, int id_user, LocalDate date_commentaire, String contenu_commentaire) {
  
        this.id_evenement = id_evenement;
        this.id_user = id_user;
        this.date_commentaire = date_commentaire;
        this.contenu_commentaire = contenu_commentaire;
    }
        public CommentaireEvenement( int id_evenement, int id_user, Date date_commentaire_affi, String contenu_commentaire) {
  
        this.id_evenement = id_evenement;
        this.id_user = id_user;
        this.date_commentaire_affi = date_commentaire_affi;
        this.contenu_commentaire = contenu_commentaire;
    }
    
        
        public CommentaireEvenement(int id_commentaire_evenement, int id_evenement, int id_user, Date date_commentaire_affi, String contenu_commentaire) {
            this.id_commentaire_evenement = id_commentaire_evenement;
        this.id_evenement = id_evenement;
        this.id_user = id_user;
        this.date_commentaire_affi = date_commentaire_affi;
        this.contenu_commentaire = contenu_commentaire;
    }

    public int getId_commentaire_evenement() {
        return id_commentaire_evenement;
    }

    public int getId_evenement() {
        return id_evenement;
    }

    public int getId_user() {
        return id_user;
    }

    public LocalDate getDate_commentaire() {
        return date_commentaire;
    }
  public Date getDate_commentaire_affi() {
        return date_commentaire_affi;
    }
    public String getContenu_commentaire() {
        return contenu_commentaire;
    }



    public void setId_evenement(int id_evenement) {
        this.id_evenement = id_evenement;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public void setDate_commentaire(LocalDate date_commentaire) {
        this.date_commentaire = LocalDate.now();
    }

    public void setContenu_commentaire(String contenu_commentaire) {
        this.contenu_commentaire = contenu_commentaire;
    }

}
