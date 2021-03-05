/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.comment;
import Entities.forum;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Moez
 */
public class ServiceForum {
    
     public void ajoutQues(forum fo) {
        ConnectionRequest con = new ConnectionRequest();
        String Url = "http://localhost:8000/newm?title=" + fo.getTitle()+ "&description=" + fo.getDescription()+ "&user=" +fo.getUser();
        con.setUrl(Url);
        
        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
            System.out.println(str);

        });
        NetworkManager.getInstance().addToQueueAndWait(con);
    }
    
        public ArrayList<forum> parseListTaskJson(String json) {

        ArrayList<forum> listForums = new ArrayList<>();

        try {
            JSONParser j = new JSONParser();

           
            Map<String, Object> tasks = j.parseJSON(new CharArrayReader(json.toCharArray()));
        
            List<Map<String, Object>> list = (List<Map<String, Object>>) tasks.get("root");

            
            for (Map<String, Object> obj : list) {
                
                forum e = new forum();

                float id = Float.parseFloat(obj.get("id").toString());
                //float id_Utilisateur = Float.parseFloat(obj.get("id_Utilisateur").toString());

                e.setId((int) id);
                e.setTitle(obj.get("title").toString());
                e.setDescription(obj.get("description").toString());
                //e.setUser((int) id_Utilisateur);
                System.out.println(e);
                
                listForums.add(e);

            }

        } catch (IOException ex) {
        }
        
      
        return listForums;

    }
    
    
    
    
    
    
    ArrayList<forum> listForums = new ArrayList<>();
    
    public ArrayList<forum> getList2(){       
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost:8000/listm");  
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServiceForum ser = new ServiceForum();
                listForums = ser.parseListTaskJson(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listForums;
    }
    
    public ArrayList<forum> listMesQuestions(String id) {
        ConnectionRequest con = new ConnectionRequest();
            con.setUrl("http://localhost:8000/myQues/" +id);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServiceForum ser = new ServiceForum();
                listForums = ser.parseListTaskJson(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listForums;
    }
    
    public void deleteQuestion(int id) {
        ConnectionRequest con = new ConnectionRequest();// création d'une nouvelle demande de connexion
        String Url = "http://localhost:8000/SuppQues/" + id;

        con.setUrl(Url);// Insertion de l'URL de notre demande de connexion

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());//Récupération de la réponse du serveur
            System.out.println(str);//Affichage de la réponse serveur sur la console

        });
        NetworkManager.getInstance().addToQueueAndWait(con);// Ajout de notre demande de connexion à la file d'attente du NetworkManager
    }
    
    public void updateQuestion(forum fo) {

        ConnectionRequest con = new ConnectionRequest();// création d'une nouvelle demande de connexion
        con.setUrl("http://localhost:8000/updateQues/" + fo.getId() + "?title=" + fo.getTitle()+ "&description=" + fo.getDescription());
        NetworkManager.getInstance().addToQueueAndWait(con);// Ajout de notre demande de connexion à la file d'attente du NetworkManager
        String data = new String(con.getResponseData());
        JSONParser j = new JSONParser();
        try {
                System.out.println(j.parseJSON(new CharArrayReader(data.toCharArray())));
        } catch (IOException ex) {

        }
    }
    
    /////////////////////////
    
    public void ajoutComment(comment co) {
     
            ConnectionRequest con = new ConnectionRequest();// création d'une nouvelle demande de connexion
            String Url ="http://localhost:8000/ajComment?id=" + co.getIdForum()+ "&contenu=" + co.getContenu();// création de l'URL
          
            con.setUrl(Url);// Insertion de l'URL de notre demande de connexion     
            NetworkManager.getInstance().addToQueueAndWait(con);// Ajout de notre demande de connexion à la file d'attente du NetworkManager
            String data = new String(con.getResponseData());
            JSONParser j = new JSONParser();
        try {
            System.out.println(j.parseJSON(new CharArrayReader(data.toCharArray())));
        } catch (IOException ex) {
        
        }
    }
    
    public ArrayList<comment> parseDetailReponseJson(String json) {

        ArrayList<comment> listReponses = new ArrayList<>();

        try {
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParsr permettant le parsing du résultat jsone

            Map<String, Object> Reponses = j.parseJSON(new CharArrayReader(json.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) Reponses.get("root");
            

            for (Map<String, Object> repObj : list) {
                comment reponse = new comment();

                //ajouter l'objet reponse
                float id = Float.parseFloat(repObj.get("id").toString());
                
                reponse.setIdCommentaire((int) id);
                reponse.setContenu(repObj.get("contenu").toString());
                reponse.setDate(repObj.get("date").toString());
                

                listReponses.add(reponse);
            }

        } catch (IOException ex) {
        }

        return listReponses;

    }

    ArrayList<comment> listDetailReponses = new ArrayList<>();

    public ArrayList<comment> getDetailQuestion(int id_q) {
        ConnectionRequest con = new ConnectionRequest();//Appel au service web (demande de connexion).
        String Url="http://localhost:8000/affComment/" +id_q;
        con.setUrl(Url);

        con.addResponseListener(new ActionListener<NetworkEvent>() {

            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServiceForum ser = new ServiceForum();
                listDetailReponses = ser.parseDetailReponseJson(new String(con.getResponseData()));//Récupération de la réponse du serveur
                
                System.out.println(listDetailReponses);
            }

        });

        NetworkManager.getInstance().addToQueueAndWait(con);

        return listDetailReponses;

    }
    
    
}
