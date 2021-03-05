/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import projet.models.categorie;
import projet.utils.DbConnection;


/**
 *
 * @author Moez
 */
public class CategorieService implements Icategorie{
    
    static Statement statement = null;
    PreparedStatement pst;

    DbConnection cnx = DbConnection.getInstance();
    Connection connection = cnx.getConnection();
    
    
    @Override
    public void insert(categorie c) {
        String req="INSERT INTO `categorie`(`nom`) VALUES (?)";
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(req);
            preparedStatement.setString(1, c.getNom());
            preparedStatement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(CategorieService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public List<categorie> selectAll2() {
        List<categorie> categories=new ArrayList<categorie>();
        String req="select * from categorie ORDER BY(id) DESC";
        try {
            Statement statement=connection.createStatement();
            ResultSet resultSet =statement.executeQuery(req);
            while(resultSet.next()){
                categorie c=new categorie(resultSet.getInt("id"), resultSet.getString("nom"));
                categories.add(c);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategorieService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return categories;
    }
    
    
    
    @Override
    public HashMap<String,Integer> selectAll() {
        
        HashMap<String,Integer> mapCategorie = new HashMap<String,Integer>();
        
        String req = "SELECT * FROM categorie ORDER BY(id) DESC";
	
        try {
            Statement statement=connection.createStatement();
            ResultSet resultSet =statement.executeQuery(req);
            
            while(resultSet.next()){
                categorie c=new categorie(resultSet.getInt(1),resultSet.getString(2));
                mapCategorie.put(c.getNom(),c.getId());
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategorieService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return mapCategorie;
    }
    
    
    @Override
    public void supprimer(int x) {
        String sql = "DELETE FROM categorie WHERE id = ? ";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, x);

            statement.executeUpdate();

            System.out.println("Categorie Supprimer");

        } catch (SQLException ex) {
            Logger.getLogger(CategorieService.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Categorie non Supprimer");
    }
    
}
