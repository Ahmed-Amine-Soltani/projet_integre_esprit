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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import projet.models.Utilisateur;
import projet.models.forum;
import projet.utils.DbConnection;

/**
 *
 * @author Moez
 */
public class ForumService implements Iforum{
    
    static Statement statement = null;
    PreparedStatement pst;

    DbConnection cnx = DbConnection.getInstance();
    Connection connection = cnx.getConnection();
    
    
    @Override
    public boolean insert(forum f){
        String req="INSERT INTO forum(categorie,user,title,description,date) VALUES (?,?,?,?,?)";
        
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(req);
            preparedStatement.setInt(1, f.getIdCategorie());
            preparedStatement.setInt(2, f.getUser());
            preparedStatement.setString(3, f.getTitle());
            preparedStatement.setString(4, f.getDescription());
            preparedStatement.setTimestamp(5, f.getDate());
            preparedStatement.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(CategorieService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
        
    }
    @Override
    public List<forum> selectAll(int idCategorie) {
        List<forum> forums=new ArrayList<forum>();
        String req="select * from forum WHERE categorie= ? ORDER BY(id) DESC";
        try {
            PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(req);
            preparedStatement.setInt(1, idCategorie);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                forum f=new forum(resultSet.getInt(1),resultSet.getInt(2), resultSet.getInt(3), resultSet.getString(4), resultSet.getString(5), resultSet.getTimestamp(6));
                forums.add(f);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategorieService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return forums;
    }
    
    List<forum> experiences = new ArrayList<forum>();
    @Override
    public void supprimer(int x) {
        String sql = "DELETE FROM forum WHERE id = ? ";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, x);

            statement.executeUpdate();

            System.out.println("Post Supprimer");

        } catch (SQLException ex) {
            Logger.getLogger(ForumService.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Post nom Supprimer");
    }
    
    
    
    public void modifier(int id, String a, String b) {
        String sql = "UPDATE forum SET title=?,description=? WHERE id =?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, a);
            statement.setString(2, b);
            statement.setInt(3, id);
            statement.executeUpdate();
            System.out.println("Post Modifiée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }
    
    @Override
    public List<forum> ListQuestion() {
        List<forum> forums=new ArrayList<forum>();
        String req="select * from forum ORDER BY(id) DESC";
        try {
            PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(req);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                forum f=new forum(resultSet.getInt(1),resultSet.getInt(2),resultSet.getInt(3),resultSet.getString(4), resultSet.getString(5), resultSet.getTimestamp(6));
                forums.add(f);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategorieService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return forums;
    }
    
    @Override
    public Utilisateur getUserById(int id) {
        Utilisateur u = new Utilisateur();
        String sql = "SELECT username FROM `fos_user` WHERE id= ? ";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                
                u.setNom_Utilisateur(rs.getString(1));
            }
        } catch (Exception e) {
        }
        return u;
    }
    
    @Override
     public List<forum> rechercheQuestions(String str) {
         List<forum> forums=new ArrayList<forum>();
        String sql = "SELECT * FROM forum WHERE title LIKE ? OR description LIKE ? ";
        PreparedStatement statement;
        
        try {

            statement = connection.prepareStatement(sql);

            statement.setString(1, "%" + str + "%");
            statement.setString(2, "%" + str + "%");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                forum f=new forum(resultSet.getInt(1),resultSet.getInt(2), resultSet.getInt(3), resultSet.getString(4), resultSet.getString(5), resultSet.getTimestamp(6));
                
                forums.add(f);
            }
        } catch (SQLException ex) {
            
        }
        return forums;
    }
    
}
