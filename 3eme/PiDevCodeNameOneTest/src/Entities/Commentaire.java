/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

/**
 *
 * @author houba
 */
public class Commentaire {
    int id, experience, user;
    String contenu, date;

    public Commentaire() {
    }

    public Commentaire(int id, int experience, int user, String contenu, String date) {
        this.id = id;
        this.experience = experience;
        this.user = user;
        this.contenu = contenu;
        this.date = date;
    }

    public Commentaire(int experience, String contenu, String date) {
        this.experience = experience;
        this.contenu = contenu;
        this.date = date;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    
}
