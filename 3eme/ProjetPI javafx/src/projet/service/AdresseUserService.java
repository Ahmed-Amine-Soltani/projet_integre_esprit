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
import java.util.logging.Level;
import java.util.logging.Logger;
import projet.controller.WorldfriendshipController;
import projet.models.Adresse;
import projet.models.Utilisateur;
import projet.utils.DbConnection;

/**
 *
 * @author yassine
 */
public class AdresseUserService implements IAdresseUserService {

    static Statement statement = null;
    PreparedStatement pst;

    DbConnection cnx = DbConnection.getInstance();
    Connection connection = cnx.getConnection();

    private Utilisateur user = WorldfriendshipController.recupererUtilisateurConnecte;

    @Override
    public Adresse getAdresse() {

        Adresse adresse = null;

        String req = "SELECT * FROM adresse  WHERE user_id =" + user.getId_Utilisateur();

        try {
            statement = connection.createStatement();
            ResultSet res = statement.executeQuery(req);

            while (res.next()) {

                adresse = new Adresse(res.getString(3), res.getString(4), res.getString(5), res.getString(6));
                adresse.setId(res.getInt(1));
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return adresse;
    }

    @Override
    public boolean updateAdresse(Adresse adr) {
        Adresse adresse = getAdresse();

        if (adresse != null) {
            System.out.print(adresse.getId());
            try {
                String req = "UPDATE adresse SET pays= ?, adresse= ?, ville= ?, etat= ? WHERE id=?";

                pst = connection.prepareStatement(req);
                pst.setString(1, adr.getPays());
                pst.setString(2, adr.getAdresse());
                pst.setString(3, adr.getVille());
                pst.setString(4, adr.getEtat());
                pst.setInt(5, adresse.getId());

                pst.executeUpdate();

                return true;

            } catch (SQLException ex) {
                Logger.getLogger(AdresseUserService.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {

            try {
                String req = "INSERT INTO adresse(user_id,pays,adresse,ville,etat) VALUES(?,?,?,?,?)";

                pst = connection.prepareStatement(req);
                pst.setInt(1, user.getId_Utilisateur());
                pst.setString(2, adr.getPays());
                pst.setString(3, adr.getAdresse());
                pst.setString(4, adr.getVille());
                pst.setString(5, adr.getEtat());

                pst.executeUpdate();

                return true;

            } catch (SQLException ex) {
                Logger.getLogger(AdresseUserService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return false;

    }
}
