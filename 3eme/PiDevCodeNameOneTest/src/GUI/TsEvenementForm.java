/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entities.Evenement;
import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Tabs;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import java.util.ArrayList;
import Services.EvenementServices;
import com.codename1.components.InteractionDialog;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.URLImage;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;

/**
 *
 * @author solta
 */
public class TsEvenementForm extends BaseForm {

    public TsEvenementForm(Resources res) {
        super(BoxLayout.y());
        refreshTheme();
        Toolbar tb = new Toolbar(false);
        setToolbar(tb);
        tb.setTitle("Tous les Evenements");
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

        Label titre = new Label("Tous les Evenements");
        titre.getAllStyles().setFgColor(0x0099ff);
        add(FlowLayout.encloseCenter(titre));

        ArrayList<Evenement> events = EvenementServices.TsEvenement();
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
            Label text = new Label("pas d'événement");
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
        ta.setUIID("NewsTopLine");
        ta.setEditable(false);

        int heightDetails = Display.getInstance().convertToPixels(6.5f);
        int widthDetails = Display.getInstance().convertToPixels(7f);

        Button detailsBouton = new Button(Resources.getGlobalResources().getImage("detail.png").fill(widthDetails, heightDetails));
        detailsBouton.addActionListener((e) -> {
            SpanLabel sp = new SpanLabel();
            InteractionDialog dlg = new InteractionDialog("Details");
            dlg.setLayout(new BorderLayout());
            sp.setText("Nom: " + even.getNom_evenement() + "\n"
                    + "Type: " + even.getType_evenement() + "\n"
                    + "Date debut: " + Services.EvenementServices.getDate(even.getDate_debut_evenement()) + "\n"
                    + "Date fin: " + Services.EvenementServices.getDate(even.getDate_fin_evenement()) + "\n"
                    + "Places restantes: " + even.getCapacite_evenement()
            );
            sp.setTextUIID("textDialog");
            dlg.add(BorderLayout.CENTER, sp);

            Button res = new Button("Reserver");
            res.addActionListener((eee) -> {
                EvenementServices.reserverEvenement(String.valueOf(SessionManager.getId()), String.valueOf(even.getId_Evenement()));
                // SendSMS.sendSMSreservation(even.getNom_evenement());
                Dialog.show("Succès", "Evénement réservé avec succès", "Ok", null);

            });
            Button close = new Button("Fermer");
            close.addActionListener((ee) -> dlg.dispose());
            Container cntDetails = new Container(BoxLayout.x());
            cntDetails.add(res);
            cntDetails.add(close);
            dlg.addComponent(BorderLayout.SOUTH, cntDetails);
            Dimension pre = dlg.getContentPane().getPreferredSize();
            int h = Display.getInstance().getDisplayHeight();
            dlg.show(h / 2, 80, 20, 50);
        });

        Container cnt = new Container();
        cnt.setLeadComponent(detailsBouton);
        cnt.setLayout(new BorderLayout());
        cnt.addComponent(BorderLayout.WEST, new ScaleImageLabel(afficheEvenement));
        cnt.addComponent(BorderLayout.CENTER, FlowLayout.encloseCenter(new Label(even.getNom_evenement())));

        return cnt;
    }

    public Image UrlAffiche(String nomAffiche) {
        String url = "http://127.0.0.1:8000/api/Affiche?img=" + nomAffiche;
        EncodedImage placeholder = EncodedImage.createFromImage(Resources.getGlobalResources().getImage("load.png").scaledWidth(Math.round(Display.getInstance().getDisplayWidth())), false);
        Image urli = URLImage.createToStorage(placeholder, "Medium_" + url, url, URLImage.RESIZE_SCALE);

        return urli;

    }

}
