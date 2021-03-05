/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import Entities.Commentaire;
import Entities.Experience;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author houba
 */
public class ServiceExperience {

    public ArrayList<Experience> parseListTaskJson(String json) {
        ArrayList<Experience> listExperience = new ArrayList<>();
        try {
            JSONParser j = new JSONParser();
            Map<String, Object> tasks = j.parseJSON(new CharArrayReader(json.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) tasks.get("root");
            for (Map<String, Object> obj : list) {
                Experience e = new Experience();
                float id = Float.parseFloat(obj.get("id").toString());
                e.setId((int) id);
                e.setNom(obj.get("nomExp").toString());
                e.setLieu(obj.get("lieuExp").toString());
                e.setImage_exp(obj.get("imageName").toString());
                //e.setDesc(obj.get("descExp").toString());

                System.out.println(e);
                listExperience.add(e);
            }
        } catch (IOException ex) {
        }
        System.out.println(listExperience);
        return listExperience;
    }

    public ArrayList<Commentaire> parseListTaskJson2(String json) {
        ArrayList<Commentaire> listCommentaires = new ArrayList<>();
        try {
            JSONParser j = new JSONParser();
            Map<String, Object> tasks = j.parseJSON(new CharArrayReader(json.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) tasks.get("root");
            for (Map<String, Object> obj : list) {
                Commentaire c = new Commentaire();
                float id = Float.parseFloat(obj.get("id").toString());
                c.setId((int) id);
                c.setContenu(obj.get("contenuCommentaire").toString());
                c.setDate(obj.get("dateCommentaire").toString());

                System.out.println(c);
                listCommentaires.add(c);
            }
        } catch (IOException ex) {
        }
        System.out.println(listCommentaires);
        return listCommentaires;
    }

    ArrayList<Experience> listTasks = new ArrayList<>();
    

    ////////////////////////////////////////////////////////////////////////////
    public ArrayList<Experience> listExperiences() {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost:8000/exp/adminn");
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServiceExperience ser = new ServiceExperience();
                listTasks = ser.parseListTaskJson(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listTasks;
    }

    ////////////////////////////////////////////////////////////////////////////
    public ArrayList<Experience> listMesExperiences() {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost:8000/exp/myPostss/" + "5");
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServiceExperience ser = new ServiceExperience();
                listTasks = ser.parseListTaskJson(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listTasks;
    }

    ////////////////////////////////////////////////////////////////////////////
    public void ajoutTask(Experience exp, String path) {
        ConnectionRequest con = new ConnectionRequest();// création d'une nouvelle demande de connexion
        String Url = "http://localhost:8000/exp/new?nomExp=" + exp.getNom()+  "&user=" + exp.getUser() + "&lieuExp="
                + exp.getLieu() + "&descExp=" + exp.getDesc() + "&imageName=" + exp.getImage_exp() + "&path=" + path;// création de l'URL
        System.out.println(path);
        con.setUrl(Url);// Insertion de l'URL de notre demande de connexion
        System.out.println(con.getUrl().toString());
        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());//Récupération de la réponse du serveur
            System.out.println(str);//Affichage de la réponse serveur sur la console

        });
        NetworkManager.getInstance().addToQueueAndWait(con);// Ajout de notre demande de connexion à la file d'attente du NetworkManager
    }

    ///////////////////////////////
    public void deleteExperience(int id) {
        ConnectionRequest con = new ConnectionRequest();// création d'une nouvelle demande de connexion
        String Url = "http://localhost:8000/exp/supreme/" + id;

        con.setUrl(Url);// Insertion de l'URL de notre demande de connexion

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());//Récupération de la réponse du serveur
            System.out.println(str);//Affichage de la réponse serveur sur la console

        });
        NetworkManager.getInstance().addToQueueAndWait(con);// Ajout de notre demande de connexion à la file d'attente du NetworkManager
    }

    //////////////////////////////
    public void updateExperience(Experience exp) {

        ConnectionRequest con = new ConnectionRequest();// création d'une nouvelle demande de connexion
        con.setUrl("http://localhost:8000/exp/updatee/" + exp.getId() + "/?nomExp=" + exp.getNom() + "&lieuExp=" + exp.getLieu() + "&descExp=" + exp.getDesc());
        NetworkManager.getInstance().addToQueueAndWait(con);// Ajout de notre demande de connexion à la file d'attente du NetworkManager
        String data = new String(con.getResponseData());
        JSONParser j = new JSONParser();
        try {
            System.out.println(j.parseJSON(new CharArrayReader(data.toCharArray())));
        } catch (IOException ex) {

        }
    }

    ///////////////////////////
    public void ajoutComment(Commentaire co) {

        ConnectionRequest con = new ConnectionRequest();// création d'une nouvelle demande de connexion
        String Url = "http://localhost:8000/exp/addcomm?id=" + co.getExperience() + "&contenuCommentaire=" + co.getContenu();// création de l'URL

        con.setUrl(Url);// Insertion de l'URL de notre demande de connexion     
        NetworkManager.getInstance().addToQueueAndWait(con);// Ajout de notre demande de connexion à la file d'attente du NetworkManager
        String data = new String(con.getResponseData());
        JSONParser j = new JSONParser();
        try {
            System.out.println(j.parseJSON(new CharArrayReader(data.toCharArray())));
        } catch (IOException ex) {

        }
    }

    ////////////////////
    ArrayList<Commentaire> listDetailReponses = new ArrayList<>();
    public ArrayList<Commentaire> getDetailQuestion(int id_q) {
        ConnectionRequest con = new ConnectionRequest();//Appel au service web (demande de connexion).
        String Url = "http://localhost:8000/exp/showcomm/" + id_q;
        con.setUrl(Url);

        con.addResponseListener(new ActionListener<NetworkEvent>() {

            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServiceExperience ser = new ServiceExperience();
                listDetailReponses = ser.parseListTaskJson2(new String(con.getResponseData()));//Récupération de la réponse du serveur

                System.out.println(listDetailReponses);
            }

        });

        NetworkManager.getInstance().addToQueueAndWait(con);

        return listDetailReponses;

    }
}
