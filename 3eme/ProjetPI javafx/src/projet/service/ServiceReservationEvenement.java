/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.service;

import projet.models.Evenement;
import projet.models.ReservationEvenement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author solta
 */
public class ServiceReservationEvenement {
    
       public static int ajouterReservation(ReservationEvenement reservation) {
        int status = 0;
        String sql = "INSERT INTO RESERVATION(id_evenement,id_user,etat,numero_ticket) VALUES(?,?,?,?)";
        System.out.println(sql);

        try {
            Connection connexion = ServiceEvenement.creationConnexion();
            PreparedStatement preparedStatement = (PreparedStatement) connexion.prepareStatement(sql);
            preparedStatement.setInt(1, reservation.getIdEvenement());
            preparedStatement.setInt(2, reservation.getIdUtilisateur());
            preparedStatement.setString(3, reservation.getEtat());
            preparedStatement.setString(4,reservation.getNumeroTicket());
            status = preparedStatement.executeUpdate();
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }
       
       public static List<Evenement> mesReservation(int IdUtilisateur){
           
        List<Evenement> listMesReservation = new ArrayList<Evenement>();
        Evenement evenement = new Evenement();
        ResultSet resultSet = null;
        try {

            Connection connexion = ServiceEvenement.creationConnexion();
            String sql = "select * from evenement e , reservation r where e.id=r.id_evenement and r.id_user = ?";
            PreparedStatement preparedStatement = (PreparedStatement) connexion.prepareStatement(sql);
            preparedStatement.setInt(1, IdUtilisateur);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                 listMesReservation.add(new Evenement(resultSet.getInt("id"), resultSet.getString("nom_evenement"), resultSet.getString("lieu_evenement"),
                        resultSet.getInt("capacite_evenement"), resultSet.getString("description_evenement"), resultSet.getString("type"),
                        resultSet.getDate("date_debut_evenement"), resultSet.getDate("date_fin_evenement"), resultSet.getString("Affiche")));
            }
            connexion.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listMesReservation ;
       }
       
       public static boolean verificationReservation(int IdUtilisateur,int IdEvenement){
          // ReservationEvenement reservationEvenement = new ReservationEvenement();
          
           
         ResultSet resultSet = null;
        try {

            Connection connexion = ServiceEvenement.creationConnexion();
            String sql = "select * from reservation where id_user = ? and id_evenement = ? ";
            PreparedStatement preparedStatement = (PreparedStatement) connexion.prepareStatement(sql);
            preparedStatement.setInt(1, IdUtilisateur);
            preparedStatement.setInt(2, IdEvenement);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next() == false ){
                return false;
            }
           /* while (resultSet.next()) {
                 reservationEvenement.setIdReservation(resultSet.getInt("id_reservation"));
                 reservationEvenement.setIdEvenement(resultSet.getInt("id_evenement"));
                 reservationEvenement.setIdUtilisateur(resultSet.getInt("id_user"));
                 reservationEvenement.setEtat(resultSet.getString("etat"));
                 reservationEvenement.setNumeroTicket(resultSet.getString("numero_ticket"));
            }*/
            connexion.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true ;
       }
       
      public static Boolean supprimerReservation(int IdUtilisateur,int IdEvenement) {
        try {
            Connection connexion = ServiceEvenement.creationConnexion();
            String sql = "DELETE FROM reservation WHERE id_user = ? and id_evenement = ?";
            PreparedStatement preparedStatement = (PreparedStatement) connexion.prepareStatement(sql);
            preparedStatement.setInt(1, IdUtilisateur);
            preparedStatement.setInt(2, IdEvenement);
            int res = preparedStatement.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;

    }
    
}
