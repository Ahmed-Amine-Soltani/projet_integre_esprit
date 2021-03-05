/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entities.forum;
import Services.ServiceForum;
import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.validation.LengthConstraint;
import com.codename1.ui.validation.Validator;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Moez
 */
public class HomeForum {

    Form f;
    TextField title;
    TextField description;
    Button btnajout, btnaff;

    public HomeForum( ) {


         Style s = UIManager.getInstance().getComponentStyle("Title");
        FontImage iconBack = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, s, 4);

        Form previous = Display.getInstance().getCurrent();
        f = new Form("Ajouter votre question");
        title = new TextField("", "title");
        description = new TextField("", "description");
        
        f.getToolbar().addCommandToLeftBar(null,iconBack, e -> previous.showBack());


        Date actuelle = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(actuelle);
        String dc = date;

        btnajout = new Button("ajouter");
        btnaff = new Button("Affichage");
        f.add(title);
        f.add(description);
        f.add(btnajout);
        f.add(btnaff);
        btnajout.addActionListener((e) -> {
            ServiceForum ser = new ServiceForum();
            forum f = new forum(0, SessionManager.getId(), title.getText(), description.getText(), dc);
            ser.ajoutQues(f);
            Dialog.show("Info", "  Ajout avec succés ", "ok", "");

        });
        btnaff.addActionListener((e) -> {
            AffichageForum a = new AffichageForum();
            a.getF().show();

        });

        Validator val = new Validator();
        val.addConstraint(title, new LengthConstraint(2));
        val.addConstraint(description, new LengthConstraint(2));
    }

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }

}
