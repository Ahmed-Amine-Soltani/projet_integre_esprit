/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entities.forum;
import Services.ServiceForum;
import static com.codename1.charts.util.ColorUtil.rgb;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import java.util.ArrayList;

/**
 *
 * @author Moez
 */
public class MesQues {

    Form f = new Form("Mes Questions");

    public MesQues() {

        
        Style s = UIManager.getInstance().getComponentStyle("Title");
        FontImage iconBack = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, s, 4);

        Form previous = Display.getInstance().getCurrent();
        f.getToolbar().addCommandToLeftBar(null,iconBack, e -> previous.showBack());

        ServiceForum serviceForum = new ServiceForum();
        ArrayList<forum> listQuestions = serviceForum.listMesQuestions(String.valueOf(SessionManager.getId()));

        for (forum fo : listQuestions) {
            Container element = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            Container element1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));

            Container mod = new Container(new BoxLayout(BoxLayout.X_AXIS));

            Label title = new Label("title : " + fo.getTitle());
            Label description = new Label("description : " + fo.getDescription());

            Button button = new Button("X");

            button.addActionListener((e -> {
                serviceForum.deleteQuestion(fo.getId());
                f.refreshTheme();
                Dialog.setDefaultBlurBackgroundRadius(8);
                Dialog.show("Notification", "Question Supprimée", "OK", null);
                MesQues a = new MesQues();
                a.getF().show();

            }));

            Button button2 = new Button("V");

            button2.addActionListener((e -> {
                QuesUpdate expUpdate = new QuesUpdate(fo);
                expUpdate.getF().show();

            }));

            button.getAllStyles().setFgColor(rgb(231, 76, 60));

            mod.add(button).add(button2);
            element.add(title).add(description).add(mod);

            element1.add(element);
            f.add(element1);
            f.show();

        }

    }

    public Form getF() {
        return f;
    }

}
