package projet.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

import projet.models.Evenement;
import projet.models.Utilisateur;
import java.time.Instant;
import java.time.Period;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.PopupWindow;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.batik.anim.timing.Interval;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.PopOver;

public class ServiceEvenement {

    private static Connection connexion = null;

    public static Connection creationConnexion() {
        String dbName = "projetdev";
        String userName = "root";
        String password = "";
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connexion = DriverManager.getConnection("jdbc:mysql://localhost/" + dbName, userName, password);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return connexion;
    }

    public static PopOver popNotification(String notification) {
        Text notifcationText = new Text(notification);
        notifcationText.setWrappingWidth(230);
        notifcationText.setStyle("-fx-font-weight: bold");

        HBox hBox = new HBox(notifcationText);
        hBox.setPadding(new Insets(0, 5, 0, 5));

        PopOver popOver = new PopOver(hBox);
        popOver.setTitle("New Notification");

        return popOver;
    }

    public static int ajouterEvenement(Evenement evenement) {
        int status = 0;
        String sql = "INSERT INTO EVENEMENT(nom_evenement,lieu_evenement,description_evenement,capacite_evenement,type,date_debut_evenement,date_fin_evenement,affiche,id_user) VALUES(?,?,?,?,?,?,?,?,?)";
        System.out.println(sql);

        try {
            Connection connexion = ServiceEvenement.creationConnexion();
            PreparedStatement preparedStatement = (PreparedStatement) connexion.prepareStatement(sql);
            preparedStatement.setString(1, evenement.getNom_evenement());
            preparedStatement.setString(2, evenement.getLieu_evenement());
            preparedStatement.setString(3, evenement.getDescription_evenement());
            preparedStatement.setInt(4, evenement.getCapacite_evenement());
            preparedStatement.setString(5, evenement.getType_evenement());
            //Date.valueOf to convert Date.util to Date.sql
            preparedStatement.setDate(6, Date.valueOf(evenement.getDate_debut_evenement()));
            preparedStatement.setDate(7, Date.valueOf(evenement.getDate_fin_evenement()));
            preparedStatement.setString(8, evenement.getAffiche_evenement());
            preparedStatement.setInt(9, evenement.getId_user());
            // preparedStatement.setBytes(7, evenement.getPhoto());
            status = preparedStatement.executeUpdate();
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    public static List<Evenement> afficherTtEvenement() {
        List<Evenement> listEvenement = new ArrayList<Evenement>();
        ResultSet resultSet = null;
        try {

            Connection connexion = ServiceEvenement.creationConnexion();
            String sql = "select * from evenement";
            PreparedStatement preparedStatement = (PreparedStatement) connexion.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                listEvenement.add(new Evenement(resultSet.getInt("id"), resultSet.getString("nom_evenement"), resultSet.getString("lieu_evenement"),
                        resultSet.getInt("capacite_evenement"), resultSet.getString("description_evenement"), resultSet.getString("type"),
                        resultSet.getDate("date_debut_evenement"), resultSet.getDate("date_fin_evenement"), resultSet.getString("Affiche")));
            }
            connexion.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listEvenement;
    }

    public static List<Evenement> afficherMyEvenement(int idUser) {
        List<Evenement> listEvenement = new ArrayList<Evenement>();
        ResultSet resultSet = null;
        try {

            Connection connexion = ServiceEvenement.creationConnexion();
            String sql = "select * from evenement where id_user = ?";
            PreparedStatement preparedStatement = (PreparedStatement) connexion.prepareStatement(sql);
            preparedStatement.setInt(1, idUser);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                listEvenement
                        .add(new Evenement(resultSet.getInt("id"), resultSet.getString("nom_evenement"), resultSet.getString("lieu_evenement"),
                                resultSet.getInt("capacite_evenement"), resultSet.getString("description_evenement"), resultSet.getString("type"),
                                resultSet.getDate("date_debut_evenement"), resultSet.getDate("date_fin_evenement"), resultSet.getString("Affiche")));
            }
            connexion.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listEvenement;
    }

    public static List<String> chercherEvenement(int idEvenement) {
        List<String> EvenementDetails = new ArrayList<String>();
        Evenement evenement = new Evenement();
        ResultSet resultSet = null;
        try {

            Connection connexion = ServiceEvenement.creationConnexion();
            String sql = "select * from evenement where id= ?";
            PreparedStatement preparedStatement = (PreparedStatement) connexion.prepareStatement(sql);
            preparedStatement.setInt(1, idEvenement);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                EvenementDetails.add("Information sur l'évenement : L'événement aura lieu à " + resultSet.getString("lieu_evenement") + " du " + resultSet.getDate("date_debut_evenement") + "  " + resultSet.getDate("date_fin_evenement"));
                EvenementDetails.add("Il reste  : " + resultSet.getString("capacite_evenement") + " place");
                EvenementDetails.add("C'est un evenement   : " + resultSet.getString("type"));
                EvenementDetails.add("Durée : ");
                EvenementDetails.add("Description : " + resultSet.getString("description_evenement"));
                /* .add(new Evenement(resultSet.getInt("id"), resultSet.getString("nom_evenement"), resultSet.getString("lieu_evenement"),
                                resultSet.getInt("capacite_evenement"), resultSet.getString("description_evenement"),
                                resultSet.getDate("date_debut_evenement"), resultSet.getString("Affiche")));*/
            }
            connexion.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        /* for (Evenement ev : listEvenement) {
            evenement.setId_Evenement(ev.getId_Evenement());
            evenement.setNom_evenement(ev.getNom_evenement());
            evenement.setLieu_evenement(ev.getLieu_evenement());
            evenement.setCapacite_evenement(ev.getCapacite_evenement());
            evenement.setDescription_evenement(ev.getDescription_evenement());
             evenement.setDate_debut_evenement(ev.getDate_debut_evenement());
            evenement.setAffiche_evenement(ev.getAffiche_evenement());

        }*/
        return EvenementDetails;
    }

    //pour décrémenter la capacité en reservant
    public static Evenement chercherEvenementById(int idEvenement) {

        Evenement evenement = new Evenement();
        ResultSet resultSet = null;
        try {

            Connection connexion = ServiceEvenement.creationConnexion();
            String sql = "select * from evenement where id= ?";
            PreparedStatement preparedStatement = (PreparedStatement) connexion.prepareStatement(sql);
            preparedStatement.setInt(1, idEvenement);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                evenement.setId_Evenement(resultSet.getInt("id"));
                evenement.setCapacite_evenement(resultSet.getInt("capacite_evenement"));

            }
            connexion.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return evenement;
    }

    public static Boolean supprimerEvenement(Evenement evenement) {
        try {
            Connection connexion = ServiceEvenement.creationConnexion();
            String sql = "DELETE FROM evenement WHERE id = ?";
            PreparedStatement preparedStatement = (PreparedStatement) connexion.prepareStatement(sql);
            preparedStatement.setInt(1, evenement.getId_Evenement());
            int res = preparedStatement.executeUpdate();
            System.out.println(res);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;

    }

    public static int modifierEvenement(Evenement evenement) {
        int status = 0;
        String sql = "UPDATE evenement SET nom_evenement = ? , lieu_evenement = ? ,description_evenement = ? , capacite_evenement = ? , date_debut_evenement = ? , date_fin_evenement = ? , Affiche = ? ,type = ? where id = ?";
        try {

            Connection connexion = ServiceEvenement.creationConnexion();
            PreparedStatement preparedStatement = (PreparedStatement) connexion.prepareStatement(sql);
            preparedStatement.setString(1, evenement.getNom_evenement());
            preparedStatement.setString(2, evenement.getLieu_evenement());
            preparedStatement.setString(3, evenement.getDescription_evenement());
            preparedStatement.setInt(4, evenement.getCapacite_evenement());
            preparedStatement.setDate(5, Date.valueOf(evenement.getDate_debut_evenement()));
            preparedStatement.setDate(6, Date.valueOf(evenement.getDate_fin_evenement()));
            preparedStatement.setString(7, evenement.getAffiche_evenement());
            preparedStatement.setString(8, evenement.getType_evenement());
            preparedStatement.setInt(9, evenement.getId_Evenement());

            // preparedStatement.setBytes(7, evenement.getPhoto());
            status = preparedStatement.executeUpdate();
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    //pour décrémenter la capacité en reservant
    public static int modifierEvenementCapacite(Evenement evenement) {
        int status = 0;
        String sql = "UPDATE evenement SET  capacite_evenement = ? where id = ?";
        try {

            Connection connexion = ServiceEvenement.creationConnexion();
            PreparedStatement preparedStatement = (PreparedStatement) connexion.prepareStatement(sql);
            preparedStatement.setInt(1, evenement.getCapacite_evenement());
            preparedStatement.setInt(2, evenement.getId_Evenement());

            status = preparedStatement.executeUpdate();
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    /* CONTROLE DE SAISIE */
    //variable de controle de saisie
    private static Matcher matcher;

    private static final String email_pattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static Pattern email_pattern_compile = Pattern.compile(email_pattern);

    public static boolean validationEmail(final String emailSaisie) {
        matcher = email_pattern_compile.matcher(emailSaisie);
        return matcher.matches();
    }

    private static final String chaineSimple_avecEspace_pattern = "^[A-Z a-z 0-9]+$";
    private static Pattern chaineSimple_pattern_avecEspace_complie = Pattern.compile(chaineSimple_avecEspace_pattern);

    public static boolean validationChaineSimpleAvecEspace(final String chaineSaisie) {
        matcher = chaineSimple_pattern_avecEspace_complie.matcher(chaineSaisie);
        return matcher.matches();
    }

    private static final String chaineSimple_sanEspace_pattern = "^[A-Za-z0-9]+$";
    private static Pattern chaineSimple_pattern__sanEspace_complie = Pattern.compile(chaineSimple_sanEspace_pattern);

    public static boolean validationChaineSimpleSansEspace(final String chaineSaisie) {
        matcher = chaineSimple_pattern__sanEspace_complie.matcher(chaineSaisie);
        return matcher.matches();
    }

    private static final String chaineSimple_nombre_pattern = "^[0-9]+$";
    private static Pattern chaineSimple_pattern__nombre_complie = Pattern.compile(chaineSimple_nombre_pattern);

    public static boolean validationChaineSimpleNombre(final String chaineSaisie) {
        matcher = chaineSimple_pattern__nombre_complie.matcher(chaineSaisie);
        return matcher.matches();
    }

    /* public static boolean validationChaineSimpleNombre(String text) {

        if (text.matches("^[0-9]+$")) {

            return true;

        } else {

            return false;

        }
    }*/
    // pattern double espace 
    /* public static String removeEspace(String s) { 
        Pattern patt = Pattern.compile("\s"); 
        Matcher mat = patt.matcher(s); 
        return mat.replaceAll(" "); 
   } */
 /* Notification */
    public static Notifications notificationBuilder;

    Node graphic;

    public static void notification(String titre, Pos pos, Node graphic, String Text) {

        notificationBuilder = Notifications.create()
                .title(titre)
                .text(Text)
                .graphic(graphic)
                .position(pos)
                .onAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent arg0) {
                        System.out.println("Notifiation is Clicked");
                    }
                });
    }

    //notification(Pos.BOTTOM_RIGHT,graphic,"test");
    //notificationBuilder.showWarning();
    // notificationBuilder.show();
    // notificationBuilder.showWarning();
    // notificationBuilder.showInformation();
    //notificationBuilder.showConfirm();
    //notificationBuilder.showError();
    //notificationBuilder.show();
}
