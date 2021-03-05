/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entities.Evenement;
import Services.EvenementServices;
import com.codename1.capture.Capture;
import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Stroke;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.RoundBorder;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.codename1.ui.validation.LengthConstraint;
import com.codename1.ui.validation.RegexConstraint;
import com.codename1.ui.validation.Validator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author solta
 */
public class AjouterEvenementForm extends BaseForm {

    public static String i;

    public AjouterEvenementForm(Resources res) {
        super(BoxLayout.y());

        Toolbar tb = new Toolbar(false);
        setToolbar(tb);
        tb.setTitle("Créer un événement");
        getContentPane().setScrollVisible(true);
        super.addSideMenu(res);

        Form previous = Display.getInstance().getCurrent();
        tb.addCommandToRightBar(null, res.getImage("back.png"), e -> previous.showBack());

        /*
        Form previous = Display.getInstance().getCurrent();
        tb.addCommandToRightBar(null, res.getImage("back.png"), e -> previous.showBack());
         */
        TextField nomEvenement = new TextField("", "...", 20, TextArea.ANY);
        Style nomEvenementStyle = nomEvenement.getAllStyles();
        Stroke borderStroke = new Stroke(2, Stroke.CAP_SQUARE, Stroke.JOIN_MITER, 1);
        nomEvenementStyle.setBorder(RoundBorder.create().
                rectangle(true).
                color(0xffffff).
                strokeColor(0).
                strokeOpacity(120).
                stroke(borderStroke));
        nomEvenementStyle.setMarginUnit(Style.UNIT_TYPE_DIPS);
        nomEvenementStyle.setMargin(Component.BOTTOM, 3);

        addStringValue("Titre", nomEvenement);
        nomEvenement.addActionListener((e) -> {
            System.out.println(nomEvenement.getText());
        });

        Picker typeEvenement = new Picker();

        Style typeEvenementStyle = typeEvenement.getAllStyles();
        typeEvenementStyle.setBorder(RoundBorder.create().
                rectangle(true).
                color(0xffffff).
                strokeColor(0).
                strokeOpacity(120).
                stroke(borderStroke));
        typeEvenementStyle.setMarginUnit(Style.UNIT_TYPE_DIPS);
        typeEvenementStyle.setMargin(Component.BOTTOM, 3);

        typeEvenement.setType(Display.PICKER_TYPE_STRINGS);
        typeEvenement.setStrings("Associatif", "Culturel", "Autres");
        addStringValue("Type", typeEvenement);

        TextField lieuEvenement = new TextField("", "...", 20, TextArea.ANY);
        Style lieuEvenementStyle = lieuEvenement.getAllStyles();
        lieuEvenementStyle.setBorder(RoundBorder.create().
                rectangle(true).
                color(0xffffff).
                strokeColor(0).
                strokeOpacity(120).
                stroke(borderStroke));
        lieuEvenementStyle.setMarginUnit(Style.UNIT_TYPE_DIPS);
        lieuEvenementStyle.setMargin(Component.BOTTOM, 3);
        addStringValue("Localisation", lieuEvenement);

        TextField capaciteEvenement = new TextField("", "...", 20, TextArea.ANY);
        Style capaciteEvenementStyle = capaciteEvenement.getAllStyles();
        capaciteEvenementStyle.setBorder(RoundBorder.create().
                rectangle(true).
                color(0xffffff).
                strokeColor(0).
                strokeOpacity(120).
                stroke(borderStroke));
        capaciteEvenementStyle.setMarginUnit(Style.UNIT_TYPE_DIPS);
        capaciteEvenementStyle.setMargin(Component.BOTTOM, 3);
        addStringValue("Capacité", capaciteEvenement);

        Picker dateDebutEvenement = new Picker();

        Style dateDebutEvenementStyle = dateDebutEvenement.getAllStyles();
        dateDebutEvenementStyle.setBorder(RoundBorder.create().
                rectangle(true).
                color(0xffffff).
                strokeColor(0).
                strokeOpacity(120).
                stroke(borderStroke));
        dateDebutEvenementStyle.setMarginUnit(Style.UNIT_TYPE_DIPS);
        dateDebutEvenementStyle.setMargin(Component.BOTTOM, 3);

        dateDebutEvenement.setDate(new Date());
        dateDebutEvenement.setType(Display.PICKER_TYPE_DATE);
        dateDebutEvenement.addActionListener((datecheck) -> {
            Date datedeb = new Date();
            if (dateDebutEvenement.getDate().getTime() < datedeb.getTime()) {
                Dialog.show("Date invalide", "Veuillez choisir une date valide", "OK", null);
            }
        });
        addStringValue("Date Debut", dateDebutEvenement);

        Picker dateFinEvenement = new Picker();

        Style dateFinEvenementStyle = dateFinEvenement.getAllStyles();
        dateFinEvenementStyle.setBorder(RoundBorder.create().
                rectangle(true).
                color(0xffffff).
                strokeColor(0).
                strokeOpacity(120).
                stroke(borderStroke));
        dateFinEvenementStyle.setMarginUnit(Style.UNIT_TYPE_DIPS);
        dateFinEvenementStyle.setMargin(Component.BOTTOM, 3);

        dateFinEvenement.setDate(new Date());
        dateFinEvenement.setType(Display.PICKER_TYPE_DATE);
        dateFinEvenement.addActionListener((datecheck) -> {
            Date datefin = new Date();
            if (dateFinEvenement.getDate().getTime() * 1000L < datefin.getTime() * 1000L) {
                Dialog.show("Date invalide", "Veuillez choisir une date valide", "OK", null);
            }
        });
        addStringValue("Date Fin", dateFinEvenement);

        TextField descriptionEvenement = new TextField("", "...", 20, TextArea.ANY);
        Style descriptionEvenementStyle = descriptionEvenement.getAllStyles();
        descriptionEvenementStyle.setBorder(RoundBorder.create().
                rectangle(true).
                color(0xffffff).
                strokeColor(0).
                strokeOpacity(120).
                stroke(borderStroke));
        descriptionEvenementStyle.setMarginUnit(Style.UNIT_TYPE_DIPS);
        descriptionEvenementStyle.setMargin(Component.BOTTOM, 3);
        addStringValue("Description", descriptionEvenement);

        Button afficheEvenementButton = new Button("Affiche");
        addStringValue("", afficheEvenementButton);

        //  Label imageForm = new Label();
        ScaleImageLabel imageForm = new ScaleImageLabel();
        TextField path = new TextField("");

        afficheEvenementButton.addActionListener(e -> {
            i = Capture.capturePhoto(Display.getInstance().getDisplayWidth(), -1);
            if (i != null) {
                Image im;
                try {
                    im = Image.createImage(i).scaledSmallerRatio(1000, 500);
                    imageForm.setIcon(im);
                    refreshTheme();
                    System.out.println("path :" + i);
                    path.setText(i);
                    ;
                } catch (IOException ex) {
                    System.out.println("Could not load image!");
                }
            }
        });

        add(LayeredLayout.encloseIn(BorderLayout.centerAbsolute(imageForm)));
        Button ajout = new Button("Ajouter");
        addStringValue("", ajout);

        Validator validateur = new Validator();
        validateur.addConstraint(nomEvenement, new LengthConstraint(1)).
                addConstraint(lieuEvenement, new RegexConstraint("^[a-zA-Z0-9 ]+$", "Veuillez choisir une localisation valide")).
                addConstraint(capaciteEvenement, new RegexConstraint("^\\d{1,10}$", "Veuillez choisir une localisation valide")).
                addConstraint(descriptionEvenement, new LengthConstraint(1));
        validateur.addSubmitButtons(ajout);

        ajout.addActionListener((add) -> {
            Date dateDebut = dateDebutEvenement.getDate();
            Date dateFin = dateFinEvenement.getDate();

            if (path.getText().equals("")) {
                Dialog.show("Echec", "Veuillez uploader une affiche", "Ok", null);
                return;
            }
            Evenement evenement = new Evenement(SessionManager.getId(), nomEvenement.getText(),
                    typeEvenement.getSelectedString(), lieuEvenement.getText(),
                    Integer.parseInt(capaciteEvenement.getText()), descriptionEvenement.getText(), path.getText(), dateDebut, dateFin);

            System.out.println(evenement.toString());

            Services.EvenementServices.ajouterEvenement(evenement);
            List<Evenement> lastEvent = new ArrayList<Evenement>();
            lastEvent = EvenementServices.TsEvenement();
            Services.EvenementServices.reserverEvenement(String.valueOf(SessionManager.getId()), String.valueOf(lastEvent.get(lastEvent.size() - 1).getId_Evenement()));
            Dialog.show("Succès", "Evénement ajouté avec succès", "Ok", null);
            new MesEvenementsForm(res).show();
            refreshTheme();

        });

    }

    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel")).
                add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }
}
