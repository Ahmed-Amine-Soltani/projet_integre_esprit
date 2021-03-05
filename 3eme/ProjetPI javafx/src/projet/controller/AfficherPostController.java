/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import projet.models.comment;
import projet.models.forum;
import projet.service.CommentService;

/**
 * FXML Controller class
 *
 * @author Moez
 */
public class AfficherPostController implements Initializable {

    @FXML
    public ListView<String> ListView_commentaire;

    @FXML
    private TextField commentaire_text_fx;

    @FXML
    public Label nomForum;

    /**
     * Initializes the controller class.
     */
    public String forum;
    private forum f;
    @FXML
    private BorderPane borderpane;
    @FXML
    private Label descF;
    @FXML
    private Label Df;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       

    }

    public int idForumRecuperer;
        
    @FXML
    public void ajouterCommentaire() {
        
        String phonee = "23911535";
        Timestamp date = new Timestamp(System.currentTimeMillis());
        CommentService cs = new CommentService();
        String contenueCommentaireEvenement = commentaire_text_fx.getText();

        ListView_commentaire.getItems().add(LocalDate.now() + "   Utilisateur " + WorldfriendshipController.recupererUtilisateurConnecte.getNom_Utilisateur() + " Contenu " + contenueCommentaireEvenement);
        comment commentaire = new comment(idForumRecuperer, WorldfriendshipController.recupererUtilisateurConnecte.getId_Utilisateur(), contenueCommentaireEvenement);
        
        cs.ajouterCommentaireForum(commentaire);
        
        commentaire_text_fx.clear();
        
        
        
        
        
        //*** API SMS
        
        sendSms(phonee);
    }
    
    public void setTest(forum fo){
    
        f = fo;
        nomForum.setText(f.getTitle());
        descF.setText(f.getDescription());
        Df.setText(f.getDate().toString());
    }

    private void sendSms(String phone) {
        // TODO Auto-generated method stub
        try {
            // Construct data
            String apiKey = "apikey=MfkkwMIH6Qg-6snwHbwPtODWkEwFf6RnIkIaw8XRW5";
            String message = "&message=" + "Tu as un commentaire sur votre question ";
            String sender = "&sender=" + "worldfriend";
            String numbers = "&numbers=+216"+ phone ;

            // Send data
            HttpURLConnection conn = (HttpURLConnection) new URL("https://api.txtlocal.com/send/?").openConnection();
            String data = apiKey + numbers + message + sender;
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
            conn.getOutputStream().write(data.getBytes("UTF-8"));
            final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            final StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                stringBuffer.append(line);
            }
            rd.close();
            System.out.println("ghjdksxjvkjvj");

        } catch (Exception e) {
            System.out.println("Error SMS " + e);
        }

    }
    
    
}
