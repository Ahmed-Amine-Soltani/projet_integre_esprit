/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entities.Evenement;
import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import java.util.ArrayList;
import Services.EvenementServices;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;

/**
 *
 * @author solta
 */
public class MesEvenementsForm extends BaseForm {

    public MesEvenementsForm(Resources res) {
        super(BoxLayout.y());
        refreshTheme();
        Toolbar tb = new Toolbar(false);
        setToolbar(tb);
        tb.setTitle("Mes Evenements");
        getContentPane().setScrollVisible(true);
        super.addSideMenu(res);

        Form previous = Display.getInstance().getCurrent();
        tb.addCommandToRightBar(null, res.getImage("back.png"), e -> previous.showBack());

        ArrayList<Evenement> listEvenement = EvenementServices.TsEvenement();
        Tabs carrousel = new Tabs();

        for (Evenement even : listEvenement) {
            ajouterAuTab(carrousel, even);
        }

        carrousel.setUIID("Container");
        carrousel.getContentPane().setUIID("Container");
        carrousel.hideTabs();

        add(LayeredLayout.encloseIn(carrousel));

        Label titre = new Label("Mes Evenements");

        add(FlowLayout.encloseCenter(titre));
        titre.getAllStyles().setFgColor(0x0099ff);

        ArrayList<Evenement> events = EvenementServices.mesEvenements(String.valueOf(SessionManager.getId()));
        if (events.size() != 0) {

            for (Evenement event : events) {
                add(addButton(event));
            }

        } else {
            refreshTheme();
            Image afficheEvenement = res.getImage("pasEvenement.png");
            ScaleImageLabel image = new ScaleImageLabel(afficheEvenement);
            image.setUIID("Container");
            image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FIT);
            Label text = new Label("vous n'avez aucun evenement");
            add(FlowLayout.encloseCenter(text));
            add(LayeredLayout.encloseIn(image));

        }
    }

    private void ajouterAuTab(Tabs carrousel, Evenement even) {
        Font largeBoldSystemFont = Font.createSystemFont(Font.FACE_MONOSPACE, Font.STYLE_BOLD, Font.SIZE_MEDIUM);
        Font dateFont = Font.createSystemFont(Font.FACE_MONOSPACE, Font.STYLE_BOLD, Font.SIZE_SMALL);
        Image afficheEvenement = UrlAffiche(even.getAffiche_evenement());
        ScaleImageLabel image = new ScaleImageLabel(afficheEvenement);

        Container carrouselContainer = new Container();
        carrouselContainer.setLayout(new BorderLayout());
        carrouselContainer.addComponent(BorderLayout.CENTER, new ScaleImageLabel(afficheEvenement));

        carrouselContainer.addComponent(BorderLayout.NORTH, BoxLayout.encloseY(
                FlowLayout.encloseCenter(createForFont(largeBoldSystemFont, even.getNom_evenement())),
                FlowLayout.encloseCenter(BoxLayout.encloseX(createForFont(dateFont, Services.EvenementServices.getDate(even.getDate_debut_evenement())),
                        createForFont(dateFont, Services.EvenementServices.getDate(even.getDate_fin_evenement()))
                ))));

        carrousel.addTab("", carrouselContainer);
    }

    private Container addButton(Evenement even) {
        int height = Display.getInstance().convertToPixels(15f);
        int width = Display.getInstance().convertToPixels(20);
        Image afficheEvenement = UrlAffiche(even.getAffiche_evenement()).fill(width, height);

        TextArea ta = new TextArea(even.getNom_evenement());
        ta.setUIID("HugeDarkLabel");
        ta.setEditable(false);

        Button supprimerEvenementBouton = new Button(Resources.getGlobalResources().getImage("supprimer.png").scaledSmallerRatio(100, 100));
        supprimerEvenementBouton.setUIID("CalendarSelectedDay");
        supprimerEvenementBouton.addActionListener((e) -> {
            Dialog.show("Succès", "Evénement supprimé avec succès", "Ok", null);
            Services.EvenementServices.supprimerEvenement(String.valueOf(even.getId_Evenement()));
            System.out.println(even.getId_Evenement());
            new MesEvenementsForm(Resources.getGlobalResources()).show();

        });

        Button modifierEvenementBouton = new Button(Resources.getGlobalResources().getImage("modifier.png").scaledSmallerRatio(100, 100));
        modifierEvenementBouton.setUIID("CalendarSelectedDay");

        modifierEvenementBouton.addActionListener((e) -> {
            new ModifierEvenementForm(Resources.getGlobalResources(), even).show();
            refreshTheme();
        });

        Container cnt = new Container();
        cnt.setLayout(new BorderLayout());
        cnt.addComponent(BorderLayout.WEST, new ScaleImageLabel(afficheEvenement));
        cnt.addComponent(BorderLayout.NORTH, FlowLayout.encloseCenter(ta));
        cnt.addComponent(BorderLayout.CENTER, FlowLayout.encloseCenter(BoxLayout.encloseX(
                modifierEvenementBouton, supprimerEvenementBouton)));

        return cnt;
    }

    public Image UrlAffiche(String nomAffiche) {
        String url = "http://127.0.0.1:8000/api/Affiche?img=" + nomAffiche;
        EncodedImage placeholder = EncodedImage.createFromImage(Resources.getGlobalResources().getImage("load.png").scaledWidth(Math.round(Display.getInstance().getDisplayWidth())), false);
        Image urli = URLImage.createToStorage(placeholder, "Medium_" + url, url, URLImage.RESIZE_SCALE);

        return urli;

    }

}
