/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import static com.codename1.charts.util.ColorUtil.rgb;
import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.RoundBorder;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import Services.ServiceExperience;
import Entities.Experience;
import java.util.ArrayList;

/**
 *
 * @author bhk
 */
public class Affichage {

    Form f = new Form("Experiences");
    Style s = UIManager.getInstance().getComponentStyle("MultiLine1");
    FontImage p = FontImage.createMaterial(FontImage.MATERIAL_PORTRAIT, s);
    EncodedImage placeholder = EncodedImage.createFromImage(p.scaled(p.getWidth() * 2, p.getHeight() * 2), false);

    
        
    ImageViewer iv;
    ImageViewer lineViewer;

    public Affichage(Resources theme) {

        f.getToolbar().addCommandToOverflowMenu("Ajouter", null, e -> {
            ExpAdd expAdd = new ExpAdd(theme);
            expAdd.getF().show();
        });

        f.getToolbar().addCommandToOverflowMenu("Mes Publications", null, e -> {
            ExpMyPosts expMyPosts = new ExpMyPosts(theme);
            expMyPosts.getF().show();
        });

         Style s2 = UIManager.getInstance().getComponentStyle("Title");
        FontImage iconBack = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, s2, 4);

        Form previous = Display.getInstance().getCurrent();
        f.getToolbar().addCommandToLeftBar(null,iconBack, e -> previous.showBack());
        
        ServiceExperience serviceExperience = new ServiceExperience();
        ArrayList<Experience> listExperiences = serviceExperience.listExperiences();

        for (Experience exp : listExperiences) {

            Container allCont = new Container(BoxLayout.y());
            Container detailsCont = new Container(BoxLayout.y());
            Container imgCont = new Container(BoxLayout.x());

            Style fStyle = f.getAllStyles();
            fStyle.setBgColor(rgb(236, 240, 241));

            Style allStyle = allCont.getAllStyles();
            allStyle.setPadding(20, 0, 30, 10);

            //InputStream i = Display.getInstance().getResourceAsStream(getClass(), "C:\\wamp64\\www\\worldfriendship\\web\\assets\\img\\experience\\uploads\\" + exp.getImage_exp());
            //Image img = new Image(new InputStream(Display.getInstance().getResourceAsStream(getClass(), "C:\\wamp64\\www\\worldfriendship\\web\\assets\\img\\experience\\uploads\\" + exp.getImage_exp())));
            //Image img;
            //iv = new ImageViewer();
            //img = theme.getImage(exp.getImage_exp());
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

            Button b = new Button("Lire");
            b.getUnselectedStyle().setAlignment(Component.CENTER);
            b.getUnselectedStyle().setBorder(
                    RoundBorder.create().color(rgb(52, 152, 219)).rectangle(true)
            );

            Label nom;
            Label lieu;
            nom = new Label("Nom : " + exp.getNom());
            lieu = new Label("Lieu : " + exp.getLieu());

            nom.getAllStyles().setFont(Font.createSystemFont(Font.FACE_MONOSPACE, Font.STYLE_PLAIN, Font.SIZE_LARGE));
            lieu.getAllStyles().setFont(Font.createSystemFont(Font.FACE_MONOSPACE, Font.STYLE_PLAIN, Font.SIZE_LARGE));

            allCont.add(imgCont);
            detailsCont.add(nom).add(lieu).add(b);
            imgCont.add(iv).add(detailsCont);
            f.add(allCont).add(lineViewer);
            f.show();

            b.addPointerPressedListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    ExpDetails expDetails = new ExpDetails(exp, theme);
                    expDetails.getF().show();
                }
            });

            //lb = new SpanLabel("");
            //lb.setText(serviceTask.listExperiences().toString());
        }

    }

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }
}
