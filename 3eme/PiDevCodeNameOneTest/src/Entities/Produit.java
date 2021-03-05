/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;


/**
 *
 * @author yassine
 */
public class Produit {
    
    private int id;
    private String libelle;
    private String description;
    private String souscategorie;
    private double prix;
    private int stock;
    private String firstimg;
    private String secondimg;
    private String thirdimg;
    private String color;
    private String taille;

    public Produit() {
    }

     
    public Produit(String libelle, String description, String souscategorie, double prix, int stock, String firstimg, String secondimg, String thirdimg, String color, String taille) {
        this.libelle = libelle;
        this.description = description;
        this.souscategorie = souscategorie;
        this.prix = prix;
        this.stock = stock;
        this.firstimg = firstimg;
        this.secondimg = secondimg;
        this.thirdimg = thirdimg;
        this.color = color;
        this.taille = taille;
    }

    public Produit(int id, String libelle, String description, double prix, int stock, String firstimg, String secondimg, String thirdimg, String color, String taille) {
        this.id = id;
        this.libelle = libelle;
        this.description = description;
        this.prix = prix;
        this.stock = stock;
        this.firstimg = firstimg;
        this.secondimg = secondimg;
        this.thirdimg = thirdimg;
        this.color = color;
        this.taille = taille;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getTaille() {
        return taille;
    }

    public void setTaille(String taille) {
        this.taille = taille;
    }

    public String getSouscategorie() {
        return souscategorie;
    }

    public void setSouscategorie(String souscategorie) {
        this.souscategorie = souscategorie;
    }
    

    
    
    
    
}
