/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import static com.codename1.charts.util.ColorUtil.rgb;
import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.RoundBorder;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import Services.ServiceExperience;
import Entities.Experience;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.plaf.UIManager;
import java.util.ArrayList;

/**
 *
 * @author houba
 */
public class ExpMyPosts {

    Form f = new Form("Mes Publications");

    ImageViewer iv;
    ImageViewer lineViewer;

    public ExpMyPosts(Resources theme) {

       Style s = UIManager.getInstance().getComponentStyle("Title");
        FontImage iconBack = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, s, 4);

        Form previous = Display.getInstance().getCurrent();
        f.getToolbar().addCommandToLeftBar(null,iconBack, e -> previous.showBack());

        ServiceExperience serviceExperience = new ServiceExperience();

        ArrayList<Experience> listExperiences = serviceExperience.listMesExperiences();

        for (Experience exp : listExperiences) {

            Container allCont = new Container(BoxLayout.y());
            Container detailsCont = new Container(BoxLayout.y());
            Container imgCont = new Container(BoxLayout.x());
            Container mod = new Container(BoxLayout.x());

            Style fStyle = f.getAllStyles();
            fStyle.setBgColor(rgb(236, 240, 241));

            Style allStyle = allCont.getAllStyles();
            allStyle.setPadding(20, 0, 30, 10);

            EncodedImage encodedImage = EncodedImage
                    .createFromImage(theme.getImage("loading.png"), true);

            String url = "http://localhost/worldfriendship/web/assets/img/experience/uploads/" + exp.getImage_exp();

            System.out.println(url);

            Image img = URLImage.createToStorage(encodedImage, url, url, URLImage.RESIZE_SCALE);
            ImageViewer iv = new ImageViewer(img);

            iv.setImage(img.scaled(400, 250));

            Image lineImage;
            lineViewer = new ImageViewer();
            lineImage = theme.getImage("line.png");
            lineViewer.setImage(lineImage);

            Button button = new Button("X");
            button.addActionListener((e -> {
                serviceExperience.deleteExperience(exp.getId());
                Dialog.setDefaultBlurBackgroundRadius(8);
                Dialog.show("Notification", "Publication Supprimée", "OK", null);

                ExpMyPosts expMyPosts = new ExpMyPosts(theme);
                expMyPosts.getF().show();
            }));

            Button button2 = new Button("Modifier");
            button2.addActionListener((e -> {
                ExpUpdate expUpdate = new ExpUpdate(theme, exp);
                expUpdate.getF().show();
            }));

            //STYLE
            button.getUnselectedStyle().setAlignment(Component.CENTER);
            button.getUnselectedStyle().setBorder(
                    RoundBorder.create()
            );

            button2.getUnselectedStyle().setAlignment(Component.CENTER);
            button2.getUnselectedStyle().setBorder(
                    RoundBorder.create().color(rgb(52, 152, 219)).rectangle(true)
            );
            //END STYLE

            Label nom;
            Label lieu;
            nom = new Label("NOM : " + exp.getNom());
            lieu = new Label("LIEU : " + exp.getLieu());

            nom.getAllStyles().setFont(Font.createSystemFont(Font.FACE_MONOSPACE, Font.STYLE_PLAIN, Font.SIZE_LARGE));
            lieu.getAllStyles().setFont(Font.createSystemFont(Font.FACE_MONOSPACE, Font.STYLE_PLAIN, Font.SIZE_LARGE));

            allCont.add(imgCont);
            mod.add(button2).add(button);
            detailsCont.add(nom).add(lieu).add(mod);
            imgCont.add(iv).add(detailsCont);
            f.add(allCont).add(lineViewer);
            f.show();

            nom.addPointerPressedListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    ExpDetails expDetails = new ExpDetails(exp, theme);
                    expDetails.getF().show();
                }
            });

            lieu.addPointerPressedListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    ExpDetails expDetails = new ExpDetails(exp, theme);
                    expDetails.getF().show();
                }
            });

        }

    }

    public Form getF() {
        return f;
    }

}
