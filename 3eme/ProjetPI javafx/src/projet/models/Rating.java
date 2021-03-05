/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.models;

/**
 *
 * @author houba
 */
public class Rating {
    private int id, user, experience;
    private float rate;

    public Rating() {
    }

    public Rating(int id, int user, int experience, float rate) {
        this.id = id;
        this.user = user;
        this.experience = experience;
        this.rate = rate;
    }
    
        public Rating(int user, int experience, float rate) {
        this.user = user;
        this.experience = experience;
        this.rate = rate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "Rating{" + "id=" + id + ", user=" + user + ", experience=" + experience + ", rate=" + rate + '}';
    }
    
    
    
}
