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
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import projet.models.comment;
import projet.utils.DbConnection;

/**
 *
 * @author Moez
 */
public class CommentService implements Icomment{

    static Statement statement = null;
    PreparedStatement pst;

    DbConnection cnx = DbConnection.getInstance();
    Connection connection = cnx.getConnection();
    
    
    @Override
    public int ajouterCommentaireForum(comment cf) {
        int status = 0;
        String req = "INSERT INTO comment(forum,user,contenu,date) VALUES(?,?,?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(req);

            preparedStatement.setInt(1, cf.getIdForum());
            preparedStatement.setInt(2, cf.getUser());
            preparedStatement.setString(3, cf.getContenu());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
   

            status = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }
    @Override
    public List<String> afficherCommentaire(int IdForum) {
        List<String> commentAfficher = new ArrayList<String>();
        comment c = new comment();

        try {

            String req = "SELECT * FROM `comment` WHERE forum= ? ORDER BY(id) DESC";
          
            
            PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(req);
            preparedStatement.setInt(1, IdForum);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            
            while (resultSet.next()) {

                commentAfficher.add(resultSet.getDate("date") + " Utilisateur "
                        + resultSet.getString("contenu"));
            }

            System.out.println(commentAfficher);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commentAfficher;
    }
    
    
    public void modifier(int id, String b) {
        String sql = "UPDATE comment SET contenu=? WHERE id =?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            
            statement.setString(2, b);
            statement.setInt(3, id);
            statement.executeUpdate();
            System.out.println("comment Modifiée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }
    
    
    
    @Override
    public List<comment> ListCommentaire() {
        List<comment> comments=new ArrayList<comment>();
        String req="select * from comment ORDER BY(id) DESC";
        try {
            PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(req);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                comment c=new comment(resultSet.getInt(1),resultSet.getInt(2), resultSet.getInt(3), resultSet.getString(4), resultSet.getTimestamp(5));
                comments.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategorieService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return comments;
    }
    
    @Override
    public void supprimer(int x) {
        String sql = "DELETE FROM comment WHERE id = ? ";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, x);

            statement.executeUpdate();

            System.out.println("commentaire Supprimer");

        } catch (SQLException ex) {
            Logger.getLogger(ForumService.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Commentairet nom Supprimer");
    }

    
    @Override
     public List<comment> rechercheCommentaires(String str) {
         List<comment> comments=new ArrayList<comment>();
        String sql = "SELECT * FROM comment WHERE contenu LIKE ? ";
        PreparedStatement statement;
        
        try {

            statement = connection.prepareStatement(sql);

            statement.setString(1, "%" + str + "%");
     
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                comment c=new comment(resultSet.getInt(1),resultSet.getInt(2), resultSet.getInt(3), resultSet.getString(4), resultSet.getTimestamp(5));
                
                comments.add(c);
            }
        } catch (SQLException ex) {
            
        }
        return comments;
    }
}
