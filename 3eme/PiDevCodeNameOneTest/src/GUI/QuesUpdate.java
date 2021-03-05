/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entities.forum;
import Services.ServiceForum;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.PickerComponent;
import com.codename1.ui.TextComponent;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.TextModeLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.validation.LengthConstraint;
import com.codename1.ui.validation.Validator;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Moez
 */
public class QuesUpdate {

    TextModeLayout tl = new TextModeLayout(3, 2);

    Form f = new Form("Modifier votre question", tl);

    public QuesUpdate(forum fo) {
       
        Style s = UIManager.getInstance().getComponentStyle("Title");
        FontImage iconBack = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, s, 4);

        Form previous = Display.getInstance().getCurrent();
        f.getToolbar().addCommandToLeftBar(null,iconBack, e -> previous.showBack());
        String id = String.valueOf(fo.getId());
        Label idd = new Label(id);
        int iddd = Integer.parseInt(idd.getText());

        TextComponent title = new TextComponent().label("title");
        TextComponent description = new TextComponent().label("Description").multiline(true);
        
        Date actuelle = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(actuelle);
        String dc = date;

        Button add = new Button("Modifier");
        add.addActionListener((e -> {
            ServiceForum serviceForum = new ServiceForum();
            forum f = new forum(iddd, title.getText(), description.getText(),
                    dc);

            serviceForum.updateQuestion(f);

            System.out.println(f);

            Dialog.setDefaultBlurBackgroundRadius(8);
            Dialog.show("Notification", "Question Modifiée", "OK", null);
            MesQues a=new MesQues();
                a.getF().show();

        }));

        Validator val = new Validator();
        val.addConstraint(title, new LengthConstraint(2));
        val.addConstraint(description, new LengthConstraint(2));

        f.add(tl.createConstraint().horizontalSpan(2), title);
        f.add(tl.createConstraint().horizontalSpan(2), description);

        f.add(add);

    }

    public Form getF() {
        return f;
    }

}
