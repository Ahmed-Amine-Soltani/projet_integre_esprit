/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entities.Utilisateur;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.util.Map;

public class SignInForm extends com.codename1.ui.Form {

    ConnectionRequest connectionRequest;

    TextField username, password;

    public SignInForm(Resources res) {
        super(BoxLayout.y());

        Utilisateur utilisateur = new Utilisateur();

        username = new com.codename1.ui.TextField("", "nom d'utilisateur", 20, TextField.ANY);
        password = new com.codename1.ui.TextField("", "Password", 20, TextField.PASSWORD);
        username.setSingleLineTextArea(false);
        password.setSingleLineTextArea(false);

        Button SeConnecter = new Button("Connexion");
        Button CreerCompte = new Button("Créer un compte");

        SeConnecter.requestFocus();

        CreerCompte.addActionListener((ee) -> {
            register r = new register(res);
            r.show();

        });

        Container content = BoxLayout.encloseY(username, password, SeConnecter, CreerCompte);
        content.setScrollableY(true);
        add(content);

        SeConnecter.addActionListener((e) -> {
            String url = "http://127.0.0.1:8000/api/loginApi";
            connectionRequest = new ConnectionRequest(url, false);
            connectionRequest.addArgument("username", username.getText());
            connectionRequest.addArgument("password", password.getText());
            connectionRequest.addResponseListener((action) -> {
                try {

                    JSONParser j = new JSONParser();
                    String json = new String(connectionRequest.getResponseData()) + "";
                    if (json.equals("failed")) {
                        Dialog.show("Echec d'authenfication", "username ou mot de passe éronné", "Ok", null);
                    } else {
                        System.out.println(json);

                        Map<String, Object> user = j.parseJSON(new CharArrayReader(json.toCharArray()));
                        float id = Float.parseFloat(user.get("id").toString());
                        System.out.println((int) id);
                        SessionManager.setId((int) id);
                        SessionManager.setPass(password.getText());
                        SessionManager.setUserName(user.get("username").toString());
                        SessionManager.setEmail(user.get("email").toString());

                        if (user.size() > 0) {
                            new AccueilEvenementForm(res).show();

                        }
                    }

                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            });
            NetworkManager.getInstance().addToQueue(connectionRequest);

        });

    }

}
