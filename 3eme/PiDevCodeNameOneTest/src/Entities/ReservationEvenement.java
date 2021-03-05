/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

/**
 *
 * @author solta
 */
public class ReservationEvenement {
    private int idReservation ;
    private int idEvenement;
    private int idUtilisateur ;
    private String etat ;
    private String typeReservation;
    private int tarif;
    private String numeroTicket;

    public ReservationEvenement(int idReservation, int idEvenement, int idUtilisateur, String etat, String typeReservation, int tarif, String numeroTicket) {
        this.idReservation = idReservation;
        this.idEvenement = idEvenement;
        this.idUtilisateur = idUtilisateur;
        this.etat = etat;
        this.typeReservation = typeReservation;
        this.tarif = tarif;
        this.numeroTicket = numeroTicket;
    }
        public ReservationEvenement(int idEvenement, int idUtilisateur, String etat, String numeroTicket) {
        this.idEvenement = idEvenement;
        this.idUtilisateur = idUtilisateur;
        this.etat = etat;
        this.typeReservation = typeReservation;
        this.tarif = tarif;
        this.numeroTicket = numeroTicket;
    }

    public ReservationEvenement() {
       
    }

    public int getIdReservation() {
        return idReservation;
    }

    public int getIdEvenement() {
        return idEvenement;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public String getEtat() {
        return etat;
    }

    public String getTypeReservation() {
        return typeReservation;
    }

    public int getTarif() {
        return tarif;
    }

    public String getNumeroTicket() {
        return numeroTicket;
    }

    public void setIdReservation(int idReservation) {
        this.idReservation = idReservation;
    }



    public void setIdEvenement(int idEvenement) {
        this.idEvenement = idEvenement;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public void setTypeReservation(String typeReservation) {
        this.typeReservation = typeReservation;
    }

    public void setTarif(int tarif) {
        this.tarif = tarif;
    }

    public void setNumeroTicket(String numeroTicket) {
        this.numeroTicket = numeroTicket;
    }
    
    
    
}
