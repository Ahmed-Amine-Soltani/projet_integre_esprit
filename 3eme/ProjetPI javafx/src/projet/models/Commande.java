/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.models;

import java.io.IOException;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Button;

/**
 *
 * @author yassine
 */
public class Commande {
    
    private int id;
    private int panier_id;
    private String file;
    private Date created;
    private Button btn_file;
    private double total;
    private String payer;

    public Commande() {
    }

    public Commande(int id,int panier_id, String file, Date created,double total,String payer) {
        this.id = id;
        this.panier_id = panier_id;
        this.file = file;
        this.created = created;
        this.total = total;
        this.payer = payer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPanier_id() {
        return panier_id;
    }

    public void setPanier_id(int panier_id) {
        this.panier_id = panier_id;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Button getBtn_file() {
        return btn_file;
    }

    public void setBtn_file(Button btn_file) {
        this.btn_file = btn_file;
        this.btn_file.setOnAction(event->{
            try {
                System.out.println("fefef"+this.file);
                Runtime.getRuntime().exec("rundll32 url.dll, FileProtocolHandler " + "C:\\Users\\solta\\Desktop\\ProjetPI\\src\\projet\\files\\"+this.file);
            } catch (IOException ex) {
                Logger.getLogger(Commande.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }
    
    @Override
    public String toString() {
        return "Commande{" + "id=" + id + ", panier_id=" + panier_id + ", file=" + file + ", created=" + created + '}';
    }
    
    
    
    
}
