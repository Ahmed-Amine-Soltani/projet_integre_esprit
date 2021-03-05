/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entities.promoo;
import Services.promo;
import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.TOP;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import javafx.scene.text.Text;

/**
 *
 * @author solta
 */
public class promotion extends BaseForm {

    Form f;
    Button valide;
    SpanLabel lb;

    Container cn1;
    ImageViewer iv;
    Image img;
    Label lblnomeve;
    Label nomeve;
    Label prix;
    Label promo;
    Label por;
    Text l;
    BoxLayout tt;
    Button t;

    public promotion(Resources theme) {
        f = new Form("WorldFriendShip", new FlowLayout(CENTER, TOP));

        /* Toolbar tb = new Toolbar(false);
        setToolbar(tb);
        tb.setTitle("Promotion");
        getContentPane().setScrollVisible(true);
        super.addSideMenu(theme);
        Form previous = Display.getInstance().getCurrent();
        tb.addCommandToRightBar(null, theme.getImage("back.png"), e -> previous.showBack());
         */
        f.setTitle("Promotion");
        promo ME = new promo();
        t = new Button();

        
        Style s = UIManager.getInstance().getComponentStyle("Title");
        FontImage iconBack = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, s, 4);

        Form previous = Display.getInstance().getCurrent();
        f.getToolbar().addCommandToLeftBar(null,iconBack, e -> previous.showBack());
        
        for (promoo eee : ME.getList3()) {
            Container contX = new Container(new BoxLayout(BoxLayout.X_AXIS));
            Container contY = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            contX.getStyle().setBorder(Border.createLineBorder(1));

            //Label nom = new Label(p.getNom());
            //   contY.add(nom);
//            contX.add(contY);
//            f.add(contX);
//            f.add("   ");
            iv = new ImageViewer();
            img = theme.getImage(eee.getNom());
            iv.setImage(img.scaled(800, 800));
            String str3 = Float.toString(eee.getPourcentage());
            por = new Label(str3 + "%");

            contY.add(por);
            contY.add(iv);
            lblnomeve = new Label("nom eve");
            nomeve = new Label(eee.getNom());
            String str1 = Float.toString(eee.getPrix_promo());
            prix = new Label(str1 + " DT");

            String str2 = Float.toString(eee.getPrix_initiale());

            promo = new Label(str2 + " DT");

            promo.getAllStyles().setStrikeThru(true);
            prix.getAllStyles().setFgColor(0xFF0000);
            por.getAllStyles().setFgColor(0xFF0000);
            contY.add(nomeve);

            contY.add(prix);
            contY.add(promo);

            contX.add(contY);
            contY.getAllStyles().setFgColor(0xFF0000);
            contX.getAllStyles().setFgColor(000000);
            f.add(contX);
            f.add("   ");
        }
    }

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }

}
