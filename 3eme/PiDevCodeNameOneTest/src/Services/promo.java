/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.promoo;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author solta
 */
public class promo {

    public ArrayList<promoo> parseListTaskJson(String json) {

        ArrayList<promoo> listTasks = new ArrayList<>();

        try {
            JSONParser j = new JSONParser();
            Map<String, Object> tasks = j.parseJSON(new CharArrayReader(json.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasks.get("root");

            for (Map<String, Object> obj : list) {

                promoo e = new promoo();
                float id = Float.parseFloat(obj.get("id").toString());
                    float idd = Float.parseFloat(obj.get("prixInitiale").toString());
                float iddd = Float.parseFloat(obj.get("prixPromo").toString());
                 float idddd = Float.parseFloat(obj.get("pourcentage").toString());

                e.setNom(obj.get("nomPromotion").toString());
                e.setPrix_initiale((float) idd);
                e.setPrix_promo((float) iddd);
                 e.setPourcentage((float) iddd);

                System.out.println(e);
                listTasks.add(e);

            }

        } catch (IOException ex) {
        }

        return listTasks;

    }

    ArrayList<promoo> listRs = new ArrayList<>();

    public ArrayList<promoo> getList3() {
        ArrayList<promoo> listRs = new ArrayList<>();

        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://127.0.0.1:8000/api/alll");

        NetworkManager.getInstance().addToQueueAndWait(con);

        JSONParser j = new JSONParser();
        String json = new String(con.getResponseData()) + "";

        Map<String, Object> content;
        try {
            content = j.parseJSON(new CharArrayReader(json.toCharArray()));
            System.out.println(content + "///////////////////");

            List<Map<String, Object>> personnes = (List<Map<String, Object>>) content.get("root");
            for (Map<String, Object> pers : personnes) {
                float a = (Float) Float.parseFloat(pers.get("id").toString());

                System.out.println("/////////////////////" + a);
                promo mar = new promo();
                listRs = mar.parseListTaskJson(new String(con.getResponseData()));

            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return listRs;
    }
}
