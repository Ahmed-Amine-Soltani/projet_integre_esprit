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
import java.util.HashMap;
import projet.models.Experience;
import projet.models.Rating;
import projet.models.Utilisateur;
import projet.models.categorie;
import projet.utils.DbConnection;

/**
 *
 * @author jumper
 */
public class ExperienceService implements IExperience {

    static Statement statement = null;
    PreparedStatement pst;

    DbConnection cnx = DbConnection.getInstance();
    Connection connection = cnx.getConnection();

    @Override
    public ArrayList<Experience> selectAll() {
        ArrayList<Experience> experiences = new ArrayList<>();
        String req = "select * from Experience";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                Experience e = new Experience();
                e.setId(rs.getInt(1));
                e.setNom(rs.getString(4));
                e.setDesc(rs.getString(5));
                e.setLieu(rs.getString(6));
                e.setUser(rs.getInt(2));
                e.setCatExp(getCategorieById(rs.getInt(3)));
                e.setImage_exp(rs.getString(7));
                e.setDate(rs.getString(8));
                //e.setRating(rs.getInt(11));
                experiences.add(e);
                System.out.println(e);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ExperienceService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return experiences;
    }

    public List<Experience> rechercheExperiences(String str) {
        ArrayList<Experience> experiences = new ArrayList<>();
        String sql = "SELECT * FROM experience WHERE nom_exp LIKE ? OR lieu_exp LIKE ? ";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, "%" + str + "%");
            ps.setString(2, "%" + str + "%");
            ps.executeQuery();

            ResultSet rs = ps.getResultSet();

            while (rs.next()) {
                Experience e = new Experience();
                 e.setId(rs.getInt(1));
                e.setNom(rs.getString(4));
                e.setDesc(rs.getString(5));
                e.setLieu(rs.getString(6));
                e.setUser(rs.getInt(2));
                e.setCatExp(getCategorieById(rs.getInt(3)));
                e.setImage_exp(rs.getString(7));
                e.setDate(rs.getString(8));
                //e.setRating(rs.getInt(11));
                experiences.add(e);
            }
        } catch (SQLException ex) {
            //System.out.println(ex.getMessage());
        }
        return experiences;
    }

    public ArrayList<categorie> selectCategorie() {
        ArrayList<categorie> categories = new ArrayList<>();
        String req = "select * from Categorie";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(req);
            while (resultSet.next()) {
                categorie categorie;
                categorie = new categorie(resultSet.getInt(1), resultSet.getString(2));
                categories.add(categorie);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ExperienceService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return categories;
    }

    public categorie getCategorieById(int id) {
        categorie c = new categorie();
        String sql = "SELECT * FROM `categorie` WHERE id= ? ";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                c.setId(rs.getInt(1));
                c.setNom(rs.getString(2));
            }
        } catch (Exception e) {
        }
        return c;
    }

    public Utilisateur getUserById(int id) {
        Utilisateur u = new Utilisateur();
        String sql = "SELECT * FROM `fos_user` WHERE id= ? ";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                u.setId_Utilisateur(rs.getInt(1));
                u.setNom_Utilisateur(rs.getString(2));
                //u.setEmail(rs.getString(4));
                //u.setMotDePasse_Utilisateur(rs.getString(8));
                //u.setRole_Utilisateur(rs.getString(12));
            }
        } catch (Exception e) {
        }
        return u;
    }

    @Override
    public void insert(Experience e) {
        String req = "INSERT INTO `Experience`(`nom_exp`, `desc_exp`, `lieu_exp`, `date_exp`, user, categorie, image_name) VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(req);
            preparedStatement.setString(1, e.getNom());
            preparedStatement.setString(2, e.getDesc());
            preparedStatement.setString(3, e.getLieu());
            preparedStatement.setString(4, e.getDate());
            preparedStatement.setInt(5, e.getUser());
            preparedStatement.setInt(6, e.getCat());
            preparedStatement.setString(7, e.getImage_exp());
            preparedStatement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ExperienceService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void supprimer(int x) {
        String sql = "DELETE FROM experience WHERE id = ? ";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, x);

            statement.executeUpdate();

            System.out.println("Experience Supprimer");

        } catch (SQLException ex) {
            Logger.getLogger(ExperienceService.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Experience nom Supprimer");
    }

    public void modifier(int id, String a, String b, String c) {
        String sql = "UPDATE experience SET nom_exp=?,desc_exp=?,lieu_exp=? WHERE id =?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, a);
            statement.setString(2, b);
            statement.setString(3, c);
            statement.setInt(4, id);
            statement.executeUpdate();
            System.out.println("experience Modifiée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public Experience chercherExperienceParId(int id) {
        Experience e = new Experience();
        String sql = "SELECT * FROM `experience` where experience.id='" + id + "'";
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                e.setId(result.getInt("id"));
                e.setNom(result.getString("nom"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Experience.class.getName()).log(Level.SEVERE, null, ex);
        }
        return e;

    }

    public HashMap<String, Integer> getCategorie() {

        HashMap<String, Integer> map = new HashMap<>();

        String req = "SELECT * FROM categorie";
        PreparedStatement statement;

        try {
            statement = connection.prepareStatement(req);
            ResultSet result = statement.executeQuery(req);
            categorie categorie;
            while (result.next()) {
                categorie = new categorie(result.getInt(1), result.getString(2));
                map.put(categorie.getNom(), categorie.getId());
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block

        }

        return map;
    }

    public void ajouterEvaluationBoutique(int user, int exp, float r) {
        //String sql = "UPDATE rating SET user=?, experience=?, rate=? WHERE experience=?";
        String sql = "INSERT INTO rating (user, experience, rate) VALUES (?,?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, user);
            statement.setInt(2, exp);
            statement.setFloat(3, r);
            statement.executeUpdate();
            System.out.println("Rating Ajouter");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void updateEvaluationBoutique(int id, float r, int user, int exp) {
        //String sql = "UPDATE rating SET user=?, experience=?, rate=? WHERE experience=?";
        String sql = "UPDATE rating SET rate=? WHERE id=? and user=? and experience=?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(2, user);
            statement.setInt(3, exp);
            statement.setFloat(4, r);
            statement.executeUpdate();
            System.out.println("Rating Ajouter");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public float CalculerMoyenneNote(int exp) {
        String sql = "SELECT AVG(rate) as 'moyenne' FROM `rating` WHERE `experience`=" + exp;
        float moyenne = 0;
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                moyenne = result.getFloat("moyenne");
            }

        } catch (SQLException ex) {
            Logger.getLogger(Rating.class.getName()).log(Level.SEVERE, null, ex);
        }
        return moyenne;
    }

    public ArrayList<Experience> TopExperiences() {
        ArrayList<Experience> experiences = new ArrayList<>();
        String req = "SELECT * FROM experience ORDER BY view_count_plural DESC LIMIT 3";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                Experience e = new Experience();
                e.setId(rs.getInt(1));
                e.setNom(rs.getString(4));
                e.setDesc(rs.getString(5));
                e.setLieu(rs.getString(6));
                e.setUser(rs.getInt(2));
                e.setCatExp(getCategorieById(rs.getInt(3)));
                e.setImage_exp(rs.getString(7));
                e.setDate(rs.getString(8));
                //e.setRating(rs.getInt(11));
                experiences.add(e);
                System.out.println(e);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ExperienceService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return experiences;
    }

    public ArrayList<Experience> MesExperiences(int id) {
        ArrayList<Experience> experiences = new ArrayList<>();
        String req = "SELECT * FROM experience WHERE user="+id;
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                Experience e = new Experience();
                e.setId(rs.getInt(1));
                e.setNom(rs.getString(4));
                e.setDesc(rs.getString(5));
                e.setLieu(rs.getString(6));
                e.setUser(rs.getInt(2));
                e.setCatExp(getCategorieById(rs.getInt(3)));
                e.setImage_exp(rs.getString(7));
                e.setDate(rs.getString(8));
                //e.setRating(rs.getInt(11));
                experiences.add(e);
                System.out.println(e);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ExperienceService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return experiences;
    }
}
