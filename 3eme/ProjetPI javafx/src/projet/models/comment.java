/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.models;

import java.sql.Timestamp;

/**
 *
 * @author Moez
 */
public class comment {

    private int idCommentaire;
    private int idForum;
    private int user;
    private String contenu;
    private Timestamp date;



    public comment(int idForum,int user, String contenu) {
        this.idForum = idForum;
        this.user = user;
        this.contenu = contenu;
        }

    public comment(int idCommentaire, int idForum, int user, String contenu, Timestamp date) {
        this.idCommentaire = idCommentaire;
        this.idForum = idForum;
        this.user = user;
        this.contenu = contenu;
        this.date = date;
    }
    
    

    public comment() {

    }

    public int getIdCommentaire() {
        return idCommentaire;
    }

    public int getIdForum() {
        return idForum;
    }

    public String getContenu() {
        return contenu;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setIdCommentaire(int idCommentaire) {
        this.idCommentaire = idCommentaire;
    }

    public void setIdForum(int idForum) {
        this.idForum = idForum;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

}
