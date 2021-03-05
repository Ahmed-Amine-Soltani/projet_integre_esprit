/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;





/**
 *
 * @author Moez
 */
public class comment {

    private int idCommentaire;
    private int idForum;
    private int user;
    private String contenu;
    private String date;

    public comment(int idForum, String contenu, String date) {
        this.idForum = idForum;
        this.contenu = contenu;
        this.date = date;
    }



    public comment(int idForum,int user, String contenu, String date) {
        this.idForum = idForum;
        this.user = user;
        this.contenu = contenu;
        this.date = date;
        }

    public comment(int idCommentaire, int idForum, int user, String contenu, String date) {
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

    public String getDate() {
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

    public void setDate(String date) {
        this.date = date;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

}
