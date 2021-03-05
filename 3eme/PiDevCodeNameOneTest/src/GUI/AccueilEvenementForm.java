/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entities.Evenement;
import Services.EvenementServices;
import com.codename1.components.InteractionDialog;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.ui.util.Resources;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Tabs;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import java.util.ArrayList;
import java.util.List;
import API.SendSMS;
import com.codename1.ui.FontImage;
import com.codename1.ui.plaf.RoundBorder;

/**
 *
 * @author solta
 */
public class AccueilEvenementForm extends BaseForm {

    public AccueilEvenementForm(Resources res) {
        Toolbar tb = new Toolbar(false);
        setToolbar(tb);
        tb.setTitle("Accueil Evenement");
        getContentPane().setScrollVisible(true);
        super.addSideMenu(res);


        List<Evenement> listEvenement = EvenementServices.TsEvenement();

        Tabs carrousel = new Tabs();
        for (Evenement even : listEvenement) {
            ajouterAuTab(carrousel, even);
        }

        carrousel.setUIID("Container");
        carrousel.getContentPane().setUIID("Container");
        carrousel.hideTabs();

        add(LayeredLayout.encloseIn(carrousel));

        Image addEvent = res.getImage("ajouter.png").scaledSmallerRatio(100, 100);
        Button events_btn = new Button(addEvent);
        events_btn.setUIID("CalendarSelectedDay");
        Label events_label = new Label("Ajouter Evenement");
        events_label.setUIID("TitleIcon");
        Container icon1 = GridLayout.encloseIn(2,
                events_label,
                events_btn
        );
        events_btn.addActionListener((evs) -> {
            new AjouterEvenementForm(res).show();
        });
        icon1.setLeadComponent(events_btn);

        Image allEveny = res.getImage("TsEvenement.png").scaledSmallerRatio(100, 100);
        Button newevent_btn = new Button(allEveny);
        newevent_btn.setUIID("CalendarSelectedDay");
        Label newevent_label = new Label("Tous Evenements");
        newevent_label.setUIID("TitleIcon");
        Container icon2 = GridLayout.encloseIn(2,
                newevent_label,
                newevent_btn
        );
        newevent_btn.addActionListener((newevs) -> {
            new TsEvenementForm(res).show();
        });
        icon2.setLeadComponent(newevent_btn);

        Image reservationImage = res.getImage("reservation.png").scaledSmallerRatio(100, 100);
        Button mesReservationBouton = new Button(reservationImage);
        mesReservationBouton.setUIID("CalendarSelectedDay");
        Label mesReservationLabel = new Label("Mes Reservations");
        mesReservationLabel.setUIID("TitleIcon");
        Container icon3 = GridLayout.encloseIn(2,
                mesReservationLabel,
                mesReservationBouton
        );
        mesReservationBouton.addActionListener((mesRes) -> {
            new MesReservationForm(res).show();
        });
        icon3.setLeadComponent(mesReservationBouton);

        Image mesEvenementImage = res.getImage("mesEvenement.png").scaledSmallerRatio(100, 100);
        Button mesEvenementBouton = new Button(mesEvenementImage);
        mesEvenementBouton.setUIID("CalendarSelectedDay");
        Label mesEvenementLabel = new Label("Mes Evenement");
        mesEvenementLabel.setUIID("TitleIcon");
        Container icon4 = GridLayout.encloseIn(2,
                mesEvenementLabel,
                mesEvenementBouton
        );
        mesEvenementBouton.addActionListener((myevs) -> {
            new MesEvenementsForm(res).show();
        });
        icon4.setLeadComponent(mesEvenementBouton);

        Button test = new Button("test");

        Container icon5 = GridLayout.encloseIn(2,
                test
        );
        test.addActionListener((zzzzz) -> {
            List<Evenement> testlist = new ArrayList<Evenement>();
            testlist = EvenementServices.TsEvenement();
            System.out.println(testlist.get(testlist.size() - 1).getId_Evenement());
        });
        icon5.setLeadComponent(test);

        add(BoxLayout.encloseY(
                createLineSeparator(),
                createLineSeparator(),
                BoxLayout.encloseY(
                        icon1,
                        icon2,
                        icon3,
                        icon4
                )));

    }

    private void ajouterAuTab(Tabs carrousel, Evenement even) {
        Font largeBoldSystemFont = Font.createSystemFont(Font.FACE_MONOSPACE, Font.STYLE_BOLD, Font.SIZE_MEDIUM);
        Font dateFont = Font.createSystemFont(Font.FACE_MONOSPACE, Font.STYLE_BOLD, Font.SIZE_SMALL);
        Image afficheEvenement = UrlAffiche(even.getAffiche_evenement());
        ScaleImageLabel image = new ScaleImageLabel(afficheEvenement);

        int heightDetails = Display.getInstance().convertToPixels(6.5f);
        int widthDetails = Display.getInstance().convertToPixels(7f);

        Button detailsBouton = new Button();
        detailsBouton.addActionListener((e) -> {
            //spanlabel malti line label
            SpanLabel sp = new SpanLabel();
            InteractionDialog dlg = new InteractionDialog();
            dlg.setLayout(new BorderLayout());
            sp.setText("Nom: " + even.getNom_evenement() + "\n"
                    + "Type: " + even.getType_evenement() + "\n"
                    + "Date debut: " + Services.EvenementServices.getDate(even.getDate_debut_evenement()) + "\n"
                    + "Date fin: " + Services.EvenementServices.getDate(even.getDate_fin_evenement()) + "\n"
                    + "Places restantes: " + even.getCapacite_evenement()
            );
            sp.setTextUIID("textDialog");

            Button commentairesBouton = new Button("Commentaires");
            commentairesBouton.setUIID("CalendarHourSelected");
            commentairesBouton.addActionListener((eee) -> {

                new CommentaireEvenementForm(Resources.getGlobalResources(), even).show();

            });

            Button reservationBouton = new Button("Reserver");
            reservationBouton.setUIID("CalendarHourSelected");
            reservationBouton.addActionListener((eee) -> {
                List<Evenement> verifReservation = EvenementServices.mesReservations(String.valueOf(SessionManager.getId()));
                for (Evenement evenement : verifReservation) {
                    if (evenement.getId_Evenement() == even.getId_Evenement()) {
                        Dialog.show("désolé", "vous etre déja inscrit a cet evenement", "Ok", null);
                        return;
                    }
                }

                EvenementServices.reserverEvenement(String.valueOf(SessionManager.getId()), String.valueOf(even.getId_Evenement()));
                SendSMS.sendSMSreservation(even.getNom_evenement());
                Dialog.show("Succès", "Evénement réservé avec succès", "Ok", null);

            });

            Container cntDetails = new Container(new BorderLayout());

            Button closeButton = new Button();
            Style closeStyle = closeButton.getAllStyles();
            closeStyle.setFgColor(0xffffff);
            closeStyle.setBgTransparency(0);
            closeStyle.setPaddingUnit(Style.UNIT_TYPE_DIPS);
            closeStyle.setPadding(3, 3, 3, 3);
            closeStyle.setBorder(RoundBorder.create().shadowOpacity(100));
            FontImage.setMaterialIcon(closeButton, FontImage.MATERIAL_CLOSE);
            closeButton.addActionListener((ee) -> dlg.dispose());

            dlg.addComponent(BorderLayout.NORTH, FlowLayout.encloseRight(closeButton));
            dlg.addComponent(BorderLayout.CENTER, sp);
            dlg.addComponent(BorderLayout.SOUTH, BoxLayout.encloseX(reservationBouton, commentairesBouton));

            Dimension pre = dlg.getContentPane().getPreferredSize();
            int h = Display.getInstance().getDisplayHeight();
            int w = Display.getInstance().getDisplayWidth();

            dlg.show(h / 2, w / 6, 20, 50);
        });

        Container carrouselContainer = new Container();
        carrouselContainer.setUIID("WelcomeContent");
        carrouselContainer.setLeadComponent(detailsBouton);
        carrouselContainer.setLayout(new BorderLayout());
        carrouselContainer.addComponent(BorderLayout.CENTER, new ScaleImageLabel(afficheEvenement));

        carrouselContainer.addComponent(BorderLayout.NORTH, BoxLayout.encloseY(
                FlowLayout.encloseCenter(createForFont(largeBoldSystemFont, even.getNom_evenement())),
                FlowLayout.encloseCenter(BoxLayout.encloseX(createForFont(dateFont, Services.EvenementServices.getDate(even.getDate_debut_evenement())),
                        createForFont(dateFont, Services.EvenementServices.getDate(even.getDate_fin_evenement()))
                ))));
        carrousel.addTab("", carrouselContainer);

    }

    public Image UrlAffiche(String nomAffiche) {
        String url = "http://127.0.0.1:8000/api/Affiche?img=" + nomAffiche;
        EncodedImage placeholder = EncodedImage.createFromImage(Resources.getGlobalResources().getImage("load.png").scaledWidth(Math.round(Display.getInstance().getDisplayWidth())), false);
        Image urli = URLImage.createToStorage(placeholder, "Medium_" + url, url, URLImage.RESIZE_SCALE);

        return urli;

    }
}
