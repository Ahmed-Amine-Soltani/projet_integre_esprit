/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.controller;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;

import com.lowagie.text.pdf.PdfWriter;
import java.io.IOException;
import projet.utils.DateConverter;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;


import java.io.FileOutputStream;
import java.sql.Connection;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import projet.service.promo;
import projet.models.Promotion;
import projet.models.Produit1;
import projet.utils.DbConnection;



/**
 * FXML Controller class
 *
 * @author Samplon
 */
public class AjoutController implements Initializable {
    private promo cp = new promo();
             Promotion p = new Promotion();

        ObservableList<String> azerty =FXCollections.observableArrayList();
         ObservableList<Produit1> list =FXCollections.observableArrayList();
String ch[];
    @FXML
    private TextField nom_promotion;
    @FXML
    private TextField pourcentage;
    @FXML
    private DatePicker date_debut;
    @FXML
    private DatePicker date_fin;
    @FXML
    private Button ajout;

    @FXML
    private AnchorPane rootpane;
    @FXML
    private Button retour;
    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private Label label3;
    @FXML
    private Label label4;
    @FXML
    private Label label5;

 CheckBox box = new CheckBox();
 List<String> liste = new ArrayList <String>();
  List<String> listee = new ArrayList <String>();
 
      private static SimpleDateFormat formater = null;
     private static java.util.Date Ddate=null;
     private static String Sdate=null;
     private static LocalDate LDdate=null;
static Statement statement = null;
    PreparedStatement pst;

    DbConnection cnx = DbConnection.getInstance();
    Connection connection = cnx.getConnection();
    @FXML
    private ComboBox<String> combo;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

  
    
    
      String requete4="Select * from produit where promotion=0";    
               
          Statement st2;
        try {
           statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(requete4);
             
             while(rs.next())
            {
   
   
    CheckBox box = new CheckBox(""+rs.getInt(1));
    
    list.add(new Produit1(rs.getInt(1),rs.getString(3),rs.getFloat(5),0,"qqq",box,0));
    
    
            }
        }catch (SQLException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        for(int i=0 ; i<list.size();i++)
        {
     combo.getItems().add(list.get(i).getNom());
        }
    }
   
    
    
    
    
    

        
 
    @FXML
    private void ajout(ActionEvent event) throws IOException {
        
     if(validerChamps())
     {
         String pnom=null;
             
               String aq=combo.getValue();
              
        DateConverter a ;
         Promotion p = new Promotion();
         
         
        p.setDate_debut(java.sql.Date.valueOf(date_debut.getValue()));
        p.setDate_fin(java.sql.Date.valueOf(date_fin.getValue()));
     //   p.setDate_fin(DateConverter.String_To_Date(DateConverter.LocalDate_To_String(date_fin.getValue())));
        p.setNom_promotion(aq);
        p.setPourcentage(Float.valueOf(pourcentage.getText()));
        
        String pour=pourcentage.getText();
        String name=nom_promotion.getText();
        p.setIdProduit("d");
        
        System.out.println();
        float prix_ini=cp.getprixq(aq);
        float por = Float.parseFloat(pourcentage.getText());
        float prix_promo=prix_ini-((prix_ini*por)/100);
        p.setPrix_promo(prix_promo);
        p.setPrix_initiale(prix_ini);
          System.out.println(prix_ini);
            System.out.println(prix_promo);
              System.out.println(aq);
           System.out.println("-------------------------------");
        cp.ajouterPromotion(p);
        
        
        
        
           
    
         
    
          
          
           
     
                  
                  
                 
                 
         
              
        
              
          
            
       BorderPane border_pane = (BorderPane) ((Node) event.getSource()).getScene().getRoot();
       FXMLLoader loader = new FXMLLoader(getClass().getResource("/projet/presentation/FXML.fxml"));
       
       Parent root = null;
        try {
             root = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(ProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
       border_pane.setCenter(root);
        
            
     
        
     
     }}
        
    

    private void test(ActionEvent event) {
                label5.setText("");

    }

    @FXML
    private void retour(ActionEvent event) throws IOException {
 
      BorderPane border_pane = (BorderPane) ((Node) event.getSource()).getScene().getRoot();
       FXMLLoader loader = new FXMLLoader(getClass().getResource("/projet/presentation/FXML.fxml"));
       
       Parent root = null;
        try {
             root = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(ProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
       border_pane.setCenter(root);
        
    }
    
    
    
    
    
    
    
    private void com()
    {
        
        
        
        String requete4="Select nom from produit";    
               
          Statement st2;
        try {
           statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(requete4);
             
             while(rs.next())
            {
   
                azerty.add(rs.getString(1));
                
            }   
             
             
             
        } catch (SQLException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
  
    private boolean validerChamps() {

  String a=pourcentage.getText();
//        float a4=Integer.parseInt(a);
        LocalDate mar=date_debut.getValue();
        LocalDate mar1=date_fin.getValue();
      LocalDate z=aujourdhui();
        if (nom_promotion.getText().isEmpty()) {
            label1.setText("Veuillez Saisir le nom du promotion");
         
            return false;
            
        }
        
     
        else if (date_debut.getValue()==null) {
            label3.setText("Veuillez Saisir la date de debut du promotion  ");
            
            return false;
            
        }
        else if (date_fin.getValue()==null) {
            label4.setText("Veuillez Saisir la date fin du promotion  ");
       
            return false;
            
        }
        else if(wa9t(mar,z)!=1)
        {
                           label4.setText("date debut est inferieur a date d'aujourd hui ");
            return false;

            
        }
        
         else if(pourcentage.getText().isEmpty()) {
            label2.setText("Veuillez Saisir le pourcentage  du promotion");
            
            return false;
            
        }
        
          else if (estUnEntier(a)) {
            label2.setText("Veuillez Saisir le un pourcentage  du promotion!!");
           
            return false;
            
        }
       
         float a4=Integer.parseInt(a);
         if (a4>100) {
            label2.setText("Veuillez Saisir le pourcentage  du promotion inferieur a 100");
          
            return false;
            
        }
         else if (a4<0) {
            label2.setText("Veuillez Saisir le pourcentage  du promotion superieur a 0");
           
            return false;
            
        }
         else if( wa9t(mar1,mar)!=1)
         {
               label4.setText("date debut est superieur a date fin error ");
            
            return false;
         }
         
       
                return true;

    }
    
    
    
    public boolean estUnEntier(String x) {
		try {

                        Float.parseFloat(x);
                        
		} catch (NumberFormatException e){
			return true;
		}
 
		return false;
	}

    @FXML
    private void yes1(KeyEvent event) {
        label1.setText("");
        
    }

    @FXML
    private void yes2(KeyEvent event) {
                label2.setText("");

    }

    @FXML
    private void yes3(KeyEvent event) {
                label3.setText("");

    }

    @FXML
    private void yes4(KeyEvent event) {
                label4.setText("");

    }
    
    
    
    private int wa9t(LocalDate a , LocalDate b)
    {
        int nbre=-1;
       
        
       int year1= a.getYear();
       int month1=a.getMonthValue();
        int jour1=a.getDayOfMonth();
       
            
       int year2= b.getYear();
       int month2=b.getMonthValue();
        int jour2=b.getDayOfMonth();
        
        if(year1>year2)
        {
            return 1;
        }
        else if((year1==year2)&&(month1>month2))
        {
            return 1;
        }
        else if ((year1==year2)&&(month1==month2)&&(jour1>jour2))
        {
            return 1;
        }
        //////////////////////
        
        
      
        else if ((year1==year2)&&(month1==month2)&&(jour1==jour2))
        {
            return 0;
        }
        
        
        
        return nbre;
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public void sendmail(String x,String y,LocalDate z,LocalDate v)
    {
         try{
            String host ="smtp.gmail.com" ;
            String user = "benabdellatif123@gmail.com";
            String pass = "marwen";
            String to = x;
            String from = "benabdellatif123@gmail.com";
            String subject = "Nouveau promotion sur WorldFriendship !!! ";
            String messageText = "Nouveau promotion sur "+y +" "+"de "+z+" jusqua "+v+" "+"consultez notre site pour plus d'information";
            boolean sessionDebug = false;

            Properties props = System.getProperties();

            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.required", "true");

            java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            Session mailSession = Session.getDefaultInstance(props, null);
            mailSession.setDebug(sessionDebug);
            Message msg = new MimeMessage(mailSession);
            msg.setFrom(new InternetAddress(from));
            InternetAddress[] address = {new InternetAddress(to)};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(subject); msg.setSentDate(new java.util.Date());
            msg.setText(messageText);

           Transport transport=mailSession.getTransport("smtp");
           transport.connect(host, user, pass);
           transport.sendMessage(msg, msg.getAllRecipients());
           transport.close();
           System.out.println("message send successfully");
        }catch(Exception ex)
        {
            System.out.println(ex);
        }
    }
    
    
    
    
     public LocalDate aujourdhui() {
        
       return LocalDate.now();
}
    
    
    
    
    
    
    
    
    public void pdf(String x,String d,String ff,String p,String pourcentage)
    {
         Document document = new Document();
        try{
            PdfWriter.getInstance(document, new FileOutputStream(x+".pdf"));
            document.open();

            
            Font f=new Font(FontFactory.getFont(FontFactory.TIMES_BOLD,24,Font.UNDERLINE));
            f.setColor(0,153,255);
            
            Font f2=new Font(FontFactory.getFont(FontFactory.TIMES_BOLD,20,Font.BOLD));
            f2.setColor(0,0,0);
                    
            
            
            
            

            Paragraph p1 = new Paragraph("Promotion  "+x+":",f);
            p1.setAlignment(Paragraph.ALIGN_CENTER);
            
      
            
              Paragraph p12 = new Paragraph("    ");
              Paragraph p13 = new Paragraph("     ");
              Paragraph p14 = new Paragraph("     ");
              
               Paragraph p15 = new Paragraph("     ");
                Paragraph p16 = new Paragraph("     ");
                 Paragraph p17 = new Paragraph("     ");
            Paragraph p2 = new Paragraph("Date debut de promotion : ");
            p2.add(d);
            
            
            Paragraph p3 = new Paragraph("Date fin de promotion : ");
            p3.add(ff);
            Paragraph p4 = new Paragraph("les produit on promotion :   ");
            p4.add(p);
            Paragraph p5 = new Paragraph("la pourcentage de reduction :  ");
            p5.add(pourcentage+"%");
        
            
	    document.add(p1);
            
            document.add(p12);
            document.add(p13);
            document.add(p14);
               
            document.add(p2);
            
            document.add(p15);
            document.add(p15);
            document.add(p15);
            
            document.add(p3);
            
            document.add(p16);
            document.add(p16);
            document.add(p16);
            
            document.add(p4);
            
            document.add(p17);
            document.add(p17);
            document.add(p17);
            
            document.add(p5);
            
     
       
            
            
            
        }
        catch(Exception e){
            System.out.println(e);
        }
        document.close();
    }
    
   public static String Date_To_String(java.util.Date dateToConvert) {
        formater = new SimpleDateFormat("yyyy-MM-dd");
          Sdate = formater.format(dateToConvert);
        return Sdate;
    }

    
    
 
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
 

