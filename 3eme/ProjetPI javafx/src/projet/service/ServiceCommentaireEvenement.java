/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.service;

import projet.models.CommentaireEvenement;
import projet.models.Evenement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author solta
 */
public class ServiceCommentaireEvenement {

    public static int ajouterCommentaireEvenement(CommentaireEvenement commentaireEvenement) {
        int status = 0;
        String sql = "INSERT INTO commentaireevenement(	id_user,id_evenement,date_commentaire,contenu) VALUES(?,?,?,?)";
        System.out.println(sql);

        try {
            Connection connexion = ServiceEvenement.creationConnexion();
            PreparedStatement preparedStatement = (PreparedStatement) connexion.prepareStatement(sql);
            preparedStatement.setInt(1, commentaireEvenement.getId_user());
            preparedStatement.setInt(2, commentaireEvenement.getId_evenement());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(4, commentaireEvenement.getContenu_commentaire());

            status = preparedStatement.executeUpdate();
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    /*   public static int modifierCommentaireEvenement(CommentaireEvenement commentaire) {
        int status = 0;
        String sql = "UPDATE commentaireevenement SET date_commentaire = ? , contenu = ? ,Etat_Commentaire = ? where id = ?";
        try {

            Connection connexion = ServiceEvenement.creationConnexion();
            PreparedStatement preparedStatement = (PreparedStatement) connexion.prepareStatement(sql);

            preparedStatement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(2, commentaire.getContenu_commentaire());
            preparedStatement.setString(3, "modifié");
            preparedStatement.setInt(4, commentaire.getId_commentaire_evenement());

          
            status = preparedStatement.executeUpdate();
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }*/
    public static List<CommentaireEvenement> afficherCommentaire(int idEvenement) {
        List<CommentaireEvenement> tsCommentaire = new ArrayList<CommentaireEvenement>();
        Evenement evenement = new Evenement();
        ResultSet resultSet = null;
        // ResultSet resultSet2 = null;

        try {
            Connection connexion = ServiceEvenement.creationConnexion();
            String sql = "select * from commentaireevenement where id_evenement = ?";
            PreparedStatement preparedStatement = (PreparedStatement) connexion.prepareStatement(sql);
            preparedStatement.setInt(1, idEvenement);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                //mou7awla fechla pour récuperer le nom d'utilisateur grace a une requette donc j'ai utilisé recupererUtilisateurConnecte.getNom_Utilisateur()+" : "+contenueCommentaireEvenement
                /*String sql2 = "select s.username from fos_user s , commentaireevenement c where s.id = c.id_user and c.id_user = ?";
                PreparedStatement preparedStatement2 = (PreparedStatement) connexion.prepareStatement(sql2);
                preparedStatement2.setInt(1, resultSet.getInt("id_user"));
                 resultSet2 = preparedStatement.executeQuery();*/
             //   tsCommentaire.add(resultSet.getDate("date_commentaire") + " Ajouter par " + resultSet.getString("contenu"));
             tsCommentaire.add(new CommentaireEvenement(resultSet.getInt("id_user"),resultSet.getInt("id_evenement"),resultSet.getDate("date_commentaire"), resultSet.getString("contenu")));
               /* tsCommentaire
                        .add(new CommentaireEvenement(
                                resultSet.getInt("id"),
                                resultSet.getInt("id_user"),
                                resultSet.getInt("id_evenement"),
                                resultSet.getDate("date_commentaire"),
                                resultSet.getString("contenu")));*/
            }
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        
        return tsCommentaire;
    }

}
