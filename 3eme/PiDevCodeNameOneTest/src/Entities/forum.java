/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.util.Date;



/**
 *
 * @author Moez
 */
public class forum {
    private int id;
    private int idCategorie;
    private int user;
    private String title;
    private String description;
    private String date;
    private Utilisateur Uti;

    public forum() {
    }

    public forum(int id, String title, String description,String date) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
    }
    

    public forum(int id, int idCategorie, int user, String title, String description, String date) {
        this.id = id;
        this.idCategorie = idCategorie;
        this.user = user;
        this.title = title;
        this.description = description;
        this.date = date;
    }

    public forum(int idCategorie, int user, String title, String description, String date) {
        this.idCategorie = idCategorie;
        this.user = user;
        this.title = title;
        this.description = description;
        this.date = date;
    }

    public forum(int id, int idCategorie, Utilisateur Uti, String title, String description,String date) {
        this.id = id;
        this.idCategorie = idCategorie;
        this.Uti = Uti;
        this.title = title;
        this.description = description;
        this.date = date;
        
    }
    
    

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }
    
    

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "forum{" + "id=" + id + ", categorie=" + idCategorie + ", user=" + user + ", title=" + title + ", description=" + description + ", date=" + date + '}';
    }

   
    
    

    

    

    

    
    
}
