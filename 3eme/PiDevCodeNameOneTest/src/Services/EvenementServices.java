/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.CommentaireEvenement;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import Entities.Evenement;
import GUI.SessionManager;
import com.codename1.io.MultipartRequest;
import java.text.SimpleDateFormat;
import com.codename1.ui.Dialog;
import com.twilio.rest.taskrouter.v1.workspace.task.Reservation;
import java.util.Date;

/**
 *
 * @author solta
 */
public class EvenementServices {

    public static String getDate(Date date) {
        String dateString = new SimpleDateFormat("EEEE dd MMMM yyyy").format(date);
        return dateString;
    }

    public static String getDateCommentaire(Date date) {
        String dateString = new SimpleDateFormat("dd MMMM yyyy").format(date);
        return dateString;
    }

    public static ArrayList<Evenement> TsEvenement() {
        ArrayList<Evenement> listEvenement = new ArrayList<>();
        String url = "http://127.0.0.1:8000/api/TsEvenementApi";
        ConnectionRequest request = new ConnectionRequest(url, false);
        NetworkManager.getInstance().addToQueueAndWait(request);

        JSONParser j = new JSONParser();
        String json = new String(request.getResponseData()) + "";
        if (!json.equals("no data")) {
            Map<String, Object> content;
            try {
                content = j.parseJSON(new CharArrayReader(json.toCharArray()));
                List<Map<String, Object>> events = (List<Map<String, Object>>) content.get("root");
                for (Map<String, Object> event : events) {

                    Map<String, Object> timeDebut = (Map<String, Object>) event.get("dateDebutEvenement");
                    Map<String, Object> timeFin = (Map<String, Object>) event.get("dateFinEvenement");
                    Date dateDebutEvenement = new Date((long) Double.parseDouble(timeDebut.get("timestamp").toString()) * 1000);
                    Date dateFinEvenement = new Date((long) Double.parseDouble(timeFin.get("timestamp").toString()) * 1000);
                    Map<String, Object> user = (Map<String, Object>) event.get("idUser");
                    String idUser = user.get("id").toString();

                    listEvenement.add(new Evenement(
                            (int) Float.parseFloat(idUser),
                            (int) Float.parseFloat(event.get("id").toString()),
                            (event.get("nomEvenement").toString()),
                            event.get("typeEvenement").toString(),
                            event.get("lieuEvenement").toString(),
                            (int) Float.parseFloat(event.get("capaciteEvenement").toString()),
                            event.get("descriptionEvenement").toString(),
                            event.get("affiche").toString(),
                            dateDebutEvenement,
                            dateFinEvenement
                    ));

                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
        return listEvenement;
    }

    public static void ajouterEvenement(Evenement e) {
        String url = "http://127.0.0.1:8000/api/ajouterEvenementApi";
        MultipartRequest req = new MultipartRequest();
        try {
            req.setUrl(url);
            req.setPost(true);
                req.addArgument("nomEvenement", e.getNom_evenement());
            req.addArgument("typeEvenement", e.getType_evenement());
            req.addArgument("descriptionEvenement", e.getDescription_evenement());
            req.addArgument("capaciteEvenement", String.valueOf(e.getCapacite_evenement()));
   
            
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateDebut = formatter.format(e.getDate_debut_evenement());
            String dateFin = formatter.format(e.getDate_fin_evenement());
            req.addArgument("dateDebutEvenement", dateDebut);
            req.addArgument("dateFinEvenement", dateFin);
            req.addArgument("lieuEvenement", e.getLieu_evenement());

            req.addArgument("idUtilisateur", String.valueOf(e.getId_user()));

            if (!e.getAffiche_evenement().equals("")) {
                req.addData("afficheEvenement", e.getAffiche_evenement(), "image/jpeg");
                req.setFilename("afficheEvenement", e.getNom_evenement() + ".jpg");
            }

            req.addResponseListener((response) -> {
                byte[] data = (byte[]) response.getMetaData();
                String s = new String(data);
                // System.out.println("data : " + s);
            });

            NetworkManager.getInstance().addToQueueAndWait(req);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void modifierEvenement(Evenement e) {
        String url = "http://127.0.0.1:8000/api/modifierEvenementApi";
        MultipartRequest req = new MultipartRequest();
        try {
            req.setUrl(url);
            req.setPost(true);
            req.addArgument("id", String.valueOf(e.getId_Evenement()));
            req.addArgument("nomEvenement", e.getNom_evenement());
            req.addArgument("typeEvenement", e.getType_evenement());
            req.addArgument("descriptionEvenement", e.getDescription_evenement());
            req.addArgument("capaciteEvenement", String.valueOf(e.getCapacite_evenement()));
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            String dateDebut = formatter.format(e.getDate_debut_evenement());
            String dateFin = formatter.format(e.getDate_fin_evenement());
            req.addArgument("dateDebutEvenement", dateDebut);
            req.addArgument("dateFinEvenement", dateFin);
            req.addArgument("lieuEvenement", e.getLieu_evenement());

            if (!e.getAffiche_evenement().equals("")) {
                req.addData("afficheEvenement", e.getAffiche_evenement(), "image/jpeg");
                req.setFilename("afficheEvenement", e.getNom_evenement() + ".jpg");
            }

            req.addResponseListener((response) -> {
                byte[] data = (byte[]) response.getMetaData();
                String s = new String(data);
            });

            NetworkManager.getInstance().addToQueueAndWait(req);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static ArrayList<Evenement> mesEvenements(String id) {
        ArrayList<Evenement> listEvenement = new ArrayList<>();
        String url = "http://127.0.0.1:8000/api/mesEvenementsApi";
        ConnectionRequest request = new ConnectionRequest(url, false);
        request.addArgument("id", id);
        NetworkManager.getInstance().addToQueueAndWait(request);

        JSONParser j = new JSONParser();
        String json = new String(request.getResponseData()) + "";
        if (!json.equals("no data")) {
            Map<String, Object> content;
            try {
                content = j.parseJSON(new CharArrayReader(json.toCharArray()));
                List<Map<String, Object>> events = (List<Map<String, Object>>) content.get("root");
                for (Map<String, Object> event : events) {

                    Map<String, Object> time = (Map<String, Object>) event.get("dateDebutEvenement");
                    Date dateDebutEvenement = new Date((long) Double.parseDouble(time.get("timestamp").toString()) * 1000L);
                    Date dateFinEvenement = new Date((long) Double.parseDouble(time.get("timestamp").toString()) * 1000L);
                    Map<String, Object> user = (Map<String, Object>) event.get("idUser");
                    String idUser = user.get("id").toString();
                    listEvenement.add(new Evenement(
                            (int) Float.parseFloat(idUser),
                            (int) Float.parseFloat(event.get("id").toString()),
                            (event.get("nomEvenement").toString()),
                            event.get("typeEvenement").toString(),
                            event.get("lieuEvenement").toString(),
                            (int) Float.parseFloat(event.get("capaciteEvenement").toString()),
                            event.get("descriptionEvenement").toString(),
                            event.get("affiche").toString(),
                            dateDebutEvenement,
                            dateFinEvenement
                    ));

                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return listEvenement;
    }

    public static Evenement chercherEvenementId(int id) {
        String id_string = String.valueOf(id);
        Map<String, Object> event;
        Evenement evenement = new Evenement();
        String url = "http://127.0.0.1:8000/api/chercherEvenementIdApi";
        ConnectionRequest request = new ConnectionRequest(url, false);
        request.addArgument("id", id_string);
        NetworkManager.getInstance().addToQueueAndWait(request);

        JSONParser j = new JSONParser();
        String json = new String(request.getResponseData()) + "";
        if (!json.equals("no data")) {
            try {
                event = j.parseJSON(new CharArrayReader(json.toCharArray()));

                evenement = new Evenement(
                        (int) Float.parseFloat(event.get("id").toString()),
                        event.get("nomEvenement").toString(),
                        event.get("affiche").toString());

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            evenement = null;
        }
        return evenement;
    }

    public static void supprimerEvenement(String id_string) {
        String url = "http://127.0.0.1:8000/api/supprimerEvenementApi";
        ConnectionRequest request = new ConnectionRequest(url, false);
        try {
            request.addArgument("id", id_string);
            NetworkManager.getInstance().addToQueueAndWait(request);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void reserverEvenement(String idUtilisateur, String idEvenement) {
        String url = "http://127.0.0.1:8000/api/reserverEvenementApi";
        ConnectionRequest request = new ConnectionRequest(url, false);
        try {
            request.addArgument("idEvenement", idEvenement);
            request.addArgument("idUser", idUtilisateur);
            NetworkManager.getInstance().addToQueueAndWait(request);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void annulerReserverEvenement(String idUtilisateur, String idEvenement) {
        String url = "http://127.0.0.1:8000/api/annulerReserverEvenementApi";
        ConnectionRequest request = new ConnectionRequest(url, false);
        try {
            request.addArgument("idEvenement", idEvenement);
            request.addArgument("idUser", idUtilisateur);
            NetworkManager.getInstance().addToQueueAndWait(request);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static ArrayList<Evenement> mesReservations(String idUtilisateur) {
        ArrayList<Evenement> listReservation = new ArrayList<>();
        String url = "http://127.0.0.1:8000/api/mesReservationsApi";
        ConnectionRequest request = new ConnectionRequest(url, false);
        request.addArgument("idUser", idUtilisateur);
        NetworkManager.getInstance().addToQueueAndWait(request);

        JSONParser j = new JSONParser();
        String json = new String(request.getResponseData()) + "";
        if (!json.equals("no data")) {
            Map<String, Object> content;
            try {
                content = j.parseJSON(new CharArrayReader(json.toCharArray()));
                List<Map<String, Object>> events = (List<Map<String, Object>>) content.get("root");
                for (Map<String, Object> event : events) {

                    Map<String, Object> time = (Map<String, Object>) event.get("dateDebutEvenement");
                    Date dateDebutEvenement = new Date((long) Double.parseDouble(time.get("timestamp").toString()) * 1000L);
                    Date dateFinEvenement = new Date((long) Double.parseDouble(time.get("timestamp").toString()) * 1000L);
                    Map<String, Object> user = (Map<String, Object>) event.get("idUser");
                    String idUser = user.get("id").toString();
                    listReservation.add(new Evenement(
                            (int) Float.parseFloat(idUser),
                            (int) Float.parseFloat(event.get("id").toString()),
                            (event.get("nomEvenement").toString()),
                            event.get("typeEvenement").toString(),
                            event.get("lieuEvenement").toString(),
                            (int) Float.parseFloat(event.get("capaciteEvenement").toString()),
                            event.get("descriptionEvenement").toString(),
                            event.get("affiche").toString(),
                            dateDebutEvenement,
                            dateFinEvenement
                    ));

                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return listReservation;
    }

    public static void ajouterCommentaire(CommentaireEvenement commentaireEvenement) {
        String url = "http://127.0.0.1:8000/api/ajouterCommentaireApi";
        ConnectionRequest request = new ConnectionRequest(url, false);
        try {
            String contenuCommentaire = SessionManager.getUserName() + " : \"" + commentaireEvenement.getContenu_commentaire() + "\"";
            request.addArgument("idEvenement", String.valueOf(commentaireEvenement.getId_evenement()));
            request.addArgument("idUser", String.valueOf(commentaireEvenement.getId_user()));
            request.addArgument("contenu", contenuCommentaire);
            NetworkManager.getInstance().addToQueueAndWait(request);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static ArrayList<CommentaireEvenement> afficherCommentaire(String idEvenement) {
        ArrayList<CommentaireEvenement> listCommentaire = new ArrayList<>();
        String url = "http://127.0.0.1:8000/api/afficherCommentaireApi";
        ConnectionRequest request = new ConnectionRequest(url, false);
        request.addArgument("idEvenement", idEvenement);
        NetworkManager.getInstance().addToQueueAndWait(request);

        JSONParser j = new JSONParser();
        String json = new String(request.getResponseData()) + "";
        if (!json.equals("no data")) {
            Map<String, Object> content;
            try {
                content = j.parseJSON(new CharArrayReader(json.toCharArray()));
                List<Map<String, Object>> commentaires = (List<Map<String, Object>>) content.get("root");
                for (Map<String, Object> commentaire : commentaires) {

                    Map<String, Object> time = (Map<String, Object>) commentaire.get("dateCommentaire");
                    Date dateCommentaire = new Date((long) Double.parseDouble(time.get("timestamp").toString()) * 1000L);
                    listCommentaire.add(new CommentaireEvenement(
                            dateCommentaire,
                            commentaire.get("contenu").toString()));

                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return listCommentaire;
    }

    // j'ai pas utilisé
    public static Evenement evenementAutoReservation() {

        Map<String, Object> event;
        Evenement evenement = new Evenement();
        String url = "http://127.0.0.1:8000/api/evenementAutoReservationApi";
        ConnectionRequest request = new ConnectionRequest(url, false);
        NetworkManager.getInstance().addToQueueAndWait(request);

        JSONParser j = new JSONParser();
        String json = new String(request.getResponseData()) + "";
        if (!json.equals("no data")) {
            try {
                event = j.parseJSON(new CharArrayReader(json.toCharArray()));

                evenement = new Evenement(
                        (int) Float.parseFloat(event.get("id").toString()));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            evenement = null;
        }
        return evenement;
    }
    


}
