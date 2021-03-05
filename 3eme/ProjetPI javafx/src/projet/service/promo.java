/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import projet.models.Promotion;
import static projet.service.PanierService.statement;
import projet.utils.DbConnection;
/**
 *
 * @author Samplon
 */
public class promo {
    
static Statement statement = null;
    PreparedStatement pst;

    DbConnection cnx = DbConnection.getInstance();
    Connection connection = cnx.getConnection();
	
     public List<Promotion> listerPromotion()
    {
        List<Promotion> myList=new ArrayList();
        try {
        
        String requete4="Select * from promotion";      
     statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(requete4);
            
            while(rs.next())
            {
              //  Promotion p=new Promotion();
                //p.setNom_promotion(rs.getString(2));
                //p.setId(rs.getInt(1));
                myList.add(new Promotion(rs.getInt(1),rs.getString(2),rs.getDate(3),rs.getDate(4),rs.getFloat(5),rs.getFloat(6),rs.getFloat(7),rs.getString(8)));
                
            }                                    
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }
     
     
     public void ajouterPromotion(Promotion p){
      String requete="INSERT INTO promotion (nom_Promotion , date_debut , date_fin , pourcentage , prix_initiale , prix_promo)"
                + " VALUES ('"+p.getNom_promotion()+"','"+p.getDate_debut()+"','"+p.getDate_fin()+"','"+p.getPourcentage()+"','"+p.getPrix_initiale()+"', '"+p.getPrix_promo()+"');";                              
        
        try {
           pst = connection.prepareStatement(requete);
            pst.executeUpdate(requete);
            System.out.println("Promotoin Ajoutée");
        } catch (SQLException ex) {
            System.out.println("aaaaaaa");
        }
     
     }
     
     
     
            
   public float  getprix(int x)  {
float idcom=0 ;
       String requete = "SELECT prix FROM `produit` WHERE `id` = ?;";
      
 PreparedStatement ps;
    try {
        
         ps = connection.prepareStatement(requete);
         ps.setInt(1,x);
            ResultSet resultat = ps.executeQuery();

          if(resultat.next());
          {

                idcom=resultat.getInt(1);
                              System.out.println(+idcom);
                              System.out.println("++++++++++++++++++++++");

                         return idcom;}
    } catch (SQLException ex) {

System.out.println("nopeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
    }
         
          return idcom;
          
          

        
    }
   public float  getprixq(String x)  {
float idcom=0 ;
       String requete = "SELECT prix FROM `produit` WHERE `libelle` = ?;";
      
 PreparedStatement ps;
    try {
        
         ps = connection.prepareStatement(requete);
         ps.setString(1,x);
            ResultSet resultat = ps.executeQuery();

          if(resultat.next());
          {

                idcom=resultat.getFloat(1);
                              System.out.println(+idcom);
                              System.out.println("++++++++++++++++++++++");

                         return idcom;}
    } catch (SQLException ex) {

System.out.println("nopeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
    }
         
          return idcom;
          
          

        
    }
   
     public String getnomproduit(int x)  {
String idcom="";
       String requete = "SELECT libelle FROM `produit` WHERE `id` = ?;";
      
 PreparedStatement ps;
    try {
       ps = connection.prepareStatement(requete);
         ps.setInt(1,x);
            ResultSet resultat = ps.executeQuery();

          if(resultat.next());
          {

                idcom=resultat.getString(1);
                           

                         return idcom;}
    } catch (SQLException ex) {

System.out.println("nopeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
    }
         
          return idcom;
          
          

        
    }
     public List<Float> getidproduit(int x)
    {
      
      List<Float> myList=new ArrayList();
        
          String requete = "SELECT prix_initiale FROM `promotion` WHERE `id` = ?;";
      
 PreparedStatement ps;
    try {
       ps = connection.prepareStatement(requete);
         ps.setInt(1,x);
            ResultSet resultat = ps.executeQuery();

            while(resultat.next())
          {

          myList.add(resultat.getFloat(1));                    


                      
          
          
          }
    } catch (SQLException ex) {

System.out.println("nopeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
    }
         
          
          return myList;

     
     


     
     
}
    public Float getprixpro(String x)  {
float idcom=0;
       String requete = "SELECT prix_promo FROM `promotion` WHERE `nom_Promotion` = ?;";
      
 PreparedStatement ps;
    try {
    ps = connection.prepareStatement(requete);
         ps.setString(1,x);
            ResultSet resultat = ps.executeQuery();

          if(resultat.next());
          {

                idcom=resultat.getFloat(1);
                           

                         return idcom;}
    } catch (SQLException ex) {

System.out.println("nopeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
    }
         
          return idcom;
          
          

        
    }
   
   
   
   public void supprimerPromotion(int x) {
        String requete3="DELETE FROM promotion WHERE id=?";
    
     try {
            pst = connection.prepareStatement(requete3);
            pst.setInt(1,x);
            
            pst.executeUpdate();
            
            System.out.println("Promotion Supprimer");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }
   
   public void supprimerPromotion2(String x) {
        String requete3="DELETE FROM promotion WHERE date_fin=?";
    
     try {
                   pst = connection.prepareStatement(requete3);
            pst.setString(1,x);
            
            pst.executeUpdate();
            
            System.out.println("Promotion Supprimer");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }  
    public List<Integer> id(String x) {
       List<Integer> liste = new ArrayList<Integer>();
        String requete3="SELECT id FROM `promotion`  WHERE date_fin=?";
    
   PreparedStatement ps;
    try {
    ps = connection.prepareStatement(requete3);
         ps.setString(1,x);
            ResultSet resultat = ps.executeQuery();

          if(resultat.next());
          {

                liste.add(resultat.getInt(1));
                           

                         return liste;}
    } catch (SQLException ex) {

System.out.println("nopeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
    }
         
          return liste;
    }
   
   
   
    public void modifierPromotion(int id,String b,Date c,float a){
    String requete2="UPDATE promotion SET  pourcentage=?,nom_Promotion=?,date_fin=? WHERE id =?";           
        try {
                 pst = connection.prepareStatement(requete2);
     
            pst.setFloat(1, a);
            pst.setString(2, b);
            pst.setDate(3, c);    
            pst.setInt(4, id);
            
            
            pst.executeUpdate();
            System.out.println("promotion Modifiée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    
    
    }
   
   
     
    public void modifierPromotionprix(int id,float a){
    String requete2="UPDATE promotion SET  prix_promo=? WHERE id =?";           
        try {
                   pst = connection.prepareStatement(requete2);
     
            pst.setFloat(1, a);
            pst.setInt(2, id);
      
            
            pst.executeUpdate();
            System.out.println("promotion Modifiée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    
    
    }
    public void modifierPromotionprix3(int id,float a){
    String requete2="UPDATE produit SET  promotion=?,idpromo=0 WHERE idpromo=?";           
        try {
                   pst = connection.prepareStatement(requete2);
     
            pst.setFloat(1, a);
            pst.setInt(2, id);
      
            
            pst.executeUpdate();
            System.out.println("promotion Modifiée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    
    
    }
      
    public void modifierPromotionprix2(int id,float a){
    String requete2="UPDATE produit SET  promotion=? WHERE idpromo =?";           
        try {
           pst = connection.prepareStatement(requete2);
     
            pst.setFloat(1, a);
            pst.setInt(2, id);
      
            
            pst.executeUpdate();
            System.out.println("promotion Modifiée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    
    
    }
     public void modifierPromotionid(int id){
    String requete2="UPDATE produit SET  idpromo=0 WHERE idpromo =?";           
        try {
                 pst = connection.prepareStatement(requete2);
     
           
            pst.setInt(1, id);
      
            
            pst.executeUpdate();
            System.out.println("promotion Modifiée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    
    
    }
    
     public void modifiercle(int id,int b){
    String requete2="UPDATE produit SET  idpromo=? WHERE id =?";           
        try {
             pst = connection.prepareStatement(requete2);
     
            pst.setFloat(1, id);
            pst.setInt(2, b);
      
            
            pst.executeUpdate();
            System.out.println("promotion Modifiée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    
    
    }
    
     public void promo(String id,int b){
    String requete2="UPDATE promotion SET  produit=? WHERE id =?";           
        try {
             pst = connection.prepareStatement(requete2);
     
            pst.setString(1, id);
            pst.setInt(2, b);
      
            
            pst.executeUpdate();
            System.out.println("promotion Modifiée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    
    
    }
    
    
     public void modifierPromotionprix2(String id,int a){
    String requete2="UPDATE produit SET  promotion=? WHERE nom =?";           
        try {
         pst = connection.prepareStatement(requete2);
     
            pst.setInt(1, a);
            pst.setString(2, id);
      
            
            pst.executeUpdate();
            System.out.println("promotion Modifiée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    
    
    }
    
    
    
    
    
    
     public int getprix2(int x)  {
int idcom=0 ;
       String requete = "SELECT prix_initiale FROM `promotion` WHERE `id` = ?;";
      
 PreparedStatement ps;
    try {
          ps = connection.prepareStatement(requete);
         ps.setInt(1,x);
            ResultSet resultat = ps.executeQuery();

          if(resultat.next());
          {

                idcom=resultat.getInt(1);
                              

                         return idcom;}
    } catch (SQLException ex) {

System.out.println("nopeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
    }
         
          return idcom;
          
          

        
    }
    
    
    
    
    
    
    
    
    
    
    public String getnom_promotion(int x)  {


       String requete = "SELECT nom_Promotion FROM `promotion` WHERE `id` = ?;";
      
 PreparedStatement ps;
    try {
         ps = connection.prepareStatement(requete);
         ps.setInt(1,x);
            ResultSet resultat = ps.executeQuery();

          if(resultat.next());
          {

          String a=resultat.getString(1);                    


                         return a;
          
          
          }
    } catch (SQLException ex) {

System.out.println("nopeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
    }
         
          
          return "aaaaaaaa";

        
    }
    
    public String getpourcentage(int x)  {
int idcom=0 ;
 List<String> myList=new ArrayList();
       String requete = "SELECT pourcentage FROM `promotion` WHERE `id` = ?;";
      
 PreparedStatement ps;
    try {
            ps = connection.prepareStatement(requete);
         ps.setInt(1,x);
            ResultSet resultat = ps.executeQuery();

          if(resultat.next());
          {

          String a=Integer.toString(resultat.getInt(1));                    


                         return a;
          
          
          }
    } catch (SQLException ex) {

System.out.println("nopeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
    }
         
          
          return "aaaaaaaa";

        
    }
    
    
      
   
    public String getdate(int x)  {


       String requete = "SELECT date_fin FROM `promotion` WHERE `id` = ?;";
      
 PreparedStatement ps;
    try {
    ps = connection.prepareStatement(requete);
         ps.setInt(1,x);
            ResultSet resultat = ps.executeQuery();

          if(resultat.next());
          {

          String a=resultat.getString(1);                    


                         return a;
          
          
          }
    } catch (SQLException ex) {

System.out.println("nopeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
    }
         
          
          return "aaaaaaaa";

        
    }
     public Date getdate2(int x)  {


       String requete = "SELECT date_fin FROM `promotion` WHERE `id` = ?;";
      
 PreparedStatement ps;
    try {
           ps = connection.prepareStatement(requete);
         ps.setInt(1,x);
            ResultSet resultat = ps.executeQuery();

          if(resultat.next());
          {

          Date a=resultat.getDate(1);                    


                         return a;
          
          
          }
    } catch (SQLException ex) {

System.out.println("nopeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
    }
         
          
          return null;

        
    }
       
    public String listerProduit(String x)
    {
      
        
          String requete = "SELECT firstimg FROM `produit` WHERE `libelle` = ?;";
      
 PreparedStatement ps;
    try {
         ps = connection.prepareStatement(requete);
         ps.setString(1,x);
            ResultSet resultat = ps.executeQuery();

          if(resultat.next());
          {

          String a=resultat.getString(1);                    


                         return a;
          
          
          }
    } catch (SQLException ex) {

System.out.println("nopeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
    }
         
          
          return null;

     
     
}
    
    
    
    
    
    
    public int idproduit(String x)
    {
      int a=0;
        
          String requete = "SELECT id FROM `produit` WHERE `nom` = ?;";
      
 PreparedStatement ps;
    try {
           ps = connection.prepareStatement(requete);
         ps.setString(1,x);
            ResultSet resultat = ps.executeQuery();

          if(resultat.next());
          {

           a=resultat.getInt(1);                    


                         return a;
          
          
          }
    } catch (SQLException ex) {

System.out.println("nopeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
    }
         
          
          return a;

     
     
}
    
    
    
     public String esmproduit(int x)
    {
      String a="";
        
          String requete = "SELECT produit FROM `promotion` WHERE `id` = ?;";
      
 PreparedStatement ps;
    try {
           ps = connection.prepareStatement(requete);
         ps.setInt(1,x);
            ResultSet resultat = ps.executeQuery();

          if(resultat.next());
          {

           a=resultat.getString(1);                    


                         return a;
          
          
          }
    } catch (SQLException ex) {

System.out.println("nopeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
    }
         
          
          return a;

     
     
}
    
    
     
 
      public List<String> esmclient()
    {
     
        
        
      
  List<String> myList=new ArrayList();
        try {
        
        String requete = "SELECT email FROM `fos_user` ";
        statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(requete);
            
            while(rs.next())
            {
               
               
               
                myList.add(rs.getString(1));
                
            }                                    
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
     
     
}
     
     
     
     
     
     
     
     
     
     
     
     
     
     
      public List<String> esmproduit2()
    {
     
        
        
      
  List<String> myList=new ArrayList();
        try {
        
        String requete = "SELECT nom_Promotion FROM `promotion` ";
                 statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(requete);
            
            while(rs.next())
            {
               
               
               
                myList.add(rs.getString(1));
                
            }                                    
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
     
     
}
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
       public List<String> listerDate()
    {
        List<String> myList=new ArrayList();
        try {
        
        String requete4="Select * from promotion";      
               statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(requete4);
            
       
            
            while(rs.next())
            {
              
                myList.add(rs.getString(4));
                
            }                                    
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }
      
      
      
      
         
     public List<String> esmproduit10(String x)
    {
      List<String> myList=new ArrayList();
        
          String requete = "SELECT produit FROM `promotion` WHERE `date_fin` = ?;";
      
 PreparedStatement ps;
    try {
      ps = connection.prepareStatement(requete);
         ps.setString(1,x);
            ResultSet resultat = ps.executeQuery();

          if(resultat.next());
          {

          myList.add(resultat.getString(1));                    


                         return myList;
          
          
          }
    } catch (SQLException ex) {

System.out.println("nopeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
    }
         
          
          return myList;

     
     
}
      public int idpromo()
      {
          int a=-1;
           String requete =("SELECT id FROM promotion  ORDER BY id DESC");
           PreparedStatement ps;
    try {
         statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(requete);
      
         

          if(rs.next());
          {
          
          a=rs.getInt(1);                    


                         return a;
          
          
          }
    } catch (SQLException ex) {

System.out.println("nopeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
    }
         
          
          return a;
      }
      
      
      
      
      
      
      
      
      
      
      public Date getdate3(int x)  {


       String requete = "SELECT date_debut FROM `promotion` WHERE `id` = ?;";
      
 PreparedStatement ps;
    try {
                  ps = connection.prepareStatement(requete);

         ps.setInt(1,x);
            ResultSet resultat = ps.executeQuery();

          if(resultat.next());
          {

          Date a=resultat.getDate(1);                    


                         return a;
          
          
          }
    } catch (SQLException ex) {

System.out.println("nopeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
    }
         
          
          return null;

        
    }
       
       
      public float getidpromo(String x)  {


       String requete = "SELECT prix_initiale FROM `promotion` WHERE `nom_Promotion` = ?;";
      
 PreparedStatement ps;
    try {
                  ps = connection.prepareStatement(requete);

         ps.setString(1,x);
            ResultSet resultat = ps.executeQuery();

          if(resultat.next());
          {

          float a=resultat.getFloat(1);                    


                         return a;
          
          
          }
    } catch (SQLException ex) {

System.out.println("nopeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
    }
         
          
          return 0;

        
    }
       
      
       public List<Integer> getState() {
        String req11 = "Select produit_id  From vente__produit ";
        List<Integer> liste = new ArrayList<Integer>();
        try {
             statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(req11);
          
            while (rs.next()) {
                  
                
                liste.add(rs.getInt(1));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return liste;
    }
         public String getState1(int x) {
             String g="";
        String req11 = "Select libelle  From produit where id=? ";
       
         PreparedStatement ps;
        try {
               ps = connection.prepareStatement(req11);

         ps.setInt(1,x);
            ResultSet rs = ps.executeQuery();
          
            while (rs.next()) {
                  
                
                g=rs.getString(1);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return g;
    }
      
      public Integer getState12(int x) {
             int g=0;
        String req11 = "Select nbr_produit  From vente__produit where produit_id=? ";
       
         PreparedStatement ps;
        try {
               ps = connection.prepareStatement(req11);

         ps.setInt(1,x);
            ResultSet rs = ps.executeQuery();
          
            while (rs.next()) {
                  
                
                g=g+rs.getInt(1);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return g;
    }
      
      
      
      
      
}
