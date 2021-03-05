/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entities.forum;
import Services.ServiceForum;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import java.util.ArrayList;

/**
 *
 * @author Moez
 */
public class AffichageForum {

    public Form f;
    public Button b1;

    public AffichageForum() {
        
        

        f = new Form("Liste Questions");
        Container line1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        
         Style s = UIManager.getInstance().getComponentStyle("Title");
        FontImage iconBack = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, s, 4);

        Form previous = Display.getInstance().getCurrent();
        f.getToolbar().addCommandToLeftBar(null,iconBack, e -> previous.showBack());
        
        //Style S1 = line1.getAllStyles();
        //S1.setBorder(Border.createLineBorder(1));
        //S1.setPadding(20,20,20,20);

        ServiceForum serviceForum = new ServiceForum();
        ArrayList<forum> lis = serviceForum.getList2();

        f.getToolbar().addCommandToOverflowMenu("Ajouter", null, e -> {
            HomeForum QuesAdd = new HomeForum();
            QuesAdd.getF().show();
        });

        f.getToolbar().addCommandToOverflowMenu("Mes Questions", null, e -> {
            MesQues mesQues = new MesQues();
            mesQues.getF().show();
        });

        for (forum fo : lis) {
            

            Label title = new Label("Title: " + fo.getTitle());
            Label description = new Label("Description: " + fo.getDescription());
            b1 = new Button("Commenter");

            line1.add(title);
            line1.add(description);
            line1.add(b1);
            

            b1.addActionListener((e -> {
                int id_q = fo.getId();
                ajouterCom ajcom = new ajouterCom(id_q);
                ajcom.getF().show();
            }));

            //line1.setLeadComponent(b1);
            //line1.setScrollableY(false);
        }
        
        f.add(line1);
        f.show();

    }

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }

}
