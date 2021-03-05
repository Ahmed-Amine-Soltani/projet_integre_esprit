/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entities.Utilisateur;
import Services.Service1;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import java.util.ArrayList;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;

/**
 *
 * @author solta
 */
public class register extends BaseForm {

    Form f;
    SpanLabel lb;
    TextField username;
    TextField mot_de_passe;
    TextField mail;

    Button valide;
    Button back;

    public register(Resources theme) {

        Toolbar tb = new Toolbar(false);
        setToolbar(tb);
        tb.setTitle("Créer un compte");

        mail = new TextField("", "Votre Email", 20, TextField.ANY);
        username = new TextField("", "Nom de compte", 20, TextField.ANY);
        mot_de_passe = new TextField("", "Mot de passe", 20, TextField.PASSWORD);

        valide = new Button("Inscription");

        Container content = BoxLayout.encloseY(username, mail, mot_de_passe, valide);
        content.setScrollableY(true);
        add(content);

        valide.addActionListener((e) -> {

            Service1 ser = new Service1();
            if (username.getText().isEmpty()) {
                Dialog dlg = new Dialog(" saisir votre username ");
                Button ok = new Button(new Command("OK"));
                dlg.add(ok);
                dlg.showDialog();
                return;
            } else if (mail.getText().isEmpty()) {

                Dialog dlg = new Dialog(" saisir votre adresse mail ");
                Button ok = new Button(new Command("OK"));
                dlg.add(ok);
                dlg.showDialog();
                return;
            } else if (mot_de_passe.getText().isEmpty()) {

                Dialog dlg = new Dialog("saisir votre mot de passe ");
                Button ok = new Button(new Command("OK"));
                dlg.add(ok);
                dlg.showDialog();
                return;
            }

            Utilisateur t = new Utilisateur(username.getText(), mail.getText(), mot_de_passe.getText());
            System.out.println("a" + t.toString());

            ArrayList<Utilisateur> listTasks = new ArrayList<>();

            ser.ajoutTask(t);
            Button ok = new Button(new Command("OK"));

            Dialog dlg = new Dialog("Votre compte a ete cree" + " " + t.getNom_Utilisateur());

            TextArea taa = new TextArea("Bienvenue  ");
            taa.setEditable(false);
            taa.setUIID("DialogBody");
            taa.getAllStyles().setFgColor(0);
            dlg.add(taa);

            ok.getAllStyles().setBorder(Border.createEmpty());
            ok.getAllStyles().setFgColor(0);
            ok.getUnselectedStyle().setFgColor(100000);

            dlg.add(ok);
            dlg.showDialog();

            SignInForm s = new SignInForm(theme);
            s.show();

        });

    }

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }

}
